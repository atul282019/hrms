package com.cotodel.hrms.web.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class TravelRequestUpdate {
	
	private List<TravelReimbursement> travelReimbursement;
	private List<AccommodationReimbursement> accommodationReimbursement;
	private List<InCityCabsReimbursement> inCityCabsReimbursement;
	private List<MisleneousReimbursement> miscellaneousReimbursement;
	private List<MealReimbursement> mealReimbursement;
	

}
