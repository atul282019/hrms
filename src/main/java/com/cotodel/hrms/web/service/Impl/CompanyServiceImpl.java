package com.cotodel.hrms.web.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cotodel.hrms.web.function.common.CommonUtils;
import com.cotodel.hrms.web.properties.ApplicationConstantConfig;
import com.cotodel.hrms.web.response.CompanyProfileDetail;
import com.cotodel.hrms.web.response.EmployeeProfileRequest;
import com.cotodel.hrms.web.service.CompanyService;
import com.cotodel.hrms.web.util.CommonUtility;
import com.cotodel.hrms.web.util.EncriptResponse;
import com.cotodel.hrms.web.util.MessageConstant;

@Service
public class CompanyServiceImpl implements CompanyService{

	@Autowired
	public ApplicationConstantConfig applicationConstantConfig;
	
	@Override
	public String saveCompany(String token, EncriptResponse employeeProfileRequest) {
		return CommonUtility.userRequest(token,MessageConstant.gson.toJson(employeeProfileRequest), applicationConstantConfig.employerServiceBaseUrl+CommonUtils.registerCompany);
	}

	@Override
	public String getCompanyProfileStatus(String token, EncriptResponse employeeProfileRequest) {
		// TODO Auto-generated method stub
		return CommonUtility.userRequest(token,MessageConstant.gson.toJson(employeeProfileRequest), applicationConstantConfig.userServiceBaseUrl+CommonUtils.companyProfileStatus);
	}

//	@Override
//	public String getorgsubType(String token, EncriptResponse employeeProfileRequest) {
//		return CommonUtility.userRequest(token,MessageConstant.gson.toJson(employeeProfileRequest), applicationConstantConfig.masterServiceBaseUrl+CommonUtils.getorgsubType);
//		
//	}
//
//	@Override
//	public String getpayrollDetails(String token, EncriptResponse employeeProfileRequest) {
//		// TODO Auto-generated method stub
//		return CommonUtility.userRequest(token,MessageConstant.gson.toJson(employeeProfileRequest), applicationConstantConfig.employerServiceBaseUrl+CommonUtils.getpayrollDetails);
//	}

	@Override
	public String getGSTDetailsByGSTNumber(String token, EncriptResponse employeeProfileRequest) {
		return CommonUtility.userRequest(token,MessageConstant.gson.toJson(employeeProfileRequest), applicationConstantConfig.gstServiceBaseUrl+CommonUtils.gstDetailService);
	}
	

	@Override
	public String saveOrganizationDetail(String token, EncriptResponse companyProfileDetail) {
		return CommonUtility.userRequest(token,MessageConstant.gson.toJson(companyProfileDetail), applicationConstantConfig.userServiceBaseUrl+CommonUtils.saveCompanyDetail);
	}
	
	@Override
	public String updateOrganizationDetail(String token, EncriptResponse companyProfileDetail) {
		return CommonUtility.userRequest(token,MessageConstant.gson.toJson(companyProfileDetail), applicationConstantConfig.userServiceBaseUrl+CommonUtils.updateCompanyDetail);
	}

	@Override
	public String activeInactiveVoucherAmount(String token, EncriptResponse jsonObject) {
		return CommonUtility.userRequest(token,MessageConstant.gson.toJson(jsonObject), applicationConstantConfig.employerServiceBaseUrl+CommonUtils.activeInactiveVoucherAmount);
	}

	@Override
	public String usedAmountByCategories(String token, EncriptResponse jsonObject) {
		return CommonUtility.userRequest(token,MessageConstant.gson.toJson(jsonObject), applicationConstantConfig.employerServiceBaseUrl+CommonUtils.usedAmountByCategories);

	}

	@Override
	public String loadActiveInactiveUserList(String token, EncriptResponse jsonObject) {
		return CommonUtility.userRequest(token,MessageConstant.gson.toJson(jsonObject), applicationConstantConfig.employerServiceBaseUrl+CommonUtils.empOnboardingListTotalActive);
	}

	@Override
	public String erupiVoucherCreateListLimit(String token, EncriptResponse jsonObject) {
		return CommonUtility.userRequest(token,MessageConstant.gson.toJson(jsonObject), applicationConstantConfig.employerServiceBaseUrl+CommonUtils.erupiVoucherCreateListLimit);
	}
	

}
