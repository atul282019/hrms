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
	private String bandFlag;
	private String bandId; 
	private String period;
	private Integer	status;
	private String[] listArray;
	private List<BandDetailRequest> list;

}
