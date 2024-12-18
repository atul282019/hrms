package com.cotodel.hrms.web.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleAccessRequest {
	
	    private Integer orgId;
	    private Integer id;
	    private String userName;
	    private String email;
	    private String mobile;
	    private String createdby;
	    private List<String> roleDesc;

}
