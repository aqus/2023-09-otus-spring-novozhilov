package ru.otus.spring.service;

public interface LocalizationService {
    
    String getMessage(String key);

    String getMessage(String key, Object[] args);
}
