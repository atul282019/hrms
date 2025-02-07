package com.cotodel.hrms.web.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cotodel.hrms.web.function.common.CommonUtils;
import com.cotodel.hrms.web.properties.ApplicationConstantConfig;
import com.cotodel.hrms.web.response.EmployeeMassterRequest;
import com.cotodel.hrms.web.response.ErupiLinkBankAccount;
import com.cotodel.hrms.web.response.ErupiVoucherMaster;
import com.cotodel.hrms.web.response.MccMaster;
import com.cotodel.hrms.web.response.UserRegistrationRequest;
import com.cotodel.hrms.web.service.MasterService;
import com.cotodel.hrms.web.util.CommonUtility;
import com.cotodel.hrms.web.util.EncriptResponse;
import com.cotodel.hrms.web.util.MessageConstant;

@Service
public class MasterServiceImpl implements MasterService{
	
	@Autowired
	public ApplicationConstantConfig applicationConstantConfig;

	@Override
	public String getStateMaster(String token,EncriptResponse userForm) {
		return CommonUtility.userRequest(token,null, applicationConstantConfig.userServiceBaseUrl +CommonUtils.stateMaster);
	}

	@Override
	public String getOrgMaster(String token,EncriptResponse userForm) {
		return CommonUtility.userRequest(token,null, applicationConstantConfig.userServiceBaseUrl +CommonUtils.orgMaster);
	}

	@Override
	public String getPermission(String token, EncriptResponse userForm) {
		return CommonUtility.userRequest(token,null, applicationConstantConfig.userServiceBaseUrl +CommonUtils.getPermission);
	}

	@Override
	public String getRole(String token, EncriptResponse userForm) {
		return CommonUtility.userRequest(token,null, applicationConstantConfig.userServiceBaseUrl +CommonUtils.getRole);
	}

	@Override
	public String getBankMaster(String token, EncriptResponse userForm) {
		return CommonUtility.userRequest(token,null, applicationConstantConfig.employerServiceBaseUrl +CommonUtils.getBankMaster);
	}

	@Override
	public String getVoucherDescriptionByVoucherCode(String token, EncriptResponse erupiVoucherMaster) {
		return CommonUtility.userRequest(token,MessageConstant.gson.toJson(erupiVoucherMaster), applicationConstantConfig.employerServiceBaseUrl +CommonUtils.getVoucherDetailByBoucherCode);
	}

	@Override
	public String getBankDetailByAccountNo(String token, EncriptResponse erupiLinkBankAccount) {
		return CommonUtility.userRequest(token,MessageConstant.gson.toJson(erupiLinkBankAccount), applicationConstantConfig.employerServiceBaseUrl +CommonUtils.getLinkedDetailByAccountNumber);
	}

	@Override
	public String getmccMasterListByPurposeCode(String token, EncriptResponse mccMaster) {
		return CommonUtility.userRequest(token,MessageConstant.gson.toJson(mccMaster), applicationConstantConfig.masterServiceBaseUrl +CommonUtils.getMCCMasterByPurposeCode);

	}

	@Override
	public String getmccMasterDetailsByPurposeCodeAndMcc(String token, EncriptResponse mccMaster) {
		return CommonUtility.userRequest(token,MessageConstant.gson.toJson(mccMaster), applicationConstantConfig.masterServiceBaseUrl +CommonUtils.getMCCMasterDeailsByPurposeCodeAndMCC);
	}

	@Override
	public String getEmployeeType(String token, EncriptResponse employeeMassterRequest) {
		return CommonUtility.userRequest(token,MessageConstant.gson.toJson(employeeMassterRequest), applicationConstantConfig.masterServiceBaseUrl +CommonUtils.getEmployeeType);

	}

	@Override
	public String getEmployeeListMaster(String token, EncriptResponse employeeMassterRequest) {
		return CommonUtility.userRequest(token,MessageConstant.gson.toJson(employeeMassterRequest), applicationConstantConfig.userServiceBaseUrl +CommonUtils.getEmployeeListMaster);

	}

	@Override
	public String getofficeLocationMaster(String token, EncriptResponse employeeMassterRequest) {
		return CommonUtility.userRequest(token,MessageConstant.gson.toJson(employeeMassterRequest), applicationConstantConfig.userServiceBaseUrl +CommonUtils.getofficeLocationMaster);

	}

	@Override
	public String getVoucherListWithIcon(String token, EncriptResponse employeeMassterRequest) {
		return CommonUtility.userRequest(token,MessageConstant.gson.toJson(employeeMassterRequest), applicationConstantConfig.masterServiceBaseUrl +CommonUtils.getVoucherListWithIcon);

	}

	@Override
	public String getPurposeListByVoucherCode(String token, EncriptResponse employeeMassterRequest) {
		return CommonUtility.userRequest(token,MessageConstant.gson.toJson(employeeMassterRequest), applicationConstantConfig.masterServiceBaseUrl +CommonUtils.getPurposeListByVoucherCode);
	}

	@Override
	public String getBankListWithVocher(String token, EncriptResponse employeeMassterRequest) {
		return CommonUtility.userRequest(token,MessageConstant.gson.toJson(employeeMassterRequest), applicationConstantConfig.employerServiceBaseUrl +CommonUtils.getVoucherCreateBankList);
	}

	@Override
	public String voucherCreateSummaryDetailByAccount(String token, EncriptResponse employeeMassterRequest) {
		return CommonUtility.userRequest(token,MessageConstant.gson.toJson(employeeMassterRequest), applicationConstantConfig.employerServiceBaseUrl +CommonUtils.getVoucherCreateSummaryDetailByAccount);
	}

}
