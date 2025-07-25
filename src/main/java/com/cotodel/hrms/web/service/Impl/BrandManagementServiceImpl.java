package com.cotodel.hrms.web.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cotodel.hrms.web.function.common.CommonUtils;
import com.cotodel.hrms.web.properties.ApplicationConstantConfig;
import com.cotodel.hrms.web.service.BrandManagementService;
import com.cotodel.hrms.web.util.CommonUtility;
import com.cotodel.hrms.web.util.EncriptResponse;
import com.cotodel.hrms.web.util.MessageConstant;

@Service
public class BrandManagementServiceImpl implements BrandManagementService{

	@Autowired
	public ApplicationConstantConfig applicationConstantConfig;

	@Override
	public String activateBrandManagement(String token, EncriptResponse jsonObject) {
		 return CommonUtility.userRequest(token,
			        MessageConstant.gson.toJson(jsonObject),
			        applicationConstantConfig.brandServiceBaseUrl +CommonUtils.activateBrandManagement
			    );
	}

	@Override
	public String addBrandDetails(String token, EncriptResponse jsonObject) {
		 return CommonUtility.userRequest(token,
			        MessageConstant.gson.toJson(jsonObject),
			        applicationConstantConfig.brandServiceBaseUrl +CommonUtils.addBrandDetails
			    );
	}

	@Override
	public String getBrandOutletList(String token, EncriptResponse jsonObject) {
		 return CommonUtility.userRequest(token,
			        MessageConstant.gson.toJson(jsonObject),
			        applicationConstantConfig.brandServiceBaseUrl +CommonUtils.brandOutletList
			    );
	}

	@Override
	public String addGeograpgicDetails(String token, EncriptResponse jsonObject) {
		 return CommonUtility.userRequest(token,
			        MessageConstant.gson.toJson(jsonObject),
			        applicationConstantConfig.brandServiceBaseUrl +CommonUtils.addGeograpichDetails
			    );
	}

	@Override
	public String getBrupiBrandGeoList(String token, EncriptResponse jsonObject) {
		 return CommonUtility.userRequest(token,
			        MessageConstant.gson.toJson(jsonObject),
			        applicationConstantConfig.brandServiceBaseUrl +CommonUtils.getBrupiBrandGeoList
			    );
	}

}
