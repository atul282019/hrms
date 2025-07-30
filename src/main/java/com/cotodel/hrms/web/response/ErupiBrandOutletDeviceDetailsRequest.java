package com.cotodel.hrms.web.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErupiBrandOutletDeviceDetailsRequest {
	
	private Long id;	
	private Long orgid;	
	private int brandid;	
	private int outletid;	
	private int deviceTypeId;	
	private int qrStateId;	
	private String deviceTypeDesc;		
	private String qrStateDesc;	
	private String upiId;	
	private int status;	
	private String statusDesc;	
	private String creationdate;	
	private String createdby;	
	private String upiImg;	
	private String reviewby;	
	private String reviewdate;	
	private String remarks;	
	private String imgApiResponse;
	private String response;
	private String clientKey;
	private String hash;

}
