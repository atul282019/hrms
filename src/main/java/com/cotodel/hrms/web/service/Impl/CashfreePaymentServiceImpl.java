package com.cotodel.hrms.web.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cotodel.hrms.web.function.common.CommonUtils;
import com.cotodel.hrms.web.properties.ApplicationConstantConfig;
import com.cotodel.hrms.web.response.OrderUserRequest;
import com.cotodel.hrms.web.service.CashfreePaymentService;
import com.cotodel.hrms.web.util.CommonUtility;
import com.cotodel.hrms.web.util.MessageConstant;

@Service
public class CashfreePaymentServiceImpl implements CashfreePaymentService{

	@Autowired
	public ApplicationConstantConfig applicationConstantConfig;

	@Override
	public String getCashfreePaymentSession(String token, OrderUserRequest orderUserRequest) {
		return CommonUtility.userRequest(token,MessageConstant.gson.toJson(orderUserRequest), applicationConstantConfig.cashfreePaymentBaseUrl+CommonUtils.cashFreePaymentOrder);
	}

	@Override
	public String getOrderDetailByOrderId(String token, OrderUserRequest orderUserRequest) {
		return CommonUtility.userRequest(token,MessageConstant.gson.toJson(orderUserRequest), applicationConstantConfig.cashfreePaymentBaseUrl+CommonUtils.cashFreePaymentOrderDetailByOrderId);
	}

}
