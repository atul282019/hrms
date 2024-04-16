package com.cotodel.hrms.web.response;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BulkConfirmationRequest {
	
	private String first_name;	
	private String last_name;	
	private String gender;
	private String contact_number;
	private String email;
	private String address;
	private String org_name;
	private String mobile;

}

