package com.cotodel.hrms.web.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeProfileRequest {
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
    private String payrollEnabledFlag;
    private String paidDate;
    private String runFayrollFlag;
    private String salaryAdvancesFlag;
     
     
}
