package com.cotodel.hrms.web.jwt.security;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Since our API only 'speaks' REST we give a HTTP 401 if user cannot be authenticated. There is no
 * login page top redirect to.
 *
 * @author pascal alma
 */
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint, Serializable {


    private static final long serialVersionUID = 3798723588865329956L;

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        String message;
        if(authException.getCause() != null) {
            message = authException.getCause().getMessage();
        } else {
            message = authException.getMessage();
        }
        
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("message",message);
        data.put("status",HttpServletResponse.SC_UNAUTHORIZED);
        
        byte[] body = new ObjectMapper()
        .writeValueAsBytes(data);
        
        response.getOutputStream().write(body);
    }
}
