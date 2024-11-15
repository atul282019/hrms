package com.cotodel.hrms.web.function.common;

public interface CommonUtils {


	public static String tokenUrl="/tokenService/Api/get/access-token";
	public static String registerUserUrl = "/userServices/Api/get/saveUserDetails";
	public static String verifyEmail = "/userServices/Api//verifyLink";
	public static String sendOtp = "/userServices/Api/getOtp";
	public static String sendOtpNew = "/userServices/Api/getOtpNew";
	public static String resendOtpNew = "/userServices/Api/getOtpResend";
	public static String verifyOtpNew = "/userServices/Api/verifyOtpNew";
	public static String regiUserBulk = "/empService/Api/add/saveBulkEmplOnboarding";
	
	public static String verifyOtp = "/userServices/Api/verifyOtp";

	public static String stateMaster = "/userServices/Api/get/state-list";
	public static String orgMaster = "/userServices/Api/get/Organization";
	public static String registerCompany = "/empService/Api/get/saveProfileDetails";
	public static String companyProfileStatus = "/empService/Api/get/getEmpComplete";
	public static String saveEmployee = "/empService/Api/add/employeeDetails";
	public static String getPayrollList = "/userServices/Api/get/payroll-list";
	public static String savePayrollDetail = "/empService/Api/save/payrollDetails";
	public static String savePayrollDetailNew = "/empService/Api/save/payrollDetailsNew";
	public static String getPermission  = "/userServices/Api/get/Permissions";
	public static String getRole  = "/userServices/Api/get/Roles";
	public static String singleUserCreation  = "/userServices/Api/add/saveUsers";
	public static String getSingleUser  = "/userServices/Api/get/userList";
	public static String getBulkEmail  = "/userServices/Api/get/sendBulkEmail";
	
	public static String empDetails  = "/empService/Api/add/empDetails";
	public static String empFamilyDetails  = "/empService/Api/add/empFamilyDetails";
	public static String getEmployeeDetails  = "/empService/Api/get/empAllDetails";
	public static String getEmployeeFamilyDetails  = "/empService/Api/get/empFamilyAllDetails";
	public static String saveEmployeeQualification  = "/empService/Api/add/saveQualification";
	public static String getEmployeeQualification = "/empService/Api/get/getQualification";
	public static String saveEmpExperience = "/empService/Api/add/saveExperience";
	public static String getEmpExperience = "/empService/Api/get/getExperience";
	public static String saveEmpCertificate = "/empService/Api/add/saveCertificate";
	public static String getEmpCertificate = "/empService/Api/get/getCertificate";
	public static String saveEmpProject = "/empService/Api/add/saveProject";
	public static String getEmpProject = "/empService/Api/get/getProject";
	public static String saveEmpOnboarding = "/empService/Api/add/saveEmplOnboarding";
	public static String getEmployeeOnboarding ="/empService/Api/get/empOnboardingList";
	public static String getEmployeeOnboardingFailList ="/empService/Api/get/empOnboardingFailList";
	public static String getEmployeeOnboardingById ="/empService/Api/get/empOnboardingById";
	
	public static String confirmBulkEmplOnboarding = "/empService/Api/add/confirmBulkEmplOnboarding";
	
	public static String saveWaitlist = "/userServices/Api/add/saveWaitingListUsers"; 
	
	public static String saveEmployeeProfile = "/empService/Api/add/saveEmplOnboardingNew";
	
	public static String saveExpenseCategory = "/empService/Api/add/expenseCategoryBandDetails";
	
	//public static String getExpenseCategory = "/empService/Api/get/expenseCategoryMaster";
	public static String getExpenseCategory = "/empService/Api/get/expenseCategoryBandDetailsList";
	
	public static String getExpenseCategoryEditValue = "/empService/Api/get/expenseCategoryBandDetailsId";
	
	public static String getEmployeeBandWithTier = "/empService/Api/get/employeeBandAddTierReview";
	
	public static String addEmployeeBandTier = "/empService/Api/add/employeeBandAddTier";
	
	public static String saveExpanceTravelAdvance ="/empService/Api/add/expanceTravelAdvanceRequest";
	
	public static String getexpanceTravelAdvance ="/empService/Api/get/expanceTravelAdvance";
	
	public static String deleteExpanceTravelAllounce = "/empService/Api/delete/deleteExpenseCategoryDetails";
	
	public static String getExpenseBandList = "/empService/Api/get/getExpenseBandList";
	//public static String getExpenseBandList = "/empService/Api/get/employeeBandName";
	
	public static String addExpensesReimbursement ="/empService/Api/add/expenseReimbursementFileUploadSubmit";
	
	public static String getExpensesReimbursement = "/empService/Api/get/expenseReimbFileDownloadByEmpID";
	
	public static String viewExpensesReimbursement = "/empService/Api/get/expenseReimbFileDownloadByID";
	public static String deleteExpenseReimbursement = "/empService/Api/delete/expenseReimbFileDeleteByID";
	
	public static String addExpensesReimbursementDraft ="/empService/Api/add/expenseReimbursementFileUpload"; 
	
	public static String addErupiLinkAccount ="/empService/Api/add/erupiLinkAccount";
	
	//public static String getErupiLinkAccountDetail ="/empService/Api/get/erupiLinkAccount";
	public static String getErupiLinkAccountDetail  ="/empService/Api/get/erupiLinkAccountList";
	public static String getBankMaster  = "/empService/Api/get/getBankMasterDetailsList";
	
	public static String getVoucherDetailByBoucherCode  = "/empService/Api/get/voucherTypeMaster";
	
	public static String createSingleVoucher = "/empService/Api/add/erupiVoucherInitiateDetails";
	
	public static String getLinkedDetailByAccountNumber = "/empService/Api/get/erupiLinkAccountDetails";
	
	public static String verifyVoucherIssueOTP ="/userServices/Api/verifyOtpWithoutUser";
	
	public static String getIssuseVoucherList ="/empService/Api/get/erupiVoucherCreateList";
	
	public static String getvoucherSummaryList ="/empService/Api/get/voucherSummaryList";
	
	public static String getPrimaryBankDetailByOrgId = "/empService/Api/get/erupiPrimaryAccountDetails";
	
	
	
}
