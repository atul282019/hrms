package com.cotodel.hrms.web.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErupiVoucherCreateDetails {
	
    private Integer id;
	private Integer voucherId;
	private String voucherCode;
	private String voucherType;
	private String voucherDesc;
	private String name;	
	private String bankCode;	
	private String mobile;			
	private Float amount;	
    private String startDate;			
    private String expDate;	
    private String purposeCode;	
    private String consent;	
    private String otpValidationStatus;		 	
    private String creationDate;
	private String createdby;	
	private Long accountId;	
	private Long orgId;	
	private String accountNumber;			
	private Integer entrymodeIdPk;
	private String response;	
	private String merchanttxnid;
	private String creationmode;	
	private Long bulktblId;
	private String redemtionType;	
	private String mcc;	
	private String extra1;	
	private String extra2;	
	private String extra3;
	private String beneficiaryID;
	private String payerVA;
	private String type;
	private Integer workflowid;
	
	private String merchantId;
	private String subMerchantId;
}

