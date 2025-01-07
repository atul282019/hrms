package com.cotodel.hrms.web.service.Impl;

import javax.xml.bind.DatatypeConverter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cotodel.hrms.web.function.common.CommonUtils;
import com.cotodel.hrms.web.properties.ApplicationConstantConfig;
import com.cotodel.hrms.web.response.EmployeeDetailsNewRequest;
import com.cotodel.hrms.web.response.ErupiLinkBankAccount;
import com.cotodel.hrms.web.response.ExpensesReimbursementRequest;
import com.cotodel.hrms.web.response.ExpensesReimbursementRequestNew;
import com.cotodel.hrms.web.service.ExpensesReimbursementService;
import com.cotodel.hrms.web.util.CommonUtility;
import com.cotodel.hrms.web.util.CopyUtility;
import com.cotodel.hrms.web.util.MessageConstant;

@Service
public class ExpensesReimbursementServiceImpl  implements ExpensesReimbursementService{

	@Autowired
	public ApplicationConstantConfig applicationConstantConfig;
	
	@Override
	public String saveExpensesReimbursement(String token, ExpensesReimbursementRequest expensesReimbursementRequest) {
		ExpensesReimbursementRequestNew expensesReimbursementRequestNew = new ExpensesReimbursementRequestNew();
		
		  CopyUtility.copyProperties(expensesReimbursementRequest, expensesReimbursementRequestNew);
		  
		  expensesReimbursementRequestNew.setFile(expensesReimbursementRequest.getFileInput());
		  expensesReimbursementRequestNew.setFileName(expensesReimbursementRequest.getFileName());
		  expensesReimbursementRequestNew.setFileType(expensesReimbursementRequest.getFileType());
		 
		return CommonUtility.userRequest(token,MessageConstant.gson.toJson(expensesReimbursementRequestNew), applicationConstantConfig.employerServiceBaseUrl+CommonUtils.addExpensesReimbursement);
		
	}

	@Override
	public String getExpanseReimbursement(String token, ExpensesReimbursementRequest expensesReimbursementRequest) {
		return CommonUtility.userRequest(token,MessageConstant.gson.toJson(expensesReimbursementRequest), applicationConstantConfig.employerServiceBaseUrl+CommonUtils.getExpensesReimbursement);
	}

	@Override
	public String viewExpenseReimbursement(String token, ExpensesReimbursementRequest expensesReimbursementRequest) {
		return CommonUtility.userRequest(token,MessageConstant.gson.toJson(expensesReimbursementRequest), applicationConstantConfig.employerServiceBaseUrl+CommonUtils.viewExpensesReimbursement);
	}

	@Override
	public String deleteExpanseReimbursement(String token, ExpensesReimbursementRequest expensesReimbursementRequest) {
		return CommonUtility.userRequest(token,MessageConstant.gson.toJson(expensesReimbursementRequest), applicationConstantConfig.employerServiceBaseUrl+CommonUtils.deleteExpenseReimbursement);
	}

	@Override
	public String saveExpensesReimbursementDraft(String token,
			ExpensesReimbursementRequest expensesReimbursementRequest) {
		ExpensesReimbursementRequestNew expensesReimbursementRequestNew = new ExpensesReimbursementRequestNew();
		
		  CopyUtility.copyProperties(expensesReimbursementRequest, expensesReimbursementRequestNew);
		  
		  expensesReimbursementRequestNew.setFile(expensesReimbursementRequest.getFileInput());
		  expensesReimbursementRequestNew.setFileName(expensesReimbursementRequest.getFileName());
		  expensesReimbursementRequestNew.setFileType(expensesReimbursementRequest.getFileType());
		 
		return CommonUtility.userRequest(token,MessageConstant.gson.toJson(expensesReimbursementRequestNew), applicationConstantConfig.employerServiceBaseUrl+CommonUtils.addExpensesReimbursementDraft);
		
	}

	@Override
	public String erupiLinkBankAccount(String token, ErupiLinkBankAccount erupiLinkBankAccount) {
		return CommonUtility.userRequest(token,MessageConstant.gson.toJson(erupiLinkBankAccount), applicationConstantConfig.employerServiceBaseUrl+CommonUtils.addErupiLinkAccount);

	}

	@Override
	public String getErupiLinkBankAccountDetail(String token, ErupiLinkBankAccount erupiLinkBankAccount) {
		return CommonUtility.userRequest(token,MessageConstant.gson.toJson(erupiLinkBankAccount), applicationConstantConfig.employerServiceBaseUrl+CommonUtils.getErupiLinkAccountDetailWithStatus);
	}

	@Override
	public String updateErupiLinkBankAccountStaus(String token, ErupiLinkBankAccount erupiLinkBankAccount) {
		return CommonUtility.userRequest(token,MessageConstant.gson.toJson(erupiLinkBankAccount), applicationConstantConfig.employerServiceBaseUrl+CommonUtils.updateErupiLinkAccount);

	}

	@Override
	public String delinkErupiAccount(String token, ErupiLinkBankAccount erupiLinkBankAccount) {
		return CommonUtility.userRequest(token,MessageConstant.gson.toJson(erupiLinkBankAccount), applicationConstantConfig.employerServiceBaseUrl+CommonUtils.delinkErupiLinkAccount);
	}
	@Override
	public String relinkErupiAccount(String token, ErupiLinkBankAccount erupiLinkBankAccount) {
		return CommonUtility.userRequest(token,MessageConstant.gson.toJson(erupiLinkBankAccount), applicationConstantConfig.employerServiceBaseUrl+CommonUtils.reLinkErupiLinkAccount);
	}

}
