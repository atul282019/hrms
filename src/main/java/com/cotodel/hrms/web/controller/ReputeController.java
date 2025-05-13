package com.cotodel.hrms.web.controller;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cotodel.hrms.web.properties.ApplicationConstantConfig;
import com.cotodel.hrms.web.response.ReputeEmployeeDetails;
import com.cotodel.hrms.web.response.ReputeEmployeeRequest;
import com.cotodel.hrms.web.response.Root;
import com.cotodel.hrms.web.service.ReputeService;
import com.cotodel.hrms.web.service.Impl.TokenGenerationImpl;
import com.cotodel.hrms.web.util.EncriptResponse;
import com.cotodel.hrms.web.util.EncryptionDecriptionUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@CrossOrigin
public class ReputeController extends CotoDelBaseController{

	private static final Logger logger = LoggerFactory.getLogger(ReputeController.class);

	@Autowired
	public ApplicationConstantConfig applicationConstantConfig;
	

	@Autowired
	TokenGenerationImpl tokengeneration;
	
	@Autowired
	ReputeService reputeService;
	
	
	@PostMapping(value="/getReputeEmployeeList")
	public @ResponseBody String getReputeEmployeeList(HttpServletRequest request, ModelMap model,Locale locale,HttpSession session,ReputeEmployeeRequest reputeEmployeeRequest) 
	{
		String profileRes=null;
		 String reputeAccessToken  = (String) session.getAttribute("reputeAccessToken");
		 String endpoint  = (String) session.getAttribute("endpoint");
		reputeEmployeeRequest.setAccessToken(reputeAccessToken);
		reputeEmployeeRequest.setEndpoint(endpoint);
		try {
			String json = EncryptionDecriptionUtil.convertToJson(reputeEmployeeRequest);

			EncriptResponse jsonObject=EncryptionDecriptionUtil.encriptResponse(json, applicationConstantConfig.apiSignaturePublicPath);

			String encriptResponse =  reputeService.getReputeEmployee(tokengeneration.getToken(), jsonObject);

   
			EncriptResponse userReqEnc =EncryptionDecriptionUtil.convertFromJson(encriptResponse, EncriptResponse.class);

			profileRes =  EncryptionDecriptionUtil.decriptResponse(userReqEnc.getEncriptData(), userReqEnc.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
   
	return profileRes;
		
	}
	

	@PostMapping(value="/webhook-repute")
	public ResponseEntity<Void> reputeWebhooks(@RequestBody(required = false) String payload) throws JsonMappingException, JsonProcessingException {
		
			logger.info("webhook-repute called");
			logger.info("repute hook called"+payload);	
		    String profileRes=null;
		    ObjectMapper om = new ObjectMapper();
			ReputeEmployeeDetails reputeEmployeeDetails = om.readValue(payload, ReputeEmployeeDetails.class); 
			try {
				profileRes = reputeService.reputeWebhooks(tokengeneration.getToken(),reputeEmployeeDetails);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return new ResponseEntity<>(null, HttpStatus.OK);
			
	}

	
}
