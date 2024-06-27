package com.cotodel.hrms.web.service.Impl;

import javax.xml.bind.DatatypeConverter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cotodel.hrms.web.function.common.CommonUtils;
import com.cotodel.hrms.web.properties.ApplicationConstantConfig;
import com.cotodel.hrms.web.response.EmployeeDetailsNewRequest;
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
		ExpensesReimbursementRequestNew expensesReimbursementRequestNew=new ExpensesReimbursementRequestNew();
		CopyUtility.copyProperties(expensesReimbursementRequest, expensesReimbursementRequestNew);
		 String file ="";
		
		try {			
			byte[] byt = expensesReimbursementRequest.getUpfile().getBytes();
			file = DatatypeConverter.printBase64Binary(byt);
		} catch (Exception e) {
			// TODO: handle exception
		}
		String fileName=expensesReimbursementRequest.getUpfile().getOriginalFilename();
		String fileType =expensesReimbursementRequest.getUpfile().getContentType();
		
		expensesReimbursementRequestNew.setFile(file);
		expensesReimbursementRequestNew.setFileName(fileName);
		expensesReimbursementRequestNew.setFileType(fileType);
		
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

}
