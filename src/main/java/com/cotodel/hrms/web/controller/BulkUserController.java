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
import com.cotodel.hrms.web.response.VoucherData;
import com.cotodel.hrms.web.response.WhatsAppRequest;
import com.cotodel.hrms.web.service.BulkEmployeeService;
import com.cotodel.hrms.web.service.ErupiVoucherCreateDetailsService;
import com.cotodel.hrms.web.service.LoginService;
import com.cotodel.hrms.web.service.Impl.EmailServiceImpl;
import com.cotodel.hrms.web.service.Impl.TokenGenerationImpl;
import com.cotodel.hrms.web.util.EncriptResponse;
import com.cotodel.hrms.web.util.EncryptionDecriptionUtil;
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
		                    
		                	System.out.println("Name: " + item.getName());
		                    System.out.println("Amount: " + item.getBeneficiaryName());
		                    System.out.println("Response: " + item.getMobile());
		                    
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
			    			//String emailRequest =	emailService.sendEmail(employeeOnboarding.getEmail());
			    			
			                }
			                catch (Exception e) {
		            			// TODO Auto-generated catch block
		            			e.printStackTrace();
		            		}
		                    WhatsAppRequest whatsapp = new WhatsAppRequest();
		                    whatsapp.setSource("new-landing-page form");
		                    whatsapp.setCampaignName("Voucher_Issuance");
		                    whatsapp.setFirstName(item.getBeneficiaryName());
		                   // whatsapp.setAmount(item.getAmount());
		                    //whatsapp.setCategory(item.getVoucherDesc());
		                    whatsapp.setMobile(item.getMobile());
		                    whatsapp.setOrganizationName("Cotodel");
		                   /// whatsapp.setValidity(item.getValidity());
		                   // whatsapp.setType(item.getRedemtionType());
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
