package com.cotodel.hrms.web.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDeactiveRequest {
	
	private Integer id;
	private Integer userDetailsId;
	private Integer employerId;
	private String status;

}
