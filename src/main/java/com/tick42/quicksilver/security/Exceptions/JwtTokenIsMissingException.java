package com.tick42.quicksilver.security.Exceptions;

public class JwtTokenIsMissingException extends RuntimeException {


    public JwtTokenIsMissingException(String exception) {
        super(exception);
    }

}
