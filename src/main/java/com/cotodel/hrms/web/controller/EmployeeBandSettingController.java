package com.cotodel.hrms.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cotodel.hrms.web.properties.ApplicationConstantConfig;
import com.cotodel.hrms.web.response.EmployeeBandSettingResponse;
import com.cotodel.hrms.web.response.EmployeeBandTiersResponse;
import com.cotodel.hrms.web.response.EmployeePayrollNew;
import com.cotodel.hrms.web.response.EmployeePayrollRequest;
import com.cotodel.hrms.web.response.EmployeeProfileRequest;
import com.cotodel.hrms.web.service.EmployeeBandSettingService;
import com.cotodel.hrms.web.service.Impl.TokenGenerationImpl;
import com.cotodel.hrms.web.util.EncriptResponse;
import com.cotodel.hrms.web.util.EncryptionDecriptionUtil;
import com.cotodel.hrms.web.util.MessageConstant;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@CrossOrigin
public class EmployeeBandSettingController extends CotoDelBaseController{
	
	private static final Logger logger = LoggerFactory.getLogger(EmployeeBandSettingController.class);

	@Autowired
	public ApplicationConstantConfig applicationConstantConfig;

	@Autowired
	TokenGenerationImpl tokengeneration;
	
	@Autowired
	EmployeeBandSettingService empBandSettingService;
	
	@PostMapping(value="/saveEmployeeBandTierTab3")
	public @ResponseBody String getEmployeeBandTier(HttpServletRequest request, ModelMap model,Locale locale,HttpSession session,EmployeeBandSettingResponse employeeBandSettingResponse) {
		String profileRes=null;
		//return empBandSettingService.getEmployeeBandTier(tokengeneration.getToken(),employeeBandSettingResponse);
		try {
			String json = EncryptionDecriptionUtil.convertToJson(employeeBandSettingResponse);

			EncriptResponse jsonObject=EncryptionDecriptionUtil.encriptResponse(json, applicationConstantConfig.apiSignaturePublicPath);

			String encriptResponse =  empBandSettingService.getEmployeeBandTier(tokengeneration.getToken(), jsonObject);

   
			EncriptResponse userReqEnc =EncryptionDecriptionUtil.convertFromJson(encriptResponse, EncriptResponse.class);

			profileRes =  EncryptionDecriptionUtil.decriptResponse(userReqEnc.getEncriptData(), userReqEnc.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
   
	return profileRes;
	}
	
	
	@PostMapping(value="/saveEmployeeBandTab2")
	public @ResponseBody String saveEmployeeBandTab2(HttpServletRequest request, ModelMap model,Locale locale,HttpSession session,EmployeeBandSettingResponse employeeBandSettingResponse) {
		String profileRes=null;
		List<EmployeeBandTiersResponse> list = new ArrayList<EmployeeBandTiersResponse>();
		String data[]= employeeBandSettingResponse.getListArray();
		
		for (int i = 0; i < data.length; i++) {
			String listValue=data[i];
			String[] rowArray=listValue.split("@");
			EmployeeBandTiersResponse employeeBandTierSetting=new EmployeeBandTiersResponse();
			employeeBandTierSetting.setEmployeeBand(rowArray[0]);
			employeeBandTierSetting.setAdditionalTiers(rowArray[1]);
			
			employeeBandTierSetting.setEmployerId(employeeBandSettingResponse.getEmployerId());
			list.add(employeeBandTierSetting);
		}
		
		employeeBandSettingResponse.setList(list);
		//return profileRes = empBandSettingService.saveEmployeeBandTier(tokengeneration.getToken(),employeeBandSettingResponse);
		try {
			String json = EncryptionDecriptionUtil.convertToJson(employeeBandSettingResponse);

			EncriptResponse jsonObject=EncryptionDecriptionUtil.encriptResponse(json, applicationConstantConfig.apiSignaturePublicPath);

			String encriptResponse =  empBandSettingService.saveEmployeeBandTier(tokengeneration.getToken(), jsonObject);

   
			EncriptResponse userReqEnc =EncryptionDecriptionUtil.convertFromJson(encriptResponse, EncriptResponse.class);

			profileRes =  EncryptionDecriptionUtil.decriptResponse(userReqEnc.getEncriptData(), userReqEnc.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
   
	return profileRes;
	}
	

}
