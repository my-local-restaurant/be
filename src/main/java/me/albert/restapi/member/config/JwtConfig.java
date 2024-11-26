package me.albert.restapi.member.config;

import me.albert.restapi.member.util.JwtTokenManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtConfig {

    @Bean
    public JwtTokenManager jwtTokenManager(
            @Value("${jwt.secret}") String secret,
            @Value("${jwt.accessExpiredTime}") long accessExpiredTime,
            @Value("${jwt.refreshExpiredTime}") long refreshExpiredTime,
            @Value("${jwt.issuer}") String issuer
    ) {
        return new JwtTokenManager(secret, accessExpiredTime, refreshExpiredTime, issuer);
    }
}
