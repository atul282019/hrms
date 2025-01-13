package com.cotodel.hrms.web.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cotodel.hrms.web.function.common.CommonUtils;
import com.cotodel.hrms.web.properties.ApplicationConstantConfig;
import com.cotodel.hrms.web.response.ReCaptcha;
import com.cotodel.hrms.web.service.ReCaptchaService;
import com.cotodel.hrms.web.util.CommonUtility;
import com.cotodel.hrms.web.util.MessageConstant;

@Service
public class ReCaptchaServiceImpl implements ReCaptchaService{

	@Autowired
	public ApplicationConstantConfig applicationConstantConfig;

	@Override
	public String getcaptcha(String token, String companyId) {
		// TODO Auto-generated method stub
		return CommonUtility.getTokenRequest(token,"",companyId, applicationConstantConfig.userServiceBaseUrl+CommonUtils.getcaptcha);
	}
}
