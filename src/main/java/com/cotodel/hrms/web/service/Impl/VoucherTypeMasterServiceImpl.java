package com.cotodel.hrms.web.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cotodel.hrms.web.function.common.CommonUtils;
import com.cotodel.hrms.web.properties.ApplicationConstantConfig;
import com.cotodel.hrms.web.response.VoucherTypeMaster;
import com.cotodel.hrms.web.service.VoucherTypeMasterService;
import com.cotodel.hrms.web.util.CommonUtility;
import com.cotodel.hrms.web.util.MessageConstant;

@Service
public class VoucherTypeMasterServiceImpl implements VoucherTypeMasterService{
	
	@Autowired
	public ApplicationConstantConfig applicationConstantConfig;
	
	@Override
	public String saveVoucherTypeMaster(String token, VoucherTypeMaster voucherTypeMaster) {
		return CommonUtility.userRequest(
	            token,
	            MessageConstant.gson.toJson(voucherTypeMaster),
	            applicationConstantConfig.employerServiceBaseUrl +CommonUtils.saveVoucherTypeMasterDetailS
	        );
	}

	@Override
	public String getVoucherTypeMasterList(String token, VoucherTypeMaster voucherTypeMaster) {
		 return CommonUtility.userRequest(token,
			        MessageConstant.gson.toJson(voucherTypeMaster),
			        applicationConstantConfig.employerServiceBaseUrl +CommonUtils.getvoucherTypeMasterList
			    );
	}

	@Override
	public String updatevoucherTypeMasterStatus(String token, VoucherTypeMaster voucherTypeMaster) {
		 return CommonUtility.userRequest(token,
			        MessageConstant.gson.toJson(voucherTypeMaster),
			        applicationConstantConfig.employerServiceBaseUrl +CommonUtils.updatevoucherTypeMasterStatus
			    );
	}


	

}
