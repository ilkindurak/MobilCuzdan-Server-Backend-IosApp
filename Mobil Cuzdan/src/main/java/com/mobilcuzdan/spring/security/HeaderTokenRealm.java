package com.mobilcuzdan.spring.security;

import com.mobilcuzdan.spring.mvc.models.User;
import com.mobilcuzdan.spring.services.UsersService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by YunusS on 3/28/2016.
 */
public class HeaderTokenRealm extends AuthorizingRealm {

    @Autowired
    UsersService usersService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        HeaderAuthenticationToken hedearToken = (HeaderAuthenticationToken) token;
        String headerVal = (String) token.getCredentials();
        User user = usersService.validateTokenAndGetUser(headerVal);
        if(user != null){
            return new SimpleAuthenticationInfo(user.getId(), headerVal, getName());
        } else {
            throw new AuthenticationException();
        }

    }

    @Override
    public boolean supports(AuthenticationToken token) {
        return (token instanceof HeaderAuthenticationToken);
    }
}