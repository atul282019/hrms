package com.cotodel.hrms.web.service;

import org.springframework.stereotype.Repository;

import com.cotodel.hrms.web.response.EmployeePayrollRequest;
import com.cotodel.hrms.web.response.ExpanceTravelAdvanceRequest;
import com.cotodel.hrms.web.response.ExpenseCategoryRequest;

@Repository
public interface ExpensesTravelService {

	String saveExpensesCategory(String token, ExpenseCategoryRequest expenseCategoryRequest);

	String getExpensesCategory(String token, ExpenseCategoryRequest expenseCategoryRequest);

	String getEditExpensesCategory(String token, ExpenseCategoryRequest expenseCategoryRequest);

	
	String getExpanseTravelAdvance(String token, ExpanceTravelAdvanceRequest expanceTravelAdvanceRequest);

	String saveExpanceTravelAdvance(String token, ExpanceTravelAdvanceRequest expanceTravelAdvanceRequest);

	
}
