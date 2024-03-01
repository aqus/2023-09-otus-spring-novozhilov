package ru.otus.catalog.services;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {

    String extractUserName(String token);

    boolean isTokenValid(String token, UserDetails userDetails);

    String generateToken(Authentication authentication);
}
