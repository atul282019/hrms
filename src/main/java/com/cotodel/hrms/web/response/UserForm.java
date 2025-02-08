package com.cotodel.hrms.web.response;

public class UserForm {
	
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
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword1() {
		return password1;
	}
	public void setPassword1(String password1) {
		this.password1 = password1;
	}
	public String getPassword2() {
		return password2;
	}
	public void setPassword2(String password2) {
		this.password2 = password2;
	}
	public String getPassword3() {
		return password3;
	}
	public void setPassword3(String password3) {
		this.password3 = password3;
	}
	public String getPassword4() {
		return password4;
	}
	public void setPassword4(String password4) {
		this.password4 = password4;
	}
	public String getPassword5() {
		return password5;
	}
	public void setPassword5(String password5) {
		this.password5 = password5;
	}
	public String getPassword6() {
		return password6;
	}
	public void setPassword6(String password6) {
		this.password6 = password6;
	}
	
	public String getSresult() {
		return sresult;
	}
	public void setSresult(String sresult) {
		this.sresult = sresult;
	}
	public String getOtp() {
		return otp;
	}
	public void setOtp(String otp) {
		this.otp = otp;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "UserForm [userName=" + userName + ", password1=" + password1 + ", password2=" + password2
				+ ", password3=" + password3 + ", password4=" + password4 + ", password5=" + password5 + ", password6="
				+ password6 + ", sresult=" + sresult + ", otp=" + otp + ", mobile=" + mobile + ", orderId=" + orderId
				+ "]";
	}
	
	
	
	
	

}
