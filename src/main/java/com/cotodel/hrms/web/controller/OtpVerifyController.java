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

import com.cotodel.hrms.web.properties.ApplicationConstantConfig;
import com.cotodel.hrms.web.response.UserForm;
import com.cotodel.hrms.web.service.LoginService;
import com.cotodel.hrms.web.service.Impl.TokenGenerationImpl;
import com.cotodel.hrms.web.util.EncriptResponse;
import com.cotodel.hrms.web.util.EncryptionDecriptionUtil;

@Controller
@CrossOrigin
public class OtpVerifyController extends CotoDelBaseController{

	
	private static final Logger logger = LoggerFactory.getLogger(OtpVerifyController.class);

	@Autowired
	LoginService loginservice;
	
	@Autowired
	TokenGenerationImpl tokengeneration;
	
	@Autowired
	public ApplicationConstantConfig applicationConstantConfig;
	
	@PostMapping(value="/verifyOTP")
	public @ResponseBody String validateLogin(HttpServletResponse response, HttpServletRequest request,
			UserForm userForm, BindingResult result, HttpSession session, Model model,RedirectAttributes redirect) {
			String profileRes=null;
			
				String password = null;
				password= userForm.getPassword1()+userForm.getPassword2()+userForm.getPassword3()+userForm.getPassword4()+userForm.getPassword5()+userForm.getPassword6();      
				userForm.setOtp(password);
				userForm.setPassword(password);
				try {
					String json = EncryptionDecriptionUtil.convertToJson(userForm);

					EncriptResponse jsonObject=EncryptionDecriptionUtil.encriptResponse(json, applicationConstantConfig.apiSignaturePublicPath);

					String encriptResponse = loginservice.verifyVoucherIssueOTP(tokengeneration.getToken(), jsonObject);

		   
					EncriptResponse userReqEnc =EncryptionDecriptionUtil.convertFromJson(encriptResponse, EncriptResponse.class);

					profileRes =  EncryptionDecriptionUtil.decriptResponse(userReqEnc.getEncriptData(), userReqEnc.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			return profileRes;
		}

}
