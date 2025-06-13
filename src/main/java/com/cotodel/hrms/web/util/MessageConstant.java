package com.cotodel.hrms.web.util;

import com.google.gson.Gson;

public class MessageConstant {

	public static final String RESPONSE_SUCCESS = "SUCCESS";
	public static final String RESPONSE_FAILED = "FAILURE";

	public static final boolean TRUE = true;
	public static final boolean FALSE = false;

	public static final String USER_NOT_LOGIN = "Kindly Login First";	
	public static final String USER_NOT_REG = "User is not registered. !!";
	public static final Gson gson = new Gson();
	
	public static final Integer ONE = 1;
	//JWT constant start

	public static final String USER = "USER";
	public static final String SECRET = "mNJIkdje2343nns";
	//public static final long EXPIRATION_TIME = 1900000 ; // 15 mins
	public static final long EXPIRATION_TIME = 60 * 60 * 1000; ; // 15 mins
	
	public static final String TOKEN_PREFIX = "Bearer ";
	public static final String HEADER_STRING = "Authorization";
	public static final String EMAIL_MOBILE_INVALID = "Invalid email or mobile number !!";
	public static final String NAME_MOBILE_INVALID = "Invalid name or mobile number !!";

	//JWT constant ends

}
