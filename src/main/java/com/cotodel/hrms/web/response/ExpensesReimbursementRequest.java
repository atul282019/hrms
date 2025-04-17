package com.cotodel.hrms.web.response;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExpensesReimbursementRequest{
	
	private Integer id;
	private Integer employeeId;
	private Integer employerId;
	private String expenseCategory;
	private String fileName;
	private String fileType;
	
	private String created_date;
	private String dateOfExpense;
	private String expenseTitle;
	private String vendorName;
	private String invoiceNumber;
	private String currency;
	private String amount;
	private String modeOfPayment;
	private String remarks;
	private String username;
	private String approvedOrRejected;
	private String rejectedRemarks;
	private String approvedAmount;
	public  String fileInput;

	public  String clientKey;
	public  String hash;
}
