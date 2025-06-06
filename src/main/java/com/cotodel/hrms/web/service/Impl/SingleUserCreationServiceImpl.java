package com.cotodel.hrms.web.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cotodel.hrms.web.function.common.CommonUtils;
import com.cotodel.hrms.web.properties.ApplicationConstantConfig;
import com.cotodel.hrms.web.response.UserRegistrationRequest;
import com.cotodel.hrms.web.response.UserRequest;
import com.cotodel.hrms.web.response.UserWaitList;
import com.cotodel.hrms.web.service.SingleUserCreationService;
import com.cotodel.hrms.web.util.CommonUtility;
import com.cotodel.hrms.web.util.EncriptResponse;
import com.cotodel.hrms.web.util.MessageConstant;

@Service
public class SingleUserCreationServiceImpl implements SingleUserCreationService{

	@Autowired
	public ApplicationConstantConfig applicationConstantConfig;

	@Override
	public String singleUserCreationEncript(String token, EncriptResponse userForm) {
		return CommonUtility.userRequest(token,MessageConstant.gson.toJson(userForm), applicationConstantConfig.userServiceBaseUrl+CommonUtils.registerUserUrl);
	}
	
	@Override
	public String singleUserCreation(String token, EncriptResponse userForm) {
		return CommonUtility.userRequest(token,MessageConstant.gson.toJson(userForm), applicationConstantConfig.userServiceBaseUrl+CommonUtils.registerUserUrl);
	}


	@Override
	public String getUser(String token, EncriptResponse userForm) {
		return CommonUtility.userRequest(token,MessageConstant.gson.toJson(userForm), applicationConstantConfig.userServiceBaseUrl+CommonUtils.getSingleUser);
	}


	@Override
	public String userWaitList(String token, EncriptResponse userWaitList) {
		return CommonUtility.userRequest(token,MessageConstant.gson.toJson(userWaitList), applicationConstantConfig.userServiceBaseUrl+CommonUtils.saveWaitlist);
	}
	@Override
	public String getuserWaitList(String token, EncriptResponse userWaitList) {
		return CommonUtility.userRequest(token,MessageConstant.gson.toJson(userWaitList), applicationConstantConfig.userServiceBaseUrl+CommonUtils.getsaveWaitlist);
	}

	@Override
	public String reputeRequestSave(String token, EncriptResponse jsonObject) {
		return CommonUtility.userRequest(token,MessageConstant.gson.toJson(jsonObject), applicationConstantConfig.userServiceBaseUrl+CommonUtils.saveReputeIdTokenData);
	}
	@Override
	public String updateuserWaitList(String token, EncriptResponse userWaitList) {
		return CommonUtility.userRequest(token,MessageConstant.gson.toJson(userWaitList), applicationConstantConfig.userServiceBaseUrl+CommonUtils.updateWaitlist);
	}

	@Override
	public String saveReputeUserDetailEncript(String token, EncriptResponse jsonObject) {
		return CommonUtility.userRequest(token,MessageConstant.gson.toJson(jsonObject), applicationConstantConfig.userServiceBaseUrl+CommonUtils.registerReputeUserDetail);

	}

	@Override
	public String userDetailByMobileNo(String token, EncriptResponse jsonObjectIdToken2) {
	return CommonUtility.userRequest(token,MessageConstant.gson.toJson(jsonObjectIdToken2), applicationConstantConfig.userServiceBaseUrl+CommonUtils.getUserDetailByMobileNumber);

	}

}
