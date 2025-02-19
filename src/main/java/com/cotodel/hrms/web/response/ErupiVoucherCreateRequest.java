package com.cotodel.hrms.web.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErupiVoucherCreateRequest {
//	private Long id;
//	private Long employerId;
//	private Long employeeId;
//	private String name;
//	private String voucherType;
//	private String voucherSubType;
//	private Integer voucherQuantity;
//	private Float amount;
//	private String remarks; 
//	private String response;
//	private Integer mobno;
	private Long id;
	private Long employerId;
	private Long employeeId;
	private String name;
	private String mobile;
	private String voucherType;
	private String voucherSubType;
	private Float amount;
	private String remarks;
	private String response;

}
