package ru.petrov.loggerstarter.config;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.petrov.loggerstarter.filter.LoggingFilter;

@AutoConfiguration
@EnableConfigurationProperties(LogProperty.class)
@ConditionalOnProperty(prefix = "http-logger", name = "enabled", havingValue = "true", matchIfMissing = false)
@Configuration
public class LogAutoConfiguration {
    @Bean
    public LoggingFilter loggingFilter() {
        return new LoggingFilter();
    }
}