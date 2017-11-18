package com.mobilcuzdan.spring.security;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * Created by YunusS on 3/28/2016.
 */
public class HeaderAuthenticationToken implements AuthenticationToken {

    private String token;

    public HeaderAuthenticationToken(String token) {
        this.token = token;
    }

    @Override
    public Object getPrincipal() {
        return null;
    }

    @Override
    public Object getCredentials() {
        return token;
    }
}