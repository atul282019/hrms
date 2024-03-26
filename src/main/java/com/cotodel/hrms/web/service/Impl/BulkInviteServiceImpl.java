package com.cotodel.hrms.web.service.Impl;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cotodel.hrms.web.function.common.CommonUtils;
import com.cotodel.hrms.web.properties.ApplicationConstantConfig;
import com.cotodel.hrms.web.response.BulkInviteRequest;
import com.cotodel.hrms.web.service.BulkInviteService;
import com.cotodel.hrms.web.util.CommonUtility;

@Service
public class BulkInviteServiceImpl implements BulkInviteService {
	
	private static final Logger logger = LoggerFactory.getLogger(BulkInviteServiceImpl.class);

	
	@Autowired
	public ApplicationConstantConfig applicationConstantConfig;

	@Override
	public String bulkInvite(String token, BulkInviteRequest bulkInviteRequest) {
		// TODO Auto-generated method stub
		 return CommonUtility.userRequest(token,emailSendRequest(bulkInviteRequest), applicationConstantConfig.userServiceBaseUrl +CommonUtils.getBulkEmail);
	}
	public static String emailSendRequest(BulkInviteRequest req) {
		JSONObject request= new JSONObject();		
		request.put("inviteEmployee", req.getInviteEmployee());	
		request.put("inviteContractor", req.getInviteContractor());	
		return request.toString();
	}
		
}
