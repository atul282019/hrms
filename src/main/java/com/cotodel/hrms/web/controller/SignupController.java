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
import com.cotodel.hrms.web.response.EmployerDetailsRequest;
import com.cotodel.hrms.web.response.SMSRequest;
import com.cotodel.hrms.web.response.UserWaitList;
import com.cotodel.hrms.web.response.WhatsAppRequest;
import com.cotodel.hrms.web.service.ErupiVoucherCreateDetailsService;
import com.cotodel.hrms.web.service.LoginService;
import com.cotodel.hrms.web.service.SingleUserCreationService;
import com.cotodel.hrms.web.service.Impl.EmailServiceImpl;
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
	
	@Autowired
	EmailServiceImpl emailService;
	
	@Autowired
	LoginService loginservice;
	@Autowired
	ErupiVoucherCreateDetailsService erupiVoucherCreateDetailsService;
	
	@PostMapping(value="/registerUser")
	public @ResponseBody String registerUser(HttpServletRequest request, EmployerDetailsRequest userForm) {
	    String profileRes = null;
	    JSONObject profileJsonRes = null;
	    // String captchaSecurity = "";
	    JSONObject responseJson = new JSONObject();

	    // captchaSecurity = (String) session.getAttribute("CAPTCHA");
	    // if(request.getSession(true).getAttribute("CAPTCHA") != null){
	    //     captchaSecurity = (String) request.getSession(true).getAttribute("CAPTCHA");
	    // }

	    // logger.info("Session Captcha==" + captchaSecurity);
	    // logger.info("User Enter Captcha==" + userForm.getCaptcha());

	    try {
	        // if (validateCaptcha(request, userForm.getCaptcha(), captchaSecurity)) {
	        // --- CAPTCHA check is disabled, proceeding without validation ---

	        // 1 - convert object to json string
	        String json = EncryptionDecriptionUtil.convertToJson(userForm);

	        // 2 - json string data encrypt
	        EncriptResponse jsonObject = EncryptionDecriptionUtil.encriptResponse(json, applicationConstantConfig.apiSignaturePublicPath);

	        String encriptResponse = usercreationService.singleUserCreationEncript(tokengeneration.getToken(), jsonObject);

	        // 3 - decrypt data convert to object            
	        EncriptResponse userReqEnc = EncryptionDecriptionUtil.convertFromJson(encriptResponse, EncriptResponse.class);

	        // 4 - object data decrypt to json
	        profileRes = EncryptionDecriptionUtil.decriptResponse(userReqEnc.getEncriptData(), userReqEnc.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
	        profileJsonRes = new JSONObject(profileRes);

	        if (profileJsonRes.getBoolean("status")) {
	        	// Start SMS and Email service
	        	SMSRequest smsRequest = new SMSRequest();
	        	smsRequest.setMobile(userForm.getMobile());
	        	
	        	String template = "Hi, #VAR1# has added you to the Cotodel Dashboard to manage your business expenses! Log in to Cotodel now to access UPI Vouchers.";
	        	String finalMessage = template
	        	        .replace("#VAR1#", userForm.getName());
	        	       // .replace("#VAR2#", voucherCode);
	        	
	        	smsRequest.setMessage(finalMessage);
	            try {
	            String userFormjson = EncryptionDecriptionUtil.convertToJson(smsRequest);

				EncriptResponse userFormjsonObject=EncryptionDecriptionUtil.encriptResponse(userFormjson, applicationConstantConfig.apiSignaturePublicPath);

				String userFormResponse = loginservice.sendTransactionalSMS(tokengeneration.getToken(), userFormjsonObject);
	   
				EncriptResponse userFornReqEnc =EncryptionDecriptionUtil.convertFromJson(userFormResponse, EncriptResponse.class);

				String smsResponse =  EncryptionDecriptionUtil.decriptResponse(userFornReqEnc.getEncriptData(), userFornReqEnc.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
				String emailRequest =	emailService.sendEmail(userForm.getEmail());
	            } catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	            try {
	            	WhatsAppRequest whatsapp = new WhatsAppRequest();
	                whatsapp.setSource("new-landing-page form");
	                whatsapp.setCampaignName("Sign Up Confirmation - Schedule Demo");
	                whatsapp.setFirstName(userForm.getName());
	                //whatsapp.setAmount(Integer.toString(root.data.order.order_amount));
	                //whatsapp.setCategory(item.getVoucherDesc());
	                whatsapp.setMobile(userForm.getMobile());
	                whatsapp.setOrganizationName("Cotodel");
	                //whatsapp.setValidity(item.getValidity());
	                //whatsapp.setType(item.getRedemtionType());
	                whatsapp.setUserName("Cotodel Communications");
	    			String whatsappJson = EncryptionDecriptionUtil.convertToJson(whatsapp);

	    			EncriptResponse whatsappJsonObject=EncryptionDecriptionUtil.encriptResponse(whatsappJson, applicationConstantConfig.apiSignaturePublicPath);

	    			String whatsappEncriptResponse =  erupiVoucherCreateDetailsService.sendWhatsupMessage(tokengeneration.getToken(), whatsappJsonObject);
	       
	    			EncriptResponse whatsappReqEnc =EncryptionDecriptionUtil.convertFromJson(whatsappEncriptResponse, EncriptResponse.class);

	    			String whatsappRes =  EncryptionDecriptionUtil.decriptResponse(whatsappReqEnc.getEncriptData(), whatsappReqEnc.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
	    		} catch (Exception e) {
	    			// TODO Auto-generated catch block
	    			e.printStackTrace();
	    		}
	            // loginservice.sendEmailToEmployee(userForm);
	        }

	        return profileRes;

	        // } else {
	        //     responseJson.put("status", false);
	        //     responseJson.put("message", "Wrong captcha entered");
	        //     System.out.println("Inside wrong captcha");
	        // }

	    } catch (Exception e) {
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
