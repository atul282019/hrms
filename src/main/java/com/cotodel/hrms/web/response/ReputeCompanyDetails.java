package com.cotodel.hrms.web.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReputeCompanyDetails {
	
	private String sub;
	private String employeeName;	
	private String hrmsName;	
	private String role;	
	private String iss;	
	private String employeeId;	
	private String userId;	
	private String[] authorities;	
	private String sid;	
	private String aud;	
	private String companyId;	
	private String phoneNumber;	
	private String azp;	 
	private String hrmsId;
	private String jti;
	private String hrmsLogoUrl;
	private String email;
	private String auth_time;
	private String exp;
	private String iat;
}
