package com.cotodel.hrms.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cotodel.hrms.web.properties.ApplicationConstantConfig;
import com.cotodel.hrms.web.response.UserRegistrationRequest;
import com.cotodel.hrms.web.service.LoginService;
import com.cotodel.hrms.web.service.Impl.TokenGenerationImpl;


@Controller
@CrossOrigin
public class SignupController  extends CotoDelBaseController{
	
	private static final Logger logger = LoggerFactory.getLogger(SignupController.class);

	@Autowired
	public ApplicationConstantConfig applicationConstantConfig;

	@Autowired
	LoginService loginservice;
	
	@Autowired
	TokenGenerationImpl tokengeneration;
	
	@PostMapping(value="/registerUser")
	public @ResponseBody String registerUser(HttpServletRequest request,UserRegistrationRequest userForm) {
		String profileRes=null;JSONObject profileJsonRes=null;
		
		profileRes = loginservice.registerUser(tokengeneration.getToken(),userForm);
		profileJsonRes= new JSONObject(profileRes);
		
		if(profileJsonRes.getBoolean("status")) { 
		//loginservice.sendEmailToEmployee(userForm);
		}
		return profileRes;
	}

}
