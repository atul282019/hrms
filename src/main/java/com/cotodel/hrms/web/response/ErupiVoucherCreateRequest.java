package com.cotodel.hrms.web.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErupiVoucherCreateRequest {

	private Long id;
	private Long orgId;
	private Long employeeId;
	private String employeeName;
	private String employeeMobile;
	private String createdby;
	private String response;
	private String clientKey;
	private String hash;
	List<VoucherRequestData> voucherRequestData;

}
