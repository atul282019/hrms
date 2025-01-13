package com.cotodel.hrms.web.response;

public class CaptchaSession {
	private boolean captchaValidated;
	 
	public boolean isCaptchaValidated() {
		return captchaValidated;
	}
 
	public void setCaptchaValidated(boolean captchaValidated) {
		this.captchaValidated = captchaValidated;
	}
	
}
