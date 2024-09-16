package com.example.effectivemobiletest.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.testcontainers.containers.PostgreSQLContainer;

@TestConfiguration(proxyBeanMethods = false)
public class TestContainersConfiguration {
    @Bean
    @ServiceConnection
    public PostgreSQLContainer<?> postgreSQLContainer() {
        try (PostgreSQLContainer<?> container = new PostgreSQLContainer<>("postgres:13.8")) {
            container.withDatabaseName("concordia-db").withInitScript("schema-initialization.sql");
            return container;
        }
    }
}