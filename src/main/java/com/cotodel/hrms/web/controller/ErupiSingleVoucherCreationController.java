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
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.cotodel.hrms.web.properties.ApplicationConstantConfig;
import com.cotodel.hrms.web.response.EmployeeMassterRequest;
import com.cotodel.hrms.web.response.EmployeeOnboarding;
import com.cotodel.hrms.web.response.ErupiVoucherCreateDetails;
import com.cotodel.hrms.web.response.ErupiVoucherStatusSmsRequest;
import com.cotodel.hrms.web.response.ExistingUserVoucherCreationRequest;
import com.cotodel.hrms.web.response.ReputeEmployeeRequest;
import com.cotodel.hrms.web.response.RoleAccessRequest;
import com.cotodel.hrms.web.response.UserDetailsEntity;
import com.cotodel.hrms.web.service.ErupiVoucherCreateDetailsService;
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
	ErupiVoucherCreateDetailsService erupiVoucherCreateDetailsService;
	
	@PostMapping(value="/createSingleVoucher")
	public @ResponseBody String createSingleVoucher(HttpServletRequest request, ModelMap model,Locale locale,HttpSession session,
			ErupiVoucherCreateDetails erupiVoucherCreateDetails) {
		String profileRes=null;
		
		//profileRes = erupiVoucherCreateDetailsService.createSingleVoucher(tokengeneration.getToken(),erupiVoucherCreateDetails);
		
		String receivedHash = erupiVoucherCreateDetails.getHash();
		 // Validate client key first
        if (!CLIENT_KEY.equals(erupiVoucherCreateDetails.getClientKey())) {
          //  return Map.of("isValid", false, "message", "Invalid client key");
        }
        String dataString = erupiVoucherCreateDetails.getName()+erupiVoucherCreateDetails.getMobile()+erupiVoucherCreateDetails.getStartDate()
        +erupiVoucherCreateDetails.getValidity()+erupiVoucherCreateDetails.getPurposeCode()+erupiVoucherCreateDetails.getConsent()
        +erupiVoucherCreateDetails.getCreatedby()+ erupiVoucherCreateDetails.getOrgId()+
        erupiVoucherCreateDetails.getMerchantId()+erupiVoucherCreateDetails.getSubMerchantId()+erupiVoucherCreateDetails.getRedemtionType()+
        erupiVoucherCreateDetails.getMcc()+erupiVoucherCreateDetails.getVoucherCode()+
        erupiVoucherCreateDetails.getVoucherDesc() +erupiVoucherCreateDetails.getBankcode()+erupiVoucherCreateDetails
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
            // Call Service
            String encryptedResponse = erupiVoucherCreateDetailsService.createSingleVoucher(tokengeneration.getToken(),erupiVoucherCreateDetails);

            // Decrypt Response
         
            JSONObject apiJsonResponse = new JSONObject(encryptedResponse);
            
            boolean status = apiJsonResponse.getBoolean("status");
	        responseMap.put("status", status);
	        responseMap.put("message", apiJsonResponse.getString("message"));

	        if (status && apiJsonResponse.has("data")) {
	        	JSONObject data = apiJsonResponse.getJSONObject("data");
	        	ErupiVoucherCreateDetails voucher = new ErupiVoucherCreateDetails();
	        	voucher.setName(data.getString("name"));
	        	voucher.setMobile(data.getString("mobile"));
	        	voucher.setName(data.getString("voucherCode"));
	        	voucher.setRedemtionType(data.getString("redemtionType"));
	        	voucher.setAmount(data.getFloat("amount"));
	        	voucher.setStartDate(data.getString("startDate"));
	        	voucher.setValidity(data.getString("validity"));
	        	voucher.setType(data.getString("type"));
	        	voucher.setResponse(data.getString("response"));
	        	voucher.setExpDate(data.getString("expDate"));
	        	
	        	
	        	responseMap.put("data", voucher); // Could be primitive or string
	          
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
	
	@PostMapping(value = "/getVoucherSummaryList")
	public @ResponseBody String getVoucherSummaryList(HttpServletRequest request, ModelMap model, Locale locale,
			HttpSession session, EmployeeMassterRequest erupiVoucherCreateDetails) {
		//String profileRes = null;
		//profileRes = erupiVoucherCreateDetailsService.getVoucherSummaryList(tokengeneration.getToken(),	erupiVoucherCreateDetails);

		//return profileRes;
		
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
		//String profileRes = null;
	
		
		//profileRes = erupiVoucherCreateDetailsService.getTotalVoucherCount(tokengeneration.getToken(),	erupiVoucherCreateDetails);

		//return profileRes;
		
		
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
		//profileRes = erupiVoucherCreateDetailsService.getPrimaryBankDetailsByOrgId(tokengeneration.getToken(),	erupiVoucherCreateDetails);

		//return profileRes;
		

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
			HttpSession session, ErupiVoucherCreateDetails erupiVoucherCreateDetails) {
		String profileRes = null;
		profileRes = erupiVoucherCreateDetailsService.revokeCreatedVoucher(tokengeneration.getToken(),	erupiVoucherCreateDetails);

		return profileRes;
	}
	
	@PostMapping(value = "/erupiVoucheSmsSend")
	public @ResponseBody String erupiVoucheSmsSend(HttpServletRequest request, ModelMap model, Locale locale,
			HttpSession session, ErupiVoucherStatusSmsRequest erupiVoucherStatusSmsRequest) {
		String profileRes = null;
		//profileRes = erupiVoucherCreateDetailsService.erupiVoucheSmsSend(tokengeneration.getToken(),	erupiVoucherCreateDetails);

		//return profileRes;
		
		
		try {
			String json = EncryptionDecriptionUtil.convertToJson(erupiVoucherStatusSmsRequest);

			EncriptResponse jsonObject=EncryptionDecriptionUtil.encriptResponse(json, applicationConstantConfig.apiSignaturePublicPath);

			String encriptResponse =  erupiVoucherCreateDetailsService.erupiVoucheSmsSend(tokengeneration.getToken(), jsonObject);

   
			EncriptResponse userReqEnc =EncryptionDecriptionUtil.convertFromJson(encriptResponse, EncriptResponse.class);

			profileRes =  EncryptionDecriptionUtil.decriptResponse(userReqEnc.getEncriptData(), userReqEnc.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
   
	return profileRes;
	}
//	
//	@PostMapping(value="/geterupiVoucherOldList")
//	public @ResponseBody String geterupiVoucherOldList(HttpServletRequest request, ModelMap model,Locale locale,HttpSession session,
//			ErupiVoucherCreateDetails erupiVoucherCreateDetails) {
//		String profileRes=null;
//		
//		profileRes = erupiVoucherCreateDetailsService.geterupiVoucherOldList(tokengeneration.getToken(),erupiVoucherCreateDetails);
//		
//		return profileRes;
//	}
//	
//	@PostMapping(value="/exitingUserVoucherCreation")
//	public @ResponseBody String exitingUserVoucherCreation(HttpServletRequest request, ModelMap model,Locale locale,HttpSession session,
//			ExistingUserVoucherCreationRequest existingUserVoucherCreationRequest) {
//		String profileRes=null;
//		
//		profileRes = erupiVoucherCreateDetailsService.exitingUserVoucherCreation(tokengeneration.getToken(),existingUserVoucherCreationRequest);
//		
//		return profileRes;
//	}
//	
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
