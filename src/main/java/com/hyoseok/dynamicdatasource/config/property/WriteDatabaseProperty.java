package com.hyoseok.dynamicdatasource.config.property;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@Getter
@ConstructorBinding
@RequiredArgsConstructor
@ConfigurationProperties(value = "spring.datasource.hikari.write")
public class WriteDatabaseProperty {
    private final String driverClassName;
    private final String jdbcUrl;
    private final String username;
    private final String password;
    private final int maximumPoolSize;
    private final long maxLifetime;
    private final long connectionTimeout;
}
