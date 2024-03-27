package org.example.springsecurity.dao;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDao {
    private Long id;
    private String username;
    private String email;
}
