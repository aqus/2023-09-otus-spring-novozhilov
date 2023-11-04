package ru.otus.spring.service;

import org.springframework.stereotype.Service;

import ru.otus.spring.domain.Student;

@Service
public class StudentServiceImpl implements StudentService {

    private final IOService ioService;

    public StudentServiceImpl(IOService ioService) {
        this.ioService = ioService;
    }

    @Override
    public Student determineCurrentStudent() {
        String firstName = ioService.readStringWithPrompt("Please input your first name");
        String lastName = ioService.readStringWithPrompt("Please input your last name");
        return new Student(firstName, lastName);
    }
}
