package com.cotodel.hrms.web.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cotodel.hrms.web.function.common.CommonUtils;
import com.cotodel.hrms.web.properties.ApplicationConstantConfig;
import com.cotodel.hrms.web.response.UserRegistrationRequest;
import com.cotodel.hrms.web.service.MasterService;
import com.cotodel.hrms.web.util.CommonUtility;

@Service
public class MasterServiceImpl implements MasterService{
	
	@Autowired
	public ApplicationConstantConfig applicationConstantConfig;

	@Override
	public String getStateMaster(String token,UserRegistrationRequest userForm) {
		return CommonUtility.userRequest(token,null, applicationConstantConfig.userServiceBaseUrl +CommonUtils.stateMaster);
	}

	@Override
	public String getOrgMaster(String token,UserRegistrationRequest userForm) {
		return CommonUtility.userRequest(token,null, applicationConstantConfig.userServiceBaseUrl +CommonUtils.orgMaster);
	}

}
