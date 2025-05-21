package com.cotodel.hrms.web.controller;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cotodel.hrms.web.properties.ApplicationConstantConfig;
import com.cotodel.hrms.web.response.BandDetailRequest;
import com.cotodel.hrms.web.response.ErupiVoucherCreateDetails;
import com.cotodel.hrms.web.response.ExpanceTravelAdvanceRequest;
import com.cotodel.hrms.web.response.ExpenseCategoryRequest;
import com.cotodel.hrms.web.response.UserDetailsEntity;
import com.cotodel.hrms.web.service.ExpensesTravelService;
import com.cotodel.hrms.web.service.Impl.TokenGenerationImpl;
import com.cotodel.hrms.web.util.EncriptResponse;
import com.cotodel.hrms.web.util.EncryptionDecriptionUtil;
import com.cotodel.hrms.web.util.JwtTokenValidator;
import com.cotodel.hrms.web.util.MessageConstant;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@CrossOrigin
public class ExpensesTravelPoliciesController extends CotoDelBaseController {

	private static final Logger logger = LoggerFactory.getLogger(ExpensesTravelPoliciesController.class);

	private static final String SECRET_KEY = "0123456789012345"; // Must match frontend
    private static final String CLIENT_KEY = "client-secret-key"; // Extra validation
    
	@Autowired
	public ApplicationConstantConfig applicationConstantConfig;

	@Autowired
	ExpensesTravelService expensesTravelService;

	@Autowired
	TokenGenerationImpl tokengeneration;

	@PostMapping(value = "/saveExpensesCategory")
	public @ResponseBody String saveExpensesCategory(HttpServletRequest request, ModelMap model, Locale locale,
			HttpSession session, ExpenseCategoryRequest expenseCategoryRequest) {
	
		List<BandDetailRequest> list = new ArrayList<BandDetailRequest>();
		String data[] = expenseCategoryRequest.getListArray();
		
		 for (int i = 0; i < data.length; i++) { String listValue=data[i]; 
		 String[] rowArray= listValue.split("@"); 
		  BandDetailRequest bandDetailRequest= new  BandDetailRequest(); 
		  
		  String rowarray0 = rowArray[0];
		  if(!rowarray0.equalsIgnoreCase("undefined")) {
		   bandDetailRequest.setBandType(rowArray[0]);
		  }
		  String rowarray1 = rowArray[1];
		  if(!rowarray1.equalsIgnoreCase("undefined")) {
		   bandDetailRequest.setBandOneInr(rowArray[1]);
		  }
		  String rowarray2 = rowArray[2];
		  if(!rowarray2.equalsIgnoreCase("undefined")) {
		   bandDetailRequest.setBandTwoInr(rowArray[2]);
		  }
		  String rowarray3 = rowArray[3];
		  if(!rowarray3.equalsIgnoreCase("undefined")) {
		   bandDetailRequest.setBandThreeInr(rowArray[3]);
		  }
		  String rowarray4 = rowArray[4];
		  if(!rowarray4.equalsIgnoreCase("undefined")) {
		  bandDetailRequest.setBandFourInr(rowArray[4]);
		  }
		  String rowarray5 = rowArray[5];
		  if(!rowarray5.equalsIgnoreCase("undefined")) {
		  bandDetailRequest.setBandFiveInr(rowArray[5]);
		  }
		  String rowarray6 = rowArray[6];
		  if(!rowarray6.equalsIgnoreCase("undefined")) {
		  bandDetailRequest.setBandSixInr(rowArray[6]);
		  }
		  list.add(bandDetailRequest); }
		 

		expenseCategoryRequest.setList(list);
	
		String receivedHash = expenseCategoryRequest.getHash();
		 // Validate client key first
      if (!CLIENT_KEY.equals(expenseCategoryRequest.getClientKey())) {
        //  return Map.of("isValid", false, "message", "Invalid client key");
      }
      String dataString = expenseCategoryRequest.getEmployerId()+expenseCategoryRequest.getExpenseCategory()+
    		  expenseCategoryRequest.getExpenseCode()+expenseCategoryRequest.getExpenseLimit()+expenseCategoryRequest.getDistingushEmployeeBand()+
      expenseCategoryRequest.getDayToExpiry()+
      CLIENT_KEY+SECRET_KEY;
     // employerid+expenseCategory+expanceCode+expanceLimit+distingushEmployeeBand+timeperiod+clientKey+secretKey;
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
  Map<String, Object> responseMap = new HashMap<>();
  ObjectMapper mapper = new ObjectMapper();

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
  if ((obj.getUser_role() == 9 || obj.getUser_role() == 1 || obj.getUser_role() == 3) && obj.getOrgid() == expenseCategoryRequest.getEmployerId().intValue()) {
      try {
          
   	   // Convert request object to JSON
          String json = EncryptionDecriptionUtil.convertToJson(expenseCategoryRequest);

          // Encrypt Request
          EncriptResponse jsonObject = EncryptionDecriptionUtil.encriptResponse(json, applicationConstantConfig.apiSignaturePublicPath);

          // Call Service
          String encryptedResponse = expensesTravelService.saveExpensesCategory(tokengeneration.getToken(), jsonObject);

          // Decrypt Response
          EncriptResponse userReqEnc = EncryptionDecriptionUtil.convertFromJson(encryptedResponse, EncriptResponse.class);
          String apiResponse = EncryptionDecriptionUtil.decriptResponse(
                  userReqEnc.getEncriptData(), 
                  userReqEnc.getEncriptKey(), 
                  applicationConstantConfig.apiSignaturePrivatePath
          );

          JSONObject apiJsonResponse = new JSONObject(apiResponse);
          
          boolean status = apiJsonResponse.getBoolean("status");
	        responseMap.put("status", status);
	        responseMap.put("message", apiJsonResponse.getString("message"));

	        if (status && apiJsonResponse.has("data")) {
	        	  responseMap.put("status", true);
	                responseMap.put("message", apiJsonResponse.getString("message"));
	        	
	          
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

	@PostMapping(value = "/updateExpensesCategory")
	public @ResponseBody String updateExpensesCategory(HttpServletRequest request, ModelMap model, Locale locale,
			HttpSession session, ExpenseCategoryRequest expenseCategoryRequest) {
		String profileRes = null;
		
		List<BandDetailRequest> list = new ArrayList<BandDetailRequest>();
		String data[] = expenseCategoryRequest.getListArray();
		
		
		  for (int i = 0; i < data.length; i++) { 
			  String listValue=data[i]; 
			  String[] rowArray=listValue.split("@");
			  BandDetailRequest bandDetailRequest= new BandDetailRequest();
			  

			  String rowarray0 = rowArray[0];
			  if(!rowarray0.equalsIgnoreCase("undefined")) {
			   bandDetailRequest.setBandType(rowArray[0]);
			  }
			  String rowarray1 = rowArray[1];
			  if(!rowarray1.equalsIgnoreCase("undefined")) {
			   bandDetailRequest.setBandOneInr(rowArray[1]);
			  }
			  String rowarray2 = rowArray[2];
			  if(!rowarray2.equalsIgnoreCase("undefined")) {
			   bandDetailRequest.setBandTwoInr(rowArray[2]);
			  }
			  String rowarray3 = rowArray[3];
			  if(!rowarray3.equalsIgnoreCase("undefined")) {
			   bandDetailRequest.setBandThreeInr(rowArray[3]);
			  }
			  String rowarray4 = rowArray[4];
			  if(!rowarray4.equalsIgnoreCase("undefined")) {
			  bandDetailRequest.setBandFourInr(rowArray[4]);
			  }
			  String rowarray5 = rowArray[5];
			  if(!rowarray5.equalsIgnoreCase("undefined")) {
			  bandDetailRequest.setBandFiveInr(rowArray[5]);
			  }
			  String rowarray6 = rowArray[6];
			  if(!rowarray6.equalsIgnoreCase("undefined")) {
			  bandDetailRequest.setBandSixInr(rowArray[6]);
			  }
			  
		     list.add(bandDetailRequest); }
		  
			expenseCategoryRequest.setList(list);
			try {
				String json = EncryptionDecriptionUtil.convertToJson(expenseCategoryRequest);

				EncriptResponse jsonObject=EncryptionDecriptionUtil.encriptResponse(json, applicationConstantConfig.apiSignaturePublicPath);

				String encriptResponse = expensesTravelService.saveExpensesCategory(tokengeneration.getToken(), jsonObject);

	   
				EncriptResponse userReqEnc =EncryptionDecriptionUtil.convertFromJson(encriptResponse, EncriptResponse.class);

				profileRes =  EncryptionDecriptionUtil.decriptResponse(userReqEnc.getEncriptData(), userReqEnc.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	   
		return profileRes;
	}
	
	@GetMapping(value = "/getExpensesCategory")
	public @ResponseBody String getExpensesCategory(HttpServletRequest request, ModelMap model, Locale locale,
			HttpSession session, ExpenseCategoryRequest expenseCategoryRequest) {
		String profileRes = null;
		
		try {
			String json = EncryptionDecriptionUtil.convertToJson(expenseCategoryRequest);

			EncriptResponse jsonObject=EncryptionDecriptionUtil.encriptResponse(json, applicationConstantConfig.apiSignaturePublicPath);

			String encriptResponse =  expensesTravelService.getExpensesCategory(tokengeneration.getToken(), jsonObject);

   
			EncriptResponse userReqEnc =EncryptionDecriptionUtil.convertFromJson(encriptResponse, EncriptResponse.class);

			profileRes =  EncryptionDecriptionUtil.decriptResponse(userReqEnc.getEncriptData(), userReqEnc.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
   
	return profileRes;
	
	
	}

	@GetMapping(value = "/editExpensesCategory")
	public @ResponseBody String editExpensesCategory(HttpServletRequest request, ModelMap model, Locale locale,
			HttpSession session, ExpenseCategoryRequest expenseCategoryRequest) {
		String profileRes = null;
		try {
			String json = EncryptionDecriptionUtil.convertToJson(expenseCategoryRequest);

			EncriptResponse jsonObject=EncryptionDecriptionUtil.encriptResponse(json, applicationConstantConfig.apiSignaturePublicPath);

			String encriptResponse =  expensesTravelService.getEditExpensesCategory(tokengeneration.getToken(), jsonObject);

   
			EncriptResponse userReqEnc =EncryptionDecriptionUtil.convertFromJson(encriptResponse, EncriptResponse.class);

			profileRes =  EncryptionDecriptionUtil.decriptResponse(userReqEnc.getEncriptData(), userReqEnc.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
   
	return profileRes;
	}

	@PostMapping(value = "/saveExpanceTravelAdvance")
	public @ResponseBody String saveExpanceTravelAdvance(HttpServletRequest request, ModelMap model, Locale locale,
			HttpSession session, ExpanceTravelAdvanceRequest expanceTravelAdvanceRequest) {
	
		String receivedHash = expanceTravelAdvanceRequest.getHash();
		 // Validate client key first
       if (!CLIENT_KEY.equals(expanceTravelAdvanceRequest.getClientKey())) {
         //  return Map.of("isValid", false, "message", "Invalid client key");
       }
       String dataString = expanceTravelAdvanceRequest.getEmployerId()+expanceTravelAdvanceRequest.getAllowEmployeesTravel()
       +expanceTravelAdvanceRequest.getAllowEmployeesCash()+expanceTravelAdvanceRequest.getEmployeesAllow()
       +expanceTravelAdvanceRequest.getNameEmployeesCash()+expanceTravelAdvanceRequest.getDaysDisbursalCash()+
       CLIENT_KEY+SECRET_KEY;
		   
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
   Map<String, Object> responseMap = new HashMap<>();
   ObjectMapper mapper = new ObjectMapper();

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
   if ((obj.getUser_role() == 9 || obj.getUser_role() == 1 || obj.getUser_role() == 3) && obj.getOrgid() == expanceTravelAdvanceRequest.getEmployerId().intValue()) {
       try {
           
    	   // Convert request object to JSON
           String json = EncryptionDecriptionUtil.convertToJson(expanceTravelAdvanceRequest);

           // Encrypt Request
           EncriptResponse jsonObject = EncryptionDecriptionUtil.encriptResponse(json, applicationConstantConfig.apiSignaturePublicPath);

           // Call Service
           String encryptedResponse = expensesTravelService.saveExpanceTravelAdvance(tokengeneration.getToken(), jsonObject);

           // Decrypt Response
           EncriptResponse userReqEnc = EncryptionDecriptionUtil.convertFromJson(encryptedResponse, EncriptResponse.class);
           String apiResponse = EncryptionDecriptionUtil.decriptResponse(
                   userReqEnc.getEncriptData(), 
                   userReqEnc.getEncriptKey(), 
                   applicationConstantConfig.apiSignaturePrivatePath
           );

           JSONObject apiJsonResponse = new JSONObject(apiResponse);
           
           boolean status = apiJsonResponse.getBoolean("status");
	        responseMap.put("status", status);
	        responseMap.put("message", apiJsonResponse.getString("message"));

	        if (status && apiJsonResponse.has("data")) {
	        	  responseMap.put("status", true);
	                responseMap.put("message", apiJsonResponse.getString("message"));
	        	
	          
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

	@GetMapping(value = "/getExpanseTravelAdvance")
	public @ResponseBody String getExpanseTravelAdvance(HttpServletRequest request, ModelMap model, Locale locale,
			HttpSession session, ExpanceTravelAdvanceRequest expanceTravelAdvanceRequest) {
		String profileRes = null;
		try {
			String json = EncryptionDecriptionUtil.convertToJson(expanceTravelAdvanceRequest);

			EncriptResponse jsonObject=EncryptionDecriptionUtil.encriptResponse(json, applicationConstantConfig.apiSignaturePublicPath);

			String encriptResponse = expensesTravelService.getExpanseTravelAdvance(tokengeneration.getToken(), jsonObject);

   
			EncriptResponse userReqEnc =EncryptionDecriptionUtil.convertFromJson(encriptResponse, EncriptResponse.class);

			profileRes =  EncryptionDecriptionUtil.decriptResponse(userReqEnc.getEncriptData(), userReqEnc.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
   
	return profileRes;
	}

	@GetMapping(value = "/deleteExpanseTravelAdvance")
	public @ResponseBody String deleteExpanseTravelAdvance(HttpServletRequest request, ModelMap model, Locale locale,
			HttpSession session, ExpenseCategoryRequest expenseCategoryRequest) {
		String profileRes = null;
	
		try {
			String json = EncryptionDecriptionUtil.convertToJson(expenseCategoryRequest);

			EncriptResponse jsonObject=EncryptionDecriptionUtil.encriptResponse(json, applicationConstantConfig.apiSignaturePublicPath);

			String encriptResponse = expensesTravelService.deletetExpanseTravelAdvance(tokengeneration.getToken(), jsonObject);

   
			EncriptResponse userReqEnc =EncryptionDecriptionUtil.convertFromJson(encriptResponse, EncriptResponse.class);

			profileRes =  EncryptionDecriptionUtil.decriptResponse(userReqEnc.getEncriptData(), userReqEnc.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
   
	return profileRes;
	}

	@GetMapping(value = "/getExpenseBandList")
	public @ResponseBody String getExpenseBandList(HttpServletRequest request, ModelMap model, Locale locale,
			HttpSession session, ExpanceTravelAdvanceRequest expanceTravelAdvanceRequest) {
		String profileRes = null;

		try {
			String json = EncryptionDecriptionUtil.convertToJson(expanceTravelAdvanceRequest);

			EncriptResponse jsonObject=EncryptionDecriptionUtil.encriptResponse(json, applicationConstantConfig.apiSignaturePublicPath);

			String encriptResponse = expensesTravelService.getExpenseBandList(tokengeneration.getToken(), jsonObject);

   
			EncriptResponse userReqEnc =EncryptionDecriptionUtil.convertFromJson(encriptResponse, EncriptResponse.class);

			profileRes =  EncryptionDecriptionUtil.decriptResponse(userReqEnc.getEncriptData(), userReqEnc.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
   
	return profileRes;
	
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
