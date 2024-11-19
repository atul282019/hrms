package com.cotodel.hrms.web.service;

import org.springframework.stereotype.Repository;

import com.cotodel.hrms.web.response.VoucherTypeMaster;



@Repository
public interface VoucherTypeMasterService {
	public String saveVoucherTypeMaster(String token,VoucherTypeMaster voucherTypeMaster);
	public String getVoucherTypeMasterList(String token,VoucherTypeMaster voucherTypeMaster);
	public String updatevoucherTypeMasterStatus(String token,VoucherTypeMaster voucherTypeMaster);
	
}
