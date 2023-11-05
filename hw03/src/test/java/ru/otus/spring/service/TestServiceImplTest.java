package ru.otus.spring.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ru.otus.spring.dao.CsvQuestionDao;
import ru.otus.spring.domain.Student;
import ru.otus.spring.domain.TestResult;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@DisplayName("TestServiceImplTest class")
@ExtendWith(MockitoExtension.class)
public class TestServiceImplTest {
    
    @Mock
    StreamsIOService ioService;
    
    @Mock
    CsvQuestionDao dao;
    
    @Mock
    LocalizationService localizationService;

    @InjectMocks
    private TestServiceImpl testService;
    
    @BeforeEach
    void setUp() {
        when(localizationService.getMessage(anyString())).thenReturn(anyString());
    }
    
    @DisplayName("Execute test method for student should be called")
    @Test
    void executeTestMethodForStudentShouldBeCalled() {
        Student student = new Student("Vasya", "Pupkin");
        TestResult expectedTestResult = new TestResult(student);
        TestResult testResult = testService.executeTestFor(student);
        assertEquals(expectedTestResult, testResult);
        verify(ioService, times(1)).printLine(anyString());
        verify(ioService, times(1)).printFormattedLine(anyString());
        verify(localizationService, times(1)).getMessage(anyString());
        verify(dao, times(1)).findAll();
    }
}
