package com.cotodel.hrms.web.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeFamilyDetailRequest {
	
	private Integer employerId;
	private Integer employeeId;
	private String name;
	private String dob;
	private String relation;
	private String nominee;
	private String insuranceNo;
	private String mobile;
	private String email;
	private String remark;
	

}
