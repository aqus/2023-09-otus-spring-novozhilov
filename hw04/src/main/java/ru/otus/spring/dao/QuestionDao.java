package ru.otus.spring.dao;

import java.util.List;

import ru.otus.spring.domain.Question;

public interface QuestionDao {
    
    List<Question> findAll();
}
