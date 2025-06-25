package com.cotodel.hrms.web.service;

import org.springframework.stereotype.Repository;

import com.cotodel.hrms.web.response.BulkEmployeeRequest;
import com.cotodel.hrms.web.util.EncriptResponse;


@Repository
public interface BulkEmployeeService {

	String saveBulkDetail(String token, BulkEmployeeRequest bulkEmployeeRequest);
	public String saveBulkEmpDetail(String token, EncriptResponse jsonObject);
	public String createBulkEmpDetail(String token, EncriptResponse jsonObject);
	
	

	


}
