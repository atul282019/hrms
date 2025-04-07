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
	
//	@PostMapping(value="/saveCompanyDetail")
//	public @ResponseBody String saveCompanyDetail(HttpServletRequest request, ModelMap model,Locale locale,
//			HttpSession session,EmployeeProfileRequest employeeProfileRequest) {
//		String profileRes=null;
//		try {
//			String json = EncryptionDecriptionUtil.convertToJson(employeeProfileRequest);
//
//			EncriptResponse jsonObject=EncryptionDecriptionUtil.encriptResponse(json, applicationConstantConfig.apiSignaturePublicPath);
//
//			String encriptResponse = companyService.saveCompany(tokengeneration.getToken(), jsonObject);
//
//   
//			EncriptResponse userReqEnc =EncryptionDecriptionUtil.convertFromJson(encriptResponse, EncriptResponse.class);
//
//			profileRes =  EncryptionDecriptionUtil.decriptResponse(userReqEnc.getEncriptData(), userReqEnc.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//   
//	return profileRes;
//	}
	
	@PostMapping(value="/getCompanyProfileStatus")
	public @ResponseBody String getCompanyProfileStatus(HttpServletRequest request, ModelMap model,Locale locale,
			HttpSession session,EmployeeProfileRequest employeeProfileRequest) {
			logger.info("getPayrollMaster");	
			String token = (String) session.getAttribute("hrms");
			String profileRes=null;
		
			try {
				String json = EncryptionDecriptionUtil.convertToJson(employeeProfileRequest);

				EncriptResponse jsonObject=EncryptionDecriptionUtil.encriptResponse(json, applicationConstantConfig.apiSignaturePublicPath);

				String encriptResponse = companyService.getCompanyProfileStatus(tokengeneration.getToken(), jsonObject);

	   
				EncriptResponse userReqEnc =EncryptionDecriptionUtil.convertFromJson(encriptResponse, EncriptResponse.class);

				profileRes =  EncryptionDecriptionUtil.decriptResponse(userReqEnc.getEncriptData(), userReqEnc.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	   
		return profileRes;
	}
	
//	@GetMapping(value="/getorgsubType")
//	public @ResponseBody String getorgsubType(HttpServletRequest request, ModelMap model,Locale locale,
//			HttpSession session,EmployeeProfileRequest employeeProfileRequest) {
//	   
//	        String profileRes=null;
//	
//	    	try {
//				String json = EncryptionDecriptionUtil.convertToJson(employeeProfileRequest);
//
//				EncriptResponse jsonObject=EncryptionDecriptionUtil.encriptResponse(json, applicationConstantConfig.apiSignaturePublicPath);
//
//				String encriptResponse = companyService.getorgsubType(tokengeneration.getToken(), jsonObject);
//
//	   
//				EncriptResponse userReqEnc =EncryptionDecriptionUtil.convertFromJson(encriptResponse, EncriptResponse.class);
//
//				profileRes =  EncryptionDecriptionUtil.decriptResponse(userReqEnc.getEncriptData(), userReqEnc.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//	   
//		return profileRes;
//	}
//	@PostMapping(value="/getpayrollDetails")
//	public @ResponseBody String getpayrollDetails(HttpServletRequest request, ModelMap model,Locale locale,HttpSession session,EmployeeProfileRequest employeeProfileRequest) {
//		
//        String profileRes=null;    
//        try {
//			String json = EncryptionDecriptionUtil.convertToJson(employeeProfileRequest);
//
//			EncriptResponse jsonObject=EncryptionDecriptionUtil.encriptResponse(json, applicationConstantConfig.apiSignaturePublicPath);
//
//			String encriptResponse = companyService.getpayrollDetails(tokengeneration.getToken(), jsonObject);
//
//   
//			EncriptResponse userReqEnc =EncryptionDecriptionUtil.convertFromJson(encriptResponse, EncriptResponse.class);
//
//			profileRes =  EncryptionDecriptionUtil.decriptResponse(userReqEnc.getEncriptData(), userReqEnc.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//   
//	return profileRes;
//	}

	@PostMapping(value="/getGSTDetailsByGSTNumber")
	public @ResponseBody String getGSTDetailsByGSTNumber(HttpServletRequest request, ModelMap model,Locale locale,HttpSession session,EmployeeProfileRequest employeeProfileRequest) {
	
        String profileRes=null;
		//String token = (String) session.getAttribute("hrms");
//		companyResponse= companyService.getGSTDetailsByGSTNumber(tokengeneration.getToken(),employeeProfileRequest);
//		 companyJsonResponse = new JSONObject(companyResponse);      
//		 if(companyJsonResponse.getBoolean("status")) { 
//			 	responseMap.put("status",true);
//				
//				JSONObject dataObject = companyJsonResponse.getJSONObject("data");
//				responseMap.put("data", dataObject.toMap());
//	        }else {
//				responseMap.put("status", false);
//			}
//	        try {
//	            jsonResponse = mapper.writeValueAsString(responseMap);
//	        } catch (Exception e) {
//	            e.printStackTrace(); 
//	        }       
//	        return jsonResponse;
        
        
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
		
		 String dataString = companyProfileDetail.getPan()+companyProfileDetail.getLegalNameOfBusiness()+companyProfileDetail.getTradeName()+companyProfileDetail.getConstitutionOfBusiness()+
		 companyProfileDetail.getOrgType()+companyProfileDetail.getAddress1()+companyProfileDetail.getAddress2()+companyProfileDetail.getDistrictName()+companyProfileDetail.getPincode()+companyProfileDetail.getStateName()+
		 companyProfileDetail.getGstIdentificationNumber()+companyProfileDetail.getCreatedBy()+companyProfileDetail.getEmployerId()+CLIENT_KEY+SECRET_KEY;

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

			String encriptResponse = companyService.saveOrganizationDetail(tokengeneration.getToken(), jsonObject);

   
			EncriptResponse userReqEnc =EncryptionDecriptionUtil.convertFromJson(encriptResponse, EncriptResponse.class);

			profileRes =  EncryptionDecriptionUtil.decriptResponse(userReqEnc.getEncriptData(), userReqEnc.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    else {
        responseMap.put("status", false);
        responseMap.put("message", "Unauthorized: Insufficient permissions.");
    }
   
	return profileRes;
	
	}
	
	@PostMapping(value="/updateOrganizationDetail")
	public @ResponseBody String updateOrganizationDetail(HttpServletRequest request, ModelMap model,Locale locale,
			HttpSession session,CompanyProfileDetail companyProfileDetail) {
		String profileRes=null;
		
		try {
			String json = EncryptionDecriptionUtil.convertToJson(companyProfileDetail);

			EncriptResponse jsonObject=EncryptionDecriptionUtil.encriptResponse(json, applicationConstantConfig.apiSignaturePublicPath);

			String encriptResponse = companyService.saveOrganizationDetail(tokengeneration.getToken(), jsonObject);

   
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
