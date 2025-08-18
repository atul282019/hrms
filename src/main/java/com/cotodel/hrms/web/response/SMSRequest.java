package com.cotodel.hrms.web.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SMSRequest {
	private String value;
	private String message;
	private String userName;
	private String password1;
	private String password2;
	private String password3;
	private String password4;
	private String password5;
	private String password6;
	private String password;
	private String sresult;
	private String otp;
	private String mobile;
	private String orderId;
	private String countdown;
	private String template;
}
