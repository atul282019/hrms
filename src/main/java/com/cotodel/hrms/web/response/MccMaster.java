package com.cotodel.hrms.web.response;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MccMaster {
	
	private Integer id;
	private String purposeCode;
	private String purposeDesc;
	private String mcc;
	private String mccDesc;
	private Integer status;

}
