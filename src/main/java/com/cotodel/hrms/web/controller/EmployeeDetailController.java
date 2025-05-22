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
import com.cotodel.hrms.web.util.Base64FileUtil;
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
	
		Map<String, Object> responseMap = new HashMap<>();
		ObjectMapper mapper = new ObjectMapper();
		EmployeeOnboarding employeeOnboarding = new EmployeeOnboarding();
		
		 String Id = requestData.get("Id");
		 String employerId = requestData.get("employerId");
		 String employeeId = requestData.get("employeeId");
		 String name = requestData.get("name");
		 String email = requestData.get("email");
		 String mobile = requestData.get("mobile");
		 String herDate = requestData.get("herDate");
		 String jobTitle = requestData.get("jobTitle");
		 String depratment = requestData.get("depratment");
		 String ctc = requestData.get("ctc");
		 String location = requestData.get("location");
		 String residentOfIndia = requestData.get("residentOfIndia");
		 String empOrCont = requestData.get("empOrCont");
		 String empPhoto = requestData.get("empPhoto");
		 String filetype = requestData.get("filetype");
		 String filename = requestData.get("filename");
		 String clientKey = requestData.get("clientKey");
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
//	     employeeOnboarding.setManagerId(
//	    		 requestData.get("managerId") != null && !requestData.get("managerId").isEmpty()
//	    		        ? Integer.parseInt(requestData.get("managerId"))
//	    		        : null
//	    		);
	     employeeOnboarding.setName(requestData.get("name"));
	     employeeOnboarding.setEmail(requestData.get("email"));
	     employeeOnboarding.setMobile(requestData.get("mobile"));
	     employeeOnboarding.setHerDate(requestData.get("herDate"));
	     employeeOnboarding.setJobTitle(requestData.get("jobTitle"));
	     employeeOnboarding.setDepratment(requestData.get("depratment"));
	    
	     employeeOnboarding.setManagerName(requestData.get("managerName"));
	     employeeOnboarding.setCtc(requestData.get("ctc"));
	     employeeOnboarding.setLocation(requestData.get("location"));
	     employeeOnboarding.setResidentOfIndia(requestData.get("residentOfIndia"));
	     employeeOnboarding.setEmpOrCont(requestData.get("empOrCont"));
	     employeeOnboarding.setEmpPhoto(requestData.get("empPhoto"));
	     employeeOnboarding.setFiletype(requestData.get("filetype"));
	     employeeOnboarding.setFilename(requestData.get("filename"));
	     employeeOnboarding.setClientKey(requestData.get("clientKey"));
	     
	     employeeOnboarding.setManagerId(
	    		 requestData.get("managerId") != null && !requestData.get("managerId").isEmpty()
 		        ? Integer.parseInt(requestData.get("managerId"))
 		        : null
	    		 
	    		 );
	     employeeOnboarding.setHash(requestData.get("hash"));
	     // Validate client key first
	        if (!CLIENT_KEY.equals(clientKey)) {
	        	responseMap.put("status", false);
		        responseMap.put("message", "Invalid client key");
	        }
	        // Ensure consistent concatenation
	        String managerId=employeeOnboarding.getManagerId()==null?"":employeeOnboarding.getManagerId().toString();
	        String dataString = Id+employerId+employeeId+name+email+mobile+herDate+jobTitle+depratment+ctc+location+residentOfIndia+
	        		empOrCont+empPhoto+filetype+filename+managerId+employeeOnboarding.getManagerName()+CLIENT_KEY+SECRET_KEY;
	       logger.info("datastring"+dataString);
	       
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
	  
	    logger.info("saveDirectorOnboarding---"+employeeOnboarding.toString());
	 
	    // Get token from session
	    
	    if(employeeOnboarding.getFiletype() != null &&  employeeOnboarding.getEmpPhoto() != "") {
	    if (!employeeOnboarding.getFiletype().equals("image/jpeg") && !employeeOnboarding.getFiletype().equals("image/png")) {
	        responseMap.put("status", false);
	        responseMap.put("message", "Request Tempered");
	        try {
	            return mapper.writeValueAsString(responseMap);
	        } catch (JsonProcessingException e) {
	            return "{\"status\":false, \"message\":\"JSON processing error\"}";
	        }
	    }
	}
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
	
	@PostMapping(value = "/getEmployeeOnboarding")
	public @ResponseBody String getEmployeeOnboardingSuccessList(
	        HttpServletRequest request,
	        ModelMap model,
	        Locale locale,
	        HttpSession session,
	        @RequestBody Map<String, String> requestData) {

	    Map<String, Object> responseMap = new HashMap<>();
	    ObjectMapper mapper = new ObjectMapper();

	    try {
	        // 1. Extract and validate input
	        String employeeIdStr = requestData.get("employeeId");
	        String employerIdStr = requestData.get("employerId");
	        String clientKey = requestData.get("key");
	        String receivedHash = requestData.get("hash");

	        Integer employeeId = parseInteger(employeeIdStr);
	        Integer employerId = parseInteger(employerIdStr);

	        if (clientKey == null || !CLIENT_KEY.equals(clientKey)) {
	            return buildErrorResponse(mapper, "Invalid client key.");
	        }

	        // 2. Hash Validation
	        String dataString = employeeIdStr + employerIdStr + clientKey + SECRET_KEY;
	        String computedHash = generateHash(dataString);

	        if (!computedHash.equals(receivedHash)) {
	            return buildErrorResponse(mapper, "Request Tempered");
	        }

	        // 3. Validate Session Token
	        String token = (String) session.getAttribute("hrms");
	        if (token == null) {
	            return buildErrorResponse(mapper, "Unauthorized: No token found.");
	        }

	        UserDetailsEntity user = JwtTokenValidator.parseToken(token);
	        if (user == null) {
	            return buildErrorResponse(mapper, "Unauthorized: Invalid token.");
	        }

	        // 4. Role & Permission Check
	        if (!isAuthorized(user, employerId)) {
	            return buildErrorResponse(mapper, "Unauthorized: Insufficient permissions.");
	        }

	        // 5. Prepare EmployeeOnboarding object
	        EmployeeOnboarding onboarding = new EmployeeOnboarding();
	        onboarding.setEmployeeId(employeeId);
	        onboarding.setEmployerId(employerId);

	        logger.info("Fetching onboarding data for: {}", onboarding);

	        // 6. Prepare and Encrypt Request
	        String jsonRequest = EncryptionDecriptionUtil.convertToJson(onboarding);
	        EncriptResponse encryptedRequest = EncryptionDecriptionUtil.encriptResponse(
	                jsonRequest, applicationConstantConfig.apiSignaturePublicPath);

	        // 7. Call External Service
	        String encryptedResponse = employeeDetailService.getEmployeeOnboarding(
	                tokengeneration.getToken(), encryptedRequest);

	        // 8. Decrypt Response
	        EncriptResponse decryptedWrapper = EncryptionDecriptionUtil.convertFromJson(encryptedResponse, EncriptResponse.class);
	        String decryptedJson = EncryptionDecriptionUtil.decriptResponse(
	                decryptedWrapper.getEncriptData(), decryptedWrapper.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);

	        JSONObject apiJsonResponse = new JSONObject(decryptedJson);

	        // 9. Prepare Final Response
	        boolean status = apiJsonResponse.getBoolean("status");
	        responseMap.put("status", status);
	        responseMap.put("message", apiJsonResponse.getString("message"));

	        if (status) {
	            List<Object> dataList = apiJsonResponse.getJSONArray("data").toList();
	            responseMap.put("data", dataList);
	        }

	    } catch (Exception e) {
	        logger.error("Error while processing onboarding request", e);
	        responseMap.put("status", false);
	        responseMap.put("message", "Internal Server Error: " + e.getMessage());
	    }

	    try {
	        return mapper.writeValueAsString(responseMap);
	    } catch (JsonProcessingException e) {
	        return "{\"status\":false, \"message\":\"JSON processing error\"}";
	    }
	}

	
	@PostMapping(value="/getEmployeeOnboardingById")
	public @ResponseBody String getEmployeeOnboardingById(HttpServletRequest request, ModelMap model,Locale locale,HttpSession session, @RequestBody Map<String, String> requestData) {
		String profileRes=null;
	
		  Map<String, Object> responseMap = new HashMap<>();
		    ObjectMapper mapper = new ObjectMapper();

		    try {
		        // 1. Extract and validate input
		        String employeeIdStr = requestData.get("id");
		        String employerIdStr = requestData.get("employerId");
		        String clientKey = requestData.get("key");
		        String receivedHash = requestData.get("hash");

		        Integer employeeId = parseInteger(employeeIdStr);
		        Integer employerId = parseInteger(employerIdStr);

		        if (clientKey == null || !CLIENT_KEY.equals(clientKey)) {
		            return buildErrorResponse(mapper, "Invalid client key.");
		        }

		        // 2. Hash Validation
		        String dataString = employeeIdStr + employerIdStr + clientKey + SECRET_KEY;
		        String computedHash = generateHash(dataString);

		        if (!computedHash.equals(receivedHash)) {
		            return buildErrorResponse(mapper, "Request Tempered");
		        }

		        // 3. Validate Session Token
		        String token = (String) session.getAttribute("hrms");
		        if (token == null) {
		            return buildErrorResponse(mapper, "Unauthorized: No token found.");
		        }

		        UserDetailsEntity user = JwtTokenValidator.parseToken(token);
		        if (user == null) {
		            return buildErrorResponse(mapper, "Unauthorized: Invalid token.");
		        }

		        // 4. Role & Permission Check
		        if (!isAuthorized(user, employerId)) {
		            return buildErrorResponse(mapper, "Unauthorized: Insufficient permissions.");
		        }

		        // 5. Prepare EmployeeOnboarding object
		        EmployeeOnboarding onboarding = new EmployeeOnboarding();
		        onboarding.setId(employeeId);
		        onboarding.setEmployerId(employerId);

		        logger.info("Fetching onboarding data for: {}", onboarding);

		        // 6. Prepare and Encrypt Request
		        String jsonRequest = EncryptionDecriptionUtil.convertToJson(onboarding);
		        EncriptResponse encryptedRequest = EncryptionDecriptionUtil.encriptResponse(
		                jsonRequest, applicationConstantConfig.apiSignaturePublicPath);

		        // 7. Call External Service
		        String encryptedResponse = employeeDetailService.getEmployeeOnboardingById(tokengeneration.getToken(), encryptedRequest);

		        // 8. Decrypt Response
		        EncriptResponse decryptedWrapper = EncryptionDecriptionUtil.convertFromJson(encryptedResponse, EncriptResponse.class);
		        String decryptedJson = EncryptionDecriptionUtil.decriptResponse(
		                decryptedWrapper.getEncriptData(), decryptedWrapper.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);

		        JSONObject apiJsonResponse = new JSONObject(decryptedJson);

		        // 9. Prepare Final Response
		        boolean status = apiJsonResponse.getBoolean("status");
		        responseMap.put("status", status);
		        responseMap.put("message", apiJsonResponse.getString("message"));

		        if (status && apiJsonResponse.has("data")) {
		        	//if section fixed by anish on 11-04-25 
		        	//it was giving error as email is not string / JSONObject["managerId"] is not a int likewise errors
		        	JSONObject data = apiJsonResponse.getJSONObject("data");
		        	EmployeeOnboarding eboarding = new EmployeeOnboarding();
		        	eboarding.setId(data.getInt("id"));
		        	eboarding.setEmployeeId(data.getInt("employerId"));
		        	eboarding.setName(data.getString("name"));
		        	eboarding.setEmpOrCont(data.getString("empOrCont"));
		        	//eboarding.setEmail(data.getString("email"));
		        	eboarding.setEmail(data.optString("email", null));
		        	eboarding.setMobile(data.getString("mobile"));
		        	//eboarding.setHerDate(data.getString("herDate"));
		        	eboarding.setHerDate(data.optString("herDate", null));
		        	//eboarding.setJobTitle(data.getString("jobTitle"));
		        	eboarding.setJobTitle(data.optString("jobTitle", null));
		        	//eboarding.setDepratment(data.getString("depratment"));
		        	eboarding.setDepratment(data.optString("depratment", null));
		        	//eboarding.setManagerName(data.getString("managerName"));
		        	eboarding.setManagerName(data.optString("managerName", null));
		        	//eboarding.setLocation(data.getString("location"));
		        	eboarding.setLocation(data.optString("location", null));
		        	eboarding.setUserDetailsId((data.getLong("userDetailsId")));
		        	//eboarding.setResidentOfIndia(data.getString("residentOfIndia"));
		        	eboarding.setResidentOfIndia(data.optString("residentOfIndia", null));
		        	//eboarding.setManagerId(data.getInt("managerId"));
		        	int managerId = data.isNull("managerId") ? 0 : data.getInt("managerId");
		        	eboarding.setManagerId(managerId);
		        	//eboarding.setEmpPhoto(data.getString("empPhoto"));
		        	eboarding.setEmpPhoto(data.optString("empPhoto", null));
		        	
		        	
		        	responseMap.put("data", eboarding); // Could be primitive or string
		          
		        }

		    } catch (Exception e) {
		        logger.error("Error while processing onboarding request", e);
		        responseMap.put("status", false);
		        responseMap.put("message", "Internal Server Error: " + e.getMessage());
		    }

		    try {
		        return mapper.writeValueAsString(responseMap);
		    } catch (JsonProcessingException e) {
		        return "{\"status\":false, \"message\":\"JSON processing error\"}";
		    }
		}
	
	@PostMapping(value="/getEmployeeDetailByEmployeeId")
	public @ResponseBody String getEmployeeDetailByEmployeeId(HttpServletRequest request, ModelMap model,Locale locale,HttpSession session, @RequestBody Map<String, String> requestData) {
		String profileRes=null;
	
		  Map<String, Object> responseMap = new HashMap<>();
		    ObjectMapper mapper = new ObjectMapper();

		    try {
		        // 1. Extract and validate input
		        String employeeIdStr = requestData.get("id");
		        String clientKey = requestData.get("key");
		        String receivedHash = requestData.get("hash");

		        Integer employeeId = parseInteger(employeeIdStr);

		        if (clientKey == null || !CLIENT_KEY.equals(clientKey)) {
		            return buildErrorResponse(mapper, "Invalid client key.");
		        }

		        // 2. Hash Validation
		        String dataString = employeeIdStr + clientKey + SECRET_KEY;
		        String computedHash = generateHash(dataString);

		        if (!computedHash.equals(receivedHash)) {
		            return buildErrorResponse(mapper, "Request Tempered");
		        }

		        // 3. Validate Session Token
		        String token = (String) session.getAttribute("hrms");
		        if (token == null) {
		            return buildErrorResponse(mapper, "Unauthorized: No token found.");
		        }

		        UserDetailsEntity user = JwtTokenValidator.parseToken(token);
		        if (user == null) {
		            return buildErrorResponse(mapper, "Unauthorized: Invalid token.");
		        }
		        // 5. Prepare EmployeeOnboarding object
		        EmployeeOnboarding onboarding = new EmployeeOnboarding();
		        onboarding.setId(employeeId);
		        
		        logger.info("Fetching onboarding data for: {}", onboarding);

		        // 6. Prepare and Encrypt Request
		        String jsonRequest = EncryptionDecriptionUtil.convertToJson(onboarding);
		        EncriptResponse encryptedRequest = EncryptionDecriptionUtil.encriptResponse(
		                jsonRequest, applicationConstantConfig.apiSignaturePublicPath);

		        // 7. Call External Service
		        String encryptedResponse = employeeDetailService.getEmployeeOnboardingById(tokengeneration.getToken(), encryptedRequest);

		        // 8. Decrypt Response
		        EncriptResponse decryptedWrapper = EncryptionDecriptionUtil.convertFromJson(encryptedResponse, EncriptResponse.class);
		        String decryptedJson = EncryptionDecriptionUtil.decriptResponse(
		                decryptedWrapper.getEncriptData(), decryptedWrapper.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);

		        JSONObject apiJsonResponse = new JSONObject(decryptedJson);

		        // 9. Prepare Final Response
		        boolean status = apiJsonResponse.getBoolean("status");
		        responseMap.put("status", status);
		        responseMap.put("message", apiJsonResponse.getString("message"));

		        if (status && apiJsonResponse.has("data")) {
		        	// if section fixed by anish on 11-04-25 
		        	//it was giving error as email is not string / JSONObject["managerId"] is not a int likewise errors
		        	JSONObject data = apiJsonResponse.getJSONObject("data");
		        	EmployeeOnboarding eboarding = new EmployeeOnboarding();
		        	eboarding.setId(data.getInt("id"));
		        	eboarding.setEmployeeId(data.getInt("employerId"));
		        	eboarding.setName(data.getString("name"));
		        	eboarding.setEmpOrCont(data.getString("empOrCont"));
		        	//eboarding.setEmail(data.getString("email"));
		        	eboarding.setEmail(data.optString("email", null));
		        	eboarding.setMobile(data.getString("mobile"));
		        	//eboarding.setHerDate(data.getString("herDate"));
		        	eboarding.setHerDate(data.optString("herDate", null));
		        	//eboarding.setJobTitle(data.getString("jobTitle"));
		        	eboarding.setJobTitle(data.optString("jobTitle", null));
		        	//eboarding.setDepratment(data.getString("depratment"));
		        	eboarding.setDepratment(data.optString("depratment", null));
		        	//eboarding.setManagerName(data.getString("managerName"));
		        	eboarding.setManagerName(data.optString("managerName", null));
		        	//eboarding.setLocation(data.getString("location"));
		        	eboarding.setLocation(data.optString("location", null));
		        	eboarding.setUserDetailsId((data.getLong("userDetailsId")));
		        	//eboarding.setResidentOfIndia(data.getString("residentOfIndia"));
		        	eboarding.setResidentOfIndia(data.optString("residentOfIndia", null));
		        	//eboarding.setManagerId(data.getInt("managerId"));
		        	int managerId = data.isNull("managerId") ? 0 : data.getInt("managerId");
		        	eboarding.setManagerId(managerId);
		        	//eboarding.setEmpPhoto(data.getString("empPhoto"));
		        	eboarding.setEmpPhoto(data.optString("empPhoto", null));
		        	responseMap.put("data", eboarding); // Could be primitive or string
		        }

		    } catch (Exception e) {
		        logger.error("Error while processing onboarding request", e);
		        responseMap.put("status", false);
		        responseMap.put("message", "Internal Server Error: " + e.getMessage());
		    }

		    try {
		        return mapper.writeValueAsString(responseMap);
		    } catch (JsonProcessingException e) {
		        return "{\"status\":false, \"message\":\"JSON processing error\"}";
		    }
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
		String receivedHash = employeeOnboarding.getHash();
		 if (!CLIENT_KEY.equals(employeeOnboarding.getClientKey())) {
	          // return Map.of("isValid", false, "message", "Invalid client key");
	        }
	        // Ensure consistent concatenation
	        String dataString = 
	        		employeeOnboarding.getId()+
	        		employeeOnboarding.getName()+
	        		employeeOnboarding.getEmail()+
	        		employeeOnboarding.getMobile()+
	        		employeeOnboarding.getProofOfIdentity()+
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
			    logger.info("obj.getUser_role() "+obj.getUser_role());
			    logger.info("obj.getOrgid()"+obj.getOrgid());
			    logger.info("employeeOnboarding.getEmployerId().intValue() "+employeeOnboarding.getEmployerId().intValue());
    if ((obj.getUser_role() == 2) && obj.getOrgid() == employeeOnboarding.getEmployerId().intValue()) {
		try {
			String json = EncryptionDecriptionUtil.convertToJson(employeeOnboarding);

			EncriptResponse jsonObject=EncryptionDecriptionUtil.encriptResponse(json, applicationConstantConfig.apiSignaturePublicPath);

			String encriptResponse = employeeDetailService.saveEmployeeProfile(tokengeneration.getToken(), jsonObject);

   
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
	
	@PostMapping(value="/updateEmployeeProfile")
	public @ResponseBody String updateEmployeeProfile(HttpServletRequest request, ModelMap model,Locale locale,HttpSession session,EmployeeOnboarding employeeOnboarding) {
		String profileRes=null;
		String receivedHash = employeeOnboarding.getHash();
		 if (!CLIENT_KEY.equals(employeeOnboarding.getClientKey())) {
	          // return Map.of("isValid", false, "message", "Invalid client key");
	        }
	        // Ensure consistent concatenation
	        String dataString = 
	        		employeeOnboarding.getId()+
	        		employeeOnboarding.getPan()+
	        		employeeOnboarding.getIfscCode()+
	        		employeeOnboarding.getBeneficiaryName()+
	        		employeeOnboarding.getBankAccountNumber()+
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
    if ((obj.getUser_role() == 2) && obj.getOrgid() == employeeOnboarding.getEmployerId().intValue()) {
		try {
			String json = EncryptionDecriptionUtil.convertToJson(employeeOnboarding);

			EncriptResponse jsonObject=EncryptionDecriptionUtil.encriptResponse(json, applicationConstantConfig.apiSignaturePublicPath);

			String encriptResponse = employeeDetailService.updateEmployeeProfile(tokengeneration.getToken(), jsonObject);

   
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
	@PostMapping(value="/getEmployeeOnboardingByManagerId")
	public @ResponseBody String getEmployeeOnboardingByManagerId(HttpServletRequest request, ModelMap model,Locale locale,HttpSession session,@RequestBody Map<String, String> requestData) {
	

		  Map<String, Object> responseMap = new HashMap<>();
		    ObjectMapper mapper = new ObjectMapper();

		    try {
		        // 1. Extract and validate input
		        String managerIdIdStr = requestData.get("managerId");
		        String employerIdStr = requestData.get("employerId");
		        String clientKey = requestData.get("key");
		        String receivedHash = requestData.get("hash");

		        Integer managerId = parseInteger(managerIdIdStr);

		        if (clientKey == null || !CLIENT_KEY.equals(clientKey)) {
		            return buildErrorResponse(mapper, "Invalid client key.");
		        }

		        // 2. Hash Validation
		        String dataString = managerIdIdStr + clientKey + SECRET_KEY;
		        String computedHash = generateHash(dataString);

		        if (!computedHash.equals(receivedHash)) {
		            return buildErrorResponse(mapper, "Request Tempered");
		        }

		        // 3. Validate Session Token
		        String token = (String) session.getAttribute("hrms");
		        if (token == null) {
		            return buildErrorResponse(mapper, "Unauthorized: No token found.");
		        }

		        UserDetailsEntity user = JwtTokenValidator.parseToken(token);
		        if (user == null) {
		            return buildErrorResponse(mapper, "Unauthorized: Invalid token.");
		        }

		        // 4. Role & Permission Check
//		        if (!isAuthorized(user, employerId)) {
//		            return buildErrorResponse(mapper, "Unauthorized: Insufficient permissions.");
//		        }

		        // 5. Prepare EmployeeOnboarding object
		        EmployeeOnboarding onboarding = new EmployeeOnboarding();
		        onboarding.setManagerId(managerId);
		      

		        logger.info("Fetching onboarding data for: {}", onboarding);

		        // 6. Prepare and Encrypt Request
		        String jsonRequest = EncryptionDecriptionUtil.convertToJson(onboarding);
		        EncriptResponse encryptedRequest = EncryptionDecriptionUtil.encriptResponse(
		                jsonRequest, applicationConstantConfig.apiSignaturePublicPath);

		        // 7. Call External Service
		        String encryptedResponse = employeeDetailService.getEmployeeOnboardingByManagerId(tokengeneration.getToken(), encryptedRequest);

		        // 8. Decrypt Response
		        EncriptResponse decryptedWrapper = EncryptionDecriptionUtil.convertFromJson(encryptedResponse, EncriptResponse.class);
		        String decryptedJson = EncryptionDecriptionUtil.decriptResponse(
		                decryptedWrapper.getEncriptData(), decryptedWrapper.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);

		        JSONObject apiJsonResponse = new JSONObject(decryptedJson);

		        // 9. Prepare Final Response
		        boolean status = apiJsonResponse.getBoolean("status");
		        responseMap.put("status", status);
		        responseMap.put("message", apiJsonResponse.getString("message"));
		        
		        if (status && apiJsonResponse.has("data")) {
		            List<Object> dataList = apiJsonResponse.getJSONArray("data").toList();
		            responseMap.put("data", dataList);
		        }
		    } catch (Exception e) {
		        logger.error("Error while processing onboarding request", e);
		        responseMap.put("status", false);
		        responseMap.put("message", "Internal Server Error: " + e.getMessage());
		    }

		    try {
		        return mapper.writeValueAsString(responseMap);
		    } catch (JsonProcessingException e) {
		        return "{\"status\":false, \"message\":\"JSON processing error\"}";
		    }
		
	}
	
	@PostMapping(value = "/saveDirectorOnboarding")
	public @ResponseBody String saveDirectorOnboarding( HttpServletRequest request,  ModelMap model,  Locale locale,  HttpSession session, @RequestBody Map<String, String> requestData) {

		 String orgId = requestData.get("orgId");
		 String name = requestData.get("name");
		 //String din = requestData.get("din");
		 String email = requestData.get("email");
		 String mobile = requestData.get("mobile");
		// String designation = requestData.get("designation");
		// String address = requestData.get("address");
		 String createdby = requestData.get("createdby");
		 String clientKey = requestData.get("key");
	     String receivedHash = requestData.get("hash");
		 
		 DirectorOnboarding directorOnboarding = new DirectorOnboarding();
		 directorOnboarding.setOrgId(Long.parseLong(requestData.get("orgId")));
	     //directorOnboarding.setDesignation(requestData.get("designation"));
	     //directorOnboarding.setAddress(requestData.get("address"));
	     directorOnboarding.setName(requestData.get("name"));
	     directorOnboarding.setMobile(requestData.get("mobile"));
	     directorOnboarding.setCreatedby(requestData.get("createdby"));
	     directorOnboarding.setEmail(requestData.get("email"));
	    
	    // directorOnboarding.setDin(requestData.get("din"));
	
	    
	     
	     directorOnboarding.setHash(receivedHash);
	     directorOnboarding.setClientKey(clientKey);

	        // Validate client key first
	        if (!CLIENT_KEY.equals(clientKey)) {
	          //  return Map.of("isValid", false, "message", "Invalid client key");
	        }
	        // Ensure consistent concatenation
	        String dataString = orgId+name+email+mobile+createdby+clientKey+SECRET_KEY;

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
	
@PostMapping(value="/getDirectorOnboarding")
	public @ResponseBody String getDirectorOnboarding(HttpServletRequest request, ModelMap model,Locale locale,HttpSession session, @RequestBody Map<String, String> requestData) {
	
		DirectorOnboarding directorOnboarding = new DirectorOnboarding();
		
		 Map<String, Object> responseMap = new HashMap<>();
		    ObjectMapper mapper = new ObjectMapper();

		    try {
		        // 1. Extract and validate input
		        String employerIdStr = requestData.get("orgId");
		        String clientKey = requestData.get("key");
		        String receivedHash = requestData.get("hash");

		        Integer employerId = parseInteger(employerIdStr);

		        if (clientKey == null || !CLIENT_KEY.equals(clientKey)) {
		            return buildErrorResponse(mapper, "Invalid client key.");
		        }

		        // 2. Hash Validation
		        String dataString =employerIdStr + clientKey + SECRET_KEY;
		        String computedHash = generateHash(dataString);

		        if (!computedHash.equals(receivedHash)) {
		            return buildErrorResponse(mapper, "Request Tempered");
		        }

		        // 3. Validate Session Token
		        String token = (String) session.getAttribute("hrms");
		        if (token == null) {
		            return buildErrorResponse(mapper, "Unauthorized: No token found.");
		        }

		        UserDetailsEntity user = JwtTokenValidator.parseToken(token);
		        if (user == null) {
		            return buildErrorResponse(mapper, "Unauthorized: Invalid token.");
		        }

		        // 4. Role & Permission Check
		        if (!isAuthorized(user, employerId)) {
		            return buildErrorResponse(mapper, "Unauthorized: Insufficient permissions.");
		        }

		        // 5. Prepare EmployeeOnboarding object
		        EmployeeOnboarding onboarding = new EmployeeOnboarding();
		       
		        directorOnboarding.setOrgId(employerId.longValue());

		        logger.info("Fetching onboarding data for: {}", directorOnboarding);

		        // 6. Prepare and Encrypt Request
		        String jsonRequest = EncryptionDecriptionUtil.convertToJson(directorOnboarding);
		        EncriptResponse encryptedRequest = EncryptionDecriptionUtil.encriptResponse(
		                jsonRequest, applicationConstantConfig.apiSignaturePublicPath);

		        // 7. Call External Service
		        String encryptedResponse = employeeDetailService.getDirectorOnboarding(tokengeneration.getToken(), encryptedRequest);

		        // 8. Decrypt Response
		        EncriptResponse decryptedWrapper = EncryptionDecriptionUtil.convertFromJson(encryptedResponse, EncriptResponse.class);
		        String decryptedJson = EncryptionDecriptionUtil.decriptResponse(
		                decryptedWrapper.getEncriptData(), decryptedWrapper.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);

		        JSONObject apiJsonResponse = new JSONObject(decryptedJson);

		        // 9. Prepare Final Response
		        boolean status = apiJsonResponse.getBoolean("status");
		        responseMap.put("status", status);
		        responseMap.put("message", apiJsonResponse.getString("message"));

		        if (status) {
		            List<Object> dataList = apiJsonResponse.getJSONArray("data").toList();
		            responseMap.put("data", dataList);
		        }

		    } catch (Exception e) {
		        logger.error("Error while processing onboarding request", e);
		        responseMap.put("status", false);
		        responseMap.put("message", "Internal Server Error: " + e.getMessage());
		    }

		    try {
		        return mapper.writeValueAsString(responseMap);
		    } catch (JsonProcessingException e) {
		        return "{\"status\":false, \"message\":\"JSON processing error\"}";
		    }
		
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
	
    private String generateHash(String data) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hashBytes = digest.digest(data.getBytes(StandardCharsets.UTF_8));
        StringBuilder hexString = new StringBuilder();
        for (byte b : hashBytes) {
            hexString.append(String.format("%02x", b));
        }
        return hexString.toString();
    }
    private Integer parseInteger(String value) {
        try {
            return (value != null && !value.isEmpty()) ? Integer.parseInt(value) : null;
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private String buildErrorResponse(ObjectMapper mapper, String message) {
        Map<String, Object> errorMap = new HashMap<>();
        errorMap.put("status", false);
        errorMap.put("message", message);
        try {
            return mapper.writeValueAsString(errorMap);
        } catch (JsonProcessingException e) {
            return "{\"status\":false, \"message\":\"JSON processing error\"}";
        }
    }

    private boolean isAuthorized(UserDetailsEntity user, Integer employerId) {
        if (user == null || employerId == null) return false;
        return (user.getUser_role() == 9 || user.getUser_role() == 1) && user.getOrgid() == employerId.intValue();
    }
    
}
