package com.cotodel.hrms.web.util;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;

import com.cotodel.hrms.web.response.UserDetailsEntity;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;



public class JwtTokenValidator {
	
	private static final Logger logger = LoggerFactory.getLogger(JwtTokenValidator.class);


	/**
	 * Tries to parse specified String as a JWT token. If successful, returns User object with username, id and role prefilled (extracted from token).
	 * If unsuccessful (token is invalid or not containing all required user properties), simply returns null.
	 *
	 * @param token the JWT token to parse
	 * @return the User object extracted from specified token or null if a token is invalid.
	 */
	public static UserDetailsEntity parseToken(String token) throws AuthenticationException{
		UserDetailsEntity u = null;Claims body = null;
		try {
			try{
				body=Jwts.parser().setSigningKey(MessageConstant.SECRET).parseClaimsJws(token).getBody();
			}catch(io.jsonwebtoken.ExpiredJwtException ex){
				logger.error("JwtTokenValidator.parseToken ex :: "+ex);
				return null;
			}catch(Exception e){
				logger.error("JwtTokenValidator.parseToken e :: "+e);
				return null;
			}
			
			if(null != body){
				String userType=body.getSubject();
				if(null != userType && !userType.isEmpty()){
					if(userType.equals(MessageConstant.USER)){
						u = new UserDetailsEntity();	
						u.setMobile((String)body.get("mobile"));
						u.setUsername((String)body.get("user_name"));
						u.setRoleId((Integer)body.get("role"));
						u.setName((String)body.get("name"));
						u.setEmail((String)body.get("email"));
						u.setOrgName((String)body.get("org"));
						u.setOrgType((String)body.get("uotyp"));
						return u;
					}
				}
			}
		}catch(Exception e){
			logger.error("JwtTokenValidator.parseToken EXPIRE-----"+e);
		}
		return null;
	}


	public static String getUserMobile(HttpServletRequest request) {
		try {
			Claims body = Jwts.parser().setSigningKey(MessageConstant.SECRET).parseClaimsJws(request.getHeader(MessageConstant.HEADER_STRING).substring(7)).getBody();
			return	(String)body.get("mobile");
		} catch (JwtException e) {
			logger.error("getCurrentUserName error-----"+e);
		}catch(Exception e) {
			logger.error("getCurrentUserName error-----"+e);
		}
		return null;
	}		
}
