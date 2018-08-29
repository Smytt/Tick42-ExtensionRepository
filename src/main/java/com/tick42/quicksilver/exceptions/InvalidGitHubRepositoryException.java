package com.tick42.quicksilver.exceptions;

public class InvalidGitHubRepositoryException extends RuntimeException {
    public InvalidGitHubRepositoryException(String exception) {
        super(exception);
    }
}
