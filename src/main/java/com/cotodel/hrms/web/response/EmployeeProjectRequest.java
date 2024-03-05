package com.cotodel.hrms.web.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeProjectRequest {
	
	private Long employeeId;
	private String region;
	private String projectName;
	private String roleInProject;
	private String assignFromDate;
	private String assignToDate;
	private String sharingPercentage;	
	private String technicalSupport;	
	private String remarks;

}
