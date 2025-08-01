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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cotodel.hrms.web.jwt.util.JwtTokenValidator;
import com.cotodel.hrms.web.properties.ApplicationConstantConfig;
import com.cotodel.hrms.web.response.BrandManagementRequest;
import com.cotodel.hrms.web.response.BulkOutletManagementRequest;
import com.cotodel.hrms.web.response.EmployeeDeactiveRequest;
import com.cotodel.hrms.web.response.ErupiBrandDetailsRequest;
import com.cotodel.hrms.web.response.ErupiBrandGeoRequest;
import com.cotodel.hrms.web.response.ErupiBrandOutletDeviceDetailsRequest;
import com.cotodel.hrms.web.response.OutletBulkCreateRequest;
import com.cotodel.hrms.web.response.UserDetailsEntity;
import com.cotodel.hrms.web.service.BrandManagementService;
import com.cotodel.hrms.web.service.Impl.TokenGenerationImpl;
import com.cotodel.hrms.web.util.EncriptResponse;
import com.cotodel.hrms.web.util.EncryptionDecriptionUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@CrossOrigin
public class BrandManagerController extends CotoDelBaseController{
	
	private static final Logger logger = LoggerFactory.getLogger(BrandManagerController.class);
	private static final String SECRET_KEY = "0123456789012345"; // Must match frontend
    private static final String CLIENT_KEY = "client-secret-key"; // Extra validation

	@Autowired
	TokenGenerationImpl tokengeneration;
	@Autowired
	public ApplicationConstantConfig applicationConstantConfig;
	
	@Autowired
	public BrandManagementService brandManagementService;
	
	@GetMapping(value = "/getOutletTemplate")
	public ResponseEntity<InputStreamResource> getOutletTemplate() {
		try {
			//String filePath ="D:\\opt\\file\\"; //local path 
			String filePath ="/opt/cotodel/key/";
			String fileName = "Bulk_Outlet_Templates.xlsx";
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
	
	@PostMapping(value = "/addOutletDetail")
	public @ResponseBody String activateBrandManagement(HttpServletRequest request, ModelMap model, Locale locale,
			HttpSession session, BrandManagementRequest brandManagementRequest) {
		String profileRes = null;
		
		try {
			String json = EncryptionDecriptionUtil.convertToJson(brandManagementRequest);

			EncriptResponse jsonObject=EncryptionDecriptionUtil.encriptResponse(json, applicationConstantConfig.apiSignaturePublicPath);

			String encriptResponse =  brandManagementService.activateBrandManagement(tokengeneration.getToken(), jsonObject);
 
			EncriptResponse userReqEnc =EncryptionDecriptionUtil.convertFromJson(encriptResponse, EncriptResponse.class);

			profileRes =  EncryptionDecriptionUtil.decriptResponse(userReqEnc.getEncriptData(), userReqEnc.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
   
		return profileRes;	
}
	@PostMapping(value = "/editBrandOutletDetail")
	public @ResponseBody String editBrandOutletDetail(HttpServletRequest request, ModelMap model, Locale locale,
			HttpSession session, BrandManagementRequest brandManagementRequest) {
		String profileRes = null;
		
		try {
			String json = EncryptionDecriptionUtil.convertToJson(brandManagementRequest);

			EncriptResponse jsonObject=EncryptionDecriptionUtil.encriptResponse(json, applicationConstantConfig.apiSignaturePublicPath);

			String encriptResponse =  brandManagementService.editBrandOutletDetail(tokengeneration.getToken(), jsonObject);
 
			EncriptResponse userReqEnc =EncryptionDecriptionUtil.convertFromJson(encriptResponse, EncriptResponse.class);

			profileRes =  EncryptionDecriptionUtil.decriptResponse(userReqEnc.getEncriptData(), userReqEnc.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
   
		return profileRes;	
}
	
	@GetMapping(value = "/getOutletDetail")
	public @ResponseBody String getOutletDetail(HttpServletRequest request, ModelMap model, Locale locale,
			HttpSession session, BrandManagementRequest brandManagementRequest) {
		String profileRes = null;
		
		try {
			String json = EncryptionDecriptionUtil.convertToJson(brandManagementRequest);

			EncriptResponse jsonObject=EncryptionDecriptionUtil.encriptResponse(json, applicationConstantConfig.apiSignaturePublicPath);

			String encriptResponse =  brandManagementService.getOutletDetail(tokengeneration.getToken(), jsonObject);
 
			EncriptResponse userReqEnc =EncryptionDecriptionUtil.convertFromJson(encriptResponse, EncriptResponse.class);

			profileRes =  EncryptionDecriptionUtil.decriptResponse(userReqEnc.getEncriptData(), userReqEnc.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
   
		return profileRes;	
}

@GetMapping(value = "/erupiBrandOutletById")
	public @ResponseBody String erupiBrandOutletById(HttpServletRequest request, ModelMap model, Locale locale,
			HttpSession session, BrandManagementRequest brandManagementRequest) {
		String profileRes = null;
		
		try {
			String json = EncryptionDecriptionUtil.convertToJson(brandManagementRequest);

			EncriptResponse jsonObject=EncryptionDecriptionUtil.encriptResponse(json, applicationConstantConfig.apiSignaturePublicPath);

			String encriptResponse =  brandManagementService.erupiBrandOutletById(tokengeneration.getToken(), jsonObject);
 
			EncriptResponse userReqEnc =EncryptionDecriptionUtil.convertFromJson(encriptResponse, EncriptResponse.class);

			profileRes =  EncryptionDecriptionUtil.decriptResponse(userReqEnc.getEncriptData(), userReqEnc.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
   
		return profileRes;	
}

	@PostMapping(value = "/addBrandDetails")
	public @ResponseBody String addBrandDetails(HttpServletRequest request, ModelMap model, Locale locale,
			HttpSession session, ErupiBrandDetailsRequest erupiBrandDetailsRequest) {
		String profileRes = null;
		
		try {
			String json = EncryptionDecriptionUtil.convertToJson(erupiBrandDetailsRequest);

			EncriptResponse jsonObject=EncryptionDecriptionUtil.encriptResponse(json, applicationConstantConfig.apiSignaturePublicPath);

			String encriptResponse =  brandManagementService.addBrandDetails(tokengeneration.getToken(), jsonObject);
 
			EncriptResponse userReqEnc =EncryptionDecriptionUtil.convertFromJson(encriptResponse, EncriptResponse.class);

			profileRes =  EncryptionDecriptionUtil.decriptResponse(userReqEnc.getEncriptData(), userReqEnc.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
   
		return profileRes;	
}
	
	@PostMapping(value = "/addGeograpgicDetails")
	public @ResponseBody String addGeograpgicDetails(HttpServletRequest request, ModelMap model, Locale locale,
			HttpSession session,@RequestBody ErupiBrandGeoRequest erupiBrandGeoRequest,BindingResult result) {
		String profileRes = null;
		
		try {
			String json = EncryptionDecriptionUtil.convertToJson(erupiBrandGeoRequest);

			EncriptResponse jsonObject=EncryptionDecriptionUtil.encriptResponse(json, applicationConstantConfig.apiSignaturePublicPath);

			String encriptResponse =  brandManagementService.addGeograpgicDetails(tokengeneration.getToken(), jsonObject);
 
			EncriptResponse userReqEnc =EncryptionDecriptionUtil.convertFromJson(encriptResponse, EncriptResponse.class);

			profileRes =  EncryptionDecriptionUtil.decriptResponse(userReqEnc.getEncriptData(), userReqEnc.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
   
		return profileRes;	
}
	@GetMapping(value = "/getBrandOutletList")
	public @ResponseBody String getBrandOutletList(HttpServletRequest request, ModelMap model, Locale locale,
			HttpSession session, ErupiBrandDetailsRequest brandManagementRequest) {
		String profileRes = null;
		
		try {
			String json = EncryptionDecriptionUtil.convertToJson(brandManagementRequest);

			EncriptResponse jsonObject=EncryptionDecriptionUtil.encriptResponse(json, applicationConstantConfig.apiSignaturePublicPath);

			String encriptResponse =  brandManagementService.getBrandOutletList(tokengeneration.getToken(), jsonObject);

			EncriptResponse userReqEnc =EncryptionDecriptionUtil.convertFromJson(encriptResponse, EncriptResponse.class);

			profileRes =  EncryptionDecriptionUtil.decriptResponse(userReqEnc.getEncriptData(), userReqEnc.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
   return profileRes;
}

	@GetMapping(value = "/getBrupiBrandGeoList")
	public @ResponseBody String getBrupiBrandGeoList(HttpServletRequest request, ModelMap model, Locale locale,
			HttpSession session, ErupiBrandGeoRequest erupiBrandGeoRequest) {
		String profileRes = null;
		
		try {
			String json = EncryptionDecriptionUtil.convertToJson(erupiBrandGeoRequest);

			EncriptResponse jsonObject=EncryptionDecriptionUtil.encriptResponse(json, applicationConstantConfig.apiSignaturePublicPath);

			String encriptResponse =  brandManagementService.getBrupiBrandGeoList(tokengeneration.getToken(), jsonObject);

			EncriptResponse userReqEnc =EncryptionDecriptionUtil.convertFromJson(encriptResponse, EncriptResponse.class);

			profileRes =  EncryptionDecriptionUtil.decriptResponse(userReqEnc.getEncriptData(), userReqEnc.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
   return profileRes;
}
	
	@PostMapping(value="/saveBulkOutlet")
	public @ResponseBody String saveBulkOutlet(HttpServletResponse response, HttpServletRequest request,
			BulkOutletManagementRequest bulkOutletManagementRequest, BindingResult result, HttpSession session, Model model, RedirectAttributes redirect) {

	    String profileRes = null;
	    Map<String, Object> responseMap = new HashMap<>();
	    ObjectMapper mapper = new ObjectMapper();
	    
	    String receivedHash = bulkOutletManagementRequest.getHash();

	    // Validate client key only
	    if (!CLIENT_KEY.equals(bulkOutletManagementRequest.getClientKey())) {
	        responseMap.put("status", false);
	        responseMap.put("message", "Invalid client key");
	        try {
	            return mapper.writeValueAsString(responseMap);
	        } catch (JsonProcessingException e) {
	            return "{\"status\":false, \"message\":\"JSON processing error\"}";
	        }
	    }
//	    System.out.println("vehicleManagementBulkUploadRequest.getOrgId() " + bulkOutletManagementRequest.getOrgId());
//        System.out.println("vehicleManagementBulkUploadRequest.getFileName() " + bulkOutletManagementRequest.getFileName());
//        System.out.println("vehicleManagementBulkUploadRequest.getFile() " + bulkOutletManagementRequest.getFile());
//        System.out.println("vehicleManagementBulkUploadRequest.getCreatedBy() " + bulkOutletManagementRequest.getCreatedBy());
//	    
	    // Prepare data string for hashing
	    String dataString = bulkOutletManagementRequest.getOrgId() + bulkOutletManagementRequest.getFileName() + bulkOutletManagementRequest.getFile() +
	            
	    		bulkOutletManagementRequest.getCreatedBy() + CLIENT_KEY + SECRET_KEY;

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

	    // Validate Token
	    UserDetailsEntity obj = (UserDetailsEntity) JwtTokenValidator.parseToken(token);
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
	    if ((obj.getUser_role() == 9 || obj.getUser_role() == 1 || obj.getUser_role() == 3)) {
	        try {
	            String json = EncryptionDecriptionUtil.convertToJson(bulkOutletManagementRequest);
	            EncriptResponse jsonObject = EncryptionDecriptionUtil.encriptResponse(json, applicationConstantConfig.apiSignaturePublicPath);
	            String encriptResponse = brandManagementService.saveBulkOutletDetail(tokengeneration.getToken(), jsonObject);

	            EncriptResponse userReqEnc = EncryptionDecriptionUtil.convertFromJson(encriptResponse, EncriptResponse.class);
	            profileRes = EncryptionDecriptionUtil.decriptResponse(userReqEnc.getEncriptData(), userReqEnc.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);

	            JSONObject apiJsonResponse = new JSONObject(profileRes);
	            boolean status = apiJsonResponse.getBoolean("status");
	            responseMap.put("status", status);
	            responseMap.put("message", apiJsonResponse.getString("message"));

	            if (status && apiJsonResponse.has("data")) {
	                responseMap.put("data", profileRes);
	            } else {
	            	responseMap.put("data", profileRes);
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

	@PostMapping(value="/createBulkOutlet")
	public @ResponseBody String createBulkOutlet(HttpServletResponse response, HttpServletRequest request,
			OutletBulkCreateRequest outletBulkCreateRequest, BindingResult result, HttpSession session, Model model, RedirectAttributes redirect) {

	    String profileRes = null;
	    Map<String, Object> responseMap = new HashMap<>();
	    ObjectMapper mapper = new ObjectMapper();
	    
	    String receivedHash = outletBulkCreateRequest.getHash();
	    String arrayJoined = String.join("", outletBulkCreateRequest.getArrayofid()); 

	    // Validate client key only
	    if (!CLIENT_KEY.equals(outletBulkCreateRequest.getClientKey())) {
	        responseMap.put("status", false);
	        responseMap.put("message", "Invalid client key");
	        try {
	            return mapper.writeValueAsString(responseMap);
	        } catch (JsonProcessingException e) {
	            return "{\"status\":false, \"message\":\"JSON processing error\"}";
	        }
	    }
	    System.out.println("outletBulkCreateRequest.getOrgId() " + outletBulkCreateRequest.getOrgId());
        System.out.println("arrayJoined " + arrayJoined);
//        System.out.println("outletBulkCreateRequest.getFile() " +vehicleBulkCreateRequest.getFile());
        System.out.println("outletBulkCreateRequest.getCreatedBy() " + outletBulkCreateRequest.getCreatedby());
	    
	    // Prepare data string for hashing
	    String dataString = outletBulkCreateRequest.getOrgId() +
	            
	    		outletBulkCreateRequest.getCreatedby() +arrayJoined+ CLIENT_KEY + SECRET_KEY;

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

//	    // Validate Token
	    UserDetailsEntity obj = (UserDetailsEntity) JwtTokenValidator.parseToken(token);
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
	     //if ((obj.getUser_role() == 9 || obj.getUser_role() == 1 || obj.getUser_role() == 3) && obj.getOrgid() == outletBulkCreateRequest.getOrgId().longValue()) {
	    if ((obj.getUser_role() == 9 || obj.getUser_role() == 1 || obj.getUser_role() == 3)) {    
	     try {
	            String json = EncryptionDecriptionUtil.convertToJson(outletBulkCreateRequest);
	            EncriptResponse jsonObject = EncryptionDecriptionUtil.encriptResponse(json, applicationConstantConfig.apiSignaturePublicPath);
	            String encriptResponse = brandManagementService.createBulkOutlet(tokengeneration.getToken(), jsonObject);

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
	
	@PostMapping(value="/addOutletDeviceDetails")
	public @ResponseBody String saveBrandOutletDeviceDetails(HttpServletResponse response, HttpServletRequest request,
			ErupiBrandOutletDeviceDetailsRequest erupiBrandOutletDeviceDetailsRequest, BindingResult result, HttpSession session, Model model, RedirectAttributes redirect) {

	    String profileRes = null;
	    Map<String, Object> responseMap = new HashMap<>();
	    ObjectMapper mapper = new ObjectMapper();
	    
	    String receivedHash = erupiBrandOutletDeviceDetailsRequest.getHash();
	    
	    // Validate client key only
	    if (!CLIENT_KEY.equals(erupiBrandOutletDeviceDetailsRequest.getClientKey())) {
	        responseMap.put("status", false);
	        responseMap.put("message", "Invalid client key");
	        try {
	            return mapper.writeValueAsString(responseMap);
	        } catch (JsonProcessingException e) {
	            return "{\"status\":false, \"message\":\"JSON processing error\"}";
	        }
	    }
	    // Prepare data string for hashing
	    String dataString = erupiBrandOutletDeviceDetailsRequest.getOrgid() + CLIENT_KEY + SECRET_KEY;

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
	    UserDetailsEntity obj = (UserDetailsEntity) JwtTokenValidator.parseToken(token);
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
	     //if ((obj.getUser_role() == 9 || obj.getUser_role() == 1 || obj.getUser_role() == 3) && obj.getOrgid() == outletBulkCreateRequest.getOrgId().longValue()) {
	    if ((obj.getUser_role() == 9 || obj.getUser_role() == 1 || obj.getUser_role() == 3)) {    
	     try {
	            String json = EncryptionDecriptionUtil.convertToJson(erupiBrandOutletDeviceDetailsRequest);
	            EncriptResponse jsonObject = EncryptionDecriptionUtil.encriptResponse(json, applicationConstantConfig.apiSignaturePublicPath);
	            String encriptResponse = brandManagementService.saveBrandOutletDeviceDetails(tokengeneration.getToken(), jsonObject);

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
	
	@GetMapping(value = "/getDeviceDetailList")
	public @ResponseBody String getDeviceDetailList(HttpServletRequest request, ModelMap model, Locale locale,
			HttpSession session, ErupiBrandOutletDeviceDetailsRequest erupiBrandOutletDeviceDetailsRequest) {
		String profileRes = null;
		
		try {
			String json = EncryptionDecriptionUtil.convertToJson(erupiBrandOutletDeviceDetailsRequest);

			EncriptResponse jsonObject=EncryptionDecriptionUtil.encriptResponse(json, applicationConstantConfig.apiSignaturePublicPath);

			String encriptResponse =  brandManagementService.getDeviceDetailList(tokengeneration.getToken(), jsonObject);

			EncriptResponse userReqEnc =EncryptionDecriptionUtil.convertFromJson(encriptResponse, EncriptResponse.class);

			profileRes =  EncryptionDecriptionUtil.decriptResponse(userReqEnc.getEncriptData(), userReqEnc.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
   return profileRes;
}
	
	@PostMapping(value="/deactivateOutlet")
	public @ResponseBody String deactivateOutlet(HttpServletRequest request, ModelMap model,Locale locale,HttpSession session,BrandManagementRequest brandManagementRequest) {
		String profileRes=null;

		
		try {
			String json = EncryptionDecriptionUtil.convertToJson(brandManagementRequest);

			EncriptResponse jsonObject=EncryptionDecriptionUtil.encriptResponse(json, applicationConstantConfig.apiSignaturePublicPath);

			String encriptResponse = brandManagementService.deactivateOutlet(tokengeneration.getToken(), jsonObject);

   
			EncriptResponse userReqEnc =EncryptionDecriptionUtil.convertFromJson(encriptResponse, EncriptResponse.class);

			profileRes =  EncryptionDecriptionUtil.decriptResponse(userReqEnc.getEncriptData(), userReqEnc.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return profileRes;
		
	}
	@PostMapping(value="/activateDeactivateLinkedDevice")
	public @ResponseBody String activateDeactivateLinkedDevice(HttpServletRequest request, ModelMap model,Locale locale,
			HttpSession session,ErupiBrandOutletDeviceDetailsRequest erupiBrandOutletDeviceDetailsRequest) {
		String profileRes=null;

		
		try {
			String json = EncryptionDecriptionUtil.convertToJson(erupiBrandOutletDeviceDetailsRequest);

			EncriptResponse jsonObject=EncryptionDecriptionUtil.encriptResponse(json, applicationConstantConfig.apiSignaturePublicPath);

			String encriptResponse = brandManagementService.activateDeactivateLinkedDevice(tokengeneration.getToken(), jsonObject);

   
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
