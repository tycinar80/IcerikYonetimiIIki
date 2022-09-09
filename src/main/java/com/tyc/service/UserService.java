package com.tyc.service;

import com.tyc.entity.Answer;
import com.tyc.entity.User;
import com.tyc.repository.AnswerRepository;
import com.tyc.repository.UserRepository;
import com.tyc.utility.MyFactoryService;

public class UserService extends MyFactoryService<UserRepository, User, Long> {
    public UserService() {
        super(new UserRepository());
    }
}
