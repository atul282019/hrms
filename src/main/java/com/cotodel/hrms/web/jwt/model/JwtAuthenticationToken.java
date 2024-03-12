package com.cotodel.hrms.web.jwt.model;


import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;


/**
 * Holder for JWT token from the request.
 * <p/>
 * Other fields aren't used but necessary to comply to the contracts of AbstractUserDetailsAuthenticationProvider
 *
 * @author pascal alma
 */
public class JwtAuthenticationToken extends UsernamePasswordAuthenticationToken {


    /**
	 * 
	 */
	private static final long serialVersionUID = 2783632915686218554L;
	private String token;

    public JwtAuthenticationToken(String token) {
        super(null, null);
        this.token = token;
    }

    public String getToken() {
        return token;
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