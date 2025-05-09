package com.cotodel.hrms.web.controller;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.cotodel.hrms.web.properties.ApplicationConstantConfig;
import com.cotodel.hrms.web.response.EmployeeOnboardingManagerRequest;
import com.cotodel.hrms.web.response.ErupiVoucherCreateRequest;
import com.cotodel.hrms.web.response.UserDetailsEntity;
import com.cotodel.hrms.web.response.VoucherTypeMaster;
import com.cotodel.hrms.web.service.VoucherTypeMasterService;
import com.cotodel.hrms.web.service.Impl.TokenGenerationImpl;
import com.cotodel.hrms.web.util.EncriptResponse;
import com.cotodel.hrms.web.util.EncryptionDecriptionUtil;
import com.cotodel.hrms.web.util.JwtTokenValidator;
import com.cotodel.hrms.web.util.MessageConstant;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@CrossOrigin
public class VoucherTypeMasterController extends CotoDelBaseController{
	private static final Logger logger = LoggerFactory.getLogger(VoucherTypeMasterController.class);
	
	private static final String SECRET_KEY = "0123456789012345"; // Must match frontend
    private static final String CLIENT_KEY = "client-secret-key"; // Extra validation
	@Autowired
	TokenGenerationImpl tokengeneration;
	
	@Autowired
	VoucherTypeMasterService voucherTypeMasterService;
	
	@Autowired
	public ApplicationConstantConfig applicationConstantConfig;
	
	
	
	@PostMapping(value="/savevoucherTypeMaster")
	public @ResponseBody String saveBankMaster(ModelMap model, Locale locale, HttpSession session,VoucherTypeMaster voucherTypeMaster) {
 
        Map<String, String> responseMap = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();
        String jsonResponse = null;

        // Call the service to save the BankMaster object
//        voucherResponse = voucherTypeMasterService.saveVoucherTypeMaster(tokengeneration.getToken(), voucherTypeMaster);
//        System.out.println(voucherResponse);  // Logging the response
//        voucherJsonResponse = new JSONObject(voucherResponse);
//
//       
//		if(voucherJsonResponse.getBoolean("status")) { 
//			responseMap.put("status", MessageConstant.RESPONSE_SUCCESS);
//		}else {
//			//loginsevice.rsendEmailVerificationCompletion(userForm);
//			responseMap.put("status", MessageConstant.RESPONSE_FAILED);
//		}
        
        try {
           // jsonResponse = mapper.writeValueAsString(responseMap);
        	String json = EncryptionDecriptionUtil.convertToJson(voucherTypeMaster); 
            EncriptResponse encryptedRequest = EncryptionDecriptionUtil.encriptResponse(json, applicationConstantConfig.apiSignaturePublicPath);
            
            String encryptedResponse = voucherTypeMasterService.saveVoucherTypeMaster(tokengeneration.getToken(), encryptedRequest);
            EncriptResponse responseObject = EncryptionDecriptionUtil.convertFromJson(encryptedResponse, EncriptResponse.class);
            String voucherResponse1 = EncryptionDecriptionUtil.decriptResponse(responseObject.getEncriptData(), responseObject.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
            
            JSONObject voucherJsonResponse1 = new JSONObject(voucherResponse1);
            if (voucherJsonResponse1.getBoolean("status")) {
                responseMap.put("status", MessageConstant.RESPONSE_SUCCESS);
            } else {
                responseMap.put("status", MessageConstant.RESPONSE_FAILED);
            }
            
            jsonResponse = mapper.writeValueAsString(responseMap);
        
        } catch (Exception e) {
            e.printStackTrace(); 
        }

        
        return jsonResponse;  
    }
	@GetMapping(value="/getvoucherTypeMaster")
	public @ResponseBody String getVoucherTypeMasterList(ModelMap model, Locale locale, HttpSession session,VoucherTypeMaster voucherTypeMaster) {
    
        Map<String, Object> responseMap = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();
        String jsonResponse = null;
        // Call the service to save the voucherResponse object
//        voucherResponse = voucherTypeMasterService.getVoucherTypeMasterList(tokengeneration.getToken(), voucherTypeMaster);
//        System.out.println(voucherResponse);  // Logging the response
//        voucherJsonResponse = new JSONObject(voucherResponse);      
//        if(voucherJsonResponse.getBoolean("status")) { 
//			
//			//responseMap.put("data", voucherJsonResponse.getJSONArray("data"));
//			List<Object> voucherList = voucherJsonResponse.getJSONArray("data").toList();
//			responseMap.put("status",true);
//			responseMap.put("data", voucherList);
//        }else {
//			//loginsevice.rsendEmailVerificationCompletion(userForm);
//			responseMap.put("status", false);
//		}
        try {
            //jsonResponse = mapper.writeValueAsString(responseMap);
        	String json = EncryptionDecriptionUtil.convertToJson(voucherTypeMaster);
            EncriptResponse encryptedRequest = EncryptionDecriptionUtil.encriptResponse(json, applicationConstantConfig.apiSignaturePublicPath);
            
            String encryptedResponse = voucherTypeMasterService.getVoucherTypeMasterList(tokengeneration.getToken(), encryptedRequest);
            EncriptResponse responseObject = EncryptionDecriptionUtil.convertFromJson(encryptedResponse, EncriptResponse.class);
            String voucherResponse1 = EncryptionDecriptionUtil.decriptResponse(responseObject.getEncriptData(), responseObject.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
            
            JSONObject voucherJsonResponse1 = new JSONObject(voucherResponse1);
            if (voucherJsonResponse1.getBoolean("status")) {
                List<Object> voucherList = voucherJsonResponse1.getJSONArray("data").toList();
                responseMap.put("status", true);
                responseMap.put("data", voucherList);
            } else {
                responseMap.put("status", false);
            }
            
            jsonResponse = mapper.writeValueAsString(responseMap);
        
        } catch (Exception e) {
            e.printStackTrace(); 
        }       
        return jsonResponse;
    }
	

	
	@PostMapping(value="/toggleVoucherStatus")
	public @ResponseBody String updateVoucherTypeMasterStatus(ModelMap model, Locale locale, 
			HttpSession session, VoucherTypeMaster voucherTypeMaster) throws JsonProcessingException {

	
        Map<String, String> responseMap = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();
        String jsonResponse = null;

//        // Call the service to save the BankMaster object
//        voucherResponse = voucherTypeMasterService.updatevoucherTypeMasterStatus(tokengeneration.getToken(), voucherTypeMaster);
//        System.out.println(voucherResponse);  // Logging the response
//        
//        voucherJsonResponse = new JSONObject(voucherResponse);
//
//        
//
//		if(voucherJsonResponse.getBoolean("status")) { 
//			responseMap.put("status","SUCCESS");
//		}else {
//			//loginsevice.rsendEmailVerificationCompletion(userForm);
//			responseMap.put("status","FAILURE");
//		}
// 
//		return new ObjectMapper().writeValueAsString(responseMap);
        try {
            String json = EncryptionDecriptionUtil.convertToJson(voucherTypeMaster);
            EncriptResponse encryptedRequest = EncryptionDecriptionUtil.encriptResponse(json, applicationConstantConfig.apiSignaturePublicPath);
            
            String encryptedResponse = voucherTypeMasterService.updatevoucherTypeMasterStatus(tokengeneration.getToken(), encryptedRequest);
            EncriptResponse responseObject = EncryptionDecriptionUtil.convertFromJson(encryptedResponse, EncriptResponse.class);
            String voucherResponse1 = EncryptionDecriptionUtil.decriptResponse(responseObject.getEncriptData(), responseObject.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
            
            JSONObject voucherJsonResponse1 = new JSONObject(voucherResponse1);
            if (voucherJsonResponse1.getBoolean("status")) {
                responseMap.put("status", "SUCCESS");
            } else {
                responseMap.put("status", "FAILURE");
            }
            
            jsonResponse = mapper.writeValueAsString(responseMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return jsonResponse;
        
		}
	
	@PostMapping(value="/createVoucher")
	public @ResponseBody String createVoucher(ModelMap model, Locale locale, HttpSession session,ErupiVoucherCreateRequest erupiVoucherCreateRequest) {
 
        
      
        String profileRes = null;

        String receivedHash = erupiVoucherCreateRequest.getHash();
		 if (!CLIENT_KEY.equals(erupiVoucherCreateRequest.getClientKey())) {
	          // return Map.of("isValid", false, "message", "Invalid client key");
	        }
//		    logger.info("erupiVoucherCreateRequest.getEmployerId()+"+erupiVoucherCreateRequest.getEmployerId());
//	        logger.info("erupiVoucherCreateRequest.getEmployeeId()+"+erupiVoucherCreateRequest.getEmployeeId());
//	        logger.info("erupiVoucherCreateRequest.getPurposeCode()+"+erupiVoucherCreateRequest.getPurposeCode());
//	        logger.info("erupiVoucherCreateRequest.getMcc()+"+erupiVoucherCreateRequest.getMcc());
//	        logger.info("erupiVoucherCreateRequest.getName()+"+erupiVoucherCreateRequest.getName());
//	        logger.info("erupiVoucherCreateRequest.getVoucherType()+"+erupiVoucherCreateRequest.getVoucherType());
//	        logger.info("erupiVoucherCreateRequest.getVoucherSubType()+"+erupiVoucherCreateRequest.getVoucherSubType());
//	        logger.info("erupiVoucherCreateRequest.getMobile()+"+erupiVoucherCreateRequest.getMobile());
//	        logger.info("erupiVoucherCreateRequest.getAmount()+"+erupiVoucherCreateRequest.getAmount());
//	        logger.info("erupiVoucherCreateRequest.getRemarks()+"+erupiVoucherCreateRequest.getRemarks());
	        // Ensure consistent concatenation
	        String dataString =
	        		//added String.valueOf because it was summing employerid and employeeid
	        		String.valueOf(erupiVoucherCreateRequest.getEmployerId()) +
	        	    String.valueOf(erupiVoucherCreateRequest.getEmployeeId()) +
	        		erupiVoucherCreateRequest.getPurposeCode()+
	        		erupiVoucherCreateRequest.getMcc()+
	        		erupiVoucherCreateRequest.getName()+
	        		erupiVoucherCreateRequest.getVoucherType()+
	        		erupiVoucherCreateRequest.getVoucherSubType()+
	        		erupiVoucherCreateRequest.getMobile()+
	        		erupiVoucherCreateRequest.getAmount()+
	        		erupiVoucherCreateRequest.getRemarks()+
	        		CLIENT_KEY+SECRET_KEY;
	        logger.info("dataString"+dataString);
	        
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
			    logger.info("obj.getOrgid()"+obj.getOrgid());
			    //here we are comparing employeeid with orgid because we are getting empid in session 
   if ((obj.getUser_role() == 2) && obj.getOrgid() == erupiVoucherCreateRequest.getEmployerId().intValue()) {
        
        try {
           // jsonResponse = mapper.writeValueAsString(responseMap);
        	String json = EncryptionDecriptionUtil.convertToJson(erupiVoucherCreateRequest); 
            EncriptResponse jsonObject = EncryptionDecriptionUtil.encriptResponse(json, applicationConstantConfig.apiSignaturePublicPath);
            
            String encriptResponse = voucherTypeMasterService.createVoucher(tokengeneration.getToken(), jsonObject);
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
	@GetMapping(value="/getRequestedVoucherList")
	public @ResponseBody String getRequestedVoucherList(ModelMap model, Locale locale, HttpSession session,ErupiVoucherCreateRequest erupiVoucherCreateRequest) {
    
        Map<String, Object> responseMap = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();
        String jsonResponse = null;
        
        try {
            //jsonResponse = mapper.writeValueAsString(responseMap);
        	String json = EncryptionDecriptionUtil.convertToJson(erupiVoucherCreateRequest);
            EncriptResponse encryptedRequest = EncryptionDecriptionUtil.encriptResponse(json, applicationConstantConfig.apiSignaturePublicPath);
            
            String encryptedResponse = voucherTypeMasterService.getRequestedVoucherList(tokengeneration.getToken(), encryptedRequest);
            EncriptResponse responseObject = EncryptionDecriptionUtil.convertFromJson(encryptedResponse, EncriptResponse.class);
            String voucherResponse1 = EncryptionDecriptionUtil.decriptResponse(responseObject.getEncriptData(), responseObject.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
            
            JSONObject voucherJsonResponse1 = new JSONObject(voucherResponse1);
            if (voucherJsonResponse1.getBoolean("status")) {
                List<Object> voucherList = voucherJsonResponse1.getJSONArray("data").toList();
                responseMap.put("status", true);
                responseMap.put("data", voucherList);
            } else {
                responseMap.put("status", false);
            }
            
            jsonResponse = mapper.writeValueAsString(responseMap);
        
        } catch (Exception e) {
            e.printStackTrace(); 
        }       
        return jsonResponse;
    }

	@GetMapping(value="/getRequestedVoucherApproveList")
	public @ResponseBody String getRequestedVoucherApproveList(ModelMap model, Locale locale, HttpSession session,ErupiVoucherCreateRequest erupiVoucherCreateRequest) {
    
        Map<String, Object> responseMap = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();
        String jsonResponse = null;
        
        try {
            //jsonResponse = mapper.writeValueAsString(responseMap);
        	String json = EncryptionDecriptionUtil.convertToJson(erupiVoucherCreateRequest);
            EncriptResponse encryptedRequest = EncryptionDecriptionUtil.encriptResponse(json, applicationConstantConfig.apiSignaturePublicPath);
            
            String encryptedResponse = voucherTypeMasterService.getRequestedVoucherApproveList(tokengeneration.getToken(), encryptedRequest);
            EncriptResponse responseObject = EncryptionDecriptionUtil.convertFromJson(encryptedResponse, EncriptResponse.class);
            String voucherResponse1 = EncryptionDecriptionUtil.decriptResponse(responseObject.getEncriptData(), responseObject.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
            
            JSONObject voucherJsonResponse1 = new JSONObject(voucherResponse1);
            if (voucherJsonResponse1.getBoolean("status")) {
                List<Object> voucherList = voucherJsonResponse1.getJSONArray("data").toList();
                responseMap.put("status", true);
                responseMap.put("data", voucherList);
            } else {
                responseMap.put("status", false);
            }
            
            jsonResponse = mapper.writeValueAsString(responseMap);
        
        } catch (Exception e) {
            e.printStackTrace(); 
        }       
        return jsonResponse;
    }

	@GetMapping(value="/erupiVoucherRequestByMngId")
	public @ResponseBody String erupiVoucherRequestByMngId(ModelMap model, Locale locale, HttpSession session,EmployeeOnboardingManagerRequest erupiVoucherCreateRequest) {
    
        Map<String, Object> responseMap = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();
        String jsonResponse = null;
        
        try {
            //jsonResponse = mapper.writeValueAsString(responseMap);
        	String json = EncryptionDecriptionUtil.convertToJson(erupiVoucherCreateRequest);
            EncriptResponse encryptedRequest = EncryptionDecriptionUtil.encriptResponse(json, applicationConstantConfig.apiSignaturePublicPath);
            
            String encryptedResponse = voucherTypeMasterService.erupiVoucherRequestByMngId(tokengeneration.getToken(), encryptedRequest);
            EncriptResponse responseObject = EncryptionDecriptionUtil.convertFromJson(encryptedResponse, EncriptResponse.class);
            String voucherResponse1 = EncryptionDecriptionUtil.decriptResponse(responseObject.getEncriptData(), responseObject.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
            
            JSONObject voucherJsonResponse1 = new JSONObject(voucherResponse1);
            if (voucherJsonResponse1.getBoolean("status")) {
                List<Object> voucherList = voucherJsonResponse1.getJSONArray("data").toList();
                responseMap.put("status", true);
                responseMap.put("data", voucherList);
            } else {
                responseMap.put("status", false);
            }
            
            jsonResponse = mapper.writeValueAsString(responseMap);
        
        } catch (Exception e) {
            e.printStackTrace(); 
        }       
        return jsonResponse;
    }
	@PostMapping(value="/approveRejectVoucherRequest")
	public @ResponseBody String approveRejectVoucher(ModelMap model, Locale locale, HttpSession session,EmployeeOnboardingManagerRequest erupiVoucherCreateRequest) {
    
        Map<String, Object> responseMap = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();
        String jsonResponse = null;
        
        try {
            //jsonResponse = mapper.writeValueAsString(responseMap);
        	String json = EncryptionDecriptionUtil.convertToJson(erupiVoucherCreateRequest);
            EncriptResponse encryptedRequest = EncryptionDecriptionUtil.encriptResponse(json, applicationConstantConfig.apiSignaturePublicPath);
            
            String encryptedResponse = voucherTypeMasterService.approveRejectVoucher(tokengeneration.getToken(), encryptedRequest);
            EncriptResponse responseObject = EncryptionDecriptionUtil.convertFromJson(encryptedResponse, EncriptResponse.class);
            String voucherResponse1 = EncryptionDecriptionUtil.decriptResponse(responseObject.getEncriptData(), responseObject.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
            
            JSONObject voucherJsonResponse1 = new JSONObject(voucherResponse1);
            if (voucherJsonResponse1.getBoolean("status")) {
                responseMap.put("status", true);
                responseMap.put("message", voucherJsonResponse1.getString("message"));
            } else {
                responseMap.put("status", false);
            }
            
            jsonResponse = mapper.writeValueAsString(responseMap);
        
        } catch (Exception e) {
            e.printStackTrace(); 
        }       
        return jsonResponse;
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
