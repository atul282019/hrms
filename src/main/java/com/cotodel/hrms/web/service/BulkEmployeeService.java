package com.cotodel.hrms.web.service;

import org.springframework.stereotype.Repository;

import com.cotodel.hrms.web.response.BulkEmployeeRequest;


@Repository
public interface BulkEmployeeService {

	String saveBulkDetail(String token, BulkEmployeeRequest bulkEmployeeRequest);

	


}
