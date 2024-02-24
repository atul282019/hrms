package com.cotodel.hrms.web.response;

import java.io.Serializable;

public class UserDetailsEntity implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8729415195374956628L;

	private Long userid;

	private String name;

	private String mobile;

	private String email;

	private Long orgid;

	private String username;

	public String createBy;

	private int status;
	
	private Integer roleId;

	private String isaadharVerify;

	private String orgName;
	
	private String orgType;

	public Long getUserid() {
		return userid;
	}

	public void setUserid(Long userid) {
		this.userid = userid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Long getOrgid() {
		return orgid;
	}

	public void setOrgid(Long orgid) {
		this.orgid = orgid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public String getIsaadharVerify() {
		return isaadharVerify;
	}

	public void setIsaadharVerify(String isaadharVerify) {
		this.isaadharVerify = isaadharVerify;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getOrgType() {
		return orgType;
	}

	public void setOrgType(String orgType) {
		this.orgType = orgType;
	}
	
	
	
}
