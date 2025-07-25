package com.cotodel.hrms.web.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BrandManagementRequest {
	private Long orgId;
	private String id_number;
	private String createdBy;
	private String ifsc;
	private boolean ifsc_details;
}
