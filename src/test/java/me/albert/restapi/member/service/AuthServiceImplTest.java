package me.albert.restapi.member.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

import me.albert.restapi.common.exception.IllegalEmailAddressOrPasswordException;
import me.albert.restapi.member.repository.AuthRepository;
import me.albert.restapi.member.service.dto.TokensResponse;
import me.albert.restapi.member.util.JwtTokenManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AuthServiceImplTest {

    @Mock
    private AuthRepository authRepository;

    @Mock
    private JwtTokenManager jwtTokenManager;

    @InjectMocks
    private AuthServiceImpl authService;

    @DisplayName("로그인 요청시 유효한 이메일과 비밀번호 및 기억하기가 true인 경우 토큰 응답에 리프레시 토큰이 포함된다.")
    @Test
    void login_with_valid_credentials_and_remember_me_returns_tokens_response_with_refresh_token() {
        // given
        String email = "test@example.com";
        String password = "password";
        String accessToken = "access-token";
        String refreshToken = "refresh-token";

        when(authRepository.existsByUsernameAndPassword(email, password)).thenReturn(true);
        when(jwtTokenManager.generateAccessToken(email)).thenReturn(accessToken);
        when(jwtTokenManager.generateRefreshToken(email)).thenReturn(refreshToken);

        // when
        TokensResponse response = authService.login(email, password, true);

        // then
        assertThat(response.accessToken()).isEqualTo(accessToken);
        assertThat(response.refreshToken()).isEqualTo(refreshToken);
    }

    @DisplayName("로그인 요청시 유효한 이메일과 비밀번호 및 기억하기가 false인 경우 토큰 응답에 리프레시 토큰이 포함되지 않는다.")
    @Test
    void login_with_valid_credentials_and_not_remember_me_returns_tokens_response_without_refresh_token() {
        // given
        String email = "test@example.com";
        String password = "password";
        String accessToken = "access-token";

        when(authRepository.existsByUsernameAndPassword(email, password)).thenReturn(true);
        when(jwtTokenManager.generateAccessToken(email)).thenReturn(accessToken);

        // when
        TokensResponse response = authService.login(email, password, false);

        // then
        assertThat(response.accessToken()).isEqualTo(accessToken);
        assertThat(response.refreshToken()).isNull();
    }

    @DisplayName("로그인 요청시 유효하지 않은 이메일과 비밀번호인 경우 IllegalEmailAddressOrPasswordException을 던진다.")
    @Test
    void login_with_invalid_credentials_throws_illegal_email_address_or_password_exception() {
        // given
        String email = "test@exmaple.com";
        String password = "password";

        when(authRepository.existsByUsernameAndPassword(email, password)).thenReturn(false);

        // when & then
        assertThatThrownBy(() -> authService.login(email, password, false))
                .isInstanceOf(IllegalEmailAddressOrPasswordException.class);
    }
}
