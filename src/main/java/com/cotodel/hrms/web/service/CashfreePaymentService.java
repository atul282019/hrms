package com.cotodel.hrms.web.service;

import org.springframework.stereotype.Repository;

import com.cotodel.hrms.web.response.OrderUserRequest;
import com.cotodel.hrms.web.response.Root;

@Repository
public interface CashfreePaymentService {

	String getCashfreePaymentSession(String token, OrderUserRequest orderUserRequest);

	String getOrderDetailByOrderId(String token, OrderUserRequest orderUserRequest);
	
	String viewOrderDetailByOrderId(String token, OrderUserRequest orderUserRequest);

	String paymentCallBackData(String token, Root root);

}
