package com.cotodel.hrms.web.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VoucherRequestData {
	private Long id;
	private String voucherName;
	private String mobile;
	private String purposeDescription;
	private String mccDescription;
	private String amount;
	private String purpose;
	private String mcc;
	private String remarks;
}
