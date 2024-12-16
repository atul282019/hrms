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
	    private String username;
	    private String email;
	    private String mobile;
	    private String createedby;
	    private List<String> roleDesc;

}
