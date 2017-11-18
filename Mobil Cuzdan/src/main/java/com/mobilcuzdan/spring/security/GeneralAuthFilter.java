package com.mobilcuzdan.spring.security;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.web.filter.authc.PassThruAuthenticationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by YunusS on 3/28/2016.
 */
public class GeneralAuthFilter extends PassThruAuthenticationFilter {

    private final String HEADER_VAL="x-mobil-token";
    private final String AJAX_HEADER_VAL="sm-request-type";


    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        if(super.isAccessAllowed(request, response, mappedValue)){
            return true; // session is alive
        } else { // try to recreate session via header if mobile
            HttpServletRequest req = (HttpServletRequest) request;
            String headerVal = req.getHeader(HEADER_VAL);
            if(headerVal == null){ // web request
                return false;
            } else { // mobile request without a session
                try{
                    SecurityUtils.getSubject().login(new HeaderAuthenticationToken(headerVal));
                    return true;
                } catch (Exception e){
                    e.printStackTrace();
                    return false;
                }
            }
        }
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        String headerVal = req.getHeader(HEADER_VAL);
        String ajaxHeaderVal = req.getHeader(AJAX_HEADER_VAL);

        if((headerVal != null && headerVal.length() > 0) || (ajaxHeaderVal != null && ajaxHeaderVal.equals("ajax")) ){ //  mobile request or ajax request- return 403
            resp.setStatus(403);
            return false; //stop filter, let the response return
        } else { // web request - redirect to login page
            return super.onAccessDenied(request, response);
        }
    }
}