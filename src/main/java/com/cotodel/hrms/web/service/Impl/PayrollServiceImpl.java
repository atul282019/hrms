package com.cotodel.hrms.web.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cotodel.hrms.web.function.common.CommonUtils;
import com.cotodel.hrms.web.properties.ApplicationConstantConfig;
import com.cotodel.hrms.web.response.EmployeePayrollRequest;
import com.cotodel.hrms.web.response.PayrollRequest;
import com.cotodel.hrms.web.service.PayrollService;
import com.cotodel.hrms.web.util.CommonUtility;
import com.cotodel.hrms.web.util.MessageConstant;

@Service
public class PayrollServiceImpl implements PayrollService{

	@Autowired
	public ApplicationConstantConfig applicationConstantConfig;

	
	@Override
	public String getPayrollMaster(String token, PayrollRequest payrollRequest) {
		return CommonUtility.userRequest(token,MessageConstant.gson.toJson(payrollRequest), applicationConstantConfig.userServiceBaseUrl+CommonUtils.getPayrollList);
	}


	@Override
	public String savePayrollDetail(String token, EmployeePayrollRequest employeePayrollRequest) {
		// TODO Auto-generated method stub
		return CommonUtility.userRequest(token,MessageConstant.gson.toJson(employeePayrollRequest), applicationConstantConfig.employerServiceBaseUrl+CommonUtils.savePayrollDetail);

	}
	
	
}
