package com.cotodel.hrms.web.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InCityCabsReimbursement {
	
	    private String travelSubType;
	    private String mode;
	    private String toBeBookedBy;
	    private String date;
	    private String departure;
	    private String arrival;
	    private String preferredTime;
	    private String timePreference;
	    private String carrierClass;
	    private String carrierDetails;
	    private String paymentMode;
	    private String remarks;
	    
	    private String hotelDetails;
	    private String location;
	    private String  checkoutDate;
	    private String checkinDate;
	    private String type;
	    
	    private String fromLocation;
	    private String toLocation;
	    
	    private String typeOfMeal;
	    private String startDate;
	    private String numberOfDays;
	    
	    
	   

}
