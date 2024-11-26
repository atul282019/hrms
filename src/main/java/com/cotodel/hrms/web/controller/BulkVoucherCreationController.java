package com.cotodel.hrms.web.controller;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;

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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cotodel.hrms.web.properties.ApplicationConstantConfig;
import com.cotodel.hrms.web.response.BulkVoucherRequest;
import com.cotodel.hrms.web.service.BulkVoucherService;
import com.cotodel.hrms.web.service.Impl.TokenGenerationImpl;
import com.cotodel.hrms.web.util.MessageConstant;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@CrossOrigin
public class BulkVoucherCreationController extends CotoDelBaseController{

	private static final Logger logger = LoggerFactory.getLogger(BulkVoucherCreationController.class);

	@Autowired
	public ApplicationConstantConfig applicationConstantConfig;
	
	@Autowired
	BulkVoucherService bulkVoucherService;

	@Autowired
	TokenGenerationImpl tokengeneration;
	
	@PostMapping(value="/saveBulkVoucher")
	public @ResponseBody  String saveBulkVoucher(HttpServletResponse response, HttpServletRequest request,
			BulkVoucherRequest bulkVoucherRequest, BindingResult result, HttpSession session, Model model,RedirectAttributes redirect) {
		
		String profileRes=null;
		profileRes = bulkVoucherService.saveBulkVoucher(tokengeneration.getToken(),bulkVoucherRequest);
		
		  return profileRes;
		  
	}

	@GetMapping(value = "/getVoucherTemplate")
	public ResponseEntity<InputStreamResource> getVoucherTemplate() {
		try {
			String filePath ="src/main/resources/file/";
			String fileName = "Bulk_Voucher_Templates.xlsx";
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
