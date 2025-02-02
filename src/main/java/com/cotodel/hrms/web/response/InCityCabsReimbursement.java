package com.cotodel.hrms.web.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InCityCabsReimbursement {
	
	    private Integer id;
	    private String mode;
	    private String toBeBookedBy;
	    private String date;
	    private String preferredTime;
	    private String fromLocation;
	    private String toLocation;
	    private String paymentMode;
	    private String amount;
	    private String limitAmount;
	    private String remarks;
}
