package com.cotodel.hrms.web.controller;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cotodel.hrms.web.properties.ApplicationConstantConfig;
import com.cotodel.hrms.web.response.AdvanceTravelRequest;
import com.cotodel.hrms.web.response.ApprovalTravelReimbursement;
import com.cotodel.hrms.web.response.EmployeeMassterRequest;
import com.cotodel.hrms.web.response.ErupiLinkBankAccount;
import com.cotodel.hrms.web.response.ExpensesReimbursementRequest;
import com.cotodel.hrms.web.response.LinkMultipleAccountRequest;
import com.cotodel.hrms.web.response.TravelAdvanceRequestUpdate;
import com.cotodel.hrms.web.response.TravelReimbursement;
import com.cotodel.hrms.web.response.TravelRequest;
import com.cotodel.hrms.web.response.UserDetailsEntity;
import com.cotodel.hrms.web.service.ExpensesReimbursementService;
import com.cotodel.hrms.web.service.Impl.TokenGenerationImpl;
import com.cotodel.hrms.web.util.EncriptResponse;
import com.cotodel.hrms.web.util.EncryptionDecriptionUtil;
import com.cotodel.hrms.web.util.JwtTokenValidator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@CrossOrigin
public class ExpenseAdavacesReimbursementsController extends CotoDelBaseController{
	
	private static final Logger logger = LoggerFactory.getLogger(BulkUserController.class);
	private static final String SECRET_KEY = "0123456789012345"; // Must match frontend
    private static final String CLIENT_KEY = "client-secret-key"; // Extra validation

	
	@Autowired
	public ApplicationConstantConfig applicationConstantConfig;
	
	@Autowired
	TokenGenerationImpl tokengeneration;
	
	@Autowired
	ExpensesReimbursementService expensesReimbursementService;
	
	@PostMapping(value="/addExpenseReimbursementDraft")
	public @ResponseBody String saveExpensesReimbursementDraft(HttpServletRequest request,
		 ExpensesReimbursementRequest expensesReimbursementRequest, BindingResult result, HttpSession session, ModelMap model,Locale locale) {
	
		String profileRes=null;
		
		profileRes = expensesReimbursementService.saveExpensesReimbursementDraft(tokengeneration.getToken(),expensesReimbursementRequest);
		
		  return profileRes;
		  
	}
	
	@PostMapping(value="/addExpenseReimbursement")
	public @ResponseBody String saveExpensesReimbursement(HttpServletRequest request,
		 ExpensesReimbursementRequest expensesReimbursementRequest, BindingResult result, HttpSession session, ModelMap model,Locale locale) {
	
	
		///profileRes = expensesReimbursementService.saveExpensesReimbursement(tokengeneration.getToken(),expensesReimbursementRequest);
		
		String profileRes=null;
		
		String receivedHash = expensesReimbursementRequest.getHash();
		 if (!CLIENT_KEY.equals(expensesReimbursementRequest.getClientKey())) {
	          //  return Map.of("isValid", false, "message", "Invalid client key");
	        }


	        // Ensure consistent concatenation
	        String dataString = expensesReimbursementRequest.getEmployerId()+expensesReimbursementRequest.getExpenseCategory()
	        +expensesReimbursementRequest.getDateOfExpense()+expensesReimbursementRequest.getExpenseTitle()+expensesReimbursementRequest.getVendorName()+
	        expensesReimbursementRequest.getInvoiceNumber()+expensesReimbursementRequest.getCurrency()+expensesReimbursementRequest.getAmount()+expensesReimbursementRequest.getModeOfPayment()
	        +expensesReimbursementRequest.getRemarks()+expensesReimbursementRequest.getEmployeeId()+CLIENT_KEY+SECRET_KEY;
	        //+expensesReimbursementRequest.getFileInput()+expensesReimbursementRequest.getFileType()
	       

	        // Compute hash
	        String computedHash = null;
			try {
				computedHash = generateHash(dataString);
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			  boolean isValid = computedHash.equals(receivedHash);
			    Map<String, Object> responseMap = new HashMap<>();

			    ObjectMapper mapper = new ObjectMapper();
			    
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
    if ((obj.getUser_role() == 2 || obj.getUser_role() == 1) && obj.getOrgid() == expensesReimbursementRequest.getEmployerId().intValue()) {
		
		try {
			
			profileRes = expensesReimbursementService.saveExpensesReimbursement(tokengeneration.getToken(),expensesReimbursementRequest);
			
          JSONObject apiJsonResponse = new JSONObject(profileRes);
            
            // Process API Response
            if (apiJsonResponse.getBoolean("status")) {
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

	
	@GetMapping(value = "/getExpanseReimbursement")
	public @ResponseBody String getExpanseReimbursement(HttpServletRequest request, ModelMap model, Locale locale,
			HttpSession session, ExpensesReimbursementRequest expensesReimbursementRequest) {
		String profileRes = null;
		//profileRes = expensesReimbursementService.getExpanseReimbursement(tokengeneration.getToken(),
		//		expensesReimbursementRequest);
		try {
			String json = EncryptionDecriptionUtil.convertToJson(expensesReimbursementRequest);

			EncriptResponse jsonObject=EncryptionDecriptionUtil.encriptResponse(json, applicationConstantConfig.apiSignaturePublicPath);

			String encriptResponse = expensesReimbursementService.getExpanseReimbursement(tokengeneration.getToken(), jsonObject);

   
			EncriptResponse userReqEnc =EncryptionDecriptionUtil.convertFromJson(encriptResponse, EncriptResponse.class);

			profileRes =  EncryptionDecriptionUtil.decriptResponse(userReqEnc.getEncriptData(), userReqEnc.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
   
	return profileRes;
	}
	
	@GetMapping(value = "/getExpanseReimbursementApprovalList")
	public @ResponseBody String getExpanseReimbursementApprovalList(HttpServletRequest request, ModelMap model, Locale locale,
			HttpSession session, ExpensesReimbursementRequest expensesReimbursementRequest) {
		String profileRes = null;
		//profileRes = expensesReimbursementService.getExpanseReimbursementApprovalList(tokengeneration.getToken(),
		//		expensesReimbursementRequest);
		try {
			String json = EncryptionDecriptionUtil.convertToJson(expensesReimbursementRequest);

			EncriptResponse jsonObject=EncryptionDecriptionUtil.encriptResponse(json, applicationConstantConfig.apiSignaturePublicPath);

			String encriptResponse = expensesReimbursementService.getExpanseReimbursementApprovalList(tokengeneration.getToken(), jsonObject);

   
			EncriptResponse userReqEnc =EncryptionDecriptionUtil.convertFromJson(encriptResponse, EncriptResponse.class);

			profileRes =  EncryptionDecriptionUtil.decriptResponse(userReqEnc.getEncriptData(), userReqEnc.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
   
	return profileRes;
	}
	
	@PostMapping(value = "/viewExpenseReimbursement")
	public @ResponseBody String viewExpenseReimbursement(HttpServletRequest request, ModelMap model, Locale locale,
			HttpSession session, ExpensesReimbursementRequest expensesReimbursementRequest) {
		String profileRes = null;
	
		//profileRes = expensesReimbursementService.viewExpenseReimbursement(tokengeneration.getToken(), expensesReimbursementRequest);
		try {
			String json = EncryptionDecriptionUtil.convertToJson(expensesReimbursementRequest);

			EncriptResponse jsonObject=EncryptionDecriptionUtil.encriptResponse(json, applicationConstantConfig.apiSignaturePublicPath);

			String encriptResponse = expensesReimbursementService.viewExpenseReimbursement(tokengeneration.getToken(), jsonObject);

   
			EncriptResponse userReqEnc =EncryptionDecriptionUtil.convertFromJson(encriptResponse, EncriptResponse.class);

			profileRes =  EncryptionDecriptionUtil.decriptResponse(userReqEnc.getEncriptData(), userReqEnc.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
   
	return profileRes;

	}
	
	@PostMapping(value = "/getExpensesReimbursementDetailById")
	public @ResponseBody String getExpensesReimbursementDetailById(HttpServletRequest request, ModelMap model, Locale locale,
			HttpSession session, ExpensesReimbursementRequest expensesReimbursementRequest) {
		String profileRes = null;
	
		//profileRes = expensesReimbursementService.getExpensesReimbursementDetailById(tokengeneration.getToken(), expensesReimbursementRequest);
		try {
			String json = EncryptionDecriptionUtil.convertToJson(expensesReimbursementRequest);

			EncriptResponse jsonObject=EncryptionDecriptionUtil.encriptResponse(json, applicationConstantConfig.apiSignaturePublicPath);

			String encriptResponse = expensesReimbursementService.getExpensesReimbursementDetailById(tokengeneration.getToken(), jsonObject);

   
			EncriptResponse userReqEnc =EncryptionDecriptionUtil.convertFromJson(encriptResponse, EncriptResponse.class);

			profileRes =  EncryptionDecriptionUtil.decriptResponse(userReqEnc.getEncriptData(), userReqEnc.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	   return profileRes;
	}
	
	@PostMapping(value = "/deleteExpanseReimbursement")
	public @ResponseBody String deleteExpanseReimbursement(HttpServletRequest request, ModelMap model, Locale locale,
			HttpSession session, ExpensesReimbursementRequest expensesReimbursementRequest) {
		String profileRes = null;
	
		//profileRes = expensesReimbursementService.deleteExpanseReimbursement(tokengeneration.getToken(),expensesReimbursementRequest);
		try {
			String json = EncryptionDecriptionUtil.convertToJson(expensesReimbursementRequest);

			EncriptResponse jsonObject=EncryptionDecriptionUtil.encriptResponse(json, applicationConstantConfig.apiSignaturePublicPath);

			String encriptResponse = expensesReimbursementService.deleteExpanseReimbursement(tokengeneration.getToken(), jsonObject);

   
			EncriptResponse userReqEnc =EncryptionDecriptionUtil.convertFromJson(encriptResponse, EncriptResponse.class);

			profileRes =  EncryptionDecriptionUtil.decriptResponse(userReqEnc.getEncriptData(), userReqEnc.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
   
	   return profileRes;
	}
	
	@PostMapping(value="/addErupiLinkBankAccount")
	public @ResponseBody String erupiLinkBankAccount(HttpServletRequest request,
			ErupiLinkBankAccount erupiLinkBankAccount, BindingResult result, HttpSession session, ModelMap model,Locale locale) {
	
		String profileRes=null;
		String receivedHash = erupiLinkBankAccount.getHash();
		 // Validate client key first
        if (!CLIENT_KEY.equals(erupiLinkBankAccount.getClientKey())) {
          //  return Map.of("isValid", false, "message", "Invalid client key");
        }
        // Ensure consistent concatenation
        String dataString = 
        		erupiLinkBankAccount.getOrgId()+
        		erupiLinkBankAccount.getBankName()+
        		erupiLinkBankAccount.getAccountHolderName()+
        		erupiLinkBankAccount.getAcNumber()+
        		erupiLinkBankAccount.getConirmAccNumber()+
        		erupiLinkBankAccount.getAccountType()+
        		erupiLinkBankAccount.getIfsc()+
				erupiLinkBankAccount.getMobile()+
				erupiLinkBankAccount.getMerchentIid()+
				erupiLinkBankAccount.getSubmurchentid()+
				erupiLinkBankAccount.getPayerva()+
				CLIENT_KEY+SECRET_KEY;

        // Compute hash
        String computedHash = null;
		try {
			computedHash = generateHash(dataString);
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
    if ((obj.getUser_role() == 9 || obj.getUser_role() == 1 || obj.getUser_role() == 3) && obj.getOrgid() == erupiLinkBankAccount.getOrgId().intValue()) {
        try {
            // Convert request object to JSON
            String json = EncryptionDecriptionUtil.convertToJson(erupiLinkBankAccount);

            // Encrypt Request
            EncriptResponse jsonObject = EncryptionDecriptionUtil.encriptResponse(json, applicationConstantConfig.apiSignaturePublicPath);

            // Call Service
            String encryptedResponse =  expensesReimbursementService.erupiLinkBankAccount(tokengeneration.getToken(),jsonObject);

            // Decrypt Response
            EncriptResponse userReqEnc = EncryptionDecriptionUtil.convertFromJson(encryptedResponse, EncriptResponse.class);
            String apiResponse = EncryptionDecriptionUtil.decriptResponse(
                    userReqEnc.getEncriptData(), 
                    userReqEnc.getEncriptKey(), 
                    applicationConstantConfig.apiSignaturePrivatePath
            );

            JSONObject apiJsonResponse = new JSONObject(apiResponse);
            
            // Process API Response
            if (apiJsonResponse.getBoolean("status")) {
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
	
	@PostMapping(value="/updateErupiLinkBankAccountStaus")
	public @ResponseBody String updateErupiLinkBankAccountStaus(HttpServletRequest request,
			ErupiLinkBankAccount erupiLinkBankAccount, BindingResult result, HttpSession session, ModelMap model,Locale locale) {
	
		String profileRes=null;
		
		//profileRes = expensesReimbursementService.updateErupiLinkBankAccountStaus(tokengeneration.getToken(),erupiLinkBankAccount);
		
		//  return profileRes;
		
		
		try {
			String json = EncryptionDecriptionUtil.convertToJson(erupiLinkBankAccount);

			EncriptResponse jsonObject=EncryptionDecriptionUtil.encriptResponse(json, applicationConstantConfig.apiSignaturePublicPath);

			String encriptResponse =   expensesReimbursementService.updateErupiLinkBankAccountStaus(tokengeneration.getToken(), jsonObject);

   
			EncriptResponse userReqEnc =EncryptionDecriptionUtil.convertFromJson(encriptResponse, EncriptResponse.class);

			profileRes =  EncryptionDecriptionUtil.decriptResponse(userReqEnc.getEncriptData(), userReqEnc.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
   
	return profileRes;
		  
	}
	
	@PostMapping(value="/getErupiLinkBankAccountDetail")
	public @ResponseBody String getErupiLinkBankAccountDetail(HttpServletRequest request,
			EmployeeMassterRequest erupiLinkBankAccount, BindingResult result, HttpSession session, ModelMap model,Locale locale) {
		
		String profileRes=null;
		
		try {
			String json = EncryptionDecriptionUtil.convertToJson(erupiLinkBankAccount);

			EncriptResponse jsonObject=EncryptionDecriptionUtil.encriptResponse(json, applicationConstantConfig.apiSignaturePublicPath);

			String encriptResponse =  expensesReimbursementService.getErupiLinkBankAccountDetail(tokengeneration.getToken(), jsonObject);

   
			EncriptResponse userReqEnc =EncryptionDecriptionUtil.convertFromJson(encriptResponse, EncriptResponse.class);

			profileRes =  EncryptionDecriptionUtil.decriptResponse(userReqEnc.getEncriptData(), userReqEnc.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
   
	return profileRes;
		
	}
	
	@PostMapping(value="/getErupiLinkDlinkAccountDetail")
	public @ResponseBody String getErupiLinkDlinkAccountDetail(HttpServletRequest request,
			ErupiLinkBankAccount erupiLinkBankAccount, BindingResult result, HttpSession session, ModelMap model,Locale locale) {
	
			String profileRes=null;
			String receivedHash = erupiLinkBankAccount.getHash();
			 if (!CLIENT_KEY.equals(erupiLinkBankAccount.getClientKey())) {
		          // return Map.of("isValid", false, "message", "Invalid client key");
		        }
		        // Ensure consistent concatenation
		        String dataString = erupiLinkBankAccount.getOrgId()+CLIENT_KEY+SECRET_KEY;

		        // Compute hash
		        String computedHash = null;
				try {
					computedHash = generateHash(dataString);
				} catch (NoSuchAlgorithmException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				  boolean isValid = computedHash.equals(receivedHash);
				    Map<String, Object> responseMap = new HashMap<>();
				    ObjectMapper mapper = new ObjectMapper();
				    
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
	    if ((obj.getUser_role() == 9 || obj.getUser_role() == 1) && obj.getOrgid() == erupiLinkBankAccount.getOrgId().intValue()) {
			try {
				String json = EncryptionDecriptionUtil.convertToJson(erupiLinkBankAccount);

				EncriptResponse jsonObject=EncryptionDecriptionUtil.encriptResponse(json, applicationConstantConfig.apiSignaturePublicPath);

				String encriptResponse =  expensesReimbursementService.getErupiLinkDlinkAccountDetail(tokengeneration.getToken(), jsonObject);

	   
				EncriptResponse userReqEnc =EncryptionDecriptionUtil.convertFromJson(encriptResponse, EncriptResponse.class);

				String apiResponse = EncryptionDecriptionUtil.decriptResponse(
	                    userReqEnc.getEncriptData(), 
	                    userReqEnc.getEncriptKey(), 
	                    applicationConstantConfig.apiSignaturePrivatePath
	            );

	            JSONObject apiJsonResponse = new JSONObject(apiResponse);
	            
	            // Process API Response
	            if (apiJsonResponse.getBoolean("status")) {
	                responseMap.put("status", true);
	                responseMap.put("message", apiJsonResponse.getString("message"));
	                responseMap.put("data", apiJsonResponse.getJSONArray("data").toList()); 
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
	
	
	@PostMapping(value="/de-linkErupiaccount")
	public @ResponseBody String delinkErupiaccount(HttpServletRequest request,
			ErupiLinkBankAccount erupiLinkBankAccount, BindingResult result, HttpSession session, ModelMap model,Locale locale) {
	
			String profileRes=null;
			String receivedHash = erupiLinkBankAccount.getHash();
			 if (!CLIENT_KEY.equals(erupiLinkBankAccount.getClientKey())) {
		          //  return Map.of("isValid", false, "message", "Invalid client key");
		        }
		        // Ensure consistent concatenation
		        String dataString = erupiLinkBankAccount.getOrgId()+erupiLinkBankAccount.getAcNumber()+CLIENT_KEY+SECRET_KEY;

		        // Compute hash
		        String computedHash = null;
				try {
					computedHash = generateHash(dataString);
				} catch (NoSuchAlgorithmException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				  boolean isValid = computedHash.equals(receivedHash);
				    Map<String, Object> responseMap = new HashMap<>();
				    ObjectMapper mapper = new ObjectMapper();
				    
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
	    if ((obj.getUser_role() == 9 || obj.getUser_role() == 1) && obj.getOrgid() == erupiLinkBankAccount.getOrgId().intValue()) {
			
			try {
				String json = EncryptionDecriptionUtil.convertToJson(erupiLinkBankAccount);

				EncriptResponse jsonObject=EncryptionDecriptionUtil.encriptResponse(json, applicationConstantConfig.apiSignaturePublicPath);

				String encriptResponse =  expensesReimbursementService.delinkErupiAccount(tokengeneration.getToken(), jsonObject);

	   
				EncriptResponse userReqEnc =EncryptionDecriptionUtil.convertFromJson(encriptResponse, EncriptResponse.class);

				String apiResponse = EncryptionDecriptionUtil.decriptResponse(
	                    userReqEnc.getEncriptData(), 
	                    userReqEnc.getEncriptKey(), 
	                    applicationConstantConfig.apiSignaturePrivatePath
	            );

	            JSONObject apiJsonResponse = new JSONObject(apiResponse);
	            
	            // Process API Response
	            if (apiJsonResponse.getBoolean("status")) {
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
	@PostMapping(value="/re-linkErupiaccount")
	public @ResponseBody String relinkErupiaccount(HttpServletRequest request,
			ErupiLinkBankAccount erupiLinkBankAccount, BindingResult result, HttpSession session, ModelMap model,Locale locale) {
			
			String receivedHash = erupiLinkBankAccount.getHash();
			 if (!CLIENT_KEY.equals(erupiLinkBankAccount.getClientKey())) {
		          //  return Map.of("isValid", false, "message", "Invalid client key");
		        }
		        // Ensure consistent concatenation
		        String dataString = erupiLinkBankAccount.getOrgId()+erupiLinkBankAccount.getAcNumber()+CLIENT_KEY+SECRET_KEY;

		        // Compute hash
		        String computedHash = null;
				try {
					computedHash = generateHash(dataString);
				} catch (NoSuchAlgorithmException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				  boolean isValid = computedHash.equals(receivedHash);
				    Map<String, Object> responseMap = new HashMap<>();
				    ObjectMapper mapper = new ObjectMapper();
				    
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
	    if ((obj.getUser_role() == 9 || obj.getUser_role() == 1) && obj.getOrgid() == erupiLinkBankAccount.getOrgId().intValue()) {
			
			try {
				String json = EncryptionDecriptionUtil.convertToJson(erupiLinkBankAccount);

				EncriptResponse jsonObject=EncryptionDecriptionUtil.encriptResponse(json, applicationConstantConfig.apiSignaturePublicPath);

				String encriptResponse =  expensesReimbursementService.relinkErupiAccount(tokengeneration.getToken(), jsonObject);

	   
				EncriptResponse userReqEnc =EncryptionDecriptionUtil.convertFromJson(encriptResponse, EncriptResponse.class);

				String apiResponse = EncryptionDecriptionUtil.decriptResponse(
	                    userReqEnc.getEncriptData(), 
	                    userReqEnc.getEncriptKey(), 
	                    applicationConstantConfig.apiSignaturePrivatePath
	            );

	            JSONObject apiJsonResponse = new JSONObject(apiResponse);
	            
	            // Process API Response
	            if (apiJsonResponse.getBoolean("status")) {
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
	
	@PostMapping(value="/updateStatusExpensesById")
	public @ResponseBody String approveExpensesById(HttpServletRequest request,
		 ExpensesReimbursementRequest expensesReimbursementRequest, BindingResult result, HttpSession session, ModelMap model,Locale locale) {
	
		String profileRes=null;
		
		//profileRes = expensesReimbursementService.approveExpensesById(tokengeneration.getToken(),expensesReimbursementRequest);
		try {
			String json = EncryptionDecriptionUtil.convertToJson(expensesReimbursementRequest);

			EncriptResponse jsonObject=EncryptionDecriptionUtil.encriptResponse(json, applicationConstantConfig.apiSignaturePublicPath);

			String encriptResponse =  expensesReimbursementService.approveExpensesById(tokengeneration.getToken(), jsonObject);

   
			EncriptResponse userReqEnc =EncryptionDecriptionUtil.convertFromJson(encriptResponse, EncriptResponse.class);

			profileRes =  EncryptionDecriptionUtil.decriptResponse(userReqEnc.getEncriptData(), userReqEnc.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
   
	return profileRes;
	
		  
	}
	
	@PostMapping(value="/cashAdvanceRequest")
	public @ResponseBody String cashAdvanceRequest(HttpServletRequest request,
			AdvanceTravelRequest advanceTravelRequest, BindingResult result, HttpSession session, ModelMap model,Locale locale) {
	
//		String profileRes=null;
//		
//		//profileRes = expensesReimbursementService.cashAdvanceRequest(tokengeneration.getToken(),advanceTravelRequest);
//		try {
//			String json = EncryptionDecriptionUtil.convertToJson(advanceTravelRequest);
//
//			EncriptResponse jsonObject=EncryptionDecriptionUtil.encriptResponse(json, applicationConstantConfig.apiSignaturePublicPath);
//
//			String encriptResponse =  expensesReimbursementService.cashAdvanceRequest(tokengeneration.getToken(), jsonObject);
//
//   
//			EncriptResponse userReqEnc =EncryptionDecriptionUtil.convertFromJson(encriptResponse, EncriptResponse.class);
//
//			profileRes =  EncryptionDecriptionUtil.decriptResponse(userReqEnc.getEncriptData(), userReqEnc.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//   
//	return profileRes;
//	

		String receivedHash = advanceTravelRequest.getHash();
		 if (!CLIENT_KEY.equals(advanceTravelRequest.getClientKey())) {
	          //  return Map.of("isValid", false, "message", "Invalid client key");
	        }
		 
		// const dataString = empId+employerid+employerName+"Cash"+cashDate+
		//			""+amount+cashCurrency+cashAmmount+cashModeOfPayment+cashRemark+clientKey+secretKey;
		//			console.log("data string"+dataString); 
	        // Ensure consistent concatenation
	        String dataString = advanceTravelRequest.getUsername()
	        +"Cash"+advanceTravelRequest.getCashDate()+advanceTravelRequest.getAmount()+advanceTravelRequest.getCurrency()+
	        advanceTravelRequest.getAmount()+advanceTravelRequest.getModeOfPayment()+
	        advanceTravelRequest.getRemarks()+CLIENT_KEY+SECRET_KEY;

	        // Compute hash
	        String computedHash = null;
			try {
				computedHash = generateHash(dataString);
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			  boolean isValid = computedHash.equals(receivedHash);
			    Map<String, Object> responseMap = new HashMap<>();
			    ObjectMapper mapper = new ObjectMapper();
			    
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
    if ((obj.getUser_role() == 2 || obj.getUser_role() == 1) && obj.getOrgid() == advanceTravelRequest.getEmployerId().intValue()) {
		
		try {
			String json = EncryptionDecriptionUtil.convertToJson(advanceTravelRequest);

			EncriptResponse jsonObject=EncryptionDecriptionUtil.encriptResponse(json, applicationConstantConfig.apiSignaturePublicPath);

			String encriptResponse =  expensesReimbursementService.cashAdvanceRequest(tokengeneration.getToken(), jsonObject);

   
			EncriptResponse userReqEnc =EncryptionDecriptionUtil.convertFromJson(encriptResponse, EncriptResponse.class);

			String apiResponse = EncryptionDecriptionUtil.decriptResponse(
                    userReqEnc.getEncriptData(), 
                    userReqEnc.getEncriptKey(), 
                    applicationConstantConfig.apiSignaturePrivatePath
            );

            JSONObject apiJsonResponse = new JSONObject(apiResponse);
            
            // Process API Response
            if (apiJsonResponse.getBoolean("status")) {
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
	
	@GetMapping(value = "/getCashAdanceRequestData")
	public @ResponseBody String getCashAdanceRequestData(HttpServletRequest request, ModelMap model, Locale locale,
			HttpSession session, AdvanceTravelRequest advanceTravelRequest) {
		String profileRes = null;
		//profileRes = expensesReimbursementService.getCashAdanceRequestData(tokengeneration.getToken(),
		//		advanceTravelRequest);
		try {
			String json = EncryptionDecriptionUtil.convertToJson(advanceTravelRequest);

			EncriptResponse jsonObject=EncryptionDecriptionUtil.encriptResponse(json, applicationConstantConfig.apiSignaturePublicPath);

			String encriptResponse =  expensesReimbursementService.getCashAdanceRequestData(tokengeneration.getToken(), jsonObject);

   
			EncriptResponse userReqEnc =EncryptionDecriptionUtil.convertFromJson(encriptResponse, EncriptResponse.class);

			profileRes =  EncryptionDecriptionUtil.decriptResponse(userReqEnc.getEncriptData(), userReqEnc.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
   
	return profileRes;
	
	}
	
	@PostMapping(value="/travelAdvanceRequest")
	public @ResponseBody String travelAdvanceRequest(HttpServletRequest request,
			@RequestBody TravelRequest travelRequest, BindingResult result, HttpSession session, ModelMap model,Locale locale) {

//		String profileRes=null;
//		try {
//			String json = EncryptionDecriptionUtil.convertToJson(travelRequest);
//
//			EncriptResponse jsonObject=EncryptionDecriptionUtil.encriptResponse(json, applicationConstantConfig.apiSignaturePublicPath);
//
//			String encriptResponse =  expensesReimbursementService.travelAdvanceRequest(tokengeneration.getToken(), jsonObject);
//
//   
//			EncriptResponse userReqEnc =EncryptionDecriptionUtil.convertFromJson(encriptResponse, EncriptResponse.class);
//
//			profileRes =  EncryptionDecriptionUtil.decriptResponse(userReqEnc.getEncriptData(), userReqEnc.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//   
//	return profileRes;
		
		String receivedHash = travelRequest.getHash();
		 if (!CLIENT_KEY.equals(travelRequest.getClientKey())) {
	          //  return Map.of("isValid", false, "message", "Invalid client key");
	        }
		 
		// const dataString = empId+employerid+employerName+"Cash"+cashDate+
		//			""+amount+cashCurrency+cashAmmount+cashModeOfPayment+cashRemark+clientKey+secretKey;
		//			console.log("data string"+dataString); 
	        // Ensure consistent concatenation
		
	        String dataString = travelRequest.getEmployeeId()+travelRequest.getRequestType()+travelRequest.getEmployerId()
	        +CLIENT_KEY+SECRET_KEY;

	        // Compute hash
	        String computedHash = null;
			try {
				computedHash = generateHash(dataString);
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			  boolean isValid = computedHash.equals(receivedHash);
			    Map<String, Object> responseMap = new HashMap<>();
			    ObjectMapper mapper = new ObjectMapper();
			    
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
    if ((obj.getUser_role() == 2 || obj.getUser_role() == 1) && obj.getOrgid() == travelRequest.getEmployerId().intValue()) {
		
		try {
			String json = EncryptionDecriptionUtil.convertToJson(travelRequest);

			EncriptResponse jsonObject=EncryptionDecriptionUtil.encriptResponse(json, applicationConstantConfig.apiSignaturePublicPath);

			String encriptResponse =  expensesReimbursementService.travelAdvanceRequest(tokengeneration.getToken(), jsonObject);
   
			EncriptResponse userReqEnc =EncryptionDecriptionUtil.convertFromJson(encriptResponse, EncriptResponse.class);

			String apiResponse = EncryptionDecriptionUtil.decriptResponse(
                    userReqEnc.getEncriptData(), 
                    userReqEnc.getEncriptKey(), 
                    applicationConstantConfig.apiSignaturePrivatePath
            );

            JSONObject apiJsonResponse = new JSONObject(apiResponse);
            
            // Process API Response
            if (apiJsonResponse.getBoolean("status")) {
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
	
	@GetMapping(value = "/getTravelReviewData")
	public @ResponseBody String getTravelReviewData(HttpServletRequest request, ModelMap model, Locale locale,
			HttpSession session, AdvanceTravelRequest advanceTravelRequest) {
		String profileRes = null;
		//profileRes = expensesReimbursementService.getTravelReviewData(tokengeneration.getToken(),advanceTravelRequest);
		try {
			String json = EncryptionDecriptionUtil.convertToJson(advanceTravelRequest);

			EncriptResponse jsonObject=EncryptionDecriptionUtil.encriptResponse(json, applicationConstantConfig.apiSignaturePublicPath);

			String encriptResponse =  expensesReimbursementService.getTravelReviewData(tokengeneration.getToken(), jsonObject);

   
			EncriptResponse userReqEnc =EncryptionDecriptionUtil.convertFromJson(encriptResponse, EncriptResponse.class);

			profileRes =  EncryptionDecriptionUtil.decriptResponse(userReqEnc.getEncriptData(), userReqEnc.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
   
	return profileRes;
	}
	
	@GetMapping(value = "/advanceTravelById")
	public @ResponseBody String advanceTravelById(HttpServletRequest request, ModelMap model, Locale locale,
			HttpSession session, TravelRequest advanceTravelRequest) {
		String profileRes = null;
		//profileRes = expensesReimbursementService.getTravelReviewData(tokengeneration.getToken(),advanceTravelRequest);
		try {
			String json = EncryptionDecriptionUtil.convertToJson(advanceTravelRequest);

			EncriptResponse jsonObject=EncryptionDecriptionUtil.encriptResponse(json, applicationConstantConfig.apiSignaturePublicPath);

			String encriptResponse =  expensesReimbursementService.advanceTravelById(tokengeneration.getToken(), jsonObject);

   
			EncriptResponse userReqEnc =EncryptionDecriptionUtil.convertFromJson(encriptResponse, EncriptResponse.class);

			profileRes =  EncryptionDecriptionUtil.decriptResponse(userReqEnc.getEncriptData(), userReqEnc.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
   
	return profileRes;
	}
	
	@PostMapping(value="/travelAdvanceRequestUpdate")
	public @ResponseBody String travelAdvanceRequestUpdate(HttpServletRequest request,
			@RequestBody TravelAdvanceRequestUpdate travelAdvanceRequestUpdate,BindingResult result, HttpSession session, ModelMap model,Locale locale) {

		String profileRes=null;
		//profileRes = expensesReimbursementService.travelAdvanceRequestUpdate(tokengeneration.getToken(),travelAdvanceRequestUpdate);	
		try {
			String json = EncryptionDecriptionUtil.convertToJson(travelAdvanceRequestUpdate);

			EncriptResponse jsonObject=EncryptionDecriptionUtil.encriptResponse(json, applicationConstantConfig.apiSignaturePublicPath);

			String encriptResponse =  expensesReimbursementService.travelAdvanceRequestUpdate(tokengeneration.getToken(), jsonObject);

   
			EncriptResponse userReqEnc =EncryptionDecriptionUtil.convertFromJson(encriptResponse, EncriptResponse.class);

			profileRes =  EncryptionDecriptionUtil.decriptResponse(userReqEnc.getEncriptData(), userReqEnc.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
   
	return profileRes;
	}
	
	
	@PostMapping(value = "/deleteAdvanceTravel")
	public @ResponseBody String deleteAdvanceTravel(HttpServletRequest request, ModelMap model, Locale locale,
			HttpSession session, TravelReimbursement travelReimbursement) {
		String profileRes = null;
	
		//profileRes = expensesReimbursementService.deleteAdvanceTravel(tokengeneration.getToken(),travelReimbursement);
		try {
			String json = EncryptionDecriptionUtil.convertToJson(travelReimbursement);

			EncriptResponse jsonObject=EncryptionDecriptionUtil.encriptResponse(json, applicationConstantConfig.apiSignaturePublicPath);

			String encriptResponse =  expensesReimbursementService.deleteAdvanceTravel(tokengeneration.getToken(), jsonObject);

   
			EncriptResponse userReqEnc =EncryptionDecriptionUtil.convertFromJson(encriptResponse, EncriptResponse.class);

			profileRes =  EncryptionDecriptionUtil.decriptResponse(userReqEnc.getEncriptData(), userReqEnc.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
   
	return profileRes;

	}
	
	@GetMapping(value = "/getAdanceCashTravelApprpvalList")
	public @ResponseBody String getAdanceCashTravelApprpvalList(HttpServletRequest request, ModelMap model, Locale locale,
			HttpSession session, AdvanceTravelRequest advanceTravelRequest) {
		String profileRes = null;
		//profileRes = expensesReimbursementService.getAdanceCashTravelApprpvalList(tokengeneration.getToken(),advanceTravelRequest);

		try {
			String json = EncryptionDecriptionUtil.convertToJson(advanceTravelRequest);

			EncriptResponse jsonObject=EncryptionDecriptionUtil.encriptResponse(json, applicationConstantConfig.apiSignaturePublicPath);

			String encriptResponse =   expensesReimbursementService.getAdanceCashTravelApprpvalList(tokengeneration.getToken(), jsonObject);

   
			EncriptResponse userReqEnc =EncryptionDecriptionUtil.convertFromJson(encriptResponse, EncriptResponse.class);

			profileRes =  EncryptionDecriptionUtil.decriptResponse(userReqEnc.getEncriptData(), userReqEnc.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
   
	return profileRes;
	}
	
	@PostMapping(value = "/approveAdvanceTravelRequest")
	public @ResponseBody String approveAdvanceTravelRequest(HttpServletRequest request, ModelMap model, Locale locale,
			HttpSession session, ApprovalTravelReimbursement advanceTravelRequest) {
		String profileRes = null;
		//profileRes = expensesReimbursementService.approveAdvanceTravelRequest(tokengeneration.getToken(),advanceTravelRequest);

		try {
			String json = EncryptionDecriptionUtil.convertToJson(advanceTravelRequest);

			EncriptResponse jsonObject=EncryptionDecriptionUtil.encriptResponse(json, applicationConstantConfig.apiSignaturePublicPath);

			String encriptResponse =    expensesReimbursementService.approveAdvanceTravelRequest(tokengeneration.getToken(), jsonObject);

   
			EncriptResponse userReqEnc =EncryptionDecriptionUtil.convertFromJson(encriptResponse, EncriptResponse.class);

			profileRes =  EncryptionDecriptionUtil.decriptResponse(userReqEnc.getEncriptData(), userReqEnc.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
   
	return profileRes;
	}
	
	@GetMapping(value = "/getCashAdvanceDetailById")
	public @ResponseBody String getCashAdvanceDetailById(HttpServletRequest request, ModelMap model, Locale locale,
			HttpSession session, AdvanceTravelRequest advanceTravelRequest) {
		String profileRes = null;
		//profileRes = expensesReimbursementService.getCashAdvanceDetailById(tokengeneration.getToken(),advanceTravelRequest);
		try {
			String json = EncryptionDecriptionUtil.convertToJson(advanceTravelRequest);

			EncriptResponse jsonObject=EncryptionDecriptionUtil.encriptResponse(json, applicationConstantConfig.apiSignaturePublicPath);

			String encriptResponse =  expensesReimbursementService.getCashAdvanceDetailById(tokengeneration.getToken(), jsonObject);

   
			EncriptResponse userReqEnc =EncryptionDecriptionUtil.convertFromJson(encriptResponse, EncriptResponse.class);

			profileRes =  EncryptionDecriptionUtil.decriptResponse(userReqEnc.getEncriptData(), userReqEnc.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
   
	return profileRes;
	}
	
	@PostMapping(value = "/getErupiLinkAccountDetails")
	public @ResponseBody String getErupiLinkAccountDetails(HttpServletRequest request, ModelMap model, Locale locale,
			HttpSession session, ErupiLinkBankAccount erupiLinkBankAccount) {
		String profileRes = null;
		//profileRes = expensesReimbursementService.getExpanseReimbursementApprovalList(tokengeneration.getToken(),
		//		expensesReimbursementRequest);
		try {
			String json = EncryptionDecriptionUtil.convertToJson(erupiLinkBankAccount);

			EncriptResponse jsonObject=EncryptionDecriptionUtil.encriptResponse(json, applicationConstantConfig.apiSignaturePublicPath);

			String encriptResponse = expensesReimbursementService.getErupiLinkAccountDetails(tokengeneration.getToken(), jsonObject);

   
			EncriptResponse userReqEnc =EncryptionDecriptionUtil.convertFromJson(encriptResponse, EncriptResponse.class);

			profileRes =  EncryptionDecriptionUtil.decriptResponse(userReqEnc.getEncriptData(), userReqEnc.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
   
	return profileRes;
	}
	
	@PostMapping(value = "/submitCotodelDetails")
	public @ResponseBody String saveCotodelbankDetails(HttpServletRequest request, ModelMap model, Locale locale,
			HttpSession session,  LinkMultipleAccountRequest linkMultipleAccountRequest) {
		String profileRes = null;
		//profileRes = expensesReimbursementService.getExpanseReimbursementApprovalList(tokengeneration.getToken(),
		//		expensesReimbursementRequest);
		try {
			String json = EncryptionDecriptionUtil.convertToJson(linkMultipleAccountRequest);

			EncriptResponse jsonObject=EncryptionDecriptionUtil.encriptResponse(json, applicationConstantConfig.apiSignaturePublicPath);

			String encriptResponse = expensesReimbursementService.SaveCotodelbankDetails(tokengeneration.getToken(), jsonObject);

   
			EncriptResponse userReqEnc =EncryptionDecriptionUtil.convertFromJson(encriptResponse, EncriptResponse.class);

			profileRes =  EncryptionDecriptionUtil.decriptResponse(userReqEnc.getEncriptData(), userReqEnc.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
   
	return profileRes;
	}
	
	@GetMapping(value = "/getsubmitedCDetails")
	public @ResponseBody String getSavedCBankDetails(HttpServletRequest request, ModelMap model, Locale locale,
			HttpSession session,  LinkMultipleAccountRequest linkMultipleAccountRequest) {
		String profileRes = null;
		//profileRes = expensesReimbursementService.getExpanseReimbursementApprovalList(tokengeneration.getToken(),
		//		expensesReimbursementRequest);
		try {
			String json = EncryptionDecriptionUtil.convertToJson(linkMultipleAccountRequest);

			EncriptResponse jsonObject=EncryptionDecriptionUtil.encriptResponse(json, applicationConstantConfig.apiSignaturePublicPath);

			String encriptResponse = expensesReimbursementService.getSavedCBankDetails(tokengeneration.getToken(), jsonObject);

   
			EncriptResponse userReqEnc =EncryptionDecriptionUtil.convertFromJson(encriptResponse, EncriptResponse.class);

			profileRes =  EncryptionDecriptionUtil.decriptResponse(userReqEnc.getEncriptData(), userReqEnc.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
   
	return profileRes;
	}
	
	@PostMapping(value = "/cApproveReject")
	public @ResponseBody String cApproveReject(HttpServletRequest request, ModelMap model, Locale locale,
			HttpSession session,  LinkMultipleAccountRequest linkMultipleAccountRequest) {
		String profileRes = null;
		//profileRes = expensesReimbursementService.getExpanseReimbursementApprovalList(tokengeneration.getToken(),
		//		expensesReimbursementRequest);
		try {
			String json = EncryptionDecriptionUtil.convertToJson(linkMultipleAccountRequest);

			EncriptResponse jsonObject=EncryptionDecriptionUtil.encriptResponse(json, applicationConstantConfig.apiSignaturePublicPath);

			String encriptResponse = expensesReimbursementService.cApproveReject(tokengeneration.getToken(), jsonObject);

   
			EncriptResponse userReqEnc =EncryptionDecriptionUtil.convertFromJson(encriptResponse, EncriptResponse.class);

			profileRes =  EncryptionDecriptionUtil.decriptResponse(userReqEnc.getEncriptData(), userReqEnc.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
   
	return profileRes;
	}
	
	@PostMapping(value = "/showLinkedAccAmount")
	public @ResponseBody String showLinkedAccAmount(HttpServletRequest request, ModelMap model, Locale locale,
			HttpSession session,  LinkMultipleAccountRequest linkMultipleAccountRequest) {
		String profileRes = null;
		//profileRes = expensesReimbursementService.getExpanseReimbursementApprovalList(tokengeneration.getToken(),
		//		expensesReimbursementRequest);
		try {
			String json = EncryptionDecriptionUtil.convertToJson(linkMultipleAccountRequest);

			EncriptResponse jsonObject=EncryptionDecriptionUtil.encriptResponse(json, applicationConstantConfig.apiSignaturePublicPath);

			String encriptResponse = expensesReimbursementService.showLinkedAccAmount(tokengeneration.getToken(), jsonObject);

   
			EncriptResponse userReqEnc =EncryptionDecriptionUtil.convertFromJson(encriptResponse, EncriptResponse.class);

			profileRes =  EncryptionDecriptionUtil.decriptResponse(userReqEnc.getEncriptData(), userReqEnc.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
   
	return profileRes;
	}
	
	
	@GetMapping(value = "/getTravelRequestApprovalList")
	public @ResponseBody String getTravelRequestApprovalList(HttpServletRequest request, ModelMap model, Locale locale,
			HttpSession session, AdvanceTravelRequest travelRequest) {
		String profileRes = null;
		try {
			String json = EncryptionDecriptionUtil.convertToJson(travelRequest);

			EncriptResponse jsonObject=EncryptionDecriptionUtil.encriptResponse(json, applicationConstantConfig.apiSignaturePublicPath);

			String encriptResponse = expensesReimbursementService.getTravelRequestApprovalList(tokengeneration.getToken(), jsonObject);

   
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
