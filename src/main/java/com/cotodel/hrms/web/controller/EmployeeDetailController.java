package com.cotodel.hrms.web.controller;

import java.util.HashMap;
import java.util.Locale;

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
import com.cotodel.hrms.web.response.EmployeeCertificateRequest;
import com.cotodel.hrms.web.response.EmployeeDetailsRequest;
import com.cotodel.hrms.web.response.EmployeeExperienceRequest;
import com.cotodel.hrms.web.response.EmployeeFamilyDetailRequest;
import com.cotodel.hrms.web.response.EmployeeOnboarding;
import com.cotodel.hrms.web.response.EmployeeProjectRequest;
import com.cotodel.hrms.web.response.EmployeeQualificationRequest;
import com.cotodel.hrms.web.service.EmployeeDetailService;
import com.cotodel.hrms.web.service.Impl.TokenGenerationImpl;
import com.cotodel.hrms.web.util.MessageConstant;
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
	
	
	@PostMapping(value="/saveEmployeeDetail")
	public @ResponseBody String saveEmployeeDetail(HttpServletRequest request, ModelMap model,Locale locale,HttpSession session,EmployeeDetailsRequest employeeDetailRequest) {
		String profileRes=null;JSONObject profileJsonRes=null;
		HashMap<String, String> otpMap = new  HashMap<String, String> ();
		ObjectMapper mapper = new ObjectMapper();
		String res=null;String userRes=null;
		
		
		profileRes = employeeDetailService.saveEmployeeDetail(tokengeneration.getToken(),employeeDetailRequest);
		logger.info(profileRes);
		profileJsonRes= new JSONObject(profileRes);
		
		if(profileJsonRes.getBoolean("status")) { 
			otpMap.put("status", MessageConstant.RESPONSE_SUCCESS);
		}else {
			//loginsevice.rsendEmailVerificationCompletion(userForm);
			otpMap.put("status", MessageConstant.RESPONSE_FAILED);
		}
		try {
			res = mapper.writeValueAsString(otpMap);
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return profileRes;
	}

	@PostMapping(value="/saveFamilyDetail")
	public @ResponseBody String saveFamilyDetail(HttpServletRequest request, ModelMap model,Locale locale,HttpSession session,EmployeeFamilyDetailRequest employeeFamilyDetailRequest) {
		String profileRes=null;JSONObject profileJsonRes=null;
		HashMap<String, String> otpMap = new  HashMap<String, String> ();
		ObjectMapper mapper = new ObjectMapper();
		String res=null;String userRes=null;
		profileRes = employeeDetailService.saveFamilyDetail(tokengeneration.getToken(),employeeFamilyDetailRequest);
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
	
	@PostMapping(value="/saveQualificationDetail")
	public @ResponseBody String saveFamilyQualification(HttpServletRequest request, ModelMap model,Locale locale,HttpSession session,EmployeeQualificationRequest employeeQualificationRequest) {
		String profileRes=null;JSONObject profileJsonRes=null;
		HashMap<String, String> otpMap = new  HashMap<String, String> ();
		ObjectMapper mapper = new ObjectMapper();
		String res=null;String userRes=null;
		profileRes = employeeDetailService.saveFamilyQualification(tokengeneration.getToken(),employeeQualificationRequest);
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
	
	@PostMapping(value="/saveEmployeeExperience")
	public @ResponseBody String saveEmpCertificateDetail(HttpServletRequest request, ModelMap model,Locale locale,HttpSession session,EmployeeExperienceRequest employeeExperienceRequest) {
		String profileRes=null;JSONObject profileJsonRes=null;
		HashMap<String, String> otpMap = new  HashMap<String, String> ();
		ObjectMapper mapper = new ObjectMapper();
		String res=null;String userRes=null;
		profileRes = employeeDetailService.saveEmpCertificateDetail(tokengeneration.getToken(),employeeExperienceRequest);
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
	
	@PostMapping(value="/saveEmployeeCertificate")
	public @ResponseBody String saveEmployeeCertificate(HttpServletRequest request, ModelMap model,Locale locale,HttpSession session,EmployeeCertificateRequest employeeCertificateRequest) {
		String profileRes=null;JSONObject profileJsonRes=null;
		HashMap<String, String> otpMap = new  HashMap<String, String> ();
		ObjectMapper mapper = new ObjectMapper();
		String res=null;String userRes=null;
		profileRes = employeeDetailService.saveEmployeeCertificate(tokengeneration.getToken(),employeeCertificateRequest);
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
	
	@PostMapping(value="/saveEmployeeProject")
	public @ResponseBody String saveEmployeeProject(HttpServletRequest request, ModelMap model,Locale locale,HttpSession session,EmployeeProjectRequest employeeProjectRequest) {
		String profileRes=null;JSONObject profileJsonRes=null;
		HashMap<String, String> otpMap = new  HashMap<String, String> ();
		ObjectMapper mapper = new ObjectMapper();
		String res=null;String userRes=null;
		profileRes = employeeDetailService.saveEmployeeProject(tokengeneration.getToken(),employeeProjectRequest);
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
	
	
	@GetMapping(value="/getEmployeeDetail")
	public @ResponseBody String getEmployeeDetail(HttpServletRequest request, ModelMap model,Locale locale,HttpSession session,EmployeeDetailsRequest employeeFamilyDetailRequest) {
		String profileRes=null;JSONObject profileJsonRes=null;
		HashMap<String, String> otpMap = new  HashMap<String, String> ();
		ObjectMapper mapper = new ObjectMapper();
		String res=null;String userRes=null;
		profileRes = employeeDetailService.getEmployeeDetail(tokengeneration.getToken(),employeeFamilyDetailRequest);
		System.out.println("profileRes::"+profileRes);
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
	
	@GetMapping(value="/getEmployeeFamilyDetail")
	public @ResponseBody String getEmployeeFamilyDetail(HttpServletRequest request, ModelMap model,Locale locale,HttpSession session,EmployeeFamilyDetailRequest employeeFamilyDetailRequest) {
		String profileRes=null;JSONObject profileJsonRes=null;
		HashMap<String, String> otpMap = new  HashMap<String, String> ();
		ObjectMapper mapper = new ObjectMapper();
		String res=null;String userRes=null;
		profileRes = employeeDetailService.getEmployeeFamilyDetail(tokengeneration.getToken(),employeeFamilyDetailRequest);
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
	
	@GetMapping(value="/getEmployeeQualificationDetail")
	public @ResponseBody String getEmployeeQualificationDetail(HttpServletRequest request, ModelMap model,Locale locale,HttpSession session,EmployeeQualificationRequest employeeQualificationRequest) {
		String profileRes=null;JSONObject profileJsonRes=null;
		HashMap<String, String> otpMap = new  HashMap<String, String> ();
		ObjectMapper mapper = new ObjectMapper();
		String res=null;String userRes=null;
		profileRes = employeeDetailService.getEmployeeQualificationDetail(tokengeneration.getToken(),employeeQualificationRequest);
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

	@GetMapping(value="/getEmployeeExperienceDetail")
	public @ResponseBody String getEmployeeExperience(HttpServletRequest request, ModelMap model,Locale locale,HttpSession session,EmployeeExperienceRequest employeeExperienceRequest) {
		String profileRes=null;JSONObject profileJsonRes=null;
		HashMap<String, String> otpMap = new  HashMap<String, String> ();
		ObjectMapper mapper = new ObjectMapper();
		String res=null;String userRes=null;
		profileRes = employeeDetailService.getEmployeeExperience(tokengeneration.getToken(),employeeExperienceRequest);
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
	
	@GetMapping(value="/getEmployeeCertificateDetail")
	public @ResponseBody String getEmployeeCertificateDetail(HttpServletRequest request, ModelMap model,Locale locale,HttpSession session,EmployeeCertificateRequest employeeCertificateRequest) {
		String profileRes=null;JSONObject profileJsonRes=null;
		HashMap<String, String> otpMap = new  HashMap<String, String> ();
		ObjectMapper mapper = new ObjectMapper();
		String res=null;String userRes=null;
		profileRes = employeeDetailService.getEmployeeCertificateDetail(tokengeneration.getToken(),employeeCertificateRequest);
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
	
	@GetMapping(value="/getEmployeeProjectDetail")
	public @ResponseBody String getEmployeeProjectDetail(HttpServletRequest request, ModelMap model,Locale locale,HttpSession session,EmployeeProjectRequest employeeProjectRequest) {
		String profileRes=null;JSONObject profileJsonRes=null;
		HashMap<String, String> otpMap = new  HashMap<String, String> ();
		ObjectMapper mapper = new ObjectMapper();
		String res=null;String userRes=null;
		profileRes = employeeDetailService.getEmployeeProjectDetail(tokengeneration.getToken(),employeeProjectRequest);
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
	
	@PostMapping(value="/employeeOnboarding")
	public @ResponseBody String saveEmployeeOnboarding(HttpServletRequest request, ModelMap model,Locale locale,HttpSession session,EmployeeOnboarding employeeOnboarding) {
		String profileRes=null;JSONObject profileJsonRes=null;
		HashMap<String, String> otpMap = new  HashMap<String, String> ();
		ObjectMapper mapper = new ObjectMapper();
		String res=null;String userRes=null;
		
		profileRes = employeeDetailService.saveEmployeeOnboarding(tokengeneration.getToken(),employeeOnboarding);
		logger.info(profileRes);
		profileJsonRes= new JSONObject(profileRes);
		
		if(profileJsonRes.getBoolean("status")) { 
			otpMap.put("status", MessageConstant.RESPONSE_SUCCESS);
		}else {
			//loginsevice.rsendEmailVerificationCompletion(userForm);
			otpMap.put("status", MessageConstant.RESPONSE_FAILED);
		}
		try {
			res = mapper.writeValueAsString(otpMap);
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return profileRes;
	}
	
	@GetMapping(value="/getEmployeeOnboarding")
	public @ResponseBody String getEmployeeOnboardingSuccessList(HttpServletRequest request, ModelMap model,Locale locale,HttpSession session,EmployeeOnboarding employeeOnboarding) {
		String profileRes=null;JSONObject profileJsonRes=null;
		HashMap<String, String> otpMap = new  HashMap<String, String> ();
		ObjectMapper mapper = new ObjectMapper();
		String res = null; String userRes = null;
		profileRes = employeeDetailService.getEmployeeOnboarding(tokengeneration.getToken(),employeeOnboarding);
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
	
	@GetMapping(value="/getEmployeeOnboardingFailList")
	public @ResponseBody String getEmployeeOnboardingFailList(HttpServletRequest request, ModelMap model,Locale locale,HttpSession session,EmployeeOnboarding employeeOnboarding) {
		String profileRes=null;JSONObject profileJsonRes=null;
		HashMap<String, String> otpMap = new  HashMap<String, String> ();
		ObjectMapper mapper = new ObjectMapper();
		String res = null; String userRes = null;
		profileRes = employeeDetailService.getEmployeeOnboardingFailList(tokengeneration.getToken(),employeeOnboarding);
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
	
	@GetMapping(value="/getEmployeeOnboardingById")
	public @ResponseBody String getEmployeeOnboardingById(HttpServletRequest request, ModelMap model,Locale locale,HttpSession session,EmployeeOnboarding employeeOnboarding) {
		String profileRes=null;JSONObject profileJsonRes=null;
		HashMap<String, String> otpMap = new  HashMap<String, String> ();
		ObjectMapper mapper = new ObjectMapper();
		String res = null; String userRes = null;
		profileRes = employeeDetailService.getEmployeeOnboardingById(tokengeneration.getToken(),employeeOnboarding);
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
	
	@PostMapping(value="/confirmBulkEmplOnboarding")
	public String confirmBulkEmplOnboarding( @RequestBody BulkConfirmationRequest[] bulkConfirmationRequest , HttpSession session) {
		String profileRes=null;JSONObject profileJsonRes=null;
		HashMap<String, String> otpMap = new  HashMap<String, String> ();
		ObjectMapper mapper = new ObjectMapper();
		/*
		 * BulkConfirmationRequest[] jsonObj = null; try { jsonObj =
		 * mapper.readValue(employeeOnboarding,BulkConfirmationRequest[].class); } catch
		 * (JsonProcessingException e1) { // TODO Auto-generated catch block
		 * e1.printStackTrace(); }
		 */
		String res = null; String userRes = null;
		profileRes = employeeDetailService.confirmBulkEmplOnboarding(tokengeneration.getToken(),bulkConfirmationRequest);
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
	
	@PostMapping(value="/saveEmployeeProfile")
	public @ResponseBody String saveEmployeeProfile(HttpServletRequest request, ModelMap model,Locale locale,HttpSession session,EmployeeOnboarding employeeOnboarding) {
		String profileRes=null;JSONObject profileJsonRes=null;
		HashMap<String, String> otpMap = new  HashMap<String, String> ();
		ObjectMapper mapper = new ObjectMapper();
		String res=null;String userRes=null;
		
		profileRes = employeeDetailService.saveEmployeeProfile(tokengeneration.getToken(),employeeOnboarding);
		logger.info(profileRes);
		profileJsonRes= new JSONObject(profileRes);
		
		if(profileJsonRes.getBoolean("status")) { 
			otpMap.put("status", MessageConstant.RESPONSE_SUCCESS);
		}else {
			//loginsevice.rsendEmailVerificationCompletion(userForm);
			otpMap.put("status", MessageConstant.RESPONSE_FAILED);
		}
		try {
			res = mapper.writeValueAsString(otpMap);
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return profileRes;
	}
	
}
