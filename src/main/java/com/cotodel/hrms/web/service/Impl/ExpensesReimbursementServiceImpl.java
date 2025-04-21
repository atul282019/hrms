package com.cotodel.hrms.web.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cotodel.hrms.web.function.common.CommonUtils;
import com.cotodel.hrms.web.properties.ApplicationConstantConfig;
import com.cotodel.hrms.web.response.AdvanceTravelRequest;
import com.cotodel.hrms.web.response.ErupiLinkBankAccount;
import com.cotodel.hrms.web.response.ExpensesReimbursementRequest;
import com.cotodel.hrms.web.response.ExpensesReimbursementRequestNew;
import com.cotodel.hrms.web.response.TravelAdvanceRequestUpdate;
import com.cotodel.hrms.web.response.TravelReimbursement;
import com.cotodel.hrms.web.response.TravelRequest;
import com.cotodel.hrms.web.service.ExpensesReimbursementService;
import com.cotodel.hrms.web.util.CommonUtility;
import com.cotodel.hrms.web.util.CopyUtility;
import com.cotodel.hrms.web.util.EncriptResponse;
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
		 
		return CommonUtility.expenseRequest(token,MessageConstant.gson.toJson(expensesReimbursementRequestNew),
				applicationConstantConfig.employerServiceBaseUrl+CommonUtils.addExpensesReimbursement,
				applicationConstantConfig.apiSignaturePublicPath,
				applicationConstantConfig.apiSignaturePrivatePath);
		
		
	}

	@Override
	public String getExpanseReimbursement(String token, EncriptResponse expensesReimbursementRequest) {
		return CommonUtility.userRequest(token,MessageConstant.gson.toJson(expensesReimbursementRequest), applicationConstantConfig.employerServiceBaseUrl+CommonUtils.getExpensesReimbursement);
	}

	@Override
	public String viewExpenseReimbursement(String token, EncriptResponse expensesReimbursementRequest) {
		return CommonUtility.userRequest(token,MessageConstant.gson.toJson(expensesReimbursementRequest), applicationConstantConfig.employerServiceBaseUrl+CommonUtils.viewExpensesReimbursement);
	}

	@Override
	public String deleteExpanseReimbursement(String token, EncriptResponse expensesReimbursementRequest) {
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
		 
		return CommonUtility.expenseRequest(token,MessageConstant.gson.toJson(expensesReimbursementRequestNew), 
				applicationConstantConfig.employerServiceBaseUrl+CommonUtils.addExpensesReimbursementDraft,
				applicationConstantConfig.apiSignaturePublicPath,
				applicationConstantConfig.apiSignaturePrivatePath);
		
	}

	@Override
	public String erupiLinkBankAccount(String token, EncriptResponse erupiLinkBankAccount) {
		return CommonUtility.userRequest(token,MessageConstant.gson.toJson(erupiLinkBankAccount), applicationConstantConfig.employerServiceBaseUrl+CommonUtils.addErupiLinkAccount);

	}

	@Override
	public String getErupiLinkBankAccountDetail(String token, EncriptResponse erupiLinkBankAccount) {
		return CommonUtility.userRequest(token,MessageConstant.gson.toJson(erupiLinkBankAccount), applicationConstantConfig.employerServiceBaseUrl+CommonUtils.getErupiLinkAccountDetailWithStatus);
	}

	@Override
	public String updateErupiLinkBankAccountStaus(String token, EncriptResponse erupiLinkBankAccount) {
		return CommonUtility.userRequest(token,MessageConstant.gson.toJson(erupiLinkBankAccount), applicationConstantConfig.employerServiceBaseUrl+CommonUtils.updateErupiLinkAccount);

	}

	@Override
	public String delinkErupiAccount(String token, EncriptResponse erupiLinkBankAccount) {
		return CommonUtility.userRequest(token,MessageConstant.gson.toJson(erupiLinkBankAccount), applicationConstantConfig.employerServiceBaseUrl+CommonUtils.delinkErupiLinkAccount);
	}
	@Override
	public String relinkErupiAccount(String token, EncriptResponse erupiLinkBankAccount) {
		return CommonUtility.userRequest(token,MessageConstant.gson.toJson(erupiLinkBankAccount), applicationConstantConfig.employerServiceBaseUrl+CommonUtils.reLinkErupiLinkAccount);
	}

	@Override
	public String getErupiLinkDlinkAccountDetail(String token, EncriptResponse erupiLinkBankAccount) {
		return CommonUtility.userRequest(token,MessageConstant.gson.toJson(erupiLinkBankAccount), applicationConstantConfig.employerServiceBaseUrl+CommonUtils.getErupiLinkAccountDetail);
	}

	@Override
	public String getExpanseReimbursementApprovalList(String token,
			EncriptResponse expensesReimbursementRequest) {
		return CommonUtility.userRequest(token,MessageConstant.gson.toJson(expensesReimbursementRequest), applicationConstantConfig.employerServiceBaseUrl+CommonUtils.getExpensesReimbursementApprovalList);
	}
	@Override
	public String getErupiLinkAccountDetails(String token,
			EncriptResponse erupiLinkAccountDetailsRequest) {
		return CommonUtility.userRequest(token,MessageConstant.gson.toJson(erupiLinkAccountDetailsRequest), applicationConstantConfig.employerServiceBaseUrl+CommonUtils.getErupiLinkAccountDetails);
	} 
	
	@Override
	public String cApproveReject(String token,
			EncriptResponse erupiLinkAccountDetailsRequest) {
		return CommonUtility.userRequest(token,MessageConstant.gson.toJson(erupiLinkAccountDetailsRequest), applicationConstantConfig.employerServiceBaseUrl+CommonUtils.cApproveReject);
	}
	
	@Override
	public String showLinkedAccAmount(String token,
			EncriptResponse erupiLinkAccountDetailsRequest) {
		return CommonUtility.userRequest(token,MessageConstant.gson.toJson(erupiLinkAccountDetailsRequest), applicationConstantConfig.employerServiceBaseUrl+CommonUtils.showLinkedAccAmount);
	}
	
	@Override
	public String SaveCotodelbankDetails(String token,
			EncriptResponse erupiLinkAccountDetailsRequest) {
		return CommonUtility.userRequest(token,MessageConstant.gson.toJson(erupiLinkAccountDetailsRequest), applicationConstantConfig.employerServiceBaseUrl+CommonUtils.saveCotodelbankdetails);
	}
	
	@Override
	public String getSavedCBankDetails(String token,
			EncriptResponse erupiLinkAccountDetailsRequest) {
		return CommonUtility.userRequest(token,MessageConstant.gson.toJson(erupiLinkAccountDetailsRequest), applicationConstantConfig.employerServiceBaseUrl+CommonUtils.getSavedCBankDetails);
	}

	@Override
	public String getExpensesReimbursementDetailById(String token,
			EncriptResponse expensesReimbursementRequest) {
		return CommonUtility.userRequest(token,MessageConstant.gson.toJson(expensesReimbursementRequest), applicationConstantConfig.employerServiceBaseUrl+CommonUtils.getExpensesReimbursementDetailById);
	}

	@Override
	public String approveExpensesById(String token, EncriptResponse expensesReimbursementRequest) {
		return CommonUtility.userRequest(token,MessageConstant.gson.toJson(expensesReimbursementRequest), applicationConstantConfig.employerServiceBaseUrl+CommonUtils.expensesReimbursementUpdate);

	}

	@Override
	public String cashAdvanceRequest(String token, EncriptResponse advanceTravelRequest) {
		return CommonUtility.userRequest(token,MessageConstant.gson.toJson(advanceTravelRequest), applicationConstantConfig.employerServiceBaseUrl+CommonUtils.cashAdvanceTravelRequest);
	}

	@Override
	public String getCashAdanceRequestData(String token, EncriptResponse advanceTravelRequest) {
		return CommonUtility.userRequest(token,MessageConstant.gson.toJson(advanceTravelRequest), applicationConstantConfig.employerServiceBaseUrl+CommonUtils.getAdvanceTravelRequestData);

	}

	@Override
	public String travelAdvanceRequest(String token, EncriptResponse travelRequest) {
		return CommonUtility.userRequest(token,MessageConstant.gson.toJson(travelRequest), applicationConstantConfig.employerServiceBaseUrl+CommonUtils.travelAdvanceTravelRequest);
	}

	@Override
	public String getTravelReviewData(String token, EncriptResponse advanceTravelRequest) {
		return CommonUtility.userRequest(token,MessageConstant.gson.toJson(advanceTravelRequest), applicationConstantConfig.employerServiceBaseUrl+CommonUtils.getTravelReviewData);
	}

	@Override
	public String travelAdvanceRequestUpdate(String token, EncriptResponse travelAdvanceRequestUpdate) {
		return CommonUtility.userRequest(token,MessageConstant.gson.toJson(travelAdvanceRequestUpdate), applicationConstantConfig.employerServiceBaseUrl+CommonUtils.updateTraveRequestData);
	}

	@Override
	public String deleteAdvanceTravel(String token, EncriptResponse travelReimbursement) {
		return CommonUtility.userRequest(token,MessageConstant.gson.toJson(travelReimbursement), applicationConstantConfig.employerServiceBaseUrl+CommonUtils.deleteTravelReimbursement);
	}

	@Override
	public String getAdanceCashTravelApprpvalList(String token, EncriptResponse advanceTravelRequest) {
		return CommonUtility.userRequest(token,MessageConstant.gson.toJson(advanceTravelRequest), applicationConstantConfig.employerServiceBaseUrl+CommonUtils.getAdanceCashTravelApprpvalList);
	}

	@Override
	public String approveAdvanceTravelRequest(String token, EncriptResponse advanceTravelRequest) {
		return CommonUtility.userRequest(token,MessageConstant.gson.toJson(advanceTravelRequest), applicationConstantConfig.employerServiceBaseUrl+CommonUtils.approveAdvanceTravelRequest);
	}

	@Override
	public String getCashAdvanceDetailById(String token, EncriptResponse advanceTravelRequest) {
		return CommonUtility.userRequest(token,MessageConstant.gson.toJson(advanceTravelRequest), applicationConstantConfig.employerServiceBaseUrl+CommonUtils.getCashAdvanceDetailById);
	}

	@Override
	public String getTravelRequestApprovalList(String token, EncriptResponse jsonObject) {
		return CommonUtility.userRequest(token,MessageConstant.gson.toJson(jsonObject), applicationConstantConfig.employerServiceBaseUrl+CommonUtils.getTravelRequestApprovalList);
	}

	@Override
	public String advanceTravelById(String token, EncriptResponse jsonObject) {
		return CommonUtility.userRequest(token,MessageConstant.gson.toJson(jsonObject), applicationConstantConfig.employerServiceBaseUrl+CommonUtils.getTravelRequestRequestById);
	}
	
}
