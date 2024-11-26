package me.albert.restapi.member.service;

import lombok.RequiredArgsConstructor;
import me.albert.restapi.common.exception.IllegalEmailAddressOrPasswordException;
import me.albert.restapi.member.repository.AuthRepository;
import me.albert.restapi.member.service.dto.TokensResponse;
import me.albert.restapi.member.util.JwtTokenManager;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {

    private final AuthRepository authRepository;
    private final JwtTokenManager jwtTokenManager;

    @Override
    public TokensResponse login(String email, String password, boolean isRememberMe) {
        if (!authRepository.existsByUsernameAndPassword(email, password)) {
            throw new IllegalEmailAddressOrPasswordException();
        }
        var accessToken = jwtTokenManager.generateAccessToken(email);
        return isRememberMe ? new TokensResponse(accessToken, jwtTokenManager.generateRefreshToken(email))
                : new TokensResponse(accessToken, null);
    }
}
