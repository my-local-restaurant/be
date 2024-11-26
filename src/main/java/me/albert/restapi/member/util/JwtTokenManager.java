package me.albert.restapi.member.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.util.Date;
import javax.crypto.SecretKey;
import me.albert.restapi.common.exception.IllegalTokenException;

public class JwtTokenManager {

    private final SecretKey secretKey;
    private final long accessExpiredTime;
    private final long refreshExpiredTime;
    private final String issuer;

    public JwtTokenManager(
            String secret,
            long accessExpiredTime,
            long refreshExpiredTime,
            String issuer
    ) {
        this.secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
        this.accessExpiredTime = accessExpiredTime;
        this.issuer = issuer;
        this.refreshExpiredTime = refreshExpiredTime;
    }

    public String generateAccessToken(String email) {
        var issuedAt = new Date();
        var expiredAt = new Date(issuedAt.getTime() + accessExpiredTime);
        return Jwts.builder()
                .issuer(issuer)
                .subject(email)
                .issuedAt(new Date())
                .expiration(expiredAt)
                .signWith(secretKey)
                .compact();
    }

    public String generateRefreshToken(String email) {
        var issuedAt = new Date();
        var expiredAt = new Date(issuedAt.getTime() + refreshExpiredTime);
        return Jwts.builder()
                .issuer(issuer)
                .subject(email)
                .issuedAt(new Date())
                .expiration(expiredAt)
                .signWith(secretKey)
                .compact();
    }

    public String getEmailFromToken(String token) {
        try {
            return Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload()
                    .getSubject();
        } catch (Exception e) {
            throw new IllegalTokenException();
        }
    }
}
