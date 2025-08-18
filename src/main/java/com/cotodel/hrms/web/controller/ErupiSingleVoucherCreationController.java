package com.cotodel.hrms.web.controller;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.cotodel.hrms.web.properties.ApplicationConstantConfig;
import com.cotodel.hrms.web.response.EmployeeMassterRequest;
import com.cotodel.hrms.web.response.ErupiVoucherCreateDetails;
import com.cotodel.hrms.web.response.ErupiVoucherStatusSmsRequest;
import com.cotodel.hrms.web.response.RevokeResponse;
import com.cotodel.hrms.web.response.RevokeVoucher;
import com.cotodel.hrms.web.response.SMSRequest;
import com.cotodel.hrms.web.response.SingleVoucherCreationRequest;
import com.cotodel.hrms.web.response.UserDetailsEntity;
import com.cotodel.hrms.web.response.UserForm;
import com.cotodel.hrms.web.response.VoucherData;
import com.cotodel.hrms.web.response.WhatsAppRequest;
import com.cotodel.hrms.web.service.ErupiVoucherCreateDetailsService;
import com.cotodel.hrms.web.service.LoginService;
import com.cotodel.hrms.web.service.Impl.EmailServiceImpl;
import com.cotodel.hrms.web.service.Impl.TokenGenerationImpl;
import com.cotodel.hrms.web.util.EncriptResponse;
import com.cotodel.hrms.web.util.EncryptionDecriptionUtil;
import com.cotodel.hrms.web.util.JwtTokenValidator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@CrossOrigin
public class ErupiSingleVoucherCreationController  extends CotoDelBaseController{

	private static final Logger logger = LoggerFactory.getLogger(ErupiSingleVoucherCreationController.class);
	
	private static final String SECRET_KEY = "0123456789012345"; // Must match frontend
    private static final String CLIENT_KEY = "client-secret-key"; // Extra validation


	@Autowired
	public ApplicationConstantConfig applicationConstantConfig;
	
	@Autowired
	TokenGenerationImpl tokengeneration;
	
	@Autowired
	EmailServiceImpl emailService;
	
	@Autowired
	LoginService loginservice;
	
	@Autowired
	ErupiVoucherCreateDetailsService erupiVoucherCreateDetailsService;
	//single voucher Issue backup
	@PostMapping(value="/createSingleVoucherRequested")
	public @ResponseBody String createSingleVoucherOLD(HttpServletRequest request,
			@RequestBody SingleVoucherCreationRequest erupiVoucherCreateDetails, BindingResult result, HttpSession session, 
			ModelMap model,Locale locale)	
	{
		String encryptedResponse=null;
		String receivedHash = erupiVoucherCreateDetails.getHash();
		 // Validate client key first
        if (!CLIENT_KEY.equals(erupiVoucherCreateDetails.getClientKey())) {
          //  return Map.of("isValid", false, "message", "Invalid client key");
        }
        String dataString = 
//        		erupiVoucherCreateDetails.getName()+
//        		erupiVoucherCreateDetails.getMobile()+
//        		erupiVoucherCreateDetails.getStartDate()
//        +erupiVoucherCreateDetails.getValidity()+
//        erupiVoucherCreateDetails.getPurposeCode()+
        erupiVoucherCreateDetails.getConsent()
        +erupiVoucherCreateDetails.getCreatedby()+ 
        erupiVoucherCreateDetails.getOrgId()+
        erupiVoucherCreateDetails.getMerchantId()+
        erupiVoucherCreateDetails.getSubMerchantId()+
//        erupiVoucherCreateDetails.getRedemtionType()+
//        erupiVoucherCreateDetails.getMcc()+
//        erupiVoucherCreateDetails.getVoucherCode()+
//        erupiVoucherCreateDetails.getVoucherDesc() +
        erupiVoucherCreateDetails.getBankcode()+erupiVoucherCreateDetails
        .getAccountNumber()+erupiVoucherCreateDetails.getPayerVA()+erupiVoucherCreateDetails.getMandateType()+
        //erupiVoucherCreateDetails.getAmount()+
        CLIENT_KEY+SECRET_KEY;
		   

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
    Map<String, Object> responseMap = new HashMap<>();
    ObjectMapper mapper = new ObjectMapper();
 
    // Get token from session
//    if (!isValid) {
//        responseMap.put("status", false);
//        responseMap.put("message", "Request Tempered");
//        try {
//            return mapper.writeValueAsString(responseMap);
//        } catch (JsonProcessingException e) {
//            return "{\"status\":false, \"message\":\"JSON processing error\"}";
//        }
//    }
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
    if ((obj.getUser_role() == 9 || obj.getUser_role() == 1 || obj.getUser_role() == 3) && obj.getOrgid() == erupiVoucherCreateDetails.getOrgId().intValue()) {
        try {
            // Call Service
            //String encryptedResponse = erupiVoucherCreateDetailsService.createSingleVoucher(tokengeneration.getToken(),erupiVoucherCreateDetails);
        	try {
    			String json = EncryptionDecriptionUtil.convertToJson(erupiVoucherCreateDetails);

    			EncriptResponse jsonObject=EncryptionDecriptionUtil.encriptResponse(json, applicationConstantConfig.apiSignaturePublicPath);

    			 encryptedResponse = erupiVoucherCreateDetailsService.createSingleVoucher(tokengeneration.getToken(),jsonObject);
            	
    			EncriptResponse userReqEnc =EncryptionDecriptionUtil.convertFromJson(encryptedResponse, EncriptResponse.class);

    			encryptedResponse =  EncryptionDecriptionUtil.decriptResponse(userReqEnc.getEncriptData(), userReqEnc.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
    		} catch (Exception e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
            JSONObject apiJsonResponse = new JSONObject(encryptedResponse);
            
            boolean status = apiJsonResponse.getBoolean("status");
	        responseMap.put("status", status);
	        responseMap.put("message", apiJsonResponse.getString("message"));
	       

	        if (status && apiJsonResponse.has("data")) {

	        	 // Extract the array
	            JSONArray dataArray = apiJsonResponse.getJSONArray("data");

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
	                    whatsapp.setCampaignName("Voucher_Issuance");
	                    whatsapp.setFirstName(item.getName());
	                    whatsapp.setAmount(item.getAmount());
	                    whatsapp.setCategory(item.getVoucherDesc());
	                    whatsapp.setMobile(item.getMobile());
	                    whatsapp.setOrganizationName("Cotodel");
	                    whatsapp.setValidity(item.getValidity());
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
	            }
	        	
	        	 List<Object> dataList = apiJsonResponse.getJSONArray("data").toList();
	        	responseMap.put("data", dataList); // Could be primitive or string
	          
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

	@PostMapping(value="/createSingleVoucher")
	public @ResponseBody String createSingleVoucher(HttpServletRequest request,
			@RequestBody SingleVoucherCreationRequest erupiVoucherCreateDetails, BindingResult result, HttpSession session, 
			ModelMap model,Locale locale) {
		String emailRequest =null;
		String receivedHash = erupiVoucherCreateDetails.getHash();
		 // Validate client key first
        if (!CLIENT_KEY.equals(erupiVoucherCreateDetails.getClientKey())) {
          //  return Map.of("isValid", false, "message", "Invalid client key");
        }
        String dataString = erupiVoucherCreateDetails.getConsent()
        +erupiVoucherCreateDetails.getCreatedby()+ erupiVoucherCreateDetails.getOrgId()+
        erupiVoucherCreateDetails.getMerchantId()+erupiVoucherCreateDetails.getSubMerchantId()+erupiVoucherCreateDetails
        .getAccountNumber()+erupiVoucherCreateDetails.getPayerVA()+erupiVoucherCreateDetails.getMandateType()+
        CLIENT_KEY+SECRET_KEY;
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
    if ((obj.getUser_role() == 9 || obj.getUser_role() == 1 || obj.getUser_role() == 3) && obj.getOrgid() == erupiVoucherCreateDetails.getOrgId().intValue()) {
        try {
        	String profileRes = null;
    		
            // Call Service
           // String encryptedResponse = erupiVoucherCreateDetailsService.createSingleVoucherWithMultipleRequest(tokengeneration.getToken(),erupiVoucherCreateDetails);
        	try {
    			String json = EncryptionDecriptionUtil.convertToJson(erupiVoucherCreateDetails);

    			EncriptResponse jsonObject=EncryptionDecriptionUtil.encriptResponse(json, applicationConstantConfig.apiSignaturePublicPath);

    			String encriptResponse =  erupiVoucherCreateDetailsService.createSingleVoucherWithMultipleRequest(tokengeneration.getToken(), jsonObject);
       
    			EncriptResponse userReqEnc =EncryptionDecriptionUtil.convertFromJson(encriptResponse, EncriptResponse.class);

    			profileRes =  EncryptionDecriptionUtil.decriptResponse(userReqEnc.getEncriptData(), userReqEnc.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
    		} catch (Exception e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
            JSONObject apiJsonResponse = new JSONObject(profileRes);
            
            boolean status = apiJsonResponse.getBoolean("status");
	        responseMap.put("status", status);
	        responseMap.put("message", apiJsonResponse.getString("message"));

	        if (status && apiJsonResponse.has("data")) {
	        	
	        	 // Extract the array
	            JSONArray dataArray = apiJsonResponse.getJSONArray("data");

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
	                    whatsapp.setCampaignName("Voucher_Issuance");
	                    whatsapp.setFirstName(item.getName());
	                    whatsapp.setAmount(item.getAmount());
	                    whatsapp.setCategory(item.getVoucherDesc());
	                    whatsapp.setMobile(item.getMobile());
	                    whatsapp.setOrganizationName("Cotodel");
	                    whatsapp.setValidity(item.getValidity());
	                    whatsapp.setType(item.getRedemtionType());
	                    whatsapp.setUserName("Cotodel Communications");
	                    try {
	            			String json = EncryptionDecriptionUtil.convertToJson(whatsapp);

	            			EncriptResponse jsonObject=EncryptionDecriptionUtil.encriptResponse(json, applicationConstantConfig.apiSignaturePublicPath);

	            			String encriptResponse =  erupiVoucherCreateDetailsService.sendWhatsupMessage(tokengeneration.getToken(), jsonObject);
	               
	            			EncriptResponse userReqEnc =EncryptionDecriptionUtil.convertFromJson(encriptResponse, EncriptResponse.class);

	            			profileRes =  EncryptionDecriptionUtil.decriptResponse(userReqEnc.getEncriptData(), userReqEnc.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
	            		} catch (Exception e) {
	            			// TODO Auto-generated catch block
	            			e.printStackTrace();
	            		}
	                    
	                    emailRequest =	emailService.sendEmail("atulyadavmca@gmail.com");
	                }
	            }

	        	 List<Object> dataList = apiJsonResponse.getJSONArray("data").toList();
	        	responseMap.put("data", dataList); // Could be primitive or string
	          
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
	
	@PostMapping(value = "/getIssueVoucherList")
	public @ResponseBody String getIssueVoucherList(HttpServletRequest request, ModelMap model, Locale locale,
			HttpSession session, EmployeeMassterRequest erupiVoucherCreateDetails) {
		String profileRes = null;
		
		try {
			String json = EncryptionDecriptionUtil.convertToJson(erupiVoucherCreateDetails);

			EncriptResponse jsonObject=EncryptionDecriptionUtil.encriptResponse(json, applicationConstantConfig.apiSignaturePublicPath);

			String encriptResponse =  erupiVoucherCreateDetailsService.getIssueVoucherList(tokengeneration.getToken(), jsonObject);
   
			EncriptResponse userReqEnc =EncryptionDecriptionUtil.convertFromJson(encriptResponse, EncriptResponse.class);

			profileRes =  EncryptionDecriptionUtil.decriptResponse(userReqEnc.getEncriptData(), userReqEnc.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
   
	return profileRes;
	}
	
	@PostMapping(value = "/erupiVoucherCreateListRedeem")
	public @ResponseBody String erupiVoucherCreateListRedeem(HttpServletRequest request, ModelMap model, Locale locale,
			HttpSession session, EmployeeMassterRequest erupiVoucherCreateDetails) {
		String profileRes = null;
		
		try {
			String json = EncryptionDecriptionUtil.convertToJson(erupiVoucherCreateDetails);

			EncriptResponse jsonObject=EncryptionDecriptionUtil.encriptResponse(json, applicationConstantConfig.apiSignaturePublicPath);

			String encriptResponse =  erupiVoucherCreateDetailsService.erupiVoucherCreateListRedeem(tokengeneration.getToken(), jsonObject);
   
			EncriptResponse userReqEnc =EncryptionDecriptionUtil.convertFromJson(encriptResponse, EncriptResponse.class);

			profileRes =  EncryptionDecriptionUtil.decriptResponse(userReqEnc.getEncriptData(), userReqEnc.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
   
	return profileRes;
	}
	
	@PostMapping(value = "/getVoucherSummaryList")
	public @ResponseBody String getVoucherSummaryList(HttpServletRequest request, ModelMap model, Locale locale,
			HttpSession session, EmployeeMassterRequest erupiVoucherCreateDetails) {
		
		String profileRes=null;
			
			try {
				String json = EncryptionDecriptionUtil.convertToJson(erupiVoucherCreateDetails);
	
				EncriptResponse jsonObject=EncryptionDecriptionUtil.encriptResponse(json, applicationConstantConfig.apiSignaturePublicPath);
	
				String encriptResponse =  erupiVoucherCreateDetailsService.getVoucherSummaryList(tokengeneration.getToken(), jsonObject);
	   
				EncriptResponse userReqEnc =EncryptionDecriptionUtil.convertFromJson(encriptResponse, EncriptResponse.class);
	
				profileRes =  EncryptionDecriptionUtil.decriptResponse(userReqEnc.getEncriptData(), userReqEnc.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
       
		return profileRes;
	}
	
	@PostMapping(value = "/getTotalVoucherCount")
	public @ResponseBody String getTotalVoucherCount(HttpServletRequest request, ModelMap model, Locale locale,
			HttpSession session, EmployeeMassterRequest erupiVoucherCreateDetails) {
		
		String profileRes=null;
		
		try {
			String json = EncryptionDecriptionUtil.convertToJson(erupiVoucherCreateDetails);

			EncriptResponse jsonObject=EncryptionDecriptionUtil.encriptResponse(json, applicationConstantConfig.apiSignaturePublicPath);

			String encriptResponse =  erupiVoucherCreateDetailsService.getTotalVoucherCount(tokengeneration.getToken(),	jsonObject);
   
			EncriptResponse userReqEnc =EncryptionDecriptionUtil.convertFromJson(encriptResponse, EncriptResponse.class);

			profileRes =  EncryptionDecriptionUtil.decriptResponse(userReqEnc.getEncriptData(), userReqEnc.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}       
		return profileRes;
	}
	
	
	@PostMapping(value = "/getPrimaryBankDetailsByOrgId")
	public @ResponseBody String getPrimaryBankDetailsByOrgId(HttpServletRequest request, ModelMap model, Locale locale,
			HttpSession session, ErupiVoucherCreateDetails erupiVoucherCreateDetails) {
		String profileRes = null;
		
		try {
			String json = EncryptionDecriptionUtil.convertToJson(erupiVoucherCreateDetails);

			EncriptResponse jsonObject=EncryptionDecriptionUtil.encriptResponse(json, applicationConstantConfig.apiSignaturePublicPath);

			String encriptResponse =  erupiVoucherCreateDetailsService.getPrimaryBankDetailsByOrgId(tokengeneration.getToken(), jsonObject);

			EncriptResponse userReqEnc =EncryptionDecriptionUtil.convertFromJson(encriptResponse, EncriptResponse.class);

			profileRes =  EncryptionDecriptionUtil.decriptResponse(userReqEnc.getEncriptData(), userReqEnc.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
   
	return profileRes;
	
	}
	
	@PostMapping(value = "/revokeCreatedVoucher")
	public @ResponseBody String revokeCreatedVoucher(HttpServletRequest request, ModelMap model, Locale locale,
			HttpSession session, RevokeVoucher erupiVoucherCreateDetails) {
		String profileRes = null;
		//profileRes = erupiVoucherCreateDetailsService.revokeCreatedVoucher(tokengeneration.getToken(),	erupiVoucherCreateDetails);

		try {
			String json = EncryptionDecriptionUtil.convertToJson(erupiVoucherCreateDetails);

			EncriptResponse jsonObject=EncryptionDecriptionUtil.encriptResponse(json, applicationConstantConfig.apiSignaturePublicPath);

			String encriptResponse =  erupiVoucherCreateDetailsService.revokeCreatedVoucherSingle(tokengeneration.getToken(),	jsonObject);

			EncriptResponse userReqEnc =EncryptionDecriptionUtil.convertFromJson(encriptResponse, EncriptResponse.class);

			profileRes =  EncryptionDecriptionUtil.decriptResponse(userReqEnc.getEncriptData(), userReqEnc.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
			
			JSONObject apiJsonResponse = new JSONObject(profileRes); 
	        boolean status = apiJsonResponse.getBoolean("status");
			if(status && apiJsonResponse.has("data")) {
				// Start SMS and Email service
				
				// Map JSON array to List<VoucherData>
				 JSONObject dataObject = apiJsonResponse.getJSONObject("data");
				 ObjectMapper objMapper = new ObjectMapper();
				  RevokeResponse revokeResponse  = objMapper.readValue(
	            			        dataObject.toString(),
	            			        RevokeResponse.class
	            			    );
	            
				SMSRequest userForm = new SMSRequest();
	            userForm.setMobile(revokeResponse .getMobile());
	            userForm.setValue(revokeResponse .getRevokeAmount());
	            userForm.setTemplate("Revoke");
	            try {
	            String userFormjson = EncryptionDecriptionUtil.convertToJson(userForm);

				EncriptResponse userFormjsonObject=EncryptionDecriptionUtil.encriptResponse(userFormjson, applicationConstantConfig.apiSignaturePublicPath);

				String userFormResponse = loginservice.sendOtpWith2Factor(tokengeneration.getToken(), userFormjsonObject);
	   
				EncriptResponse userFornReqEnc =EncryptionDecriptionUtil.convertFromJson(userFormResponse, EncriptResponse.class);

				String smsResponse =  EncryptionDecriptionUtil.decriptResponse(userFornReqEnc.getEncriptData(), userFornReqEnc.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
				//String emailRequest =	emailService.sendEmail(employeeOnboarding.getEmail());
	            } catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	            try {
	            	WhatsAppRequest whatsapp = new WhatsAppRequest();
	                whatsapp.setSource("new-landing-page form");
	                whatsapp.setCampaignName("Voucher Revoke");
	                whatsapp.setFirstName(revokeResponse.getName());
	                whatsapp.setAmount(revokeResponse.getRevokeAmount());
	                whatsapp.setCategory(revokeResponse.getVoucherDesc());
	                whatsapp.setMobile(revokeResponse.getMobile());
	                whatsapp.setOrganizationName("Cotodel");
	                whatsapp.setValidity(revokeResponse.getValidity());
	                whatsapp.setType(revokeResponse.getRedemtionType());
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
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return profileRes;
	}
	
	@PostMapping(value = "/erupiVoucheSmsSend")
	public @ResponseBody String erupiVoucheSmsSend(HttpServletRequest request, ModelMap model, Locale locale,
			HttpSession session, ErupiVoucherStatusSmsRequest erupiVoucherStatusSmsRequest) {
		String profileRes = null;
		try {
			String json = EncryptionDecriptionUtil.convertToJson(erupiVoucherStatusSmsRequest);

			EncriptResponse jsonObject=EncryptionDecriptionUtil.encriptResponse(json, applicationConstantConfig.apiSignaturePublicPath);

			String encriptResponse =  erupiVoucherCreateDetailsService.erupiVoucheSmsSend(tokengeneration.getToken(), jsonObject);
   
			EncriptResponse userReqEnc =EncryptionDecriptionUtil.convertFromJson(encriptResponse, EncriptResponse.class);

			profileRes =  EncryptionDecriptionUtil.decriptResponse(userReqEnc.getEncriptData(), userReqEnc.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
			
			//End Start SMS and Email service
			JSONObject apiJsonResponse = new JSONObject(profileRes); 
	        boolean status = apiJsonResponse.getBoolean("status");
			if(status && apiJsonResponse.has("data")) {
				// Start SMS and Email service
	            UserForm userForm = new UserForm();
	            userForm.setMobile((String) session.getAttribute("mobile"));
	            userForm.setTemplate("Cotodel Voucher Activity");
	            try {
	            String userFormjson = EncryptionDecriptionUtil.convertToJson(userForm);

				EncriptResponse userFormjsonObject=EncryptionDecriptionUtil.encriptResponse(userFormjson, applicationConstantConfig.apiSignaturePublicPath);

				String userFormResponse = loginservice.sendOtpWith2Factor(tokengeneration.getToken(), userFormjsonObject);
	   
				EncriptResponse userFornReqEnc =EncryptionDecriptionUtil.convertFromJson(userFormResponse, EncriptResponse.class);

				String smsResponse =  EncryptionDecriptionUtil.decriptResponse(userFornReqEnc.getEncriptData(), userFornReqEnc.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
				//String emailRequest =	emailService.sendEmail(employeeOnboarding.getEmail());
	            } catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	            try {
	            	WhatsAppRequest whatsapp = new WhatsAppRequest();
	                whatsapp.setSource("new-landing-page form");
	                whatsapp.setCampaignName("Voucher_Issuance");
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
			}
	         //End Start SMS and Email service
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
   
	return profileRes;
	}
	
	@PostMapping(value="/voucherUserSearch")
	public @ResponseBody String voucherUserSearch(HttpServletRequest request, ModelMap model,Locale locale,
			HttpSession session,EmployeeMassterRequest roleAccessRequest) {
			
		String profileRes=null;
		
		try {
			String json = EncryptionDecriptionUtil.convertToJson(roleAccessRequest);

			EncriptResponse jsonObject=EncryptionDecriptionUtil.encriptResponse(json, applicationConstantConfig.apiSignaturePublicPath);

			String encriptResponse =  erupiVoucherCreateDetailsService.voucherUserSearch(tokengeneration.getToken(), jsonObject);

   
			EncriptResponse userReqEnc =EncryptionDecriptionUtil.convertFromJson(encriptResponse, EncriptResponse.class);

			profileRes =  EncryptionDecriptionUtil.decriptResponse(userReqEnc.getEncriptData(), userReqEnc.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
   
	return profileRes;
	}
	@PostMapping(value="/erupiVoucherStatusHistory")
	public @ResponseBody String erupiVoucherStatusHistory(HttpServletRequest request, ModelMap model,Locale locale,
			HttpSession session,ErupiVoucherStatusSmsRequest ErupiVoucherStatusSmsRequest) {
			
		String profileRes=null;
		
		try {
			String json = EncryptionDecriptionUtil.convertToJson(ErupiVoucherStatusSmsRequest);

			EncriptResponse jsonObject=EncryptionDecriptionUtil.encriptResponse(json, applicationConstantConfig.apiSignaturePublicPath);

			String encriptResponse =  erupiVoucherCreateDetailsService.erupiVoucherStatusHistory(tokengeneration.getToken(), jsonObject);

   
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
