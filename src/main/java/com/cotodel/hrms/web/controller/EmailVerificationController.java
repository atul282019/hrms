package com.cotodel.hrms.web.controller;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.cotodel.hrms.web.properties.ApplicationConstantConfig;
import com.cotodel.hrms.web.response.UserRegistrationRequest;
import com.cotodel.hrms.web.service.LoginService;
import com.cotodel.hrms.web.service.Impl.TokenGenerationImpl;
import com.cotodel.hrms.web.util.MessageConstant;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@CrossOrigin
public class EmailVerificationController extends CotoDelBaseController{
	
	private static final Logger logger = LoggerFactory.getLogger(EmailVerificationController.class);

	@Autowired
	public ApplicationConstantConfig applicationConstantConfig;

	@Autowired
	LoginService loginservice;
	
	@Autowired
	TokenGenerationImpl tokengeneration;
	
	@RequestMapping(value = "/sendEmailVerifyLink/{token}/{emailbyt}", method = RequestMethod.GET)
	public ModelAndView getRegistrationVerifiction(Model model, HttpServletRequest request, @PathVariable("token") String token
			,@PathVariable("emailbyt") String emailbyt) {
		
			System.out.println("In Request Mapping"); 
			byte[] tokenBytes = Base64.getDecoder().decode(token);//parseBase64Binary(token + "==");
			String mobileno = new String(tokenBytes, StandardCharsets.UTF_8);
			byte[] emailBytes = Base64.getDecoder().decode(emailbyt);
			String emailAgain = new String(emailBytes, StandardCharsets.UTF_8);
			System.out.println(mobileno + " ------ "+emailAgain);
			
		    UserRegistrationRequest userForm = new UserRegistrationRequest();
			userForm.setEmail(mobileno);
			userForm.setMobile(mobileno);
			model.addAttribute("userform",userForm);
		
			return new ModelAndView("emailverification", "command", "");
		
	}
	
	
	
	@PostMapping(value="/verifyRegisterUser")
	public @ResponseBody String verifyRegisterUser(HttpServletRequest request,UserRegistrationRequest userForm) {
		String profileRes=null;JSONObject profileJsonRes=null;
		HashMap<String, String> otpMap = new  HashMap<String, String> ();
		ObjectMapper mapper = new ObjectMapper();
		String res=null;String userRes=null;
		profileRes = loginservice.verifyRegisterUser(tokengeneration.getToken(),userForm);
		profileJsonRes= new JSONObject(profileRes);
		
		if(profileJsonRes.getBoolean("status")) { 
			otpMap.put("status", MessageConstant.RESPONSE_SUCCESS);
		}else {
			//loginservice.sendEmailVerificationCompletion(userForm);
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
