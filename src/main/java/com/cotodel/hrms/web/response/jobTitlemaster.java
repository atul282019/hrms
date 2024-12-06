package com.cotodel.hrms.web.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class jobTitlemaster {
 
	private Integer id;
	private String jobDisc;
	private Integer status;
	private Integer managerLblId;
	private String creationDate;
	private String createdBy;
	private Integer orgId;
	private String updationDate;
	private String updatedBy;
	private String remarks;
	
	
	
}
