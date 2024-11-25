package com.cotodel.hrms.web.service;

import org.springframework.stereotype.Repository;

import com.cotodel.hrms.web.response.BulkVoucherRequest;

@Repository
public interface BulkVoucherService {

	String saveBulkVoucher(String token, BulkVoucherRequest bulkVoucherRequest);
	

}
