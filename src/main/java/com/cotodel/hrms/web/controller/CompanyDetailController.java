package com.cotodel.hrms.web.controller;

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
import com.cotodel.hrms.web.service.CompanyService;
import com.cotodel.hrms.web.service.Impl.TokenGenerationImpl;
import com.cotodel.hrms.web.util.EncriptResponse;
import com.cotodel.hrms.web.util.EncryptionDecriptionUtil;
import com.cotodel.hrms.web.util.MessageConstant;
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
	
	@PostMapping(value="/saveCompanyDetail")
	public @ResponseBody String saveCompanyDetail(HttpServletRequest request, ModelMap model,Locale locale,
			HttpSession session,EmployeeProfileRequest employeeProfileRequest) {
		String profileRes=null;JSONObject profileJsonRes=null;
		HashMap<String, String> otpMap = new  HashMap<String, String> ();
		ObjectMapper mapper = new ObjectMapper();
		String res = null;String userRes = null;
//		profileRes = companyService.saveCompany(tokengeneration.getToken(),employeeProfileRequest);
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
			String json = EncryptionDecriptionUtil.convertToJson(employeeProfileRequest);

			EncriptResponse jsonObject=EncryptionDecriptionUtil.encriptResponse(json, applicationConstantConfig.apiSignaturePublicPath);

			String encriptResponse = companyService.saveCompany(tokengeneration.getToken(), jsonObject);

   
			EncriptResponse userReqEnc =EncryptionDecriptionUtil.convertFromJson(encriptResponse, EncriptResponse.class);

			profileRes =  EncryptionDecriptionUtil.decriptResponse(userReqEnc.getEncriptData(), userReqEnc.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
   
	return profileRes;
	}
	
	@PostMapping(value="/getCompanyProfileStatus")
	public @ResponseBody String getCompanyProfileStatus(HttpServletRequest request, ModelMap model,Locale locale,
			HttpSession session,EmployeeProfileRequest employeeProfileRequest) {
			logger.info("getPayrollMaster");	
			String token = (String) session.getAttribute("hrms");
			String profileRes=null;
			//return companyService.getCompanyProfileStatus(tokengeneration.getToken(),employeeProfileRequest);
			
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
	
	@GetMapping(value="/getorgsubType")
	public @ResponseBody String getorgsubType(HttpServletRequest request, ModelMap model,Locale locale,
			HttpSession session,EmployeeProfileRequest employeeProfileRequest) {
	     String companyResponse = null;
	        JSONObject companyJsonResponse = null;
	        Map<String, Object> responseMap = new HashMap<>();
	        ObjectMapper mapper = new ObjectMapper();
	        String jsonResponse = null;	
	        String profileRes=null;
			//String token = (String) session.getAttribute("hrms");
//			companyResponse= companyService.getorgsubType(tokengeneration.getToken(),employeeProfileRequest);
//			 companyJsonResponse = new JSONObject(companyResponse);      
//		        if(companyJsonResponse.getBoolean("status")) { 
//					
//					List<Object> companyList = companyJsonResponse.getJSONArray("data").toList();
//					responseMap.put("status",true);
//					responseMap.put("data", companyList);
//		        }else {
//					responseMap.put("status", false);
//				}
//		        try {
//		            jsonResponse = mapper.writeValueAsString(responseMap);
//		        } catch (Exception e) {
//		            e.printStackTrace(); 
//		        }       
//		        return jsonResponse;
	        
	    	try {
				String json = EncryptionDecriptionUtil.convertToJson(employeeProfileRequest);

				EncriptResponse jsonObject=EncryptionDecriptionUtil.encriptResponse(json, applicationConstantConfig.apiSignaturePublicPath);

				String encriptResponse = companyService.getorgsubType(tokengeneration.getToken(), jsonObject);

	   
				EncriptResponse userReqEnc =EncryptionDecriptionUtil.convertFromJson(encriptResponse, EncriptResponse.class);

				profileRes =  EncryptionDecriptionUtil.decriptResponse(userReqEnc.getEncriptData(), userReqEnc.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	   
		return profileRes;
	}
	@PostMapping(value="/getpayrollDetails")
	public @ResponseBody String getpayrollDetails(HttpServletRequest request, ModelMap model,Locale locale,HttpSession session,EmployeeProfileRequest employeeProfileRequest) {
		String companyResponse = null;
        JSONObject companyJsonResponse = null;
        Map<String, Object> responseMap = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();
        String jsonResponse = null;	
        String profileRes=null;
		//String token = (String) session.getAttribute("hrms");
//		companyResponse= companyService.getpayrollDetails(tokengeneration.getToken(),employeeProfileRequest);
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

			String encriptResponse = companyService.getpayrollDetails(tokengeneration.getToken(), jsonObject);

   
			EncriptResponse userReqEnc =EncryptionDecriptionUtil.convertFromJson(encriptResponse, EncriptResponse.class);

			profileRes =  EncryptionDecriptionUtil.decriptResponse(userReqEnc.getEncriptData(), userReqEnc.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
   
	return profileRes;
	}

	@PostMapping(value="/getGSTDetailsByGSTNumber")
	public @ResponseBody String getGSTDetailsByGSTNumber(HttpServletRequest request, ModelMap model,Locale locale,HttpSession session,EmployeeProfileRequest employeeProfileRequest) {
		String companyResponse = null;
        JSONObject companyJsonResponse = null;
        Map<String, Object> responseMap = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();
        String jsonResponse = null;	
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
		String profileRes=null;JSONObject profileJsonRes=null;
		HashMap<String, String> otpMap = new  HashMap<String, String> ();
		ObjectMapper mapper = new ObjectMapper();
		String res = null;String userRes = null;
//		profileRes = companyService.saveOrganizationDetail(tokengeneration.getToken(),companyProfileDetail);
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
//		}
//		
//		return profileRes;
		
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
	
	@PostMapping(value="/updateOrganizationDetail")
	public @ResponseBody String updateOrganizationDetail(HttpServletRequest request, ModelMap model,Locale locale,
			HttpSession session,CompanyProfileDetail companyProfileDetail) {
		String profileRes=null;JSONObject profileJsonRes=null;
		HashMap<String, String> otpMap = new  HashMap<String, String> ();
		ObjectMapper mapper = new ObjectMapper();
		String res = null;String userRes = null;
//		profileRes = companyService.saveOrganizationDetail(tokengeneration.getToken(),companyProfileDetail);
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
//		}
//		
//		return profileRes;
		

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
}
