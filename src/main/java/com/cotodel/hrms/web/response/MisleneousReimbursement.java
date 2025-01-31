package com.cotodel.hrms.web.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MisleneousReimbursement {
	 private Integer id;
	 private String title;
	 private String toBeBookedBy;
	 private String date;
	 private String amount;
	 private String paymentMode;
	 private String limitAmount;
	 private String remarks;
}
