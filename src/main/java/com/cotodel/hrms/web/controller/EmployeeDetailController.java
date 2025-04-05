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
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.cotodel.hrms.web.properties.ApplicationConstantConfig;
import com.cotodel.hrms.web.response.BulkConfirmationRequest;
import com.cotodel.hrms.web.response.DirectorOnboarding;
import com.cotodel.hrms.web.response.EmployeeDeactiveRequest;
import com.cotodel.hrms.web.response.EmployeeOnboarding;
import com.cotodel.hrms.web.response.UserDetailsEntity;
import com.cotodel.hrms.web.service.EmployeeDetailService;
import com.cotodel.hrms.web.service.Impl.TokenGenerationImpl;
import com.cotodel.hrms.web.util.EncriptResponse;
import com.cotodel.hrms.web.util.EncryptionDecriptionUtil;
import com.cotodel.hrms.web.util.JwtTokenValidator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@CrossOrigin
public class EmployeeDetailController extends CotoDelBaseController{

	private static final Logger logger = LoggerFactory.getLogger(EmployeeDetailController.class);

	private static final String SECRET_KEY = "0123456789012345"; // Must match frontend
    private static final String CLIENT_KEY = "client-secret-key"; // Extra validation

	@Autowired
	public ApplicationConstantConfig applicationConstantConfig;
	
	@Autowired
	EmployeeDetailService employeeDetailService;

	@Autowired
	TokenGenerationImpl tokengeneration;
	
	
	@PostMapping(value="/employeeRegistration")
	public @ResponseBody String saveEmployeeOnboarding(HttpServletRequest request, ModelMap model,Locale locale,HttpSession session, @RequestBody Map<String, String> requestData) {
		String profileRes=null;
		EmployeeOnboarding employeeOnboarding = new EmployeeOnboarding();
		
		 String Id = requestData.get("Id");
		 String employerId = requestData.get("employerId");
		 String employeeId = requestData.get("employeeId");
		 String managerName1 =requestData.get("managerName1");
		 String name = requestData.get("name");
		 String email = requestData.get("email");
		 String mobile = requestData.get("mobile");
		 String herDate = requestData.get("herDate");
		 String jobTitle = requestData.get("jobTitle");
		 String depratment = requestData.get("depratment");
		 String managerId = requestData.get("managerId");
		 String managerName = requestData.get("managerName");
		 String ctc = requestData.get("ctc");
		 String location = requestData.get("location");
		 String residentOfIndia = requestData.get("residentOfIndia");
		 String empOrCont = requestData.get("empOrCont");
		 String empPhoto = requestData.get("empPhoto");
		 
		 String clientKey = requestData.get("key");
	     String receivedHash = requestData.get("hash");
	     
	     employeeOnboarding.setId(
	    		    requestData.get("Id") != null && !requestData.get("Id").isEmpty()
	    		        ? Integer.parseInt(requestData.get("Id"))
	    		        : null
	    		);
	     employeeOnboarding.setEmployeeId(
	    		 requestData.get("employeeId") != null && !requestData.get("employeeId").isEmpty()
 		        ? Integer.parseInt(requestData.get("employeeId"))
 		        : null
	    		 
	    		 );
	     employeeOnboarding.setEmployerId(
	    		 requestData.get("employerId") != null && !requestData.get("employerId").isEmpty()
 		        ? Integer.parseInt(requestData.get("employerId"))
 		        : null
	    		 
	    		 );
	     employeeOnboarding.setName(requestData.get("name"));
	     employeeOnboarding.setEmail(requestData.get("email"));
	     employeeOnboarding.setMobile(requestData.get("mobile"));
	     employeeOnboarding.setHerDate(requestData.get("herDate"));
	     employeeOnboarding.setJobTitle(requestData.get("jobTitle"));
	     employeeOnboarding.setDepratment(requestData.get("depratment"));
	     employeeOnboarding.setManagerId(
	    		 requestData.get("managerId") != null && !requestData.get("managerId").isEmpty()
	    		        ? Integer.parseInt(requestData.get("managerId"))
	    		        : null
	    		);
	     employeeOnboarding.setManagerName(requestData.get("managerName"));
	     employeeOnboarding.setManagerName(requestData.get("managerName1"));
	     employeeOnboarding.setCtc(requestData.get("ctc"));
	     employeeOnboarding.setLocation(requestData.get("location"));
	     employeeOnboarding.setResidentOfIndia(requestData.get("residentOfIndia"));
	     employeeOnboarding.setEmpOrCont(requestData.get("empOrCont"));
	     employeeOnboarding.setEmpPhoto(requestData.get("empPhoto"));
	     
	     // Validate client key first
	        if (!CLIENT_KEY.equals(clientKey)) {
	          //  return Map.of("isValid", false, "message", "Invalid client key");
	        }
	        // Ensure consistent concatenation
	        String dataString = Id+employerId+employeeId+name+email+mobile+herDate+jobTitle+depratment+managerId+managerName+ctc+location+residentOfIndia+
	        		empOrCont+empPhoto+clientKey+SECRET_KEY;

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
	 
	    logger.info("saveDirectorOnboarding---"+employeeOnboarding.toString());
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
	    if ((obj.getUser_role() == 9 || obj.getUser_role() == 1) && obj.getOrgid() == employeeOnboarding.getEmployerId().intValue()) {
	        try {
	            // Convert request object to JSON
	            String json = EncryptionDecriptionUtil.convertToJson(employeeOnboarding);

	            // Encrypt Request
	            EncriptResponse jsonObject = EncryptionDecriptionUtil.encriptResponse(json, applicationConstantConfig.apiSignaturePublicPath);

	            // Call Service
	            String encryptedResponse = employeeDetailService.saveEmployeeOnboarding(tokengeneration.getToken(), jsonObject);

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
	
	@GetMapping(value="/getEmployeeOnboarding")
	public @ResponseBody String getEmployeeOnboardingSuccessList(HttpServletRequest request, ModelMap model,Locale locale,HttpSession session,EmployeeOnboarding employeeOnboarding) 
	{
		String profileRes=null;
		
		try {
			String json = EncryptionDecriptionUtil.convertToJson(employeeOnboarding);

			EncriptResponse jsonObject=EncryptionDecriptionUtil.encriptResponse(json, applicationConstantConfig.apiSignaturePublicPath);

			String encriptResponse =  employeeDetailService.getEmployeeOnboarding(tokengeneration.getToken(), jsonObject);

   
			EncriptResponse userReqEnc =EncryptionDecriptionUtil.convertFromJson(encriptResponse, EncriptResponse.class);

			profileRes =  EncryptionDecriptionUtil.decriptResponse(userReqEnc.getEncriptData(), userReqEnc.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
   
	return profileRes;
		
	}
	
	
	@GetMapping(value="/getEmployeeOnboardingById")
	public @ResponseBody String getEmployeeOnboardingById(HttpServletRequest request, ModelMap model,Locale locale,HttpSession session,EmployeeOnboarding employeeOnboarding) {
		String profileRes=null;
	
		try {
			String json = EncryptionDecriptionUtil.convertToJson(employeeOnboarding);

			EncriptResponse jsonObject=EncryptionDecriptionUtil.encriptResponse(json, applicationConstantConfig.apiSignaturePublicPath);

			String encriptResponse = employeeDetailService.getEmployeeOnboardingById(tokengeneration.getToken(), jsonObject);

   
			EncriptResponse userReqEnc =EncryptionDecriptionUtil.convertFromJson(encriptResponse, EncriptResponse.class);

			profileRes =  EncryptionDecriptionUtil.decriptResponse(userReqEnc.getEncriptData(), userReqEnc.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
   
	return profileRes;
	
	}
	
	@PostMapping(value="/confirmBulkEmplOnboarding")
	public String confirmBulkEmplOnboarding( @RequestBody BulkConfirmationRequest[] bulkConfirmationRequest , HttpSession session) {
		String profileRes=null;
	
		try {
			String json = EncryptionDecriptionUtil.convertToJson(bulkConfirmationRequest);

			EncriptResponse jsonObject=EncryptionDecriptionUtil.encriptResponse(json, applicationConstantConfig.apiSignaturePublicPath);

			String encriptResponse = employeeDetailService.confirmBulkEmplOnboarding(tokengeneration.getToken(), jsonObject);

   
			EncriptResponse userReqEnc =EncryptionDecriptionUtil.convertFromJson(encriptResponse, EncriptResponse.class);

			profileRes =  EncryptionDecriptionUtil.decriptResponse(userReqEnc.getEncriptData(), userReqEnc.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return profileRes;
		
	}
	
	@PostMapping(value="/saveEmployeeProfile")
	public @ResponseBody String saveEmployeeProfile(HttpServletRequest request, ModelMap model,Locale locale,HttpSession session,EmployeeOnboarding employeeOnboarding) {
		String profileRes=null;
	
		try {
			String json = EncryptionDecriptionUtil.convertToJson(employeeOnboarding);

			EncriptResponse jsonObject=EncryptionDecriptionUtil.encriptResponse(json, applicationConstantConfig.apiSignaturePublicPath);

			String encriptResponse = employeeDetailService.saveEmployeeProfile(tokengeneration.getToken(), jsonObject);

   
			EncriptResponse userReqEnc =EncryptionDecriptionUtil.convertFromJson(encriptResponse, EncriptResponse.class);

			profileRes =  EncryptionDecriptionUtil.decriptResponse(userReqEnc.getEncriptData(), userReqEnc.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return profileRes;
		
	}
	@GetMapping(value="/getEmployeeOnboardingByManagerId")
	public @ResponseBody String getEmployeeOnboardingByManagerId(HttpServletRequest request, ModelMap model,Locale locale,HttpSession session,EmployeeOnboarding employeeOnboarding) {
		String profileRes=null;
		
//		profileRes = employeeDetailService.getEmployeeOnboardingByManagerId(tokengeneration.getToken(),employeeOnboarding);
//		profileJsonRes= new JSONObject(profileRes);
//		
//		if(profileJsonRes.getBoolean("status")) { 
//			otpMap.put("status", MessageConstant.RESPONSE_SUCCESS);
//		}else {
//			loginservice.sendEmailVerificationCompletion(userForm);
//			otpMap.put("status", MessageConstant.RESPONSE_FAILED);
//		}
//		try {
//			res = mapper.writeValueAsString(otpMap);
//		} catch (Exception e) {
//			
//		}
//		
//		return profileRes;
		
		try {
			String json = EncryptionDecriptionUtil.convertToJson(employeeOnboarding);

			EncriptResponse jsonObject=EncryptionDecriptionUtil.encriptResponse(json, applicationConstantConfig.apiSignaturePublicPath);

			String encriptResponse = employeeDetailService.getEmployeeOnboardingByManagerId(tokengeneration.getToken(), jsonObject);

   
			EncriptResponse userReqEnc =EncryptionDecriptionUtil.convertFromJson(encriptResponse, EncriptResponse.class);

			profileRes =  EncryptionDecriptionUtil.decriptResponse(userReqEnc.getEncriptData(), userReqEnc.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return profileRes;
		
	}
	
	@PostMapping(value = "/saveDirectorOnboarding")
	public @ResponseBody String saveDirectorOnboarding( HttpServletRequest request,  ModelMap model,  Locale locale,  HttpSession session, @RequestBody Map<String, String> requestData) {

		 String orgId = requestData.get("orgId");
		 String name = requestData.get("name");
		 String din = requestData.get("din");
		 String email = requestData.get("email");
		 String mobile = requestData.get("mobile");
		 String designation = requestData.get("designation");
		 String address = requestData.get("address");
		 String createdby = requestData.get("createdby");
		 
		 DirectorOnboarding directorOnboarding = new DirectorOnboarding();
		 directorOnboarding.setOrgId(Long.parseLong(requestData.get("orgId")));
	     directorOnboarding.setDesignation(requestData.get("designation"));
	     directorOnboarding.setAddress(requestData.get("address"));
	     directorOnboarding.setName(requestData.get("name"));
	     directorOnboarding.setMobile(requestData.get("mobile"));
	     directorOnboarding.setCreatedby(requestData.get("createdby"));
	     directorOnboarding.setEmail(requestData.get("email"));
	     directorOnboarding.setDin(requestData.get("din"));
	
	     String clientKey = requestData.get("key");
	     String receivedHash = requestData.get("hash");

	        // Validate client key first
	        if (!CLIENT_KEY.equals(clientKey)) {
	          //  return Map.of("isValid", false, "message", "Invalid client key");
	        }

	        // Ensure consistent concatenation
	        String dataString = orgId+name+din+email+mobile+designation+address+createdby+clientKey+SECRET_KEY;

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
	 
	    logger.info("saveDirectorOnboarding---"+directorOnboarding.toString());
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
	    if ((obj.getUser_role() == 9 || obj.getUser_role() == 1) && obj.getOrgid() == directorOnboarding.getOrgId().intValue()) {
	        try {
	            // Convert request object to JSON
	            String json = EncryptionDecriptionUtil.convertToJson(directorOnboarding);

	            // Encrypt Request
	            EncriptResponse jsonObject = EncryptionDecriptionUtil.encriptResponse(json, applicationConstantConfig.apiSignaturePublicPath);

	            // Call Service
	            String encryptedResponse = employeeDetailService.saveDirectorOnboarding(tokengeneration.getToken(), jsonObject);

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
	
@GetMapping(value="/getDirectorOnboarding")
	public @ResponseBody String getDirectorOnboarding(HttpServletRequest request, ModelMap model,Locale locale,HttpSession session,DirectorOnboarding directorOnboarding) {
		String profileRes=null;

		try {
			String json = EncryptionDecriptionUtil.convertToJson(directorOnboarding);

			EncriptResponse jsonObject=EncryptionDecriptionUtil.encriptResponse(json, applicationConstantConfig.apiSignaturePublicPath);

			String encriptResponse =  employeeDetailService.getDirectorOnboarding(tokengeneration.getToken(), jsonObject);

   
			EncriptResponse userReqEnc =EncryptionDecriptionUtil.convertFromJson(encriptResponse, EncriptResponse.class);

			profileRes =  EncryptionDecriptionUtil.decriptResponse(userReqEnc.getEncriptData(), userReqEnc.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
   
	return profileRes;
		
	}
	@PostMapping(value="/toggleEmployee")//de activating employee from emp-onboarding-full-action
	public @ResponseBody String deactiveEmployee(HttpServletRequest request, ModelMap model,Locale locale,HttpSession session,EmployeeDeactiveRequest employeeDeactiveRequest) {
		String profileRes=null;

		
		try {
			String json = EncryptionDecriptionUtil.convertToJson(employeeDeactiveRequest);

			EncriptResponse jsonObject=EncryptionDecriptionUtil.encriptResponse(json, applicationConstantConfig.apiSignaturePublicPath);

			String encriptResponse = employeeDetailService.deactiveEmployee(tokengeneration.getToken(), jsonObject);

   
			EncriptResponse userReqEnc =EncryptionDecriptionUtil.convertFromJson(encriptResponse, EncriptResponse.class);

			profileRes =  EncryptionDecriptionUtil.decriptResponse(userReqEnc.getEncriptData(), userReqEnc.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return profileRes;
		
	}
	
	@GetMapping(value="/getEmployeeOnboardingByUserDetailId")
	public @ResponseBody String getEmployeeOnboardingByUserDetailId(HttpServletRequest request, ModelMap model,Locale locale,HttpSession session,EmployeeOnboarding employeeOnboarding) {
		String profileRes=null;
		

		
		try {
			String json = EncryptionDecriptionUtil.convertToJson(employeeOnboarding);

			EncriptResponse jsonObject=EncryptionDecriptionUtil.encriptResponse(json, applicationConstantConfig.apiSignaturePublicPath);

			String encriptResponse = employeeDetailService.getEmployeeOnboardingByUserDetailId(tokengeneration.getToken(), jsonObject);

   
			EncriptResponse userReqEnc =EncryptionDecriptionUtil.convertFromJson(encriptResponse, EncriptResponse.class);

			profileRes =  EncryptionDecriptionUtil.decriptResponse(userReqEnc.getEncriptData(), userReqEnc.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
   
	return profileRes;
	
	}
	
//	@PostMapping(value="/validateRequest")
//	public @ResponseBody String validateRequest(@RequestBody Map<String, String> requestData) {
//        String userId = requestData.get("userId");
//        String timestamp = requestData.get("timestamp");
//        String clientKey = requestData.get("key");
//        String receivedHash = requestData.get("hash");
//
//        // Validate client key first
//        if (!CLIENT_KEY.equals(clientKey)) {
//          //  return Map.of("isValid", false, "message", "Invalid client key");
//        }
//
//        // Ensure consistent concatenation
//        String dataString = userId + timestamp + clientKey + SECRET_KEY;
//
//        // Compute hash
//        String computedHash = null;
//		try {
//			computedHash = generateHash(dataString);
//		} catch (NoSuchAlgorithmException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//        // Validate hash
//        boolean isValid = computedHash.equals(receivedHash);
//		return computedHash;
//
//    }

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
