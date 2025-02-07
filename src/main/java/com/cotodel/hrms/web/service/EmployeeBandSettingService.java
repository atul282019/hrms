package com.cotodel.hrms.web.service;

import org.springframework.stereotype.Repository;

import com.cotodel.hrms.web.response.EmployeeBandSettingResponse;
import com.cotodel.hrms.web.util.EncriptResponse;

@Repository
public interface EmployeeBandSettingService {
	
	String saveEmployeeBandTier(String token, EncriptResponse employeeBandSettingResponse);

	String getEmployeeBandTier(String token, EncriptResponse employeeBandSettingResponse);

}
