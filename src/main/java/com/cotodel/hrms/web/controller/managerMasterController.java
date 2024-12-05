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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


import com.cotodel.hrms.web.response.managerMaster;
import com.cotodel.hrms.web.service.managerMasterService;
import com.cotodel.hrms.web.service.Impl.TokenGenerationImpl;
import com.cotodel.hrms.web.util.MessageConstant;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@CrossOrigin
public class managerMasterController {

	@Autowired
	TokenGenerationImpl tokengeneration;
	
	@Autowired
	managerMasterService managermasterService;
	
	@PostMapping(value="/savemanagerMaster")// saving details form manager master form
	public @ResponseBody String savemanagerMaster(ModelMap model, Locale locale, HttpSession session,managerMaster managermaster) {
        String managerResponse = null;
        JSONObject managerJsonResponse = null;
        Map<String, String> responseMap = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();
        String jsonResponse = null;

        // Call the service to save the BankMaster object
        managerResponse = managermasterService.savemanagerMaster(tokengeneration.getToken(), managermaster);
        System.out.println(managerResponse);  // Logging the response
        managerJsonResponse = new JSONObject(managerResponse);

       
		if(managerJsonResponse.getBoolean("status")) { 
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
	
	@PostMapping(value="/getmanagerMasterWithId")// saving details form manager master form
	public @ResponseBody String getmanagerMasterWithId(ModelMap model, Locale locale, HttpSession session,managerMaster managermaster) {
        String managerResponse = null;
        JSONObject managerJsonResponse = null;
        Map<String, Object> responseMap = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();
        String jsonResponse = null;

        // Call the service to save the BankMaster object
        managerResponse = managermasterService.getmanagerMasterWithId(tokengeneration.getToken(), managermaster);
        System.out.println(managerResponse);  // Logging the response
        managerJsonResponse = new JSONObject(managerResponse);

       
		if(managerJsonResponse.getBoolean("status")) { 
			
			//responseMap.put("data", bankJsonResponse.getJSONArray("data"));
			List<Object> managerList =  managerJsonResponse .getJSONArray("data").toList();
			responseMap.put("status",true);
			responseMap.put("data", managerList);
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
