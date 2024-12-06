package com.cotodel.hrms.web.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class managerMaster {

	private Integer id;
	private String managerLblDesc;
	private String createdBy;
	private String orgId;
	private String remarks;
	private Integer status;
	
}
