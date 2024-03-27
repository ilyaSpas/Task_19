package org.example.springsecurity.service.impl;

import org.example.springsecurity.dao.UserRegistrationDao;
import org.example.springsecurity.entity.Role;
import org.example.springsecurity.entity.User;
import org.example.springsecurity.repository.UserRepository;
import org.example.springsecurity.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final ModelMapper modelMapper;

    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }

    public User registration(UserRegistrationDao userRegistrationDao){
        User user = modelMapper.map(userRegistrationDao, User.class);
        user.setPassword(passwordEncoder.encode(userRegistrationDao.getPassword()));
        user.setRole(Role.USER);
        return userRepository.save(user);
    }
}
