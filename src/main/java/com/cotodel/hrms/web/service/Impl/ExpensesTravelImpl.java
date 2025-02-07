package com.cotodel.hrms.web.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cotodel.hrms.web.function.common.CommonUtils;
import com.cotodel.hrms.web.properties.ApplicationConstantConfig;
import com.cotodel.hrms.web.response.ExpanceTravelAdvanceRequest;
import com.cotodel.hrms.web.response.ExpenseCategoryRequest;
import com.cotodel.hrms.web.service.ExpensesTravelService;
import com.cotodel.hrms.web.util.CommonUtility;
import com.cotodel.hrms.web.util.EncriptResponse;
import com.cotodel.hrms.web.util.MessageConstant;

@Service
public class ExpensesTravelImpl implements ExpensesTravelService{

	@Autowired
	public ApplicationConstantConfig applicationConstantConfig;

	@Override
	public String saveExpensesCategory(String token, EncriptResponse expenseCategoryRequest) {
		
		return CommonUtility.userRequest(token,MessageConstant.gson.toJson(expenseCategoryRequest), applicationConstantConfig.employerServiceBaseUrl+CommonUtils.saveExpenseCategory);

	}

	@Override
	public String getExpensesCategory(String token, EncriptResponse expenseCategoryRequest) {
		
		return CommonUtility.userRequest(token,MessageConstant.gson.toJson(expenseCategoryRequest), applicationConstantConfig.employerServiceBaseUrl+CommonUtils.getExpenseCategory);
	}

	@Override
	public String getEditExpensesCategory(String token, EncriptResponse expenseCategoryRequest) {
		return CommonUtility.userRequest(token,MessageConstant.gson.toJson(expenseCategoryRequest), applicationConstantConfig.employerServiceBaseUrl+CommonUtils.getExpenseCategoryEditValue);
	}

	@Override
	public String getExpanseTravelAdvance(String token, EncriptResponse expanceTravelAdvanceRequest) {
		// TODO Auto-generated method stub
		return CommonUtility.userRequest(token,MessageConstant.gson.toJson(expanceTravelAdvanceRequest), applicationConstantConfig.employerServiceBaseUrl+CommonUtils.getexpanceTravelAdvance);
	}

	@Override
	public String saveExpanceTravelAdvance(String token, EncriptResponse expanceTravelAdvanceRequest) {
		return CommonUtility.userRequest(token,MessageConstant.gson.toJson(expanceTravelAdvanceRequest), applicationConstantConfig.employerServiceBaseUrl+CommonUtils.saveExpanceTravelAdvance);
	}

	@Override
	public String deletetExpanseTravelAdvance(String token, EncriptResponse expenseCategoryRequest) {
		return CommonUtility.userRequest(token,MessageConstant.gson.toJson(expenseCategoryRequest), applicationConstantConfig.employerServiceBaseUrl+CommonUtils.deleteExpanceTravelAllounce);
	}

	@Override
	public String getExpenseBandList(String token, EncriptResponse expanceTravelAdvanceRequest) {
		return CommonUtility.userRequest(token,MessageConstant.gson.toJson(expanceTravelAdvanceRequest), applicationConstantConfig.employerServiceBaseUrl+CommonUtils.getExpenseBandList);
	}

	
	

}
