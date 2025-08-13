package com.cotodel.hrms.web.controller;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cotodel.hrms.web.properties.ApplicationConstantConfig;
import com.cotodel.hrms.web.response.UserForm;
import com.cotodel.hrms.web.service.LoginService;
import com.cotodel.hrms.web.service.Impl.TokenGenerationImpl;
import com.cotodel.hrms.web.util.EncriptResponse;
import com.cotodel.hrms.web.util.EncryptionDecriptionUtil;

@Controller
@CrossOrigin
public class OtpSenderController extends CotoDelBaseController{
	
	private static final Logger logger = LoggerFactory.getLogger(OtpSenderController.class);
	
	@Autowired
	LoginService loginservice;
	
	@Autowired
	TokenGenerationImpl tokengeneration;
	
	@Autowired
	public ApplicationConstantConfig applicationConstantConfig;
	
	@PostMapping(value="/smsOtpSender")
	public @ResponseBody String sendOtp(HttpServletRequest request,@ModelAttribute("userForm") UserForm userForm,Locale locale,Model model) {
		String profileRes=null;

		try {
			String json = EncryptionDecriptionUtil.convertToJson(userForm);

			EncriptResponse jsonObject=EncryptionDecriptionUtil.encriptResponse(json, applicationConstantConfig.apiSignaturePublicPath);

			String encriptResponse = loginservice.sendOtp(tokengeneration.getToken(), jsonObject);

   
			EncriptResponse userReqEnc =EncryptionDecriptionUtil.convertFromJson(encriptResponse, EncriptResponse.class);

			profileRes =  EncryptionDecriptionUtil.decriptResponse(userReqEnc.getEncriptData(), userReqEnc.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return profileRes;
	}	
	
	@PostMapping(value="/smsOtpSenderWithTemplate")
	public @ResponseBody String sendOtpWith2Factor(HttpServletRequest request,@ModelAttribute("userForm") UserForm userForm,Locale locale,Model model) {
		String profileRes=null;

		try {
			//String encriptResponse = loginservice.sendOtpWith2Factor(tokengeneration.getToken(), userForm);
			String json = EncryptionDecriptionUtil.convertToJson(userForm);

			EncriptResponse jsonObject=EncryptionDecriptionUtil.encriptResponse(json, applicationConstantConfig.apiSignaturePublicPath);

			String encriptResponse = loginservice.sendOtpWith2Factor(tokengeneration.getToken(), jsonObject);

   
			EncriptResponse userReqEnc =EncryptionDecriptionUtil.convertFromJson(encriptResponse, EncriptResponse.class);

			profileRes =  EncryptionDecriptionUtil.decriptResponse(userReqEnc.getEncriptData(), userReqEnc.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return profileRes;
	}	
	
	@PostMapping(value="/otpVerifyWithTemplate")
	public @ResponseBody String otpVerifyWithTemplate(HttpServletRequest request,@ModelAttribute("userForm") UserForm userForm,Locale locale,Model model) {
		String profileRes=null;
		String otp= userForm.getPassword1()+userForm.getPassword2()+userForm.getPassword3()+userForm.getPassword4()+userForm.getPassword5()+userForm.getPassword6();
		 userForm.setOtp(otp);
		try {
			String json = EncryptionDecriptionUtil.convertToJson(userForm);

			EncriptResponse jsonObject=EncryptionDecriptionUtil.encriptResponse(json, applicationConstantConfig.apiSignaturePublicPath);

			String encriptResponse = loginservice.otpVerifyWithTemplate(tokengeneration.getToken(), jsonObject);

   
			EncriptResponse userReqEnc =EncryptionDecriptionUtil.convertFromJson(encriptResponse, EncriptResponse.class);

			profileRes =  EncryptionDecriptionUtil.decriptResponse(userReqEnc.getEncriptData(), userReqEnc.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return profileRes;
	}	
	
	@PostMapping(value="/otpWithOutRegister")
	public @ResponseBody String otpWithOutRegister(HttpServletRequest request,@ModelAttribute("userForm") UserForm userForm,Locale locale,Model model) {
		String profileRes=null;

		try {
			String json = EncryptionDecriptionUtil.convertToJson(userForm);

			EncriptResponse jsonObject=EncryptionDecriptionUtil.encriptResponse(json, applicationConstantConfig.apiSignaturePublicPath);

			String encriptResponse = loginservice.otpWithOutRegister(tokengeneration.getToken(), jsonObject);

   
			EncriptResponse userReqEnc =EncryptionDecriptionUtil.convertFromJson(encriptResponse, EncriptResponse.class);

			profileRes =  EncryptionDecriptionUtil.decriptResponse(userReqEnc.getEncriptData(), userReqEnc.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return profileRes;
	}	
	
	@PostMapping(value="/smsOtpResender")
	public @ResponseBody String resendOTP(HttpServletRequest request,@ModelAttribute("userForm") UserForm userForm,Locale locale,Model model) {
		String profileRes=null;
		try {
				String json = EncryptionDecriptionUtil.convertToJson(userForm);

				EncriptResponse jsonObject=EncryptionDecriptionUtil.encriptResponse(json, applicationConstantConfig.apiSignaturePublicPath);

				String encriptResponse = loginservice.resendOtp(tokengeneration.getToken(), jsonObject);

	   
				EncriptResponse userReqEnc =EncryptionDecriptionUtil.convertFromJson(encriptResponse, EncriptResponse.class);

				profileRes =  EncryptionDecriptionUtil.decriptResponse(userReqEnc.getEncriptData(), userReqEnc.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return profileRes;
	}
}
