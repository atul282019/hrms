package com.cotodel.hrms.web.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cotodel.hrms.web.function.common.CommonUtils;
import com.cotodel.hrms.web.properties.ApplicationConstantConfig;
import com.cotodel.hrms.web.response.ExpenseBillReaderRequest;
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
			        applicationConstantConfig.brandServiceBaseUrl +CommonUtils.brandList
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

	@Override
	public String getOutletDetail(String token, EncriptResponse jsonObject) {
		 return CommonUtility.userRequest(token,
			        MessageConstant.gson.toJson(jsonObject),
			        applicationConstantConfig.brandServiceBaseUrl +CommonUtils.getOutletDetail
			    );
	}

	@Override
	public String saveBulkOutletDetail(String token, EncriptResponse jsonObject) {
		 return CommonUtility.userRequest(token,
			        MessageConstant.gson.toJson(jsonObject),
			        applicationConstantConfig.brandServiceBaseUrl +CommonUtils.bulkOutletUpload
			    );
	}

	@Override
	public String createBulkOutlet(String token, EncriptResponse jsonObject) {
		 return CommonUtility.userRequest(token,
			        MessageConstant.gson.toJson(jsonObject),
			        applicationConstantConfig.brandServiceBaseUrl +CommonUtils.confirmBulkOutletUpload
			    );
	}

	@Override
	public String erupiBrandOutletById(String token, EncriptResponse jsonObject) {
		 return CommonUtility.userRequest(token,
			        MessageConstant.gson.toJson(jsonObject),
			        applicationConstantConfig.brandServiceBaseUrl +CommonUtils.brandOutletDetailById
			    );
	}

	@Override
	public String saveBrandOutletDeviceDetails(String token, EncriptResponse jsonObject) {
		 return CommonUtility.userRequest(token,
			        MessageConstant.gson.toJson(jsonObject),
			        applicationConstantConfig.brandServiceBaseUrl +CommonUtils.saveBrandOutletDeviceDetails
			    );
	}

	@Override
	public String getDeviceDetailList(String token, EncriptResponse jsonObject) {
		return CommonUtility.userRequest(token,
		        MessageConstant.gson.toJson(jsonObject),
		        applicationConstantConfig.brandServiceBaseUrl +CommonUtils.getDeviceDetailList
		    );
	}

	@Override
	public String deactivateOutlet(String token, EncriptResponse jsonObject) {
		return CommonUtility.userRequest(token,
		        MessageConstant.gson.toJson(jsonObject),
		        applicationConstantConfig.brandServiceBaseUrl +CommonUtils.deactivateOutlet
		    );
	}

	@Override
	public String editBrandOutletDetail(String token, EncriptResponse jsonObject) {
		return CommonUtility.userRequest(token,
		        MessageConstant.gson.toJson(jsonObject),
		        applicationConstantConfig.brandServiceBaseUrl +CommonUtils.editOutletDetail
		    );
	}

	@Override
	public String activateDeactivateLinkedDevice(String token, EncriptResponse jsonObject) {
		return CommonUtility.userRequest(token,
		        MessageConstant.gson.toJson(jsonObject),
		        applicationConstantConfig.brandServiceBaseUrl +CommonUtils.activateDeactivateLinkedDevice
		    );
	}

	@Override
	public String getLinkedDeviceDetail(String token, EncriptResponse jsonObject) {
		return CommonUtility.userRequest(token,
		        MessageConstant.gson.toJson(jsonObject),
		        applicationConstantConfig.brandServiceBaseUrl +CommonUtils.reviewLinkedDevice
		    );
	}

	@Override
	public String readDataFromImage(String token,  ExpenseBillReaderRequest expenseBillReaderRequest) {
		return CommonUtility.userRequest(token,
		        MessageConstant.gson.toJson(expenseBillReaderRequest),
		        applicationConstantConfig.employerServiceBaseUrl +CommonUtils.readDataFromImage
		    );
	}

	@Override
	public String editActiveBrandDetails(String token, EncriptResponse jsonObject) {
		return CommonUtility.userRequest(token,
		        MessageConstant.gson.toJson(jsonObject),
		        applicationConstantConfig.brandServiceBaseUrl +CommonUtils.editActiveBrandDetails
		    );
	}

	@Override
	public String editGeograpgicDetails(String token, EncriptResponse jsonObject) {
		return CommonUtility.userRequest(token,
		        MessageConstant.gson.toJson(jsonObject),
		        applicationConstantConfig.brandServiceBaseUrl +CommonUtils.updateBrandGeoList
		    );
	}

}
