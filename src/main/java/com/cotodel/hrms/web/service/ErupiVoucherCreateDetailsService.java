package com.cotodel.hrms.web.service;

import org.springframework.stereotype.Repository;

import com.cotodel.hrms.web.response.BulkVoucherRequest;
import com.cotodel.hrms.web.response.ErupiBulkVoucherCreateRequest;
import com.cotodel.hrms.web.response.ErupiVoucherCreateDetails;
import com.cotodel.hrms.web.response.ExistingUserVoucherCreationRequest;

@Repository
public interface ErupiVoucherCreateDetailsService {

	String createSingleVoucher(String token, ErupiVoucherCreateDetails erupiVoucherCreateDetails);

	String getIssueVoucherList(String token, ErupiVoucherCreateDetails erupiVoucherCreateDetails);

	String getVoucherSummaryList(String token, ErupiVoucherCreateDetails erupiVoucherCreateDetails);

	String getPrimaryBankDetailsByOrgId(String token, ErupiVoucherCreateDetails erupiVoucherCreateDetails);

	String revokeCreatedVoucher(String token, ErupiVoucherCreateDetails erupiVoucherCreateDetails);

	String erupiVoucheSmsSend(String token, ErupiVoucherCreateDetails erupiVoucherCreateDetails);

	String issueBulkVoucher(String token, ErupiBulkVoucherCreateRequest erupiBulkVoucherCreateRequest);

	String beneficiaryDeleteFromVoucherList(String token, BulkVoucherRequest bulkVoucherRequest);

	String geterupiVoucherOldList(String token, ErupiVoucherCreateDetails erupiVoucherCreateDetails);

	String exitingUserVoucherCreation(String token,ExistingUserVoucherCreationRequest existingUserVoucherCreationRequest);

}
