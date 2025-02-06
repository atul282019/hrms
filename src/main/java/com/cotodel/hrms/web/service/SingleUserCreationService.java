package com.cotodel.hrms.web.service;

import org.springframework.stereotype.Repository;

import com.cotodel.hrms.web.response.UserRegistrationRequest;
import com.cotodel.hrms.web.response.UserWaitList;
import com.cotodel.hrms.web.util.EncriptResponse;

@Repository
public interface SingleUserCreationService {

	String singleUserCreationEncript(String token, EncriptResponse userForm);
	String singleUserCreation(String token, UserRegistrationRequest userForm);

	String getUser(String token, UserRegistrationRequest userForm);

	String userWaitList(String token, UserWaitList userWaitList);

}
