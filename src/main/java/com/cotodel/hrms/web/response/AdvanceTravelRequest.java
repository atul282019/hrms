package com.cotodel.hrms.web.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdvanceTravelRequest {
	private Integer id;
	private String  sequeneId;
	private Integer employerId;
	private Integer employeeId;
	private String  requestType;
	private String  expenseCategoryId;
	private String  expenseCategory;
	private String  mode;
	private String  toBeBookedBy;
	private String  travelDate;
	private String  departureLocation;
	private String  arrivalLocation;
	private String  preferredTimeBefore;
	private String  preferredTimeArrival;
	private String  carrierDetails;
	private String  modeOfPayment;
	private String  currency;
	private String  amount;
	private String  approvedAmount;
	private String  createdBy;
	private String  remarks;
	private String  cashDate;
	private String  statusMessage;
	private String response;
	private int status;
    private String approvalRemarks;
    private String approvedBy;
	private Float approvalAmount;
	private String approvedOrRejected;
	
	private String username;
	private String clientKey;
	private String hash;
	
}