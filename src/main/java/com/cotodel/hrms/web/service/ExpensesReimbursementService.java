package com.cotodel.hrms.web.service;

import org.springframework.stereotype.Repository;

import com.cotodel.hrms.web.response.AdvanceTravelRequest;
import com.cotodel.hrms.web.response.ErupiLinkBankAccount;
import com.cotodel.hrms.web.response.ExpensesReimbursementRequest;
import com.cotodel.hrms.web.response.TravelAdvanceRequestUpdate;
import com.cotodel.hrms.web.response.TravelReimbursement;
import com.cotodel.hrms.web.response.TravelRequest;
import com.cotodel.hrms.web.util.EncriptResponse;

@Repository
public interface ExpensesReimbursementService {

	String saveExpensesReimbursement(String token, ExpensesReimbursementRequest expensesReimbursementRequest);

	String getExpanseReimbursement(String token, EncriptResponse expensesReimbursementRequest);

	String viewExpenseReimbursement(String token, EncriptResponse expensesReimbursementRequest);

	String deleteExpanseReimbursement(String token, EncriptResponse jsonObject);

	String saveExpensesReimbursementDraft(String token, ExpensesReimbursementRequest expensesReimbursementRequest);

	String erupiLinkBankAccount(String token, EncriptResponse erupiLinkBankAccount);

	String getErupiLinkBankAccountDetail(String token, EncriptResponse erupiLinkBankAccount);

	String updateErupiLinkBankAccountStaus(String token, EncriptResponse erupiLinkBankAccount);
	
	String delinkErupiAccount(String token, EncriptResponse erupiLinkBankAccount);
	String relinkErupiAccount(String token, EncriptResponse erupiLinkBankAccount);

	String getErupiLinkDlinkAccountDetail(String token, EncriptResponse erupiLinkBankAccount);

	String getExpanseReimbursementApprovalList(String token, EncriptResponse expensesReimbursementRequest);
	
	String getErupiLinkAccountDetails(String token, EncriptResponse ErupiLinkAccountDetailsRequest);
	
	String getSavedCBankDetails(String token, EncriptResponse ErupiLinkAccountDetailsRequest);
	
	String SaveCotodelbankDetails(String token, EncriptResponse ErupiLinkAccountDetailsRequest);
	
	String cApproveReject(String token, EncriptResponse ErupiLinkAccountDetailsRequest);

	String getExpensesReimbursementDetailById(String token, EncriptResponse expensesReimbursementRequest);

	String approveExpensesById(String token, EncriptResponse jsonObject);

	String cashAdvanceRequest(String token, EncriptResponse jsonObject);

	String getCashAdanceRequestData(String token, EncriptResponse jsonObject);

	String travelAdvanceRequest(String token, EncriptResponse travelRequest);

	String getTravelReviewData(String token, EncriptResponse jsonObject);

	String travelAdvanceRequestUpdate(String token, EncriptResponse jsonObject);

	String deleteAdvanceTravel(String token, EncriptResponse jsonObject);

	String getAdanceCashTravelApprpvalList(String token, EncriptResponse jsonObject);

	String approveAdvanceTravelRequest(String token, EncriptResponse jsonObject);

	String getCashAdvanceDetailById(String token, EncriptResponse jsonObject);
}
