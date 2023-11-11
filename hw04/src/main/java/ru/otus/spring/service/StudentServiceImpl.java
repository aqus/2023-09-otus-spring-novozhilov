package ru.otus.spring.service;

import org.springframework.stereotype.Service;

import ru.otus.spring.domain.Student;

@Service
public class StudentServiceImpl implements StudentService {
    
    private final PrinterFacade printerFacade;

    public StudentServiceImpl(PrinterFacade printerFacade) {
        this.printerFacade = printerFacade;
    }

    @Override
    public Student determineCurrentStudent() {
        String firstName = printerFacade.readStringWithPrompt("test.first.name");
        String lastName = printerFacade.readStringWithPrompt("test.last.name");
        return new Student(firstName, lastName);
    }
}
