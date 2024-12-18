package com.cotodel.hrms.web.service;

import org.springframework.stereotype.Repository;

import com.cotodel.hrms.web.response.RoleAccessRequest;

@Repository
public interface RoleAccessService {

	String getUserRole(String token, RoleAccessRequest roleAccessRequest);

	String editUserRole(String token, RoleAccessRequest roleAccessRequest);

	String deleteUserRole(String token, RoleAccessRequest roleAccessRequest);

	String userSearch(String token, RoleAccessRequest roleAccessRequest);

}
