package com.cotodel.hrms.web.service;

import org.springframework.stereotype.Repository;

import com.cotodel.hrms.web.util.EncriptResponse;

@Repository
public interface TicketSupportService {

	String submitTicketDetails(String token, EncriptResponse jsonObject);

	String getAllTicket(String token, EncriptResponse jsonObject);

	String getTicketListForAction(String token, EncriptResponse jsonObject);

	String getTicketDetailById(String token, EncriptResponse jsonObject);

	String replyTicket(String token, EncriptResponse jsonObject);
	
	String getTicketstatus(String token, EncriptResponse jsonObject);

	String ticketReplyHistory(String token, EncriptResponse jsonObject);

}
