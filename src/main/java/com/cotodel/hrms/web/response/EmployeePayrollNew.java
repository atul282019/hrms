package com.cotodel.hrms.web.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeePayrollNew  {		

    private Long employerId;	
    private Long employeeId;	
	private String salary_component;	
    private String per_ctc;    
    private String per ;    
    private String  taxable;    
    private Integer status;
			
}
