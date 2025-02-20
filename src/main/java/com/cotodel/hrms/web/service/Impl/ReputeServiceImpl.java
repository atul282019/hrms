package com.cotodel.hrms.web.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cotodel.hrms.web.function.common.CommonUtils;
import com.cotodel.hrms.web.properties.ApplicationConstantConfig;
import com.cotodel.hrms.web.service.ReputeService;
import com.cotodel.hrms.web.util.CommonUtility;
import com.cotodel.hrms.web.util.EncriptResponse;
import com.cotodel.hrms.web.util.MessageConstant;

@Service
public class ReputeServiceImpl implements ReputeService{

	@Autowired
	public ApplicationConstantConfig applicationConstantConfig; 
	
	@Override
	public String getReputeEmployee(String token, EncriptResponse jsonObject) {
		return CommonUtility.userRequest(
	            token,
	            MessageConstant.gson.toJson(jsonObject),
	            applicationConstantConfig.reputeServiceBaseUrl +CommonUtils.getReputeEmployeeList
	        );
	}

}
