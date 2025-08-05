package com.cotodel.hrms.web.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErupiBrandDetailsRequest {
	private Long id;	
	private Long orgid;	
	private String brandname;	
	private String brandlogo;	
	private String createdby;	
	private int status;	
	private int outletsNo;	
	private String storetypedesc;	
	private int storetypeId;	
	private String extra1;
	private String response;
	private String updatedby;
}
