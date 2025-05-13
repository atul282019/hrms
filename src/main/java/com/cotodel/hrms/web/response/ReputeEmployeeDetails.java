package com.cotodel.hrms.web.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReputeEmployeeDetails {
	
	private String companyId;
	private String company_cotodel;
	private String companyName;
	private String hrmsId;
	private String employeeId;
	private String employeeName;
	private String dob;
	private String gender;
	private String department;
	private String employmentType;
	private String designation;
	private String doj;
	private String officialEmailId;
	private String personalEmailId;
	private String mobileNumber;
	private String pincode;
	private String employmentStatus;
	private String grade;
	private String managerEmployeeId;
	private String reputeEmployeeId;
	
}
