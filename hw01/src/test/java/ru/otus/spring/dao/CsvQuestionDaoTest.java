package ru.otus.spring.dao;

import java.io.IOException;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.opencsv.exceptions.CsvValidationException;

import ru.otus.spring.config.TestFileNameProvider;
import ru.otus.spring.domain.Answer;
import ru.otus.spring.domain.Question;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("QuestionDao class")
public class CsvQuestionDaoTest {
    
    @DisplayName("print all the questions from file")
    @Test
    void shouldPrintAllQuestionsFromFile() throws CsvValidationException, IOException {
        TestFileNameProvider testFileNameProvider = new TestFileNameProvider() {
            @Override
            public String getTestFileName() {
                return "test.csv";
            }
        };
        CsvQuestionDao csvQuestionDao = new CsvQuestionDao(testFileNameProvider);
        Question question = new Question("What was the day on 15th august 1947?", List.of(
                new Answer("Friday", true),
                new Answer("Saturday", false),
                new Answer("Sunday", false)));
        List<Question> questions = List.of(question);
        assertEquals(questions, csvQuestionDao.findAll());
    }
}
