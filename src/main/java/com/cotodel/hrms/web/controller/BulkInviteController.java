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
import com.cotodel.hrms.web.response.BulkInviteRequest;
import com.cotodel.hrms.web.service.BulkInviteService;
import com.cotodel.hrms.web.service.Impl.TokenGenerationImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@CrossOrigin
public class BulkInviteController extends CotoDelBaseController{

	private static final Logger logger = LoggerFactory.getLogger(BulkInviteController.class);

	@Autowired
	public ApplicationConstantConfig applicationConstantConfig;
	
	@Autowired
	BulkInviteService bulkInviteService;

	@Autowired
	TokenGenerationImpl tokengeneration;
	
	
	@PostMapping(value="/sendInviteEmail")
	public @ResponseBody String saveEmployeeDetail(HttpServletRequest request, ModelMap model,Locale locale,HttpSession session,BulkInviteRequest bulkEmployeeRequest) {
		String profileRes=null;JSONObject profileJsonRes=null;
		HashMap<String, String> otpMap = new  HashMap<String, String> ();
		ObjectMapper mapper = new ObjectMapper();
		String res=null;String userRes=null;
				
		profileRes = bulkInviteService.bulkInvite(tokengeneration.getToken(),bulkEmployeeRequest);

		
		return profileRes;
	}

	}
