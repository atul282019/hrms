package com.cotodel.hrms.web.service.Impl;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.cotodel.hrms.web.service.LoginService;

@Service
public class TokenGenerationImpl {
	
	
	private static final Logger logger = LoggerFactory.getLogger(TokenGenerationImpl.class);

	@Autowired
	LoginService loginservice;

	public String getToken() {
			
			logger.info("opening getInTouch");
			String responseToken="";
			String authToken = "";
	    	try {	    		
	    		String companyId = "HRMS00001";
	    		 responseToken = loginservice.getToken(companyId);
					//String authToken = "";
					if (!ObjectUtils.isEmpty(responseToken)) {
						JSONObject getTokenRes = new JSONObject(responseToken);
						authToken = getTokenRes.getString("access_token");
					}
	    	 
	    	}catch (Exception e) {
				
	    		e.printStackTrace();
			}
			return authToken;
		}


}
