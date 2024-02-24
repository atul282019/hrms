package com.cotodel.hrms.web.response;

import java.io.Serializable;

import org.springframework.web.multipart.MultipartFile;

public class CreateOrganisationRequest implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -854609680520331009L;

	//orgnisation name
	public String orgname;

	//orgnisation name
	public String orgtype;

	//orgnisation name
	public String gst;

	//orgnisation name
	public String tin;

	//orgnisation name
	public String contactName;

	//orgnisation name
	public String email;	
	
	//mobile number
	public String mobile;

	public MultipartFile file;
	
	public String expiry;
	
	public String domain;

	public String address;
	
	public String getOrgname() {
		return orgname;
	}

	public void setOrgname(String orgname) {
		this.orgname = orgname;
	}

	public String getOrgtype() {
		return orgtype;
	}

	public void setOrgtype(String orgtype) {
		this.orgtype = orgtype;
	}

	public String getGst() {
		return gst;
	}

	public void setGst(String gst) {
		this.gst = gst;
	}

	public String getTin() {
		return tin;
	}

	public void setTin(String tin) {
		this.tin = tin;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
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

	public String getExpiry() {
		return expiry;
	}

	public void setExpiry(String expiry) {
		this.expiry = expiry;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	@Override
	public String toString() {
		return "CreateOrganisationRequest [orgname=" + orgname + ", orgtype=" + orgtype + ", gst=" + gst + ", tin="
				+ tin + ", contactName=" + contactName + ", email=" + email + ", mobile=" + mobile + ", file=" + file
				+ ", expiry=" + expiry + ", domain=" + domain + ", address=" + address + "]";
	}

	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}


	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	
	

}
