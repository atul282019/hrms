package com.cotodel.hrms.web.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccommodationReimbursement {
	
	 private Integer id;
	 private String type;
	 private String toBeBookedBy;
	 private String checkinDate;
	 private String  checkoutDate;
	 private String location;
	 private String hotelDetails;
	 private String preferredTime;
	 private String paymentMode;
	 private String amount;
	 private String limitAmount;
	 private String remarks;

}
