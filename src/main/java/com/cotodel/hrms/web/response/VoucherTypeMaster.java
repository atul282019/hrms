package com.cotodel.hrms.web.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VoucherTypeMaster {
	private Integer id;
	private String voucherDesc;
	private String voucherType;
	private String voucherSubType;
	private String purposeCode;
	private Integer status;
	
}
