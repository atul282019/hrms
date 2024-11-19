package com.cotodel.hrms.web.service;

import org.springframework.stereotype.Repository;

import com.cotodel.hrms.web.response.BankMaster;
import com.cotodel.hrms.web.response.VoucherTypeMaster;

@Repository
public interface BankMasterService {
	public String saveBankMaster(String token,BankMaster bankMaster);
	public String getBankMasterList(String token,BankMaster bankMaster);
	public String getaftersaveBankMasterDetailsList(String token,BankMaster bankMaster);
	public String updatebankMasterDetailStatus(String token,BankMaster bankMaster);
	
}
