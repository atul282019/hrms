package com.cotodel.hrms.web.controller;


import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.cotodel.hrms.web.properties.ApplicationConstantConfig;
import com.cotodel.hrms.web.response.ExpenseCategoryRequest;
import com.cotodel.hrms.web.response.VehicleManagementRequest;
import com.cotodel.hrms.web.response.VehicleManagementSaveRequest;
import com.cotodel.hrms.web.service.VehicleManagementService;
import com.cotodel.hrms.web.service.Impl.TokenGenerationImpl;
import com.cotodel.hrms.web.util.EncriptResponse;
import com.cotodel.hrms.web.util.EncryptionDecriptionUtil;

@RestController
@CrossOrigin
public class VehicleManagementController extends CotoDelBaseController {
	
	@Autowired
	TokenGenerationImpl tokengeneration;
	
	@Autowired
	VehicleManagementService vehicleManagementService;
	
	@Autowired
	public ApplicationConstantConfig applicationConstantConfig;
	
	@GetMapping(value = "/getVehicleList")
	public @ResponseBody String VehicleManagementList(HttpServletRequest request, ModelMap model, Locale locale,
			HttpSession session, VehicleManagementRequest vehicleManagementRequest) {
		String profileRes = null;
		
		try {
			String json = EncryptionDecriptionUtil.convertToJson(vehicleManagementRequest);

			EncriptResponse jsonObject=EncryptionDecriptionUtil.encriptResponse(json, applicationConstantConfig.apiSignaturePublicPath);

			String encriptResponse =  vehicleManagementService.getVehicleManagementList(tokengeneration.getToken(), jsonObject);

   
			EncriptResponse userReqEnc =EncryptionDecriptionUtil.convertFromJson(encriptResponse, EncriptResponse.class);

			profileRes =  EncryptionDecriptionUtil.decriptResponse(userReqEnc.getEncriptData(), userReqEnc.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
   
	return profileRes;
	
	
	}
	
	@PostMapping(value = "/addVehicleDetails")
	public @ResponseBody String addVehicleDetails(HttpServletRequest request, ModelMap model, Locale locale,
			HttpSession session, VehicleManagementSaveRequest vManagementSaveRequest) {
		String profileRes = null;
		
		try {
			String json = EncryptionDecriptionUtil.convertToJson(vManagementSaveRequest);

			EncriptResponse jsonObject=EncryptionDecriptionUtil.encriptResponse(json, applicationConstantConfig.apiSignaturePublicPath);

			String encriptResponse =  vehicleManagementService.addVehicleDetails(tokengeneration.getToken(), jsonObject);

   
			EncriptResponse userReqEnc =EncryptionDecriptionUtil.convertFromJson(encriptResponse, EncriptResponse.class);

			profileRes =  EncryptionDecriptionUtil.decriptResponse(userReqEnc.getEncriptData(), userReqEnc.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
   
	return profileRes;
	
	
	}

}
