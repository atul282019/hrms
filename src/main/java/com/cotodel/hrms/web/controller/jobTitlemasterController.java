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

import com.cotodel.hrms.web.response.BankMaster;
import com.cotodel.hrms.web.response.jobTitlemaster;
import com.cotodel.hrms.web.service.jobTitlemasterService;
import com.cotodel.hrms.web.service.Impl.TokenGenerationImpl;
import com.cotodel.hrms.web.util.MessageConstant;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@CrossOrigin
public class jobTitlemasterController {
	@Autowired
	TokenGenerationImpl tokengeneration;
	
	@Autowired
	jobTitlemasterService jobTitlemasterservice;
	
	@PostMapping(value="/savejobTitlemaster")// saving details form job master form
	public @ResponseBody String savejobTitlemaster(ModelMap model, Locale locale, HttpSession session,jobTitlemaster jobTitlemaster) {
        String jobTitleResponse = null;
        JSONObject jobTitleJsonResponse = null;
        Map<String, String> responseMap = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();
        String jsonResponse = null;

        // Call the service to save the BankMaster object
        jobTitleResponse = jobTitlemasterservice.savejobTitlemaster(tokengeneration.getToken(), jobTitlemaster);
        System.out.println(jobTitleResponse);  // Logging the response
        jobTitleJsonResponse = new JSONObject(jobTitleResponse);

       
		if(jobTitleJsonResponse.getBoolean("status")) { 
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
	
	@PostMapping(value="/getjobMasterWithId")// for displaying data with the help job job id
	public @ResponseBody String getjobTitlemaster(ModelMap model, Locale locale, HttpSession session,jobTitlemaster jobTitlemaster) {
        String jobTitleResponse = null;
        JSONObject jobTitleJsonResponse = null;
        Map<String, Object> responseMap = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();
        String jsonResponse = null;
        // Call the service to save the BankMaster object
        jobTitleResponse = jobTitlemasterservice.getjobTitlemaster(tokengeneration.getToken(),jobTitlemaster);
        System.out.println(jobTitleResponse);  // Logging the response
        jobTitleJsonResponse = new JSONObject(jobTitleResponse);      
        if(jobTitleJsonResponse.getBoolean("status")) { 
			
			//responseMap.put("data", jobTitleJsonResponse.getJSONArray("data"));
			List<Object> bankList = jobTitleJsonResponse.getJSONArray("data").toList();
			responseMap.put("status",true);
			responseMap.put("data", bankList);
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