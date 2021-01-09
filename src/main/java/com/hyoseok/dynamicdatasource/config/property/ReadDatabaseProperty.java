package com.hyoseok.dynamicdatasource.config.property;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

import java.util.List;

@Getter
@ConstructorBinding
@RequiredArgsConstructor
@ConfigurationProperties(value = "spring.datasource.hikari.read")
public class ReadDatabaseProperty {
    private final List<ReadProperty> list;
    private final String driverClassName;
    private final String username;
    private final String password;
    private final int maximumPoolSize;
    private final long maxLifetime;
    private final long connectionTimeout;

    @Getter
    @Setter
    public static class ReadProperty {
        private String name;
        private String jdbcUrl;
    }
}
