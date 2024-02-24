package ru.otus.catalog.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.catalog.dto.UserLoginDto;
import ru.otus.catalog.services.AuthService;

@RestController
public class UserController {

    private final AuthService authService;

    public UserController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("api/v1/authenticate")
    public ResponseEntity<String> authenticate(@RequestBody UserLoginDto user) {
        return new ResponseEntity<>(authService.authenticate(user), HttpStatus.OK);
    }
}
