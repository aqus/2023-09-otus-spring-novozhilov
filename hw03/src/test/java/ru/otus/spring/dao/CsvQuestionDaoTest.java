package ru.otus.spring.dao;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ru.otus.spring.config.TestFileNameProvider;
import ru.otus.spring.domain.Answer;
import ru.otus.spring.domain.Question;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

@DisplayName("QuestionDao class")
@ExtendWith(MockitoExtension.class)
public class CsvQuestionDaoTest {
    
    private final static String TEST_FILE_NAME = "test.csv";

    @Mock
    private TestFileNameProvider testFileNameProvider;
    @InjectMocks
    private CsvQuestionDao questionDao;

    @BeforeEach
    void setUp() {
        given(testFileNameProvider.getTestFileName()).willReturn(TEST_FILE_NAME);
    }

    @DisplayName("Read all the questions from dao")
    @Test
    void shouldReadAllQuestionsFromDao() {
        Question expectedQuestion = new Question("What was the day on 15th august 1947?", List.of(
                new Answer("Friday", true),
                new Answer("Saturday", false),
                new Answer("Sunday", false)));
        List<Question> expectedQuestions = List.of(expectedQuestion);
        assertEquals(expectedQuestions, questionDao.findAll());
    }
}
