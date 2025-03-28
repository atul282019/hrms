package com.cotodel.hrms.web.service;

import org.springframework.stereotype.Repository;

import com.cotodel.hrms.web.response.EmployeeMassterRequest;
import com.cotodel.hrms.web.response.ErupiLinkBankAccount;
import com.cotodel.hrms.web.response.ErupiVoucherMaster;
import com.cotodel.hrms.web.response.MccMaster;
import com.cotodel.hrms.web.response.UserRegistrationRequest;
import com.cotodel.hrms.web.util.EncriptResponse;

@Repository
public interface MasterService {

//	String getStateMaster(String token,EncriptResponse userForm);
//
//	String getOrgMaster(String token,EncriptResponse userForm);
//
//	String getPermission(String token, EncriptResponse userForm);

	String getRole(String token, EncriptResponse userForm);

	String getBankMaster(String token, EncriptResponse userForm);

	String getVoucherDescriptionByVoucherCode(String token, EncriptResponse erupiVoucherMaster);

	String getBankDetailByAccountNo(String token, EncriptResponse erupiLinkBankAccount);

	String getmccMasterListByPurposeCode(String token, EncriptResponse mccMaster);

	String getmccMasterDetailsByPurposeCodeAndMcc(String token, EncriptResponse mccMaster);

	String getEmployeeType(String token, EncriptResponse employeeMassterRequest);

	String getEmployeeListMaster(String token, EncriptResponse employeeMassterRequest);

	String getofficeLocationMaster(String token, EncriptResponse employeeMassterRequest);

	String getVoucherListWithIcon(String token, EncriptResponse employeeMassterRequest);

	String getPurposeListByVoucherCode(String token, EncriptResponse employeeMassterRequest);

	String getBankListWithVocher(String token, EncriptResponse employeeMassterRequest);

	String voucherCreateSummaryDetailByAccount(String token, EncriptResponse employeeMassterRequest);

}
