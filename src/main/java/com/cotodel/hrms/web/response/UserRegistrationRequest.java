package com.cotodel.hrms.web.response;

import java.io.Serializable;

public class UserRegistrationRequest implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4615208660281419839L;

	private String username;
	
	private String email;

	private String mobile;
	
	private String orgname;
	
	private String noofEmp;
	
	private String privacyCheck;
	
	private String whatsupCheck;
	
	private Integer employerId;
	
	public Integer getEmployerId() {
		return employerId;
	}

	public void setEmployerId(Integer employerId) {
		this.employerId = employerId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getOrgname() {
		return orgname;
	}

	public void setOrgname(String orgname) {
		this.orgname = orgname;
	}

	public String getNoofEmp() {
		return noofEmp;
	}

	public void setNoofEmp(String noofEmp) {
		this.noofEmp = noofEmp;
	}

	public String getPrivacyCheck() {
		return privacyCheck;
	}

	public void setPrivacyCheck(String privacyCheck) {
		this.privacyCheck = privacyCheck;
	}

	public String getWhatsupCheck() {
		return whatsupCheck;
	}

	public void setWhatsupCheck(String whatsupCheck) {
		this.whatsupCheck = whatsupCheck;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "UserRegistrationRequest [username=" + username + ", email=" + email + ", mobile=" + mobile
				+ ", orgname=" + orgname + ", noofEmp=" + noofEmp + ", privacyCheck=" + privacyCheck + ", whatsupCheck="
				+ whatsupCheck + "]";
	}
	

	

	
	
}