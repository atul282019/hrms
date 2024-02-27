package com.cotodel.hrms.web.service;

import org.springframework.stereotype.Repository;

import com.cotodel.hrms.web.response.EmployeePayrollRequest;
import com.cotodel.hrms.web.response.EmployeeProfileRequest;
import com.cotodel.hrms.web.response.PayrollRequest;
import com.cotodel.hrms.web.response.UserRegistrationRequest;

@Repository
public interface PayrollService {

	String getPayrollMaster(String token, PayrollRequest payrollRequest);

	String savePayrollDetail(String token, EmployeePayrollRequest employeePayrollRequest);

}
