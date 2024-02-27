package com.cotodel.hrms.web.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDetailRequest {
	private String employerId;
	private String firstName;
    private String lastName;
    private String dateOfBirth;
    private String gender;
    private String Mobile;
    private String picUrl;
    private String email;
    private String address;
    private String roleId;
    private String username;
    private String pwd;
    private String bankAccount;
    private String ifsc;
    private String urn;
    private String pan;
    private String aadhaar;
    private String extra1;
    private String extra2;
    private String status;
    private String intextra1;
     

    private String dateOfJoining;
    private String designation;
    private String department;
    private String salaryType;
    private String salaryAmount;
     
}
