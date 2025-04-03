package com.cotodel.hrms.web.controller;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

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
import com.cotodel.hrms.web.util.EncriptResponse;
import com.cotodel.hrms.web.util.EncryptionDecriptionUtil;
import com.cotodel.hrms.web.util.JwtTokenValidator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
@RestController
@CrossOrigin
public class EmployeeDetailController extends CotoDelBaseController{

	private static final Logger logger = LoggerFactory.getLogger(EmployeeDetailController.class);

	@Autowired
	public ApplicationConstantConfig applicationConstantConfig;
	
	@Autowired
	EmployeeDetailService employeeDetailService;

	@Autowired
	TokenGenerationImpl tokengeneration;
	
	
	@PostMapping(value="/employeeOnboarding")
	public @ResponseBody String saveEmployeeOnboarding(HttpServletRequest request, ModelMap model,Locale locale,HttpSession session,EmployeeOnboarding employeeOnboarding) {
		String profileRes=null;

		try {
			String json = EncryptionDecriptionUtil.convertToJson(employeeOnboarding);

			EncriptResponse jsonObject=EncryptionDecriptionUtil.encriptResponse(json, applicationConstantConfig.apiSignaturePublicPath);

			String encriptResponse =  employeeDetailService.saveEmployeeOnboarding(tokengeneration.getToken(), jsonObject);

   
			EncriptResponse userReqEnc =EncryptionDecriptionUtil.convertFromJson(encriptResponse, EncriptResponse.class);

			profileRes =  EncryptionDecriptionUtil.decriptResponse(userReqEnc.getEncriptData(), userReqEnc.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
   
	return profileRes;
		
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
	
//	@GetMapping(value="/getEmployeeOnboardingFailList")
//	public @ResponseBody String getEmployeeOnboardingFailList(HttpServletRequest request, ModelMap model,Locale locale,HttpSession session,EmployeeOnboarding employeeOnboarding) {
//		String profileRes=null;
//
//		
//		try {
//			String json = EncryptionDecriptionUtil.convertToJson(employeeOnboarding);
//
//			EncriptResponse jsonObject=EncryptionDecriptionUtil.encriptResponse(json, applicationConstantConfig.apiSignaturePublicPath);
//
//			String encriptResponse =   employeeDetailService.getEmployeeOnboardingFailList(tokengeneration.getToken(), jsonObject);
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
//	
//	}
	
	@GetMapping(value="/getEmployeeOnboardingById")
	public @ResponseBody String getEmployeeOnboardingById(HttpServletRequest request, ModelMap model,Locale locale,HttpSession session,EmployeeOnboarding employeeOnboarding) {
		String profileRes=null;
		
//		profileRes = employeeDetailService.getEmployeeOnboardingById(tokengeneration.getToken(),employeeOnboarding);
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
//		}
//		
//		return profileRes;
		
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
		/*
		 * BulkConfirmationRequest[] jsonObj = null; try { jsonObj =
		 * mapper.readValue(employeeOnboarding,BulkConfirmationRequest[].class); } catch
		 * (JsonProcessingException e1) { // TODO Auto-generated catch block
		 * e1.printStackTrace(); }
		 */
//		String res = null; 
//		profileRes = employeeDetailService.confirmBulkEmplOnboarding(tokengeneration.getToken(),bulkConfirmationRequest);
//		profileJsonRes= new JSONObject(profileRes);
//		
//		if(profileJsonRes.getBoolean("status")) { 
//			otpMap.put("status", MessageConstant.RESPONSE_SUCCESS);
//		}else {
//			otpMap.put("status", MessageConstant.RESPONSE_FAILED);
//		}
//		try {
//			res = mapper.writeValueAsString(otpMap);
//		} catch (Exception e) {
//			// TODO: handle exception
//		}
		
//		return profileRes;
		
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
//		profileRes = employeeDetailService.saveEmployeeProfile(tokengeneration.getToken(),employeeOnboarding);
//		logger.info(profileRes);
//		profileJsonRes= new JSONObject(profileRes);
//		
//		if(profileJsonRes.getBoolean("status")) { 
//			otpMap.put("status", MessageConstant.RESPONSE_SUCCESS);
//		}else {
//			loginsevice.rsendEmailVerificationCompletion(userForm);
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
	public @ResponseBody String saveDirectorOnboarding( HttpServletRequest request,  ModelMap model,  Locale locale,  HttpSession session, @Valid DirectorOnboarding directorOnboarding) {

	    Map<String, Object> responseMap = new HashMap<>();
	    ObjectMapper mapper = new ObjectMapper();

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
	
	
}
