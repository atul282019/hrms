package com.cotodel.hrms.web.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TravelReimbursement {
	
	    private Integer id;
	    private String mode;
	    private String toBeBookedBy;
	    private String date;

	    private String fromLocation;
	    private String toLocation;
	    private String preferredTime;
	    private String arrivalPreference;
	    private String carrierClass;
	    private String carrierDetails;
	    private String paymentMode;
	    private String limitAmount;
	    private String amount;
	    private String remarks;
	    
}
