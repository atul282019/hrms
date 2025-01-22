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
import com.cotodel.hrms.web.response.EmployeeProfileRequest;
import com.cotodel.hrms.web.response.PayrollRequest;
import com.cotodel.hrms.web.service.CompanyService;
import com.cotodel.hrms.web.service.Impl.TokenGenerationImpl;
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
		profileRes = companyService.saveCompany(tokengeneration.getToken(),employeeProfileRequest);
		profileJsonRes= new JSONObject(profileRes);
		
		if(profileJsonRes.getBoolean("status")) { 
			otpMap.put("status", MessageConstant.RESPONSE_SUCCESS);
		}else {
			//loginservice.sendEmailVerificationCompletion(userForm);
			otpMap.put("status", MessageConstant.RESPONSE_FAILED);
		}
		try {
			res = mapper.writeValueAsString(otpMap);
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return profileRes;
	}
	
	@PostMapping(value="/getCompanyProfileStatus")
	public @ResponseBody String getCompanyProfileStatus(HttpServletRequest request, ModelMap model,Locale locale,
			HttpSession session,EmployeeProfileRequest employeeProfileRequest) {
			logger.info("getPayrollMaster");	
			String token = (String) session.getAttribute("hrms");
			return companyService.getCompanyProfileStatus(tokengeneration.getToken(),employeeProfileRequest);
	}
	
	@GetMapping(value="/getorgsubType")
	public @ResponseBody String getorgsubType(HttpServletRequest request, ModelMap model,Locale locale,
			HttpSession session,EmployeeProfileRequest employeeProfileRequest) {
	     String companyResponse = null;
	        JSONObject companyJsonResponse = null;
	        Map<String, Object> responseMap = new HashMap<>();
	        ObjectMapper mapper = new ObjectMapper();
	        String jsonResponse = null;	
			//String token = (String) session.getAttribute("hrms");
			companyResponse= companyService.getorgsubType(tokengeneration.getToken(),employeeProfileRequest);
			 companyJsonResponse = new JSONObject(companyResponse);      
		        if(companyJsonResponse.getBoolean("status")) { 
					
					//responseMap.put("data", companyJsonResponse.getJSONArray("data"));
					List<Object> companyList = companyJsonResponse.getJSONArray("data").toList();
					responseMap.put("status",true);
					responseMap.put("data", companyList);
		        }else {
					//loginsevice.rsendEmailVerificationCompletion(userForm);
					responseMap.put("status", false);
				}
		        try {
		            jsonResponse = mapper.writeValueAsString(responseMap);
		        } catch (Exception e) {
		            e.printStackTrace(); 
		        }       
		        return jsonResponse;
	}
	@PostMapping(value="/getpayrollDetails")
	public @ResponseBody String getpayrollDetails(HttpServletRequest request, ModelMap model,Locale locale,HttpSession session,EmployeeProfileRequest employeeProfileRequest) {
		String companyResponse = null;
        JSONObject companyJsonResponse = null;
        Map<String, Object> responseMap = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();
        String jsonResponse = null;	
		//String token = (String) session.getAttribute("hrms");
		companyResponse= companyService.getpayrollDetails(tokengeneration.getToken(),employeeProfileRequest);
		 companyJsonResponse = new JSONObject(companyResponse);      
		 if(companyJsonResponse.getBoolean("status")) { 
			 	responseMap.put("status",true);
				
				JSONObject dataObject = companyJsonResponse.getJSONObject("data");
				//List<Object> companyList = companyJsonResponse.getJSONArray("data").toList();
				responseMap.put("data", dataObject.toMap());
				//responseMap.put("data", companyList);
	        }else {
				//loginsevice.rsendEmailVerificationCompletion(userForm);
				responseMap.put("status", false);
			}
	        try {
	            jsonResponse = mapper.writeValueAsString(responseMap);
	        } catch (Exception e) {
	            e.printStackTrace(); 
	        }       
	        return jsonResponse;
	}

	@PostMapping(value="/getGSTDetailsByGSTNumber")
	public @ResponseBody String getGSTDetailsByGSTNumber(HttpServletRequest request, ModelMap model,Locale locale,HttpSession session,EmployeeProfileRequest employeeProfileRequest) {
		String companyResponse = null;
        JSONObject companyJsonResponse = null;
        Map<String, Object> responseMap = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();
        String jsonResponse = null;	
		//String token = (String) session.getAttribute("hrms");
		companyResponse= companyService.getGSTDetailsByGSTNumber(tokengeneration.getToken(),employeeProfileRequest);
		 companyJsonResponse = new JSONObject(companyResponse);      
		 if(companyJsonResponse.getBoolean("status")) { 
			 	responseMap.put("status",true);
				
				JSONObject dataObject = companyJsonResponse.getJSONObject("data");
				//List<Object> companyList = companyJsonResponse.getJSONArray("data").toList();
				responseMap.put("data", dataObject.toMap());
				//responseMap.put("data", companyList);
	        }else {
				//loginsevice.rsendEmailVerificationCompletion(userForm);
				responseMap.put("status", false);
			}
	        try {
	            jsonResponse = mapper.writeValueAsString(responseMap);
	        } catch (Exception e) {
	            e.printStackTrace(); 
	        }       
	        return jsonResponse;
	}

}
