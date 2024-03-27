package org.example.springsecurity.service;

import org.example.springsecurity.dao.UserRegistrationDao;
import org.example.springsecurity.entity.User;

public interface UserService {
    User registration(UserRegistrationDao userRegistrationDao);
}
