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
    				

    				bodyText = "<!-- File: /public/index.html -->\r\n"
    						+ "<!doctype html>\r\n"
    						+ "<html lang=\"en\">\r\n"
    						+ "  <head>\r\n"
    						+ "    <meta charset=\"utf-8\" />\r\n"
    						+ "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\" />\r\n"
    						+ "    <title>Cotodel – Welcome</title>\r\n"
    						+ "\r\n"
    						+ "    <!-- Bootstrap 5 -->\r\n"
    						+ "    <link href=\"https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css\" rel=\"stylesheet\" />\r\n"
    						+ "    <!-- Bootstrap Icons -->\r\n"
    						+ "    <link href=\"https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.css\" rel=\"stylesheet\" />\r\n"
    						+ "    <!-- Google Font -->\r\n"
    						+ "    <link href=\"https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600;700&display=swap\" rel=\"stylesheet\" />\r\n"
    						+ "\r\n"
    						+ "    <style>\r\n"
    						+ "      :root {\r\n"
    						+ "        --brand: #2b8a56;\r\n"
    						+ "        --brand-600: #237246;\r\n"
    						+ "        --brand-700: #1c5e39;\r\n"
    						+ "        --ink: #1d2b21;\r\n"
    						+ "        --muted-bg: #f1f6f3;\r\n"
    						+ "        --soft: #e6efe9;\r\n"
    						+ "\r\n"
    						+ "        /* Controls overall mail content width */\r\n"
    						+ "        --mail-width: 880px;\r\n"
    						+ "        /* Controls footer band width (can override via utility classes or inline) */\r\n"
    						+ "        --footer-width: var(--mail-width);\r\n"
    						+ "      }\r\n"
    						+ "\r\n"
    						+ "      html, body { height: 100%; }\r\n"
    						+ "      body {\r\n"
    						+ "        font-family: \"Poppins\", system-ui, -apple-system, Segoe UI, Roboto, \"Helvetica Neue\", Arial, \"Noto Sans\", \"Apple Color Emoji\", \"Segoe UI Emoji\";\r\n"
    						+ "        color: var(--ink);\r\n"
    						+ "        background: #ffffff;\r\n"
    						+ "      }\r\n"
    						+ "\r\n"
    						+ "      /* Email-like max width for comfy reading */\r\n"
    						+ "      .mail-shell { max-width: var(--mail-width); }\r\n"
    						+ "\r\n"
    						+ "      /* Top banner */\r\n"
    						+ "      .hero-bar {\r\n"
    						+ "        background: var(--brand);\r\n"
    						+ "        color: #fff;\r\n"
    						+ "        min-height: 140px;\r\n"
    						+ "        display: flex;\r\n"
    						+ "        align-items: center;\r\n"
    						+ "      }\r\n"
    						+ "\r\n"
    						+ "      .logo { font-weight: 700; letter-spacing: .2px; font-size: 2.1rem; line-height: 1; }\r\n"
    						+ "      .logo .accent { color: #c6f0cf; }\r\n"
    						+ "      .tagline { opacity: .92; }\r\n"
    						+ "\r\n"
    						+ "      /* Illustration card */\r\n"
    						+ "      .illus-card { background: var(--muted-bg); border: 0; border-radius: 18px; }\r\n"
    						+ "      .illus-wrapper { position: relative; overflow: hidden; border-radius: 18px; }\r\n"
    						+ "      .illus-wrapper .back-bubble {\r\n"
    						+ "        position: absolute; inset: 16px auto auto 16px; width: 44px; height: 44px;\r\n"
    						+ "        background: #ffffff; border-radius: 50%; display: grid; place-items: center;\r\n"
    						+ "        box-shadow: 0 6px 16px rgba(0,0,0,.08);\r\n"
    						+ "      }\r\n"
    						+ "\r\n"
    						+ "      .cta-btn { background: var(--brand); border-color: var(--brand); }\r\n"
    						+ "      .cta-btn:hover { background: var(--brand-600); border-color: var(--brand-600); }\r\n"
    						+ "\r\n"
    						+ "      /* ===================== Footer ===================== */\r\n"
    						+ "      /* Full-bleed helper so the footer can escape .mail-shell */\r\n"
    						+ "      .full-bleed {\r\n"
    						+ "        position: relative; /* why: enable 100vw band centered regardless of parent width */\r\n"
    						+ "        left: 50%;\r\n"
    						+ "        right: 50%;\r\n"
    						+ "        margin-left: calc(50% - 50vw);\r\n"
    						+ "        margin-right: calc(50% - 50vw);\r\n"
    						+ "        width: 100vw;\r\n"
    						+ "      }\r\n"
    						+ "\r\n"
    						+ "      /* The adjustable-width band centered inside the 100vw strip */\r\n"
    						+ "	  .footer-band {\r\n"
    						+ "	    width: 100vw;\r\n"
    						+ "	    margin: 0; /* remove centering */\r\n"
    						+ "	  }\r\n"
    						+ "\r\n"
    						+ "      /* Preset width utilities (pick one or override inline: style=\"--footer-width: 1280px\") */\r\n"
    						+ "      .footer-wide-sm { --footer-width: 960px; }\r\n"
    						+ "      .footer-wide-md { --footer-width: 1100px; }\r\n"
    						+ "      .footer-wide-lg { --footer-width: 1280px; }\r\n"
    						+ "      .footer-wide-xl { --footer-width: 1440px; }\r\n"
    						+ "      .footer-full    { --footer-width: 100vw; } /* makes the band edge-to-edge */\r\n"
    						+ "\r\n"
    						+ "	  .footer-strip {\r\n"
    						+ "	    background: var(--muted-bg);\r\n"
    						+ "	    min-height: 200px;\r\n"
    						+ "	    border-radius: 0;\r\n"
    						+ "	    padding: 2rem 2rem;\r\n"
    						+ "	    display: block;\r\n"
    						+ "	  }\r\n"
    						+ "	  .footer-grid {\r\n"
    						+ "	    display: grid;\r\n"
    						+ "	    grid-template-columns: 1fr 1fr 1fr;\r\n"
    						+ "	    align-items: center;\r\n"
    						+ "	    margin-left: 260px; /* account for illustration */\r\n"
    						+ "	  }\r\n"
    						+ "\r\n"
    						+ "	  .footer-grid > div {\r\n"
    						+ "	    padding: 0.5rem;\r\n"
    						+ "	  }\r\n"
    						+ "\r\n"
    						+ "	  @media (max-width: 768px) {\r\n"
    						+ "	    .footer-grid {\r\n"
    						+ "	      grid-template-columns: 1fr;\r\n"
    						+ "	      text-align: center;\r\n"
    						+ "	      margin-left: 200px;\r\n"
    						+ "	    }\r\n"
    						+ "	  }\r\n"
    						+ "	  .footer-strip.footer-no-radius {\r\n"
    						+ "	    border-radius: 0;\r\n"
    						+ "	    padding: 0; /* remove both left & right padding */\r\n"
    						+ "	  } /* optional when using .footer-full */\r\n"
    						+ "\r\n"
    						+ "      /* Footer left illustration: green side + lady overlay */\r\n"
    						+ "      .footer-illus {\r\n"
    						+ "        position: absolute; inset: 0 auto 0 0; width: 260px;\r\n"
    						+ "        background: url('https://staging.cotodel.com/img/emailer_side_green_img.png') left bottom / cover no-repeat;\r\n"
    						+ "        overflow: hidden;\r\n"
    						+ "      }\r\n"
    						+ "      .footer-illus .lady { position: absolute; left: 20px; bottom: 0; height: 150px; width: auto; }\r\n"
    						+ "      .footer-site { position: absolute; bottom: 8px; left: 20px; font-size: .85rem; color: #2b8a56; text-decoration: none; }\r\n"
    						+ "\r\n"
    						+ "      .footer-content { margin-left: 260px; }\r\n"
    						+ "\r\n"
    						+ "      .link-muted { color: #6b7a71; text-decoration: none; }\r\n"
    						+ "      .link-muted:hover { color: var(--brand-700); text-decoration: underline; }\r\n"
    						+ "      .header-ill { max-height: 120px; }\r\n"
    						+ "\r\n"
    						+ "      /* Brand logo sizing */\r\n"
    						+ "      .brand-logo { height: 48px; width: auto; max-width: 100%; }\r\n"
    						+ "      @media (min-width: 768px) { .brand-logo { height: 60px; } }\r\n"
    						+ "\r\n"
    						+ "      /* Mobile tweaks */\r\n"
    						+ "      @media (max-width: 575.98px) {\r\n"
    						+ "        .logo { font-size: 1.7rem; }\r\n"
    						+ "        .hero-bar { min-height: 120px; }\r\n"
    						+ "        .footer-illus { width: 200px; }\r\n"
    						+ "        .footer-content { margin-left: 200px; }\r\n"
    						+ "      }\r\n"
    						+ "	  :root {\r\n"
    						+ "	    --footer-shift-x: 0px; /* default, no shift */\r\n"
    						+ "	  }\r\n"
    						+ "\r\n"
    						+ "	  .shiftable-footer {\r\n"
    						+ "	    position: relative;\r\n"
    						+ "	    left: var(--footer-shift-x);\r\n"
    						+ "	  }\r\n"
    						+ "	  \r\n"
    						+ "	  \r\n"
    						+ "	  .footer-content-wrapper {\r\n"
    						+ "	    margin-left: 260px; /* space for illustration */\r\n"
    						+ "	    padding: 1rem 2rem;\r\n"
    						+ "	  }\r\n"
    						+ "\r\n"
    						+ "	  .footer-bottom {\r\n"
    						+ "	    gap: 1rem;\r\n"
    						+ "	  }\r\n"
    						+ "\r\n"
    						+ "	  .footer-bottom .small {\r\n"
    						+ "	    white-space: nowrap;\r\n"
    						+ "	  }\r\n"
    						+ "\r\n"
    						+ "	  .footer-left hr {\r\n"
    						+ "	    border: 0;\r\n"
    						+ "	    border-top: 1px solid #ccc;\r\n"
    						+ "	    margin: 1rem 0;\r\n"
    						+ "	  }\r\n"
    						+ "\r\n"
    						+ "	  @media (max-width: 768px) {\r\n"
    						+ "	    .footer-content-wrapper {\r\n"
    						+ "	      margin-left: 200px;\r\n"
    						+ "	      text-align: center;\r\n"
    						+ "	    }\r\n"
    						+ "\r\n"
    						+ "	    .footer-bottom {\r\n"
    						+ "	      flex-direction: column;\r\n"
    						+ "	      gap: 0.5rem;\r\n"
    						+ "	    }\r\n"
    						+ "\r\n"
    						+ "	    .footer-bottom .small {\r\n"
    						+ "	      white-space: normal;\r\n"
    						+ "	    }\r\n"
    						+ "	  }\r\n"
    						+ "\r\n"
    						+ "\r\n"
    						+ "\r\n"
    						+ "    </style>\r\n"
    						+ "  </head>\r\n"
    						+ "  <body>\r\n"
    						+ "    <header class=\"hero-bar\">\r\n"
    						+ "      <div class=\"container mail-shell py-4\">\r\n"
    						+ "        <div class=\"row align-items-center g-3\">\r\n"
    						+ "          <div class=\"col-12 col-md-7\">\r\n"
    						+ "			<img src=\"https://staging.cotodel.com/img/cotodel_without%20tagline.png\"\r\n"
    						+ "			     alt=\"Cotodel logo\"\r\n"
    						+ "			     style=\"width:162px !important; height:54px !important; object-fit:contain;\" />\r\n"
    						+ "\r\n"
    						+ "            <p class=\"mb-0 tagline fw-medium text-white-50\">Business expenses made seamless like your personal UPI spends</p>\r\n"
    						+ "          </div>\r\n"
    						+ "          <div class=\"col-12 col-md-5 text-center text-md-end\">\r\n"
    						+ "            <img src=\"img/Emailer_Scan to pay-bro 1.svg\" alt=\"Scan to Pay Illustration\" class=\"img-fluid header-ill\" />\r\n"
    						+ "          </div>\r\n"
    						+ "        </div>\r\n"
    						+ "      </div>\r\n"
    						+ "    </header>\r\n"
    						+ "\r\n"
    						+ "    <main class=\"container mail-shell my-5\">\r\n"
    						+ "      <!-- Illustration / hero image card -->\r\n"
    						+ "      <div class=\"card illus-card p-2 p-sm-3 mb-4\">\r\n"
    						+ "        <div class=\"illus-wrapper\">\r\n"
    						+ "          <img class=\"w-100 d-block\" src=\"https://staging.cotodel.com/img/Emailer_Corp_Bank.svg\" alt=\"Cotodel platform welcome illustration\" style=\"object-fit: cover; max-height: 420px;\" />\r\n"
    						+ "          \r\n"
    						+ "        </div>\r\n"
    						+ "      </div>\r\n"
    						+ "\r\n"
    						+ "      <!-- Copy section -->\r\n"
    						+ "      <section class=\"px-1 px-sm-2 px-md-1\">\r\n"
    						+ "        <h2 class=\"fw-bold mb-3\">Great news, <span class=\"text-body-secondary\">"+session.getAttribute("organizationName")+"</span>!</h2>\r\n"
    						+ "        <p class=\"mb-3\">Your Bank Account is now live on the Cotodel platform. You can now onboard your employees and start managing your corporate expenses via UPI Vouchers!</p>\r\n"
    						+ "		<p class=\"mb-3\">\r\n"
    						+ "		  If you have any concerns, please contact us at \r\n"
    						+ "		  <a href=\"mailto:support@cotodel.com\" style=\"color:#367AFF; text-decoration:none;\">\r\n"
    						+ "		    support@cotodel.com\r\n"
    						+ "		  </a>\r\n"
    						+ "		</p>\r\n"
    						+ "        <p class=\"mb-4\">Sincerely,<br>Team Cotodel</p>\r\n"
    						+ "\r\n"
    						+ "		<div class=\"text-center mb-3\">\r\n"
    						+ "		  <a href=\"https://www.cotodel.com/login\" class=\"btn btn-success cta-btn px-4 py-2 fw-semibold rounded-3\">Log In to Cotodel </a>\r\n"
    						+ "		</div>\r\n"
    						+ "		<p class=\"small text-secondary \" style=\"margin-left: 135px;\">\r\n"
    						+ "		  Disclaimer: Please mark us as <em>'Not Spam'</em> to receive future emails from Cotodel in your inbox\r\n"
    						+ "		</p>\r\n"
    						+ "\r\n"
    						+ "	 </section>\r\n"
    						+ "\r\n"
    						+ "      <!-- ===================== Footer ===================== -->\r\n"
    						+ "      <!--\r\n"
    						+ "        HOW TO CONTROL FOOTER WIDTH:\r\n"
    						+ "        - Change the class on .footer-band to one of: footer-wide-sm | footer-wide-md | footer-wide-lg | footer-wide-xl | footer-full\r\n"
    						+ "        - Or override exactly via inline style: style=\"--footer-width: 1360px\"\r\n"
    						+ "      -->\r\n"
    						+ "	  <!-- ===================== Footer ===================== -->\r\n"
    						+ "	  <!-- ===================== Footer ===================== -->\r\n"
    						+ "	  <footer class=\"mt-5\" style=\"margin-right: 420px;\">\r\n"
    						+ "	    <div class=\"full-bleed\">\r\n"
    						+ "	      <div class=\"footer-band footer-full\">\r\n"
    						+ "	        <div class=\"footer-strip footer-no-radius position-relative\">\r\n"
    						+ "\r\n"
    						+ "	          <!-- Left Illustration -->\r\n"
    						+ "	          <div class=\"footer-illus\">\r\n"
    						+ "	            <img src=\"https://staging.cotodel.com/img/Emailer_singleLady.svg\" alt=\"Person working on laptop\" class=\"lady\" />\r\n"
    						+ "	            <a class=\"footer-site small\" href=\"https://www.cotodel.com\">www.cotodel.com</a>\r\n"
    						+ "	          </div>\r\n"
    						+ "\r\n"
    						+ "	          <!-- Content Wrapper -->\r\n"
    						+ "	          <div class=\"footer-content-wrapper \">\r\n"
    						+ "	            \r\n"
    						+ "	            <!-- Top: Logo + Address -->\r\n"
    						+ "	            <div class=\"footer-left mb-3\">\r\n"
    						+ "	              <!--<div class=\"logo mb-2\" style=\"font-size:1.8rem;\">coto<span class=\"accent\">del</span></div>-->\r\n"
    						+ "				  <img src=\"https://staging.cotodel.com/img/Cotodel Logo.webp\"\r\n"
    						+ "				  			     alt=\"Cotodel logo\"\r\n"
    						+ "				  			     style=\"width:120px !important; height:34px !important; object-fit:contain;\" />\r\n"
    						+ "	              <div class=\"small text-secondary\">\r\n"
    						+ "	                <div class=\"fw-semibold\">ADDRESS</div>\r\n"
    						+ "	                <div>WeWork, Eldeco Centre, Malviya Nagar New Delhi - 110017</div>\r\n"
    						+ "	              </div>\r\n"
    						+ "	            </div>\r\n"
    						+ "\r\n"
    						+ "	            <hr>\r\n"
    						+ "\r\n"
    						+ "	            <!-- Bottom Row: unsubscribe | copyright | social -->\r\n"
    						+ "	            <div class=\"footer-bottom d-flex align-items-center justify-content-between flex-wrap mt-3\">\r\n"
    						+ "	              <a class=\"link-muted small\" href=\"#\">Click to unsubscribe from Marketing emails</a>\r\n"
    						+ "				  \r\n"
    						+ "	              <div class=\"small text-secondary text-start flex-grow-1\">| © 2025 Cotodel – All rights reserved</div>\r\n"
    						+ "	              <div class=\"d-flex gap-3 fs-5\">\r\n"
    						+ "	                <a class=\"text-dark\" href=\"https://www.linkedin.com/company/cotopay/posts/?feedView=all\" aria-label=\"LinkedIn\"><i class=\"bi bi-linkedin\"></i></a>\r\n"
        					+ "	                <a class=\"text-dark\" href=\"https://www.instagram.com/cotodelsolutions\" aria-label=\"Instagram\"><i class=\"bi bi-instagram\"></i></a>\r\n"
        					+ "	                <a class=\"text-dark\" href=\"https://www.youtube.com/@Cotodel\" aria-label=\"YouTube\"><i class=\"bi bi-youtube\"></i></a>\r\n"
    						+ "	              </div>\r\n"
    						+ "	            </div>\r\n"
    						+ "\r\n"
    						+ "	          </div>\r\n"
    						+ "\r\n"
    						+ "	        </div>\r\n"
    						+ "	      </div>\r\n"
    						+ "	    </div>\r\n"
    						+ "	  </footer>\r\n"
    						+ "\r\n"
    						+ "      <!-- Footer meta row under the band (optional) \r\n"
    						+ "      <div class=\"d-flex justify-content-between small text-secondary mt-3\">\r\n"
    						+ "        <span><a class=\"link-muted\" href=\"#\">Made with Bootstrap 5</a></span>\r\n"
    						+ "      </div>-->\r\n"
    						+ "    </main>\r\n"
    						+ "\r\n"
    						+ "    <!-- Bootstrap JS -->\r\n"
    						+ "    <script src=\"https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js\"></script>\r\n"
    						+ "  </body>\r\n"
    						+ "</html>\r\n"
    						+ "";
    				Map<String, Object> response = GraphMailSender.sendMail(GraphMailSender.acquireToken(), "support@cotodel.com",
        			email,  "✅ Bank Account Added",  bodyText);
    	            } catch (Exception e) {
    				// TODO Auto-generated catch block
    				e.printStackTrace();
    			}
    	            try {
    	            	WhatsAppRequest whatsapp = new WhatsAppRequest();
    	                whatsapp.setSource("new-landing-page form");
    	                whatsapp.setCampaignName("250825_Bank Account Activation");
    	                whatsapp.setFirstName((String) session.getAttribute("usernamme"));
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
