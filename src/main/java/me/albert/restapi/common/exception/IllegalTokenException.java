package me.albert.restapi.common.exception;

public class IllegalTokenException extends AuthException {

    private static final String MESSAGE = "토큰이 유효하지 않습니다.";

    public IllegalTokenException() {
        super(MESSAGE);
    }
}
