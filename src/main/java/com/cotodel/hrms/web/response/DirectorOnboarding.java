package com.cotodel.hrms.web.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DirectorOnboarding {
	
	   	private Long id;	
		private Long orgId;	
		private String name;	
		private String email;	
		private String mobile;	
		private String din;	
		private String designation;		
		private String address;
		private String createdby;
		private String response;

}
