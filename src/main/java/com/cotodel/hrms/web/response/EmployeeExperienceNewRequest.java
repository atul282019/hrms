package com.cotodel.hrms.web.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeExperienceNewRequest {

	private Integer employeeId;
	private Integer employerId;
	private String designation;
	private String company;	
	private String fromDate;	
	private String toDate;
	private String noOfYear;
	private String country;
	private String referenceEmail;
	private String referenceMobile;	
	private String remarks;
	private  String docfile;
	private  String docFileName;
}
