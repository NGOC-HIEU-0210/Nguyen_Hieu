package com.hieu.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JWTConstant {

    private final String SECRET_KEY;
    private final long expirationTime;

    public JWTConstant(
            @Value("${jwt.secret}") String secretKey,
            @Value("${jwt.expiration}") long expirationTime
    ) {
        this.SECRET_KEY = secretKey;
        this.expirationTime = expirationTime;
    }

    public String getSecretKey() {
        return SECRET_KEY;
    }

    public long getExpirationTime() {
        return expirationTime;
    }

    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
}