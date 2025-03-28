package com.cotodel.hrms.web.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cotodel.hrms.web.function.common.CommonUtils;
import com.cotodel.hrms.web.properties.ApplicationConstantConfig;
import com.cotodel.hrms.web.response.managerMaster;
import com.cotodel.hrms.web.service.managerMasterService;
import com.cotodel.hrms.web.util.CommonUtility;
import com.cotodel.hrms.web.util.EncriptResponse;
import com.cotodel.hrms.web.util.MessageConstant;

@Service
public class managerMasterServiceImpl implements managerMasterService{
		
	@Autowired
	public ApplicationConstantConfig applicationConstantConfig; 
	
//	@Override
//	public String savemanagerMaster(String token, EncriptResponse managermasterRequest) {
//		{
//	        // Convert the BankMaster object to JSON and make the request
//	        return CommonUtility.userRequest(
//	            token,
//	            MessageConstant.gson.toJson(managermasterRequest),
//	            applicationConstantConfig.employerServiceBaseUrl +CommonUtils.savemanagerMasterDetailS
//	        );
//	    }
// 
//}
//
//	@Override
//	public String getmanagerMasterWithId(String token, EncriptResponse managermasterRequest)  {
//		{
//	        // Convert the BankMaster object to JSON and make the request
//	        return CommonUtility.userRequest(
//	            token,
//	            MessageConstant.gson.toJson(managermasterRequest),
//	            applicationConstantConfig.employerServiceBaseUrl +CommonUtils.getmanagerMasterDetailSwithId
//	        );
//	    }
// 
//}

	
	
	
}
