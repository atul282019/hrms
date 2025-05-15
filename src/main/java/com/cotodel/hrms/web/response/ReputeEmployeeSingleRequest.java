package com.cotodel.hrms.web.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReputeEmployeeSingleRequest {
	
	private String endpoint;
	private String accessToken;
	private String employeeId;

}
