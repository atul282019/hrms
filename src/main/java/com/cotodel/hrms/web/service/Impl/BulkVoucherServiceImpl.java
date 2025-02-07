package com.cotodel.hrms.web.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cotodel.hrms.web.function.common.CommonUtils;
import com.cotodel.hrms.web.properties.ApplicationConstantConfig;
import com.cotodel.hrms.web.service.BulkVoucherService;
import com.cotodel.hrms.web.util.CommonUtility;
import com.cotodel.hrms.web.util.EncriptResponse;
import com.cotodel.hrms.web.util.MessageConstant;

@Service
public class BulkVoucherServiceImpl implements BulkVoucherService{

	@Autowired
	public ApplicationConstantConfig applicationConstantConfig;
	
	@Override
	public String saveBulkVoucher(String token, EncriptResponse bulkVoucherRequest) {
		return CommonUtility.userRequest(token,MessageConstant.gson.toJson(bulkVoucherRequest), applicationConstantConfig.employerServiceBaseUrl+CommonUtils.bulkVoucherUpload);

	}

}
