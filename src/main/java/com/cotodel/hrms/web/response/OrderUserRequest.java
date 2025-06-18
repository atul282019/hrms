package com.cotodel.hrms.web.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderUserRequest {
	
	private String orderAmount;
	private String amountServiceCharge;
	private String orderCurrency;
	private String customerId;
	private String customerName;
	private String customerEmail;
	private String customerPhone;
	private Long orgId;
	private String payment_session_id;
	private String orderId;
	
	private String bankCode;	
	private String bankName;	
	private String acNumber;
	private String createdBy;

}
