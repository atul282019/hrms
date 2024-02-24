package com.cotodel.hrms.web.response;

import java.io.Serializable;

public class GetInTouchRequest implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4615208660281419839L;

	private String nameu;
	
	private String emailu;

	private String mobileu;
	
	private String messageu;

	public String getNameu() {
		return nameu;
	}

	public void setNameu(String nameu) {
		this.nameu = nameu;
	}

	public String getEmailu() {
		return emailu;
	}

	public void setEmailu(String emailu) {
		this.emailu = emailu;
	}

	public String getMobileu() {
		return mobileu;
	}

	public void setMobileu(String mobileu) {
		this.mobileu = mobileu;
	}

	public String getMessageu() {
		return messageu;
	}

	public void setMessageu(String messageu) {
		this.messageu = messageu;
	}

	@Override
	public String toString() {
		return "GetInTouchRequest [nameu=" + nameu + ", emailu=" + emailu + ", mobileu=" + mobileu + ", messageu="
				+ messageu + "]";
	}
	
}
