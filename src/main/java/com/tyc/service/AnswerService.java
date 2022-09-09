package com.tyc.service;

import com.tyc.entity.Answer;
import com.tyc.repository.AnswerRepository;
import com.tyc.utility.MyFactoryService;

public class AnswerService extends MyFactoryService<AnswerRepository, Answer, Long> {
    public AnswerService() {
        super(new AnswerRepository());
    }
}
