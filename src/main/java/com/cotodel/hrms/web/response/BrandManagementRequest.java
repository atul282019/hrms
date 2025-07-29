package com.cotodel.hrms.web.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BrandManagementRequest {
	private Long id;	
	private Long orgid;	
	private int brandid;	
	private int geocatid;	
	private String geocatname;	
	private String name;	
	private int typeid;	
	private String typeDesc;	
	private String mgrName;	
	private String mgrMobile;	
	private String address;	
	private String location;	
	private int status;	
	private String appType;	
	private String creationdate;	
	private String createdby;	
	private String updatedate;	
	private String updateby;	
	private String extra1;	
	private String bulkSingle;	
	private String outletImg;
	private String response;
}
