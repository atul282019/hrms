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
				
				bodyText = "\r\n"
						+ "<!DOCTYPE html>\r\n"
						+ "<html lang=\"en\" xmlns=\"http://www.w3.org/1999/xhtml\">\r\n"
						+ "  <head>\r\n"
						+ "    <meta charset=\"utf-8\" />\r\n"
						+ "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\" />\r\n"
						+ "    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\" />\r\n"
						+ "    <meta name=\"x-apple-disable-message-reformatting\" />\r\n"
						+ "    <meta name=\"format-detection\" content=\"telephone=no,address=no,email=no,date=no,url=no\" />\r\n"
						+ "    <title>Cotodel – Welcome</title>\r\n"
						+ "    <style>\r\n"
						+ "      html, body { margin:0 !important; padding:0 !important; height:100% !important; width:100% !important; }\r\n"
						+ "      /* iOS blue links */\r\n"
						+ "      a[x-apple-data-detectors] { color:inherit !important; text-decoration:none !important; }\r\n"
						+ "      /* Mobile stack */\r\n"
						+ "      @media only screen and (max-width:600px){\r\n"
						+ "        .container{ width:100% !important; max-width:100% !important; }\r\n"
						+ "        .stack{ display:block !important; width:100% !important; }\r\n"
						+ "        .px-24{ padding-left:16px !important; padding-right:16px !important; }\r\n"
						+ "        .py-24{ padding-top:16px !important; padding-bottom:16px !important; }\r\n"
						+ "        .text-center-sm{ text-align:center !important; }\r\n"
						+ "        .fluid-img{ width:100% !important; height:auto !important; }\r\n"
						+ "      }\r\n"
						+ "    </style>\r\n"
						+ "  </head>\r\n"
						+ "  <body style=\"margin:0; padding:0; background:#ffffff;\">\r\n"
						+ "    <!-- Preheader (hidden) -->\r\n"
						+ "    <div style=\"display:none; font-size:1px; line-height:1px; max-height:0; max-width:0; opacity:0; overflow:hidden; mso-hide:all;\">\r\n"
						+ "      Welcome to Cotodel! You have successfully signed up.\r\n"
						+ "    </div>\r\n"
						+ "\r\n"
						+ "    <center role=\"article\" aria-roledescription=\"email\" lang=\"en\" style=\"width:100%; background:#ffffff;\">\r\n"
						+ "      <table role=\"presentation\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"background:#ffffff;\">\r\n"
						+ "        <tr>\r\n"
						+ "          <td align=\"center\">\r\n"
						+ "            <!-- Outer container -->\r\n"
						+ "            <table role=\"presentation\" width=\"600\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" class=\"container\" style=\"width:600px; max-width:600px;\">\r\n"
						+ "              <!-- Header / Hero Bar -->\r\n"
						+ "              <tr>\r\n"
						+ "                <td style=\"background:#2b8a56; padding:20px 24px;\">\r\n"
						+ "                  <table role=\"presentation\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\r\n"
						+ "                    <tr>\r\n"
						+ "                      <td class=\"stack text-center-sm\" valign=\"middle\" style=\"padding:0 0 10px 0;\">\r\n"
						+ "                        <img src=\"https://staging.cotodel.com/img/emailer_cotodel_without_tagline.png\" width=\"162\" height=\"54\" alt=\"Cotodel\" style=\"display:block; width:162px; height:54px; object-fit:contain; border:0;\" />\r\n"
						+ "                        <div style=\"font:14px/20px -apple-system,Segoe UI,Roboto,Arial,sans-serif; color:#e6f5ea; margin-top:6px;\">\r\n"
						+ "                          Business expenses made seamless like your personal UPI spends\r\n"
						+ "                        </div>\r\n"
						+ "                      </td>\r\n"
						+ "                      <td class=\"stack\" align=\"right\" valign=\"middle\" style=\"padding:0;\">\r\n"
						+ "                        <img src=\"https://staging.cotodel.com/img/Emailer_fleet_topbar.png\" width=\"200\" alt=\"Scan to Pay Illustration\" class=\"fluid-img\" style=\"display:block; border:0; height:auto;\" />\r\n"
						+ "                      </td>\r\n"
						+ "                    </tr>\r\n"
						+ "                  </table>\r\n"
						+ "                </td>\r\n"
						+ "              </tr>\r\n"
						+ "\r\n"
						+ "              <!-- Hero Illustration -->\r\n"
						+ "              <tr>\r\n"
						+ "                <td style=\"background:#ffffff; padding:20px 24px 8px;\" class=\"px-24 py-24\">\r\n"
						+ "                  <img src=\"https://staging.cotodel.com/img/EmailerFleetBankAC.png\" width=\"552\" alt=\"Cotodel platform welcome illustration\" class=\"fluid-img\" style=\"display:block; width:100%; max-width:552px; height:auto; border:0; margin:0 auto;\" />\r\n"
						+ "                </td>\r\n"
						+ "              </tr>\r\n"
						+ "\r\n"
						+ "              <!-- Body Copy & CTA -->\r\n"
						+ "              <tr>\r\n"
						+ "                <td style=\"background:#ffffff; padding:0 24px 8px;\" class=\"px-24\">\r\n"
						+ "                  <div style=\"font:16px/24px -apple-system,Segoe UI,Roboto,Arial,sans-serif; color:#1d2b21;\">\r\n"
						+ "                    <h2 style=\"margin:0 0 8px 0; font:700 22px/28px -apple-system,Segoe UI,Roboto,Arial,sans-serif;\">Welcome, <span style=\"font-weight:500; color:#6b7a71;\">"+userForm.getName()+"</span>!</h2>\r\n"
						+ "                    <p style=\"margin:0 0 10px 0;\">You have successfully signed up on Cotodel platform!</p>\r\n"
						+ "					 <p style=\"margin:0 0 10px 0;\">Our team will get in touch with you soon to activate your account. Start managing your corporate expenses with ease by administering allowances for your employees. \r\n"
						+ "						 \r\n"
						+ "					 </p> \r\n"
						+ "					 <p style=\"margin:0 0 10px 0;\"> If you would want to schedule a demo with our team, please choose any slot as per your convenience. \r\n"
						+ " 					 </p>  \r\n"
						+ "					 \r\n"
						+ "					</div>\r\n"
						+ "\r\n"
						+ "                  <!-- Bulletproof button -->\r\n"
						+ "                  <table role=\"presentation\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" align=\"center\" style=\"margin:0 auto 12px;\">\r\n"
						+ "                    <tr>\r\n"
						+ "                      <td align=\"center\" bgcolor=\"#2b8a56\" style=\"border-radius:10px;\">\r\n"
						+ "                        <a href=\"https://calendly.com/business-cotodel/demo\" style=\"display:inline-block; padding:12px 20px; font:600 14px/18px -apple-system,Segoe UI,Roboto,Arial,sans-serif; color:#ffffff; text-decoration:none; border-radius:10px;\">Schedule Demo</a>\r\n"
						+ "                      </td>\r\n"
						+ "                    </tr>\r\n"
						+ "                  </table>\r\n"
						+ "\r\n"
						+ "                  <div style=\"font:11px/16px -apple-system,Segoe UI,Roboto,Arial,sans-serif; color:#6b7a71; text-align:center; margin-bottom:10px;\">\r\n"
						+ "                    Disclaimer: Please mark us as 'Not Spam' to receive future emails from Cotodel in your inbox\r\n"
						+ "                  </div>\r\n"
						+ "                </td>\r\n"
						+ "              </tr>\r\n"
						+ "\r\n"
						+ "              <!-- Divider -->\r\n"
						+ "              <tr>\r\n"
						+ "                <td style=\"background:#ffffff; padding:0 24px;\" class=\"px-24\">\r\n"
						+ "                  <div style=\"height:1px; background:#e5e7eb; width:100%;\"></div>\r\n"
						+ "                </td>\r\n"
						+ "              </tr>\r\n"
						+ "\r\n"
						+ "              <!-- Footer -->\r\n"
						+ "              <tr>\r\n"
						+ "                <td style=\"background:#EAF4EF; padding:18px 24px 28px;\" class=\"px-24\">\r\n"
						+ "                  <table role=\"presentation\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\r\n"
						+ "                    <tr>\r\n"
						+ "                      <!-- Left visual column -->\r\n"
						+ "                      <td class=\"stack text-center-sm\" valign=\"top\" style=\"width:180px;\">\r\n"
						+ "                        <div style=\"position:relative; width:180px; height:140px; line-height:0; margin-left:-16px;\">\r\n"
						+ "                          <img src=\"https://staging.cotodel.com/img/Graphic.png\" width=\"180\" height=\"120\" alt=\"Cotodel brand strip\" style=\"display:block; width:180px; height:120px; border:0;\" />\r\n"
						+ "                         \r\n"
						+ "                        </div>\r\n"
						+ "                        <div style=\"font:12px/16px -apple-system,Segoe UI,Roboto,Arial,sans-serif; color:#2b8a56; margin-top:6px; text-align:left; margin-left:-16px;\">\r\n"
						+ "                          <a href=\"https://www.cotodel.com\" style=\"color:#2b8a56; text-decoration:none;\">www.cotodel.com</a>\r\n"
						+ "                        </div>\r\n"
						+ "                      </td>\r\n"
						+ "\r\n"
						+ "                      <!-- Content column -->\r\n"
						+ "                      <td class=\"stack\" valign=\"top\" style=\"padding-left:18px;\">\r\n"
						+ "                        <img src=\"https://staging.cotodel.com/img/Cotodel_Logo.png\" width=\"120\" height=\"34\" alt=\"Cotodel\" style=\"display:block; border:0; margin-bottom:8px;\" />\r\n"
						+ "                        <div style=\"font:12px/18px -apple-system,Segoe UI,Roboto,Arial,sans-serif; color:#6b7a71;\">\r\n"
						+ "                          <div style=\"font-weight:600; color:#1d2b21;\">ADDRESS</div>\r\n"
						+ "                          <div>WeWork, Eldeco Centre, Malviya Nagar New Delhi - 110017</div>\r\n"
						+ "                        </div>\r\n"
						+ "\r\n"
						+ "                        <div style=\"height:1px; background:#e5e7eb; width:100%; margin:12px 0;\"></div>\r\n"
						+ "\r\n"
						+ "                        <table role=\"presentation\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\r\n"
						+ "                          <tr>\r\n"
						+ "                            <td class=\"stack text-center-sm\" style=\"font:11px/16px -apple-system,Segoe UI,Roboto,Arial,sans-serif; color:#6b7a71;\">\r\n"
						+ "                              <a href=\"#\" style=\"color:#6b7a71; text-decoration:underline;\">Click to unsubscribe from Marketing emails</a>\r\n"
						+ "                            </td>\r\n"
						+ "                            <td class=\"stack text-center-sm\" align=\"center\" style=\"font:11px/16px -apple-system,Segoe UI,Roboto,Arial,sans-serif; color:#6b7a71;\">\r\n"
						+ "                              © 2025 Cotodel – All rights reserved\r\n"
						+ "                            </td>\r\n"
						+ "                            <td class=\"stack\" align=\"right\" style=\"white-space:nowrap;\">\r\n"
						+ "                              <!-- Use social media images from earlier prompt -->\r\n"
						+ "                              <a href=\"https://www.linkedin.com/company/cotopay/posts/?feedView=all\" style=\"display:inline-block; margin-left:8px; position:relative; top:-2px;\">\r\n"
						+ "                                <img src=\"https://staging.cotodel.com/img/Emailer_Linkedin_Logo.png\" width=\"20\" height=\"20\" alt=\"LinkedIn\" style=\"display:block; border:0;\" />\r\n"
						+ "                              </a>\r\n"
						+ "                              <a href=\"https://www.instagram.com/\" style=\"display:inline-block; margin-left:8px;\">\r\n"
						+ "                                <img src=\"https://www.instagram.com/cotodelsolutions/img/Emailer_Instagram_Logo.png\" width=\"20\" height=\"20\" alt=\"Instagram\" style=\"display:block; border:0;\" />\r\n"
						+ "                              </a>\r\n"
						+ "                              <a href=\"https://www.youtube.com/@Cotodel\" style=\"display:inline-block; margin-left:8px; position:relative; top:2px;\">\r\n"
						+ "                                <img src=\"https://staging.cotodel.com/img/Emailer_YouTube_Logo.png\" width=\"24\" height=\"24\" alt=\"YouTube\" style=\"display:block; border:0;\" />\r\n"
						+ "                              </a>\r\n"
						+ "                            </td>\r\n"
						+ "                          </tr>\r\n"
						+ "                        </table>\r\n"
						+ "                      </td>\r\n"
						+ "                    </tr>\r\n"
						+ "                  </table>\r\n"
						+ "                </td>\r\n"
						+ "              </tr>\r\n"
						+ "\r\n"
						+ "            </table>\r\n"
						+ "          </td>\r\n"
						+ "        </tr>\r\n"
						+ "      </table>\r\n"
						+ "    </center>\r\n"
						+ "  </body>\r\n"
						+ "</html>\r\n"
						+ "\r\n"
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

	@PostMapping(value="/corporateRegistration")
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
