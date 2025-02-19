package com.cotodel.hrms.web.service;

import org.springframework.stereotype.Repository;

import com.cotodel.hrms.web.response.UserRegistrationRequest;
import com.cotodel.hrms.web.response.UserWaitList;
import com.cotodel.hrms.web.util.EncriptResponse;

@Repository
public interface SingleUserCreationService {

	String singleUserCreationEncript(String token, EncriptResponse userForm);
	String singleUserCreation(String token, EncriptResponse userForm);

	String getUser(String token, EncriptResponse userForm);

	String userWaitList(String token, EncriptResponse userWaitList);
	String reputeRequestSave(String token, EncriptResponse jsonObject);

}
