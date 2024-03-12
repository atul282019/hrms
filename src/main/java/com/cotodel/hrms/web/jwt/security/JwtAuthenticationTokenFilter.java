package com.cotodel.hrms.web.jwt.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AndRequestMatcher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.NegatedRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import com.cotodel.hrms.web.jwt.model.JwtAuthenticationToken;
import com.cotodel.hrms.web.jwt.security.exception.JwtTokenMalformedException;
import com.cotodel.hrms.web.jwt.security.exception.JwtTokenMissingException;
import com.cotodel.hrms.web.util.MessageConstant;





public class JwtAuthenticationTokenFilter extends AbstractAuthenticationProcessingFilter {


	static RequestMatcher customRequestMatcher = new AndRequestMatcher(
			new AntPathRequestMatcher("/api"), 
			new NegatedRequestMatcher(
					new AntPathRequestMatcher("/v1/users", "POST")
					),
			new NegatedRequestMatcher(
					new AntPathRequestMatcher("/v1/users/signin", "POST")
					)
			);

	public JwtAuthenticationTokenFilter() {
		super(customRequestMatcher);
	}



	/**
	 * Attempt to authenticate request - basically just pass over to another method to authenticate request headers
	 */
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
		String header = request.getHeader(MessageConstant.HEADER_STRING);
		System.out.println(header);
		if (header == null || !header.startsWith("Bearer ")) {
			try {
				new JwtAuthenticationEntryPoint ().commence(request, response, new JwtTokenMissingException("No JWT token found in request headers"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//throw new JwtTokenMissingException("No JWT token found in request headers");
		}
		else{
			try{
				String authToken = header.substring(7);

				JwtAuthenticationToken authRequest = new JwtAuthenticationToken(authToken);
				org.springframework.security.core.Authentication auth	=	null;
				try{
					auth 	=	getAuthenticationManager().authenticate(authRequest);
				}
				catch(io.jsonwebtoken.ExpiredJwtException ex){
					System.out.println("JwtAuthenticationTokenFilter . attemptAuthentication == 1 == "+ex.getMessage());
					ex.printStackTrace();
					new JwtAuthenticationEntryPoint ().commence(request, response, new JwtTokenMissingException(ex.getMessage()));
				}
				catch(AuthenticationException e){
					System.out.println("JwtAuthenticationTokenFilter . attemptAuthentication == 2 == "+e.getMessage());
					e.printStackTrace();
					new JwtAuthenticationEntryPoint ().commence(request, response, new JwtTokenMissingException("Jwt Token Malformed Exception"));
				}
				catch (Exception e) {
					System.out.println("JwtAuthenticationTokenFilter . attemptAuthentication Exception == 3 == "+e.getMessage());
					e.printStackTrace();
					new JwtAuthenticationEntryPoint ().commence(request, response, new JwtTokenMissingException(e.getMessage()));
				}
				return auth;
			}
			catch(Exception e){
				e.printStackTrace();
				try {
					new JwtAuthenticationEntryPoint ().commence(request, response, new JwtTokenMalformedException(e.getMessage()));
				} catch (IOException ex) {
					// TODO Auto-generated catch block
					ex.printStackTrace();
				}
			}

		}
		return null;
	}

	/**
	 * Make sure the rest of the filterchain is satisfied
	 *
	 * @param request
	 * @param response
	 * @param chain
	 * @param authResult
	 * @throws IOException
	 * @throws ServletException
	 */
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult)
			throws IOException, ServletException {
		super.successfulAuthentication(request, response, chain, authResult);

		// As this authentication is in HTTP header, after success we need to continue the request normally
		// and return the response as if the resource was not secured at all
		chain.doFilter(request, response);
	}
}