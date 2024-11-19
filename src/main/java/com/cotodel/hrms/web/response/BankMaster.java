package com.cotodel.hrms.web.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class BankMaster
{
	private String bankName;
	//private String bankIfsc;
	private String bankCode;
	private Integer status;
	private String bankLogo;
	private Integer id;
//	public String getBankName() {
//		return bankName;
//	}
//	public void setBankName(String bankName) {
//		this.bankName = bankName;
//	}
//	public String getBankIfsc() {
//		return bankIfsc;
//	}
//	public void setBankIfsc(String bankIfsc) {
//		this.bankIfsc = bankIfsc;
//	}
//	public String getBankCode() {
//		return bankCode;
//	}
//	public void setBankCode(String bankCode) {
//		this.bankCode = bankCode;
//	}
//	
//	
//	public Integer getStatus() {
//		return status;
//	}
//	public void setStatus(Integer status) {
//		this.status = status;
//	}
//	public String getBankLogo() {
//		return bankLogo;
//	}
//	public void setBankLogo(String bankLogo) {
//		this.bankLogo = bankLogo;
//	}
//	@Override
//	public String toString() {
//		return "BankMaster [bankName=" + bankName + ", bankIfsc=" + bankIfsc + ", bankCode=" + bankCode + ", status="
//				+ status + ", bankLogo=" + bankLogo + "]";
//	}
//	

	}

//}