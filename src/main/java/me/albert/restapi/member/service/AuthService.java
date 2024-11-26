package me.albert.restapi.member.service;

import me.albert.restapi.member.service.dto.TokensResponse;
import me.albert.restapi.common.exception.IllegalEmailAddressOrPasswordException;

public interface AuthService {

    /**
     * 로그인 유지 시 accessToken과 refreshToken을 반환합니다. 로그인 유지를 하지 않을 경우 accessToken만 반환합니다.
     *
     * @param email        이메일
     * @param password     비밀번호
     * @param isRememberMe 로그인 유지 여부
     * @return 토큰 응답
     * @throws IllegalEmailAddressOrPasswordException 이메일 또는 비밀번호가 일치하지 않을 경우
     */
    TokensResponse login(String email, String password, boolean isRememberMe);

}
