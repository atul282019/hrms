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
	
	private boolean erupistatus ;
	
	private String captcha;
	
	private String role;
    private String companyId;
    private String hrmsId;
    private String hrmsName;
    private String employeeName;
   
	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public String getHrmsName() {
		return hrmsName;
	}

	public void setHrmsName(String hrmsName) {
		this.hrmsName = hrmsName;
	}

	public String getcaptcha() {
		return captcha;
	}

	public void setcaptcha(String captcha) {
		this.captcha = captcha;
	}
	
	
	public Integer getEmployerId() {
		return employerId;
	}

	public boolean isErupistatus() {
		return erupistatus;
	}

	public void setErupistatus(boolean erupistatus) {
		this.erupistatus = erupistatus;
	}

//	public String getCaptcha() {
//		return captcha;
//	}
//
//	public void setCaptcha(String captcha) {
//		this.captcha = captcha;
//	}

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

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getHrmsId() {
		return hrmsId;
	}

	public void setHrmsId(String hrmsId) {
		this.hrmsId = hrmsId;
	}

	@Override
	public String toString() {
		return "UserRegistrationRequest [username=" + username + ", email=" + email + ", mobile=" + mobile
				+ ", orgname=" + orgname + ", noofEmp=" + noofEmp + ", privacyCheck=" + privacyCheck + ", whatsupCheck="
				+ whatsupCheck + ", employerId=" + employerId + ", erupistatus=" + erupistatus + ", captcha=" + captcha
				+ ", role=" + role + ", companyId=" + companyId + ", hrmsId=" + hrmsId + ", getcaptcha()="
				+ getcaptcha() + ", getEmployerId()=" + getEmployerId() + ", isErupistatus()=" + isErupistatus()
				+ ", getUsername()=" + getUsername() + ", getEmail()=" + getEmail() + ", getMobile()=" + getMobile()
				+ ", getOrgname()=" + getOrgname() + ", getNoofEmp()=" + getNoofEmp() + ", getPrivacyCheck()="
				+ getPrivacyCheck() + ", getWhatsupCheck()=" + getWhatsupCheck() + ", getRole()=" + getRole()
				+ ", getCompanyId()=" + getCompanyId() + ", getHrmsId()=" + getHrmsId() + ", getClass()=" + getClass()
				+ ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
	}


	

	
	
}