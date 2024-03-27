package org.example.springsecurity.service;

import org.example.springsecurity.utils.JwtTokenUtils;
import org.example.springsecurity.dao.CustomTokenDao;
import org.example.springsecurity.dao.UserAuthDao;
import org.example.springsecurity.dao.UserDao;
import org.example.springsecurity.dao.UserRegistrationDao;
import org.example.springsecurity.entity.User;
import org.example.springsecurity.exception.CustomException;
import org.example.springsecurity.repository.UserRepository;
import org.example.springsecurity.security.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;

    private final UserRepository userRepository;

    private final UserService userService;

    private final JwtTokenUtils jwtTokenUtils;

    private final CustomUserDetailsService customUserDetailsService;

    @Autowired
    public AuthService(AuthenticationManager authenticationManager, UserRepository userRepository, UserService userService, JwtTokenUtils jwtTokenUtils, CustomUserDetailsService customUserDetailsService) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.userService = userService;
        this.jwtTokenUtils = jwtTokenUtils;
        this.customUserDetailsService = customUserDetailsService;
    }

    public ResponseEntity<?> createAuthToken(@RequestBody UserAuthDao userAuthDao) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userAuthDao.getUsername(), userAuthDao.getPassword()));
        } catch (BadCredentialsException e) {
            return new ResponseEntity<>(new CustomException(HttpStatus.UNAUTHORIZED.value(), "Неправильный логин или пароль"), HttpStatus.UNAUTHORIZED);
        }
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(userAuthDao.getUsername());
        String token = jwtTokenUtils.generateToken(userDetails);
        return ResponseEntity.ok(new CustomTokenDao(token));
    }

    public ResponseEntity<?> createNewUser(@RequestBody UserRegistrationDao userRegistrationDao) {
        if (userRepository.findByUsername(userRegistrationDao.getUsername()).isPresent()) {
            return new ResponseEntity<>(new CustomException(HttpStatus.BAD_REQUEST.value(), "Пользователь с указанным именем уже существует"), HttpStatus.BAD_REQUEST);
        }
        User user = userService.registration(userRegistrationDao);
        return ResponseEntity.ok(new UserDao(user.getId(), user.getUsername(), user.getEmail()));
    }
}
