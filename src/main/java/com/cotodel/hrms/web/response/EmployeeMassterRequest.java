package com.cotodel.hrms.web.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeMassterRequest {
	
	private Long id;
	private Integer employeeId;
	private Integer orgId;
	private String  purposeCode;
	private String  accNumber;
	private String timePeriod;
	private String userName;
}
