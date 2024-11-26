package me.albert.restapi.common.exception;

/**
 * 인증 예외
 */
public class AuthException extends RuntimeException {

    public AuthException(String message) {
        super(message);
    }

}
