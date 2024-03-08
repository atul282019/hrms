package com.cotodel.hrms.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cotodel.hrms.web.properties.ApplicationConstantConfig;
import com.cotodel.hrms.web.response.UserRegistrationRequest;
import com.cotodel.hrms.web.service.SingleUserCreationService;
import com.cotodel.hrms.web.service.Impl.TokenGenerationImpl;

@Controller
@CrossOrigin
public class SingleUserCreationController extends CotoDelBaseController{
	
	private static final Logger logger = LoggerFactory.getLogger(SingleUserCreationController.class);
	
	@Autowired
	public ApplicationConstantConfig applicationConstantConfig;
	@Autowired
	SingleUserCreationService singleUserService;
	
	@Autowired
	TokenGenerationImpl tokengeneration;

	@PostMapping(value="/singleUserCreation")
	public @ResponseBody String registerUser(HttpServletRequest request,UserRegistrationRequest userForm) {
		String profileRes=null;
		profileRes = singleUserService.singleUserCreation(tokengeneration.getToken(),userForm);
		return profileRes;
	}
	

	@PostMapping(value="/getUserList")
	public @ResponseBody String getUser(HttpServletRequest request,UserRegistrationRequest userForm) {
		String profileRes=null;
		profileRes = singleUserService.getUser(tokengeneration.getToken(),userForm);
		return profileRes;
	}
}
