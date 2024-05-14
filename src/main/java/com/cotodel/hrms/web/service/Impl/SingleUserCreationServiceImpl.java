package com.cotodel.hrms.web.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cotodel.hrms.web.function.common.CommonUtils;
import com.cotodel.hrms.web.properties.ApplicationConstantConfig;
import com.cotodel.hrms.web.response.UserRegistrationRequest;
import com.cotodel.hrms.web.response.UserWaitList;
import com.cotodel.hrms.web.service.SingleUserCreationService;
import com.cotodel.hrms.web.util.CommonUtility;
import com.cotodel.hrms.web.util.MessageConstant;

@Service
public class SingleUserCreationServiceImpl implements SingleUserCreationService{

	@Autowired
	public ApplicationConstantConfig applicationConstantConfig;

	
	@Override
	public String singleUserCreation(String token, UserRegistrationRequest userForm) {
		return CommonUtility.userRequest(token,MessageConstant.gson.toJson(userForm), applicationConstantConfig.userServiceBaseUrl+CommonUtils.registerUserUrl);
	}


	@Override
	public String getUser(String token, UserRegistrationRequest userForm) {
		return CommonUtility.userRequest(token,MessageConstant.gson.toJson(userForm), applicationConstantConfig.userServiceBaseUrl+CommonUtils.getSingleUser);
	}


	@Override
	public String userWaitList(String token, UserWaitList userWaitList) {
		return CommonUtility.userRequest(token,MessageConstant.gson.toJson(userWaitList), applicationConstantConfig.userServiceBaseUrl+CommonUtils.saveWaitlist);
	}

}
