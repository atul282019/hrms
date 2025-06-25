package com.cotodel.hrms.web.response;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeBulkUploadRequest {

	private Long orgId;	
	private String file;
	private String fileName;
	private String response;
	private String createdby;
	private String clientKey;
	private String hash;

}
