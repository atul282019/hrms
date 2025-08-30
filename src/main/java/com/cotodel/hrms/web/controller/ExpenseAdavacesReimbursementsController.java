package com.cotodel.hrms.web.controller;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cotodel.hrms.web.properties.ApplicationConstantConfig;
import com.cotodel.hrms.web.response.AdvanceTravelRequest;
import com.cotodel.hrms.web.response.ApprovalTravelReimbursement;
import com.cotodel.hrms.web.response.EmployeeMassterRequest;
import com.cotodel.hrms.web.response.ErupiLinkBankAccount;
import com.cotodel.hrms.web.response.ExpensesReimbursementRequest;
import com.cotodel.hrms.web.response.LinkMultipleAccountRequest;
import com.cotodel.hrms.web.response.Ocr;
import com.cotodel.hrms.web.response.SMSRequest;
import com.cotodel.hrms.web.response.TravelAdvanceRequestUpdate;
import com.cotodel.hrms.web.response.TravelReimbursement;
import com.cotodel.hrms.web.response.TravelRequest;
import com.cotodel.hrms.web.response.UserDetailsEntity;
import com.cotodel.hrms.web.response.WhatsAppRequest;
import com.cotodel.hrms.web.service.ErupiVoucherCreateDetailsService;
import com.cotodel.hrms.web.service.ExpensesReimbursementService;
import com.cotodel.hrms.web.service.LoginService;
import com.cotodel.hrms.web.service.Impl.EmailServiceImpl;
import com.cotodel.hrms.web.service.Impl.TokenGenerationImpl;
import com.cotodel.hrms.web.util.EncriptResponse;
import com.cotodel.hrms.web.util.EncryptionDecriptionUtil;
import com.cotodel.hrms.web.util.GraphMailSender;
import com.cotodel.hrms.web.util.JwtTokenValidator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@CrossOrigin
public class ExpenseAdavacesReimbursementsController extends CotoDelBaseController{
	
	private static final Logger logger = LoggerFactory.getLogger(BulkUserController.class);
	private static final String SECRET_KEY = "0123456789012345"; // Must match frontend
    private static final String CLIENT_KEY = "client-secret-key"; // Extra validation

	
	@Autowired
	public ApplicationConstantConfig applicationConstantConfig;
	
	@Autowired
	TokenGenerationImpl tokengeneration;
	
	@Autowired
	ExpensesReimbursementService expensesReimbursementService;
	
	@Autowired
	LoginService loginservice;
	
	@Autowired
	ErupiVoucherCreateDetailsService erupiVoucherCreateDetailsService;
	
	@Autowired
	EmailServiceImpl emailService;
	
	@PostMapping(value="/addExpenseReimbursementDraft")
	public @ResponseBody String saveExpensesReimbursementDraft(HttpServletRequest request,
		 ExpensesReimbursementRequest expensesReimbursementRequest, BindingResult result, HttpSession session, ModelMap model,Locale locale) {
	
		String profileRes=null;
		
		profileRes = expensesReimbursementService.saveExpensesReimbursementDraft(tokengeneration.getToken(),expensesReimbursementRequest);
		
		  return profileRes;
		  
	}
	
	@PostMapping(value="/addExpenseReimbursement")
	public @ResponseBody String saveExpensesReimbursement(HttpServletRequest request,
		 ExpensesReimbursementRequest expensesReimbursementRequest, BindingResult result, HttpSession session, ModelMap model,Locale locale) {
	
		String profileRes=null;
		
		String receivedHash = expensesReimbursementRequest.getHash();
		 if (!CLIENT_KEY.equals(expensesReimbursementRequest.getClientKey())) {
	          //  return Map.of("isValid", false, "message", "Invalid client key");
	        }
	        // Ensure consistent concatenation
	        String dataString = expensesReimbursementRequest.getEmployerId()+expensesReimbursementRequest.getExpenseCategory()
	        +expensesReimbursementRequest.getDateOfExpense()+expensesReimbursementRequest.getExpenseTitle()+expensesReimbursementRequest.getVendorName()+
	        expensesReimbursementRequest.getInvoiceNumber()+expensesReimbursementRequest.getCurrency()+expensesReimbursementRequest.getAmount()+expensesReimbursementRequest.getModeOfPayment()
	        +expensesReimbursementRequest.getRemarks()+expensesReimbursementRequest.getEmployeeId()+CLIENT_KEY+SECRET_KEY;
	        //+expensesReimbursementRequest.getFileInput()+expensesReimbursementRequest.getFileType()
	       

	        // Compute hash
	        String computedHash = null;
			try {
				computedHash = generateHash(dataString);
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			  boolean isValid = computedHash.equals(receivedHash);
			    Map<String, Object> responseMap = new HashMap<>();

			    ObjectMapper mapper = new ObjectMapper();
			    
			  if (!isValid) {
			        responseMap.put("status", false);
			        responseMap.put("message", "Request Tempered");
			        try {
			            return mapper.writeValueAsString(responseMap);
			        } catch (JsonProcessingException e) {
			            return "{\"status\":false, \"message\":\"JSON processing error\"}";
			        }
			    }
			  
			  String token = (String) session.getAttribute("hrms");
			    
			    if (token == null) {
			        responseMap.put("status", false);
			        responseMap.put("message", "Unauthorized: No token found.");
			        try {
			            return mapper.writeValueAsString(responseMap);
			        } catch (JsonProcessingException e) {
			            return "{\"status\":false, \"message\":\"JSON processing error\"}";
			        }
			    }
			    // Validate Token
			    UserDetailsEntity obj = JwtTokenValidator.parseToken(token);
			    if (obj == null) {
			        responseMap.put("status", false);
			        responseMap.put("message", "Unauthorized: Invalid token.");
			        try {
			            return mapper.writeValueAsString(responseMap);
			        } catch (JsonProcessingException e) {
			            return "{\"status\":false, \"message\":\"JSON processing error\"}";
			        }
			    }
    if ((obj.getUser_role() == 2 || obj.getUser_role() == 1 || obj.getUser_role() == 3 || obj.getUser_role() == 9) && obj.getOrgid() == expensesReimbursementRequest.getEmployerId().intValue()) {
		
		try {
			
			profileRes = expensesReimbursementService.saveExpensesReimbursement(tokengeneration.getToken(),expensesReimbursementRequest);
			
           JSONObject apiJsonResponse = new JSONObject(profileRes);
            
            // Process API Response
            if (apiJsonResponse.getBoolean("status")) {
                responseMap.put("status", true);
                responseMap.put("message", apiJsonResponse.getString("message"));
            } else {
                responseMap.put("status", false);
                responseMap.put("message", apiJsonResponse.getString("message"));
            }
        } catch (Exception e) {
            responseMap.put("status", false);
            responseMap.put("message", "Internal Server Error: " + e.getMessage());
            e.printStackTrace();
        }
    } else {
        responseMap.put("status", false);
        responseMap.put("message", "Unauthorized: Insufficient permissions.");
    }
    try {
        return mapper.writeValueAsString(responseMap);
    } catch (JsonProcessingException e) {
        return "{\"status\":false, \"message\":\"JSON processing error\"}";
    }
}

	
	@GetMapping(value = "/getExpanseReimbursement")
	public @ResponseBody String getExpanseReimbursement(HttpServletRequest request, ModelMap model, Locale locale,
			HttpSession session, ExpensesReimbursementRequest expensesReimbursementRequest) {
		String profileRes = null;
		
		try {
			String json = EncryptionDecriptionUtil.convertToJson(expensesReimbursementRequest);

			EncriptResponse jsonObject=EncryptionDecriptionUtil.encriptResponse(json, applicationConstantConfig.apiSignaturePublicPath);

			String encriptResponse = expensesReimbursementService.getExpanseReimbursement(tokengeneration.getToken(), jsonObject);
  
			EncriptResponse userReqEnc =EncryptionDecriptionUtil.convertFromJson(encriptResponse, EncriptResponse.class);

			profileRes =  EncryptionDecriptionUtil.decriptResponse(userReqEnc.getEncriptData(), userReqEnc.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
   
	return profileRes;
	}
	
	@GetMapping(value = "/getExpanseLimitByExpenseTitleId")
	public @ResponseBody String getExpanseLimitByExpenseTitleId(HttpServletRequest request, ModelMap model, Locale locale,
			HttpSession session, ExpensesReimbursementRequest expensesReimbursementRequest) {
		String profileRes = null;
	
		try {
			String json = EncryptionDecriptionUtil.convertToJson(expensesReimbursementRequest);

			EncriptResponse jsonObject=EncryptionDecriptionUtil.encriptResponse(json, applicationConstantConfig.apiSignaturePublicPath);

			String encriptResponse = expensesReimbursementService.getExpanseLimitByExpenseTitleId(tokengeneration.getToken(), jsonObject);

   
			EncriptResponse userReqEnc =EncryptionDecriptionUtil.convertFromJson(encriptResponse, EncriptResponse.class);

			profileRes =  EncryptionDecriptionUtil.decriptResponse(userReqEnc.getEncriptData(), userReqEnc.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
   
	return profileRes;
	}
	
	@GetMapping(value = "/getExpanseReimbursementApprovalList")
	public @ResponseBody String getExpanseReimbursementApprovalList(HttpServletRequest request, ModelMap model, Locale locale,
			HttpSession session, ExpensesReimbursementRequest expensesReimbursementRequest) {
		String profileRes = null;
		
		try {
			String json = EncryptionDecriptionUtil.convertToJson(expensesReimbursementRequest);

			EncriptResponse jsonObject=EncryptionDecriptionUtil.encriptResponse(json, applicationConstantConfig.apiSignaturePublicPath);

			String encriptResponse = expensesReimbursementService.getExpanseReimbursementApprovalList(tokengeneration.getToken(), jsonObject);
 
			EncriptResponse userReqEnc =EncryptionDecriptionUtil.convertFromJson(encriptResponse, EncriptResponse.class);

			profileRes =  EncryptionDecriptionUtil.decriptResponse(userReqEnc.getEncriptData(), userReqEnc.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
   
	return profileRes;
	}
	
	@PostMapping(value = "/viewExpenseReimbursement")
	public @ResponseBody String viewExpenseReimbursement(HttpServletRequest request, ModelMap model, Locale locale,
			HttpSession session, ExpensesReimbursementRequest expensesReimbursementRequest) {
		String profileRes = null;
		try {
			String json = EncryptionDecriptionUtil.convertToJson(expensesReimbursementRequest);

			EncriptResponse jsonObject=EncryptionDecriptionUtil.encriptResponse(json, applicationConstantConfig.apiSignaturePublicPath);

			String encriptResponse = expensesReimbursementService.viewExpenseReimbursement(tokengeneration.getToken(), jsonObject);
   
			EncriptResponse userReqEnc =EncryptionDecriptionUtil.convertFromJson(encriptResponse, EncriptResponse.class);

			profileRes =  EncryptionDecriptionUtil.decriptResponse(userReqEnc.getEncriptData(), userReqEnc.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
   
	return profileRes;

	}
	
	@PostMapping(value = "/getExpensesReimbursementDetailById")
	public @ResponseBody String getExpensesReimbursementDetailById(HttpServletRequest request, ModelMap model, Locale locale,
			HttpSession session, ExpensesReimbursementRequest expensesReimbursementRequest) {
		String profileRes = null;
	
		try {
			String json = EncryptionDecriptionUtil.convertToJson(expensesReimbursementRequest);

			EncriptResponse jsonObject=EncryptionDecriptionUtil.encriptResponse(json, applicationConstantConfig.apiSignaturePublicPath);

			String encriptResponse = expensesReimbursementService.getExpensesReimbursementDetailById(tokengeneration.getToken(), jsonObject);

   
			EncriptResponse userReqEnc =EncryptionDecriptionUtil.convertFromJson(encriptResponse, EncriptResponse.class);

			profileRes =  EncryptionDecriptionUtil.decriptResponse(userReqEnc.getEncriptData(), userReqEnc.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	   return profileRes;
	}
	@PostMapping(value = "/readOcr")
	public @ResponseBody String readOcr(HttpServletRequest request, ModelMap model, Locale locale,
			HttpSession session, Ocr ocrclass) {
		String profileRes = null;
	
		try {
			profileRes = expensesReimbursementService.readOcr(tokengeneration.getToken(), ocrclass);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	   return profileRes;
	}
	
	
	@PostMapping(value = "/deleteExpanseReimbursement")
	public @ResponseBody String deleteExpanseReimbursement(HttpServletRequest request, ModelMap model, Locale locale,
			HttpSession session, ExpensesReimbursementRequest expensesReimbursementRequest) {
		String profileRes = null;
	
		try {
			String json = EncryptionDecriptionUtil.convertToJson(expensesReimbursementRequest);

			EncriptResponse jsonObject=EncryptionDecriptionUtil.encriptResponse(json, applicationConstantConfig.apiSignaturePublicPath);

			String encriptResponse = expensesReimbursementService.deleteExpanseReimbursement(tokengeneration.getToken(), jsonObject);

			EncriptResponse userReqEnc =EncryptionDecriptionUtil.convertFromJson(encriptResponse, EncriptResponse.class);

			profileRes =  EncryptionDecriptionUtil.decriptResponse(userReqEnc.getEncriptData(), userReqEnc.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
   
	   return profileRes;
	}
	
	@PostMapping(value="/addErupiLinkBankAccount")
	public @ResponseBody String erupiLinkBankAccount(HttpServletRequest request,
			ErupiLinkBankAccount erupiLinkBankAccount, BindingResult result, HttpSession session, ModelMap model,Locale locale) {
	
		String profileRes=null;
		String bodyText = null;
		String email = (String) session.getAttribute("email");
		String receivedHash = erupiLinkBankAccount.getHash();
		 // Validate client key first
        if (!CLIENT_KEY.equals(erupiLinkBankAccount.getClientKey())) {
          //  return Map.of("isValid", false, "message", "Invalid client key");
        }
        // Ensure consistent concatenation
        String dataString = 
        		erupiLinkBankAccount.getOrgId()+
        		erupiLinkBankAccount.getBankName()+
        		erupiLinkBankAccount.getAccountHolderName()+
        		erupiLinkBankAccount.getAcNumber()+
        		erupiLinkBankAccount.getConirmAccNumber()+
        		erupiLinkBankAccount.getAccountType()+
        		erupiLinkBankAccount.getIfsc()+
				erupiLinkBankAccount.getMobile()+
				erupiLinkBankAccount.getMerchentIid()+
				erupiLinkBankAccount.getSubmurchentid()+
				erupiLinkBankAccount.getPayerva()+
				CLIENT_KEY+SECRET_KEY;

        // Compute hash
        String computedHash = null;
		try {
			computedHash = generateHash(dataString);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    // Validate hash
    boolean isValid = computedHash.equals(receivedHash);
    Map<String, Object> responseMap = new HashMap<>();
    ObjectMapper mapper = new ObjectMapper();
 
    // Get token from session
    if (!isValid) {
        responseMap.put("status", false);
        responseMap.put("message", "Request Tempered");
        try {
            return mapper.writeValueAsString(responseMap);
        } catch (JsonProcessingException e) {
            return "{\"status\":false, \"message\":\"JSON processing error\"}";
        }
    }
    String token = (String) session.getAttribute("hrms");
    
    if (token == null) {
        responseMap.put("status", false);
        responseMap.put("message", "Unauthorized: No token found.");
        try {
            return mapper.writeValueAsString(responseMap);
        } catch (JsonProcessingException e) {
            return "{\"status\":false, \"message\":\"JSON processing error\"}";
        }
    }
    // Validate Token
    UserDetailsEntity obj = JwtTokenValidator.parseToken(token);
    if (obj == null) {
        responseMap.put("status", false);
        responseMap.put("message", "Unauthorized: Invalid token.");
        try {
            return mapper.writeValueAsString(responseMap);
        } catch (JsonProcessingException e) {
            return "{\"status\":false, \"message\":\"JSON processing error\"}";
        }
    }

    // Check User Role and Organization ID
    if ((obj.getUser_role() == 9 || obj.getUser_role() == 1 || obj.getUser_role() == 3) && obj.getOrgid() == erupiLinkBankAccount.getOrgId().intValue()) {
        try {
            // Convert request object to JSON
            String json = EncryptionDecriptionUtil.convertToJson(erupiLinkBankAccount);

            // Encrypt Request
            EncriptResponse jsonObject = EncryptionDecriptionUtil.encriptResponse(json, applicationConstantConfig.apiSignaturePublicPath);

            // Call Service
            String encryptedResponse =  expensesReimbursementService.erupiLinkBankAccount(tokengeneration.getToken(),jsonObject);

            // Decrypt Response
            EncriptResponse userReqEnc = EncryptionDecriptionUtil.convertFromJson(encryptedResponse, EncriptResponse.class);
            String apiResponse = EncryptionDecriptionUtil.decriptResponse(
                    userReqEnc.getEncriptData(), 
                    userReqEnc.getEncriptKey(), 
                    applicationConstantConfig.apiSignaturePrivatePath
            );

            JSONObject apiJsonResponse = new JSONObject(apiResponse);
            
            // Process API Response
            if (apiJsonResponse.getBoolean("status")) {
                responseMap.put("status", true);
                responseMap.put("message", apiJsonResponse.getString("message"));
                
    				// Start SMS and Email service
                    SMSRequest userForm = new SMSRequest();
    	            userForm.setMobile((String) session.getAttribute("mobile"));
    	            //userForm.setTemplate("Activation Confirmation");
    	            userForm.setMessage("Congratulations! Your bank account is now live on Cotodel. You can start issuing UPI vouchers. Next step: Onboard your team!");
    	            try {
    	            String userFormjson = EncryptionDecriptionUtil.convertToJson(userForm);

    				EncriptResponse userFormjsonObject=EncryptionDecriptionUtil.encriptResponse(userFormjson, applicationConstantConfig.apiSignaturePublicPath);

    				String userFormResponse = loginservice.sendTransactionalSMS(tokengeneration.getToken(), userFormjsonObject);
    	   
    				EncriptResponse userFornReqEnc =EncryptionDecriptionUtil.convertFromJson(userFormResponse, EncriptResponse.class);

    				String smsResponse =  EncryptionDecriptionUtil.decriptResponse(userFornReqEnc.getEncriptData(), userFornReqEnc.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
    				
    				if (email != null && !email.isEmpty()) {
    				bodyText = "<!DOCTYPE html>\r\n"
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
    						+ "      a[x-apple-data-detectors] { color:inherit !important; text-decoration:none !important; }\r\n"
    						+ "      img { border:0; outline:none; text-decoration:none; }\r\n"
    						+ "      table { border-collapse:collapse; }\r\n"
    						+ "      /* Mobile stack */\r\n"
    						+ "      @media only screen and (max-width:600px){\r\n"
    						+ "        .container{ width:100% !important; max-width:100% !important; }\r\n"
    						+ "        .stack{ display:block !important; width:100% !important; }\r\n"
    						+ "        .px-24{ padding-left:16px !important; padding-right:16px !important; }\r\n"
    						+ "        .py-24{ padding-top:16px !important; padding-bottom:16px !important; }\r\n"
    						+ "        .text-center-sm{ text-align:center !important; }\r\n"
    						+ "        .fluid-img{ width:100% !important; height:auto !important; }\r\n"
    						+ "        .footer-icons{ text-align:center !important; padding-top:8px !important; }\r\n"
    						+ "      }\r\n"
    						+ "    </style>\r\n"
    						+ "  </head>\r\n"
    						+ "  <body style=\"margin:0; padding:0; background:#ffffff;\">\r\n"
    						+ "    <!-- Preheader (hidden) -->\r\n"
    						+ "    <div style=\"display:none; font-size:1px; line-height:1px; max-height:0; max-width:0; opacity:0; overflow:hidden; mso-hide:all;\">\r\n"
    						+ "     \r\n"
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
    						+ "                    <h2 style=\"margin:0 0 8px 0; font:700 22px/28px -apple-system,Segoe UI,Roboto,Arial,sans-serif;\">Great news, <span style=\"font-weight:500; color:#6b7a71;\">"+session.getAttribute("organizationName")+"</span>!</h2>\r\n"
    						+ "                    <p style=\"margin:0 0 10px 0;\">Your Bank Account is now live on the Cotodel platform. You can now onboard your users to start managing your business spends via UPI Vouchers!</p>\r\n"
    						+ "                    <p style=\"margin:0 0 10px 0;\">If you have any concerns, please contact us at \r\n"
    						+ "                      <a href=\"mailto:support@cotodel.com\" style=\"color:#367AFF; text-decoration:none;\">support@cotodel.com</a>\r\n"
    						+ "                    </p>\r\n"
    						+ "                    <p style=\"margin:0 0 10px 0;\">Sincerely,<br>Team Cotodel</p>\r\n"
    						+ "                  </div>\r\n"
    						+ "\r\n"
    						+ "                  <!-- Bulletproof button -->\r\n"
    						+ "                  <table role=\"presentation\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" align=\"center\" style=\"margin:0 auto 12px;\">\r\n"
    						+ "                    <tr>\r\n"
    						+ "                      <td align=\"center\" bgcolor=\"#2b8a56\" style=\"border-radius:10px;\">\r\n"
    						+ "                        <a href=\"https://www.cotodel.com/login\" style=\"display:inline-block; padding:12px 20px; font:600 14px/18px -apple-system,Segoe UI,Roboto,Arial,sans-serif; color:#ffffff; text-decoration:none; border-radius:10px;\">Log In to Cotodel</a>\r\n"
    						+ "                      </td>\r\n"
    						+ "                    </tr>\r\n"
    						+ "                  </table>\r\n"
    						+ "\r\n"
    						+ "                  </td>\r\n"
    						+ "              </tr>\r\n"
    						+ "\r\n"
    						+ "              <!-- Divider -->\r\n"
    						+ "              <tr>\r\n"
    						+ "                <td style=\"background:#ffffff; padding:0 24px;\" class=\"px-24\">\r\n"
    						+ "                  <div style=\"height:1px; background:#e5e7eb; width:100%;\"></div>\r\n"
    						+ "                </td>\r\n"
    						+ "              </tr>\r\n"
    						+ "\r\n"
    						+ "              <!-- Footer (matched to design) -->\r\n"
    						+ "              <tr>\r\n"
    						+ "                <td style=\"background:#ffffff; padding:18px 24px 28px;\" class=\"px-24\">\r\n"
    						+ "                  <div style=\"font:11px/16px -apple-system,Segoe UI,Roboto,Arial,sans-serif; color:#6b7a71; text-align:right; margin:0 0 8px;\">Disclaimer: Please mark us as 'Not Spam' to receive future emails from Cotodel in your inbox</div>\r\n"
    						+ "                  <!-- Logo + address -->\r\n"
    						+ "                  <table role=\"presentation\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\r\n"
    						+ "                    <tr>\r\n"
    						+ "                      <td align=\"left\" style=\"padding:0 0 8px 0;\">\r\n"
    						+ "                        <img src=\"https://staging.cotodel.com/img/Cotodel_Logo.png\" width=\"80\" alt=\"Cotodel\" style=\"display:block; border:0; width:80px; height:auto;\" />\r\n"
    						+ "                      </td>\r\n"
    						+ "                    </tr>\r\n"
    						+ "                    <tr>\r\n"
    						+ "                      <td style=\"font:12px/18px -apple-system,Segoe UI,Roboto,Arial,sans-serif; color:#6b7a71;\">\r\n"
    						+ "                        <div style=\"font-weight:600; color:#1d2b21; letter-spacing:.3px;\">ADDRESS</div>\r\n"
    						+ "                        <div>WeWork, Eldeco Centre, Malviya Nagar New Delhi - 110017</div>\r\n"
    						+ "                      </td>\r\n"
    						+ "                    </tr>\r\n"
    						+ "                  </table>\r\n"
    						+ "\r\n"
    						+ "                  <div style=\"height:1px; background:#e5e7eb; width:100%; margin:12px 0 10px;\"></div>\r\n"
    						+ "\r\n"
    						+ "                  <!-- Bottom bar: copyright + icons -->\r\n"
    						+ "                  <table role=\"presentation\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\r\n"
    						+ "                    <tr>\r\n"
    						+ "                      <td class=\"stack text-center-sm\" style=\"font:11px/16px -apple-system,Segoe UI,Roboto,Arial,sans-serif; color:#6b7a71;\">\r\n"
    						+ "                        © 2025 Cotodel – All rights reserved\r\n"
    						+ "                      </td>\r\n"
    						+ "                      <td class=\"stack footer-icons\" align=\"right\" style=\"white-space:nowrap;\">\r\n"
    						+ "                        <a href=\"https://www.linkedin.com/company/cotopay/posts/?feedView=all/\" style=\"display:inline-block; margin-left:8px;\">\r\n"
    						+ "                          <img src=\"https://staging.cotodel.com/img/Emailer_Linkedin_Logo.png\" width=\"20\" height=\"20\" alt=\"LinkedIn\" style=\"display:block;\" />\r\n"
    						+ "                        </a>\r\n"
    						+ "                        <a href=\"https://www.instagram.com/cotodelsolution/\" style=\"display:inline-block; margin-left:8px;\">\r\n"
    						+ "                          <img src=\"https://staging.cotodel.com/img/Emailer_Instagram_Logo.png\" width=\"20\" height=\"20\" alt=\"Instagram\" style=\"display:block;\" />\r\n"
    						+ "                        </a>\r\n"
    						+ "                        <a href=\"https://www.youtube.com/@Cotodel\" style=\"display:inline-block; margin-left:8px;\">\r\n"
    						+ "                          <img src=\"https://staging.cotodel.com/img/Emailer_YouTube_Logo.png\" width=\"24\" height=\"24\" alt=\"YouTube\" style=\"display:block;\" />\r\n"
    						+ "                        </a>\r\n"
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
    						+ "";
    				Map<String, Object> response = GraphMailSender.sendMail(GraphMailSender.acquireToken(), "support@cotodel.com",
        			email,  "✅ Bank Account Added",  bodyText);
    	            }
    	            } catch (Exception e) {
    				// TODO Auto-generated catch block
    				e.printStackTrace();
    			}
    	            try {
    	            	WhatsAppRequest whatsapp = new WhatsAppRequest();
    	                whatsapp.setSource("new-landing-page form");
    	                whatsapp.setCampaignName("250825_Bank Account Activation");
    	                whatsapp.setFirstName((String) session.getAttribute("organizationName"));
    	                //whatsapp.setAmount(Integer.toString(root.data.order.order_amount));
    	                //whatsapp.setCategory(item.getVoucherDesc());
    	                whatsapp.setMobile((String) session.getAttribute("mobile"));
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
    			
    	         //End Start SMS and Email service
    		
                
                
            } else {
                responseMap.put("status", false);
                responseMap.put("message", apiJsonResponse.getString("message"));
            }

        } catch (Exception e) {
            responseMap.put("status", false);
            responseMap.put("message", "Internal Server Error: " + e.getMessage());
            e.printStackTrace();
        }
    } else {
        responseMap.put("status", false);
        responseMap.put("message", "Unauthorized: Insufficient permissions.");
    }
    try {
        return mapper.writeValueAsString(responseMap);
    } catch (JsonProcessingException e) {
        return "{\"status\":false, \"message\":\"JSON processing error\"}";
    }  
	}
	
	@PostMapping(value="/updateErupiLinkBankAccountStaus")
	public @ResponseBody String updateErupiLinkBankAccountStaus(HttpServletRequest request,
			ErupiLinkBankAccount erupiLinkBankAccount, BindingResult result, HttpSession session, ModelMap model,Locale locale) {
	
			String profileRes=null;
			
			try {
				String json = EncryptionDecriptionUtil.convertToJson(erupiLinkBankAccount);
	
				EncriptResponse jsonObject=EncryptionDecriptionUtil.encriptResponse(json, applicationConstantConfig.apiSignaturePublicPath);
	
				String encriptResponse =   expensesReimbursementService.updateErupiLinkBankAccountStaus(tokengeneration.getToken(), jsonObject);
				
				EncriptResponse userReqEnc =EncryptionDecriptionUtil.convertFromJson(encriptResponse, EncriptResponse.class);
	
				profileRes =  EncryptionDecriptionUtil.decriptResponse(userReqEnc.getEncriptData(), userReqEnc.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	   
	return profileRes;
		  
	}
	
	@PostMapping(value="/getErupiLinkBankAccountDetail")
	public @ResponseBody String getErupiLinkBankAccountDetail(HttpServletRequest request,
			EmployeeMassterRequest erupiLinkBankAccount, BindingResult result, HttpSession session, ModelMap model,Locale locale) {
		
		String profileRes=null;
		
		try {
			String json = EncryptionDecriptionUtil.convertToJson(erupiLinkBankAccount);

			EncriptResponse jsonObject=EncryptionDecriptionUtil.encriptResponse(json, applicationConstantConfig.apiSignaturePublicPath);

			String encriptResponse =  expensesReimbursementService.getErupiLinkBankAccountDetail(tokengeneration.getToken(), jsonObject);

   
			EncriptResponse userReqEnc =EncryptionDecriptionUtil.convertFromJson(encriptResponse, EncriptResponse.class);

			profileRes =  EncryptionDecriptionUtil.decriptResponse(userReqEnc.getEncriptData(), userReqEnc.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
   
	return profileRes;
		
	}
	
	@PostMapping(value="/getErupiLinkDlinkAccountDetail")
	public @ResponseBody String getErupiLinkDlinkAccountDetail(HttpServletRequest request,
			ErupiLinkBankAccount erupiLinkBankAccount, BindingResult result, HttpSession session, ModelMap model,Locale locale) {
	
			String profileRes=null;
			String receivedHash = erupiLinkBankAccount.getHash();
			 if (!CLIENT_KEY.equals(erupiLinkBankAccount.getClientKey())) {
		          // return Map.of("isValid", false, "message", "Invalid client key");
		        }
		        String dataString = erupiLinkBankAccount.getOrgId()+CLIENT_KEY+SECRET_KEY;

		        // Compute hash
		        String computedHash = null;
				try {
					computedHash = generateHash(dataString);
				} catch (NoSuchAlgorithmException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				  boolean isValid = computedHash.equals(receivedHash);
				    Map<String, Object> responseMap = new HashMap<>();
				    ObjectMapper mapper = new ObjectMapper();
				    
				  if (!isValid) {
				        responseMap.put("status", false);
				        responseMap.put("message", "Request Tempered");
				        try {
				            return mapper.writeValueAsString(responseMap);
				        } catch (JsonProcessingException e) {
				            return "{\"status\":false, \"message\":\"JSON processing error\"}";
				        }
				    }
				  
				  String token = (String) session.getAttribute("hrms");
				    
				    if (token == null) {
				        responseMap.put("status", false);
				        responseMap.put("message", "Unauthorized: No token found.");
				        try {
				            return mapper.writeValueAsString(responseMap);
				        } catch (JsonProcessingException e) {
				            return "{\"status\":false, \"message\":\"JSON processing error\"}";
				        }
				    }
				    // Validate Token
				    UserDetailsEntity obj = JwtTokenValidator.parseToken(token);
				    if (obj == null) {
				        responseMap.put("status", false);
				        responseMap.put("message", "Unauthorized: Invalid token.");
				        try {
				            return mapper.writeValueAsString(responseMap);
				        } catch (JsonProcessingException e) {
				            return "{\"status\":false, \"message\":\"JSON processing error\"}";
				        }
				    }
		if ((obj.getUser_role() == 9 || obj.getUser_role() == 1 || obj.getUser_role() == 3) && obj.getOrgid() == erupiLinkBankAccount.getOrgId().intValue()) {
			try {
				String json = EncryptionDecriptionUtil.convertToJson(erupiLinkBankAccount);

				EncriptResponse jsonObject=EncryptionDecriptionUtil.encriptResponse(json, applicationConstantConfig.apiSignaturePublicPath);

				String encriptResponse =  expensesReimbursementService.getErupiLinkDlinkAccountDetail(tokengeneration.getToken(), jsonObject);
	   
				EncriptResponse userReqEnc =EncryptionDecriptionUtil.convertFromJson(encriptResponse, EncriptResponse.class);

				String apiResponse = EncryptionDecriptionUtil.decriptResponse(
	                    userReqEnc.getEncriptData(), 
	                    userReqEnc.getEncriptKey(), 
	                    applicationConstantConfig.apiSignaturePrivatePath
	            );

	            JSONObject apiJsonResponse = new JSONObject(apiResponse);
	            
	            // Process API Response
	            if (apiJsonResponse.getBoolean("status")) {
	                responseMap.put("status", true);
	                responseMap.put("message", apiJsonResponse.getString("message"));
	                responseMap.put("data", apiJsonResponse.getJSONArray("data").toList()); 
	            } else {
	                responseMap.put("status", false);
	                responseMap.put("message", apiJsonResponse.getString("message"));
	            }

	        } catch (Exception e) {
	            responseMap.put("status", false);
	            responseMap.put("message", "Internal Server Error: " + e.getMessage());
	            e.printStackTrace();
	        }
	    } else {
	        responseMap.put("status", false);
	        responseMap.put("message", "Unauthorized: Insufficient permissions.");
	    }
	    try {
	        return mapper.writeValueAsString(responseMap);
	    } catch (JsonProcessingException e) {
	        return "{\"status\":false, \"message\":\"JSON processing error\"}";
	    }
		  
	}
	
	@PostMapping(value="/de-linkErupiaccount")
	public @ResponseBody String delinkErupiaccount(HttpServletRequest request,
			ErupiLinkBankAccount erupiLinkBankAccount, BindingResult result, HttpSession session, ModelMap model,Locale locale) {
	
			String profileRes=null;
			String receivedHash = erupiLinkBankAccount.getHash();
			 if (!CLIENT_KEY.equals(erupiLinkBankAccount.getClientKey())) {
		          //  return Map.of("isValid", false, "message", "Invalid client key");
		        }
		        // Ensure consistent concatenation
		        String dataString = erupiLinkBankAccount.getOrgId()+erupiLinkBankAccount.getAcNumber()+CLIENT_KEY+SECRET_KEY;

		        // Compute hash
		        String computedHash = null;
				try {
					computedHash = generateHash(dataString);
				} catch (NoSuchAlgorithmException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				  boolean isValid = computedHash.equals(receivedHash);
				    Map<String, Object> responseMap = new HashMap<>();
				    ObjectMapper mapper = new ObjectMapper();
				    
				  if (!isValid) {
				        responseMap.put("status", false);
				        responseMap.put("message", "Request Tempered");
				        try {
				            return mapper.writeValueAsString(responseMap);
				        } catch (JsonProcessingException e) {
				            return "{\"status\":false, \"message\":\"JSON processing error\"}";
				        }
				    }
				  
				  String token = (String) session.getAttribute("hrms");
				    
				    if (token == null) {
				        responseMap.put("status", false);
				        responseMap.put("message", "Unauthorized: No token found.");
				        try {
				            return mapper.writeValueAsString(responseMap);
				        } catch (JsonProcessingException e) {
				            return "{\"status\":false, \"message\":\"JSON processing error\"}";
				        }
				    }
				    // Validate Token
				    UserDetailsEntity obj = JwtTokenValidator.parseToken(token);
				    if (obj == null) {
				        responseMap.put("status", false);
				        responseMap.put("message", "Unauthorized: Invalid token.");
				        try {
				            return mapper.writeValueAsString(responseMap);
				        } catch (JsonProcessingException e) {
				            return "{\"status\":false, \"message\":\"JSON processing error\"}";
				        }
				    }
	    if ((obj.getUser_role() == 9 || obj.getUser_role() == 1 || obj.getUser_role() == 3) && obj.getOrgid() == erupiLinkBankAccount.getOrgId().intValue()) {
			
			try {
				String json = EncryptionDecriptionUtil.convertToJson(erupiLinkBankAccount);

				EncriptResponse jsonObject=EncryptionDecriptionUtil.encriptResponse(json, applicationConstantConfig.apiSignaturePublicPath);

				String encriptResponse =  expensesReimbursementService.delinkErupiAccount(tokengeneration.getToken(), jsonObject);
				
				EncriptResponse userReqEnc =EncryptionDecriptionUtil.convertFromJson(encriptResponse, EncriptResponse.class);

				String apiResponse = EncryptionDecriptionUtil.decriptResponse(
	                    userReqEnc.getEncriptData(), 
	                    userReqEnc.getEncriptKey(), 
	                    applicationConstantConfig.apiSignaturePrivatePath
	            );

	            JSONObject apiJsonResponse = new JSONObject(apiResponse);
	            
	            // Process API Response
	            if (apiJsonResponse.getBoolean("status")) {
	                responseMap.put("status", true);
	                responseMap.put("message", apiJsonResponse.getString("message"));
	            } else {
	                responseMap.put("status", false);
	                responseMap.put("message", apiJsonResponse.getString("message"));
	            }

	        } catch (Exception e) {
	            responseMap.put("status", false);
	            responseMap.put("message", "Internal Server Error: " + e.getMessage());
	            e.printStackTrace();
	        }
	    } else {
	        responseMap.put("status", false);
	        responseMap.put("message", "Unauthorized: Insufficient permissions.");
	    }
	    try {
	        return mapper.writeValueAsString(responseMap);
	    } catch (JsonProcessingException e) {
	        return "{\"status\":false, \"message\":\"JSON processing error\"}";
	    }
		  
	}
	@PostMapping(value="/re-linkErupiaccount")
	public @ResponseBody String relinkErupiaccount(HttpServletRequest request,
			ErupiLinkBankAccount erupiLinkBankAccount, BindingResult result, HttpSession session, ModelMap model,Locale locale) {
			
			String receivedHash = erupiLinkBankAccount.getHash();
			 if (!CLIENT_KEY.equals(erupiLinkBankAccount.getClientKey())) {
		          //  return Map.of("isValid", false, "message", "Invalid client key");
		        }
		        // Ensure consistent concatenation
		        String dataString = erupiLinkBankAccount.getOrgId()+erupiLinkBankAccount.getAcNumber()+CLIENT_KEY+SECRET_KEY;

		        // Compute hash
		        String computedHash = null;
				try {
					computedHash = generateHash(dataString);
				} catch (NoSuchAlgorithmException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				  boolean isValid = computedHash.equals(receivedHash);
				    Map<String, Object> responseMap = new HashMap<>();
				    ObjectMapper mapper = new ObjectMapper();
				    
				  if (!isValid) {
				        responseMap.put("status", false);
				        responseMap.put("message", "Request Tempered");
				        try {
				            return mapper.writeValueAsString(responseMap);
				        } catch (JsonProcessingException e) {
				            return "{\"status\":false, \"message\":\"JSON processing error\"}";
				        }
				    }
				  
				  String token = (String) session.getAttribute("hrms");
				    
				    if (token == null) {
				        responseMap.put("status", false);
				        responseMap.put("message", "Unauthorized: No token found.");
				        try {
				            return mapper.writeValueAsString(responseMap);
				        } catch (JsonProcessingException e) {
				            return "{\"status\":false, \"message\":\"JSON processing error\"}";
				        }
				    }
				    // Validate Token
				    UserDetailsEntity obj = JwtTokenValidator.parseToken(token);
				    if (obj == null) {
				        responseMap.put("status", false);
				        responseMap.put("message", "Unauthorized: Invalid token.");
				        try {
				            return mapper.writeValueAsString(responseMap);
				        } catch (JsonProcessingException e) {
				            return "{\"status\":false, \"message\":\"JSON processing error\"}";
				        }
				    }
	    if ((obj.getUser_role() == 9 || obj.getUser_role() == 1 || obj.getUser_role() == 3) && obj.getOrgid() == erupiLinkBankAccount.getOrgId().intValue()) {
			
			try {
				String json = EncryptionDecriptionUtil.convertToJson(erupiLinkBankAccount);

				EncriptResponse jsonObject=EncryptionDecriptionUtil.encriptResponse(json, applicationConstantConfig.apiSignaturePublicPath);

				String encriptResponse =  expensesReimbursementService.relinkErupiAccount(tokengeneration.getToken(), jsonObject);

	   
				EncriptResponse userReqEnc =EncryptionDecriptionUtil.convertFromJson(encriptResponse, EncriptResponse.class);

				String apiResponse = EncryptionDecriptionUtil.decriptResponse(
	                    userReqEnc.getEncriptData(), 
	                    userReqEnc.getEncriptKey(), 
	                    applicationConstantConfig.apiSignaturePrivatePath
	            );

	            JSONObject apiJsonResponse = new JSONObject(apiResponse);
	            
	            // Process API Response
	            if (apiJsonResponse.getBoolean("status")) {
	                responseMap.put("status", true);
	                responseMap.put("message", apiJsonResponse.getString("message"));
	            } else {
	                responseMap.put("status", false);
	                responseMap.put("message", apiJsonResponse.getString("message"));
	            }

	        } catch (Exception e) {
	            responseMap.put("status", false);
	            responseMap.put("message", "Internal Server Error: " + e.getMessage());
	            e.printStackTrace();
	        }
	    } else {
	        responseMap.put("status", false);
	        responseMap.put("message", "Unauthorized: Insufficient permissions.");
	    }
	    try {
	        return mapper.writeValueAsString(responseMap);
	    } catch (JsonProcessingException e) {
	        return "{\"status\":false, \"message\":\"JSON processing error\"}";
	    }
		  
	}
	
	@PostMapping(value="/updateStatusExpensesById")
	public @ResponseBody String approveExpensesById(HttpServletRequest request,
		 ExpensesReimbursementRequest expensesReimbursementRequest, BindingResult result, HttpSession session, ModelMap model,Locale locale) {
	
		String profileRes=null;
		try {
			String json = EncryptionDecriptionUtil.convertToJson(expensesReimbursementRequest);

			EncriptResponse jsonObject=EncryptionDecriptionUtil.encriptResponse(json, applicationConstantConfig.apiSignaturePublicPath);

			String encriptResponse =  expensesReimbursementService.approveExpensesById(tokengeneration.getToken(), jsonObject);
			EncriptResponse userReqEnc =EncryptionDecriptionUtil.convertFromJson(encriptResponse, EncriptResponse.class);

			profileRes =  EncryptionDecriptionUtil.decriptResponse(userReqEnc.getEncriptData(), userReqEnc.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
   
	return profileRes;
	
		  
	}
	
	@PostMapping(value="/cashAdvanceRequest")
	public @ResponseBody String cashAdvanceRequest(HttpServletRequest request,
			AdvanceTravelRequest advanceTravelRequest, BindingResult result, HttpSession session, ModelMap model,Locale locale) {
	
		String receivedHash = advanceTravelRequest.getHash();
		 if (!CLIENT_KEY.equals(advanceTravelRequest.getClientKey())) {
	          //  return Map.of("isValid", false, "message", "Invalid client key");
	        }
		 
		// const dataString = empId+employerid+employerName+"Cash"+cashDate+
		//			""+amount+cashCurrency+cashAmmount+cashModeOfPayment+cashRemark+clientKey+secretKey;
		//			console.log("data string"+dataString); 
	        // Ensure consistent concatenation
	        String dataString = advanceTravelRequest.getUsername()
	        +"Cash"+advanceTravelRequest.getCashDate()+advanceTravelRequest.getAmount()+advanceTravelRequest.getCurrency()+
	        advanceTravelRequest.getAmount()+advanceTravelRequest.getModeOfPayment()+
	        advanceTravelRequest.getRemarks()+CLIENT_KEY+SECRET_KEY;

	        // Compute hash
	        String computedHash = null;
			try {
				computedHash = generateHash(dataString);
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			  boolean isValid = computedHash.equals(receivedHash);
			    Map<String, Object> responseMap = new HashMap<>();
			    ObjectMapper mapper = new ObjectMapper();
			    
			  if (!isValid) {
			        responseMap.put("status", false);
			        responseMap.put("message", "Request Tempered");
			        try {
			            return mapper.writeValueAsString(responseMap);
			        } catch (JsonProcessingException e) {
			            return "{\"status\":false, \"message\":\"JSON processing error\"}";
			        }
			    }
			  
			  String token = (String) session.getAttribute("hrms");
			    
			    if (token == null) {
			        responseMap.put("status", false);
			        responseMap.put("message", "Unauthorized: No token found.");
			        try {
			            return mapper.writeValueAsString(responseMap);
			        } catch (JsonProcessingException e) {
			            return "{\"status\":false, \"message\":\"JSON processing error\"}";
			        }
			    }
			    // Validate Token
			    UserDetailsEntity obj = JwtTokenValidator.parseToken(token);
			    if (obj == null) {
			        responseMap.put("status", false);
			        responseMap.put("message", "Unauthorized: Invalid token.");
			        try {
			            return mapper.writeValueAsString(responseMap);
			        } catch (JsonProcessingException e) {
			            return "{\"status\":false, \"message\":\"JSON processing error\"}";
			        }
			    }
    if ((obj.getUser_role() == 2 || obj.getUser_role() == 1 || obj.getUser_role() == 3 || obj.getUser_role() == 9) && obj.getOrgid() == advanceTravelRequest.getEmployerId().intValue()) {
		
		try {
			String json = EncryptionDecriptionUtil.convertToJson(advanceTravelRequest);

			EncriptResponse jsonObject=EncryptionDecriptionUtil.encriptResponse(json, applicationConstantConfig.apiSignaturePublicPath);

			String encriptResponse =  expensesReimbursementService.cashAdvanceRequest(tokengeneration.getToken(), jsonObject);

   
			EncriptResponse userReqEnc =EncryptionDecriptionUtil.convertFromJson(encriptResponse, EncriptResponse.class);

			String apiResponse = EncryptionDecriptionUtil.decriptResponse(
                    userReqEnc.getEncriptData(), 
                    userReqEnc.getEncriptKey(), 
                    applicationConstantConfig.apiSignaturePrivatePath
            );

            JSONObject apiJsonResponse = new JSONObject(apiResponse);
            
            // Process API Response
            if (apiJsonResponse.getBoolean("status")) {
                responseMap.put("status", true);
                responseMap.put("message", apiJsonResponse.getString("message"));
            } else {
                responseMap.put("status", false);
                responseMap.put("message", apiJsonResponse.getString("message"));
            }

        } catch (Exception e) {
            responseMap.put("status", false);
            responseMap.put("message", "Internal Server Error: " + e.getMessage());
            e.printStackTrace();
        }
    } else {
        responseMap.put("status", false);
        responseMap.put("message", "Unauthorized: Insufficient permissions.");
    }
    try {
        return mapper.writeValueAsString(responseMap);
    } catch (JsonProcessingException e) {
        return "{\"status\":false, \"message\":\"JSON processing error\"}";
    }
	  
		  
	}
	
	@GetMapping(value = "/getCashAdanceRequestData")
	public @ResponseBody String getCashAdanceRequestData(HttpServletRequest request, ModelMap model, Locale locale,
			HttpSession session, AdvanceTravelRequest advanceTravelRequest) {
		String profileRes = null;
	
		try {
			String json = EncryptionDecriptionUtil.convertToJson(advanceTravelRequest);

			EncriptResponse jsonObject=EncryptionDecriptionUtil.encriptResponse(json, applicationConstantConfig.apiSignaturePublicPath);

			String encriptResponse =  expensesReimbursementService.getCashAdanceRequestData(tokengeneration.getToken(), jsonObject);

   
			EncriptResponse userReqEnc =EncryptionDecriptionUtil.convertFromJson(encriptResponse, EncriptResponse.class);

			profileRes =  EncryptionDecriptionUtil.decriptResponse(userReqEnc.getEncriptData(), userReqEnc.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
   
	return profileRes;
	
	}
	
	@PostMapping(value="/travelAdvanceRequest")
	public @ResponseBody String travelAdvanceRequest(HttpServletRequest request,
			@RequestBody TravelRequest travelRequest, BindingResult result, HttpSession session, ModelMap model,Locale locale) {

		String receivedHash = travelRequest.getHash();
		 if (!CLIENT_KEY.equals(travelRequest.getClientKey())) {
	          //  return Map.of("isValid", false, "message", "Invalid client key");
	        }
		 
	        String dataString = travelRequest.getEmployeeId()+travelRequest.getRequestType()+travelRequest.getEmployerId()
	        +CLIENT_KEY+SECRET_KEY;

	        // Compute hash
	        String computedHash = null;
			try {
				computedHash = generateHash(dataString);
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			  boolean isValid = computedHash.equals(receivedHash);
			    Map<String, Object> responseMap = new HashMap<>();
			    ObjectMapper mapper = new ObjectMapper();
			    
			  if (!isValid) {
			        responseMap.put("status", false);
			        responseMap.put("message", "Request Tempered");
			        try {
			            return mapper.writeValueAsString(responseMap);
			        } catch (JsonProcessingException e) {
			            return "{\"status\":false, \"message\":\"JSON processing error\"}";
			        }
			    }
			  
			  String token = (String) session.getAttribute("hrms");
			    
			    if (token == null) {
			        responseMap.put("status", false);
			        responseMap.put("message", "Unauthorized: No token found.");
			        try {
			            return mapper.writeValueAsString(responseMap);
			        } catch (JsonProcessingException e) {
			            return "{\"status\":false, \"message\":\"JSON processing error\"}";
			        }
			    }
			    // Validate Token
			    UserDetailsEntity obj = JwtTokenValidator.parseToken(token);
			    if (obj == null) {
			        responseMap.put("status", false);
			        responseMap.put("message", "Unauthorized: Invalid token.");
			        try {
			            return mapper.writeValueAsString(responseMap);
			        } catch (JsonProcessingException e) {
			            return "{\"status\":false, \"message\":\"JSON processing error\"}";
			        }
			    }
    if ((obj.getUser_role() == 2 || obj.getUser_role() == 1 || obj.getUser_role() == 3 || obj.getUser_role() == 9) && obj.getOrgid() == travelRequest.getEmployerId().intValue()) {
		
		try {
			String json = EncryptionDecriptionUtil.convertToJson(travelRequest);

			EncriptResponse jsonObject=EncryptionDecriptionUtil.encriptResponse(json, applicationConstantConfig.apiSignaturePublicPath);

			String encriptResponse =  expensesReimbursementService.travelAdvanceRequest(tokengeneration.getToken(), jsonObject);
   
			EncriptResponse userReqEnc =EncryptionDecriptionUtil.convertFromJson(encriptResponse, EncriptResponse.class);

			String apiResponse = EncryptionDecriptionUtil.decriptResponse(
                    userReqEnc.getEncriptData(), 
                    userReqEnc.getEncriptKey(), 
                    applicationConstantConfig.apiSignaturePrivatePath
            );

            JSONObject apiJsonResponse = new JSONObject(apiResponse);
            
            // Process API Response
            if (apiJsonResponse.getBoolean("status")) {
                responseMap.put("status", true);
                responseMap.put("message", apiJsonResponse.getString("message"));
            } else {
                responseMap.put("status", false);
                responseMap.put("message", apiJsonResponse.getString("message"));
            }

        } catch (Exception e) {
            responseMap.put("status", false);
            responseMap.put("message", "Internal Server Error: " + e.getMessage());
            e.printStackTrace();
        }
    } else {
        responseMap.put("status", false);
        responseMap.put("message", "Unauthorized: Insufficient permissions.");
    }
    try {
        return mapper.writeValueAsString(responseMap);
    } catch (JsonProcessingException e) {
        return "{\"status\":false, \"message\":\"JSON processing error\"}";
    }
	  
	}
	
	@GetMapping(value = "/getTravelReviewData")
	public @ResponseBody String getTravelReviewData(HttpServletRequest request, ModelMap model, Locale locale,
			HttpSession session, AdvanceTravelRequest advanceTravelRequest) {
		String profileRes = null;
		try {
			
			//review and submit
			String json = EncryptionDecriptionUtil.convertToJson(advanceTravelRequest);

			EncriptResponse jsonObject=EncryptionDecriptionUtil.encriptResponse(json, applicationConstantConfig.apiSignaturePublicPath);

			String encriptResponse =  expensesReimbursementService.getTravelReviewData(tokengeneration.getToken(), jsonObject);

   
			EncriptResponse userReqEnc =EncryptionDecriptionUtil.convertFromJson(encriptResponse, EncriptResponse.class);

			profileRes =  EncryptionDecriptionUtil.decriptResponse(userReqEnc.getEncriptData(), userReqEnc.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
   
	return profileRes;
	}
	
	@GetMapping(value = "/getTravelDraftDataReview")
	public @ResponseBody String getTravelDraftDataReview(HttpServletRequest request, ModelMap model, Locale locale,
			HttpSession session, AdvanceTravelRequest advanceTravelRequest) {
		String profileRes = null;
		try {
			
			//review and submit
			String json = EncryptionDecriptionUtil.convertToJson(advanceTravelRequest);

			EncriptResponse jsonObject=EncryptionDecriptionUtil.encriptResponse(json, applicationConstantConfig.apiSignaturePublicPath);

			String encriptResponse =  expensesReimbursementService.getTravelDraftDataReview(tokengeneration.getToken(), jsonObject);

			EncriptResponse userReqEnc =EncryptionDecriptionUtil.convertFromJson(encriptResponse, EncriptResponse.class);

			profileRes =  EncryptionDecriptionUtil.decriptResponse(userReqEnc.getEncriptData(), userReqEnc.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
   
	return profileRes;
	}
	
	@GetMapping(value = "/getTravelViewData")
	public @ResponseBody String getTravelReviewDataByExpensesID(HttpServletRequest request, ModelMap model, Locale locale,
			HttpSession session, AdvanceTravelRequest advanceTravelRequest) {
		String profileRes = null;
		try {
			
			//review and submit
			String json = EncryptionDecriptionUtil.convertToJson(advanceTravelRequest);

			EncriptResponse jsonObject=EncryptionDecriptionUtil.encriptResponse(json, applicationConstantConfig.apiSignaturePublicPath);
    		String encriptResponse =  expensesReimbursementService.getTravelReviewDataByExpensesID(tokengeneration.getToken(), jsonObject);

			EncriptResponse userReqEnc =EncryptionDecriptionUtil.convertFromJson(encriptResponse, EncriptResponse.class);

			profileRes =  EncryptionDecriptionUtil.decriptResponse(userReqEnc.getEncriptData(), userReqEnc.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
   
	return profileRes;
	}
	@GetMapping(value = "/advanceTravelById")
	public @ResponseBody String advanceTravelById(HttpServletRequest request, ModelMap model, Locale locale,
			HttpSession session, TravelRequest advanceTravelRequest) {
		String profileRes = null;
		try {
			String json = EncryptionDecriptionUtil.convertToJson(advanceTravelRequest);

			EncriptResponse jsonObject=EncryptionDecriptionUtil.encriptResponse(json, applicationConstantConfig.apiSignaturePublicPath);

			String encriptResponse =  expensesReimbursementService.advanceTravelById(tokengeneration.getToken(), jsonObject);
			EncriptResponse userReqEnc =EncryptionDecriptionUtil.convertFromJson(encriptResponse, EncriptResponse.class);

			profileRes =  EncryptionDecriptionUtil.decriptResponse(userReqEnc.getEncriptData(), userReqEnc.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
   
	return profileRes;
	}
	
	@PostMapping(value="/travelAdvanceRequestUpdate")
	public @ResponseBody String travelAdvanceRequestUpdate(HttpServletRequest request,
			@RequestBody TravelAdvanceRequestUpdate travelAdvanceRequestUpdate,BindingResult result, HttpSession session, ModelMap model,Locale locale) {

		String profileRes=null;
		try {
			String json = EncryptionDecriptionUtil.convertToJson(travelAdvanceRequestUpdate);

			EncriptResponse jsonObject=EncryptionDecriptionUtil.encriptResponse(json, applicationConstantConfig.apiSignaturePublicPath);

			String encriptResponse =  expensesReimbursementService.travelAdvanceRequestUpdate(tokengeneration.getToken(), jsonObject);

   
			EncriptResponse userReqEnc =EncryptionDecriptionUtil.convertFromJson(encriptResponse, EncriptResponse.class);

			profileRes =  EncryptionDecriptionUtil.decriptResponse(userReqEnc.getEncriptData(), userReqEnc.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
   
	return profileRes;
	}
	
	
	@PostMapping(value = "/deleteAdvanceTravel")
	public @ResponseBody String deleteAdvanceTravel(HttpServletRequest request, ModelMap model, Locale locale,
			HttpSession session, TravelReimbursement travelReimbursement) {
		String profileRes = null;
		try {
			String json = EncryptionDecriptionUtil.convertToJson(travelReimbursement);

			EncriptResponse jsonObject=EncryptionDecriptionUtil.encriptResponse(json, applicationConstantConfig.apiSignaturePublicPath);

			String encriptResponse =  expensesReimbursementService.deleteAdvanceTravel(tokengeneration.getToken(), jsonObject);
   
			EncriptResponse userReqEnc =EncryptionDecriptionUtil.convertFromJson(encriptResponse, EncriptResponse.class);

			profileRes =  EncryptionDecriptionUtil.decriptResponse(userReqEnc.getEncriptData(), userReqEnc.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
   
	return profileRes;

	}
	
	@GetMapping(value = "/getAdanceCashTravelApprpvalList")
	public @ResponseBody String getAdanceCashTravelApprpvalList(HttpServletRequest request, ModelMap model, Locale locale,
			HttpSession session, AdvanceTravelRequest advanceTravelRequest) {
		String profileRes = null;
		try {
			String json = EncryptionDecriptionUtil.convertToJson(advanceTravelRequest);

			EncriptResponse jsonObject=EncryptionDecriptionUtil.encriptResponse(json, applicationConstantConfig.apiSignaturePublicPath);

			String encriptResponse =   expensesReimbursementService.getAdanceCashTravelApprpvalList(tokengeneration.getToken(), jsonObject);

   
			EncriptResponse userReqEnc =EncryptionDecriptionUtil.convertFromJson(encriptResponse, EncriptResponse.class);

			profileRes =  EncryptionDecriptionUtil.decriptResponse(userReqEnc.getEncriptData(), userReqEnc.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
   
	return profileRes;
	}
	
	@PostMapping(value = "/approveAdvanceTravelRequest")
	public @ResponseBody String approveAdvanceTravelRequest(HttpServletRequest request, ModelMap model, Locale locale,
			HttpSession session, ApprovalTravelReimbursement advanceTravelRequest) {
		String profileRes = null;
		try {
			String json = EncryptionDecriptionUtil.convertToJson(advanceTravelRequest);

			EncriptResponse jsonObject=EncryptionDecriptionUtil.encriptResponse(json, applicationConstantConfig.apiSignaturePublicPath);

			String encriptResponse =    expensesReimbursementService.approveAdvanceTravelRequest(tokengeneration.getToken(), jsonObject);

   
			EncriptResponse userReqEnc =EncryptionDecriptionUtil.convertFromJson(encriptResponse, EncriptResponse.class);

			profileRes =  EncryptionDecriptionUtil.decriptResponse(userReqEnc.getEncriptData(), userReqEnc.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
   
	return profileRes;
	}
	
	@GetMapping(value = "/getCashAdvanceDetailById")
	public @ResponseBody String getCashAdvanceDetailById(HttpServletRequest request, ModelMap model, Locale locale,
			HttpSession session, AdvanceTravelRequest advanceTravelRequest) {
		String profileRes = null;
		try {
			String json = EncryptionDecriptionUtil.convertToJson(advanceTravelRequest);

			EncriptResponse jsonObject=EncryptionDecriptionUtil.encriptResponse(json, applicationConstantConfig.apiSignaturePublicPath);

			String encriptResponse =  expensesReimbursementService.getCashAdvanceDetailById(tokengeneration.getToken(), jsonObject);

   
			EncriptResponse userReqEnc =EncryptionDecriptionUtil.convertFromJson(encriptResponse, EncriptResponse.class);

			profileRes =  EncryptionDecriptionUtil.decriptResponse(userReqEnc.getEncriptData(), userReqEnc.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
   
	return profileRes;
	}
	
	@PostMapping(value = "/getErupiLinkAccountDetails")
	public @ResponseBody String getErupiLinkAccountDetails(HttpServletRequest request, ModelMap model, Locale locale,
			HttpSession session, ErupiLinkBankAccount erupiLinkBankAccount) {
		String profileRes = null;
		try {
			String json = EncryptionDecriptionUtil.convertToJson(erupiLinkBankAccount);

			EncriptResponse jsonObject=EncryptionDecriptionUtil.encriptResponse(json, applicationConstantConfig.apiSignaturePublicPath);

			String encriptResponse = expensesReimbursementService.getErupiLinkAccountDetails(tokengeneration.getToken(), jsonObject);

   
			EncriptResponse userReqEnc =EncryptionDecriptionUtil.convertFromJson(encriptResponse, EncriptResponse.class);

			profileRes =  EncryptionDecriptionUtil.decriptResponse(userReqEnc.getEncriptData(), userReqEnc.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
   
	return profileRes;
	}
	
	@PostMapping(value = "/submitCotodelDetails")
	public @ResponseBody String saveCotodelbankDetails(HttpServletRequest request, ModelMap model, Locale locale,
			HttpSession session,  LinkMultipleAccountRequest linkMultipleAccountRequest) {
		String profileRes = null;
		try {
			String json = EncryptionDecriptionUtil.convertToJson(linkMultipleAccountRequest);

			EncriptResponse jsonObject=EncryptionDecriptionUtil.encriptResponse(json, applicationConstantConfig.apiSignaturePublicPath);

			String encriptResponse = expensesReimbursementService.SaveCotodelbankDetails(tokengeneration.getToken(), jsonObject);
   
			EncriptResponse userReqEnc =EncryptionDecriptionUtil.convertFromJson(encriptResponse, EncriptResponse.class);

			profileRes =  EncryptionDecriptionUtil.decriptResponse(userReqEnc.getEncriptData(), userReqEnc.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
   
	return profileRes;
	}
	
	@GetMapping(value = "/getsubmitedCDetails")
	public @ResponseBody String getSavedCBankDetails(HttpServletRequest request, ModelMap model, Locale locale,
			HttpSession session,  LinkMultipleAccountRequest linkMultipleAccountRequest) {
		String profileRes = null;
		try {
			String json = EncryptionDecriptionUtil.convertToJson(linkMultipleAccountRequest);

			EncriptResponse jsonObject=EncryptionDecriptionUtil.encriptResponse(json, applicationConstantConfig.apiSignaturePublicPath);

			String encriptResponse = expensesReimbursementService.getSavedCBankDetails(tokengeneration.getToken(), jsonObject);

			EncriptResponse userReqEnc =EncryptionDecriptionUtil.convertFromJson(encriptResponse, EncriptResponse.class);

			profileRes =  EncryptionDecriptionUtil.decriptResponse(userReqEnc.getEncriptData(), userReqEnc.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
   
	return profileRes;
	}
	
	@PostMapping(value = "/cApproveReject")
	public @ResponseBody String cApproveReject(HttpServletRequest request, ModelMap model, Locale locale,
			HttpSession session,  LinkMultipleAccountRequest linkMultipleAccountRequest) {
		String profileRes = null;
		try {
			String json = EncryptionDecriptionUtil.convertToJson(linkMultipleAccountRequest);

			EncriptResponse jsonObject=EncryptionDecriptionUtil.encriptResponse(json, applicationConstantConfig.apiSignaturePublicPath);

			String encriptResponse = expensesReimbursementService.cApproveReject(tokengeneration.getToken(), jsonObject);
  
			EncriptResponse userReqEnc =EncryptionDecriptionUtil.convertFromJson(encriptResponse, EncriptResponse.class);

			profileRes =  EncryptionDecriptionUtil.decriptResponse(userReqEnc.getEncriptData(), userReqEnc.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
   
	return profileRes;
	}
	
	@PostMapping(value = "/showLinkedAccAmount")
	public @ResponseBody String showLinkedAccAmount(HttpServletRequest request, ModelMap model, Locale locale,
			HttpSession session,  LinkMultipleAccountRequest linkMultipleAccountRequest) {
		String profileRes = null;
		try {
			String json = EncryptionDecriptionUtil.convertToJson(linkMultipleAccountRequest);

			EncriptResponse jsonObject=EncryptionDecriptionUtil.encriptResponse(json, applicationConstantConfig.apiSignaturePublicPath);

			String encriptResponse = expensesReimbursementService.showLinkedAccAmount(tokengeneration.getToken(), jsonObject);

   
			EncriptResponse userReqEnc =EncryptionDecriptionUtil.convertFromJson(encriptResponse, EncriptResponse.class);

			profileRes =  EncryptionDecriptionUtil.decriptResponse(userReqEnc.getEncriptData(), userReqEnc.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
   
	return profileRes;
	}
	
	
	@GetMapping(value = "/getTravelRequestApprovalList")
	public @ResponseBody String getTravelRequestApprovalList(HttpServletRequest request, ModelMap model, Locale locale,
			HttpSession session, AdvanceTravelRequest travelRequest) {
		String profileRes = null;
		try {
			String json = EncryptionDecriptionUtil.convertToJson(travelRequest);

			EncriptResponse jsonObject=EncryptionDecriptionUtil.encriptResponse(json, applicationConstantConfig.apiSignaturePublicPath);

			String encriptResponse = expensesReimbursementService.getTravelRequestApprovalList(tokengeneration.getToken(), jsonObject);
  
			EncriptResponse userReqEnc =EncryptionDecriptionUtil.convertFromJson(encriptResponse, EncriptResponse.class);

			profileRes =  EncryptionDecriptionUtil.decriptResponse(userReqEnc.getEncriptData(), userReqEnc.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
   
	return profileRes;
	}
	
	  private String generateHash(String data) throws NoSuchAlgorithmException {
	        MessageDigest digest = MessageDigest.getInstance("SHA-256");
	        byte[] hashBytes = digest.digest(data.getBytes(StandardCharsets.UTF_8));
	        StringBuilder hexString = new StringBuilder();
	        for (byte b : hashBytes) {
	            hexString.append(String.format("%02x", b));
	        }
	        return hexString.toString();
	    }
}
