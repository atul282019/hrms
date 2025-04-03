package com.cotodel.hrms.web.jwt.util;


import java.util.Date;

import com.cotodel.hrms.web.response.UserDetailsEntity;
import com.cotodel.hrms.web.util.MessageConstant;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;


public class JwtTokenGenerator {

    /**
     * Generates a JWT token containing username as subject, and userId and role as additional claims.
     *  These properties are taken from the specified
     * User object. Tokens validity is infinite.
     *
     * @param u the user for which the token will be generated
     * @return the JWT token
     */
    public static String generateToken(String email,String mobile,String username,Integer user_role,Integer orgid, String secret) {
        Claims claims = Jwts.claims().setSubject(MessageConstant.USER);
        claims.put("mobile", mobile);
        claims.put("user_name", username);
        claims.put("email", email);
        claims.put("user_role", user_role);
        claims.put("orgid", orgid);
        
        return Jwts.builder()
                .setClaims(claims).setExpiration(new Date(System.currentTimeMillis()+MessageConstant.EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }
}
