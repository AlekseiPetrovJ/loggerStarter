package ru.petrov.config;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@AutoConfiguration
@EnableConfigurationProperties(LogProperty.class)
@ConditionalOnProperty(prefix = "http-logger", name = "enabled", havingValue = "true", matchIfMissing = false)
public class LogAutoConfiguration {
}