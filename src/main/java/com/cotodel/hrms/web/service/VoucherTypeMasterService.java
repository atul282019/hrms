package com.cotodel.hrms.web.service;

import org.springframework.stereotype.Repository;

import com.cotodel.hrms.web.response.VoucherTypeMaster;
import com.cotodel.hrms.web.util.EncriptResponse;



@Repository
public interface VoucherTypeMasterService {
	public String saveVoucherTypeMaster(String token,EncriptResponse voucherTypeMaster);
	public String getVoucherTypeMasterList(String token,EncriptResponse voucherTypeMaster);
	public String updatevoucherTypeMasterStatus(String token,EncriptResponse voucherTypeMaster);
	
}
  