package com.cotodel.hrms.web.controller;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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
import com.cotodel.hrms.web.response.EmployeeMassterRequest;
import com.cotodel.hrms.web.response.ErupiLinkBankAccount;
import com.cotodel.hrms.web.response.ExpensesReimbursementRequest;
import com.cotodel.hrms.web.response.LinkMultipleAccountRequest;
import com.cotodel.hrms.web.response.TravelAdvanceRequestUpdate;
import com.cotodel.hrms.web.response.TravelReimbursement;
import com.cotodel.hrms.web.response.TravelRequest;
import com.cotodel.hrms.web.service.ExpensesReimbursementService;
import com.cotodel.hrms.web.service.Impl.TokenGenerationImpl;
import com.cotodel.hrms.web.util.EncriptResponse;
import com.cotodel.hrms.web.util.EncryptionDecriptionUtil;

@Controller
@CrossOrigin
public class ExpenseAdavacesReimbursementsController extends CotoDelBaseController{
	
	private static final Logger logger = LoggerFactory.getLogger(BulkUserController.class);

	
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
	
		String profileRes=null;
		
		profileRes = expensesReimbursementService.saveExpensesReimbursement(tokengeneration.getToken(),expensesReimbursementRequest);
		
		  return profileRes;
		  
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
		
	//	profileRes = expensesReimbursementService.erupiLinkBankAccount(tokengeneration.getToken(),erupiLinkBankAccount);
		
		//  return profileRes;
		

		try {
			String json = EncryptionDecriptionUtil.convertToJson(erupiLinkBankAccount);

			EncriptResponse jsonObject=EncryptionDecriptionUtil.encriptResponse(json, applicationConstantConfig.apiSignaturePublicPath);

			String encriptResponse =  expensesReimbursementService.erupiLinkBankAccount(tokengeneration.getToken(),jsonObject);
   
			EncriptResponse userReqEnc =EncryptionDecriptionUtil.convertFromJson(encriptResponse, EncriptResponse.class);

			profileRes =  EncryptionDecriptionUtil.decriptResponse(userReqEnc.getEncriptData(), userReqEnc.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
       
		return profileRes;
		  
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
	
			//String profileRes=null;
			
			//profileRes = expensesReimbursementService.getErupiLinkBankAccountDetail(tokengeneration.getToken(),erupiLinkBankAccount);
			
			//return profileRes;
		
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
			
			//profileRes = expensesReimbursementService.getErupiLinkDlinkAccountDetail(tokengeneration.getToken(),erupiLinkBankAccount);
			
			///return profileRes;
			
			try {
				String json = EncryptionDecriptionUtil.convertToJson(erupiLinkBankAccount);

				EncriptResponse jsonObject=EncryptionDecriptionUtil.encriptResponse(json, applicationConstantConfig.apiSignaturePublicPath);

				String encriptResponse =  expensesReimbursementService.getErupiLinkDlinkAccountDetail(tokengeneration.getToken(), jsonObject);

	   
				EncriptResponse userReqEnc =EncryptionDecriptionUtil.convertFromJson(encriptResponse, EncriptResponse.class);

				profileRes =  EncryptionDecriptionUtil.decriptResponse(userReqEnc.getEncriptData(), userReqEnc.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	   
		return profileRes;
		  
	}
	
	
	@PostMapping(value="/de-linkErupiaccount")
	public @ResponseBody String delinkErupiaccount(HttpServletRequest request,
			ErupiLinkBankAccount erupiLinkBankAccount, BindingResult result, HttpSession session, ModelMap model,Locale locale) {
	
			String profileRes=null;
			
			//profileRes = expensesReimbursementService.delinkErupiAccount(tokengeneration.getToken(),erupiLinkBankAccount);
			
			//return profileRes;
			
			try {
				String json = EncryptionDecriptionUtil.convertToJson(erupiLinkBankAccount);

				EncriptResponse jsonObject=EncryptionDecriptionUtil.encriptResponse(json, applicationConstantConfig.apiSignaturePublicPath);

				String encriptResponse =  expensesReimbursementService.delinkErupiAccount(tokengeneration.getToken(), jsonObject);

	   
				EncriptResponse userReqEnc =EncryptionDecriptionUtil.convertFromJson(encriptResponse, EncriptResponse.class);

				profileRes =  EncryptionDecriptionUtil.decriptResponse(userReqEnc.getEncriptData(), userReqEnc.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	   
		return profileRes;
		  
	}
	@PostMapping(value="/re-linkErupiaccount")
	public @ResponseBody String relinkErupiaccount(HttpServletRequest request,
			ErupiLinkBankAccount erupiLinkBankAccount, BindingResult result, HttpSession session, ModelMap model,Locale locale) {
	
			String profileRes=null;
			
			//profileRes = expensesReimbursementService.relinkErupiAccount(tokengeneration.getToken(),erupiLinkBankAccount);
			
			//return profileRes;
			
			try {
				String json = EncryptionDecriptionUtil.convertToJson(erupiLinkBankAccount);

				EncriptResponse jsonObject=EncryptionDecriptionUtil.encriptResponse(json, applicationConstantConfig.apiSignaturePublicPath);

				String encriptResponse =  expensesReimbursementService.relinkErupiAccount(tokengeneration.getToken(), jsonObject);

	   
				EncriptResponse userReqEnc =EncryptionDecriptionUtil.convertFromJson(encriptResponse, EncriptResponse.class);

				profileRes =  EncryptionDecriptionUtil.decriptResponse(userReqEnc.getEncriptData(), userReqEnc.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	   
		return profileRes;
		  
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
	
		String profileRes=null;
		
		//profileRes = expensesReimbursementService.cashAdvanceRequest(tokengeneration.getToken(),advanceTravelRequest);
		try {
			String json = EncryptionDecriptionUtil.convertToJson(advanceTravelRequest);

			EncriptResponse jsonObject=EncryptionDecriptionUtil.encriptResponse(json, applicationConstantConfig.apiSignaturePublicPath);

			String encriptResponse =  expensesReimbursementService.cashAdvanceRequest(tokengeneration.getToken(), jsonObject);

   
			EncriptResponse userReqEnc =EncryptionDecriptionUtil.convertFromJson(encriptResponse, EncriptResponse.class);

			profileRes =  EncryptionDecriptionUtil.decriptResponse(userReqEnc.getEncriptData(), userReqEnc.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
   
	return profileRes;
	
		  
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

		String profileRes=null;
		//profileRes = expensesReimbursementService.travelAdvanceRequest(tokengeneration.getToken(),travelRequest);	
		try {
			String json = EncryptionDecriptionUtil.convertToJson(travelRequest);

			EncriptResponse jsonObject=EncryptionDecriptionUtil.encriptResponse(json, applicationConstantConfig.apiSignaturePublicPath);

			String encriptResponse =  expensesReimbursementService.travelAdvanceRequest(tokengeneration.getToken(), jsonObject);

   
			EncriptResponse userReqEnc =EncryptionDecriptionUtil.convertFromJson(encriptResponse, EncriptResponse.class);

			profileRes =  EncryptionDecriptionUtil.decriptResponse(userReqEnc.getEncriptData(), userReqEnc.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
   
	return profileRes;
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
			HttpSession session, AdvanceTravelRequest advanceTravelRequest) {
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
	
}
