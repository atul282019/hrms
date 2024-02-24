package com.cotodel.hrms.web.response;

import java.io.Serializable;

public class CreateSuperAdminRequest implements Serializable{
	

	/**
	 * 
	 */
	private static final long serialVersionUID = -5145118965670277166L;

	public Long orgid;

	public String name;

	public String email;	
	
	public String mobile;
	
	private String otp;

	
	public String getOtp() {
		return otp;
	}

	public void setOtp(String otp) {
		this.otp = otp;
	}

	public Long getOrgid() {
		return orgid;
	}

	public void setOrgid(Long orgid) {
		this.orgid = orgid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	@Override
	public String toString() {
		return "CreateSuperAdminRequest [orgid=" + orgid + ", name=" + name + ", email=" + email + ", mobile=" + mobile
				+ "]";
	}
	
}
