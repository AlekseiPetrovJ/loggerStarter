package ru.petrov.loggerstarter.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "http-logger")
public class LogProperty {
    private boolean enabled;
}