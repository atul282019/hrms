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
				
				bodyText = "<!doctype html>\r\n"
    					+ "<html lang=\"en\">\r\n"
    					+ "  <head>\r\n"
    					+ "    <meta charset=\"utf-8\" />\r\n"
    					+ "    <title>Cotodel – Welcome</title>\r\n"
    					+ "    <style>\r\n"
    					+ "      body {\r\n"
    					+ "        margin: 0;\r\n"
    					+ "        padding: 0;\r\n"
    					+ "        background: #ffffff;\r\n"
    					+ "        font-family: Arial, Helvetica, sans-serif;\r\n"
    					+ "        color: #1d2b21;\r\n"
    					+ "      }\r\n"
    					+ "      table { border-spacing: 0; border-collapse: collapse; width: 100%; }\r\n"
    					+ "      img { border: 0; display: block; max-width: 100%; }\r\n"
    					+ "      .container { max-width: 600px; margin: 0 auto; }\r\n"
    					+ "      .hero-bar {\r\n"
    					+ "        background: #2b8a56;\r\n"
    					+ "        color: #ffffff;\r\n"
    					+ "      }\r\n"
    					+ "      .tagline { font-size: 14px; opacity: .9; margin-top: 6px; }\r\n"
    					+ "      .card {\r\n"
    					+ "        background: #f1f6f3;\r\n"
    					+ "        border-radius: 12px;\r\n"
    					+ "        overflow: hidden;\r\n"
    					+ "        margin: 20px 0;\r\n"
    					+ "      }\r\n"
    					+ "      .content { padding: 20px; font-size: 15px; line-height: 1.6; }\r\n"
    					+ "      h2 { margin: 0 0 15px; font-size: 20px; }\r\n"
    					+ "      .btn {\r\n"
    					+ "        background: #2b8a56;\r\n"
    					+ "        color: #ffffff !important;\r\n"
    					+ "        text-decoration: none;\r\n"
    					+ "        padding: 12px 24px;\r\n"
    					+ "        border-radius: 6px;\r\n"
    					+ "        display: inline-block;\r\n"
    					+ "        font-weight: bold;\r\n"
    					+ "        font-size: 14px;\r\n"
    					+ "      }\r\n"
    					+ "      .footer {\r\n"
    					+ "        background: #f1f6f3;\r\n"
    					+ "        padding: 20px;\r\n"
    					+ "        font-size: 13px;\r\n"
    					+ "        color: #6b7a71;\r\n"
    					+ "      }\r\n"
    					+ "      .footer-logo { margin-bottom: 10px; }\r\n"
    					+ "      .footer hr { border: 0; border-top: 1px solid #ccc; margin: 15px 0; }\r\n"
    					+ "      .footer a { color: #2b8a56; text-decoration: none; }\r\n"
    					+ "      .social a { margin-right: 10px; text-decoration: none; font-size: 16px; color: #2b8a56; }\r\n"
    					+ "    </style>\r\n"
    					+ "  </head>\r\n"
    					+ "  <body>\r\n"
    					+ "    <table role=\"presentation\" width=\"100%\">\r\n"
    					+ "      <tr>\r\n"
    					+ "        <td>\r\n"
    					+ "          <div class=\"container\">\r\n"
    					+ "\r\n"
    					+ "            <!-- Header with background + side image -->\r\n"
    					+ "            <table role=\"presentation\" width=\"100%\" class=\"hero-bar\">\r\n"
    					+ "              <tr>\r\n"
    					+ "                <td style=\"padding:20px; vertical-align:middle; width:65%;\">\r\n"
    					+ "                  <img src=\"https://staging.cotodel.com/img/emailer_cotodel_without_tagline.png\" alt=\"Cotodel logo\" width=\"160\" height=\"54\" style=\"object-fit:contain;\" />\r\n"
    					+ "                  <p class=\"tagline\">Business expenses made seamless like your personal UPI spends</p>\r\n"
    					+ "                </td>\r\n"
    					+ "                <td style=\"width:35%; text-align:right; vertical-align:bottom;\">\r\n"
    					+ "                  <img src=\"https://staging.cotodel.com/img/Emailer_Scan_to_pay-bro_1.png\" alt=\"Green side illustration\" width=\"200\" style=\"max-width:200px; height:auto;\" />\r\n"
    					+ "                </td>\r\n"
    					+ "              </tr>\r\n"
    					+ "            </table>\r\n"
    					+ "\r\n"
    					+ "            <!-- Illustration -->\r\n"
    					+ "            <div class=\"card\">\r\n"
    					+ "              <img src=\"https://staging.cotodel.com/img/Emailer_corp_signup_img1.png\" alt=\"Cotodel platform welcome illustration\" />\r\n"
    					+ "            </div>\r\n"
    					+ "\r\n"
    					+ "            <!-- Body -->\r\n"
    					+ "            <div class=\"content\">\r\n"
    					+ "              <h2>Welcome, <span style=\"color:#555;\">"+userForm.getName()+"</span>!</h2>\r\n"
    					+ "              <p>You have successfully signed up on Cotodel platform!</p>\r\n"
    					+ "              <p>Our team will get in touch with you soon to activate your account. Start managing your corporate expenses with ease by administering allowances for your employees.</p>\r\n"
    					+ "              <p>If you would want to schedule a demo with our team, please choose any slot as per your convenience.</p>\r\n"
    					+ "\r\n"
    					+ "              <p style=\"text-align:center; margin:20px 0;\">\r\n"
    					+ "                <a href=\"https://calendly.com/business-cotodel/demo\" class=\"btn\">Schedule Demo</a>\r\n"
    					+ "              </p>\r\n"
    					+ "\r\n"
    					+ "              <p style=\"font-size:12px; color:#777; margin-top:20px;\">\r\n"
    					+ "                Disclaimer: Please mark us as <em>'Not Spam'</em> to receive future emails from Cotodel in your inbox\r\n"
    					+ "              </p>\r\n"
    					+ "            </div>\r\n"
    					+ "\r\n"
    					+ "            <!-- Footer -->\r\n"
    					+ "            <div class=\"footer\">\r\n"
    					+ "              <div class=\"footer-logo\">\r\n"
    					+ "                <img src=\"https://staging.cotodel.com/img/Cotodel_Logo.png\" alt=\"Cotodel logo\" width=\"120\" height=\"34\" style=\"object-fit:contain;\" />\r\n"
    					+ "              </div>\r\n"
    					+ "              <div><strong>ADDRESS</strong><br>WeWork, Eldeco Centre, Malviya Nagar New Delhi - 110017</div>\r\n"
    					+ "              <hr>\r\n"
    					+ "              <div>\r\n"
    					+ "                <a href=\"#\">Click to unsubscribe from Marketing emails</a><br><br>\r\n"
    					+ "                © 2025 Cotodel – All rights reserved\r\n"
    					+ "              </div>\r\n"
    					+ "              <div class=\"social\" style=\"margin-top:10px;\">\r\n"
    					+ "	                <a href=\"https://www.linkedin.com/company/cotopay/posts/?feedView=all\" aria-label=\"LinkedIn\">\r\n"
    					+ "	                  <img src=\"https://cdn-icons-png.flaticon.com/512/174/174857.png\" alt=\"LinkedIn\" width=\"24\" height=\"24\" style=\"display:inline-block; margin-right:10px;\">\r\n"
    					+ "	                </a>\r\n"
    					+ "	                <a href=\"https://www.instagram.com/cotodelsolutions\" aria-label=\"Instagram\">\r\n"
    					+ "	                  <img src=\"https://cdn-icons-png.flaticon.com/512/2111/2111463.png\" alt=\"Instagram\" width=\"24\" height=\"24\" style=\"display:inline-block; margin-right:10px;\">\r\n"
    					+ "	                </a>\r\n"
    					+ "	                <a href=\"https://www.youtube.com/@Cotodel\" aria-label=\"YouTube\">\r\n"
    					+ "	                  <img src=\"https://cdn-icons-png.flaticon.com/512/1384/1384060.png\" alt=\"YouTube\" width=\"24\" height=\"24\" style=\"display:inline-block;\">\r\n"
    					+ "	                </a>\r\n"
    					+ "              </div>\r\n"
    					+ "            </div>\r\n"
    					+ "\r\n"
    					+ "          </div>\r\n"
    					+ "        </td>\r\n"
    					+ "      </tr>\r\n"
    					+ "    </table>\r\n"
    					+ "  </body>\r\n"
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

	@PostMapping(value="/registerUserCorporate")
	public @ResponseBody String registerUserCorporate(HttpServletRequest request, EmployerDetailsRequest userForm) {
	    String profileRes = null;
	    JSONObject profileJsonRes = null;
	    // String captchaSecurity = "";
	    JSONObject responseJson = new JSONObject();
	    String subject = null; 
		String bodyText = null;
	   
	    try {
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
	            
	        	try {
	            String userFormjson = EncryptionDecriptionUtil.convertToJson(smsRequest);

				EncriptResponse userFormjsonObject=EncryptionDecriptionUtil.encriptResponse(userFormjson, applicationConstantConfig.apiSignaturePublicPath);

				String userFormResponse = loginservice.sendTransactionalSMS(tokengeneration.getToken(), userFormjsonObject);
	   
				EncriptResponse userFornReqEnc =EncryptionDecriptionUtil.convertFromJson(userFormResponse, EncriptResponse.class);

				String smsResponse =  EncryptionDecriptionUtil.decriptResponse(userFornReqEnc.getEncriptData(), userFornReqEnc.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
				
				bodyText = "<!doctype html>\r\n"
    					+ "<html lang=\"en\">\r\n"
    					+ "  <head>\r\n"
    					+ "    <meta charset=\"utf-8\" />\r\n"
    					+ "    <title>Cotodel – Welcome</title>\r\n"
    					+ "    <style>\r\n"
    					+ "      body {\r\n"
    					+ "        margin: 0;\r\n"
    					+ "        padding: 0;\r\n"
    					+ "        background: #ffffff;\r\n"
    					+ "        font-family: Arial, Helvetica, sans-serif;\r\n"
    					+ "        color: #1d2b21;\r\n"
    					+ "      }\r\n"
    					+ "      table { border-spacing: 0; border-collapse: collapse; width: 100%; }\r\n"
    					+ "      img { border: 0; display: block; max-width: 100%; }\r\n"
    					+ "      .container { max-width: 600px; margin: 0 auto; }\r\n"
    					+ "      .hero-bar {\r\n"
    					+ "        background: #2b8a56;\r\n"
    					+ "        color: #ffffff;\r\n"
    					+ "      }\r\n"
    					+ "      .tagline { font-size: 14px; opacity: .9; margin-top: 6px; }\r\n"
    					+ "      .card {\r\n"
    					+ "        background: #f1f6f3;\r\n"
    					+ "        border-radius: 12px;\r\n"
    					+ "        overflow: hidden;\r\n"
    					+ "        margin: 20px 0;\r\n"
    					+ "      }\r\n"
    					+ "      .content { padding: 20px; font-size: 15px; line-height: 1.6; }\r\n"
    					+ "      h2 { margin: 0 0 15px; font-size: 20px; }\r\n"
    					+ "      .btn {\r\n"
    					+ "        background: #2b8a56;\r\n"
    					+ "        color: #ffffff !important;\r\n"
    					+ "        text-decoration: none;\r\n"
    					+ "        padding: 12px 24px;\r\n"
    					+ "        border-radius: 6px;\r\n"
    					+ "        display: inline-block;\r\n"
    					+ "        font-weight: bold;\r\n"
    					+ "        font-size: 14px;\r\n"
    					+ "      }\r\n"
    					+ "      .footer {\r\n"
    					+ "        background: #f1f6f3;\r\n"
    					+ "        padding: 20px;\r\n"
    					+ "        font-size: 13px;\r\n"
    					+ "        color: #6b7a71;\r\n"
    					+ "      }\r\n"
    					+ "      .footer-logo { margin-bottom: 10px; }\r\n"
    					+ "      .footer hr { border: 0; border-top: 1px solid #ccc; margin: 15px 0; }\r\n"
    					+ "      .footer a { color: #2b8a56; text-decoration: none; }\r\n"
    					+ "      .social a { margin-right: 10px; text-decoration: none; font-size: 16px; color: #2b8a56; }\r\n"
    					+ "    </style>\r\n"
    					+ "  </head>\r\n"
    					+ "  <body>\r\n"
    					+ "    <table role=\"presentation\" width=\"100%\">\r\n"
    					+ "      <tr>\r\n"
    					+ "        <td>\r\n"
    					+ "          <div class=\"container\">\r\n"
    					+ "\r\n"
    					+ "            <!-- Header with background + side image -->\r\n"
    					+ "            <table role=\"presentation\" width=\"100%\" class=\"hero-bar\">\r\n"
    					+ "              <tr>\r\n"
    					+ "                <td style=\"padding:20px; vertical-align:middle; width:65%;\">\r\n"
    					+ "                  <img src=\"https://staging.cotodel.com/img/emailer_cotodel_without_tagline.png\" alt=\"Cotodel logo\" width=\"160\" height=\"54\" style=\"object-fit:contain;\" />\r\n"
    					+ "                  <p class=\"tagline\">Business expenses made seamless like your personal UPI spends</p>\r\n"
    					+ "                </td>\r\n"
    					+ "                <td style=\"width:35%; text-align:right; vertical-align:bottom;\">\r\n"
    					+ "                  <img src=\"https://staging.cotodel.com/img/Emailer_Scan_to_pay-bro_1.png\" alt=\"Green side illustration\" width=\"200\" style=\"max-width:200px; height:auto;\" />\r\n"
    					+ "                </td>\r\n"
    					+ "              </tr>\r\n"
    					+ "            </table>\r\n"
    					+ "\r\n"
    					+ "            <!-- Illustration -->\r\n"
    					+ "            <div class=\"card\">\r\n"
    					+ "              <img src=\"https://staging.cotodel.com/img/Emailer_corp_signup_img1.png\" alt=\"Cotodel platform welcome illustration\" />\r\n"
    					+ "            </div>\r\n"
    					+ "\r\n"
    					+ "            <!-- Body -->\r\n"
    					+ "            <div class=\"content\">\r\n"
    					+ "              <h2>Welcome, <span style=\"color:#555;\">"+userForm.getName()+"</span>!</h2>\r\n"
    					+ "              <p>You have successfully signed up on Cotodel platform!</p>\r\n"
    					+ "              <p>Our team will get in touch with you soon to activate your account. Start managing your corporate expenses with ease by administering allowances for your employees.</p>\r\n"
    					+ "              <p>If you would want to schedule a demo with our team, please choose any slot as per your convenience.</p>\r\n"
    					+ "\r\n"
    					+ "              <p style=\"text-align:center; margin:20px 0;\">\r\n"
    					+ "                <a href=\"https://calendly.com/business-cotodel/demo\" class=\"btn\">Schedule Demo</a>\r\n"
    					+ "              </p>\r\n"
    					+ "\r\n"
    					+ "              <p style=\"font-size:12px; color:#777; margin-top:20px;\">\r\n"
    					+ "                Disclaimer: Please mark us as <em>'Not Spam'</em> to receive future emails from Cotodel in your inbox\r\n"
    					+ "              </p>\r\n"
    					+ "            </div>\r\n"
    					+ "\r\n"
    					+ "            <!-- Footer -->\r\n"
    					+ "            <div class=\"footer\">\r\n"
    					+ "              <div class=\"footer-logo\">\r\n"
    					+ "                <img src=\"https://staging.cotodel.com/img/Cotodel_Logo.png\" alt=\"Cotodel logo\" width=\"120\" height=\"34\" style=\"object-fit:contain;\" />\r\n"
    					+ "              </div>\r\n"
    					+ "              <div><strong>ADDRESS</strong><br>WeWork, Eldeco Centre, Malviya Nagar New Delhi - 110017</div>\r\n"
    					+ "              <hr>\r\n"
    					+ "              <div>\r\n"
    					+ "                <a href=\"#\">Click to unsubscribe from Marketing emails</a><br><br>\r\n"
    					+ "                © 2025 Cotodel – All rights reserved\r\n"
    					+ "              </div>\r\n"
    					+ "              <div class=\"social\" style=\"margin-top:10px;\">\r\n"
    					+ "	                <a href=\"https://www.linkedin.com/company/cotopay/posts/?feedView=all\" aria-label=\"LinkedIn\">\r\n"
    					+ "	                  <img src=\"https://cdn-icons-png.flaticon.com/512/174/174857.png\" alt=\"LinkedIn\" width=\"24\" height=\"24\" style=\"display:inline-block; margin-right:10px;\">\r\n"
    					+ "	                </a>\r\n"
    					+ "	                <a href=\"https://www.instagram.com/cotodelsolutions\" aria-label=\"Instagram\">\r\n"
    					+ "	                  <img src=\"https://cdn-icons-png.flaticon.com/512/2111/2111463.png\" alt=\"Instagram\" width=\"24\" height=\"24\" style=\"display:inline-block; margin-right:10px;\">\r\n"
    					+ "	                </a>\r\n"
    					+ "	                <a href=\"https://www.youtube.com/@Cotodel\" aria-label=\"YouTube\">\r\n"
    					+ "	                  <img src=\"https://cdn-icons-png.flaticon.com/512/1384/1384060.png\" alt=\"YouTube\" width=\"24\" height=\"24\" style=\"display:inline-block;\">\r\n"
    					+ "	                </a>\r\n"
    					+ "              </div>\r\n"
    					+ "            </div>\r\n"
    					+ "\r\n"
    					+ "          </div>\r\n"
    					+ "        </td>\r\n"
    					+ "      </tr>\r\n"
    					+ "    </table>\r\n"
    					+ "  </body>\r\n"
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
