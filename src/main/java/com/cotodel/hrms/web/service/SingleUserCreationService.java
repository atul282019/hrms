package com.cotodel.hrms.web.service;

import org.springframework.stereotype.Repository;

import com.cotodel.hrms.web.response.UserRegistrationRequest;

@Repository
public interface SingleUserCreationService {

	String singleUserCreation(String token, UserRegistrationRequest userForm);

	String getUser(String token, UserRegistrationRequest userForm);

}
