package com.cotodel.hrms.web.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExistingUserVoucherCreationRequest {

	private String mcc;
	private String type;
	private String expDate;
	private Integer orgId;
	private String createdby;
	private Integer entrymodeIdPk;
	private String merchantId;
	private Integer voucherId;
	private String creationmode;
	private String payerVA;
	private String voucherCode;
	private String bankCode;
	private String redemtionType;
	private String beneficiaryID;
	private String amount;
	private String subMerchantId;
	private String voucherType;
	private String consent;
	private String creationDate;
	private String otpValidationStatus;
	private String accountId;
	private String purposeCode;
	private String name;
	private String voucherDesc;
	private String startDate;
	private String[] arrayofnamemobile;

}
