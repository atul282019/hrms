package com.cotodel.hrms.web.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class VehicleManagementRequest {
	private Long orgId;
	private Long id;
	private String response;
	private String vehicleSequenceId;

}
