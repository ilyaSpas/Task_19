package org.example.springsecurity.exception;

import java.util.Date;

public class CustomException {
    private int status;
    private String message;
    private Date timestamp;

    public CustomException(int status, String message) {
        this.status = status;
        this.message = message;
        this.timestamp = new Date();
    }
}
