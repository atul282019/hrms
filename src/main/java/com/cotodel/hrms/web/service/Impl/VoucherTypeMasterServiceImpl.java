package com.cotodel.hrms.web.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cotodel.hrms.web.function.common.CommonUtils;
import com.cotodel.hrms.web.properties.ApplicationConstantConfig;
import com.cotodel.hrms.web.response.VoucherTypeMaster;
import com.cotodel.hrms.web.service.VoucherTypeMasterService;
import com.cotodel.hrms.web.util.CommonUtility;
import com.cotodel.hrms.web.util.EncriptResponse;
import com.cotodel.hrms.web.util.MessageConstant;

@Service
public class VoucherTypeMasterServiceImpl implements VoucherTypeMasterService{
	
	@Autowired
	public ApplicationConstantConfig applicationConstantConfig;
	
	@Override
	public String saveVoucherTypeMaster(String token, EncriptResponse voucherTypeMaster) {
		return CommonUtility.userRequest( 
	            token,
	            MessageConstant.gson.toJson(voucherTypeMaster),
	            applicationConstantConfig.masterServiceBaseUrl +CommonUtils.saveVoucherTypeMasterDetailS
	        );
	}

	@Override
	public String getVoucherTypeMasterList(String token, EncriptResponse voucherTypeMaster) {
		 return CommonUtility.userRequest(token,
			        MessageConstant.gson.toJson(voucherTypeMaster),
			        applicationConstantConfig.masterServiceBaseUrl +CommonUtils.getvoucherTypeMasterList
			    );
	}

	@Override
	public String updatevoucherTypeMasterStatus(String token, EncriptResponse voucherTypeMaster) {
		 return CommonUtility.userRequest(token,
			        MessageConstant.gson.toJson(voucherTypeMaster),
			        applicationConstantConfig.employerServiceBaseUrl +CommonUtils.updatevoucherTypeMasterStatus
			    );
	}

	@Override
	public String createVoucher(String token, EncriptResponse VoucherCreateRequest) {
		return CommonUtility.userRequest(token,
		        MessageConstant.gson.toJson(VoucherCreateRequest),
		        applicationConstantConfig.employerServiceBaseUrl +CommonUtils.createVoucher
		    );
	}

	@Override
	public String getRequestedVoucherList(String token, EncriptResponse voucherTypeMaster) {
		 return CommonUtility.userRequest(token,
			        MessageConstant.gson.toJson(voucherTypeMaster),
			        applicationConstantConfig.employerServiceBaseUrl +CommonUtils.getRequestedVoucherList
			    );
	}

	@Override
	public String erupiVoucherRequestByMngId(String token, EncriptResponse encryptedRequest) {
		 return CommonUtility.userRequest(token,
			        MessageConstant.gson.toJson(encryptedRequest),
			        applicationConstantConfig.employerServiceBaseUrl +CommonUtils.erupiVoucherRequestByMngId
			    );
	}

	@Override
	public String approveRejectVoucher(String token, EncriptResponse encryptedRequest) {
		 return CommonUtility.userRequest(token,
			        MessageConstant.gson.toJson(encryptedRequest),
			        applicationConstantConfig.employerServiceBaseUrl +CommonUtils.approveRejectRequestedVoucherById
			    );
	}

	@Override
	public String getRequestedVoucherApproveList(String token, EncriptResponse encryptedRequest) {
		 return CommonUtility.userRequest(token,
			        MessageConstant.gson.toJson(encryptedRequest),
			        applicationConstantConfig.employerServiceBaseUrl +CommonUtils.getRequestedVoucherApproveList
			    );
	}
	

}
