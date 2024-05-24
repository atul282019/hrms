package com.cotodel.hrms.web.service;

import org.springframework.stereotype.Repository;

import com.cotodel.hrms.web.response.EmployeeBandSettingResponse;

@Repository
public interface EmployeeBandSettingService {

	String employeeBand(String token, EmployeeBandSettingResponse employeeBandSettingResponse);

	String saveEmployeeBandTier(String token, EmployeeBandSettingResponse employeeBandSettingResponse);

}
