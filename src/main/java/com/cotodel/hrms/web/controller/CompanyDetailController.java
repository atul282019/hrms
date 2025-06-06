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
import com.cotodel.hrms.web.response.CompanyProfileDetail;
import com.cotodel.hrms.web.response.EmployeeProfileRequest;
import com.cotodel.hrms.web.response.UserDetailsEntity;
import com.cotodel.hrms.web.service.CompanyService;
import com.cotodel.hrms.web.service.Impl.TokenGenerationImpl;
import com.cotodel.hrms.web.util.EncriptResponse;
import com.cotodel.hrms.web.util.EncryptionDecriptionUtil;
import com.cotodel.hrms.web.util.JwtTokenValidator;
import com.cotodel.hrms.web.util.MessageConstant;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@CrossOrigin
public class CompanyDetailController extends CotoDelBaseController{

	private static final Logger logger = LoggerFactory.getLogger(CompanyDetailController.class);

	@Autowired
	public ApplicationConstantConfig applicationConstantConfig;
	
	@Autowired
	CompanyService companyService;

	@Autowired
	TokenGenerationImpl tokengeneration;
	
	private static final String SECRET_KEY = "0123456789012345"; // Must match frontend
    private static final String CLIENT_KEY = "client-secret-key"; // Extra validation
	
	
	@PostMapping(value="/getGSTDetailsByGSTNumber")
	public @ResponseBody String getGSTDetailsByGSTNumber(HttpServletRequest request, ModelMap model,Locale locale,HttpSession session,EmployeeProfileRequest employeeProfileRequest) {
	
        String profileRes=null;
        try {
			String json = EncryptionDecriptionUtil.convertToJson(employeeProfileRequest);

			EncriptResponse jsonObject=EncryptionDecriptionUtil.encriptResponse(json, applicationConstantConfig.apiSignaturePublicPath);

			String encriptResponse = companyService.getGSTDetailsByGSTNumber(tokengeneration.getToken(), jsonObject);

   
			EncriptResponse userReqEnc =EncryptionDecriptionUtil.convertFromJson(encriptResponse, EncriptResponse.class);

			profileRes =  EncryptionDecriptionUtil.decriptResponse(userReqEnc.getEncriptData(), userReqEnc.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
   
	return profileRes;
	}

	@PostMapping(value="/saveOrganizationDetail")
	public @ResponseBody String saveOrganizationDetail(HttpServletRequest request, ModelMap model,Locale locale,
			HttpSession session,CompanyProfileDetail companyProfileDetail) {
		String profileRes=null;
		String receivedHash = companyProfileDetail.getHash();
		if (!CLIENT_KEY.equals(companyProfileDetail.getClientKey())) {
	          //  return Map.of("isValid", false, "message", "Invalid client key");
	        }
//		const dataString = panNo1+panNo1+legalName+legalName+tradeName+orgType+address1+address2+district+
//				pincode+state+employerName+employerId+clientKey+secretKey;
		String dataString =
				 companyProfileDetail.getPan()+
				 companyProfileDetail.getLegalNameOfBusiness()+
				 companyProfileDetail.getTradeName()+
				 companyProfileDetail.getConstitutionOfBusiness()+
				 companyProfileDetail.getOrgType()+
				companyProfileDetail.getAddress1()+
				companyProfileDetail.getAddress2()+
				companyProfileDetail.getDistrictName()+
				companyProfileDetail.getPincode()+
				companyProfileDetail.getStateName()+
				companyProfileDetail.getGstIdentificationNumber()+
				//companyProfileDetail.getPan()+
				companyProfileDetail.getCreatedBy()+
				companyProfileDetail.getEmployerId()+
				CLIENT_KEY+SECRET_KEY;

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
			    if ((obj.getUser_role() == 9 || obj.getUser_role() == 1 || obj.getUser_role() == 2) && obj.getOrgid() == companyProfileDetail.getEmployerId().intValue()) {

			    	  try {
				            // Convert request object to JSON
				            String json = EncryptionDecriptionUtil.convertToJson(companyProfileDetail);

				            // Encrypt Request
				            EncriptResponse jsonObject = EncryptionDecriptionUtil.encriptResponse(json, applicationConstantConfig.apiSignaturePublicPath);

				            // Call Service
				            String encryptedResponse =companyService.saveOrganizationDetail(tokengeneration.getToken(), jsonObject);

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
	
	@PostMapping(value="/updateOrganizationDetail")
	public @ResponseBody String updateOrganizationDetail(HttpServletRequest request, ModelMap model,Locale locale,
			HttpSession session,CompanyProfileDetail companyProfileDetail) {
		String profileRes=null;
		String receivedHash = companyProfileDetail.getHash();
		if (!CLIENT_KEY.equals(companyProfileDetail.getClientKey())) {
	          //  return Map.of("isValid", false, "message", "Invalid client key");
	        }
//		const dataString = panNo1+panNo1+legalName+legalName+tradeName+orgType+address1+address2+district+
//				pincode+state+employerName+employerId+consent+otpstatus+clientKey+secretKey;
		String dataString =
				 companyProfileDetail.getPan()+
				 companyProfileDetail.getLegalNameOfBusiness()+
				 companyProfileDetail.getTradeName()+
				 companyProfileDetail.getConstitutionOfBusiness()+
				 companyProfileDetail.getOrgType()+
				 companyProfileDetail.getAddress1()+
				 companyProfileDetail.getAddress2()+
				 companyProfileDetail.getDistrictName()+
				 companyProfileDetail.getPincode()+
				 companyProfileDetail.getStateName()+
				 companyProfileDetail.getGstIdentificationNumber()+
				 //companyProfileDetail.getPan()+
				 companyProfileDetail.getCreatedBy()+
				 companyProfileDetail.getEmployerId()+
				 companyProfileDetail.getConsent()+
				 companyProfileDetail.getOtpStatus()+
				
				 CLIENT_KEY+SECRET_KEY;

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
			    if ((obj.getUser_role() == 9 || obj.getUser_role() == 1 || obj.getUser_role() == 2) && obj.getOrgid() == companyProfileDetail.getEmployerId().intValue()) {
		
		try {
			String json = EncryptionDecriptionUtil.convertToJson(companyProfileDetail);

			EncriptResponse jsonObject=EncryptionDecriptionUtil.encriptResponse(json, applicationConstantConfig.apiSignaturePublicPath);

			String encriptResponse = companyService.updateOrganizationDetail(tokengeneration.getToken(), jsonObject);

   
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
					// TODO Auto-generated catch block
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
   
	//return profileRes;
	
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
