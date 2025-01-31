package com.cotodel.hrms.web.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MealReimbursement {
	
	  private Integer id;
	  private String typeOfMeal;
	  private String date;
	  private String numberOfDays;
	  private String location;
	  private String paymentMode;
	  private String amount;
	  private String limitAmount;
	  private String remarks;
}
