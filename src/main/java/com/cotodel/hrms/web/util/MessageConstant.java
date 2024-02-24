package com.cotodel.hrms.web.util;

import com.google.gson.Gson;

public class MessageConstant {

	public static final String RESPONSE_SUCCESS = "SUCCESS";
	public static final String RESPONSE_FAILED = "FAILURE";

	public static final boolean TRUE = true;
	public static final boolean FALSE = false;

	public static final String USER_NOT_LOGIN = "Kindly Login First";	
	
	public static final Gson gson = new Gson();
	
	//JWT constant start

	public static final String USER = "USER";
	public static final String SECRET = "mNJIkdje2343nns";
	public static final long EXPIRATION_TIME = 1900000 ; // 15 mins
	public static final String TOKEN_PREFIX = "Bearer ";
	public static final String HEADER_STRING = "Authorization";

	//JWT constant ends

}
