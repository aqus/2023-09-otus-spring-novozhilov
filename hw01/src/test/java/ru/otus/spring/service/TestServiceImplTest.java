package ru.otus.spring.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;

@DisplayName("TestServiceImplTest class")
@ExtendWith(MockitoExtension.class)
public class TestServiceImplTest {

    @Mock
    private TestServiceImpl testService;

    @DisplayName("Execute test method should be called")
    @Test
    void executeTestMethodShouldBeCalled() {
        testService.executeTest();
        verify(testService).executeTest();
    }
}
