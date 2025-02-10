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
import com.cotodel.hrms.web.util.EncriptResponse;
import com.cotodel.hrms.web.util.EncryptionDecriptionUtil;
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
	public @ResponseBody String saveEmployeeDetail(HttpServletRequest request, ModelMap model,Locale locale,HttpSession session,BulkInviteRequest bulkInviteRequest) {
		String profileRes=null;
		try {
			String json = EncryptionDecriptionUtil.convertToJson(bulkInviteRequest);

			EncriptResponse jsonObject=EncryptionDecriptionUtil.encriptResponse(json, applicationConstantConfig.apiSignaturePublicPath);

			String encriptResponse = bulkInviteService.bulkInvite(tokengeneration.getToken(), jsonObject);

   
			EncriptResponse userReqEnc =EncryptionDecriptionUtil.convertFromJson(encriptResponse, EncriptResponse.class);

			profileRes =  EncryptionDecriptionUtil.decriptResponse(userReqEnc.getEncriptData(), userReqEnc.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
   
	return profileRes;
	}

	}
