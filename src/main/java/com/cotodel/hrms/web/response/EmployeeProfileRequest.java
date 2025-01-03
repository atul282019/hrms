package com.cotodel.hrms.web.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeProfileRequest {
	
	private Integer employeeId;
	private Long employerId;
	private String gstnNo;
	private String organizationType;
    private String pan;
    private String brandName;
    private String panDetails;
    private String companyName;
    private String officeAddress;
    private String addressLine;
    private String pinCode;
    private String stateCode;
    private boolean payrollEnabledFlag;
    private String paidDate;
    private boolean runPayrollFlag;
    private boolean salaryAdvancesFlag;
    private Integer id;
    private String orgType;
    private String orgDesc;
    private Integer status;
    private Integer orgTypeId;
    private Integer profileComplete;
    
    
     
}
