package com.cotodel.hrms.web.service;

import org.springframework.stereotype.Repository;

import com.cotodel.hrms.web.response.EmployeeProfileRequest;

@Repository
public interface CompanyService {

	String saveCompany(String token, EmployeeProfileRequest employeeProfileRequest);

	String getCompanyProfileStatus(String token, EmployeeProfileRequest employeeProfileRequest);
	String getorgsubType(String token, EmployeeProfileRequest employeeProfileRequest);
	String getpayrollDetails(String token, EmployeeProfileRequest employeeProfileRequest);

}
