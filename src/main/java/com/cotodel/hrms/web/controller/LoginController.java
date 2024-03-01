package com.cotodel.hrms.web.controller;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.bind.DatatypeConverter;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cotodel.hrms.web.properties.ApplicationConstantConfig;
import com.cotodel.hrms.web.response.UserDetailsEntity;
import com.cotodel.hrms.web.response.UserForm;
import com.cotodel.hrms.web.service.LoginService;
import com.cotodel.hrms.web.service.Impl.TokenGenerationImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.cotodel.hrms.web.util.MessageConstant;

@Controller
@CrossOrigin
public class LoginController extends CotoDelBaseController{

	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

	@Autowired
	public ApplicationConstantConfig applicationConstantConfig;

	@Autowired
	LoginService loginservice;
	
	@Autowired
	TokenGenerationImpl tokengeneration;
	
	@PostMapping(value="/userLogin")
	public String validateLogin(HttpServletResponse response, HttpServletRequest request,
			@ModelAttribute("userForm") UserForm userForm, BindingResult result, HttpSession session, Model model,RedirectAttributes redirect) {
		String profileRes=null;JSONObject profileJsonRes=null;String screenName="index";
		UserDetailsEntity obj =null;
		try {
			String password = null;
			password= userForm.getPassword1()+userForm.getPassword2()+userForm.getPassword3()+userForm.getPassword4()+userForm.getPassword5()+userForm.getPassword6();      
			profileRes =loginservice.verifyOtp(tokengeneration.getToken(),userForm.getUserName(),userForm.getMob(),password,userForm.getOrderId());
			
			logger.info(profileRes);
			
			if(!ObjectUtils.isEmpty(profileRes)) {
				
				profileJsonRes= new JSONObject(profileRes);
				
				if(profileJsonRes.getBoolean("status") 
						&& profileJsonRes.getString("message").equalsIgnoreCase(MessageConstant.RESPONSE_SUCCESS)) {
				
					//set token in session
					request.getSession(true).setAttribute("email", profileJsonRes.getJSONObject("data").getString("email"));									  
					request.getSession(true).setAttribute("hrms", profileJsonRes.getJSONObject("data").getString("mobile"));
					request.getSession(true).setAttribute("username", profileJsonRes.getJSONObject("data").getString("username"));
					
					//request.getSession(true).setAttribute("cotodel", profileJsonRes.getString("token"));
					
					//obj =  JwtTokenValidator.parseToken(profileJsonRes.getString("token"));
					
					session.setAttribute("email", profileJsonRes.getJSONObject("data").getString("email"));
					session.setAttribute("mobile", profileJsonRes.getJSONObject("data").getString("mobile"));
					session.setAttribute("username", profileJsonRes.getJSONObject("data").getString("username"));
					
				
					// switch case to identify the user screen login
					switch (String.valueOf(profileJsonRes.getJSONObject("data").getInt("role_id"))) {	
					case "1":
						screenName="index";
						model.addAttribute("message", "No Role assigned to User. Please contact to Organisation Admin !!");
						break;		
					case "0":
						screenName="dashboard";
						model.addAttribute("name",profileJsonRes.getJSONObject("data").getString("email"));
						break;		
					}
					return screenName;
				}else {
						model.addAttribute("message", "Incorrect OTP !!");
				}
			}else {
				model.addAttribute("message", "Incorrect OTP !!");
			}	
			return "index";
		}catch (Exception e) {
			logger.error(e.getMessage());
			model.addAttribute("message", "System not responding, Please try again later..!");
			return "index";
		}finally {
			profileRes=null;profileJsonRes=null;screenName=null; obj =null;
		}
	}

}