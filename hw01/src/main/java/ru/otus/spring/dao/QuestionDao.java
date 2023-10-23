package ru.otus.spring.dao;

import java.util.List;

import ru.otus.spring.domain.Question;
import ru.otus.spring.exceptions.QuestionReadException;

public interface QuestionDao {
    
    List<Question> findAll();
}
