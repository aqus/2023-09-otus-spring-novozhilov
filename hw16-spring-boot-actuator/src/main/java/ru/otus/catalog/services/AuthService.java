package ru.otus.catalog.services;

import ru.otus.catalog.dto.UserLoginDto;

public interface AuthService {

    String authenticate(UserLoginDto user);
}
