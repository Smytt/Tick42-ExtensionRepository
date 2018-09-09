package com.tick42.quicksilver.security;

import com.tick42.quicksilver.models.User;
import com.tick42.quicksilver.security.Exceptions.JwtExpiredTokenException;
import com.tick42.quicksilver.security.Exceptions.JwtTokenIsIncorrectException;
import com.tick42.quicksilver.security.Exceptions.JwtTokenIsMissingException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Base64;

@Component
public class JwtValidator {
    private String secret = "ourSecretTeamQuickSilver";
    private byte[] encodedBytes = Base64.getEncoder().encode(secret.getBytes());

    public User validate(String token) {
        User jwtUser = null;
        try {
            Claims body = Jwts.parser()
                    .setSigningKey(new String(encodedBytes))
                    .parseClaimsJws(token)
                    .getBody();

            jwtUser = new User();

            jwtUser.setUsername(body.getSubject());
            jwtUser.setId(Integer.parseInt((String) body.get("userId")));
            jwtUser.setRole((String) body.get("role"));
        } catch (ExpiredJwtException e) {
            throw new JwtExpiredTokenException("Jwt token has expired.");
        } catch (Exception e) {
            System.out.println(e);
        }

        return jwtUser;
    }

    public int getUserIdFromToken(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (header == null || !header.startsWith("Token ")) {
            throw new JwtTokenIsMissingException("JWT Token is missing");
        }
        String token = header.substring(6);
        User user = validate(token);
        if(user == null) {
            throw new JwtTokenIsIncorrectException("JWT Token is incorrect");
        }
        return user.getId();
    }
}
