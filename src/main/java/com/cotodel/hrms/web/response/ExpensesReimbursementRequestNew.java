package com.cotodel.hrms.web.response;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExpensesReimbursementRequestNew {

	private Integer employeeId;
	private Integer employerId;
	private String expenseCategory;
	private String fileName;
	private String fileType;
	public  String file;
	private String created_date;
	private String dateOfExpense;
	private String expenseTitle;
	private String vendorName;
	private String invoiceNumber;
	private String currency;
	private String amount;
	private String modeOfPayment;
	private String remarks;
}
