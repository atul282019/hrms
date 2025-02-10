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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cotodel.hrms.web.properties.ApplicationConstantConfig;
import com.cotodel.hrms.web.response.BulkEmployeeRequest;
import com.cotodel.hrms.web.response.UserForm;
import com.cotodel.hrms.web.service.BulkEmployeeService;
import com.cotodel.hrms.web.service.Impl.TokenGenerationImpl;
import com.cotodel.hrms.web.util.MessageConstant;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@CrossOrigin
public class BulkUserController extends CotoDelBaseController{

	private static final Logger logger = LoggerFactory.getLogger(BulkUserController.class);

	@Autowired
	public ApplicationConstantConfig applicationConstantConfig;
	
	@Autowired
	BulkEmployeeService bulkEmployeeService;

	@Autowired
	TokenGenerationImpl tokengeneration;
	
	@PostMapping(value="/saveBulkFile")
	public String saveEmployeeDetail(HttpServletResponse response, HttpServletRequest request,
			@ModelAttribute("formData") BulkEmployeeRequest bulkEmployeeRequest, BindingResult result, HttpSession session, Model model,RedirectAttributes redirect) {
		
		String profileRes=null;JSONObject profileJsonRes=null;
		HashMap<String, String> otpMap = new  HashMap<String, String> ();
		ObjectMapper mapper = new ObjectMapper();
		String res = null; String userRes = null;
		int orgid=(int)request.getSession(true).getAttribute("id");
		Long emplrid=(long)orgid;
		bulkEmployeeRequest.setEmployerId(emplrid);
		profileRes = bulkEmployeeService.saveBulkDetail(tokengeneration.getToken(),bulkEmployeeRequest);
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
	@GetMapping(value = "/bulkUserTemplate")
	public ResponseEntity<InputStreamResource> bulkUSer() {
		try {
			//String filePath ="D:\\opt\\file\\";
			String filePath ="/opt/cotodel/key/";
			String fileName = "BulkUserTemplate.xlsx";
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
