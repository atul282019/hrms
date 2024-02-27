package com.cotodel.hrms.web.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cotodel.hrms.web.function.common.CommonUtils;
import com.cotodel.hrms.web.properties.ApplicationConstantConfig;
import com.cotodel.hrms.web.response.EmployeeDetailRequest;
import com.cotodel.hrms.web.service.EmployeeDetailService;
import com.cotodel.hrms.web.util.CommonUtility;
import com.cotodel.hrms.web.util.MessageConstant;

@Service
public class EmployeeDetailServiceImpl implements EmployeeDetailService {

	@Autowired
	public ApplicationConstantConfig applicationConstantConfig;

	@Override
	public String saveEmployeeDetail(String token, EmployeeDetailRequest employeeDetailRequest) {
		return CommonUtility.userRequest(token,MessageConstant.gson.toJson(employeeDetailRequest), applicationConstantConfig.employerServiceBaseUrl+CommonUtils.getPayrollList);
	}
	
}
