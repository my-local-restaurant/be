package me.albert.restapi.common.exception;

/**
 * 이메일 또는 비밀번호가 일치하지 않을 경우 발생하는 예외
 */
public class IllegalEmailAddressOrPasswordException extends AuthException {

    private static final String MESSAGE = "이메일 또는 비밀번호가 일치하지 않습니다.";

    public IllegalEmailAddressOrPasswordException() {
        super(MESSAGE);
    }

}
