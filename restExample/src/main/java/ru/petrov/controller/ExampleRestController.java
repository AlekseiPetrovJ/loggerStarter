package ru.petrov.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.petrov.model.Example;

@RestController
@RequestMapping("/example")
public class ExampleRestController {
    @Operation(summary = "Приветствие по имени", description = """
            Принимает параметр name. Возвращает приветствие.
            """)
    @GetMapping()
    public ResponseEntity<?> hello(@RequestParam(defaultValue = "WORLD") String name) {
        return ResponseEntity.ok("Hello " + name + ".");
    }

    @Operation(summary = "Конткатенация строк", description = """
            Присоединяет первую строку ко второй.
            """)
    @PostMapping()
    public ResponseEntity<String> concatenation(@RequestBody Example example) {
        String answer = example.getFirstString() + example.getSecondString();
        return new ResponseEntity<>(answer, HttpStatus.OK);

    }
}

