package com.cotodel.hrms.web.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeBandTiersResponse {
	
	private Integer employerId;
	private String  employeeBand;
	private String  additionalTiers;

}
