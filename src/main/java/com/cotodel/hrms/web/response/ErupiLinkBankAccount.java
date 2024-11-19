package com.cotodel.hrms.web.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErupiLinkBankAccount {

	private Integer id;
	//private ErupiBankMaster bank;
	private String bankName;
	private String bankCode;
	private String accountHolderName;
	private String acNumber;
	private String conirmAccNumber;
	private String accountType;
	private String ifsc;
	private String erupiFlag;
	private String createdby;
	private String updateDate;
	private String updatedby;
	private String branchCode;
	private Integer orgId;
	private String orgCode;
	private String employeCode;
	private String authStatus;
	private String authResponse;
	private String mobile;
	private Integer accstatus;
	private String tid;
	private String merchentIid;
	private String mcc;
	private String submurchentid;
	private String payerva;
	
		  
}
