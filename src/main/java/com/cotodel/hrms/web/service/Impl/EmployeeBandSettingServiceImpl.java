package com.cotodel.hrms.web.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cotodel.hrms.web.function.common.CommonUtils;
import com.cotodel.hrms.web.properties.ApplicationConstantConfig;
import com.cotodel.hrms.web.response.EmployeeBandSettingResponse;
import com.cotodel.hrms.web.service.EmployeeBandSettingService;
import com.cotodel.hrms.web.util.CommonUtility;
import com.cotodel.hrms.web.util.EncriptResponse;
import com.cotodel.hrms.web.util.MessageConstant;


@Service
public class EmployeeBandSettingServiceImpl implements EmployeeBandSettingService {

	@Autowired
	public ApplicationConstantConfig applicationConstantConfig;

	
	@Override
	public String getEmployeeBandTier(String token, EncriptResponse employeeBandSettingResponse) {
		// TODO Auto-generated method stub
		return CommonUtility.userRequest(token,MessageConstant.gson.toJson(employeeBandSettingResponse), applicationConstantConfig.employerServiceBaseUrl+CommonUtils.getEmployeeBandWithTier);
	}

	@Override
	public String saveEmployeeBandTier(String token, EncriptResponse employeeBandSettingResponse) {
		// TODO Auto-generated method stub
		return CommonUtility.userRequest(token,MessageConstant.gson.toJson(employeeBandSettingResponse), applicationConstantConfig.employerServiceBaseUrl+CommonUtils.addEmployeeBandTier);
	}


}
