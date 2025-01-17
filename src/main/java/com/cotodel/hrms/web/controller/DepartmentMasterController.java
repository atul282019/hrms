package com.cotodel.hrms.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.cotodel.hrms.web.properties.ApplicationConstantConfig;
import com.cotodel.hrms.web.response.DepartmentMaster;
import com.cotodel.hrms.web.response.ReCaptcha;
import com.cotodel.hrms.web.response.UserRegistrationRequest;
import com.cotodel.hrms.web.service.DepartmentMasterService;
import com.cotodel.hrms.web.service.Impl.TokenGenerationImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@CrossOrigin
public class DepartmentMasterController extends CotoDelBaseController{
	
	private static final Logger logger = LoggerFactory.getLogger(DepartmentMasterController.class);
	
	@Autowired
	TokenGenerationImpl tokengeneration;
	
	@Autowired
	public ApplicationConstantConfig applicationConstantConfig;
	
	@Autowired
	DepartmentMasterService departmentMasterService;
	
//	@GetMapping(value="/getDepartmentMaster")
//	public @ResponseBody String getDepartmentMaster(HttpServletRequest request, ModelMap model,Locale locale,
//			HttpSession session,DepartmentMaster departmentMaster) {
//		logger.info("getDepartmentMaster");	
//		String token = (String) session.getAttribute("cotodel");
//		return departmentMasterService.getDepartmentMaster(tokengeneration.getToken(),departmentMaster);
//	}
	
	

}
