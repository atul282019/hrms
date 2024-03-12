package com.cotodel.hrms.web.controller;

import java.util.HashMap;
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
import com.cotodel.hrms.web.response.BulkEmployeeRequest;
import com.cotodel.hrms.web.service.BulkEmployeeService;
import com.cotodel.hrms.web.service.Impl.TokenGenerationImpl;
import com.cotodel.hrms.web.util.MessageConstant;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@CrossOrigin
public class BulkUserController extends CotoDelBaseController{

	private static final Logger logger = LoggerFactory.getLogger(BulkUserController.class);

	@Autowired
	public ApplicationConstantConfig applicationConstantConfig;
	
	@Autowired
	BulkEmployeeService bulkEmployeeService;

	@Autowired
	TokenGenerationImpl tokengeneration;
	
	
	@PostMapping(value="/saveBulkFile")
	public @ResponseBody String saveEmployeeDetail(HttpServletRequest request, ModelMap model,Locale locale,HttpSession session,BulkEmployeeRequest bulkEmployeeRequest) {
		String profileRes=null;JSONObject profileJsonRes=null;
		HashMap<String, String> otpMap = new  HashMap<String, String> ();
		ObjectMapper mapper = new ObjectMapper();
		String res=null;String userRes=null;
		
		
		
		profileRes = bulkEmployeeService.saveBulkDetail(tokengeneration.getToken(),bulkEmployeeRequest);
		logger.info(profileRes);
		profileJsonRes= new JSONObject(profileRes);
		
		if(profileJsonRes.getBoolean("status")) { 
			otpMap.put("status", MessageConstant.RESPONSE_SUCCESS);
		}else {
			//loginsevice.rsendEmailVerificationCompletion(userForm);
			otpMap.put("status", MessageConstant.RESPONSE_FAILED);
		}
		try {
			res = mapper.writeValueAsString(otpMap);
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return profileRes;
	}

	}
