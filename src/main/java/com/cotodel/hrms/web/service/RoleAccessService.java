package com.cotodel.hrms.web.service;

import org.springframework.stereotype.Repository;

import com.cotodel.hrms.web.response.RoleAccessRequest;
import com.cotodel.hrms.web.response.RoleDTO;
import com.cotodel.hrms.web.util.EncriptResponse;

@Repository
public interface RoleAccessService {

	String getUserRole(String token, EncriptResponse roleAccessRequest);

	String editUserRole(String token, EncriptResponse roleAccessRequest);

	String deleteUserRole(String token, EncriptResponse roleAccessRequest);

	String userSearch(String token, EncriptResponse roleAccessRequest);

	String editUserRoleDTO(String token, RoleDTO requestDTO);

}
