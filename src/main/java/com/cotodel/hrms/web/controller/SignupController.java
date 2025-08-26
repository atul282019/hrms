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
import com.cotodel.hrms.web.util.GraphMailSender;


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
	    String subject = null; 
		String bodyText = null;
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
	        	smsRequest.setMessage("Hi, your Cotodel account has been successfully created for your business. Let’s reduce pilferages for your operational spends!");
	            
	        	//String template = "Hi, #VAR1# has added you to the Cotodel Dashboard to manage your business expenses! Log in to Cotodel now to access UPI Vouchers.";
	        	//String finalMessage = template
	        	//        .replace("#VAR1#", userForm.getName());
	        	       // .replace("#VAR2#", voucherCode);
	        	
	        	//smsRequest.setMessage(finalMessage);
	        	
	        	try {
	            String userFormjson = EncryptionDecriptionUtil.convertToJson(smsRequest);

				EncriptResponse userFormjsonObject=EncryptionDecriptionUtil.encriptResponse(userFormjson, applicationConstantConfig.apiSignaturePublicPath);

				String userFormResponse = loginservice.sendTransactionalSMS(tokengeneration.getToken(), userFormjsonObject);
	   
				EncriptResponse userFornReqEnc =EncryptionDecriptionUtil.convertFromJson(userFormResponse, EncriptResponse.class);

				String smsResponse =  EncryptionDecriptionUtil.decriptResponse(userFornReqEnc.getEncriptData(), userFornReqEnc.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
				
				bodyText = "<!DOCTYPE html>\r\n"
    					+ "<html lang=\"en\">\r\n"
    					+ "<head>\r\n"
    					+ "  <meta charset=\"UTF-8\">\r\n"
    					+ "  <title>Welcome to Cotodel</title>\r\n"
    					+ "</head>\r\n"
    					+ "<body style=\"margin:0; padding:0; font-family: Arial, sans-serif; background-color:#f5f5f5;\">\r\n"
    					+ "  <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" bgcolor=\"#f5f5f5\">\r\n"
    					+ "    <tr>\r\n"
    					+ "      <td align=\"center\">\r\n"
    					+ "        <!-- Main Container -->\r\n"
    					+ "        <table width=\"600\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" style=\"background-color:#ffffff; border-radius:8px; overflow:hidden;\">\r\n"
    					+ "          \r\n"
    					+ "          <!-- Header -->\r\n"
    					+ "          <tr>\r\n"
    					+ "            <td bgcolor=\"#0a8f64\" style=\"padding:20px; color:#ffffff; font-size:18px; font-weight:bold;\">\r\n"
    					+ "              <img src=\"https://staging.cotodel.com/img/Hero-Image.png\" alt=\"Cotodel\" style=\"height:32px; vertical-align:middle;\"> \r\n"
    					+ "              <span style=\"margin-left:10px; font-size:16px; font-weight:normal;\">Business expenses made seamless like your personal UPI spends</span>\r\n"
    					+ "            </td>\r\n"
    					+ "          </tr>\r\n"
    					+ "          \r\n"
    					+ "          <!-- Banner Image -->\r\n"
    					+ "          <tr>\r\n"
    					+ "            <td align=\"center\" style=\"padding:20px;\">\r\n"
    					+ "              <img src=\"https://staging.cotodel.com/img/welcome-illustration.png\" alt=\"Welcome\" width=\"100%\" style=\"max-width:560px; border-radius:6px;\">\r\n"
    					+ "            </td>\r\n"
    					+ "          </tr>\r\n"
    					+ "          \r\n"
    					+ "          <!-- Content -->\r\n"
    					+ "          <tr>\r\n"
    					+ "            <td style=\"padding:20px; color:#333333; font-size:14px; line-height:22px;\">\r\n"
    					+ "              <h2 style=\"margin:0; font-size:18px; color:#0a8f64;\">Welcome," +session.getAttribute("organizationName")+ "!</h2>\r\n"
    					+ "              <p>Your organization has onboarded you to Cotodel. Start managing your business expenses as seamlessly as your personal UPI spends!</p>\r\n"
    					+ "              <p>If you have any concerns, please reach out to your Business admin or contact us at \r\n"
    					+ "                <a href=\"mailto:support@cotodel.com\" style=\"color:#0a8f64; text-decoration:none;\">support@cotodel.com</a>.\r\n"
    					+ "              </p>\r\n"
    					+ "              <p>Sincerely,<br>Team Cotodel</p>\r\n"
    					+ "              <!-- CTA Button -->\r\n"
    					+ "              <p style=\"text-align:center; margin:30px 0;\">\r\n"
    					+ "                <a href=\"https://www.cotodel.com/login\" \r\n"
    					+ "                   style=\"background-color:#0a8f64; color:#ffffff; padding:12px 28px; \r\n"
    					+ "                          text-decoration:none; border-radius:6px; font-weight:bold;\">\r\n"
    					+ "                  Log In to Cotodel\r\n"
    					+ "                </a>\r\n"
    					+ "              </p>\r\n"
    					+ "            </td>\r\n"
    					+ "          </tr>\r\n"
    					+ "          \r\n"
    					+ "          <!-- Footer -->\r\n"
    					+ "          <tr>\r\n"
    					+ "            <td bgcolor=\"#f0f0f0\" style=\"padding:20px; text-align:center; font-size:12px; color:#666666;\">\r\n"
    					+ "              <img src=\"https://staging.cotodel.com/img/cotodel_logo_New.svg\" alt=\"Cotodel\" style=\"height:28px; margin-bottom:10px;\"><br>\r\n"
    					+ "              ADDRESS: WeWork, Eldeco Centre, Nehru Place, New Delhi - 110017<br><br>\r\n"
    					+ "              <a href=\"https://www.linkedin.com/company/cotopay/posts/?feedView=all\" style=\"color:#0a8f64; text-decoration:none; margin:0 8px;\">LinkedIn</a> | \r\n"
    					+ "              <a href=\"https://www.instagram.com/cotodelsolutions\" style=\"color:#0a8f64; text-decoration:none; margin:0 8px;\">Instagram</a> | \r\n"
    					+ "              <a href=\"https://x.com/CotodelSolution\" style=\"color:#0a8f64; text-decoration:none; margin:0 8px;\">Twitter</a> | \r\n"
    					+ "              <a href=\"https://www.facebook.com/cotopay/\" style=\"color:#0a8f64; text-decoration:none; margin:0 8px;\">Facebook</a><br><br>\r\n"
    					+ "              © 2025 Cotodel - All rights reserved<br>\r\n"
    					+ "              <a href=\"https://www.cotodel.com/unsubscribe\" style=\"color:#999999; text-decoration:none;\">Click to unsubscribe</a>\r\n"
    					+ "            </td>\r\n"
    					+ "          </tr>\r\n"
    					+ "          \r\n"
    					+ "        </table>\r\n"
    					+ "      </td>\r\n"
    					+ "    </tr>\r\n"
    					+ "  </table>\r\n"
    					+ "</body>\r\n"
    					+ "</html>\r\n"
    					+ "";
				Map<String, Object> response = GraphMailSender.sendMail(GraphMailSender.acquireToken(), "support@cotodel.com",
    					userForm.getEmail(),  "Sign Up Successful",  bodyText);
	            } catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	            try {
	            	WhatsAppRequest whatsapp = new WhatsAppRequest();
	                whatsapp.setSource("new-landing-page form");
	                whatsapp.setCampaignName("250825_SignUp Confirmation");
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
