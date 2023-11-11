package ru.otus.spring.service;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import ru.otus.spring.config.LocaleProvider;

@Service
public class LocalizationServiceImpl implements LocalizationService {
    
    private final MessageSource messageSource;
    
    private final LocaleProvider localeProvider;

    public LocalizationServiceImpl(MessageSource messageSource, LocaleProvider localeProvider) {
        this.messageSource = messageSource;
        this.localeProvider = localeProvider;
    }

    @Override
    public String getMessage(String key) {
        return messageSource.getMessage(key, null, localeProvider.getLocale());
    }

    @Override
    public String getMessage(String key, Object[] args) {
        return messageSource.getMessage(key, args, localeProvider.getLocale());
    }
}
