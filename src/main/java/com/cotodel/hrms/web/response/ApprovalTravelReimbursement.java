package com.cotodel.hrms.web.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApprovalTravelReimbursement {
	private Long id;
    private String approvalRemarks;
    private String approvedBy;
	private Float approvalAmount;
	private String approvedOrRejected;
	private String response;
}
