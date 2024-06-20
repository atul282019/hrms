package com.cotodel.hrms.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

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
import com.cotodel.hrms.web.response.EmployeePayrollNew;
import com.cotodel.hrms.web.response.EmployeePayrollRequest;
import com.cotodel.hrms.web.response.ExpanceTravelAdvanceRequest;
import com.cotodel.hrms.web.response.ExpenseCategoryRequest;
import com.cotodel.hrms.web.service.ExpensesTravelService;
import com.cotodel.hrms.web.service.Impl.TokenGenerationImpl;
import com.cotodel.hrms.web.util.MessageConstant;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@CrossOrigin
public class ExpensesTravelPoliciesController extends CotoDelBaseController {

	private static final Logger logger = LoggerFactory.getLogger(ExpensesTravelPoliciesController.class);

	@Autowired
	public ApplicationConstantConfig applicationConstantConfig;

	@Autowired
	ExpensesTravelService expensesTravelService;

	@Autowired
	TokenGenerationImpl tokengeneration;

	@PostMapping(value = "/saveExpensesCategory")
	public @ResponseBody String saveExpensesCategory(HttpServletRequest request, ModelMap model, Locale locale,
			HttpSession session, ExpenseCategoryRequest expenseCategoryRequest) {
		String profileRes = null;
		JSONObject profileJsonRes = null;
		HashMap<String, String> otpMap = new HashMap<String, String>();
		ObjectMapper mapper = new ObjectMapper();
		String res = null;
		String userRes = null;
		List<BandDetailRequest> list = new ArrayList<BandDetailRequest>();
		String data[] = expenseCategoryRequest.getListArray();
		
		 for (int i = 1; i < data.length; i++) { String listValue=data[i]; String[]
		 rowArray=listValue.split("@"); 
		  BandDetailRequest bandDetailRequest= new  BandDetailRequest(); 
		  
		  bandDetailRequest.setBandType(rowArray[0]);
		  bandDetailRequest.setBandOneInr(rowArray[1]);
		  bandDetailRequest.setBandTwoInr(rowArray[2]);
		  bandDetailRequest.setBandThreeInr(rowArray[3]);
		  bandDetailRequest.setBandFourInr(rowArray[4]);
		  bandDetailRequest.setBandFiveInr(rowArray[5]);
		  bandDetailRequest.setBandSixInr(rowArray[6]);
		  
		  list.add(bandDetailRequest); }
		 

		expenseCategoryRequest.setList(list);
		profileRes = expensesTravelService.saveExpensesCategory(tokengeneration.getToken(), expenseCategoryRequest);
		profileJsonRes = new JSONObject(profileRes);

		if (profileJsonRes.getBoolean("status")) {
			otpMap.put("status", MessageConstant.RESPONSE_SUCCESS);
		} else {
			// loginservice.sendEmailVerificationCompletion(userForm);
			otpMap.put("status", MessageConstant.RESPONSE_FAILED);
		}
		try {
			res = mapper.writeValueAsString(otpMap);
		} catch (Exception e) {
			// TODO: handle exception
		}

		return profileRes;
	}

	@GetMapping(value = "/getExpensesCategory")
	public @ResponseBody String getExpensesCategory(HttpServletRequest request, ModelMap model, Locale locale,
			HttpSession session, ExpenseCategoryRequest expenseCategoryRequest) {
		String profileRes = null;
		JSONObject profileJsonRes = null;
		HashMap<String, String> otpMap = new HashMap<String, String>();
		ObjectMapper mapper = new ObjectMapper();
		String res = null;
		String userRes = null;
		profileRes = expensesTravelService.getExpensesCategory(tokengeneration.getToken(), expenseCategoryRequest);
		profileJsonRes = new JSONObject(profileRes);

		if (profileJsonRes.getBoolean("status")) {
			otpMap.put("status", MessageConstant.RESPONSE_SUCCESS);
		} else {
			// loginservice.sendEmailVerificationCompletion(userForm);
			otpMap.put("status", MessageConstant.RESPONSE_FAILED);
		}
		try {
			res = mapper.writeValueAsString(otpMap);
		} catch (Exception e) {
			// TODO: handle exception
		}

		return profileRes;
	}

	@GetMapping(value = "/editExpensesCategory")
	public @ResponseBody String editExpensesCategory(HttpServletRequest request, ModelMap model, Locale locale,
			HttpSession session, ExpenseCategoryRequest expenseCategoryRequest) {
		String profileRes = null;
		JSONObject profileJsonRes = null;
		HashMap<String, String> otpMap = new HashMap<String, String>();
		ObjectMapper mapper = new ObjectMapper();
		String res = null;
		String userRes = null;
		profileRes = expensesTravelService.getEditExpensesCategory(tokengeneration.getToken(), expenseCategoryRequest);
		profileJsonRes = new JSONObject(profileRes);

		if (profileJsonRes.getBoolean("status")) {
			otpMap.put("status", MessageConstant.RESPONSE_SUCCESS);
		} else {
			// loginservice.sendEmailVerificationCompletion(userForm);
			otpMap.put("status", MessageConstant.RESPONSE_FAILED);
		}
		try {
			res = mapper.writeValueAsString(otpMap);
		} catch (Exception e) {
			// TODO: handle exception
		}

		return profileRes;
	}

	@PostMapping(value = "/saveExpanceTravelAdvance")
	public @ResponseBody String saveExpanceTravelAdvance(HttpServletRequest request, ModelMap model, Locale locale,
			HttpSession session, ExpanceTravelAdvanceRequest expanceTravelAdvanceRequest) {
		String profileRes = null;
		JSONObject profileJsonRes = null;
		HashMap<String, String> otpMap = new HashMap<String, String>();
		ObjectMapper mapper = new ObjectMapper();
		String res = null;
		String userRes = null;
		profileRes = expensesTravelService.saveExpanceTravelAdvance(tokengeneration.getToken(),
				expanceTravelAdvanceRequest);
		profileJsonRes = new JSONObject(profileRes);

		if (profileJsonRes.getBoolean("status")) {
			otpMap.put("status", MessageConstant.RESPONSE_SUCCESS);
		} else {
			// loginservice.sendEmailVerificationCompletion(userForm);
			otpMap.put("status", MessageConstant.RESPONSE_FAILED);
		}
		try {
			res = mapper.writeValueAsString(otpMap);
		} catch (Exception e) {
			// TODO: handle exception
		}

		return profileRes;
	}

	@GetMapping(value = "/getExpanseTravelAdvance")
	public @ResponseBody String getExpanseTravelAdvance(HttpServletRequest request, ModelMap model, Locale locale,
			HttpSession session, ExpanceTravelAdvanceRequest expanceTravelAdvanceRequest) {
		String profileRes = null;
		JSONObject profileJsonRes = null;
		HashMap<String, String> otpMap = new HashMap<String, String>();
		ObjectMapper mapper = new ObjectMapper();
		String res = null;
		String userRes = null;
		profileRes = expensesTravelService.getExpanseTravelAdvance(tokengeneration.getToken(),
				expanceTravelAdvanceRequest);
		profileJsonRes = new JSONObject(profileRes);

		if (profileJsonRes.getBoolean("status")) {
			otpMap.put("status", MessageConstant.RESPONSE_SUCCESS);
		} else {
			// loginservice.sendEmailVerificationCompletion(userForm);
			otpMap.put("status", MessageConstant.RESPONSE_FAILED);
		}
		try {
			res = mapper.writeValueAsString(otpMap);
		} catch (Exception e) {
			// TODO: handle exception
		}

		return profileRes;
	}

	@GetMapping(value = "/deleteExpanseTravelAdvance")
	public @ResponseBody String deleteExpanseTravelAdvance(HttpServletRequest request, ModelMap model, Locale locale,
			HttpSession session, ExpenseCategoryRequest expenseCategoryRequest) {
		String profileRes = null;
		JSONObject profileJsonRes = null;
		HashMap<String, String> otpMap = new HashMap<String, String>();
		ObjectMapper mapper = new ObjectMapper();
		String res = null;
		String userRes = null;
		profileRes = expensesTravelService.deletetExpanseTravelAdvance(tokengeneration.getToken(),
				expenseCategoryRequest);
		profileJsonRes = new JSONObject(profileRes);

		if (profileJsonRes.getBoolean("status")) {
			otpMap.put("status", MessageConstant.RESPONSE_SUCCESS);
		} else {
			// loginservice.sendEmailVerificationCompletion(userForm);
			otpMap.put("status", MessageConstant.RESPONSE_FAILED);
		}
		try {
			res = mapper.writeValueAsString(otpMap);
		} catch (Exception e) {
			// TODO: handle exception
		}

		return profileRes;
	}

	@GetMapping(value = "/getExpenseBandList")
	public @ResponseBody String getExpenseBandList(HttpServletRequest request, ModelMap model, Locale locale,
			HttpSession session, ExpanceTravelAdvanceRequest expanceTravelAdvanceRequest) {
		String profileRes = null;
		JSONObject profileJsonRes = null;
		HashMap<String, String> otpMap = new HashMap<String, String>();
		ObjectMapper mapper = new ObjectMapper();
		String res = null;
		String userRes = null;
		profileRes = expensesTravelService.getExpenseBandList(tokengeneration.getToken(), expanceTravelAdvanceRequest);
		profileJsonRes = new JSONObject(profileRes);

		if (profileJsonRes.getBoolean("status")) {
			otpMap.put("status", MessageConstant.RESPONSE_SUCCESS);
		} else {
			// loginservice.sendEmailVerificationCompletion(userForm);
			otpMap.put("status", MessageConstant.RESPONSE_FAILED);
		}
		try {
			res = mapper.writeValueAsString(otpMap);
		} catch (Exception e) {
			// TODO: handle exception
		}

		return profileRes;
	}

}
