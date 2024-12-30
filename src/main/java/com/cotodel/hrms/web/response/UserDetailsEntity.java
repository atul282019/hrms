package com.cotodel.hrms.web.response;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDetailsEntity implements Serializable{
	
	private static final long serialVersionUID = -8729415195374956628L;

	private Long userid;

	private String name;

	private String mobile;

	private String email;

	private Long orgid;

	private String username;

	public String createBy;

	private int status;
	
	private Integer user_role;

	private String isaadharVerify;

	private String orgName;
	
	private String orgType;
	
}
