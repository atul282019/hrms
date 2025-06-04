package com.cotodel.hrms.web.response;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VehicleManagementBulkUploadRequest {
	
	@NotNull(message = "Organization cannot be null")
    @Min(value = 1, message = "Organization ID must be greater than or equal to 1")
	private Long orgId;
	@NotNull(message = "file cannot be null")
	private String file;
	private String fileName;
	private String response;	
	private String createdBy;	
	private String clientKey;
	private String hash;

}
