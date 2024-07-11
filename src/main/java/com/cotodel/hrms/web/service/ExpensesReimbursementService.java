package com.cotodel.hrms.web.service;

import org.springframework.stereotype.Repository;

import com.cotodel.hrms.web.response.ExpensesReimbursementRequest;

@Repository
public interface ExpensesReimbursementService {

	String saveExpensesReimbursement(String token, ExpensesReimbursementRequest expensesReimbursementRequest);

	String getExpanseReimbursement(String token, ExpensesReimbursementRequest expensesReimbursementRequest);

	String viewExpenseReimbursement(String token, ExpensesReimbursementRequest expensesReimbursementRequest);

	String deleteExpanseReimbursement(String token, ExpensesReimbursementRequest expensesReimbursementRequest);

	String saveExpensesReimbursementDraft(String token, ExpensesReimbursementRequest expensesReimbursementRequest);

}
