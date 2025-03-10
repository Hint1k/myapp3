package com.demo.fds.exception.custom;

public class InvalidPageableException extends RuntimeException {

    public InvalidPageableException(String message) {
        super(message);
    }
}