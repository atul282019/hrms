package com.cotodel.hrms.web.controller;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
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
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cotodel.hrms.web.properties.ApplicationConstantConfig;
import com.cotodel.hrms.web.response.EmployeeBandSettingResponse;
import com.cotodel.hrms.web.response.EmployeeBandTiersResponse;
import com.cotodel.hrms.web.response.EmployeePayrollNew;
import com.cotodel.hrms.web.response.EmployeePayrollRequest;
import com.cotodel.hrms.web.response.EmployeeProfileRequest;
import com.cotodel.hrms.web.response.UserDetailsEntity;
import com.cotodel.hrms.web.service.EmployeeBandSettingService;
import com.cotodel.hrms.web.service.Impl.TokenGenerationImpl;
import com.cotodel.hrms.web.util.EncriptResponse;
import com.cotodel.hrms.web.util.EncryptionDecriptionUtil;
import com.cotodel.hrms.web.util.JwtTokenValidator;
import com.cotodel.hrms.web.util.MessageConstant;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@CrossOrigin
public class EmployeeBandSettingController extends CotoDelBaseController{
	
	private static final Logger logger = LoggerFactory.getLogger(EmployeeBandSettingController.class);
	
	private static final String SECRET_KEY = "0123456789012345"; // Must match frontend
    private static final String CLIENT_KEY = "client-secret-key"; // Extra validation


	@Autowired
	public ApplicationConstantConfig applicationConstantConfig;

	@Autowired
	TokenGenerationImpl tokengeneration;
	
	@Autowired
	EmployeeBandSettingService empBandSettingService;
	
	@PostMapping(value="/saveEmployeeBandTierTab3")
	public @ResponseBody String getEmployeeBandTier(HttpServletRequest request, ModelMap model,Locale locale,HttpSession session,EmployeeBandSettingResponse employeeBandSettingResponse) {
		String profileRes=null;
		String receivedHash = employeeBandSettingResponse.getHash();
		 if (!CLIENT_KEY.equals(employeeBandSettingResponse.getClientKey())) {
	          // return Map.of("isValid", false, "message", "Invalid client key");
	        }
	        // Ensure consistent concatenation
	        String dataString = 
	        		employeeBandSettingResponse.getEmployerId()+
	        		CLIENT_KEY+SECRET_KEY;
//	        logger.info("data string"+dataString);
//	        System.out.println("data string"+dataString);
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
			    
	    if ((obj.getUser_role() == 9 || obj.getUser_role() == 1 || obj.getUser_role() == 3) && obj.getOrgid() == employeeBandSettingResponse.getEmployerId().intValue()) {
			    try {
			String json = EncryptionDecriptionUtil.convertToJson(employeeBandSettingResponse);

			EncriptResponse jsonObject=EncryptionDecriptionUtil.encriptResponse(json, applicationConstantConfig.apiSignaturePublicPath);

			String encriptResponse =  empBandSettingService.getEmployeeBandTier(tokengeneration.getToken(), jsonObject);

   
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
               responseMap.put("data", apiJsonResponse); //as list
//                JSONArray dataArray = apiJsonResponse.getJSONArray("data");
//                responseMap.put("data", dataArray.toList());
	            JSONObject dataObj = apiJsonResponse.getJSONObject("data");
	            responseMap.put("data", dataObj.toMap());
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
	
	
	@PostMapping(value="/saveEmployeeBandTab2")
	public @ResponseBody String saveEmployeeBandTab2(HttpServletRequest request, ModelMap model,Locale locale,HttpSession session,EmployeeBandSettingResponse employeeBandSettingResponse) {
		String profileRes=null;
		String receivedHash = employeeBandSettingResponse.getHash(); 
	
		 if (!CLIENT_KEY.equals(employeeBandSettingResponse.getClientKey())) {
	          // return Map.of("isValid", false, "message", "Invalid client key");
	        } 
		 
		 StringBuilder hashBuilder = new StringBuilder();
		 hashBuilder.append(employeeBandSettingResponse.getEmployerId())
		            .append(employeeBandSettingResponse.getBandEnabled())
		            .append(employeeBandSettingResponse.getEmployeeBandNo())
		            .append(employeeBandSettingResponse.getEmployeeBandNoAlpha())
		            .append(employeeBandSettingResponse.getEmployeeBandOrder())
		            .append(employeeBandSettingResponse.getIntroAddTierFlag())
		 			.append(employeeBandSettingResponse.getStatus());

		 if (employeeBandSettingResponse.getListArray() != null) {
		     for (String item : employeeBandSettingResponse.getListArray()) {
		         hashBuilder.append(item);
		     }
		 }

		 hashBuilder.append(CLIENT_KEY).append(SECRET_KEY);
		 String dataString=hashBuilder.toString();
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
		//old code 
		List<EmployeeBandTiersResponse> list = new ArrayList<EmployeeBandTiersResponse>();
		String data[]= employeeBandSettingResponse.getListArray();
		
		for (int i = 0; i < data.length; i++) {
			String listValue=data[i];
			String[] rowArray=listValue.split("@");
			EmployeeBandTiersResponse employeeBandTierSetting=new EmployeeBandTiersResponse();
			employeeBandTierSetting.setEmployeeBand(rowArray[0]);
			employeeBandTierSetting.setAdditionalTiers(rowArray[1]);
			
			employeeBandTierSetting.setEmployerId(employeeBandSettingResponse.getEmployerId());
			list.add(employeeBandTierSetting);
		}
		
		employeeBandSettingResponse.setList(list);
		//return profileRes = empBandSettingService.saveEmployeeBandTier(tokengeneration.getToken(),employeeBandSettingResponse);
		if ((obj.getUser_role() == 9 || obj.getUser_role() == 1 || obj.getUser_role() == 3) && obj.getOrgid() == employeeBandSettingResponse.getEmployerId().intValue()) {
			
		try {
			String json = EncryptionDecriptionUtil.convertToJson(employeeBandSettingResponse);

			EncriptResponse jsonObject=EncryptionDecriptionUtil.encriptResponse(json, applicationConstantConfig.apiSignaturePublicPath);

			String encriptResponse =  empBandSettingService.saveEmployeeBandTier(tokengeneration.getToken(), jsonObject);

   
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
               responseMap.put("data", apiResponse); //as list
//                JSONArray dataArray = apiJsonResponse.getJSONArray("data");
//                responseMap.put("data", dataArray.toList());
//	            JSONObject dataObj = apiJsonResponse.getJSONObject("data");
//	            responseMap.put("data", dataObj.toMap());
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
