package com.cotodel.hrms.web.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExpenseCategoryRequest {
	
	private Integer id;
	private Integer employerId;
	private String expenseCategory;
	private String expenseCode;
	private String expenseLimit;
	private String bandFlag;
	private String bandId; 
	private String dayToExpiry;
	private Integer	status;
	private String[] listArray;
	private List<BandDetailRequest> list;

	private String hash;
	private String clientKey;
	private String distingushEmployeeBand;
	

}
