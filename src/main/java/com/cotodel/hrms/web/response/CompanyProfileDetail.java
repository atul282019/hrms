package com.cotodel.hrms.web.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompanyProfileDetail {
	
	private String pan;
	private Long id;
	private Long employerId;
	private String createdBy;
	private String gstIdentificationNumber;
	private String legalNameOfBusiness;
	private String tradeName;
	private String constitutionOfBusiness;
	private String orgType;
	private String address1;
	private String address2;
	private String districtName;
	private String pincode;
	private String stateName;
	private String consent;
	private String streetName;
	private String buildingNumber;
	private String buildingName;
	private String location;
	private String floorNumber;
	private String natureOfPrincipalPlaceOfBusiness;
	private String centerJurisdictionCode;
	private String  otpStatus;

}
