package com.cotodel.hrms.web.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeOnboarding {
	
	private Integer id;
	private Integer employerId;
	private Integer employeeId;	
	private String empOrCont;
	private String name;
	private String email;
	private String mobile;
	private String herDate;
	private String jobTitle;		
	private String depratment;
	private String managerName;
	private String ctc;
	private String location;
	private String residentOfIndia;
	
}
