package com.cotodel.hrms.web.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeOnboardingManagerRequest {
	private Long id;
	private Long employerId;
	private Long employeeId;
	private Long managerId;
	private String loginuser;
	private String clientKey;
	private String hash;   
}
