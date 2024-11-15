package com.cotodel.hrms.web.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cotodel.hrms.web.function.common.CommonUtils;
import com.cotodel.hrms.web.properties.ApplicationConstantConfig;
import com.cotodel.hrms.web.response.ErupiLinkBankAccount;
import com.cotodel.hrms.web.response.ErupiVoucherMaster;
import com.cotodel.hrms.web.response.UserRegistrationRequest;
import com.cotodel.hrms.web.service.MasterService;
import com.cotodel.hrms.web.util.CommonUtility;
import com.cotodel.hrms.web.util.MessageConstant;

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

	@Override
	public String getPermission(String token, UserRegistrationRequest userForm) {
		return CommonUtility.userRequest(token,null, applicationConstantConfig.userServiceBaseUrl +CommonUtils.getPermission);
	}

	@Override
	public String getRole(String token, UserRegistrationRequest userForm) {
		return CommonUtility.userRequest(token,null, applicationConstantConfig.userServiceBaseUrl +CommonUtils.getRole);
	}

	@Override
	public String getBankMaster(String token, UserRegistrationRequest userForm) {
		return CommonUtility.userRequest(token,null, applicationConstantConfig.employerServiceBaseUrl +CommonUtils.getBankMaster);
	}

	@Override
	public String getVoucherDescriptionByVoucherCode(String token, ErupiVoucherMaster erupiVoucherMaster) {
		return CommonUtility.userRequest(token,MessageConstant.gson.toJson(erupiVoucherMaster), applicationConstantConfig.employerServiceBaseUrl +CommonUtils.getVoucherDetailByBoucherCode);
	}

	@Override
	public String getBankDetailByAccountNo(String token, ErupiLinkBankAccount erupiLinkBankAccount) {
		return CommonUtility.userRequest(token,MessageConstant.gson.toJson(erupiLinkBankAccount), applicationConstantConfig.employerServiceBaseUrl +CommonUtils.getLinkedDetailByAccountNumber);
	}

}
