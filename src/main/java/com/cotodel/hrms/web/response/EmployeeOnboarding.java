package com.cotodel.hrms.web.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeOnboarding {
	
	private Integer id;
	//private Integer userDetailsId;
	private Long userDetailsId;
	private Integer employerId;
	private Integer employeeId;	
	private String empOrCont;
	private String name;
	private String email;
	private String mobile;
	private String herDate;
	private String jobTitle;		
	private String depratment;
	private Integer managerId;
	private String ctc;
	private String location;
	private String residentOfIndia;
	private boolean updateStatus ;
    private boolean emailStatus ;
    private String proofOfIdentity;
    private String pan;
    private String bankAccountNumber;
    private String ifscCode;
    private String beneficiaryName;    
	private String response;
	private String empPhoto;
	private String managerName;
	private String filename;
	private String filetype;
	private String clientKey;
	private String hash;   
	  
	
	
}
