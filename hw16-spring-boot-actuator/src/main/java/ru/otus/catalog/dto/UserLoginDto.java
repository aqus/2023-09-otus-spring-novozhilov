package ru.otus.catalog.dto;

public class UserLoginDto {

    private final String username;

    private final String password;


    public UserLoginDto(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
