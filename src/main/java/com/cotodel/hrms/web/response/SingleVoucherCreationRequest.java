package com.cotodel.hrms.web.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SingleVoucherCreationRequest {

	private String merchantId;
	private String subMerchantId;
	private String mandateType;
	private String clientKey;
	private String hash;
	private String activeStatus;
	private String payeeVPA;
	private String consent;	
    private String otpValidationStatus;		 	
    private String creationDate;
	private String createdby;	
	private Long accountId;
	private Long orgId;	
	private String accountNumber;			
	private String response;
	private String responseApi;
	private String merchanttxnid;
	private String creationmode;
	private String payerVA;
	private String bankcode;
	private List<ErupiVoucherCreation> erupiVoucherCreateDetails;
}
