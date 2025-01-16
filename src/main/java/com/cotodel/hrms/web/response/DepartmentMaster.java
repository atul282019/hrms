package com.cotodel.hrms.web.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DepartmentMaster {
	private Integer id;
	private Integer orgId;
	private String departmentName;
	private Integer status;
	private String creationDate;
	private String createdBy;
}
