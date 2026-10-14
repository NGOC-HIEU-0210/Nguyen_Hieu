package com.hieu.config;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Service
public class JWTProvider {

    private final SecretKey key;
    private final long expirationTime;

    public JWTProvider(JWTConstant jwtConstant) {

        this.key = Keys.hmacShaKeyFor(
                jwtConstant.getSecretKey()
                        .getBytes(StandardCharsets.UTF_8)
        );

        this.expirationTime = jwtConstant.getExpirationTime();
    }

    public String generateToken(
            Authentication auth,
            Long userId
    ) {

        Collection<? extends GrantedAuthority> authorities =
                auth.getAuthorities();

        String roles = populateAuthorities(authorities);

        return Jwts.builder()
                .issuedAt(new Date())
                .expiration(
                        new Date(
                                System.currentTimeMillis()
                                        + expirationTime
                        )
                )
                .claim("email", auth.getName())
                .claim("authorities", roles)
                .claim("userid", userId)
                .signWith(key)
                .compact();
    }

    private String populateAuthorities(
            Collection<? extends GrantedAuthority> authorities
    ) {

        Set<String> auths = new HashSet<>();

        for (GrantedAuthority authority : authorities) {
            auths.add(authority.getAuthority());
        }

        return String.join(",", auths);
    }
}