package com.hyoseok.dynamicdatasource.config;

import com.hyoseok.dynamicdatasource.config.property.ReadDatabaseProperty;
import com.hyoseok.dynamicdatasource.config.property.WriteDatabaseProperty;
import com.zaxxer.hikari.HikariDataSource;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
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
import java.util.HashMap;
import java.util.Map;

@Configuration
@Profile(value = "!test") // active가 test이면 제외하고, 나머지 모두 적용
@RequiredArgsConstructor
@EnableTransactionManagement // DataSourceTransactionManager Bean을 찾아 Transaction Manager로 사용한다.
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class}) // 기존의 DataSourceAutoConfiguration을 제외하고...
@EnableConfigurationProperties(value = {WriteDatabaseProperty.class, ReadDatabaseProperty.class})
@EnableJpaRepositories(basePackages = {"com.hyoseok.dynamicdatasource.domain"})
public class DataSourceConfig {

    private final WriteDatabaseProperty writeDatabaseProperty;
    private final ReadDatabaseProperty readDatabaseProperty;

    @Bean
    public DataSource writeDataSource() {
        HikariDataSource hikariDataSource = DataSourceBuilder.create()
                .driverClassName(writeDatabaseProperty.getDriverClassName())
                .username(writeDatabaseProperty.getUsername())
                .password(writeDatabaseProperty.getPassword())
                .url(writeDatabaseProperty.getJdbcUrl())
                .type(HikariDataSource.class)
                .build();

        hikariDataSource.setPoolName("HikariWritePool");
        hikariDataSource.setMaximumPoolSize(writeDatabaseProperty.getMaximumPoolSize());
        hikariDataSource.setMinimumIdle(writeDatabaseProperty.getMinimumIdle());
        hikariDataSource.setMaxLifetime(writeDatabaseProperty.getMaxLifetime());
        hikariDataSource.setConnectionTimeout(writeDatabaseProperty.getConnectionTimeout());

        return hikariDataSource;
    }

    @Bean
    public DataSource readDataSource() {
        System.out.println(readDatabaseProperty.getUsername());
        HikariDataSource hikariDataSource = DataSourceBuilder.create()
                .driverClassName(readDatabaseProperty.getDriverClassName())
                .username(readDatabaseProperty.getUsername())
                .password(readDatabaseProperty.getPassword())
                .url(readDatabaseProperty.getJdbcUrl())
                .type(HikariDataSource.class)
                .build();

        hikariDataSource.setPoolName("HikariReadPool");
        hikariDataSource.setMaximumPoolSize(readDatabaseProperty.getMaximumPoolSize());
        hikariDataSource.setMinimumIdle(readDatabaseProperty.getMinimumIdle());
        hikariDataSource.setMaxLifetime(readDatabaseProperty.getMaxLifetime());
        hikariDataSource.setConnectionTimeout(readDatabaseProperty.getConnectionTimeout());

        return hikariDataSource;
    }

    /*
    * @Qualifier 어노테이션을 사용한 이유?
    * DataSource 타입의 Bean 객체가 여러개 등록되어 있기 때문에, 상황에 따라 원하는 Bean 객체를 사용하기 위해서 설정함
    * */
    @Bean
    public DataSource routingDataSource(@Qualifier("writeDataSource") DataSource writeDataSource,
                                        @Qualifier("readDataSource") DataSource readDataSource) {
        Map<Object, Object> dataSourceMap = new HashMap<>();
        dataSourceMap.put("write", writeDataSource);
        dataSourceMap.put("read", readDataSource);

        DynamicRoutingDataSource dynamicRoutingDataSource = new DynamicRoutingDataSource();
        dynamicRoutingDataSource.setTargetDataSources(dataSourceMap);
        dynamicRoutingDataSource.setDefaultTargetDataSource(writeDataSource);

        return dynamicRoutingDataSource;
    }

    @Bean
    @Primary
    public DataSource dataSource(@Qualifier("routingDataSource") DataSource routingDataSource) {
        // routingDataSource을 등록시켜, 연결할 때마다 Write / Read를 분기시키는 역할을 한다.
        return new LazyConnectionDataSourceProxy(routingDataSource);
    }
}
