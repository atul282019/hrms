package com.cotodel.hrms.web.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cotodel.hrms.web.function.common.CommonUtils;
import com.cotodel.hrms.web.properties.ApplicationConstantConfig;
import com.cotodel.hrms.web.response.jobTitlemaster;
import com.cotodel.hrms.web.service.jobTitlemasterService;
import com.cotodel.hrms.web.util.CommonUtility;
import com.cotodel.hrms.web.util.MessageConstant;

@Service
public class jobTitlemasterServiceImpl implements  jobTitlemasterService{
	
	
	@Autowired
	public ApplicationConstantConfig applicationConstantConfig; 

	@Override
	public String savejobTitlemaster(String token, jobTitlemaster jobtitlemasterRequest) {
		{
	        // Convert the BankMaster object to JSON and make the request
	        return CommonUtility.userRequest(
	            token,
	            MessageConstant.gson.toJson(jobtitlemasterRequest),
	            applicationConstantConfig.employerServiceBaseUrl +CommonUtils.savejobTitlemasterDetailS
	        );
	    }
 
}

	@Override
	public String getjobTitlemaster(String token, jobTitlemaster jobtitlemasterRequest) {
		{
	        // Convert the BankMaster object to JSON and make the request
	        return CommonUtility.userRequest(
	            token,
	            MessageConstant.gson.toJson(jobtitlemasterRequest),
	            applicationConstantConfig.employerServiceBaseUrl +CommonUtils.getjobTitlemasterDetailS
	        );
	    }
 
}
}
