package com.tyc.repository;

import com.tyc.entity.Answer;
import com.tyc.entity.Lesson;
import com.tyc.utility.MyFactoryRepository;

public class LessonRepository extends MyFactoryRepository<Lesson, Long> {
    public LessonRepository() {
        super(new Lesson());
    }
}
