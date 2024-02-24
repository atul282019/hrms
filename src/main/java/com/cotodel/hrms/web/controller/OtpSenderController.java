package com.cotodel.hrms.web.controller;

import java.util.HashMap;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cotodel.hrms.web.response.UserForm;
import com.cotodel.hrms.web.service.LoginService;
import com.cotodel.hrms.web.service.Impl.TokenGenerationImpl;
import com.cotodel.hrms.web.util.MessageConstant;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@CrossOrigin
public class OtpSenderController extends CotoDelBaseController{
	
	private static final Logger logger = LoggerFactory.getLogger(OtpSenderController.class);
	
	@Autowired
	LoginService loginservice;
	
	@Autowired
	TokenGenerationImpl tokengeneration;
	
	@PostMapping(value="/smsOtpSender")
	public @ResponseBody String sendOtp(HttpServletRequest request,@ModelAttribute("userForm") UserForm userForm,Locale locale,Model model) {
		ObjectMapper mapper = new ObjectMapper();
		String res=null;String userRes=null;
		HashMap<String, String> otpMap = new  HashMap<String, String> ();
		try {
			userRes=loginservice.sendOtp(tokengeneration.getToken(), userForm.getUserName(),userForm.getMob());
			
			if(!ObjectUtils.isEmpty(userRes)) {	
				JSONObject userJson= new JSONObject(userRes);
				if(userJson.getBoolean("status")) {
					otpMap.put("msg", userJson.getString("message"));
					otpMap.put("status", MessageConstant.RESPONSE_SUCCESS);
				}else {
					otpMap.put("msg", userJson.getString("message"));
					otpMap.put("status", MessageConstant.RESPONSE_FAILED);
				}
			}else { 
				otpMap.put("msg", "System not responding, Please try again later..!"); 
				otpMap.put("status",  MessageConstant.RESPONSE_FAILED); 
			}
			res = mapper.writeValueAsString(otpMap);
			logger.info("response for smsOtpSender *****-----"+res);
		} catch (Exception e) {
			logger.error("error in smsOtpSender =============="+e);
		}finally {
			mapper=null;otpMap=null;userRes=null;
		}
		return res;
	}	
	
	

}
