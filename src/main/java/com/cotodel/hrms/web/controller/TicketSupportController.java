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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.cotodel.hrms.web.properties.ApplicationConstantConfig;
import com.cotodel.hrms.web.response.ErupiTicketSaveRequest;
import com.cotodel.hrms.web.response.UserDetailsEntity;
import com.cotodel.hrms.web.response.VehicleManagementRequest;
import com.cotodel.hrms.web.service.TicketSupportService;
import com.cotodel.hrms.web.service.Impl.TokenGenerationImpl;
import com.cotodel.hrms.web.util.EncriptResponse;
import com.cotodel.hrms.web.util.EncryptionDecriptionUtil;
import com.cotodel.hrms.web.util.JwtTokenValidator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@CrossOrigin
public class TicketSupportController extends CotoDelBaseController {
	
	private static final Logger logger = LoggerFactory.getLogger(VehicleManagementController.class);
	private static final String SECRET_KEY = "0123456789012345"; // Must match frontend
    private static final String CLIENT_KEY = "client-secret-key"; // Extra validation

	@Autowired
	TokenGenerationImpl tokengeneration;
	
	@Autowired
	public ApplicationConstantConfig applicationConstantConfig;
	
	@Autowired
	public TicketSupportService ticketSupportService;
	
	@PostMapping(value = "/submitTicket")
	public @ResponseBody String submitTicket(HttpServletRequest request, ModelMap model, Locale locale,
			HttpSession session, ErupiTicketSaveRequest erupiTicketSaveRequest) {
		
		  String profileRes = null;
		    Map<String, Object> responseMap = new HashMap<>();
		    ObjectMapper mapper = new ObjectMapper();
		    
		    String receivedHash =erupiTicketSaveRequest.getHash();

		    // Validate client key only
		    if (!CLIENT_KEY.equals(erupiTicketSaveRequest.getClientKey())) {
		        responseMap.put("status", false);
		        responseMap.put("message", "Invalid client key");
		        try {
		            return mapper.writeValueAsString(responseMap);
		        } catch (JsonProcessingException e) {
		            return "{\"status\":false, \"message\":\"JSON processing error\"}";
		        }
		    }
		    
		    String dataString =erupiTicketSaveRequest.getOrgId() +erupiTicketSaveRequest.getSubject()+
		    		erupiTicketSaveRequest.getIssueDesc()+erupiTicketSaveRequest.getCreatedby() +
		    		erupiTicketSaveRequest.getTicketImg() +CLIENT_KEY + SECRET_KEY;

		    String computedHash = null;
		    try {
		        computedHash = generateHash(dataString);
		        System.out.println("computedHash: " + computedHash);
		        System.out.println("dataString: " + dataString);
		    } catch (NoSuchAlgorithmException e) {
		        e.printStackTrace();
		    }

		    // Validate hash
		    boolean isValid = computedHash != null && computedHash.equals(receivedHash);
		    if (!isValid) {
		        responseMap.put("status", false);
		        responseMap.put("message", "Request Tempered");
		        try {
		            return mapper.writeValueAsString(responseMap);
		        } catch (JsonProcessingException e) {
		            return "{\"status\":false, \"message\":\"JSON processing error\"}";
		        }
		    }


		    // Get token from session
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

//		    // Validate Token
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

//		     Check User Role and Organization ID
		    if ((obj.getUser_role() == 9 || obj.getUser_role() == 1 || obj.getUser_role() == 3) && obj.getOrgid() == erupiTicketSaveRequest.getOrgId().intValue()) {
		        try {
		            String json = EncryptionDecriptionUtil.convertToJson(erupiTicketSaveRequest);
		            EncriptResponse jsonObject = EncryptionDecriptionUtil.encriptResponse(json, applicationConstantConfig.apiSignaturePublicPath);
		            String encriptResponse = ticketSupportService.submitTicketDetails(tokengeneration.getToken(), jsonObject);

		            EncriptResponse userReqEnc = EncryptionDecriptionUtil.convertFromJson(encriptResponse, EncriptResponse.class);
		            profileRes = EncryptionDecriptionUtil.decriptResponse(userReqEnc.getEncriptData(), userReqEnc.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);

		            JSONObject apiJsonResponse = new JSONObject(profileRes);
		            boolean status = apiJsonResponse.getBoolean("status");
		            responseMap.put("status", status);
		            responseMap.put("message", apiJsonResponse.getString("message"));

		            if (status && apiJsonResponse.has("data")) {
		                responseMap.put("data", profileRes);
		            } else {
		            	//responseMap.put("data", profileRes);
		                responseMap.put("status", false);
		                responseMap.put("message", apiJsonResponse.getString("message"));
		                responseMap.put("data", profileRes);
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

	@PostMapping(value = "/replyTicket")
	public @ResponseBody String replyTicket(HttpServletRequest request, ModelMap model, Locale locale,
			HttpSession session, ErupiTicketSaveRequest erupiTicketSaveRequest) {
		
		  String profileRes = null;
		    Map<String, Object> responseMap = new HashMap<>();
		    ObjectMapper mapper = new ObjectMapper();
		    
		    String receivedHash =erupiTicketSaveRequest.getHash();

		    // Validate client key only
		    if (!CLIENT_KEY.equals(erupiTicketSaveRequest.getClientKey())) {
		        responseMap.put("status", false);
		        responseMap.put("message", "Invalid client key");
		        try {
		            return mapper.writeValueAsString(responseMap);
		        } catch (JsonProcessingException e) {
		            return "{\"status\":false, \"message\":\"JSON processing error\"}";
		        }
		    }
		    
		    String dataString =erupiTicketSaveRequest.getOrgId() +erupiTicketSaveRequest.getSubject()+
		    		erupiTicketSaveRequest.getIssueDesc()+erupiTicketSaveRequest.getCreatedby() +
		    		erupiTicketSaveRequest.getTicketImg() +CLIENT_KEY + SECRET_KEY;

		    String computedHash = null;
		    try {
		        computedHash = generateHash(dataString);
		        System.out.println("computedHash: " + computedHash);
		        System.out.println("dataString: " + dataString);
		    } catch (NoSuchAlgorithmException e) {
		        e.printStackTrace();
		    }

		    // Validate hash
		    boolean isValid = computedHash != null && computedHash.equals(receivedHash);
		    if (isValid) {
		        responseMap.put("status", false);
		        responseMap.put("message", "Request Tempered");
		        try {
		            return mapper.writeValueAsString(responseMap);
		        } catch (JsonProcessingException e) {
		            return "{\"status\":false, \"message\":\"JSON processing error\"}";
		        }
		    }


		    // Get token from session
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

//		    // Validate Token
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

//		     Check User Role and Organization ID
		    if ((obj.getUser_role() == 9 || obj.getUser_role() == 1 || obj.getUser_role() == 3) && obj.getOrgid() == erupiTicketSaveRequest.getOrgId().intValue()) {
		        try {
		            String json = EncryptionDecriptionUtil.convertToJson(erupiTicketSaveRequest);
		            EncriptResponse jsonObject = EncryptionDecriptionUtil.encriptResponse(json, applicationConstantConfig.apiSignaturePublicPath);
		            String encriptResponse = ticketSupportService.replyTicket(tokengeneration.getToken(), jsonObject);

		            EncriptResponse userReqEnc = EncryptionDecriptionUtil.convertFromJson(encriptResponse, EncriptResponse.class);
		            profileRes = EncryptionDecriptionUtil.decriptResponse(userReqEnc.getEncriptData(), userReqEnc.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);

		            JSONObject apiJsonResponse = new JSONObject(profileRes);
		            boolean status = apiJsonResponse.getBoolean("status");
		            responseMap.put("status", status);
		            responseMap.put("message", apiJsonResponse.getString("message"));

		            if (status && apiJsonResponse.has("data")) {
		                responseMap.put("data", profileRes);
		            } else {
		            	//responseMap.put("data", profileRes);
		                responseMap.put("status", false);
		                responseMap.put("message", apiJsonResponse.getString("message"));
		                responseMap.put("data", profileRes);
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


	@GetMapping(value = "/getAllTicket")
	public @ResponseBody String getAllTicket(HttpServletRequest request, ModelMap model, Locale locale,
			HttpSession session, ErupiTicketSaveRequest erupiTicketSaveRequest) {
		String profileRes = null;
		
		try {
			String json = EncryptionDecriptionUtil.convertToJson(erupiTicketSaveRequest);

			EncriptResponse jsonObject=EncryptionDecriptionUtil.encriptResponse(json, applicationConstantConfig.apiSignaturePublicPath);

			String encriptResponse =  ticketSupportService.getAllTicket(tokengeneration.getToken(), jsonObject);
  
			EncriptResponse userReqEnc =EncryptionDecriptionUtil.convertFromJson(encriptResponse, EncriptResponse.class);

			profileRes =  EncryptionDecriptionUtil.decriptResponse(userReqEnc.getEncriptData(), userReqEnc.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
   
    	return profileRes;
	}
	
	@GetMapping(value = "/getTicketListForAction")
	public @ResponseBody String getTicketListForAction(HttpServletRequest request, ModelMap model, Locale locale,
			HttpSession session, ErupiTicketSaveRequest erupiTicketSaveRequest) {
		String profileRes = null;
		
		try {
			String json = EncryptionDecriptionUtil.convertToJson(erupiTicketSaveRequest);

			EncriptResponse jsonObject=EncryptionDecriptionUtil.encriptResponse(json, applicationConstantConfig.apiSignaturePublicPath);

			String encriptResponse =  ticketSupportService.getTicketListForAction(tokengeneration.getToken(), jsonObject);
  
			EncriptResponse userReqEnc =EncryptionDecriptionUtil.convertFromJson(encriptResponse, EncriptResponse.class);

			profileRes =  EncryptionDecriptionUtil.decriptResponse(userReqEnc.getEncriptData(), userReqEnc.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
   
    	return profileRes;
	}
	 @GetMapping("/getTicketDetailByTicketId")
	    public @ResponseBody String getTicketDetailById(HttpServletRequest request, ModelMap model, Locale locale,
				HttpSession session, ErupiTicketSaveRequest erupiTicketSaveRequest) {
		 String profileRes = null;
		
			try {
				String json = EncryptionDecriptionUtil.convertToJson(erupiTicketSaveRequest);

				EncriptResponse jsonObject=EncryptionDecriptionUtil.encriptResponse(json, applicationConstantConfig.apiSignaturePublicPath);

				String encriptResponse =  ticketSupportService.getTicketDetailById(tokengeneration.getToken(), jsonObject);
	  
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
