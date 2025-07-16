package com.cotodel.hrms.web.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErupiTicketSaveRequest {
	
	private Long id;	
	private String ticketnumber;	
	private String subject;	
	private String issuetype;	
	private String issueDesc;	
	private int status;	
	private String createdby;	
	private String updatedby;
	private Long orgId;
	private String responseIssueDesc;	
	private int custTicketStatus;	
	private String custTicketStatusDesc;	
	private int respTicketStatus;	
	private String respTicketStatusDesc;	
	private String ticketImg;	
	private String responseTktImg;	
	private String responedby;	
	private String ticketno;
	private String response;
	private String clientKey;
	private String hash;
	private String name;
	
	

}
