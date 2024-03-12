package com.cotodel.hrms.web.response;

import java.io.Serializable;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDetailsNewRequest  implements Serializable{
	
	private static final long serialVersionUID = -5145118965670277166L;
	
	private Long employerId;	
	private String firstName;	
	private String middleName;	
	private String lastName;	
	private String dateOfBirth;	
	private String nationality;	
	private String maritalStatus;	
	private String dateOfJoining;
	private String designation;	
	private String gender;	
	private String department;	
	private String location;	
	private String panNo;	
	private String esiNo;	
	private String accountNumber;	
	private String ifscCode;	
	private String uanNo;	
	private String bankName;	
	private String remarks;	
	private String briefDescription;	
//	public  MultipartFile docfile;	
//	public  MultipartFile sigfile;
	private  String docfile;
	private  String docFileName;
	private  String sigfile;
	private  String sigFileName;
	private String serviceStatus;
	private String employeeType;	
	private String category;	
	private Boolean active=false;
	private String response;
	private Long id;
}
