package me.albert.restapi.common.exception;

public class AuthException extends RuntimeException {

    public AuthException(String message) {
        super(message);
    }

}