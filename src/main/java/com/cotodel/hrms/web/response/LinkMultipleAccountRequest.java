package com.cotodel.hrms.web.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LinkMultipleAccountRequest {
	private Long id;	
	private Long linkId;	
	private String bankCode;//FK	
	private String bankName;	
	private String accountHolderName;	
	private String acNumber;
	private String accountType;//Saving, Current	
	
	private String ifscCode;	
	private String createdby;		
	private Long orgId;	
	private String mobile;	
	private Float amountLimit;	
	private Float balance;	
	private String approvedby;	
	private String rejectedby;
	private String response;
}
