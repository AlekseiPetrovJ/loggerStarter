package ru.petrov.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class Example {


    @NotEmpty(message = "first string not might be empty")
    @Schema(example = "Hello")
    String firstString;

    @NotEmpty(message = "first string not might be empty")
    @Schema(example = "world")
    String secondString;
}
