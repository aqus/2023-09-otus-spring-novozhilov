package ru.otus.spring.service;

import org.springframework.stereotype.Service;

import ru.otus.spring.domain.Student;

@Service
public class StudentServiceImpl implements StudentService {

    private final IOService ioService;
    
    private final LocalizationService localizationService;

    public StudentServiceImpl(IOService ioService, LocalizationService localizationService) {
        this.ioService = ioService;
        this.localizationService = localizationService;
    }

    @Override
    public Student determineCurrentStudent() {
        String firstName = ioService.readStringWithPrompt(localizationService.getMessage("test.first.name"));
        String lastName = ioService.readStringWithPrompt(localizationService.getMessage("test.last.name"));
        return new Student(firstName, lastName);
    }
}
