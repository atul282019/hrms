package com.cotodel.hrms.web.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReputeTokenRequest {

	private Long id ;	
    private String mobile ;    
	private String accessToken;
	private String refreshToken;
	private String scope;
	private String idToken;
	private String tokenType;
	private String expiresIn;
	private String response;
	
}
