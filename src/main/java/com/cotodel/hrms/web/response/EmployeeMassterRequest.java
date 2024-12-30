package com.cotodel.hrms.web.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeMassterRequest {
	
	public Integer employeeId;
	public Integer orgId;
	public String purposeCode;

}
