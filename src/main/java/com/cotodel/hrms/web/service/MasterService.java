package com.cotodel.hrms.web.service;

import org.springframework.stereotype.Repository;

import com.cotodel.hrms.web.response.UserRegistrationRequest;

@Repository
public interface MasterService {

	String getStateMaster(String token,UserRegistrationRequest userForm);

	String getOrgMaster(String token,UserRegistrationRequest userForm);

}
