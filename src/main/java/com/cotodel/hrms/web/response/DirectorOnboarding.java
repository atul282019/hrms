package com.cotodel.hrms.web.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DirectorOnboarding {
	
private String name;
private String id;
private String mobile;
private String joingDate;
private String remark;
private String status;

}
