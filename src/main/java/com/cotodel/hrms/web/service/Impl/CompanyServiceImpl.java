package com.cotodel.hrms.web.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cotodel.hrms.web.function.common.CommonUtils;
import com.cotodel.hrms.web.properties.ApplicationConstantConfig;
import com.cotodel.hrms.web.response.EmployeeProfileRequest;
import com.cotodel.hrms.web.service.CompanyService;
import com.cotodel.hrms.web.util.CommonUtility;
import com.cotodel.hrms.web.util.MessageConstant;

@Service
public class CompanyServiceImpl implements CompanyService{

	@Autowired
	public ApplicationConstantConfig applicationConstantConfig;
	
	@Override
	public String saveCompany(String token, EmployeeProfileRequest employeeProfileRequest) {
		return CommonUtility.userRequest(token,MessageConstant.gson.toJson(employeeProfileRequest), applicationConstantConfig.employerServiceBaseUrl+CommonUtils.registerCompany);
	}

	@Override
	public String getCompanyProfileStatus(String token, EmployeeProfileRequest employeeProfileRequest) {
		// TODO Auto-generated method stub
		return CommonUtility.userRequest(token,MessageConstant.gson.toJson(employeeProfileRequest), applicationConstantConfig.employerServiceBaseUrl+CommonUtils.companyProfileStatus);
	}

	@Override
	public String getorgsubType(String token, EmployeeProfileRequest employeeProfileRequest) {
		return CommonUtility.userRequest(token,MessageConstant.gson.toJson(employeeProfileRequest), applicationConstantConfig.masterServiceBaseUrl+CommonUtils.getorgsubType);
		
	}

	@Override
	public String getpayrollDetails(String token, EmployeeProfileRequest employeeProfileRequest) {
		// TODO Auto-generated method stub
		return CommonUtility.userRequest(token,MessageConstant.gson.toJson(employeeProfileRequest), applicationConstantConfig.employerServiceBaseUrl+CommonUtils.getpayrollDetails);
	}

	@Override
	public String getGSTDetailsByGSTNumber(String token, EmployeeProfileRequest employeeProfileRequest) {
		return CommonUtility.userRequest(token,MessageConstant.gson.toJson(employeeProfileRequest), applicationConstantConfig.employerServiceBaseUrl+CommonUtils.gstDetailService);
	}
	
	

}
