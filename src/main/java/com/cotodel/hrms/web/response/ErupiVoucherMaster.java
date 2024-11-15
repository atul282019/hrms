package com.cotodel.hrms.web.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErupiVoucherMaster {

	private Integer id;
	private String voucherCode;
	private String voucherType;
	private String voucherSubType;
	private String voucherDesc;
	private String purposeCode;
	private String activeStatus;
	private String creationDate;
	private String createdby;
	private String status;
	private String beneficiaryID;
	private String payerVA;
	private String type;
}
