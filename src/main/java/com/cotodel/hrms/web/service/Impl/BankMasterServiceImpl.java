package com.cotodel.hrms.web.service.Impl;
import com.cotodel.hrms.web.util.MessageConstant;
import java.io.Console;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cotodel.hrms.web.function.common.CommonUtils;
import com.cotodel.hrms.web.properties.ApplicationConstantConfig;
import com.cotodel.hrms.web.response.BankMaster;
import com.cotodel.hrms.web.service.BankMasterService;
import com.cotodel.hrms.web.util.CommonUtility;

@Service
public class BankMasterServiceImpl implements BankMasterService {

	
	@Autowired
	public ApplicationConstantConfig applicationConstantConfig; 
	
	@Override
	public String saveBankMaster(String token,BankMaster bankMasterRequest) {
		{
	        // Convert the BankMaster object to JSON and make the request
	        return CommonUtility.userRequest(
	            token,
	            MessageConstant.gson.toJson(bankMasterRequest),
	            applicationConstantConfig.employerServiceBaseUrl +CommonUtils.saveBankMasterDetailS
	        );
	    }
 
}


	

	@Override
	public String getBankMasterList(String token, BankMaster bankMasterRequest) {
		 return CommonUtility.userRequest(token,
        MessageConstant.gson.toJson(bankMasterRequest),
        applicationConstantConfig.employerServiceBaseUrl +CommonUtils.getbankNameMasterList
    );
	}




	@Override
	public String getaftersaveBankMasterDetailsList(String token, BankMaster bankMaster) {
		return CommonUtility.userRequest(token,
		        MessageConstant.gson.toJson(bankMaster),
		        applicationConstantConfig.employerServiceBaseUrl +CommonUtils.getaftersaveBankMasterDetailsList
		    );
	}




	@Override
	public String updatebankMasterDetailStatus(String token, BankMaster bankMaster) {
		return CommonUtility.userRequest(token,
		        MessageConstant.gson.toJson(bankMaster),
		        applicationConstantConfig.employerServiceBaseUrl +CommonUtils.updatebankMasterDetailStatus
		    );
	}
}

