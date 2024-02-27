package com.cotodel.hrms.web.service;

import org.springframework.stereotype.Repository;

import com.cotodel.hrms.web.response.EmployeeDetailRequest;

@Repository
public interface EmployeeDetailService {

	String saveEmployeeDetail(String token, EmployeeDetailRequest employeeDetailRequest);

}
