package com.cotodel.hrms.web.response;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeExperienceRequest {

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
	public MultipartFile attachment;
}
