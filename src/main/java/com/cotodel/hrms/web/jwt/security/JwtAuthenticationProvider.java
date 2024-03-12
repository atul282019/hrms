package com.cotodel.hrms.web.jwt.security;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.cotodel.hrms.web.response.UserDetailsEntity;
import com.cotodel.hrms.web.jwt.security.exception.JwtTokenMalformedException;
import com.cotodel.hrms.web.jwt.model.AuthenticatedUser;
import com.cotodel.hrms.web.jwt.model.JwtAuthenticationToken;
import com.cotodel.hrms.web.jwt.util.JwtTokenValidator;


@Component
public class JwtAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {

    @Override
    public boolean supports(Class<?> authentication) {
        return (JwtAuthenticationToken.class.isAssignableFrom(authentication));
    }

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
    }

    @Override
    protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        JwtAuthenticationToken jwtAuthenticationToken = (JwtAuthenticationToken) authentication;
        String token = jwtAuthenticationToken.getToken();
        String message	=	"";
        Object p = null;
        try{
        	p	=	JwtTokenValidator.parseToken(token);
        	 if (p == null) {
             	System.out.println("inside if p == null == "+message);
                 throw new JwtTokenMalformedException(message);
             }
             UserDetailsEntity parsedUser = null;
             if(p instanceof UserDetailsEntity){
             	parsedUser	=	(UserDetailsEntity) p;
//             	 List<GrantedAuthority> authorityList = AuthorityUtils.commaSeparatedStringToAuthorityList(parsedUser.getRole());
//                  if(null != authorityList)
//                  	return new AuthenticatedUser(parsedUser.getUserId(), parsedUser.getUserLoginid(), token, authorityList);
//                  else
                  	return new AuthenticatedUser(parsedUser.getUserid(),parsedUser.getUsername(), token, null);
             }             
        }
        catch(AuthenticationException e){
        	System.out.println("JwtAuthenticationProvider . retrieveUser == "+e.getMessage());
        	message	=	e.getMessage();
        	throw e;
        }
		return null;
    }
}
