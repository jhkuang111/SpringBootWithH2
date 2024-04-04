package com.spring.server.exception;

public class AuthenticationFailException extends Exception {

    public AuthenticationFailException(String message) {
        super(message);
    }
}
