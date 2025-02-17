package com.cotodel.hrms.web.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VoucherTypeMaster {
	private Integer id;
	private String voucherName;
	//private String voucherType;
	//private String voucherSubType;
	private String purposeCode;
	private String purposeDesc;
	private String voucherIcon;
	private Integer status;
	private String mccIcon; 
	private String mccMainIcon;
	private String mcc;
	private String mccDesc;
	
	
	
	
}
