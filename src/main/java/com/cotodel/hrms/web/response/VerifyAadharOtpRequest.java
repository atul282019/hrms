package com.cotodel.hrms.web.response;


import java.io.Serializable;


public class VerifyAadharOtpRequest implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6047086312809415818L;

	public String aadhaar;

	public String otp;	
	
	public String txn;

	public String consent = "Y";
	

	public String getAadhaar() {
		return aadhaar;
	}

	public void setAadhaar(String aadhaar) {
		this.aadhaar = aadhaar;
	}

	public String getOtp() {
		return otp;
	}

	public void setOtp(String otp) {
		this.otp = otp;
	}

	public String getTxn() {
		return txn;
	}

	public void setTxn(String txn) {
		this.txn = txn;
	}

	public String getConsent() {
		return consent;
	}

	public void setConsent(String consent) {
		this.consent = consent;
	}

	@Override
	public String toString() {
		return "VerifyAadharOtpRequest [aadhaar=" + aadhaar + ", otp=" + otp + ", txn=" + txn + ", consent=" + consent
				+ "]";
	}

}
