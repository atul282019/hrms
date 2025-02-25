package com.cotodel.hrms.web.response;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserWaitList implements Serializable{
	
	private static final long serialVersionUID = 4615208660281419839L;

	
	private Long id ;	
	private String companyName;	
	private String companySize;	
	private String industry;	
	private String contactPersonName;	
	private String contactNumber;  
	private String email;
	private String response;
	private String status;
	private boolean erupistatus ;
	
	
	
	
	
}