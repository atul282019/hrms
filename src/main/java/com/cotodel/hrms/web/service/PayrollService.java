package com.cotodel.hrms.web.service;

import org.springframework.stereotype.Repository;

import com.cotodel.hrms.web.response.EmployeePayrollRequest;
import com.cotodel.hrms.web.util.EncriptResponse;

@Repository
public interface PayrollService {

	String getPayrollMaster(String token, EncriptResponse payrollRequest);

	String savePayrollDetail(String token, EncriptResponse employeePayrollRequest);

}
