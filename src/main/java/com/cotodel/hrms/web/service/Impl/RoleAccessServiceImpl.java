package com.cotodel.hrms.web.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cotodel.hrms.web.function.common.CommonUtils;
import com.cotodel.hrms.web.properties.ApplicationConstantConfig;
import com.cotodel.hrms.web.response.RoleAccessRequest;
import com.cotodel.hrms.web.response.RoleDTO;
import com.cotodel.hrms.web.service.RoleAccessService;
import com.cotodel.hrms.web.util.CommonUtility;
import com.cotodel.hrms.web.util.EncriptResponse;
import com.cotodel.hrms.web.util.MessageConstant;

@Service
public class RoleAccessServiceImpl implements RoleAccessService{


	@Autowired
	public ApplicationConstantConfig applicationConstantConfig;

	@Override
	public String getUserRole(String token, EncriptResponse roleAccessRequest) {
		return CommonUtility.userRequest(token,MessageConstant.gson.toJson(roleAccessRequest), applicationConstantConfig.userServiceBaseUrl+CommonUtils.getUserListWithRole);

	}

	@Override
	public String editUserRole(String token, EncriptResponse roleAccessRequest) {
		return CommonUtility.userRequest(token,MessageConstant.gson.toJson(roleAccessRequest), applicationConstantConfig.userServiceBaseUrl+CommonUtils.editUserListWithRole);
	}

	@Override
	public String deleteUserRole(String token, EncriptResponse roleAccessRequest) {
		return CommonUtility.userRequest(token,MessageConstant.gson.toJson(roleAccessRequest), applicationConstantConfig.userServiceBaseUrl+CommonUtils.deleteUserListWithRole);
	}

	@Override
	public String userSearch(String token, EncriptResponse roleAccessRequest) {
		return CommonUtility.userRequest(token,MessageConstant.gson.toJson(roleAccessRequest), applicationConstantConfig.userServiceBaseUrl+CommonUtils.addUserSearch);
	}

	@Override
	public String editUserRoleDTO(String token, RoleDTO requestDTO) {
		return CommonUtility.userRequest(token,MessageConstant.gson.toJson(requestDTO), applicationConstantConfig.userServiceBaseUrl+CommonUtils.editUserListWithRole);
	}
}
