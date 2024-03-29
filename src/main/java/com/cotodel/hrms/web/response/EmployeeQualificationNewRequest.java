package com.cotodel.hrms.web.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeQualificationNewRequest {
	
	private Integer employerId;
	private Integer employeeId;
	private String fromDate;
	private String toDate;
	private String education;
	private String institutes;
	private String referenceType;
	private String remarks;
	private  String docfile;
	private  String docFileName;
	//public MultipartFile attachment;

}
