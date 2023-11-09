package ru.otus.spring.service;

import ru.otus.spring.domain.Student;
import ru.otus.spring.domain.TestResult;

public interface TestService {

    TestResult executeTestFor(Student student);
}
