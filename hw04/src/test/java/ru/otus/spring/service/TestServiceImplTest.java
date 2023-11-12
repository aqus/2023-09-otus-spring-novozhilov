package ru.otus.spring.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import ru.otus.spring.dao.CsvQuestionDao;
import ru.otus.spring.domain.Student;
import ru.otus.spring.domain.TestResult;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@DisplayName("TestServiceImplTest class")
@SpringBootTest
public class TestServiceImplTest {
    
    @MockBean
    private StreamsIOService ioService;
    
    @MockBean
    private CsvQuestionDao dao;
    
    @MockBean
    private PrinterFacade printerFacade;

    @Autowired
    private TestServiceImpl testService;
    
    @DisplayName("Execute test method for student should be called")
    @Test
    void executeTestMethodForStudentShouldBeCalled() {
        Student student = new Student("Vasya", "Pupkin");
        TestResult expectedTestResult = new TestResult(student);
        TestResult testResult = testService.executeTestFor(student);
        assertEquals(expectedTestResult, testResult);
        verify(printerFacade, times(1)).printFormattedLine(anyString());
        verify(dao, times(1)).findAll();
    }
}
