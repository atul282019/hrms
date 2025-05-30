package com.cotodel.hrms.web.controller;


import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Locale;

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
import com.cotodel.hrms.web.response.VehicleManagementRequest;
import com.cotodel.hrms.web.response.VehicleManagementSaveRequest;
import com.cotodel.hrms.web.service.VehicleManagementService;
import com.cotodel.hrms.web.service.Impl.TokenGenerationImpl;
import com.cotodel.hrms.web.util.EncriptResponse;
import com.cotodel.hrms.web.util.EncryptionDecriptionUtil;
import com.cotodel.hrms.web.util.MessageConstant;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@CrossOrigin
public class VehicleManagementController extends CotoDelBaseController {

	private static final Logger logger = LoggerFactory.getLogger(VehicleManagementController.class);

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
	
	@PostMapping(value="/saveBulkVehicleDetail")
	public String saveBulkVehicleDetail(HttpServletResponse response, HttpServletRequest request,
			@ModelAttribute("formData") BulkEmployeeRequest bulkEmployeeRequest, BindingResult result, HttpSession session, Model model,RedirectAttributes redirect) {
		
		String profileRes=null;JSONObject profileJsonRes=null;
		HashMap<String, String> otpMap = new  HashMap<String, String> ();
		ObjectMapper mapper = new ObjectMapper();
		String res = null; 
		int orgid=(int)request.getSession(true).getAttribute("id");
		Long emplrid=(long)orgid;
		bulkEmployeeRequest.setEmployerId(emplrid);
		profileRes = vehicleManagementService.saveBulkVehicleDetail(tokengeneration.getToken(),bulkEmployeeRequest);
		profileJsonRes= new JSONObject(profileRes);
		if(profileJsonRes.getString("status").equalsIgnoreCase("SUCCESS")) { 
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
		  session.setAttribute("list",profileRes); logger.info(profileRes);
		  model.addAttribute("list",profileRes); 
		  return "bulk-table-invitelist";
		  
	}
	

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

	
}
