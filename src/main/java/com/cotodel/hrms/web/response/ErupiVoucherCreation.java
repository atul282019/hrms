package com.cotodel.hrms.web.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErupiVoucherCreation {

	private Long requestId;
	private Integer voucherId;
	private String name;	
	private String mobile;			
	private String amount;	
    private String startDate;			
    private String expDate;	
    private String purposeCode;	
	private String mcc;	
	private String mccDescription;
	private String purposeDescription;
	private String type;
	private String bankcode;
	private String voucherCode;
	private String voucherType;
	private String voucherDesc;
	private String redemptionType;	
	private String validity;
	
}
