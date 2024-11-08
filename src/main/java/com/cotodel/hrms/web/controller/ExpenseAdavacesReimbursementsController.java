package com.cotodel.hrms.web.controller;

import java.util.HashMap;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cotodel.hrms.web.properties.ApplicationConstantConfig;
import com.cotodel.hrms.web.response.EmployeeDetailsRequest;
import com.cotodel.hrms.web.response.ErupiLinkBankAccount;
import com.cotodel.hrms.web.response.ExpanceTravelAdvanceRequest;
import com.cotodel.hrms.web.response.ExpenseCategoryRequest;
import com.cotodel.hrms.web.response.ExpensesReimbursementRequest;
import com.cotodel.hrms.web.service.ExpensesReimbursementService;
import com.cotodel.hrms.web.service.Impl.TokenGenerationImpl;
import com.cotodel.hrms.web.util.MessageConstant;
import com.fasterxml.jackson.databind.ObjectMapper;

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
		profileRes = expensesReimbursementService.getExpanseReimbursement(tokengeneration.getToken(),
				expensesReimbursementRequest);

		return profileRes;
	}
	
	@PostMapping(value = "/viewExpenseReimbursement")
	public @ResponseBody String viewExpenseReimbursement(HttpServletRequest request, ModelMap model, Locale locale,
			HttpSession session, ExpensesReimbursementRequest expensesReimbursementRequest) {
		String profileRes = null;
	
		profileRes = expensesReimbursementService.viewExpenseReimbursement(tokengeneration.getToken(),
				expensesReimbursementRequest);

		return profileRes;
	}
	
	@PostMapping(value = "/deleteExpanseReimbursement")
	public @ResponseBody String deleteExpanseReimbursement(HttpServletRequest request, ModelMap model, Locale locale,
			HttpSession session, ExpensesReimbursementRequest expensesReimbursementRequest) {
		String profileRes = null;
	
		profileRes = expensesReimbursementService.deleteExpanseReimbursement(tokengeneration.getToken(),
				expensesReimbursementRequest);

		return profileRes;
	}
	
	@PostMapping(value="/addErupiLinkBankAccount")
	public @ResponseBody String erupiLinkBankAccount(HttpServletRequest request,
			ErupiLinkBankAccount erupiLinkBankAccount, BindingResult result, HttpSession session, ModelMap model,Locale locale) {
	
		String profileRes=null;
		
		profileRes = expensesReimbursementService.erupiLinkBankAccount(tokengeneration.getToken(),erupiLinkBankAccount);
		
		  return profileRes;
		  
	}
	
	@PostMapping(value="/getErupiLinkBankAccountDetail")
	public @ResponseBody String getErupiLinkBankAccountDetail(HttpServletRequest request,
			ErupiLinkBankAccount erupiLinkBankAccount, BindingResult result, HttpSession session, ModelMap model,Locale locale) {
	
			String profileRes=null;
			
			profileRes = expensesReimbursementService.getErupiLinkBankAccountDetail(tokengeneration.getToken(),erupiLinkBankAccount);
			
			return profileRes;
		  
	}
	
}
