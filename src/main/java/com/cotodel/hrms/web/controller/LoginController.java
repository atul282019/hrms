package com.cotodel.hrms.web.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cotodel.hrms.web.jwt.util.JwtTokenGenerator;
import com.cotodel.hrms.web.properties.ApplicationConstantConfig;
import com.cotodel.hrms.web.response.UserDetailsEntity;
import com.cotodel.hrms.web.response.UserForm;
import com.cotodel.hrms.web.service.LoginService;
import com.cotodel.hrms.web.service.Impl.TokenGenerationImpl;
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
					request.getSession(true).setAttribute("id", profileJsonRes.getJSONObject("data").getInt("id"));
					
					//request.getSession(true).setAttribute("cotodel", profileJsonRes.getString("token"));
					
					//obj =  JwtTokenValidator.parseToken(profileJsonRes.getString("token"));
					session.setAttribute("id", profileJsonRes.getJSONObject("data").getInt("id"));
					session.setAttribute("email", profileJsonRes.getJSONObject("data").getString("email"));
					session.setAttribute("mobile", profileJsonRes.getJSONObject("data").getString("mobile"));
					session.setAttribute("username", profileJsonRes.getJSONObject("data").getString("username"));
					
					//check user is in database or not
					//userRes=loginService.checkUserExsistance(userForm);

					//logger.info("user login user entity "+userRes);

					//check user is active
					//check role is defined
					// check user is verified or not
					
						Map<String,Object> userRole = new HashMap<String,Object>();
						userRole.put("email", profileJsonRes.getJSONObject("data").getString("email"));
						userRole.put("mobile", profileJsonRes.getJSONObject("data").getString("mobile"));
						String email = profileJsonRes.getJSONObject("data").getString("email");
						String mobile = profileJsonRes.getJSONObject("data").getString("mobile");
						String username = profileJsonRes.getJSONObject("data").getString("username");
						String token	=	JwtTokenGenerator.generateToken(email,mobile,username, MessageConstant.SECRET);
						//return JSONUtil.setJSONResonse(MessageConstant.RESPONSE_SUCCESS, MessageConstant.TRUE, userRole,token);
					    request.getSession(true).setAttribute("hrms", token);
					    // switch case to identify the user screen login
					switch (String.valueOf(profileJsonRes.getJSONObject("data").getInt("role_id"))) {	
					case "1":
						screenName="index";
						model.addAttribute("message", "No Role assigned to User. Please contact to Organisation Admin !!");
						break;	
					case "2":
						//screenName="dashboard";
						screenName="employee-dashboard";
						model.addAttribute("message", "No Role assigned to User. Please contact to Organisation Admin !!");
						break;	
					case "0":
						//screenName="dashboard";
						screenName="employee-dashboard";
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