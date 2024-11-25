package com.cotodel.hrms.web.response;

import org.springframework.web.multipart.MultipartFile;

public class BulkVoucherRequest {
	
	private static final long serialVersionUID = -5145118965670277166L;	
	private Long employerId;	
	private String bankName;	
	private String bankCode;	
	private String accountHolderName;
	
	private String acNumber;	
	private String accountType;	
	private String ifsc;
	private String orgId;
	private String orgCode;
	private String tid;
	private String merchentIid;
	private String mcc;
	private String submurchentid;
	private String merchentid;
	private String payerva;
	public  MultipartFile docfile;
	private String response;
	private Long id;
	public  MultipartFile up;

}
