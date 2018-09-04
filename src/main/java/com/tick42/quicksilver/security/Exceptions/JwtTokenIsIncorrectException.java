package com.tick42.quicksilver.security.Exceptions;

public class JwtTokenIsIncorrectException extends RuntimeException {

    public JwtTokenIsIncorrectException(String exception) {
        super(exception);
    }


}
