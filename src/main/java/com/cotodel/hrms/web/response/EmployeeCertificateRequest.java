package com.cotodel.hrms.web.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeCertificateRequest {

	private Integer employerId;
	private Long employeeId;	
	private String docName;
	private String institutes;
	private String docType;
	private String docDate;
	private String docNo;
	private String remarks;		
	private String response;
}
