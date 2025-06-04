package com.cotodel.hrms.web.controller;


import java.io.File;
import java.io.FileInputStream;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cotodel.hrms.web.properties.ApplicationConstantConfig;
import com.cotodel.hrms.web.response.BankVerificationRequest;
import com.cotodel.hrms.web.response.BulkEmployeeRequest;
import com.cotodel.hrms.web.response.EmployeeOnboardingDriverRequest;
import com.cotodel.hrms.web.response.RCRequest;
import com.cotodel.hrms.web.response.VehicleBulkCreateRequest;
import com.cotodel.hrms.web.response.VehicleManagementBulkUploadRequest;
import com.cotodel.hrms.web.response.VehicleManagementRequest;
import com.cotodel.hrms.web.response.VehicleManagementSaveRequest;
import com.cotodel.hrms.web.service.VehicleManagementService;
import com.cotodel.hrms.web.service.Impl.TokenGenerationImpl;
import com.cotodel.hrms.web.util.EncriptResponse;
import com.cotodel.hrms.web.util.EncryptionDecriptionUtil;
import com.cotodel.hrms.web.util.MessageConstant;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@CrossOrigin
public class VehicleManagementController extends CotoDelBaseController {

	private static final Logger logger = LoggerFactory.getLogger(VehicleManagementController.class);
	private static final String SECRET_KEY = "0123456789012345"; // Must match frontend
    private static final String CLIENT_KEY = "client-secret-key"; // Extra validation

	@Autowired
	TokenGenerationImpl tokengeneration;
	
	@Autowired
	VehicleManagementService vehicleManagementService;
	
	@Autowired
	public ApplicationConstantConfig applicationConstantConfig;
	
	@GetMapping(value = "/checkAccountNumberValidation")
	public @ResponseBody String checkAccountNumberValidation(HttpServletRequest request, ModelMap model, Locale locale,
	    HttpSession session, BankVerificationRequest bankVerificationRequest) {
		String profileRes = null;
		
		try {
			String json = EncryptionDecriptionUtil.convertToJson(bankVerificationRequest);

			EncriptResponse jsonObject=EncryptionDecriptionUtil.encriptResponse(json, applicationConstantConfig.apiSignaturePublicPath);

			String encriptResponse =  vehicleManagementService.checkAccountNumberValidation(tokengeneration.getToken(), jsonObject);
  
			EncriptResponse userReqEnc =EncryptionDecriptionUtil.convertFromJson(encriptResponse, EncriptResponse.class);

			profileRes =  EncryptionDecriptionUtil.decriptResponse(userReqEnc.getEncriptData(), userReqEnc.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
   
    	return profileRes;
	}
	
	@GetMapping(value = "/getVehicleDetaiilByRC")
	public @ResponseBody String getVehicleNumberDetaiilByVehicleNumber(HttpServletRequest request, ModelMap model, Locale locale,
	    HttpSession session, RCRequest rCRequest) {
		String profileRes = null;
		
		try {
			String json = EncryptionDecriptionUtil.convertToJson(rCRequest);

			EncriptResponse jsonObject=EncryptionDecriptionUtil.encriptResponse(json, applicationConstantConfig.apiSignaturePublicPath);

			String encriptResponse =  vehicleManagementService.getVehicleNumberDetaiilByVehicleNumber(tokengeneration.getToken(), jsonObject);
  
			EncriptResponse userReqEnc =EncryptionDecriptionUtil.convertFromJson(encriptResponse, EncriptResponse.class);

			profileRes =  EncryptionDecriptionUtil.decriptResponse(userReqEnc.getEncriptData(), userReqEnc.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
   
    	return profileRes;
	}
	@GetMapping(value = "/getVehicleList")
	public @ResponseBody String VehicleManagementList(HttpServletRequest request, ModelMap model, Locale locale,
			HttpSession session, VehicleManagementRequest vehicleManagementRequest) {
		String profileRes = null;
		
		try {
			String json = EncryptionDecriptionUtil.convertToJson(vehicleManagementRequest);

			EncriptResponse jsonObject=EncryptionDecriptionUtil.encriptResponse(json, applicationConstantConfig.apiSignaturePublicPath);

			String encriptResponse =  vehicleManagementService.getVehicleManagementList(tokengeneration.getToken(), jsonObject);
  
			EncriptResponse userReqEnc =EncryptionDecriptionUtil.convertFromJson(encriptResponse, EncriptResponse.class);

			profileRes =  EncryptionDecriptionUtil.decriptResponse(userReqEnc.getEncriptData(), userReqEnc.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
   
    	return profileRes;
	}
	

	@GetMapping(value = "/getEmployeeDriver")
	public @ResponseBody String getEmployeeDriver(HttpServletRequest request, ModelMap model, Locale locale,
			HttpSession session, EmployeeOnboardingDriverRequest vehicleManagementRequest) {
		String profileRes = null;
		
		try {
			String json = EncryptionDecriptionUtil.convertToJson(vehicleManagementRequest);

			EncriptResponse jsonObject=EncryptionDecriptionUtil.encriptResponse(json, applicationConstantConfig.apiSignaturePublicPath);

			String encriptResponse =  vehicleManagementService.getEmployeeDriver(tokengeneration.getToken(), jsonObject);

			EncriptResponse userReqEnc =EncryptionDecriptionUtil.convertFromJson(encriptResponse, EncriptResponse.class);

			profileRes =  EncryptionDecriptionUtil.decriptResponse(userReqEnc.getEncriptData(), userReqEnc.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
   return profileRes;
}
	
	@PostMapping(value = "/addVehicleDetails")
	public @ResponseBody String addVehicleDetails(HttpServletRequest request, ModelMap model, Locale locale,
			HttpSession session, VehicleManagementSaveRequest vManagementSaveRequest) {
		String profileRes = null;
		
		try {
			String json = EncryptionDecriptionUtil.convertToJson(vManagementSaveRequest);

			EncriptResponse jsonObject=EncryptionDecriptionUtil.encriptResponse(json, applicationConstantConfig.apiSignaturePublicPath);

			String encriptResponse =  vehicleManagementService.addVehicleDetails(tokengeneration.getToken(), jsonObject);
 
			EncriptResponse userReqEnc =EncryptionDecriptionUtil.convertFromJson(encriptResponse, EncriptResponse.class);

			profileRes =  EncryptionDecriptionUtil.decriptResponse(userReqEnc.getEncriptData(), userReqEnc.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
   
		return profileRes;	
}


	@PostMapping(value = "/updateVehicleDetails")
	public @ResponseBody String updateVehicleDetails(HttpServletRequest request, ModelMap model, Locale locale,
			HttpSession session, VehicleManagementSaveRequest vManagementSaveRequest) {
		String profileRes = null;
		
		try {
			String json = EncryptionDecriptionUtil.convertToJson(vManagementSaveRequest);

			EncriptResponse jsonObject=EncryptionDecriptionUtil.encriptResponse(json, applicationConstantConfig.apiSignaturePublicPath);

			String encriptResponse =  vehicleManagementService.updateVehicleDetails(tokengeneration.getToken(), jsonObject);
   
			EncriptResponse userReqEnc =EncryptionDecriptionUtil.convertFromJson(encriptResponse, EncriptResponse.class);

			profileRes =  EncryptionDecriptionUtil.decriptResponse(userReqEnc.getEncriptData(), userReqEnc.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
   
	return profileRes;
}

	
	@GetMapping(value = "/getVehicleManagementById")
	public @ResponseBody String getVehicleManagementById(HttpServletRequest request, ModelMap model, Locale locale,
			HttpSession session, VehicleManagementRequest vehicleManagementRequest) {
		String profileRes = null;
		
		try {
			String json = EncryptionDecriptionUtil.convertToJson(vehicleManagementRequest);

			EncriptResponse jsonObject=EncryptionDecriptionUtil.encriptResponse(json, applicationConstantConfig.apiSignaturePublicPath);

			String encriptResponse =  vehicleManagementService.getVehicleManagementById(tokengeneration.getToken(), jsonObject);
   
			EncriptResponse userReqEnc =EncryptionDecriptionUtil.convertFromJson(encriptResponse, EncriptResponse.class);

			profileRes =  EncryptionDecriptionUtil.decriptResponse(userReqEnc.getEncriptData(), userReqEnc.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
   
		return profileRes;
	}
	
	@GetMapping(value = "/vehichleTripHistory")
	public @ResponseBody String vehichleTripHistory(HttpServletRequest request, ModelMap model, Locale locale,
			HttpSession session, VehicleManagementRequest vehicleManagementRequest) {
		String profileRes = null;
		
		try {
			String json = EncryptionDecriptionUtil.convertToJson(vehicleManagementRequest);

			EncriptResponse jsonObject=EncryptionDecriptionUtil.encriptResponse(json, applicationConstantConfig.apiSignaturePublicPath);

			String encriptResponse =  vehicleManagementService.vehichleTripHistory(tokengeneration.getToken(), jsonObject);
  
			EncriptResponse userReqEnc =EncryptionDecriptionUtil.convertFromJson(encriptResponse, EncriptResponse.class);

			profileRes =  EncryptionDecriptionUtil.decriptResponse(userReqEnc.getEncriptData(), userReqEnc.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
   
    	return profileRes;
	}
	
//	@PostMapping(value="/saveBulkVehicleDetail")
//	public String saveBulkVehicleDetail(HttpServletResponse response, HttpServletRequest request,
//			@ModelAttribute("formData") BulkEmployeeRequest bulkEmployeeRequest, BindingResult result, HttpSession session, Model model,RedirectAttributes redirect) {
//		
//		String profileRes=null;JSONObject profileJsonRes=null;
//		HashMap<String, String> otpMap = new  HashMap<String, String> ();
//		ObjectMapper mapper = new ObjectMapper();
//		String res = null; 
//		int orgid=(int)request.getSession(true).getAttribute("id");
//		Long emplrid=(long)orgid;
//		bulkEmployeeRequest.setEmployerId(emplrid);
//		profileRes = vehicleManagementService.saveBulkVehicleDetail(tokengeneration.getToken(),bulkEmployeeRequest);
//		profileJsonRes= new JSONObject(profileRes);
//		if(profileJsonRes.getString("status").equalsIgnoreCase("SUCCESS")) { 
//			otpMap.put("status", MessageConstant.RESPONSE_SUCCESS);
//		}else {
//			//loginservice.sendEmailVerificationCompletion(userForm);
//			otpMap.put("status", MessageConstant.RESPONSE_FAILED);
//		}
//		try {
//			res = mapper.writeValueAsString(otpMap);
//		} catch (Exception e) {
//			// TODO: handle exception
//		}
//		  session.setAttribute("list",profileRes); logger.info(profileRes);
//		  model.addAttribute("list",profileRes); 
//		  return "bulk-table-invitelist";
//		  
//	}
	

	@GetMapping(value = "/getVehicleTemplate")
	public ResponseEntity<InputStreamResource> getVoucherTemplate() {
		try {
			//String filePath ="D:\\opt\\file\\"; //local path 
			String filePath ="/opt/cotodel/key/";
			String fileName = "Bulk_Vehicle_Templates.xlsx";
			File file = new File(filePath+fileName);
			HttpHeaders headers = new HttpHeaders();    
			
			headers.add("content-disposition", "inline;filename=" +fileName);

			InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

			return ResponseEntity.ok()
					.headers(headers)
					.contentLength(file.length())
					.contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
					.body(resource);

		}catch (Exception e) {
			logger.info(e.getMessage());// TODO: handle exception
		}
		return null;
	}
	
	@PostMapping(value="/saveBulkVehicle")
	public @ResponseBody String saveBulkVehicle(HttpServletResponse response, HttpServletRequest request,
			VehicleManagementBulkUploadRequest vehicleManagementBulkUploadRequest, BindingResult result, HttpSession session, Model model, RedirectAttributes redirect) {

	    String profileRes = null;
	    Map<String, Object> responseMap = new HashMap<>();
	    ObjectMapper mapper = new ObjectMapper();
	    
	    String receivedHash = vehicleManagementBulkUploadRequest.getHash();

	    // Validate client key only
	    if (!CLIENT_KEY.equals(vehicleManagementBulkUploadRequest.getClientKey())) {
	        responseMap.put("status", false);
	        responseMap.put("message", "Invalid client key");
	        try {
	            return mapper.writeValueAsString(responseMap);
	        } catch (JsonProcessingException e) {
	            return "{\"status\":false, \"message\":\"JSON processing error\"}";
	        }
	    }
	    System.out.println("vehicleManagementBulkUploadRequest.getOrgId() " + vehicleManagementBulkUploadRequest.getOrgId());
        System.out.println("vehicleManagementBulkUploadRequest.getFileName() " + vehicleManagementBulkUploadRequest.getFileName());
        System.out.println("vehicleManagementBulkUploadRequest.getFile() " + vehicleManagementBulkUploadRequest.getFile());
        System.out.println("vehicleManagementBulkUploadRequest.getCreatedBy() " + vehicleManagementBulkUploadRequest.getCreatedBy());
	    
	    // Prepare data string for hashing
	    String dataString = vehicleManagementBulkUploadRequest.getOrgId() + vehicleManagementBulkUploadRequest.getFileName() + vehicleManagementBulkUploadRequest.getFile() +
	            
	    		vehicleManagementBulkUploadRequest.getCreatedBy() + CLIENT_KEY + SECRET_KEY;

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
//	    String token = (String) session.getAttribute("hrms");
//	    if (token == null) {
//	        responseMap.put("status", false);
//	        responseMap.put("message", "Unauthorized: No token found.");
//	        try {
//	            return mapper.writeValueAsString(responseMap);
//	        } catch (JsonProcessingException e) {
//	            return "{\"status\":false, \"message\":\"JSON processing error\"}";
//	        }
//	    }

//	    // Validate Token
//	    UserDetailsEntity obj = JwtTokenValidator.parseToken(token);
//	    if (obj == null) {
//	        responseMap.put("status", false);
//	        responseMap.put("message", "Unauthorized: Invalid token.");
//	        try {
//	            return mapper.writeValueAsString(responseMap);
//	        } catch (JsonProcessingException e) {
//	            return "{\"status\":false, \"message\":\"JSON processing error\"}";
//	        }
//	    }

	    // Check User Role and Organization ID
	   // if ((obj.getUser_role() == 9 || obj.getUser_role() == 1 || obj.getUser_role() == 3) && obj.getOrgid() == bulkVoucherRequest.getOrgId().intValue()) {
	        try {
	            String json = EncryptionDecriptionUtil.convertToJson(vehicleManagementBulkUploadRequest);
	            EncriptResponse jsonObject = EncryptionDecriptionUtil.encriptResponse(json, applicationConstantConfig.apiSignaturePublicPath);
	            String encriptResponse = vehicleManagementService.saveBulkVehicleDetail(tokengeneration.getToken(), jsonObject);

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
	            }

	        } catch (Exception e) {
	            responseMap.put("status", false);
	            responseMap.put("message", "Internal Server Error: " + e.getMessage());
	            e.printStackTrace();
	        }
//	    } else {
//	        responseMap.put("status", false);
//	        responseMap.put("message", "Unauthorized: Insufficient permissions.");
//	    }

	    try {
	        return mapper.writeValueAsString(responseMap);
	    } catch (JsonProcessingException e) {
	        return "{\"status\":false, \"message\":\"JSON processing error\"}";
	    }
	} 

	@PostMapping(value="/createBulkVehicle")
	public @ResponseBody String createBulkVehicle(HttpServletResponse response, HttpServletRequest request,
			VehicleBulkCreateRequest vehicleBulkCreateRequest, BindingResult result, HttpSession session, Model model, RedirectAttributes redirect) {

	    String profileRes = null;
	    Map<String, Object> responseMap = new HashMap<>();
	    ObjectMapper mapper = new ObjectMapper();
	    
	    String receivedHash = vehicleBulkCreateRequest.getHash();
	    String arrayJoined = String.join("", vehicleBulkCreateRequest.getArrayofid()); 

	    // Validate client key only
	    if (!CLIENT_KEY.equals(vehicleBulkCreateRequest.getClientKey())) {
	        responseMap.put("status", false);
	        responseMap.put("message", "Invalid client key");
	        try {
	            return mapper.writeValueAsString(responseMap);
	        } catch (JsonProcessingException e) {
	            return "{\"status\":false, \"message\":\"JSON processing error\"}";
	        }
	    }
	    System.out.println("vehicleManagementBulkUploadRequest.getOrgId() " + vehicleBulkCreateRequest.getOrgId());
        System.out.println("arrayJoined " + arrayJoined);
//        System.out.println("vehicleManagementBulkUploadRequest.getFile() " +vehicleBulkCreateRequest.getFile());
        System.out.println("vehicleManagementBulkUploadRequest.getCreatedBy() " + vehicleBulkCreateRequest.getCreatedby());
	    
	    // Prepare data string for hashing
	    String dataString = vehicleBulkCreateRequest.getOrgId() +
	            
	    		vehicleBulkCreateRequest.getCreatedby() +arrayJoined+ CLIENT_KEY + SECRET_KEY;

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
	    //isValid=true;
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
//	    String token = (String) session.getAttribute("hrms");
//	    if (token == null) {
//	        responseMap.put("status", false);
//	        responseMap.put("message", "Unauthorized: No token found.");
//	        try {
//	            return mapper.writeValueAsString(responseMap);
//	        } catch (JsonProcessingException e) {
//	            return "{\"status\":false, \"message\":\"JSON processing error\"}";
//	        }
//	    }

//	    // Validate Token
//	    UserDetailsEntity obj = JwtTokenValidator.parseToken(token);
//	    if (obj == null) {
//	        responseMap.put("status", false);
//	        responseMap.put("message", "Unauthorized: Invalid token.");
//	        try {
//	            return mapper.writeValueAsString(responseMap);
//	        } catch (JsonProcessingException e) {
//	            return "{\"status\":false, \"message\":\"JSON processing error\"}";
//	        }
//	    }

	    // Check User Role and Organization ID
	   // if ((obj.getUser_role() == 9 || obj.getUser_role() == 1 || obj.getUser_role() == 3) && obj.getOrgid() == bulkVoucherRequest.getOrgId().intValue()) {
	        try {
	            String json = EncryptionDecriptionUtil.convertToJson(vehicleBulkCreateRequest);
	            EncriptResponse jsonObject = EncryptionDecriptionUtil.encriptResponse(json, applicationConstantConfig.apiSignaturePublicPath);
	            String encriptResponse = vehicleManagementService.createBulkVehicle(tokengeneration.getToken(), jsonObject);

	            EncriptResponse userReqEnc = EncryptionDecriptionUtil.convertFromJson(encriptResponse, EncriptResponse.class);
	            profileRes = EncryptionDecriptionUtil.decriptResponse(userReqEnc.getEncriptData(), userReqEnc.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);

	            JSONObject apiJsonResponse = new JSONObject(profileRes);
	            boolean status = apiJsonResponse.getBoolean("status");
	            responseMap.put("status", status);
	            responseMap.put("message", apiJsonResponse.getString("message"));

	            if (status && apiJsonResponse.has("data")) {
	                responseMap.put("data", profileRes);
	            } else {
	                responseMap.put("status", false);
	                responseMap.put("message", apiJsonResponse.getString("message"));
	            }

	        } catch (Exception e) {
	            responseMap.put("status", false);
	            responseMap.put("message", "Internal Server Error: " + e.getMessage());
	            e.printStackTrace();
	        }
//	    } else {
//	        responseMap.put("status", false);
//	        responseMap.put("message", "Unauthorized: Insufficient permissions.");
//	    }

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
