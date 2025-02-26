package com.cotodel.hrms.web.controller;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cotodel.hrms.web.properties.ApplicationConstantConfig;
import com.cotodel.hrms.web.response.CaptchaSession;
import com.cotodel.hrms.web.response.CompanyProfileDetail;
import com.cotodel.hrms.web.response.UserRegistrationRequest;
import com.cotodel.hrms.web.response.UserWaitList;
import com.cotodel.hrms.web.service.SingleUserCreationService;
import com.cotodel.hrms.web.service.Impl.TokenGenerationImpl;
import com.cotodel.hrms.web.util.EncriptResponse;
import com.cotodel.hrms.web.util.EncryptionDecriptionUtil;


@Controller
@CrossOrigin
public class SignupController  extends CotoDelBaseController{
	private Map<String, Boolean> captcaValidationMap = new HashMap<String, Boolean>();
	HashMap<String, Boolean> captchaMap = new HashMap<String, Boolean>();
	CaptchaSession csotp = new CaptchaSession();
	
	private static final Logger logger = LoggerFactory.getLogger(SignupController.class);

	@Autowired
	public ApplicationConstantConfig applicationConstantConfig;

	@Autowired
	SingleUserCreationService usercreationService;
	
	@Autowired
	TokenGenerationImpl tokengeneration; 
	
	@PostMapping(value="/registerUser")
	public @ResponseBody String registerUser(HttpServletRequest request,UserRegistrationRequest userForm) {
		String profileRes=null;
		JSONObject profileJsonRes=null;
		String captchaSecurity="";
		JSONObject responseJson = new JSONObject();
		
		captchaSecurity = (String) session.getAttribute("CAPTCHA");
		if(request.getSession(true).getAttribute("CAPTCHA")!=null){
			captchaSecurity=(String) request.getSession(true).getAttribute("CAPTCHA");
		}
		logger.info("Session Captcha=="+captchaSecurity);
		logger.info("User Enter Captcha=="+userForm.getcaptcha());
		try {
		if (validateCaptcha(request, userForm.getcaptcha(),captchaSecurity)) {
			//1-convert object to json string
            String json = EncryptionDecriptionUtil.convertToJson(userForm);
            //2-json string data encript
            EncriptResponse jsonObject=EncryptionDecriptionUtil.encriptResponse(json, applicationConstantConfig.apiSignaturePublicPath);
		
            String encriptResponse = usercreationService.singleUserCreationEncript(tokengeneration.getToken(),jsonObject);
            //3-decript data convert to object            
            EncriptResponse userReqEnc =EncryptionDecriptionUtil.convertFromJson(encriptResponse, EncriptResponse.class);
            //4-object data to decript to json
            profileRes=EncryptionDecriptionUtil.decriptResponse(userReqEnc.getEncriptData(), userReqEnc.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
            profileJsonRes= new JSONObject(profileRes);		
		if(profileJsonRes.getBoolean("status")) { 
		//loginservice.sendEmailToEmployee(userForm);
			
		}
		return profileRes;
		}
		else {
			responseJson.put("status", false);
			responseJson.put("message", "Wrong captcha entered");
			
			System.out.println("Inside wrong captcha");
		}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			responseJson.put("status", false);
	        responseJson.put("message", "An error occurred while processing the request");
		}
		return responseJson.toString();
		
	}

	@PostMapping(value="/userWaitList")
	public @ResponseBody String userWaitList(HttpServletRequest request, ModelMap model,Locale locale,
			HttpSession session,UserWaitList userWaitList) {
			String profileRes=null;
		
		//profileRes = usercreationService.userWaitList(tokengeneration.getToken(),userWaitList);
		
		try {
			String json = EncryptionDecriptionUtil.convertToJson(userWaitList);

			EncriptResponse jsonObject=EncryptionDecriptionUtil.encriptResponse(json, applicationConstantConfig.apiSignaturePublicPath);

			String encriptResponse = usercreationService.userWaitList(tokengeneration.getToken(), jsonObject);

   
			EncriptResponse userReqEnc =EncryptionDecriptionUtil.convertFromJson(encriptResponse, EncriptResponse.class);

			profileRes =  EncryptionDecriptionUtil.decriptResponse(userReqEnc.getEncriptData(), userReqEnc.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return profileRes;
		
	
	}
	@SuppressWarnings("unchecked")
	protected boolean validateCaptcha(HttpServletRequest request, String captcha,String sessioncaptcha) throws Exception {
		String captchaId =sessioncaptcha;
 
		// HashMap<String, Boolean> captchaMap = new HashMap<String, Boolean>();
		captchaMap = (HashMap<String, Boolean>) request.getSession().getAttribute("capchaValidatedMap");
		logger.debug("Captcha validation done for " + captchaId);
		
			if (null == captchaMap || !captchaMap.containsKey(captchaId)) {
				if (captcha != null && !"".equals(captcha.trim())) {
					if (captcha.equals(captchaId)) {
						captcaValidationMap.put(captchaId, false);
						request.getSession().setAttribute("capchaValidatedMap", captcaValidationMap);
						logger.debug("Captcha is set in session  : :" + captchaId);
						csotp.setCaptchaValidated(true);
						return true; // invalid captcha
					} else {
						logger.error("Captcha is already set in session  : :" + captchaId);
						csotp.setCaptchaValidated(false);
						return false; // valid captcha
					}
				}
			} else {
				logger.error("Captcha is already set in session  : :: :" + captchaId);
				csotp.setCaptchaValidated(false);
				return false;
			}
			logger.error("Captcha is already set in session or captchaMap is not null : ::::::: :" + captchaId);
			csotp.setCaptchaValidated(false);
			return false;
		
	}
	
	@GetMapping(value="/getuserWaitList")
	public @ResponseBody String getuserWaitList(HttpServletRequest request,UserWaitList userWaitList) {
		String profileRes=null;JSONObject profileJsonRes=null;
		
		//profileRes = usercreationService.userWaitList(tokengeneration.getToken(),userWaitList);
		
		try {
			String json = EncryptionDecriptionUtil.convertToJson(userWaitList);

			EncriptResponse jsonObject=EncryptionDecriptionUtil.encriptResponse(json, applicationConstantConfig.apiSignaturePublicPath);

			String encriptResponse = usercreationService.getuserWaitList(tokengeneration.getToken(), jsonObject);

   
			EncriptResponse userReqEnc =EncryptionDecriptionUtil.convertFromJson(encriptResponse, EncriptResponse.class);

			profileRes =  EncryptionDecriptionUtil.decriptResponse(userReqEnc.getEncriptData(), userReqEnc.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return profileRes;
		
	
	}
	
	@PostMapping(value="/updateuserWaitList")
	public @ResponseBody String updateuserWaitList(HttpServletRequest request,UserWaitList userWaitList) {
		String profileRes=null;JSONObject profileJsonRes=null;
		
		//profileRes = usercreationService.userWaitList(tokengeneration.getToken(),userWaitList);
		
		try {
			String json = EncryptionDecriptionUtil.convertToJson(userWaitList);

			EncriptResponse jsonObject=EncryptionDecriptionUtil.encriptResponse(json, applicationConstantConfig.apiSignaturePublicPath);

			String encriptResponse = usercreationService.updateuserWaitList(tokengeneration.getToken(), jsonObject);

   
			EncriptResponse userReqEnc =EncryptionDecriptionUtil.convertFromJson(encriptResponse, EncriptResponse.class);

			profileRes =  EncryptionDecriptionUtil.decriptResponse(userReqEnc.getEncriptData(), userReqEnc.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return profileRes;
		
	
	}
	
}
