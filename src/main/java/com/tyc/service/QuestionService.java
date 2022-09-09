package com.tyc.service;

import com.tyc.entity.Answer;
import com.tyc.entity.Question;
import com.tyc.repository.AnswerRepository;
import com.tyc.repository.QuestionRepository;
import com.tyc.utility.MyFactoryService;

public class QuestionService extends MyFactoryService<QuestionRepository, Question, Long> {
    public QuestionService() {
        super(new QuestionRepository());
    }
}
