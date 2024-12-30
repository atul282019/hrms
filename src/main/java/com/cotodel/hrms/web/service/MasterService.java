package com.cotodel.hrms.web.service;

import org.springframework.stereotype.Repository;

import com.cotodel.hrms.web.response.EmployeeMassterRequest;
import com.cotodel.hrms.web.response.ErupiLinkBankAccount;
import com.cotodel.hrms.web.response.ErupiVoucherMaster;
import com.cotodel.hrms.web.response.MccMaster;
import com.cotodel.hrms.web.response.UserRegistrationRequest;

@Repository
public interface MasterService {

	String getStateMaster(String token,UserRegistrationRequest userForm);

	String getOrgMaster(String token,UserRegistrationRequest userForm);

	String getPermission(String token, UserRegistrationRequest userForm);

	String getRole(String token, UserRegistrationRequest userForm);

	String getBankMaster(String token, UserRegistrationRequest userForm);

	String getVoucherDescriptionByVoucherCode(String token, ErupiVoucherMaster erupiVoucherMaster);

	String getBankDetailByAccountNo(String token, ErupiLinkBankAccount erupiLinkBankAccount);

	String getmccMasterListByPurposeCode(String token, MccMaster mccMaster);

	String getmccMasterDetailsByPurposeCodeAndMcc(String token, MccMaster mccMaster);

	String getEmployeeType(String token, EmployeeMassterRequest employeeMassterRequest);

	String getEmployeeListMaster(String token, EmployeeMassterRequest employeeMassterRequest);

	String getofficeLocationMaster(String token, EmployeeMassterRequest employeeMassterRequest);

	String getVoucherListWithIcon(String token, EmployeeMassterRequest employeeMassterRequest);

	String getPurposeListByVoucherCode(String token, EmployeeMassterRequest employeeMassterRequest);

}
