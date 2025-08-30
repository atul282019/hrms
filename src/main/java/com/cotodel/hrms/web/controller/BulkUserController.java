package com.cotodel.hrms.web.controller;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cotodel.hrms.web.properties.ApplicationConstantConfig;
import com.cotodel.hrms.web.response.BulkEmployeeRequest;
import com.cotodel.hrms.web.response.BulkEmployeeResponse;
import com.cotodel.hrms.web.response.EmployeeBulkCreateRequest;
import com.cotodel.hrms.web.response.EmployeeBulkUploadRequest;
import com.cotodel.hrms.web.response.SMSRequest;
import com.cotodel.hrms.web.response.UserDetailsEntity;
import com.cotodel.hrms.web.response.WhatsAppRequest;
import com.cotodel.hrms.web.service.BulkEmployeeService;
import com.cotodel.hrms.web.service.ErupiVoucherCreateDetailsService;
import com.cotodel.hrms.web.service.LoginService;
import com.cotodel.hrms.web.service.Impl.EmailServiceImpl;
import com.cotodel.hrms.web.service.Impl.TokenGenerationImpl;
import com.cotodel.hrms.web.util.EncriptResponse;
import com.cotodel.hrms.web.util.EncryptionDecriptionUtil;
import com.cotodel.hrms.web.util.GraphMailSender;
import com.cotodel.hrms.web.util.JwtTokenValidator;
import com.cotodel.hrms.web.util.MessageConstant;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@CrossOrigin
public class BulkUserController extends CotoDelBaseController{

	private static final Logger logger = LoggerFactory.getLogger(BulkUserController.class);
	private static final String SECRET_KEY = "0123456789012345"; // Must match frontend
    private static final String CLIENT_KEY = "client-secret-key"; // Extra validation

	@Autowired
	public ApplicationConstantConfig applicationConstantConfig;
	
	@Autowired
	BulkEmployeeService bulkEmployeeService;

	@Autowired
	TokenGenerationImpl tokengeneration;
	
	@Autowired
	EmailServiceImpl emailService;
	
	@Autowired
	LoginService loginservice;
	
	@Autowired
	ErupiVoucherCreateDetailsService erupiVoucherCreateDetailsService;
	
	@PostMapping(value="/saveBulkFile")
	public String saveEmployeeDetail(HttpServletResponse response, HttpServletRequest request,
			@ModelAttribute("formData") BulkEmployeeRequest bulkEmployeeRequest, BindingResult result, HttpSession session, Model model,RedirectAttributes redirect) {
		
		String profileRes=null;JSONObject profileJsonRes=null;
		HashMap<String, String> otpMap = new  HashMap<String, String> ();
		ObjectMapper mapper = new ObjectMapper();
		String res = null; 
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
	@PostMapping("/generateExcelTemp")
	public ResponseEntity<ByteArrayResource> generateTempExcel(@RequestBody List<Map<String, String>> filteredData) {
	    try {
	        File file = new File("/opt/cotodel/key/BulkUserTemplate.xlsx");
	        if (!file.exists()) {
	            System.err.println("❌ Excel template file not found at: " + file.getAbsolutePath());
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	        }

	        FileInputStream fis = new FileInputStream(file);
	        Workbook workbook = new XSSFWorkbook(fis);
	        Sheet sheet = workbook.createSheet("Organization Users");

	        Row header = sheet.createRow(0);
	        header.createCell(0).setCellValue("User's Name");
	        header.createCell(1).setCellValue("User's Mobile Number");

	        int rowIdx = 1;
	        for (Map<String, String> emp : filteredData) {
	            Row row = sheet.createRow(rowIdx++);
	            row.createCell(0).setCellValue(emp.getOrDefault("name", ""));
	            row.createCell(1).setCellValue(emp.getOrDefault("mobile", ""));
	        }
	        //sheet.protectSheet("lock123");//lock the sheet so user cannot edit it password for unlocking is lock123
	        ByteArrayOutputStream out = new ByteArrayOutputStream();
	        workbook.write(out);
	        workbook.close();

	        ByteArrayResource resource = new ByteArrayResource(out.toByteArray());
	        HttpHeaders headers = new HttpHeaders();
	        headers.add("Content-Disposition", "attachment; filename=ModifiedBulkUserTemplate.xlsx");

	        return ResponseEntity.ok()
	                .headers(headers)
	                .contentLength(resource.contentLength())
	                .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
	                .body(resource);
	    } catch (Exception e) {
	        e.printStackTrace(); // Log actual error for debug
	        return ResponseEntity.internalServerError().build();
	    }
	}

	@PostMapping(value="/saveBulkemp")
	public @ResponseBody String saveBulkemp(HttpServletResponse response, HttpServletRequest request,
			EmployeeBulkUploadRequest employeeBulkUploadRequest, BindingResult result, HttpSession session, Model model, RedirectAttributes redirect) {

	    String profileRes = null;
	    Map<String, Object> responseMap = new HashMap<>();
	    ObjectMapper mapper = new ObjectMapper();
	    
	    String receivedHash =employeeBulkUploadRequest.getHash();

	    // Validate client key only
	    if (!CLIENT_KEY.equals(employeeBulkUploadRequest.getClientKey())) {
	        responseMap.put("status", false);
	        responseMap.put("message", "Invalid client key");
	        try {
	            return mapper.writeValueAsString(responseMap);
	        } catch (JsonProcessingException e) {
	            return "{\"status\":false, \"message\":\"JSON processing error\"}";
	        }
	    }
	    System.out.println("employeeBulkUploadRequest.getOrgId() " +employeeBulkUploadRequest.getOrgId());
        System.out.println("employeeBulkUploadRequest.getFileName() " +employeeBulkUploadRequest.getFileName());
        System.out.println("employeeBulkUploadRequest.getFile() " +employeeBulkUploadRequest.getFile());
        System.out.println("employeeBulkUploadRequest.getCreatedBy() " +employeeBulkUploadRequest.getCreatedby());
	    
	    // Prepare data string for hashing
	    String dataString =employeeBulkUploadRequest.getOrgId() +employeeBulkUploadRequest.getFileName() +employeeBulkUploadRequest.getFile() +
	            
	    		employeeBulkUploadRequest.getCreatedby() + CLIENT_KEY + SECRET_KEY;

	    String computedHash = null;
	    try {
	        computedHash = generateHash(dataString);
	        System.out.println("computedHash: " + computedHash);
	        System.out.println("dataString: " + dataString);
	    } catch (NoSuchAlgorithmException e) {
	        e.printStackTrace();
	    }

	    // Validate hash
	    boolean isValid = computedHash != null && computedHash.equals(receivedHash);
	    if (!isValid) {
	        responseMap.put("status", false);
	        responseMap.put("message", "Request Tempered");
	        try {
	            return mapper.writeValueAsString(responseMap);
	        } catch (JsonProcessingException e) {
	            return "{\"status\":false, \"message\":\"JSON processing error\"}";
	        }
	    }


	    // Get token from session
	    String token = (String) session.getAttribute("hrms");
	    if (token == null) {
	        responseMap.put("status", false);
	        responseMap.put("message", "Unauthorized: No token found.");
	        try {
	            return mapper.writeValueAsString(responseMap);
	        } catch (JsonProcessingException e) {
	            return "{\"status\":false, \"message\":\"JSON processing error\"}";
	        }
	    }

//	    // Validate Token
	    UserDetailsEntity obj = JwtTokenValidator.parseToken(token);
	    if (obj == null) {
	        responseMap.put("status", false);
	        responseMap.put("message", "Unauthorized: Invalid token.");
	        try {
	            return mapper.writeValueAsString(responseMap);
	        } catch (JsonProcessingException e) {
	            return "{\"status\":false, \"message\":\"JSON processing error\"}";
	        }
	    }

//	     Check User Role and Organization ID
	    if ((obj.getUser_role() == 9 || obj.getUser_role() == 1 || obj.getUser_role() == 3) && obj.getOrgid() == employeeBulkUploadRequest.getOrgId().intValue()) {
	        try {
	            String json = EncryptionDecriptionUtil.convertToJson(employeeBulkUploadRequest);
	            EncriptResponse jsonObject = EncryptionDecriptionUtil.encriptResponse(json, applicationConstantConfig.apiSignaturePublicPath);
	            String encriptResponse = bulkEmployeeService.saveBulkEmpDetail(tokengeneration.getToken(), jsonObject);

	            EncriptResponse userReqEnc = EncryptionDecriptionUtil.convertFromJson(encriptResponse, EncriptResponse.class);
	            profileRes = EncryptionDecriptionUtil.decriptResponse(userReqEnc.getEncriptData(), userReqEnc.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);

	            JSONObject apiJsonResponse = new JSONObject(profileRes);
	            boolean status = apiJsonResponse.getBoolean("status");
	            responseMap.put("status", status);
	            responseMap.put("message", apiJsonResponse.getString("message"));

	            if (status && apiJsonResponse.has("data")) {
	                responseMap.put("data", profileRes);
	            } else {
	            	//responseMap.put("data", profileRes);
	                responseMap.put("status", false);
	                responseMap.put("message", apiJsonResponse.getString("message"));
	                responseMap.put("data", profileRes);
	            }

	        } catch (Exception e) {
	            responseMap.put("status", false);
	            responseMap.put("message", "Internal Server Error: " + e.getMessage());
	            e.printStackTrace();
	        }
	    } else {
	        responseMap.put("status", false);
	        responseMap.put("message", "Unauthorized: Insufficient permissions.");
	    }

	    try {
	        return mapper.writeValueAsString(responseMap);
	    } catch (JsonProcessingException e) {
	        return "{\"status\":false, \"message\":\"JSON processing error\"}";
	    }
	} 
	@PostMapping(value="/createBulkemp")
	public @ResponseBody String createBulkemp(HttpServletResponse response, HttpServletRequest request,
			EmployeeBulkCreateRequest employeeBulkCreateRequest, BindingResult result, HttpSession session, Model model, RedirectAttributes redirect) {
		String subject = null; 
		String bodyText = null;
		
	    String profileRes = null;
	    String whatsappResponse = null;
	    Map<String, Object> responseMap = new HashMap<>();
	    ObjectMapper mapper = new ObjectMapper();
	    
	    String receivedHash =employeeBulkCreateRequest.getHash();
	    String arrayJoined = String.join("", employeeBulkCreateRequest.getArrayofid()); 

	    // Validate client key only
	    if (!CLIENT_KEY.equals(employeeBulkCreateRequest.getClientKey())) {
	        responseMap.put("status", false);
	        responseMap.put("message", "Invalid client key");
	        try {
	            return mapper.writeValueAsString(responseMap);
	        } catch (JsonProcessingException e) {
	            return "{\"status\":false, \"message\":\"JSON processing error\"}";
	        }
	    }
	    System.out.println("vehicleManagementBulkUploadRequest.getOrgId() " +employeeBulkCreateRequest.getOrgId());
	    System.out.println("employeeBulkCreateRequest.getCreatedBy() " +employeeBulkCreateRequest.getCreatedBy());
       
	    // Prepare data string for hashing
	    String dataString =employeeBulkCreateRequest.getOrgId()+employeeBulkCreateRequest.getCreatedBy()+arrayJoined+ CLIENT_KEY + SECRET_KEY;

	    String computedHash = null;
	    try {
	        computedHash = generateHash(dataString);
	        System.out.println("computedHash: " + computedHash);
	        System.out.println("dataString: " + dataString);
	    } catch (NoSuchAlgorithmException e) {
	        e.printStackTrace();
	    }

	    // Validate hash
	    boolean isValid = computedHash != null && computedHash.equals(receivedHash);
	    if (!isValid) {
	        responseMap.put("status", false);
	        responseMap.put("message", "Request Tempered");
	        try {
	            return mapper.writeValueAsString(responseMap);
	        } catch (JsonProcessingException e) {
	            return "{\"status\":false, \"message\":\"JSON processing error\"}";
	        }
	    }


	    // Get token from session
	    String token = (String) session.getAttribute("hrms");
	    if (token == null) {
	        responseMap.put("status", false);
	        responseMap.put("message", "Unauthorized: No token found.");
	        try {
	            return mapper.writeValueAsString(responseMap);
	        } catch (JsonProcessingException e) {
	            return "{\"status\":false, \"message\":\"JSON processing error\"}";
	        }
	    }

//	    // Validate Token
	    UserDetailsEntity obj = JwtTokenValidator.parseToken(token);
	    if (obj == null) {
	        responseMap.put("status", false);
	        responseMap.put("message", "Unauthorized: Invalid token.");
	        try {
	            return mapper.writeValueAsString(responseMap);
	        } catch (JsonProcessingException e) {
	            return "{\"status\":false, \"message\":\"JSON processing error\"}";
	        }
	    }

	    // Check User Role and Organization ID
	    if ((obj.getUser_role() == 9 || obj.getUser_role() == 1 || obj.getUser_role() == 3) && obj.getOrgid() == employeeBulkCreateRequest.getOrgId().intValue()) {
	        try {
	            String json = EncryptionDecriptionUtil.convertToJson(employeeBulkCreateRequest);
	            EncriptResponse jsonObject = EncryptionDecriptionUtil.encriptResponse(json, applicationConstantConfig.apiSignaturePublicPath);
	            String encriptResponse = bulkEmployeeService.createBulkEmpDetail(tokengeneration.getToken(), jsonObject);

	            EncriptResponse userReqEnc = EncryptionDecriptionUtil.convertFromJson(encriptResponse, EncriptResponse.class);
	            profileRes = EncryptionDecriptionUtil.decriptResponse(userReqEnc.getEncriptData(), userReqEnc.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);

	            JSONObject apiJsonResponse = new JSONObject(profileRes);
	            boolean status = apiJsonResponse.getBoolean("status");
	            responseMap.put("status", status);
	            responseMap.put("message", apiJsonResponse.getString("message"));

	            if (status && apiJsonResponse.has("data")) {
	                responseMap.put("data", profileRes);
	                // Map JSON array to List<VoucherData>
	                JSONArray dataArray = apiJsonResponse.getJSONArray("data");
		            ObjectMapper objMapper = new ObjectMapper();
		            List<BulkEmployeeResponse> bulkEmployeeResponse = objMapper.readValue(
		                dataArray.toString(),
		                objMapper.getTypeFactory().constructCollectionType(List.class, BulkEmployeeResponse.class)
		            );

		            // Iterate only successful vouchers
		            for (BulkEmployeeResponse item : bulkEmployeeResponse) {
		                if ("SUCCESS".equalsIgnoreCase(item.getResponse())) {
		                    
		                    SMSRequest smsRequest = new SMSRequest();
				        	smsRequest.setMobile(item.getMobile());
				        	
				        	String template = "Hi, #VAR1# has added you to the Cotodel Dashboard to manage your business expenses! Log in to Cotodel now to access UPI Vouchers.";
				        	String finalMessage = template
				        	        .replace("#VAR1#", "Cotodel");
				        	       // .replace("#VAR2#", voucherCode);
				        	
				        	smsRequest.setMessage(finalMessage);
			                try {
			                String userFormjson = EncryptionDecriptionUtil.convertToJson(smsRequest);

			    			EncriptResponse userFormjsonObject=EncryptionDecriptionUtil.encriptResponse(userFormjson, applicationConstantConfig.apiSignaturePublicPath);

			    			String encriptsmsResponse = loginservice.sendTransactionalSMS(tokengeneration.getToken(), userFormjsonObject);

			       
			    			EncriptResponse userFornReqEnc =EncryptionDecriptionUtil.convertFromJson(encriptsmsResponse, EncriptResponse.class);

			    			String smsResponse =  EncryptionDecriptionUtil.decriptResponse(userFornReqEnc.getEncriptData(), userFornReqEnc.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
			    			if(item.getEmail() !=null && item.getEmail() !="") {
				    			bodyText = "<!doctype html>\r\n"
				    					+ "<html lang=\"en\">\r\n"
				    					+ "  <head>\r\n"
				    					+ "    <meta charset=\"utf-8\" />\r\n"
				    					+ "    <title>Cotodel – Welcome</title>\r\n"
				    					+ "    <style>\r\n"
				    					+ "      body {\r\n"
				    					+ "        margin: 0;\r\n"
				    					+ "        padding: 0;\r\n"
				    					+ "        background: #ffffff;\r\n"
				    					+ "        font-family: Arial, Helvetica, sans-serif;\r\n"
				    					+ "        color: #1d2b21;\r\n"
				    					+ "      }\r\n"
				    					+ "      table { border-spacing: 0; border-collapse: collapse; width: 100%; }\r\n"
				    					+ "      img { border: 0; display: block; max-width: 100%; }\r\n"
				    					+ "      .container { max-width: 600px; margin: 0 auto; }\r\n"
				    					+ "      .hero-bar {\r\n"
				    					+ "        background: #2b8a56;\r\n"
				    					+ "        color: #ffffff;\r\n"
				    					+ "      }\r\n"
				    					+ "      .tagline { font-size: 14px; opacity: .9; margin-top: 6px; }\r\n"
				    					+ "      .card {\r\n"
				    					+ "        background: #f1f6f3;\r\n"
				    					+ "        border-radius: 12px;\r\n"
				    					+ "        overflow: hidden;\r\n"
				    					+ "        margin: 20px 0;\r\n"
				    					+ "      }\r\n"
				    					+ "      .content { padding: 20px; font-size: 15px; line-height: 1.6; }\r\n"
				    					+ "      h2 { margin: 0 0 15px; font-size: 20px; }\r\n"
				    					+ "      .btn {\r\n"
				    					+ "        background: #2b8a56;\r\n"
				    					+ "        color: #ffffff !important;\r\n"
				    					+ "        text-decoration: none;\r\n"
				    					+ "        padding: 12px 24px;\r\n"
				    					+ "        border-radius: 6px;\r\n"
				    					+ "        display: inline-block;\r\n"
				    					+ "        font-weight: bold;\r\n"
				    					+ "        font-size: 14px;\r\n"
				    					+ "      }\r\n"
				    					+ "      .footer {\r\n"
				    					+ "        background: #f1f6f3;\r\n"
				    					+ "        padding: 20px;\r\n"
				    					+ "        font-size: 13px;\r\n"
				    					+ "        color: #6b7a71;\r\n"
				    					+ "      }\r\n"
				    					+ "      .footer-logo { margin-bottom: 10px; }\r\n"
				    					+ "      .footer hr { border: 0; border-top: 1px solid #ccc; margin: 15px 0; }\r\n"
				    					+ "      .footer a { color: #2b8a56; text-decoration: none; }\r\n"
				    					+ "      .social a { margin-right: 10px; text-decoration: none; font-size: 16px; color: #2b8a56; }\r\n"
				    					+ "    </style>\r\n"
				    					+ "  </head>\r\n"
				    					+ "  <body>\r\n"
				    					+ "    <table role=\"presentation\" width=\"100%\">\r\n"
				    					+ "      <tr>\r\n"
				    					+ "        <td>\r\n"
				    					+ "          <div class=\"container\">\r\n"
				    					+ "\r\n"
				    					+ "            <!-- Header with background + side image -->\r\n"
				    					+ "            <table role=\"presentation\" width=\"100%\" class=\"hero-bar\">\r\n"
				    					+ "              <tr>\r\n"
				    					+ "                <td style=\"padding:20px; vertical-align:middle; width:65%;\">\r\n"
				    					+ "                  <img src=\"https://staging.cotodel.com/img/emailer_cotodel_without_tagline.png\" alt=\"Cotodel logo\" width=\"160\" height=\"54\" style=\"object-fit:contain;\" />\r\n"
				    					+ "                  <p class=\"tagline\">Business expenses made seamless like your personal UPI spends</p>\r\n"
				    					+ "                </td>\r\n"
				    					+ "                <td style=\"width:35%; text-align:right; vertical-align:bottom;\">\r\n"
				    					+ "                  <img src=\"https://staging.cotodel.com/img/Emailer_Scan_to_pay-bro_1.png\" alt=\"Green side illustration\" width=\"200\" style=\"max-width:200px; height:auto;\" />\r\n"
				    					+ "                </td>\r\n"
				    					+ "              </tr>\r\n"
				    					+ "            </table>\r\n"
				    					+ "\r\n"
				    					+ "            <!-- Illustration -->\r\n"
				    					+ "            <div class=\"card\">\r\n"
				    					+ "              <img src=\"https://staging.cotodel.com/img/Emailer_corp_signup_img1.png\" alt=\"Cotodel platform welcome illustration\" />\r\n"
				    					+ "            </div>\r\n"
				    					+ "\r\n"
				    					+ "            <!-- Body -->\r\n"
				    					+ "            <div class=\"content\">\r\n"
				    					+ "              <h2>Welcome, <span style=\"color:#555;\">"+item.getName()+"</span>!</h2>\r\n"
				    					+ "              <p>You have successfully signed up on Cotodel platform!</p>\r\n"
				    					+ "              <p>Our team will get in touch with you soon to activate your account. Start managing your corporate expenses with ease by administering allowances for your employees.</p>\r\n"
				    					+ "              <p>If you would want to schedule a demo with our team, please choose any slot as per your convenience.</p>\r\n"
				    					+ "\r\n"
				    					+ "              <p style=\"text-align:center; margin:20px 0;\">\r\n"
				    					+ "                <a href=\"https://calendly.com/business-cotodel/demo\" class=\"btn\">Schedule Demo</a>\r\n"
				    					+ "              </p>\r\n"
				    					+ "\r\n"
				    					+ "              <p style=\"font-size:12px; color:#777; margin-top:20px;\">\r\n"
				    					+ "                Disclaimer: Please mark us as <em>'Not Spam'</em> to receive future emails from Cotodel in your inbox\r\n"
				    					+ "              </p>\r\n"
				    					+ "            </div>\r\n"
				    					+ "\r\n"
				    					+ "            <!-- Footer -->\r\n"
				    					+ "            <div class=\"footer\">\r\n"
				    					+ "              <div class=\"footer-logo\">\r\n"
				    					+ "                <img src=\"https://staging.cotodel.com/img/Cotodel_Logo.png\" alt=\"Cotodel logo\" width=\"120\" height=\"34\" style=\"object-fit:contain;\" />\r\n"
				    					+ "              </div>\r\n"
				    					+ "              <div><strong>ADDRESS</strong><br>WeWork, Eldeco Centre, Malviya Nagar New Delhi - 110017</div>\r\n"
				    					+ "              <hr>\r\n"
				    					+ "              <div>\r\n"
				    					+ "                <a href=\"#\">Click to unsubscribe from Marketing emails</a><br><br>\r\n"
				    					+ "                © 2025 Cotodel – All rights reserved\r\n"
				    					+ "              </div>\r\n"
				    					+ "              <div class=\"social\" style=\"margin-top:10px;\">\r\n"
				    					+ "	                <a href=\"https://www.linkedin.com/company/cotopay/posts/?feedView=all\" aria-label=\"LinkedIn\">\r\n"
				    					+ "	                  <img src=\"https://cdn-icons-png.flaticon.com/512/174/174857.png\" alt=\"LinkedIn\" width=\"24\" height=\"24\" style=\"display:inline-block; margin-right:10px;\">\r\n"
				    					+ "	                </a>\r\n"
				    					+ "	                <a href=\"https://www.instagram.com/cotodelsolutions\" aria-label=\"Instagram\">\r\n"
				    					+ "	                  <img src=\"https://cdn-icons-png.flaticon.com/512/2111/2111463.png\" alt=\"Instagram\" width=\"24\" height=\"24\" style=\"display:inline-block; margin-right:10px;\">\r\n"
				    					+ "	                </a>\r\n"
				    					+ "	                <a href=\"https://www.youtube.com/@Cotodel\" aria-label=\"YouTube\">\r\n"
				    					+ "	                  <img src=\"https://cdn-icons-png.flaticon.com/512/1384/1384060.png\" alt=\"YouTube\" width=\"24\" height=\"24\" style=\"display:inline-block;\">\r\n"
				    					+ "	                </a>\r\n"
				    					+ "              </div>\r\n"
				    					+ "            </div>\r\n"
				    					+ "\r\n"
				    					+ "          </div>\r\n"
				    					+ "        </td>\r\n"
				    					+ "      </tr>\r\n"
				    					+ "    </table>\r\n"
				    					+ "  </body>\r\n"
				    					+ "</html>\r\n"
				    					+ "";
				    			Map<String, Object> responses = GraphMailSender.sendMail(GraphMailSender.acquireToken(), "support@cotodel.com",
				    					item.getEmail(),"✅ You are invited to join Cotodel",  bodyText);
				    			}
			                }
			                catch (Exception e) {
		            			// TODO Auto-generated catch block
		            			e.printStackTrace();
		            		}
		                    WhatsAppRequest whatsapp = new WhatsAppRequest();
		                    whatsapp.setSource("new-landing-page form");
		                    whatsapp.setCampaignName("Employee_add_confirmation");
		                    whatsapp.setFirstName(item.getName());
		                    whatsapp.setMobile(item.getMobile());
		                    whatsapp.setOrganizationName("Cotodel");
		                    whatsapp.setUserName("Cotodel Communications");
		                    try {
		            			String whatsappJson = EncryptionDecriptionUtil.convertToJson(whatsapp);

		            			EncriptResponse whatsappjsonObject=EncryptionDecriptionUtil.encriptResponse(whatsappJson, applicationConstantConfig.apiSignaturePublicPath);

		            			String whatsappencriptResponse =  erupiVoucherCreateDetailsService.sendWhatsupMessage(tokengeneration.getToken(), whatsappjsonObject);
		               
		            			EncriptResponse whatsuserReqEnc =EncryptionDecriptionUtil.convertFromJson(whatsappencriptResponse, EncriptResponse.class);

		            			whatsappResponse =  EncryptionDecriptionUtil.decriptResponse(whatsuserReqEnc.getEncriptData(), whatsuserReqEnc.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
		            		} catch (Exception e) {
		            			// TODO Auto-generated catch block
		            			e.printStackTrace();
		            		}
		                    
		                }
			                
		            }
		        	   
	             
	            } else {
	            	//responseMap.put("data", profileRes);
	                responseMap.put("status", false);
	                responseMap.put("message", apiJsonResponse.getString("message"));
	                responseMap.put("data", profileRes);
	            }

	        } catch (Exception e) {
	            responseMap.put("status", false);
	            responseMap.put("message", "Internal Server Error: " + e.getMessage());
	            e.printStackTrace();
	        }
	    } else {
	        responseMap.put("status", false);
	        responseMap.put("message", "Unauthorized: Insufficient permissions.");
	    }

	    try {
	        return mapper.writeValueAsString(responseMap);
	    } catch (JsonProcessingException e) {
	        return "{\"status\":false, \"message\":\"JSON processing error\"}";
	    }
	} 
	 private String generateHash(String data) throws NoSuchAlgorithmException {
	        MessageDigest digest = MessageDigest.getInstance("SHA-256");
	        byte[] hashBytes = digest.digest(data.getBytes(StandardCharsets.UTF_8));
	        StringBuilder hexString = new StringBuilder();
	        for (byte b : hashBytes) {
	            hexString.append(String.format("%02x", b));
	        }
	        return hexString.toString();
	    }
	 
}
