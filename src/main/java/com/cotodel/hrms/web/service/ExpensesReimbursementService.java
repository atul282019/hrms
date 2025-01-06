package com.cotodel.hrms.web.service;

import org.springframework.stereotype.Repository;

import com.cotodel.hrms.web.response.ErupiLinkBankAccount;
import com.cotodel.hrms.web.response.ExpensesReimbursementRequest;

@Repository
public interface ExpensesReimbursementService {

	String saveExpensesReimbursement(String token, ExpensesReimbursementRequest expensesReimbursementRequest);

	String getExpanseReimbursement(String token, ExpensesReimbursementRequest expensesReimbursementRequest);

	String viewExpenseReimbursement(String token, ExpensesReimbursementRequest expensesReimbursementRequest);

	String deleteExpanseReimbursement(String token, ExpensesReimbursementRequest expensesReimbursementRequest);

	String saveExpensesReimbursementDraft(String token, ExpensesReimbursementRequest expensesReimbursementRequest);

	String erupiLinkBankAccount(String token, ErupiLinkBankAccount erupiLinkBankAccount);

	String getErupiLinkBankAccountDetail(String token, ErupiLinkBankAccount erupiLinkBankAccount);

	String updateErupiLinkBankAccountStaus(String token, ErupiLinkBankAccount erupiLinkBankAccount);
	
	String delinkErupiAccount(String token, ErupiLinkBankAccount erupiLinkBankAccount);
	String relinkErupiAccount(String token, ErupiLinkBankAccount erupiLinkBankAccount);

}
