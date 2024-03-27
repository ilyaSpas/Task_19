package org.example.springsecurity.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
public class UserAuthDao {

    private String username;

    private String password;
}
