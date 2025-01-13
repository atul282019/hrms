package com.cotodel.hrms.web.service;

import org.springframework.stereotype.Repository;

import com.cotodel.hrms.web.response.ReCaptcha;

@Repository
public interface ReCaptchaService {
	public String getcaptcha(String token ,String companyId);

}
