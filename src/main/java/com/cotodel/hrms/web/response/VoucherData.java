package com.cotodel.hrms.web.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VoucherData {
	private String voucherId;
    private String name;
    private String mobile;
    private String amount;
    private String startDate;
    private String expDate;
    private String purposeCode;
    private String consent;
    private String otpValidationStatus;
    private String creationDate;
    private String createdby;
    private String accountId;
    private Integer orgId;
    private String accountNumber;
    private String response;
    private String responseApi;
    private String merchanttxnid;
    private String creationmode;
    private String bulktblId;
    private String redemtionType;
    private String mcc;
    private String extra1;
    private String extra2;
    private String extra3;
    private String beneficiaryID;
    private String payerVA;
    private String type;
    private String bankcode;
    private String voucherCode;
    private String voucherType;
    private String voucherDesc;
    private String merchantId;
    private String subMerchantId;
    private String mandateType;
    private String payeeVPA;
    private String validity;
    private String requestId;
    private String clientKey;
    private String hash;
}
