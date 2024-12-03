package com.cotodel.hrms.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.cotodel.hrms.web.response.BankMaster;
import com.cotodel.hrms.web.response.VoucherTypeMaster;
import com.cotodel.hrms.web.service.VoucherTypeMasterService;
import com.cotodel.hrms.web.service.Impl.TokenGenerationImpl;
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
	
	@PostMapping(value="/savevoucherTypeMaster")
	public @ResponseBody String saveBankMaster(ModelMap model, Locale locale, HttpSession session,VoucherTypeMaster voucherTypeMaster) {
        String voucherResponse = null;
        JSONObject voucherJsonResponse = null;
        Map<String, String> responseMap = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();
        String jsonResponse = null;

        // Call the service to save the BankMaster object
        voucherResponse = voucherTypeMasterService.saveVoucherTypeMaster(tokengeneration.getToken(), voucherTypeMaster);
        System.out.println(voucherResponse);  // Logging the response
        voucherJsonResponse = new JSONObject(voucherResponse);

       
		if(voucherJsonResponse.getBoolean("status")) { 
			responseMap.put("status", MessageConstant.RESPONSE_SUCCESS);
		}else {
			//loginsevice.rsendEmailVerificationCompletion(userForm);
			responseMap.put("status", MessageConstant.RESPONSE_FAILED);
		}
        
        try {
            jsonResponse = mapper.writeValueAsString(responseMap);
        } catch (Exception e) {
            e.printStackTrace(); 
        }

        
        return jsonResponse;  
    }
	@GetMapping(value="/getvoucherTypeMaster")
	public @ResponseBody String getVoucherTypeMasterList(ModelMap model, Locale locale, HttpSession session,VoucherTypeMaster voucherTypeMaster) {
        String voucherResponse = null;
        JSONObject voucherJsonResponse = null;
        Map<String, Object> responseMap = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();
        String jsonResponse = null;
        // Call the service to save the voucherResponse object
        voucherResponse = voucherTypeMasterService.getVoucherTypeMasterList(tokengeneration.getToken(), voucherTypeMaster);
        System.out.println(voucherResponse);  // Logging the response
        voucherJsonResponse = new JSONObject(voucherResponse);      
        if(voucherJsonResponse.getBoolean("status")) { 
			
			//responseMap.put("data", voucherJsonResponse.getJSONArray("data"));
			List<Object> voucherList = voucherJsonResponse.getJSONArray("data").toList();
			responseMap.put("status",true);
			responseMap.put("data", voucherList);
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
	

	
	@PostMapping(value="/toggleVoucherStatus")
	public @ResponseBody String updateVoucherTypeMasterStatus(ModelMap model, Locale locale, 
			HttpSession session, VoucherTypeMaster voucherTypeMaster) throws JsonProcessingException {

		String voucherResponse = null;
        JSONObject voucherJsonResponse = null;
        Map<String, String> responseMap = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();
        String jsonResponse = null;

        // Call the service to save the BankMaster object
        voucherResponse = voucherTypeMasterService.updatevoucherTypeMasterStatus(tokengeneration.getToken(), voucherTypeMaster);
        System.out.println(voucherResponse);  // Logging the response
        
        voucherJsonResponse = new JSONObject(voucherResponse);

        

		if(voucherJsonResponse.getBoolean("status")) { 
			responseMap.put("status","SUCCESS");
		}else {
			//loginsevice.rsendEmailVerificationCompletion(userForm);
			responseMap.put("status","FAILURE");
		}
 
		return new ObjectMapper().writeValueAsString(responseMap);
		}


}
