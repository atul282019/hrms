package com.cotodel.hrms.web.controller;
import java.util.List;
import java.util.HashMap;
import org.apache.naming.java.javaURLContextFactory;

import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.cotodel.hrms.web.properties.ApplicationConstantConfig;
import com.cotodel.hrms.web.response.BankMaster;
import com.cotodel.hrms.web.response.VoucherTypeMaster;
import com.cotodel.hrms.web.service.BankMasterService;
import com.cotodel.hrms.web.service.Impl.TokenGenerationImpl;
import com.cotodel.hrms.web.util.EncriptResponse;
import com.cotodel.hrms.web.util.EncryptionDecriptionUtil;
import com.cotodel.hrms.web.util.MessageConstant;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


//import com.nimbusds.jose.shaded.json.JSONObject;
import org.json.JSONObject;

@RestController
@CrossOrigin
public class BankMasterController extends CotoDelBaseController{
	
	@Autowired
	TokenGenerationImpl tokengeneration;
	
	@Autowired
	 BankMasterService bankMasterService;
	
	@Autowired
	public ApplicationConstantConfig applicationConstantConfig;
	
	
	@PostMapping(value="/savebankMaster")// saving details form bank master form
	public @ResponseBody String saveBankMaster(ModelMap model, Locale locale, HttpSession session,BankMaster bankMaster) {
        String bankResponse = null;
        JSONObject bankJsonResponse = null;
        Map<String, String> responseMap = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();
        String jsonResponse = null;

        // Call the service to save the BankMaster object
//        bankResponse = bankMasterService.saveBankMaster(tokengeneration.getToken(), bankMaster);
//        System.out.println(bankResponse);  // Logging the response
//        bankJsonResponse = new JSONObject(bankResponse);

       
//		if(bankJsonResponse.getBoolean("status")) { 
//			responseMap.put("status", MessageConstant.RESPONSE_SUCCESS);
//		}else {
//			//loginsevice.rsendEmailVerificationCompletion(userForm);
//			responseMap.put("status", MessageConstant.RESPONSE_FAILED);
//		}
        
        try {
            //jsonResponse = mapper.writeValueAsString(responseMap);
        	String json = EncryptionDecriptionUtil.convertToJson(bankMaster);

			EncriptResponse jsonObject=EncryptionDecriptionUtil.encriptResponse(json, applicationConstantConfig.apiSignaturePublicPath);

			String encriptResponse =  bankMasterService.saveBankMaster(tokengeneration.getToken(), jsonObject);

   
			EncriptResponse userReqEnc =EncryptionDecriptionUtil.convertFromJson(encriptResponse, EncriptResponse.class);

			jsonResponse =  EncryptionDecriptionUtil.decriptResponse(userReqEnc.getEncriptData(), userReqEnc.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
		
       
        } catch (Exception e) {
            e.printStackTrace(); 
        }

        
        return jsonResponse;  
    }
	@GetMapping(value="/getbankmaster")// for displaying bank name and bank code in bank master form 
	public @ResponseBody String getBankMasterList(ModelMap model, Locale locale, HttpSession session,BankMaster bankMaster) {
        String bankResponse = null;
        JSONObject bankJsonResponse = null;
        Map<String, Object> responseMap = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();
        String jsonResponse = null;
        // Call the service to save the BankMaster object
//        bankResponse = bankMasterService.getBankMasterList(tokengeneration.getToken(), bankMaster);
//        System.out.println(bankResponse);  // Logging the response
//        bankJsonResponse = new JSONObject(bankResponse);      
//        if(bankJsonResponse.getBoolean("status")) { 
//			
//			//responseMap.put("data", bankJsonResponse.getJSONArray("data"));
//			List<Object> bankList = bankJsonResponse.getJSONArray("data").toList();
//			responseMap.put("status",true);
//			responseMap.put("data", bankList);
//        }else {
//			//loginsevice.rsendEmailVerificationCompletion(userForm);
//			responseMap.put("status", false);
//		}
        try {
            //jsonResponse = mapper.writeValueAsString(responseMap);

            String json = EncryptionDecriptionUtil.convertToJson(bankMaster);
            EncriptResponse encryptedRequest = EncryptionDecriptionUtil.encriptResponse(json, applicationConstantConfig.apiSignaturePublicPath);
            
             String encryptedResponse = bankMasterService.getBankMasterList(tokengeneration.getToken(), encryptedRequest);
            EncriptResponse responseObject = EncryptionDecriptionUtil.convertFromJson(encryptedResponse, EncriptResponse.class);
            bankResponse = EncryptionDecriptionUtil.decriptResponse(responseObject.getEncriptData(), responseObject.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
            
            bankJsonResponse = new JSONObject(bankResponse);
            if (bankJsonResponse.getBoolean("status")) {
                List<Object> bankList = bankJsonResponse.getJSONArray("data").toList();
                responseMap.put("status", true);
                responseMap.put("data", bankList);
            } else {
                responseMap.put("status", false);
            }
            
            jsonResponse = mapper.writeValueAsString(responseMap);
        } catch (Exception e) {
            e.printStackTrace(); 
        }       
        return jsonResponse;
    }
	@PostMapping(value="/getaftersaveBankMasterDetailsList")// for displaying the whole data in table
	public @ResponseBody String getaftersaveBankMasterDetailsList(ModelMap model, Locale locale, HttpSession session,BankMaster bankMaster){
        String bankResponse = null;
        JSONObject bankJsonResponse = null;
        Map<String, Object> responseMap = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();
        String jsonResponse = null;

        // Call the service to save the BankMaster object
//        bankResponse = bankMasterService.getaftersaveBankMasterDetailsList(tokengeneration.getToken(), bankMaster);
//        System.out.println(bankResponse);  // Logging the response
//        bankJsonResponse = new JSONObject(bankResponse);
//
//       
//
//		if(bankJsonResponse.getBoolean("status")) { 
//			
//			List<Object> bankList = bankJsonResponse.getJSONArray("data").toList();
//			responseMap.put("status", MessageConstant.RESPONSE_SUCCESS);
//			responseMap.put("data", bankList);
//		}else {
//			//loginsevice.rsendEmailVerificationCompletion(userForm);
//			responseMap.put("status", MessageConstant.RESPONSE_FAILED);
//		}
        
        try {
            //jsonResponse = mapper.writeValueAsString(responseMap);
        	 String json = EncryptionDecriptionUtil.convertToJson(bankMaster);
             EncriptResponse encryptedRequest = EncryptionDecriptionUtil.encriptResponse(json, applicationConstantConfig.apiSignaturePublicPath);
             
             String encryptedResponse = bankMasterService.getaftersaveBankMasterDetailsList(tokengeneration.getToken(), encryptedRequest);
             EncriptResponse responseObject = EncryptionDecriptionUtil.convertFromJson(encryptedResponse, EncriptResponse.class);
             bankResponse = EncryptionDecriptionUtil.decriptResponse(responseObject.getEncriptData(), responseObject.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
             
             bankJsonResponse = new JSONObject(bankResponse);
             if (bankJsonResponse.getBoolean("status")) {
                 List<Object> bankList = bankJsonResponse.getJSONArray("data").toList();
                 responseMap.put("status", MessageConstant.RESPONSE_SUCCESS);
                 responseMap.put("data", bankList);
             } else {
                 responseMap.put("status", MessageConstant.RESPONSE_FAILED);
             }
             
             jsonResponse = mapper.writeValueAsString(responseMap);
         
        } catch (Exception e) {
            e.printStackTrace(); 
        }

        
        return jsonResponse;  
    }
	
	@PostMapping(value="/togglebankmasterStatus")
	public @ResponseBody String updatebankMasterDetailStatus(ModelMap model, Locale locale, 
			HttpSession session, BankMaster bankMaster) throws JsonProcessingException {

		String bankResponse = null;
        JSONObject bankJsonResponse = null;
        Map<String, Boolean> responseMap = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();
        String jsonResponse = null;

        // Call the service to save the BankMaster object
//        bankResponse= bankMasterService.updatebankMasterDetailStatus(tokengeneration.getToken(),bankMaster);
//       
//        
//        bankJsonResponse = new JSONObject(bankResponse);
//
//        
//
//		if(bankJsonResponse.getBoolean("status")) { 
//			responseMap.put("status",true);
//		}else {
//			//loginsevice.rsendEmailVerificationCompletion(userForm);
//			responseMap.put("status",false);
//		}
//        
//		return new ObjectMapper().writeValueAsString(responseMap);
        
        
        try {
            String json = EncryptionDecriptionUtil.convertToJson(bankMaster);
            EncriptResponse encryptedRequest = EncryptionDecriptionUtil.encriptResponse(json, applicationConstantConfig.apiSignaturePublicPath);
            
            String encryptedResponse = bankMasterService.updatebankMasterDetailStatus(tokengeneration.getToken(), encryptedRequest);
            EncriptResponse responseObject = EncryptionDecriptionUtil.convertFromJson(encryptedResponse, EncriptResponse.class);
            bankResponse = EncryptionDecriptionUtil.decriptResponse(responseObject.getEncriptData(), responseObject.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
            
            bankJsonResponse = new JSONObject(bankResponse);
            if (bankJsonResponse.getBoolean("status")) {
                responseMap.put("status", true);
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
