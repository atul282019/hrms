package com.cotodel.hrms.web.service;

import org.springframework.stereotype.Repository;

import com.cotodel.hrms.web.response.AdvanceTravelRequest;
import com.cotodel.hrms.web.response.ErupiLinkBankAccount;
import com.cotodel.hrms.web.response.ExpensesReimbursementRequest;
import com.cotodel.hrms.web.response.TravelAdvanceRequestUpdate;
import com.cotodel.hrms.web.response.TravelReimbursement;
import com.cotodel.hrms.web.response.TravelRequest;
import com.cotodel.hrms.web.response.TravelRequestUpdate;

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

	String getErupiLinkDlinkAccountDetail(String token, ErupiLinkBankAccount erupiLinkBankAccount);

	String getExpanseReimbursementApprovalList(String token, ExpensesReimbursementRequest expensesReimbursementRequest);

	String getExpensesReimbursementDetailById(String token, ExpensesReimbursementRequest expensesReimbursementRequest);

	String approveExpensesById(String token, ExpensesReimbursementRequest expensesReimbursementRequest);

	String cashAdvanceRequest(String token, AdvanceTravelRequest advanceTravelRequest);

	String getCashAdanceRequestData(String token, AdvanceTravelRequest advanceTravelRequest);

	String travelAdvanceRequest(String token, TravelRequest travelRequest);

	String getTravelReviewData(String token, AdvanceTravelRequest advanceTravelRequest);

	String travelAdvanceRequestUpdate(String token, TravelAdvanceRequestUpdate travelAdvanceRequestUpdate);

	String deleteAdvanceTravel(String token, TravelReimbursement travelReimbursement);
}
