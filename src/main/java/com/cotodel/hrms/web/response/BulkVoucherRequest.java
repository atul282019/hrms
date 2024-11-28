package com.cotodel.hrms.web.response;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BulkVoucherRequest {
	
	private static final long serialVersionUID = -5145118965670277166L;	
	
	private Long id;	
	private Long employerId;	
	private String fileName;
	private String file;
	private String bankName;	
	private String bankcode;	
	private String accountHolderName;
	
	private String acNumber;	
	private String type;	
	private String ifsc;
	private String orgId;
	private String orgCode;
	private String tid;
	private String merchantId;
	private String mcc;
	private String subMerchantId;
	private String merchentid;
	private String payerVA;
	private String createdby;
	
	private String voucherDesc;
	
	private String purposeCode;
	private String accountId;
	
	private String voucherCode;
	private String beneficiaryID;
	
	

}
