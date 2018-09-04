package com.tick42.quicksilver.security;

import com.tick42.quicksilver.models.User;
import com.tick42.quicksilver.security.Exceptions.JwtTokenIsMissingException;
import io.jsonwebtoken.Claims;
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
            jwtUser.setRole((String)body.get("role"));
        } catch (Exception e) {
            System.out.println(e);
        }

        return jwtUser;
    }

    public int getUserIdFromToken(HttpServletRequest request) {
        int id = 0;
        String header = request.getHeader("Authorization");
        if (header == null || !header.startsWith("Token ")) {
            throw new JwtTokenIsMissingException("JWT Token is missing");
        }
        String token = header.substring(6);
        try {
            Claims body = Jwts.parser()
                    .setSigningKey(new String(encodedBytes))
                    .parseClaimsJws(token)
                    .getBody();

            id = Integer.parseInt((String) body.get("userId"));
        } catch (Exception e) {
            System.out.println(e);
        }
        return id;
    }
}
