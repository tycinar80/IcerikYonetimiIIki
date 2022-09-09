package com.tyc.repository;

import com.tyc.entity.Answer;
import com.tyc.entity.User;
import com.tyc.utility.MyFactoryRepository;

public class UserRepository extends MyFactoryRepository<User, Long> {
    public UserRepository() {
        super(new User());
    }
}
