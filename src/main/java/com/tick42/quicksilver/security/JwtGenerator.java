package com.tick42.quicksilver.security;


import com.tick42.quicksilver.models.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Date;

@Component
public class JwtGenerator {

    @Value("${app.jwtSecret}")
    private String jwtSecret;

    @Value("${app.jwtExpirationInMs}")
    private int jwtExpirationInMs;
    private byte[] encodedBytes = Base64.getEncoder().encode("ourSecret".getBytes());

    public String generate(User user) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationInMs);

        Claims claims = Jwts.claims()
                .setSubject(user.getUsername());
        claims.put("userId", String.valueOf(user.getId()));
        claims.put("role", "ROLE_ADMIN");

        System.out.println(new String(encodedBytes));
        return Jwts.builder()
                .setClaims(claims)
                .signWith(io.jsonwebtoken.SignatureAlgorithm.HS512, new String(encodedBytes))
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .compact();
    }

}
