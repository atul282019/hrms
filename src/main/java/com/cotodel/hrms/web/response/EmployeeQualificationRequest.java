package com.cotodel.hrms.web.response;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeQualificationRequest {
	
	private Integer employerId;
	private Integer employeeId;
	private String fromDate;
	private String toDate;
	private String education;
	private String institutes;
	private String referenceType;
	private String remarks;
	//public MultipartFile attachment;

}
