package com.cotodel.hrms.web.service;

import org.springframework.stereotype.Repository;

import com.cotodel.hrms.web.response.VoucherTypeMaster;
import com.cotodel.hrms.web.util.EncriptResponse;



@Repository
public interface VoucherTypeMasterService {
	public String saveVoucherTypeMaster(String token,EncriptResponse voucherTypeMaster);
	public String getVoucherTypeMasterList(String token,EncriptResponse voucherTypeMaster);
	public String updatevoucherTypeMasterStatus(String token,EncriptResponse voucherTypeMaster);
	
	public String createVoucher(String token,EncriptResponse VoucherCreateRequest);
	public String getRequestedVoucherList(String token,EncriptResponse VoucherGetRequest);
	public String erupiVoucherRequestByMngId(String token, EncriptResponse encryptedRequest);
	public String approveRejectVoucher(String token, EncriptResponse encryptedRequest);
	public String getRequestedVoucherApproveList(String token, EncriptResponse encryptedRequest);
	
}
  