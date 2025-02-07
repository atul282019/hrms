package com.cotodel.hrms.web.service;

import org.springframework.stereotype.Repository;

import com.cotodel.hrms.web.response.CompanyProfileDetail;
import com.cotodel.hrms.web.response.EmployeeProfileRequest;
import com.cotodel.hrms.web.util.EncriptResponse;

@Repository
public interface CompanyService {

	String saveCompany(String token, EncriptResponse employeeProfileRequest);

	String getCompanyProfileStatus(String token, EncriptResponse employeeProfileRequest);
	String getorgsubType(String token, EncriptResponse employeeProfileRequest);
	String getpayrollDetails(String token, EncriptResponse employeeProfileRequest);

	String getGSTDetailsByGSTNumber(String token, EncriptResponse employeeProfileRequest);

	String saveOrganizationDetail(String token, EncriptResponse companyProfileDetail);

}
