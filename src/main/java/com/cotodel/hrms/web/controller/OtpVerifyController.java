package com.cotodel.hrms.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cotodel.hrms.web.response.UserForm;
import com.cotodel.hrms.web.service.LoginService;
import com.cotodel.hrms.web.service.Impl.TokenGenerationImpl;

@Controller
@CrossOrigin
public class OtpVerifyController extends CotoDelBaseController{

	
	private static final Logger logger = LoggerFactory.getLogger(OtpVerifyController.class);

	@Autowired
	LoginService loginservice;
	
	@Autowired
	TokenGenerationImpl tokengeneration;
	
	@PostMapping(value="/verifyOTP")
	public @ResponseBody String validateLogin(HttpServletResponse response, HttpServletRequest request,
			UserForm userForm, BindingResult result, HttpSession session, Model model,RedirectAttributes redirect) {
			String profileRes=null;
			
				String password = null;
				password= userForm.getPassword1()+userForm.getPassword2()+userForm.getPassword3()+userForm.getPassword4()+userForm.getPassword5()+userForm.getPassword6();      
				profileRes =loginservice.verifyVoucherIssueOTP(tokengeneration.getToken(),userForm.getUserName(),userForm.getMob(),password,userForm.getOrderId());
			
			return profileRes;
		}

}
