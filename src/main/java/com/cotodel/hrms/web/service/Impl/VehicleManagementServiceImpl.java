package com.cotodel.hrms.web.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cotodel.hrms.web.function.common.CommonUtils;
import com.cotodel.hrms.web.properties.ApplicationConstantConfig;
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
}
