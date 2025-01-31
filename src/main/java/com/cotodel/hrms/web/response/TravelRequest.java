package com.cotodel.hrms.web.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TravelRequest {
	
	private Integer employeeId;
	private Integer employerId;
	private String requestType;
	private List<TravelAddRequest> travelReimbursement;

}
