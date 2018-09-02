package com.tick42.quicksilver.security.models;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public class JwtToken extends UsernamePasswordAuthenticationToken {

    private String token;

    public JwtToken(String token) {
        super(null, null);
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return null;
    }
}
