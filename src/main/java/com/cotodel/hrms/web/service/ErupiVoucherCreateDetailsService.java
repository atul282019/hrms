package com.cotodel.hrms.web.service;

import org.springframework.stereotype.Repository;

import com.cotodel.hrms.web.response.BulkVoucherRequest;
import com.cotodel.hrms.web.response.ErupiBulkVoucherCreateRequest;
import com.cotodel.hrms.web.response.ErupiVoucherCreateDetails;
import com.cotodel.hrms.web.response.ExistingUserVoucherCreationRequest;
import com.cotodel.hrms.web.response.RevokeVoucher;
import com.cotodel.hrms.web.response.RoleAccessRequest;
import com.cotodel.hrms.web.response.SingleVoucherCreationRequest;
import com.cotodel.hrms.web.util.EncriptResponse;

@Repository
public interface ErupiVoucherCreateDetailsService {

	String createSingleVoucher(String token, ErupiVoucherCreateDetails erupiVoucherCreateDetails);

	String getIssueVoucherList(String token, EncriptResponse erupiVoucherCreateDetails);
	
	String erupiVoucherCreateListRedeem(String token, EncriptResponse erupiVoucherCreateDetails);

	String getVoucherSummaryList(String token, EncriptResponse erupiVoucherCreateDetails);

	String getPrimaryBankDetailsByOrgId(String token, EncriptResponse erupiVoucherCreateDetails);

	//String revokeCreatedVoucher(String token, RevokeVoucher erupiVoucherCreateDetails);

	String erupiVoucheSmsSend(String token, EncriptResponse erupiVoucherStatusSmsRequest);

	String issueBulkVoucher(String token, ErupiBulkVoucherCreateRequest erupiBulkVoucherCreateRequest);

	String beneficiaryDeleteFromVoucherList(String token, EncriptResponse bulkVoucherRequest);

	String geterupiVoucherOldList(String token, ErupiVoucherCreateDetails erupiVoucherCreateDetails);

	String exitingUserVoucherCreation(String token,ExistingUserVoucherCreationRequest existingUserVoucherCreationRequest);

	String voucherUserSearch(String token, EncriptResponse roleAccessRequest);

	String getTotalVoucherCount(String token, EncriptResponse erupiVoucherCreateDetails);

	String revokeCreatedVoucherSingle(String token, EncriptResponse jsonObject);

	String createSingleVoucherWithMultipleRequest(String token, EncriptResponse jsonObject);

}
