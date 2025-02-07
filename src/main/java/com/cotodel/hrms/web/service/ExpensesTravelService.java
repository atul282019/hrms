package com.cotodel.hrms.web.service;

import org.springframework.stereotype.Repository;

import com.cotodel.hrms.web.response.ExpanceTravelAdvanceRequest;
import com.cotodel.hrms.web.response.ExpenseCategoryRequest;
import com.cotodel.hrms.web.util.EncriptResponse;

@Repository
public interface ExpensesTravelService {

	String saveExpensesCategory(String token, EncriptResponse expenseCategoryRequest);

	String getExpensesCategory(String token, EncriptResponse expenseCategoryRequest);

	String getEditExpensesCategory(String token, EncriptResponse expenseCategoryRequest);

	
	String getExpanseTravelAdvance(String token, EncriptResponse expanceTravelAdvanceRequest);

	String saveExpanceTravelAdvance(String token, EncriptResponse expanceTravelAdvanceRequest);

	String deletetExpanseTravelAdvance(String token, EncriptResponse expenseCategoryRequest);

	String getExpenseBandList(String token, EncriptResponse expanceTravelAdvanceRequest);

	
}
