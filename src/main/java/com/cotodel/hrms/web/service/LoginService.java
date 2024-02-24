package com.cotodel.hrms.web.service;

import com.cotodel.hrms.web.response.UserRegistrationRequest;

public interface LoginService {

	String sendOtp(String token, String userName, String mobile);
	String verifyOtp(String token, String userName, String mob, String otp);
	String registerUser(String token,UserRegistrationRequest userForm);
	String verifyRegisterUser(String token,UserRegistrationRequest userForm);
	public void sendEmailToEmployee(UserRegistrationRequest userForm);
	public void sendEmailVerificationCompletion(UserRegistrationRequest userForm);
	String getToken(String companyId);

}
