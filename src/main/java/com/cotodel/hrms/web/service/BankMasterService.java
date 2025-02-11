package com.cotodel.hrms.web.service;

import org.springframework.stereotype.Repository;

import com.cotodel.hrms.web.response.BankMaster;
import com.cotodel.hrms.web.response.VoucherTypeMaster;
import com.cotodel.hrms.web.util.EncriptResponse;

@Repository
public interface BankMasterService {
	public String saveBankMaster(String token,EncriptResponse bankMaster);
	public String getBankMasterList(String token,EncriptResponse bankMaster);
	public String getaftersaveBankMasterDetailsList(String token,EncriptResponse bankMaster);
	public String updatebankMasterDetailStatus(String token,EncriptResponse bankMaster);
	
}
