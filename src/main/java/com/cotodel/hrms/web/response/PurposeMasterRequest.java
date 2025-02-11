package com.cotodel.hrms.web.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PurposeMasterRequest {
	
	private Long id;
	private String mcc;
	private String mccDesc;
	private String purposeCode;
	private String response;
}
