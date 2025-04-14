package com.cotodel.hrms.web.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExpanceTravelAdvanceRequest {
	
	private Integer id;
	private Integer employerId;
	private String allowEmployeesTravel;
	private String allowEmployeesCash;
	private String employeesAllow;
	private String nameEmployeesCash;
	private String daysDisbursalCash;
	
	private String hash;
	private String clientKey;
	

}
