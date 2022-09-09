package com.tyc.service;

import com.tyc.entity.Answer;
import com.tyc.entity.Subject;
import com.tyc.repository.AnswerRepository;
import com.tyc.repository.SubjectRepository;
import com.tyc.utility.MyFactoryService;

public class SubjectService extends MyFactoryService<SubjectRepository, Subject, Long> {
    public SubjectService() {
        super(new SubjectRepository());
    }
}
