package com.cotodel.hrms.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.cotodel.hrms.web.properties.ApplicationConstantConfig;
import com.cotodel.hrms.web.response.ErupiVoucherCreateRequest;
import com.cotodel.hrms.web.response.VoucherTypeMaster;
import com.cotodel.hrms.web.service.VoucherTypeMasterService;
import com.cotodel.hrms.web.service.Impl.TokenGenerationImpl;
import com.cotodel.hrms.web.util.EncriptResponse;
import com.cotodel.hrms.web.util.EncryptionDecriptionUtil;
import com.cotodel.hrms.web.util.MessageConstant;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@CrossOrigin
public class VoucherTypeMasterController extends CotoDelBaseController{
	
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
        	String json = EncryptionDecriptionUtil.convertToJson(erupiVoucherCreateRequest); 
            EncriptResponse encryptedRequest = EncryptionDecriptionUtil.encriptResponse(json, applicationConstantConfig.apiSignaturePublicPath);
            
            String encryptedResponse = voucherTypeMasterService.createVoucher(tokengeneration.getToken(), encryptedRequest);
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

}
