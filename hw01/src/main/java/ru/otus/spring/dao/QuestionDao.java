package ru.otus.spring.dao;

import java.io.IOException;
import java.util.List;

import com.opencsv.exceptions.CsvValidationException;

import ru.otus.spring.domain.Question;

public interface QuestionDao {
    
    List<Question> findAll() throws IOException, CsvValidationException;
}
