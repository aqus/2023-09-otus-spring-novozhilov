package ru.otus.catalog.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.catalog.dto.UserLoginDto;
import ru.otus.catalog.security.UserService;

@RestController
public class UserController {

    private final UserService userService;

    private final DaoAuthenticationProvider daoAuthenticationProvider;

    public UserController(UserService userService, DaoAuthenticationProvider daoAuthenticationProvider) {
        this.userService = userService;
        this.daoAuthenticationProvider = daoAuthenticationProvider;
    }

    @PostMapping("api/v1/login")
    public ResponseEntity<Boolean> login(@RequestBody UserLoginDto user) {
        UserDetails principal = userService.loadUserByUsername(user.getUsername());
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(principal, user.getPassword(), principal.getAuthorities());
        Authentication authenticate = daoAuthenticationProvider.authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authenticate);
        return new ResponseEntity<>(SecurityContextHolder.getContext().getAuthentication().isAuthenticated(),
                HttpStatus.OK);
    }
}
