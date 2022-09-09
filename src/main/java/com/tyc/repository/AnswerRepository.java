package com.tyc.repository;

import com.tyc.entity.Answer;
import com.tyc.utility.MyFactoryRepository;

public class AnswerRepository extends MyFactoryRepository<Answer, Long> {
    public AnswerRepository() {
        super(new Answer());
    }
}
