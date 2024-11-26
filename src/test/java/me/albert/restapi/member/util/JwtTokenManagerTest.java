package me.albert.restapi.member.util;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import me.albert.restapi.common.exception.IllegalTokenException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class JwtTokenManagerTest {

    String security;

    @BeforeEach
    void setUp() {
        byte[] keyBytes = new byte[32];
        new java.security.SecureRandom().nextBytes(keyBytes);
        security = Encoders.BASE64.encode(keyBytes);
    }

    @DisplayName("Secret Key가 같으면 같은 Key 객체를 생성한다.")
    @Test
    void if_secret_key_is_same_then_return_same_key_object() {
        // given

        var target = Keys.hmacShaKeyFor(Decoders.BASE64.decode(security));

        // when
        var result = Keys.hmacShaKeyFor(Decoders.BASE64.decode(security));

        // then
        assertThat(result).isEqualTo(target);
    }

    @DisplayName("Access Token을 생성하면 올바른 토큰을 반환한다.")
    @Test
    void generateAccessToken_returns_valid_token() {
        // given
        String email = "test@example.com";
        JwtTokenManager jwtTokenManager = new JwtTokenManager(security, 3600000, 7200000, "issuer");

        // when
        String token = jwtTokenManager.generateAccessToken(email);

        // then
        assertThat(token).isNotNull();
        assertThat(jwtTokenManager.getEmailFromToken(token)).isEqualTo(email);
    }

    @DisplayName("Refresh Token을 생성하면 올바른 토큰을 반환한다.")
    @Test
    void generateRefreshToken_returns_valid_token() {
        // given
        String email = "test@example.com";
        JwtTokenManager jwtTokenManager = new JwtTokenManager(security, 3600000, 7200000, "issuer");

        // when
        String token = jwtTokenManager.generateRefreshToken(email);

        // then
        assertThat(token).isNotNull();
        assertThat(jwtTokenManager.getEmailFromToken(token)).isEqualTo(email);
    }

    @DisplayName("유효하지 않은 토큰을 디코딩하면 IllegalTokenException을 던진다.")
    @Test
    void getEmailFromToken_throws_IllegalTokenException_for_invalid_token() {
        // given
        String invalidToken = "invalid-token";
        JwtTokenManager jwtTokenManager = new JwtTokenManager(security, 3600000, 7200000, "issuer");

        // when & then
        assertThatThrownBy(() -> jwtTokenManager.getEmailFromToken(invalidToken))
                .isInstanceOf(IllegalTokenException.class);
    }

    @DisplayName("만료된 토큰을 디코딩하면 IllegalTokenException을 던진다.")
    @Test
    void getEmailFromToken_throws_IllegalTokenException_for_expired_token() {
        // given
        String email = "test@example.com";
        JwtTokenManager jwtTokenManager = new JwtTokenManager(security, -1, -1, "issuer");
        String expiredToken = jwtTokenManager.generateAccessToken(email);

        // when & then
        assertThatThrownBy(() -> jwtTokenManager.getEmailFromToken(expiredToken))
                .isInstanceOf(IllegalTokenException.class);
    }
}
