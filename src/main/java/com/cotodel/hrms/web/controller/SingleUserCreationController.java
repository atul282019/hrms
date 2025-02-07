package com.cotodel.hrms.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cotodel.hrms.web.properties.ApplicationConstantConfig;
import com.cotodel.hrms.web.response.UserRegistrationRequest;
import com.cotodel.hrms.web.service.SingleUserCreationService;
import com.cotodel.hrms.web.service.Impl.TokenGenerationImpl;
import com.cotodel.hrms.web.util.EncriptResponse;
import com.cotodel.hrms.web.util.EncryptionDecriptionUtil;

@Controller
@CrossOrigin
public class SingleUserCreationController extends CotoDelBaseController{
	
	private static final Logger logger = LoggerFactory.getLogger(SingleUserCreationController.class);
	
	@Autowired
	public ApplicationConstantConfig applicationConstantConfig;
	@Autowired
	SingleUserCreationService singleUserService;
	
	@Autowired
	TokenGenerationImpl tokengeneration;

	@PostMapping(value="/singleUserCreation")
	public @ResponseBody String registerUser(HttpServletRequest request,UserRegistrationRequest userForm) {
		String profileRes=null;
		//profileRes = singleUserService.singleUserCreation(tokengeneration.getToken(),userForm);
		try {
			String json = EncryptionDecriptionUtil.convertToJson(userForm);

			EncriptResponse jsonObject=EncryptionDecriptionUtil.encriptResponse(json, applicationConstantConfig.apiSignaturePublicPath);

			String encriptResponse = singleUserService.singleUserCreation(tokengeneration.getToken(), jsonObject);

   
			EncriptResponse userReqEnc =EncryptionDecriptionUtil.convertFromJson(encriptResponse, EncriptResponse.class);

			profileRes =  EncryptionDecriptionUtil.decriptResponse(userReqEnc.getEncriptData(), userReqEnc.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
   
		return profileRes;
	}
	

	@GetMapping(value="/getUserList")
	public @ResponseBody String getUser(HttpServletRequest request,UserRegistrationRequest userForm) {
		String profileRes=null;
		//profileRes = singleUserService.getUser(tokengeneration.getToken(),userForm);
		
		try {
			String json = EncryptionDecriptionUtil.convertToJson(userForm);

			EncriptResponse jsonObject=EncryptionDecriptionUtil.encriptResponse(json, applicationConstantConfig.apiSignaturePublicPath);

			String encriptResponse = singleUserService.getUser(tokengeneration.getToken(), jsonObject);

   
			EncriptResponse userReqEnc =EncryptionDecriptionUtil.convertFromJson(encriptResponse, EncriptResponse.class);

			profileRes =  EncryptionDecriptionUtil.decriptResponse(userReqEnc.getEncriptData(), userReqEnc.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return profileRes;
	}
}
