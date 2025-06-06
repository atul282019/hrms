package com.cotodel.hrms.web.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cotodel.hrms.web.function.common.CommonUtils;
import com.cotodel.hrms.web.properties.ApplicationConstantConfig;
import com.cotodel.hrms.web.response.BulkEmployeeRequest;
import com.cotodel.hrms.web.response.VehicleManagementBulkUploadRequest;
import com.cotodel.hrms.web.service.VehicleManagementService;
import com.cotodel.hrms.web.util.CommonUtility;
import com.cotodel.hrms.web.util.EncriptResponse;
import com.cotodel.hrms.web.util.MessageConstant;

@Service
public class VehicleManagementServiceImpl implements VehicleManagementService {
	@Autowired
	public ApplicationConstantConfig applicationConstantConfig;

	@Override
	public String getVehicleManagementList(String token, EncriptResponse VehicleManagementRequest) {
		 return CommonUtility.userRequest(token,
			        MessageConstant.gson.toJson(VehicleManagementRequest),
			        applicationConstantConfig.employerServiceBaseUrl +CommonUtils.getVehicleManagementList
			    );
	} 

	@Override
	public String addVehicleDetails(String token, EncriptResponse VehicleManagementSaveRequest) {
		 return CommonUtility.userRequest(token,
			        MessageConstant.gson.toJson(VehicleManagementSaveRequest),
			        applicationConstantConfig.employerServiceBaseUrl +CommonUtils.addVehicleDetails
			    );
	}

	@Override
	public String getVehicleManagementById(String token, EncriptResponse jsonObject) {
		 return CommonUtility.userRequest(token,
			        MessageConstant.gson.toJson(jsonObject),
			        applicationConstantConfig.employerServiceBaseUrl +CommonUtils.getVehicleManagementById
			    );
	}

	@Override
	public String updateVehicleDetails(String token, EncriptResponse jsonObject) {
		 return CommonUtility.userRequest(token,
			        MessageConstant.gson.toJson(jsonObject),
			        applicationConstantConfig.employerServiceBaseUrl +CommonUtils.updateVehicleDetails
			    );
	}

	@Override
	public String getEmployeeDriver(String token, EncriptResponse jsonObject) {
		 return CommonUtility.userRequest(token,
			        MessageConstant.gson.toJson(jsonObject),
			        applicationConstantConfig.employerServiceBaseUrl +CommonUtils.getDriverDetail
			    );
	}

	@Override
	public String vehichleTripHistory(String token, EncriptResponse jsonObject) {
		 return CommonUtility.userRequest(token,
			        MessageConstant.gson.toJson(jsonObject),
			        applicationConstantConfig.employerServiceBaseUrl +CommonUtils.vehichleTripHistory
			    );
	}

	@Override
	public String getVehicleNumberDetaiilByVehicleNumber(String token, EncriptResponse jsonObject) {
		 return CommonUtility.userRequest(token,
			        MessageConstant.gson.toJson(jsonObject),
			        applicationConstantConfig.gstServiceBaseUrl +CommonUtils.getVehicleNumberDetaiilByVehicleNumber
			    );
	}

	@Override
	public String checkAccountNumberValidation(String token, EncriptResponse jsonObject) {
		 return CommonUtility.userRequest(token,
		        MessageConstant.gson.toJson(jsonObject),
		        applicationConstantConfig.gstServiceBaseUrl +CommonUtils.getVehicleNumberDetaiilByVehicleNumber
		    );
	}

	@Override
	public String saveBulkVehicleDetail(String token, EncriptResponse jsonObject) {
		return CommonUtility.userRequest(token,MessageConstant.gson.toJson(jsonObject), applicationConstantConfig.employerServiceBaseUrl+CommonUtils.bulkVehicleUpload);

	}

	@Override
	public String createBulkVehicle(String token, EncriptResponse jsonObject) {
		return CommonUtility.userRequest(token,MessageConstant.gson.toJson(jsonObject), applicationConstantConfig.employerServiceBaseUrl+CommonUtils.createbulkVehicle);

	} 
}
