package com.tick42.quicksilver.exceptions;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String exception) {
        super(exception);
    }
}
