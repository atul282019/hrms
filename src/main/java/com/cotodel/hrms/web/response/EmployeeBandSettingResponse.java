package com.cotodel.hrms.web.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeBandSettingResponse {
	
	private Integer employerId;
	private Long bandEnabled;	
	private String employeeBandNo;
	private String employeeBandNoAlpha;
	private String employeeBandOrder;
	private Long status;
	
	private String[] listArray;
    private List<EmployeeBandTiersResponse> list;
	
}
