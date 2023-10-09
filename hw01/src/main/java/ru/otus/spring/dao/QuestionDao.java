package ru.otus.spring.dao;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import ru.otus.spring.domain.Question;

public interface QuestionDao {
    
    List<Question> findAll() throws URISyntaxException, IOException;
}
