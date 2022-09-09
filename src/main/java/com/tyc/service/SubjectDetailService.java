package com.tyc.service;

import com.tyc.entity.Answer;
import com.tyc.entity.SubjectDetail;
import com.tyc.repository.AnswerRepository;
import com.tyc.repository.SubjectDetailRepository;
import com.tyc.utility.MyFactoryService;

public class SubjectDetailService extends MyFactoryService<SubjectDetailRepository, SubjectDetail, Long> {
    public SubjectDetailService() {
        super(new SubjectDetailRepository());
    }
}
