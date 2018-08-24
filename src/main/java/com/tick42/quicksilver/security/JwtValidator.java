package com.tick42.quicksilver.security;

import com.tick42.quicksilver.models.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Base64;

@Component
public class JwtValidator {
    @Value("${app.jwtSecret}")
    private String jwtSecret;
    private byte[] encodedBytes = Base64.getEncoder().encode("ourSecret".getBytes());

    public User validate(String token) {

        User jwtUser = null;
        try {
            Claims body = Jwts.parser()
                    .setSigningKey(new String(encodedBytes))
                    .parseClaimsJws(token)
                    .getBody();

            jwtUser = new User();

            jwtUser.setUsername(body.getSubject());
            System.out.println(body.getSubject());
            jwtUser.setId(Integer.parseInt((String) body.get("userId")));
            System.out.println(body.get("userId"));
            jwtUser.setRole((String) body.get("role"));
        }
        catch (Exception e) {
            System.out.println(e);
        }

        return jwtUser;
    }
}
