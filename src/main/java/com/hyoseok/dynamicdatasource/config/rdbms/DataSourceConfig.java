package com.hyoseok.dynamicdatasource.config.rdbms;

import com.hyoseok.dynamicdatasource.config.rdbms.property.ReadDatabaseProperty;
import com.hyoseok.dynamicdatasource.config.rdbms.property.WriteDatabaseProperty;
import com.zaxxer.hikari.HikariDataSource;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@Configuration
@Profile(value = "replication") // active가 test이면 제외하고, 나머지 모두 적용
@RequiredArgsConstructor
@EnableTransactionManagement // DataSourceTransactionManager Bean을 찾아 Transaction Manager로 사용한다.
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class}) // 기존의 DataSourceAutoConfiguration을 제외하고...
@EnableConfigurationProperties(value = {WriteDatabaseProperty.class, ReadDatabaseProperty.class})
@EnableJpaRepositories(basePackages = {"com.hyoseok.dynamicdatasource.domain"})
public class DataSourceConfig {

    private final WriteDatabaseProperty writeDatabaseProperty;
    private final ReadDatabaseProperty readDatabaseProperty;

    @Bean
    public DataSource writeDataSource() throws SQLException {
        HikariDataSource hikariDataSource = DataSourceBuilder.create()
                .driverClassName(writeDatabaseProperty.getDriverClassName())
                .username(writeDatabaseProperty.getUsername())
                .password(writeDatabaseProperty.getPassword())
                .url(writeDatabaseProperty.getJdbcUrl())
                .type(HikariDataSource.class)
                .build();

        hikariDataSource.setPoolName("HikariWritePool");
        hikariDataSource.setMaximumPoolSize(writeDatabaseProperty.getMaximumPoolSize());
        hikariDataSource.setMaxLifetime(writeDatabaseProperty.getMaxLifetime());
        hikariDataSource.setConnectionTimeout(writeDatabaseProperty.getConnectionTimeout());
        hikariDataSource.getConnection().isValid(Long.valueOf(writeDatabaseProperty.getConnectionTimeout()).intValue());

        return hikariDataSource;
    }

    @Bean
    public Map<String, DataSource> readDataSourceMap() {
        Map<String, DataSource> dataSourceMap = new HashMap<>();

        readDatabaseProperty.getList().forEach(readProperty -> {
            HikariDataSource hikariDataSource = DataSourceBuilder.create()
                    .driverClassName(readDatabaseProperty.getDriverClassName())
                    .username(readDatabaseProperty.getUsername())
                    .password(readDatabaseProperty.getPassword())
                    .url(readProperty.getJdbcUrl())
                    .type(HikariDataSource.class)
                    .build();

            hikariDataSource.setPoolName("HikariReadPool");
            hikariDataSource.setMaximumPoolSize(readDatabaseProperty.getMaximumPoolSize());
            hikariDataSource.setMaxLifetime(readDatabaseProperty.getMaxLifetime());
            hikariDataSource.setConnectionTimeout(readDatabaseProperty.getConnectionTimeout());

            try {
                int connectionTimeout = Long.valueOf(readDatabaseProperty.getConnectionTimeout()).intValue();
                hikariDataSource.getConnection().isValid(connectionTimeout);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

            dataSourceMap.put(readProperty.getName(),hikariDataSource);
        });

        return dataSourceMap;
    }

    @Bean
    public DataSource routingDataSource() throws SQLException {
        Map<Object, Object> dataSourceMap = new HashMap<>();

        DataSource writeDataSource = writeDataSource();
        Map<String, DataSource> readDataSourceMap = readDataSourceMap();

        dataSourceMap.put("write", writeDataSource);
        readDataSourceMap.keySet().forEach(key -> dataSourceMap.put(key, readDataSourceMap.get(key)));

        DynamicRoutingDataSource dynamicRoutingDataSource = new DynamicRoutingDataSource();
        dynamicRoutingDataSource.setTargetDataSources(dataSourceMap);
        dynamicRoutingDataSource.setDefaultTargetDataSource(writeDataSource);

        return dynamicRoutingDataSource;
    }

    @Bean
    @Primary
    public DataSource dataSource() throws SQLException {
        // routingDataSource을 등록시켜, 연결할 때마다 Write / Read를 분기시키는 역할을 한다.
        return new LazyConnectionDataSourceProxy(routingDataSource());
    }
}
