package com.tyc.service;

import com.tyc.entity.Answer;
import com.tyc.entity.Lesson;
import com.tyc.repository.AnswerRepository;
import com.tyc.repository.LessonRepository;
import com.tyc.utility.MyFactoryService;

public class LessonService extends MyFactoryService<LessonRepository, Lesson, Long> {
    public LessonService() {
        super(new LessonRepository());
    }
}
