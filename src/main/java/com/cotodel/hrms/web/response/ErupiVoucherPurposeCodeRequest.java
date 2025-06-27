package com.cotodel.hrms.web.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErupiVoucherPurposeCodeRequest {
	
	private Long orgId;
	private String timePeriod;
	private String mobile;

}
