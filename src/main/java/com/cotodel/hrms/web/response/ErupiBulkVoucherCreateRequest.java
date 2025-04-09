package com.cotodel.hrms.web.response;
import java.lang.reflect.Array;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErupiBulkVoucherCreateRequest {
	
	private Integer id;
	private Integer voucherId;
    private String createdby; 
	private String type;
	private String mcc;	
	private String merchantId;
	private String subMerchantId;
	
	private String[] arrayofid;
	private String redemtionType;	
	private String payerVA;
	private String voucherCode;
	private String voucherType;
	private String voucherDesc;
	private String name;	
	private String bankcode;	
	private String mobile;			
	private String amount;	
    private String startDate;			
    private String expDate;	
    private String purposeCode;	
    private String consent;
    private String otpValidationStatus;		 	
    private String creationDate;
	
	private Integer workflowid;
	private Long accountId;	
	private Long orgId;	
	private String accountNumber;			
	private Integer entrymodeIdPk;
	private String response;	
	private String merchanttxnid;
	private String creationmode;	
	private Long bulktblId;
	
	private String extra1;	
	private String extra2;	
	private String extra3;
	private String beneficiaryID;

	private String responseApi;
	private String mandateType;
	private String payeeVPA;
	private String clientKey;
	private String hash;
}
