package com.cotodel.hrms.web.controller;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Row;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cotodel.hrms.web.properties.ApplicationConstantConfig;
import com.cotodel.hrms.web.response.BulkVoucherRequest;
import com.cotodel.hrms.web.response.ErupiBulkVoucherCreateRequest;
import com.cotodel.hrms.web.response.ErupiVoucherRevokeDetailsBulkRequest;
import com.cotodel.hrms.web.response.RevokeResponse;
import com.cotodel.hrms.web.response.SMSRequest;
import com.cotodel.hrms.web.response.UserDetailsEntity;
import com.cotodel.hrms.web.response.VoucherData;
import com.cotodel.hrms.web.response.WhatsAppRequest;
import com.cotodel.hrms.web.service.BulkVoucherService;
import com.cotodel.hrms.web.service.ErupiVoucherCreateDetailsService;
import com.cotodel.hrms.web.service.LoginService;
import com.cotodel.hrms.web.service.Impl.EmailServiceImpl;
import com.cotodel.hrms.web.service.Impl.TokenGenerationImpl;
import com.cotodel.hrms.web.util.EncriptResponse;
import com.cotodel.hrms.web.util.EncryptionDecriptionUtil;
import com.cotodel.hrms.web.util.GraphMailSender;
import com.cotodel.hrms.web.util.JwtTokenValidator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.IOException;
import com.cotodel.hrms.web.util.MessageConstant;




@Controller
@CrossOrigin
public class ErupiBulkVoucherCreationController extends CotoDelBaseController{

	private static final Logger logger = LoggerFactory.getLogger(ErupiBulkVoucherCreationController.class);
	private static final String SECRET_KEY = "0123456789012345"; // Must match frontend
    private static final String CLIENT_KEY = "client-secret-key"; // Extra validation
	@Autowired
	public ApplicationConstantConfig applicationConstantConfig;
	
	@Autowired
	BulkVoucherService bulkVoucherService;

	@Autowired
	TokenGenerationImpl tokengeneration;
	
	@Autowired
	ErupiVoucherCreateDetailsService erupiVoucherCreateDetailsService;
	
	@Autowired
	EmailServiceImpl emailService;
	
	@Autowired
	LoginService loginservice;
	
	
	@PostMapping(value="/saveBulkVoucher")
	public @ResponseBody  String saveBulkVoucher(HttpServletResponse response, HttpServletRequest request,
			BulkVoucherRequest bulkVoucherRequest, BindingResult result, HttpSession session, Model model,RedirectAttributes redirect) {
		
		String profileRes=null;
	
	    Map<String, Object> responseMap = new HashMap<>();
	    ObjectMapper mapper = new ObjectMapper();
	    
		String receivedHash = bulkVoucherRequest.getHash();
		 // Validate client key first
        if (!CLIENT_KEY.equals(bulkVoucherRequest.getClientKey())) {
        	 responseMap.put("status", false);
             responseMap.put("message", "Invalid client key");
        }
        String dataString = bulkVoucherRequest.getOrgId()+bulkVoucherRequest.getFileName()+bulkVoucherRequest.getFile()+
        bulkVoucherRequest.getMcc()+bulkVoucherRequest.getVoucherCode()
        +bulkVoucherRequest.getVoucherDesc()+bulkVoucherRequest.getCreatedby()+CLIENT_KEY+SECRET_KEY;
		  
        // Compute hash
        String computedHash = null;
		try {
			computedHash = generateHash(dataString);
			System.out.println("computedHash"+computedHash);
			System.out.println("computedHash---"+dataString);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    // Validate hash
    boolean isValid = computedHash.equals(receivedHash);
 
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
    if ((obj.getUser_role() == 9 || obj.getUser_role() == 1 || obj.getUser_role() == 3) && obj.getOrgid() == bulkVoucherRequest.getOrgId().intValue()) {
        try {
        	String json = EncryptionDecriptionUtil.convertToJson(bulkVoucherRequest);
        	
			EncriptResponse jsonObject=EncryptionDecriptionUtil.encriptResponse(json, applicationConstantConfig.apiSignaturePublicPath);

			String encriptResponse = bulkVoucherService.saveBulkVoucher(tokengeneration.getToken(), jsonObject);

   
			EncriptResponse userReqEnc =EncryptionDecriptionUtil.convertFromJson(encriptResponse, EncriptResponse.class);

			profileRes =  EncryptionDecriptionUtil.decriptResponse(userReqEnc.getEncriptData(), userReqEnc.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
       			
            JSONObject apiJsonResponse = new JSONObject(profileRes);
            
            boolean status = apiJsonResponse.getBoolean("status");
	        responseMap.put("status", status);
	        responseMap.put("message", apiJsonResponse.getString("message"));

	        if (status && apiJsonResponse.has("data")) {
	            //List<Object> dataList = apiJsonResponse.getJSONArray("data").toList();
	            responseMap.put("data", profileRes);
	        } else {
                responseMap.put("status", false);
                responseMap.put("message", apiJsonResponse.getString("message"));
                responseMap.put("data",  apiJsonResponse.getJSONObject("data"));
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


	@PostMapping(value="/issueBulkVoucher")
	public @ResponseBody String issueBulkVoucher(HttpServletRequest request, ModelMap model,Locale locale,HttpSession session,
			ErupiBulkVoucherCreateRequest erupiBulkVoucherCreateRequest) {
		
	    Map<String, Object> responseMap = new HashMap<>();
	    ObjectMapper mapper = new ObjectMapper();
	    
		String bodyText = null;
		String email = (String) session.getAttribute("email");
		
		String receivedHash = erupiBulkVoucherCreateRequest.getHash();
		 // Validate client key first
        if (!CLIENT_KEY.equals(erupiBulkVoucherCreateRequest.getClientKey())) {
        	 responseMap.put("status", false);
             responseMap.put("message", "Invalid client key");
        }
        
        String dataString = erupiBulkVoucherCreateRequest.getOrgId()+erupiBulkVoucherCreateRequest.getPurposeCode()+erupiBulkVoucherCreateRequest.
        		getMcc()+
        		erupiBulkVoucherCreateRequest.getPayerVA()+erupiBulkVoucherCreateRequest.getMandateType()+erupiBulkVoucherCreateRequest.getType()
        +erupiBulkVoucherCreateRequest.getBankcode()+ erupiBulkVoucherCreateRequest.getAccountNumber()+
        erupiBulkVoucherCreateRequest.getVoucherCode()+erupiBulkVoucherCreateRequest.getVoucherDesc()+erupiBulkVoucherCreateRequest.getMerchantId()+
        erupiBulkVoucherCreateRequest.getSubMerchantId()+erupiBulkVoucherCreateRequest.getCreatedby()+CLIENT_KEY+SECRET_KEY;
		   

        // Compute hash
        String computedHash = null;
		try {
			computedHash = generateHash(dataString);
			System.out.println("computedHash"+computedHash);
			System.out.println("computedHash---"+dataString);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    // Validate hash
    boolean isValid = computedHash.equals(receivedHash);
 
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
    if ((obj.getUser_role() == 9 || obj.getUser_role() == 1 || obj.getUser_role() == 3) && obj.getOrgid() == erupiBulkVoucherCreateRequest.getOrgId().intValue()) {
        try {
            // Call Service
            String encryptedResponse = erupiVoucherCreateDetailsService.issueBulkVoucher(tokengeneration.getToken(),erupiBulkVoucherCreateRequest);
            // Decrypt Response
         
            JSONObject apiJsonResponse = new JSONObject(encryptedResponse);
            
            boolean status = apiJsonResponse.getBoolean("status");
	        responseMap.put("status", status);
	        responseMap.put("message", apiJsonResponse.getString("message"));

	        if (status && apiJsonResponse.has("data")) {
	        	
	        	 // Extract the array
	        	
	            JSONArray dataArray = apiJsonResponse.getJSONArray("data");
	            logger.info("voucher dataArray"+dataArray);// TODO: handle exception
	            // Map JSON array to List<VoucherData>
	            ObjectMapper objMapper = new ObjectMapper();
	            List<VoucherData> voucherList = objMapper.readValue(
	                dataArray.toString(),
	                objMapper.getTypeFactory().constructCollectionType(List.class, VoucherData.class)
	            );

	            // Iterate only successful vouchers
	            for (VoucherData item : voucherList) {
	                if ("SUCCESS".equalsIgnoreCase(item.getResponse())) {
	                    System.out.println("Name: " + item.getName());
	                    System.out.println("Amount: " + item.getAmount());
	                    System.out.println("Response: " + item.getResponse());
	                    WhatsAppRequest whatsapp = new WhatsAppRequest();
	                    whatsapp.setSource("new-landing-page form");
	                    whatsapp.setCampaignName("Voucher_issuance_user");
	                    whatsapp.setFirstName(item.getName());
	                    whatsapp.setAmount(item.getAmount());
	                    whatsapp.setCategory(item.getMccDescription());
	                    whatsapp.setMobile(item.getMobile());
	                    whatsapp.setOrganizationName("Cotodel");
	                    whatsapp.setValidity(item.getExpDate());
	                    whatsapp.setType(item.getRedemtionType());
	                    whatsapp.setUserName("Cotodel Communications");
	                    try {
	            			String json = EncryptionDecriptionUtil.convertToJson(whatsapp);

	            			EncriptResponse jsonObject=EncryptionDecriptionUtil.encriptResponse(json, applicationConstantConfig.apiSignaturePublicPath);

	            			String encriptResponse =  erupiVoucherCreateDetailsService.sendWhatsupMessage(tokengeneration.getToken(), jsonObject);
	               
	            			EncriptResponse userReqEnc =EncryptionDecriptionUtil.convertFromJson(encriptResponse, EncriptResponse.class);

	            			encryptedResponse =  EncryptionDecriptionUtil.decriptResponse(userReqEnc.getEncriptData(), userReqEnc.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
	            		} catch (Exception e) {
	            			// TODO Auto-generated catch block
	            			e.printStackTrace();
	            		}
	                }
	                String smsResponse =null;
	                SMSRequest smsRequest = new SMSRequest();
		        	smsRequest.setMobile(item .getMobile());
		        	//smsRequest.setValue(revokeResponse .getRevokeAmount());
		        	
		        	String template = "UPI Voucher worth â‚¹#VAR1# for #VAR2# spends is issued to you! Transact using your Google Pay App. - Cotodel.";
		        	String finalMessage = template
		        	        .replace("#VAR1#", item.getAmount())
		        	        .replace("#VAR2#",item.getMccDescription());
		        	
		        	smsRequest.setMessage(finalMessage);
		        	
		            try {
		            String userFormjson = EncryptionDecriptionUtil.convertToJson(smsRequest);

					EncriptResponse userFormjsonObject=EncryptionDecriptionUtil.encriptResponse(userFormjson, applicationConstantConfig.apiSignaturePublicPath);

					String userFormResponse = loginservice.sendTransactionalSMS(tokengeneration.getToken(), userFormjsonObject);
		   
					EncriptResponse userFornReqEnc =EncryptionDecriptionUtil.convertFromJson(userFormResponse, EncriptResponse.class);

					 smsResponse =  EncryptionDecriptionUtil.decriptResponse(userFornReqEnc.getEncriptData(), userFornReqEnc.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
					 JSONObject smsapiJsonResponse = new JSONObject(smsResponse); 

					 bodyText ="\r\n"
								+ "<!DOCTYPE html>\r\n"
								+ "<html lang=\"en\" xmlns=\"http://www.w3.org/1999/xhtml\">\r\n"
								+ "  <head>\r\n"
								+ "    <meta charset=\"utf-8\" />\r\n"
								+ "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\" />\r\n"
								+ "    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\" />\r\n"
								+ "    <meta name=\"x-apple-disable-message-reformatting\" />\r\n"
								+ "    <meta name=\"format-detection\" content=\"telephone=no,address=no,email=no,date=no,url=no\" />\r\n"
								+ "    <title>Cotodel – UPI Voucher Issued</title>\r\n"
								+ "    <!-- Why: modest client-level styles for better defaults; heavy styles are inlined below. -->\r\n"
								+ "    <style>\r\n"
								+ "      html,\r\n"
								+ "      body {\r\n"
								+ "        margin: 0 !important;\r\n"
								+ "        padding: 0 !important;\r\n"
								+ "        height: 100% !important;\r\n"
								+ "        width: 100% !important;\r\n"
								+ "      }\r\n"
								+ "      /* Why: stop iOS blue links */\r\n"
								+ "      a[x-apple-data-detectors] {\r\n"
								+ "        color: inherit !important;\r\n"
								+ "        text-decoration: none !important;\r\n"
								+ "      }\r\n"
								+ "      /* Why: make images fluid on small screens */\r\n"
								+ "      @media only screen and (max-width: 600px) {\r\n"
								+ "        .container {\r\n"
								+ "          width: 100% !important;\r\n"
								+ "          max-width: 100% !important;\r\n"
								+ "        }\r\n"
								+ "        .px-32 { padding-left: 16px !important; padding-right: 16px !important; }\r\n"
								+ "        .py-24 { padding-top: 16px !important; padding-bottom: 16px !important; }\r\n"
								+ "        .fluid-img { width: 100% !important; height: auto !important; }\r\n"
								+ "        .text-center-sm { text-align: center !important; }\r\n"
								+ "      }\r\n"
								+ "    </style>\r\n"
								+ "  </head>\r\n"
								+ "  <body style=\"margin:0; padding:0; background-color:#f5f7fa;\">\r\n"
								+ "    <!-- Preheader (hidden) -->\r\n"
								+ "    <div style=\"display:none; font-size:1px; line-height:1px; max-height:0; max-width:0; opacity:0; overflow:hidden; mso-hide:all;\">\r\n"
								+ "      Your UPI Voucher has been issued by "+session.getAttribute("organizationName")+".\r\n"
								+ "    </div>\r\n"
								+ "    <center role=\"article\" aria-roledescription=\"email\" lang=\"en\" style=\"width:100%; background:#f5f7fa;\">\r\n"
								+ "      <table role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\" style=\"background:#f5f7fa;\">\r\n"
								+ "        <tr>\r\n"
								+ "          <td align=\"center\">\r\n"
								+ "            <!-- Outer container -->\r\n"
								+ "            <table role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"600\" class=\"container\" style=\"width:600px; max-width:600px;\">\r\n"
								+ "              <!-- Header -->\r\n"
								+ "              <tr>\r\n"
								+ "                <td style=\"background:#12a05b; padding:24px 24px 18px 24px; text-align:center;\">\r\n"
								+ "                  <!-- Replace src with your hosted logo asset -->\r\n"
								+ "                  <img src=\"https://staging.cotodel.com/img/emailer_cotodel_without_tagline.png\" width=\"120\" height=\"32\" alt=\"cotodel\" style=\"display:block; margin:0 auto; border:0; outline:none; text-decoration:none;\" />\r\n"
								+ "                  <div style=\"font: 14px/20px -apple-system,Segoe UI,Roboto,Arial,sans-serif; color:#e9fff5; margin-top:8px;\">\r\n"
								+ "                    Business expenses made seamless like your personal UPI spends\r\n"
								+ "                  </div>\r\n"
								+ "                </td>\r\n"
								+ "              </tr>\r\n"
								+ "              <!-- Body card -->\r\n"
								+ "              <tr>\r\n"
								+ "                <td style=\"background:#ffffff; padding:28px 32px;\" class=\"px-32 py-24\">\r\n"
								+ "                  <div style=\"font:16px/24px -apple-system,Segoe UI,Roboto,Arial,sans-serif; color:#111827;\">\r\n"
								+ "                    <p style=\"margin:0 0 16px 0;\">Dear "+item.getName()+",</p>\r\n"
								+ "                    <p style=\"margin:0 0 20px 0;\">A <strong>UPI Voucher</strong> has been <strong>issued</strong> to you by <strong>Cotodel</strong>.</p>\r\n"
								+ "                  </div>\r\n"
								+ "                  <!-- Hero image -->\r\n"
								+ "                  <!-- Replace src with your hosted illustration (e.g., from the design). -->\r\n"
								+ "                  <img src=\"https://staging.cotodel.com/img/IssueVoucherEmailerImg.png\" width=\"536\" alt=\"UPI Voucher illustration\" class=\"fluid-img\" style=\"width:100%; max-width:536px; height:auto; display:block; margin:8px auto 20px; border:0;\" />\r\n"
								+ "\r\n"
								+ "                  <!-- Voucher details -->\r\n"
								+ "                  <table role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\" style=\"font:14px/22px -apple-system,Segoe UI,Roboto,Arial,sans-serif; color:#111827;\">\r\n"
								+ "                    <tr>\r\n"
								+ "                      <td style=\"width:92px; font-weight:700; padding:2px 0; color:#111827;\">Value:</td>\r\n"
								+ "                      <td style=\"padding:2px 0;\">₹"+item.getAmount()+" </td>\r\n"
								+ "                    </tr>\r\n"
								+ "                    <tr>\r\n"
								+ "                      <td style=\"width:92px; font-weight:700; padding:2px 0; color:#111827;\">Category:</td>\r\n"
								+ "                      <td style=\"padding:2px 0;\">"+item.getMccDescription()+"</td>\r\n"
								+ "                    </tr>\r\n"
								+ "                    <tr>\r\n"
								+ "                      <td style=\"width:92px; font-weight:700; padding:2px 0; color:#111827;\">Usage:</td>\r\n"
								+ "                      <td style=\"padding:2px 0;\">"+item.getRedemtionType()+" transaction</td>\r\n"
								+ "                    </tr>\r\n"
								+ "                    <tr>\r\n"
								+ "                      <td style=\"width:92px; font-weight:700; padding:2px 0; color:#111827;\">Issued On:</td>\r\n"
								+ "                      <td style=\"padding:2px 0;\">"+item.getCreationDate()+"</td>\r\n"
								+ "                    </tr>\r\n"
								+ "                    <tr>\r\n"
								+ "                      <td style=\"width:92px; font-weight:700; padding:2px 0; color:#111827;\">Valid Until:</td>\r\n"
								+ "                      <td style=\"padding:2px 0;\">"+item.getExpDate()+"</td>\r\n"
								+ "                    </tr>\r\n"
								+ "                  </table>\r\n"
								+ "\r\n"
								+ "                  <div style=\"font:14px/22px -apple-system,Segoe UI,Roboto,Arial,sans-serif; color:#111827; margin-top:18px;\">\r\n"
								+ "                    Use your Google Pay app to pay for your Business expenses via these UPI Vouchers.\r\n"
								+ "                  </div>\r\n"
								+ "\r\n"
								+ "                  <div style=\"font:14px/22px -apple-system,Segoe UI,Roboto,Arial,sans-serif; color:#111827; margin-top:18px;\">\r\n"
								+ "                    If you have any concerns about this voucher, please reach out to your Business admin or contact us at\r\n"
								+ "                    <a href=\"mailto:support@cotodel.com\" style=\"color:#12a05b; text-decoration:underline;\">support@cotodel.com</a>.\r\n"
								+ "                  </div>\r\n"
								+ "                  <div style=\"font:14px/22px -apple-system,Segoe UI,Roboto,Arial,sans-serif; color:#111827; margin-top:20px;\">\r\n"
								+ "                    Sincerely,<br />\r\n"
								+ "                    Team Cotodel\r\n"
								+ "                  </div>\r\n"
								+ "                </td>\r\n"
								+ "              </tr>\r\n"
								+ "              <!-- Divider & disclaimer -->\r\n"
								+ "              <tr>\r\n"
								+ "                <td style=\"background:#ffffff; padding:0 32px 24px;\" class=\"px-32\">\r\n"
								+ "                  <div style=\"height:1px; background:#e5e7eb; width:100%;\"></div>\r\n"
								+ "                  <div style=\"font:10px/16px -apple-system,Segoe UI,Roboto,Arial,sans-serif; color:#6b7280; margin-top:12px; text-align:right;\">\r\n"
								+ "                    Disclaimer: Please mark us as &quot;Not Spam&quot; to receive future emails from Cotodel in your inbox.\r\n"
								+ "                  </div>\r\n"
								+ "                </td>\r\n"
								+ "              </tr>\r\n"
								+ "              <!-- Footer -->\r\n"
								+ "              <tr>\r\n"
								+ "                <td style=\"background:#ffff; padding:24px 32px 40px;\" class=\"px-32\">\r\n"
								+ "                  <table role=\"presentation\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\r\n"
								+ "                    <tr>\r\n"
								+ "                      <td class=\"text-center-sm\" style=\"vertical-align:top;\">\r\n"
								+ "                        <img src=\"https://staging.cotodel.com/img/Cotodel_Logo.png\" width=\"96\" height=\"24\" alt=\"cotodel\" style=\"display:block; border:0;\" />\r\n"
								+ "                        <div style=\"font:10px/16px -apple-system,Segoe UI,Roboto,Arial,sans-serif; color:#6b7280; margin-top:10px;\">\r\n"
								+ "                          WeWork, Eldeco Centre, Malviya Nagar New Delhi - 110017\r\n"
								+ "                        </div>\r\n"
								+ "                      </td>\r\n"
								+ "                    </tr>\r\n"
								+ "                  </table>\r\n"
								+ "                  <table role=\"presentation\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"margin-top:14px;\">\r\n"
								+ "                    <tr>\r\n"
								+ "                      <td class=\"text-center-sm\" valign=\"middle\" style=\"font:10px/16px -apple-system,Segoe UI,Roboto,Arial,sans-serif; color:#9ca3af; vertical-align:middle;\">\r\n"
								+ "                        © {{year}} Cotodel · All rights reserved\r\n"
								+ "                      </td>\r\n"
								+ "                      <td align=\"right\" valign=\"middle\" style=\"white-space:nowrap; vertical-align:middle;\">\r\n"
								+ "                        <a href=\"https://www.linkedin.com/company/cotopay/posts/?feedView=all\" style=\"display:inline-block; margin-left:8px; position:relative; top:-2px;\">\r\n"
								+ "                          <img src=\"https://staging.cotodel.com/img/Emailer_Linkedin_Logo.png\" width=\"20\" height=\"20\" alt=\"LinkedIn\" style=\"display:block; border:0;\" />\r\n"
								+ "                        </a>\r\n"
								+ "                        <a href=\"https://www.instagram.com/cotodelsolutions\" style=\"display:inline-block; margin-left:8px;\">\r\n"
								+ "                          <img src=\"https://staging.cotodel.com/img/Emailer_Instagram_Logo.png\" width=\"20\" height=\"20\" alt=\"Instagram\" style=\"display:block; border:0;\" />\r\n"
								+ "                        </a>\r\n"
								+ "                        <a href=\"https://www.youtube.com/@Cotodel/\" style=\"display:inline-block; margin-left:8px; position:relative; top:2px;\">\r\n"
								+ "                          <img src=\"https://staging.cotodel.com/img/Emailer_YouTube_Logo.png\" width=\"24\" height=\"24\" alt=\"YouTube\" style=\"display:block; border:0;\" />\r\n"
								+ "                        </a>\r\n"
								+ "                      </td>\r\n"
								+ "                    </tr>\r\n"
								+ "                  </table>\r\n"
								+ "                </td>\r\n"
								+ "              </tr>\r\n"
								+ "			</table>\r\n"
								+ "            </td>\r\n"
								+ "          </tr>\r\n"
								+ "        </table>\r\n"
								+ "      </td>\r\n"
								+ "    </tr>\r\n"
								+ "      </table>\r\n"
								+ "    </center>\r\n"
								+ "  </body>\r\n"
								+ "</html>\r\n"
								+ "";
		 				Map<String, Object> response = GraphMailSender.sendMail(GraphMailSender.acquireToken(), "support@cotodel.com",
		     			email,  "📲 Your UPI Voucher is Issued",  bodyText);
		            } catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	                
	            }
	        	
	            List<Object> dataList = apiJsonResponse.getJSONArray("data").toList();
	            responseMap.put("data", dataList);
	            
	            
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
	
	@PostMapping(value = "/beneficiaryDeleteFromVoucherList")
	public @ResponseBody String beneficiaryDeleteFromVoucherList(HttpServletRequest request, ModelMap model, Locale locale,
			HttpSession session, BulkVoucherRequest bulkVoucherRequest) {
		String profileRes = null;
	
		try {
			String json = EncryptionDecriptionUtil.convertToJson(bulkVoucherRequest);

			EncriptResponse jsonObject=EncryptionDecriptionUtil.encriptResponse(json, applicationConstantConfig.apiSignaturePublicPath);

			String encriptResponse = erupiVoucherCreateDetailsService.beneficiaryDeleteFromVoucherList(tokengeneration.getToken(), jsonObject);

   
			EncriptResponse userReqEnc =EncryptionDecriptionUtil.convertFromJson(encriptResponse, EncriptResponse.class);

			profileRes =  EncryptionDecriptionUtil.decriptResponse(userReqEnc.getEncriptData(), userReqEnc.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
   
	return profileRes;
	}
	
	@PostMapping(value = "/erupiVoucherRevokeBulk")
	public @ResponseBody String erupiVoucherRevokeBulk(HttpServletRequest request, ModelMap model, Locale locale,
			HttpSession session, ErupiVoucherRevokeDetailsBulkRequest erupiVoucherRevokeDetailsBulkRequest) {
		String profileRes = null;
		String smsResponse = null;
		String bodyText = null;
		String email = (String) session.getAttribute("email");
		try {
			String json = EncryptionDecriptionUtil.convertToJson(erupiVoucherRevokeDetailsBulkRequest);

			EncriptResponse jsonObject=EncryptionDecriptionUtil.encriptResponse(json, applicationConstantConfig.apiSignaturePublicPath);

			String encriptResponse = erupiVoucherCreateDetailsService.erupiVoucherRevokeBulk(tokengeneration.getToken(), jsonObject);

   
			EncriptResponse userReqEnc =EncryptionDecriptionUtil.convertFromJson(encriptResponse, EncriptResponse.class);

			profileRes =  EncryptionDecriptionUtil.decriptResponse(userReqEnc.getEncriptData(), userReqEnc.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
		
			// Start SMS and Email service
			JSONObject apiJsonResponse = new JSONObject(profileRes); 
	        boolean status = apiJsonResponse.getBoolean("status");
			if(status && apiJsonResponse.has("data")) {
			// Map JSON array to List<VoucherData>
			JSONArray dataArray = apiJsonResponse.getJSONArray("data");
			 ObjectMapper objMapper = new ObjectMapper();
			
			  List<RevokeResponse> revokeResponse = objMapper.readValue(
		                dataArray.toString(),
		                objMapper.getTypeFactory().constructCollectionType(List.class, RevokeResponse.class)
		            );
			  for (RevokeResponse item : revokeResponse) {
		            SMSRequest smsRequest = new SMSRequest();
		        	smsRequest.setMobile(item.getMobile());
		        	//smsRequest.setValue(revokeResponse .getRevokeAmount());
		        	
		        	String template = "UPI Voucher worth ₹#VAR1# for #VAR2# Spends is revoked! View details and manage via your Cotodel dashboard.";
		        	String finalMessage = template
		        	.replace("#VAR1#", item.getRevokeAmount())
        	        .replace("#VAR2#",item.getMccDescription());
        	
		        	smsRequest.setMessage(finalMessage);
		        	
		            try {
		            String userFormjson = EncryptionDecriptionUtil.convertToJson(smsRequest);
		
					EncriptResponse userFormjsonObject=EncryptionDecriptionUtil.encriptResponse(userFormjson, applicationConstantConfig.apiSignaturePublicPath);
		
					String userFormResponse = loginservice.sendTransactionalSMS(tokengeneration.getToken(), userFormjsonObject);
		   
					EncriptResponse userFornReqEnc =EncryptionDecriptionUtil.convertFromJson(userFormResponse, EncriptResponse.class);
			
					smsResponse =  EncryptionDecriptionUtil.decriptResponse(userFornReqEnc.getEncriptData(), userFornReqEnc.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
					
					JSONObject smsapiJsonResponse = new JSONObject(smsResponse); 

		 			bodyText = "\r\n"
		 					+ "<!DOCTYPE html>\r\n"
		 					+ "<html lang=\"en\" xmlns=\"http://www.w3.org/1999/xhtml\">\r\n"
		 					+ "  <head>\r\n"
		 					+ "    <meta charset=\"utf-8\" />\r\n"
		 					+ "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\" />\r\n"
		 					+ "    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\" />\r\n"
		 					+ "    <meta name=\"x-apple-disable-message-reformatting\" />\r\n"
		 					+ "    <meta name=\"format-detection\" content=\"telephone=no,address=no,email=no,date=no,url=no\" />\r\n"
		 					+ "    <title>Cotodel – UPI Voucher Issued</title>\r\n"
		 					+ "    <!-- Why: modest client-level styles for better defaults; heavy styles are inlined below. -->\r\n"
		 					+ "    <style>\r\n"
		 					+ "      html,\r\n"
		 					+ "      body {\r\n"
		 					+ "        margin: 0 !important;\r\n"
		 					+ "        padding: 0 !important;\r\n"
		 					+ "        height: 100% !important;\r\n"
		 					+ "        width: 100% !important;\r\n"
		 					+ "      }\r\n"
		 					+ "      /* Why: stop iOS blue links */\r\n"
		 					+ "      a[x-apple-data-detectors] {\r\n"
		 					+ "        color: inherit !important;\r\n"
		 					+ "        text-decoration: none !important;\r\n"
		 					+ "      }\r\n"
		 					+ "      /* Why: make images fluid on small screens */\r\n"
		 					+ "      @media only screen and (max-width: 600px) {\r\n"
		 					+ "        .container {\r\n"
		 					+ "          width: 100% !important;\r\n"
		 					+ "          max-width: 100% !important;\r\n"
		 					+ "        }\r\n"
		 					+ "        .px-32 { padding-left: 16px !important; padding-right: 16px !important; }\r\n"
		 					+ "        .py-24 { padding-top: 16px !important; padding-bottom: 16px !important; }\r\n"
		 					+ "        .fluid-img { width: 100% !important; height: auto !important; }\r\n"
		 					+ "        .text-center-sm { text-align: center !important; }\r\n"
		 					+ "      }\r\n"
		 					+ "    </style>\r\n"
		 					+ "  </head>\r\n"
		 					+ "  <body style=\"margin:0; padding:0; background-color:#f5f7fa;\">\r\n"
		 					+ "    <!-- Preheader (hidden) -->\r\n"
		 					+ "    <div style=\"display:none; font-size:1px; line-height:1px; max-height:0; max-width:0; opacity:0; overflow:hidden; mso-hide:all;\">\r\n"
		 					+ "      Your UPI Voucher has been issued by {{business_name}}.\r\n"
		 					+ "    </div>\r\n"
		 					+ "    <center role=\"article\" aria-roledescription=\"email\" lang=\"en\" style=\"width:100%; background:#f5f7fa;\">\r\n"
		 					+ "      <table role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\" style=\"background:#f5f7fa;\">\r\n"
		 					+ "        <tr>\r\n"
		 					+ "          <td align=\"center\">\r\n"
		 					+ "            <!-- Outer container -->\r\n"
		 					+ "            <table role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"600\" class=\"container\" style=\"width:600px; max-width:600px;\">\r\n"
		 					+ "              <!-- Header -->\r\n"
		 					+ "              <tr>\r\n"
		 					+ "                <td style=\"background:#12a05b; padding:24px 24px 18px 24px; text-align:center;\">\r\n"
		 					+ "                  <!-- Replace src with your hosted logo asset -->\r\n"
		 					+ "                  <img src=\"https://staging.cotodel.com/img/emailer_cotodel_without_tagline.png\" width=\"120\" height=\"32\" alt=\"cotodel\" style=\"display:block; margin:0 auto; border:0; outline:none; text-decoration:none;\" />\r\n"
		 					+ "                  <div style=\"font: 14px/20px -apple-system,Segoe UI,Roboto,Arial,sans-serif; color:#e9fff5; margin-top:8px;\">\r\n"
		 					+ "                    Business expenses made seamless like your personal UPI spends\r\n"
		 					+ "                  </div>\r\n"
		 					+ "                </td>\r\n"
		 					+ "              </tr>\r\n"
		 					+ "              <!-- Body card -->\r\n"
		 					+ "              <tr>\r\n"
		 					+ "                <td style=\"background:#ffffff; padding:28px 32px;\" class=\"px-32 py-24\">\r\n"
		 					+ "                  <div style=\"font:16px/24px -apple-system,Segoe UI,Roboto,Arial,sans-serif; color:#111827;\">\r\n"
		 					+ "                    <p style=\"margin:0 0 16px 0;\">Dear Customer,</p>\r\n"
		 					+ "                    <p style=\"margin:0 0 20px 0;\">We’d like to inform you that a   <strong>UPI Voucher</strong> has been <strong>revoked</strong> by <strong> "+session.getAttribute("organizationName")+"</strong>.</p>\r\n"
		 					+ "                  </div>\r\n"
		 					+ "                  <!-- Hero image -->\r\n"
		 					+ "                  <!-- Replace src with your hosted illustration (e.g., from the design).\r\n"
		 					+ "                  <img src=\"https://staging.cotodel.com/img/IssueVoucherEmailerImg.png\" width=\"536\" alt=\"UPI Voucher illustration\" class=\"fluid-img\" style=\"width:100%; max-width:536px; height:auto; display:block; margin:8px auto 20px; border:0;\" />\r\n"
		 					+ "				  -->\r\n"
		 					+ "                  <!-- Voucher details -->\r\n"
		 					+ "                  <table role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\" style=\"font:14px/22px -apple-system,Segoe UI,Roboto,Arial,sans-serif; color:#111827;\">\r\n"
		 					+ "                    <tr>\r\n"
		 					+ "                      <td style=\"width:92px; font-weight:700; padding:2px 0; color:#111827;\">Value:</td>\r\n"
		 					+ "                      <td style=\"padding:2px 0;\">₹"+item.getRevokeAmount()+" </td>\r\n"
		 					+ "                    </tr>\r\n"
		 					+ "                    <tr>\r\n"
		 					+ "                      <td style=\"width:92px; font-weight:700; padding:2px 0; color:#111827;\">Category:</td>\r\n"
		 					+ "                      <td style=\"padding:2px 0;\">"+item.getMccDescription()+"</td>\r\n"
		 					+ "                    </tr>\r\n"
		 					+ "                    <tr>\r\n"
		 					+ "                      <td style=\"width:92px; font-weight:700; padding:2px 0; color:#111827;\">Usage:</td>\r\n"
		 					+ "                      <td style=\"padding:2px 0;\">"+item.getRedemtionType()+" Transactions</td>\r\n"
		 					+ "                    </tr>\r\n"
		 					+ "                    <tr>\r\n"
		 					+ "                      <td style=\"width:92px; font-weight:700; padding:2px 0; color:#111827;\">Issued On:</td>\r\n"
		 					+ "                      <td style=\"padding:2px 0;\">"+item.getCreationDate()+"</td>\r\n"
		 					+ "                    </tr>\r\n"
		 					+ "                    <tr>\r\n"
		 					+ "                      <td style=\"width:92px; font-weight:700; padding:2px 0; color:#111827;\">Valid Until:</td>\r\n"
		 					+ "                      <td style=\"padding:2px 0;\">"+item.getExpDate()+"</td>\r\n"
		 					+ "                    </tr>\r\n"
		 					+ "                  </table>\r\n"
		 					+ "\r\n"
		 					+ "                  <div style=\"font:14px/22px -apple-system,Segoe UI,Roboto,Arial,sans-serif; color:#111827; margin-top:18px;\">\r\n"
		 					+ "                    This voucher is no longer valid and cannot be redeemed.\r\n"
		 					+ "                  </div>\r\n"
		 					+ "\r\n"
		 					+ "                  <div style=\"font:14px/22px -apple-system,Segoe UI,Roboto,Arial,sans-serif; color:#111827; margin-top:18px;\">\r\n"
		 					+ "                    If you have any concerns about this voucher, please reach out to your Business admin or contact us at\r\n"
		 					+ "                    <a href=\"mailto:support@cotodel.com\" style=\"color:#12a05b; text-decoration:underline;\">support@cotodel.com</a>.\r\n"
		 					+ "                  </div>\r\n"
		 					+ "                  <div style=\"font:14px/22px -apple-system,Segoe UI,Roboto,Arial,sans-serif; color:#111827; margin-top:20px;\">\r\n"
		 					+ "                    Sincerely,<br />\r\n"
		 					+ "                    Team Cotodel\r\n"
		 					+ "                  </div>\r\n"
		 					+ "                </td>\r\n"
		 					+ "              </tr>\r\n"
		 					+ "              <!-- Divider & disclaimer -->\r\n"
		 					+ "              <tr>\r\n"
		 					+ "                <td style=\"background:#ffffff; padding:0 32px 24px;\" class=\"px-32\">\r\n"
		 					+ "                  <div style=\"height:1px; background:#e5e7eb; width:100%;\"></div>\r\n"
		 					+ "                  <div style=\"font:10px/16px -apple-system,Segoe UI,Roboto,Arial,sans-serif; color:#6b7280; margin-top:12px; text-align:right;\">\r\n"
		 					+ "                    Disclaimer: Please mark us as &quot;Not Spam&quot; to receive future emails from Cotodel in your inbox.\r\n"
		 					+ "                  </div>\r\n"
		 					+ "                </td>\r\n"
		 					+ "              </tr>\r\n"
		 					+ "              <!-- Footer -->\r\n"
		 					+ "              <tr>\r\n"
		 					+ "                <td style=\"background:#ffff; padding:24px 32px 40px;\" class=\"px-32\">\r\n"
		 					+ "                  <table role=\"presentation\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\r\n"
		 					+ "                    <tr>\r\n"
		 					+ "                      <td class=\"text-center-sm\" style=\"vertical-align:top;\">\r\n"
		 					+ "                        <img src=\"https://staging.cotodel.com/img/Cotodel_Logo.png\" width=\"96\" height=\"24\" alt=\"cotodel\" style=\"display:block; border:0;\" />\r\n"
		 					+ "                        <div style=\"font:10px/16px -apple-system,Segoe UI,Roboto,Arial,sans-serif; color:#6b7280; margin-top:10px;\">\r\n"
		 					+ "                          WeWork, Eldeco Centre, Malviya Nagar New Delhi - 110017\r\n"
		 					+ "                        </div>\r\n"
		 					+ "                      </td>\r\n"
		 					+ "                    </tr>\r\n"
		 					+ "                  </table>\r\n"
		 					+ "                  <table role=\"presentation\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"margin-top:14px;\">\r\n"
		 					+ "                    <tr>\r\n"
		 					+ "                      <td class=\"text-center-sm\" valign=\"middle\" style=\"font:10px/16px -apple-system,Segoe UI,Roboto,Arial,sans-serif; color:#9ca3af; vertical-align:middle;\">\r\n"
		 					+ "                        © {{year}} Cotodel · All rights reserved\r\n"
		 					+ "                      </td>\r\n"
		 					+ "                      <td align=\"right\" valign=\"middle\" style=\"white-space:nowrap; vertical-align:middle;\">\r\n"
		 					+ "                        <a href=\"https://www.linkedin.com/\" style=\"display:inline-block; margin-left:8px; position:relative; top:-2px;\">\r\n"
		 					+ "                          <img src=\"https://staging.cotodel.com/img/Emailer_Linkedin_Logo.png\" width=\"20\" height=\"20\" alt=\"LinkedIn\" style=\"display:block; border:0;\" />\r\n"
		 					+ "                        </a>\r\n"
		 					+ "                        <a href=\"https://www.instagram.com/\" style=\"display:inline-block; margin-left:8px;\">\r\n"
		 					+ "                          <img src=\"https://staging.cotodel.com/img/Emailer_Instagram_Logo.png\" width=\"20\" height=\"20\" alt=\"Instagram\" style=\"display:block; border:0;\" />\r\n"
		 					+ "                        </a>\r\n"
		 					+ "                        <a href=\"https://www.youtube.com/\" style=\"display:inline-block; margin-left:8px; position:relative; top:2px;\">\r\n"
		 					+ "                          <img src=\"https://staging.cotodel.com/img/Emailer_YouTube_Logo.png\" width=\"24\" height=\"24\" alt=\"YouTube\" style=\"display:block; border:0;\" />\r\n"
		 					+ "                        </a>\r\n"
		 					+ "                      </td>\r\n"
		 					+ "                    </tr>\r\n"
		 					+ "                  </table>\r\n"
		 					+ "                </td>\r\n"
		 					+ "              </tr>\r\n"
		 					+ "</table>\r\n"
		 					+ "                </td>\r\n"
		 					+ "              </tr>\r\n"
		 					+ "            </table>\r\n"
		 					+ "          </td>\r\n"
		 					+ "        </tr>\r\n"
		 					+ "      </table>\r\n"
		 					+ "    </center>\r\n"
		 					+ "  </body>\r\n"
		 					+ "</html>\r\n"
		 					+ "";
		 				Map<String, Object> response = GraphMailSender.sendMail(GraphMailSender.acquireToken(), "support@cotodel.com",
		     			email,  "❌ Your UPI Voucher is Revoked",  bodyText);
		            } catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		            try {
		            	WhatsAppRequest whatsapp = new WhatsAppRequest();
		                whatsapp.setSource("new-landing-page form");
		                whatsapp.setCampaignName("revoke_voucher_user");
		                whatsapp.setFirstName(item.getName());
		                whatsapp.setAmount(item.getRevokeAmount());
		                whatsapp.setCategory(item.getMccDescription());
		                whatsapp.setMobile(item.getMobile());
		                whatsapp.setOrganizationName("Cotodel");
		                whatsapp.setValidity(item.getExpDate());
		                whatsapp.setType(item.getRedemtionType());
		                whatsapp.setUserName("Cotodel Communications");
		    			String whatsappJson = EncryptionDecriptionUtil.convertToJson(whatsapp);
		
		    			EncriptResponse whatsappJsonObject=EncryptionDecriptionUtil.encriptResponse(whatsappJson, applicationConstantConfig.apiSignaturePublicPath);
		
		    			String whatsappEncriptResponse =  erupiVoucherCreateDetailsService.sendWhatsupMessage(tokengeneration.getToken(), whatsappJsonObject);
		       
		    			EncriptResponse whatsappReqEnc =EncryptionDecriptionUtil.convertFromJson(whatsappEncriptResponse, EncriptResponse.class);
		
		    			String whatsappReqRes =  EncryptionDecriptionUtil.decriptResponse(whatsappReqEnc.getEncriptData(), whatsappReqEnc.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
		    		} catch (Exception e) {
		    			// TODO Auto-generated catch block
		    			e.printStackTrace();
		    		}
		         //End Start SMS and Email service
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
   
	return profileRes;
	}
	
	@GetMapping(value = "/getVoucherTemplate")
	public ResponseEntity<InputStreamResource> getVoucherTemplate() {
		try {
			//String filePath ="D:\\opt\\file\\"; //local path 
			String filePath ="/opt/cotodel/key/";
			String fileName = "Bulk_Voucher_Templates.xlsx";
			File file = new File(filePath+fileName);
			HttpHeaders headers = new HttpHeaders();    
			
			headers.add("content-disposition", "inline;filename=" +fileName);

			InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

			return ResponseEntity.ok()
					.headers(headers)
					.contentLength(file.length())
					.contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
					.body(resource);

		}catch (Exception e) {
			logger.info(e.getMessage());// TODO: handle exception
		}
		return null;
	}
//	@PostMapping("/generateVoucherExcelTemp")
//	public ResponseEntity<ByteArrayResource> generateTempExcel(@RequestBody List<Map<String, String>> filteredData) {
//	    try {
//	        File file = new File("/opt/cotodel/key/Bulk_Voucher_Templates.xlsx");
//	        if (!file.exists()) {
//	            System.err.println("❌ Excel template file not found at: " + file.getAbsolutePath());
//	            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
//	        }
//
//	        FileInputStream fis = new FileInputStream(file);
//	        Workbook workbook = new XSSFWorkbook(fis);
//	        Sheet sheet = workbook.createSheet("Organization Users");
//
//	        Row header = sheet.createRow(0);
//	        header.createCell(0).setCellValue("User's Name");
//	        header.createCell(1).setCellValue("User's Mobile Number");
//
//	        int rowIdx = 1;
//	        for (Map<String, String> emp : filteredData) {
//	            Row row = sheet.createRow(rowIdx++);
//	            row.createCell(0).setCellValue(emp.getOrDefault("name", ""));
//	            row.createCell(1).setCellValue(emp.getOrDefault("mobile", ""));
//	        }
//	        //sheet.protectSheet("lock123");//lock the sheet so user cannot edit it password for unlocking is lock123
//	        ByteArrayOutputStream out = new ByteArrayOutputStream();
//	        workbook.write(out);
//	        workbook.close();
//
//	        ByteArrayResource resource = new ByteArrayResource(out.toByteArray());
//	        HttpHeaders headers = new HttpHeaders();
//	        headers.add("Content-Disposition", "attachment; filename=ModifiedVoucherExcelTemp.xlsx");
//
//	        return ResponseEntity.ok()
//	                .headers(headers)
//	                .contentLength(resource.contentLength())
//	                .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
//	                .body(resource);
//	    } catch (Exception e) {
//	        e.printStackTrace(); // Log actual error for debug
//	        return ResponseEntity.internalServerError().build();
//	    }
//	}
	@PostMapping("/generateVoucherExcelTemp")
	public ResponseEntity<ByteArrayResource> generateTempExcel(@RequestBody List<Map<String, String>> filteredData) {
	    try {
	        File file = new File("/opt/cotodel/key/Bulk_Voucher_Templates.xlsx");

	        if (!file.exists()) {
	            System.err.println("❌ Excel template file not found at: " + file.getAbsolutePath());
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	        }

	        FileInputStream fis = new FileInputStream(file);
	        Workbook workbook = new XSSFWorkbook(fis);

	        String sheetName = "Organization Users";
	        Sheet sheet = workbook.getSheet(sheetName);

	        // If sheet doesn't exist, create it
	        if (sheet == null) {
	            sheet = workbook.createSheet(sheetName);
	        } else {
	            // If sheet exists, clear previous rows
	            int lastRow = sheet.getLastRowNum();
	            for (int i = 0; i <= lastRow; i++) {
	                Row row = sheet.getRow(i);
	                if (row != null) sheet.removeRow(row);
	            }
	        }

	        // ✅ Write header
	        Row header = sheet.createRow(0);
	        header.createCell(0).setCellValue("User's Name");
	        header.createCell(1).setCellValue("User's Mobile Number");

	        // ✅ Write data rows
	        int rowIdx = 1;
	        for (Map<String, String> emp : filteredData) {
	            Row row = sheet.createRow(rowIdx++);
	            row.createCell(0).setCellValue(emp.getOrDefault("name", ""));
	            row.createCell(1).setCellValue(emp.getOrDefault("mobile", ""));
	        }

	        // Optional: Protect the sheet
	        // sheet.protectSheet("lock123");

	        ByteArrayOutputStream out = new ByteArrayOutputStream();
	        workbook.write(out);
	        workbook.close();

	        ByteArrayResource resource = new ByteArrayResource(out.toByteArray());
	        HttpHeaders headers = new HttpHeaders();
	        headers.add("Content-Disposition", "attachment; filename=ModifiedVoucherExcelTemp.xlsx");

	        return ResponseEntity.ok()
	                .headers(headers)
	                .contentLength(resource.contentLength())
	                .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
	                .body(resource);

	    } catch (Exception e) {
	        e.printStackTrace();
	        return ResponseEntity.internalServerError().build();
	    }
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
