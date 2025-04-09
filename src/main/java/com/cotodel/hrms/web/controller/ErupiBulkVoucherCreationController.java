package com.cotodel.hrms.web.controller;

import java.io.File;
import java.io.FileInputStream;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cotodel.hrms.web.properties.ApplicationConstantConfig;
import com.cotodel.hrms.web.response.BulkVoucherRequest;
import com.cotodel.hrms.web.response.ErupiBulkVoucherCreateRequest;
import com.cotodel.hrms.web.response.ErupiVoucherCreateDetails;
import com.cotodel.hrms.web.response.UserDetailsEntity;
import com.cotodel.hrms.web.service.BulkVoucherService;
import com.cotodel.hrms.web.service.ErupiVoucherCreateDetailsService;
import com.cotodel.hrms.web.service.Impl.TokenGenerationImpl;
import com.cotodel.hrms.web.util.EncriptResponse;
import com.cotodel.hrms.web.util.EncryptionDecriptionUtil;
import com.cotodel.hrms.web.util.JwtTokenValidator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@CrossOrigin
public class ErupiBulkVoucherCreationController extends CotoDelBaseController{

	private static final Logger logger = LoggerFactory.getLogger(ErupiBulkVoucherCreationController.class);
	private static final String SECRET_KEY = "0123456789012345"; // Must match frontend
    private static final String CLIENT_KEY = "client-secret-key"; // Extra validation
	@Autowired
	public ApplicationConstantConfig applicationConstantConfig;
	
	@Autowired
	BulkVoucherService bulkVoucherService;

	@Autowired
	TokenGenerationImpl tokengeneration;
	
	@Autowired
	ErupiVoucherCreateDetailsService erupiVoucherCreateDetailsService;
	
	@PostMapping(value="/saveBulkVoucher")
	public @ResponseBody  String saveBulkVoucher(HttpServletResponse response, HttpServletRequest request,
			BulkVoucherRequest bulkVoucherRequest, BindingResult result, HttpSession session, Model model,RedirectAttributes redirect) {
		
		String profileRes=null;
		//profileRes = bulkVoucherService.saveBulkVoucher(tokengeneration.getToken(),bulkVoucherRequest);
		
		//  return profileRes;
		
		try {
			String json = EncryptionDecriptionUtil.convertToJson(bulkVoucherRequest);

			EncriptResponse jsonObject=EncryptionDecriptionUtil.encriptResponse(json, applicationConstantConfig.apiSignaturePublicPath);

			String encriptResponse = bulkVoucherService.saveBulkVoucher(tokengeneration.getToken(), jsonObject);

   
			EncriptResponse userReqEnc =EncryptionDecriptionUtil.convertFromJson(encriptResponse, EncriptResponse.class);

			profileRes =  EncryptionDecriptionUtil.decriptResponse(userReqEnc.getEncriptData(), userReqEnc.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
   
	return profileRes;
		  
	}


	@PostMapping(value="/issueBulkVoucher")
	public @ResponseBody String issueBulkVoucher(HttpServletRequest request, ModelMap model,Locale locale,HttpSession session,
			ErupiBulkVoucherCreateRequest erupiBulkVoucherCreateRequest) {
		
	    Map<String, Object> responseMap = new HashMap<>();
	    ObjectMapper mapper = new ObjectMapper();
		//profileRes = erupiVoucherCreateDetailsService.issueBulkVoucher(tokengeneration.getToken(),erupiBulkVoucherCreateRequest);
		
		//return profileRes;
		
		String receivedHash = erupiBulkVoucherCreateRequest.getHash();
		 // Validate client key first
        if (!CLIENT_KEY.equals(erupiBulkVoucherCreateRequest.getClientKey())) {
        	 responseMap.put("status", false);
             responseMap.put("message", "Invalid client key");
        }
       // const dataString = orgId+voucherCode+purposeCode+payerva+"01"+CREATE+
       // 		bankCode+acNumber+voucherCode+voucherName+merchentid+submurchentid+createdby+
       // 		firstColumnData+clientKey+secretKey;
        String dataString = erupiBulkVoucherCreateRequest.getOrgId()+erupiBulkVoucherCreateRequest.getPurposeCode()+erupiBulkVoucherCreateRequest.
        		getMcc()+
        		erupiBulkVoucherCreateRequest.getPayerVA()+erupiBulkVoucherCreateRequest.getMandateType()+erupiBulkVoucherCreateRequest.getType()
        +erupiBulkVoucherCreateRequest.getBankcode()+ erupiBulkVoucherCreateRequest.getAccountNumber()+
        erupiBulkVoucherCreateRequest.getVoucherCode()+erupiBulkVoucherCreateRequest.getVoucherDesc()+erupiBulkVoucherCreateRequest.getMerchantId()+
        erupiBulkVoucherCreateRequest.getSubMerchantId()+erupiBulkVoucherCreateRequest.getCreatedby()+erupiBulkVoucherCreateRequest.getArrayofid()+CLIENT_KEY+SECRET_KEY;
		   

        // Compute hash
        String computedHash = null;
		try {
			computedHash = generateHash(dataString);
			System.out.println("computedHash"+computedHash);
			System.out.println("computedHash---"+dataString);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    // Validate hash
    boolean isValid = computedHash.equals(receivedHash);
 
    // Get token from session
    if (!isValid) {
        responseMap.put("status", false);
        responseMap.put("message", "Request Tempered");
        try {
            return mapper.writeValueAsString(responseMap);
        } catch (JsonProcessingException e) {
            return "{\"status\":false, \"message\":\"JSON processing error\"}";
        }
    }
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
    // Validate Token
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
    if ((obj.getUser_role() == 9 || obj.getUser_role() == 1 || obj.getUser_role() == 3) && obj.getOrgid() == erupiBulkVoucherCreateRequest.getOrgId().intValue()) {
        try {
            // Call Service
            String encryptedResponse = erupiVoucherCreateDetailsService.issueBulkVoucher(tokengeneration.getToken(),erupiBulkVoucherCreateRequest);
            // Decrypt Response
         
            JSONObject apiJsonResponse = new JSONObject(encryptedResponse);
            
            boolean status = apiJsonResponse.getBoolean("status");
	        responseMap.put("status", status);
	        responseMap.put("message", apiJsonResponse.getString("message"));

	        if (status && apiJsonResponse.has("data")) {
	            List<Object> dataList = apiJsonResponse.getJSONArray("data").toList();
	            responseMap.put("data", dataList);
	        } else {
                responseMap.put("status", false);
                responseMap.put("message", apiJsonResponse.getString("message"));
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
	
	@PostMapping(value = "/beneficiaryDeleteFromVoucherList")
	public @ResponseBody String beneficiaryDeleteFromVoucherList(HttpServletRequest request, ModelMap model, Locale locale,
			HttpSession session, BulkVoucherRequest bulkVoucherRequest) {
		String profileRes = null;
		//profileRes = erupiVoucherCreateDetailsService.beneficiaryDeleteFromVoucherList(tokengeneration.getToken(),	bulkVoucherRequest);

		//return profileRes;
		try {
			String json = EncryptionDecriptionUtil.convertToJson(bulkVoucherRequest);

			EncriptResponse jsonObject=EncryptionDecriptionUtil.encriptResponse(json, applicationConstantConfig.apiSignaturePublicPath);

			String encriptResponse = erupiVoucherCreateDetailsService.beneficiaryDeleteFromVoucherList(tokengeneration.getToken(), jsonObject);

   
			EncriptResponse userReqEnc =EncryptionDecriptionUtil.convertFromJson(encriptResponse, EncriptResponse.class);

			profileRes =  EncryptionDecriptionUtil.decriptResponse(userReqEnc.getEncriptData(), userReqEnc.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
   
	return profileRes;
	}
	
	@GetMapping(value = "/getVoucherTemplate")
	public ResponseEntity<InputStreamResource> getVoucherTemplate() {
		try {
			//String filePath ="D:\\opt\\file\\"; //local path 
			String filePath ="/opt/cotodel/key/";
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
