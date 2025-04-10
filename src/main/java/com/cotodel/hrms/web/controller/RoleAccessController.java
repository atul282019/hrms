package com.cotodel.hrms.web.controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cotodel.hrms.web.properties.ApplicationConstantConfig;
import com.cotodel.hrms.web.response.RoleAccessRequest;
import com.cotodel.hrms.web.response.RoleDTO;
import com.cotodel.hrms.web.response.UserDTO;
import com.cotodel.hrms.web.response.UserDetailsEntity;
import com.cotodel.hrms.web.response.UserRoleDTO;
import com.cotodel.hrms.web.service.RoleAccessService;
import com.cotodel.hrms.web.service.Impl.TokenGenerationImpl;
import com.cotodel.hrms.web.util.EncriptResponse;
import com.cotodel.hrms.web.util.EncryptionDecriptionUtil;
import com.cotodel.hrms.web.util.JwtTokenValidator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@CrossOrigin
public class RoleAccessController extends CotoDelBaseController{

	private static final Logger logger = LoggerFactory.getLogger(RoleAccessController.class);
	
	private static final String SECRET_KEY = "0123456789012345"; // Must match frontend
    private static final String CLIENT_KEY = "client-secret-key"; // Extra validation


	@Autowired
	public ApplicationConstantConfig applicationConstantConfig;

	@Autowired
	TokenGenerationImpl tokengeneration;
	
	@Autowired
	RoleAccessService roleaccessservice;
	
	@GetMapping(value="/getUserWithRole")
	public @ResponseBody String getStateMaster(HttpServletRequest request, ModelMap model,Locale locale,
			HttpSession session,RoleAccessRequest roleAccessRequest) {
			logger.info("get User With Role");	
			String token = (String) session.getAttribute("hrms");
			//return roleaccessservice.getUserRole(tokengeneration.getToken(),roleAccessRequest);
			
			String profileRes=null;
			
			try {
				String json = EncryptionDecriptionUtil.convertToJson(roleAccessRequest);

				EncriptResponse jsonObject=EncryptionDecriptionUtil.encriptResponse(json, applicationConstantConfig.apiSignaturePublicPath);

				String encriptResponse =  roleaccessservice.getUserRole(tokengeneration.getToken(), jsonObject);

	   
				EncriptResponse userReqEnc =EncryptionDecriptionUtil.convertFromJson(encriptResponse, EncriptResponse.class);

				profileRes =  EncryptionDecriptionUtil.decriptResponse(userReqEnc.getEncriptData(), userReqEnc.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	   
		return profileRes;
	}
	
	@PostMapping(value="/editUserRole")
	public @ResponseBody String editUserRole(HttpServletRequest request, ModelMap model,Locale locale,
			HttpSession session,@RequestBody RoleDTO requestDTO) {
			logger.info("edit User with Role");	
			String profileRes=null;
			//String token = (String) session.getAttribute("hrms");
			
			 // Validate or process the data as needed
	        System.out.println("Org ID: " + requestDTO.getOrgId());
	        System.out.println("Employer ID: " + requestDTO.getEmployerId());
	        System.out.println("Created By: " + requestDTO.getCreatedBy());
	        
	        for (UserDTO user : requestDTO.getUserDTO()) {
	            System.out.println("User ID: " + user.getId());
	            System.out.println("Username: " + user.getUsername());
	            System.out.println("Email: " + user.getEmail());
	            System.out.println("Mobile: " + user.getMobile());
	            for (UserRoleDTO role : user.getUserRole()) {
	                System.out.println("Role: " + role.getRoleDesc());
	            }
	        }

	        String receivedHash =  requestDTO.getHash();
	        if (!CLIENT_KEY.equals(requestDTO.getClientKey())) {
		          // return Map.of("isValid", false, "message", "Invalid client key");
		        }
	        StringBuilder dataBuilder = new StringBuilder();

	        dataBuilder.append(requestDTO.getOrgId())
	                   .append(requestDTO.getEmployerId())
	                   .append(requestDTO.getUserMobile())
	                   .append(requestDTO.getConsent())
	                   .append(requestDTO.getCreatedBy());

	        for (UserDTO user : requestDTO.getUserDTO()) {
	            dataBuilder.append(user.getId())
	                       .append(user.getUsername())
	                       .append(user.getEmail())
	                       .append(user.getMobile());

	            for (UserRoleDTO role : user.getUserRole()) {
	                dataBuilder.append(role.getRoleDesc());
	            }
	        }

	        dataBuilder.append(CLIENT_KEY).append(SECRET_KEY);

	        String dataString = dataBuilder.toString();
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
		//	return roleaccessservice.editUserRoleDTO(tokengeneration.getToken(),requestDTO);
		    if ((obj.getUser_role() == 9 || obj.getUser_role() == 1 || obj.getUser_role() == 3) && obj.getOrgid() == requestDTO.getOrgId().intValue()) {
				
		    try {
				String json = EncryptionDecriptionUtil.convertToJson(requestDTO);

				EncriptResponse jsonObject=EncryptionDecriptionUtil.encriptResponse(json, applicationConstantConfig.apiSignaturePublicPath);

				String encriptResponse =  roleaccessservice.editUserRoleDTO(tokengeneration.getToken(), jsonObject);

	   
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
	               //responseMap.put("data", apiJsonResponse.getJSONArray("data").toList()); //as list
//	                List<Object> dataList = apiJsonResponse.getJSONArray("data").toList();//as object
//	                
//		            responseMap.put("data", dataList);
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
	
	@PostMapping(value="/deleteUserRole")
	public @ResponseBody String deleteUserRole(HttpServletRequest request, ModelMap model,Locale locale,
			HttpSession session,RoleAccessRequest roleAccessRequest) {
			logger.info("delete User with Role");	
			String token = (String) session.getAttribute("hrms");
			//return roleaccessservice.deleteUserRole(tokengeneration.getToken(),roleAccessRequest);
           String profileRes=null;
			
			try {
				String json = EncryptionDecriptionUtil.convertToJson(roleAccessRequest);

				EncriptResponse jsonObject=EncryptionDecriptionUtil.encriptResponse(json, applicationConstantConfig.apiSignaturePublicPath);

				String encriptResponse =  roleaccessservice.deleteUserRole(tokengeneration.getToken(), jsonObject);

	   
				EncriptResponse userReqEnc =EncryptionDecriptionUtil.convertFromJson(encriptResponse, EncriptResponse.class);

				profileRes =  EncryptionDecriptionUtil.decriptResponse(userReqEnc.getEncriptData(), userReqEnc.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	   
		return profileRes;
	}
	
	@PostMapping(value="/userSearch")
	public @ResponseBody String userSearch(HttpServletRequest request, ModelMap model,Locale locale,
			HttpSession session,RoleAccessRequest roleAccessRequest) {
			logger.info("search User");	
			String token = (String) session.getAttribute("hrms");
			//return roleaccessservice.userSearch(tokengeneration.getToken(),roleAccessRequest);
			
			String profileRes=null;
			
			try {
				String json = EncryptionDecriptionUtil.convertToJson(roleAccessRequest);

				EncriptResponse jsonObject=EncryptionDecriptionUtil.encriptResponse(json, applicationConstantConfig.apiSignaturePublicPath);

				String encriptResponse =  roleaccessservice.userSearch(tokengeneration.getToken(), jsonObject);

	   
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
