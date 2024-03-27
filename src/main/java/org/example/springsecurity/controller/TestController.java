package org.example.springsecurity.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/USER")
    public String getUserData() {
        return "USER";
    }

    @GetMapping("/MODERATOR")
    public String getModeratorData() {
        return "MODERATOR";
    }

    @GetMapping("/ADMIN")
    public String getAdminData() {
        return "ADMIN";
    }

    @GetMapping("/info")
    public String getInfo() {
        return "info";
    }
}
