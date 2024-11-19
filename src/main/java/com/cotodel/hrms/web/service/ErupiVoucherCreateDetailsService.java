package com.cotodel.hrms.web.service;

import org.springframework.stereotype.Repository;

import com.cotodel.hrms.web.response.ErupiVoucherCreateDetails;

@Repository
public interface ErupiVoucherCreateDetailsService {

	String createSingleVoucher(String token, ErupiVoucherCreateDetails erupiVoucherCreateDetails);

	String getIssueVoucherList(String token, ErupiVoucherCreateDetails erupiVoucherCreateDetails);

	String getVoucherSummaryList(String token, ErupiVoucherCreateDetails erupiVoucherCreateDetails);

	String getPrimaryBankDetailsByOrgId(String token, ErupiVoucherCreateDetails erupiVoucherCreateDetails);

	String revokeCreatedVoucher(String token, ErupiVoucherCreateDetails erupiVoucherCreateDetails);

}
