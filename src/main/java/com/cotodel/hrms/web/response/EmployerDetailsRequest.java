package com.cotodel.hrms.web.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployerDetailsRequest {
	private Long id;
	private Long employerId;
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
	private String otpStatus;
	private String natureOfPrincipalPlaceOfBusiness;
	private String centerJurisdictionCode;
	private String gstIdentificationNumber;
	private String pan;
	private String createdBy;	
	private String response;
	private String clientKey;
	private String hash;
	private String organizationName;		
	private String mobile;	
	private String email;
	private String name;
	private boolean erupistatus;
	private String companySize;
	private String privacyCheck;
	private String whatsupCheck;
	private String captcha;
	private String companyType;
}
 