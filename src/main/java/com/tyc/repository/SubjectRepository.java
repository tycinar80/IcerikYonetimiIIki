package com.tyc.repository;

import com.tyc.entity.Answer;
import com.tyc.entity.Subject;
import com.tyc.utility.MyFactoryRepository;

public class SubjectRepository extends MyFactoryRepository<Subject, Long> {
    public SubjectRepository() {
        super(new Subject());
    }
}
