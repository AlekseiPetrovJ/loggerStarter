package ru.petrov.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class ExampleRestController {
    @GetMapping
    public ResponseEntity<?> hello(@RequestParam(defaultValue = "WORLD") String name) {
        return ResponseEntity.ok("Hello " + name + ".");
    }
}

