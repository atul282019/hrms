package com.cotodel.hrms.web.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cotodel.hrms.web.function.common.CommonUtils;
import com.cotodel.hrms.web.properties.ApplicationConstantConfig;
import com.cotodel.hrms.web.service.DepartmentMasterService;
import com.cotodel.hrms.web.util.CommonUtility;
import com.cotodel.hrms.web.util.EncriptResponse;
import com.cotodel.hrms.web.util.MessageConstant;

@Service
public class DepartmentMasterServiceImpl implements DepartmentMasterService{
	@Autowired
	public ApplicationConstantConfig applicationConstantConfig;

	@Override
	public String getDepartmentMaster(String token,EncriptResponse departmentMaster) {
		// TODO Auto-generated method stub
		return CommonUtility.userRequest(token,MessageConstant.gson.toJson(departmentMaster), applicationConstantConfig.masterServiceBaseUrl +CommonUtils.getDepartmentMaster);
	}

}
