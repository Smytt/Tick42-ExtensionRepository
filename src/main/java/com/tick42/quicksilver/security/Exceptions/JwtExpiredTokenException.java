package com.tick42.quicksilver.security.Exceptions;

public class JwtExpiredTokenException extends RuntimeException{

    public JwtExpiredTokenException(String exception) {
        super(exception);
    }
}
