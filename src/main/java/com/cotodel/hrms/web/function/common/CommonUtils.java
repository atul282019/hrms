package com.cotodel.hrms.web.function.common;

public interface CommonUtils {


	public static String tokenUrl="/tokenService/Api/get/access-token";
	//public static String registerUserUrl = "/userServices/Api/get/saveUserDetails";
	public static String registerUserUrl = "/userServices/Api/add/saveEmployerOnBoardingDetails";
	public static String verifyEmail = "/userServices/Api//verifyLink";
	public static String sendOtp = "/userServices/Api/getOtp";
	public static String otpWithOutRegister = "/userServices/Api/get/otpWithOutRegister";
	public static String sendOtpNew = "/userServices/Api/getOtpNew";
	public static String resendOtpNew = "/userServices/Api/getOtpResend";
	public static String verifyOtpNew = "/userServices/Api/verifyOtpNew";
	public static String verifyOtpWithOutRegister = "/userServices/Api/get/verifyOtpWithOutRegister";
	public static String regiUserBulk = "/empService/Api/add/saveBulkEmplOnboarding";
	public static String verifyOtp = "/userServices/Api/verifyOtp";

	public static String registerCompany = "/empService/Api/get/saveProfileDetails";
	public static String companyProfileStatus = "/userServices/Api/get/getEmployerComplete";

	public static String saveEmployee = "/empService/Api/add/employeeDetails";
	
	public static String getRole  = "/userServices/Api/get/Roles";
	public static String singleUserCreation  = "/userServices/Api/add/saveUsers";
	public static String getSingleUser  = "/userServices/Api/get/userList";
	public static String getBulkEmail  = "/userServices/Api/get/sendBulkEmail";

	public static String getEmployeeDetails  = "/empService/Api/get/empAllDetails";
	
	public static String saveEmpOnboarding = "/empService/Api/add/saveEmplOnboarding";
	public static String getEmployeeOnboarding ="/empService/Api/get/empOnboardingList";
	public static String getEmployeeOnboardingFailList ="/empService/Api/get/empOnboardingFailList";
	public static String getEmployeeOnboardingById ="/empService/Api/get/empOnboardingById";
	public static String getEmployeeOnboardingByUserDetailId ="/empService/Api/get/empOnboardingByUserDetailId";
	public static String getEmployeeOnboardingByIdManagerId ="/empService/Api/get/empOnboardingByManagerId";	
	public static String confirmBulkEmplOnboarding = "/empService/Api/add/confirmBulkEmplOnboarding";
	public static String saveWaitlist = "/userServices/Api/add/saveWaitingListUsers"; 
	public static String saveEmployeeProfile = "/empService/Api/add/saveEmplOnboardingNew";
	public static String updateEmployeeProfile = "/empService/Api/update/updateEmplOnboardingNew";
	public static String deactiveEmployeeProfile = "/empService/Api/update/emplOnboardingStatus";
	public static String saveExpenseCategory = "/empService/Api/add/expenseCategoryBandDetails";
	public static String getExpenseCategory = "/empService/Api/get/expenseCategoryBandDetailsList";
	public static String getExpenseCategoryEditValue = "/empService/Api/get/expenseCategoryBandDetailsId";
	public static String getEmployeeBandWithTier = "/empService/Api/get/employeeBandAddTierReview";
	public static String addEmployeeBandTier = "/empService/Api/add/employeeBandAddTier";
	public static String saveExpanceTravelAdvance ="/empService/Api/add/expanceTravelAdvanceRequest";
	public static String getexpanceTravelAdvance ="/empService/Api/get/expanceTravelAdvance";
	public static String deleteExpanceTravelAllounce = "/empService/Api/delete/deleteExpenseCategoryDetails";
	public static String getExpenseBandList = "/empService/Api/get/getExpenseBandList";
	public static String addExpensesReimbursement ="/empService/Api/add/expenseReimbursementFileUploadSubmit";
	public static String getExpensesReimbursement = "/empService/Api/get/expenseReimbFileDownloadByEmpID";
	public static String viewExpensesReimbursement = "/empService/Api/get/expenseReimbFileDownloadByID";
	public static String deleteExpenseReimbursement = "/empService/Api/delete/expenseReimbFileDeleteByID";
	public static String addExpensesReimbursementDraft ="/empService/Api/add/expenseReimbursementFileUpload"; 
	public static String addErupiLinkAccount ="/empService/Api/add/erupiLinkAccount";
	public static String getErupiLinkAccountDetail  ="/empService/Api/get/erupiLinkAccountList";
	public static String getErupiLinkAccountDetailWithStatus  ="/empService/Api/get/erupiLinkAccountListWithStatus";
	public static String getBankMaster  = "/empService/Api/get/getBankMasterDetailsList";
	public static String getVoucherDetailByBoucherCode  = "/empService/Api/get/voucherTypeMaster";
	public static String createSingleVoucherOLD = "/empService/Api/add/erupiVoucherInitiateDetailsNew";   //old api
	public static String createSingleVoucher = "/empService/Api/add/erupiVoucherSingleCreation";
	
	public static String getLinkedDetailByAccountNumber = "/empService/Api/get/erupiLinkAccountDetails";
	public static String verifyVoucherIssueOTP ="/userServices/Api/verifyOtpWithoutUser";
	public static String getIssuseVoucherList ="/empService/Api/get/erupiVoucherCreateList";
	public static String erupiVoucherCreateListRedeem ="/empService/Api/get/erupiVoucherCreateListRedeem";
	public static String getvoucherSummaryList ="/empService/Api/get/voucherCreateSummaryList";
	public static String getPrimaryBankDetailByOrgId = "/empService/Api/get/erupiPrimaryAccountDetails";
	public static String updateErupiLinkAccount = "/empService/Api/update/erupiLinAccPsFlag";
	public static String reLinkErupiLinkAccount="/empService/Api/update/erupiLinAccEnable";
	public static String delinkErupiLinkAccount = "/empService/Api/update/erupiLinAccDisable";
	public static String saveVoucherTypeMasterDetailS="/masterService/Api/add/mccMasterupdate";
	public static String getvoucherTypeMasterList="/masterService/Api/get/mccMasterList";
	public static String getRequestedVoucherList="/empService/Api/get/erupiVoucherRequest";
	public static String getRequestedVoucherApproveList = "/empService/Api/get/erupiVoucherRequestApproved";
	public static String erupiVoucherRequestByMngId="/empService/Api/get/erupiVoucherRequestByMngId";
	public static String approveRejectRequestedVoucherById = "/empService/Api/update/erupiVoucherRequestUpdate";
	public static String updatevoucherTypeMasterStatus="/empService/Api/update/voucherTypeMasterStatus";
	public static String createVoucher="/empService/Api/add/erupiVoucherRequest";
	public static String saveBankMasterDetailS="/empService/Api/add/bankMasterDetails";
	public static String getaftersaveBankMasterDetailsList="/empService/Api/get/getBankMasterDetailsList";
	public static String getbankNameMasterList="/empService/Api/get/bankNameMasterList";
	public static String updatebankMasterDetailStatus="/empService/Api/update/bankMasterDetailStatus";
	public static String revokeCreatedVoucherOneByOne="/empService/Api/update/erupiVoucherRevokeSingle";
	public static String resendErupiVoucheSmsSend="/empService/Api/get/erupiVoucherSms";
	public static String bulkVoucherUpload = "/empService/Api/add/erupiVoucherBulkVoucherUploadNew";
	public static String createBulkVoucher = "/empService/Api/add/erupiVoucherBulkVoucherCreate";
	public static String erupiVoucherRevokeDetailsBulkRequest = "/empService/Api/update/erupiVoucherRevokeBulk";
	public static String beneficiaryDeleteFromVoucherList = "/empService/Api/update/erupiVoucherDelete";
	public static String getMCCMasterByPurposeCode="/masterService/Api/get/mccMasterListByPurposeCode";
	public static String getMCCMasterDeailsByPurposeCodeAndMCC="/masterService/Api/get/mccMasterListByPurposeCodeAndMccCode";
	public static String getVoucherUserList="/empService/Api/get/erupiVoucherOldList";
	public static String exitingUserVoucherCreation="/empService/Api/add/erupiVoucherCreateList";
	public static String getUserListWithRole="/userServices/Api/get/userListWithRole";
	public static String editUserListWithRole="/userServices/Api/update/editUserRole";
	public static String deleteUserListWithRole="/userServices/Api/delete/deleteUserRole";
	public static String addUserSearch="/userServices/Api/get/userSearch";
	public static String getEmployeeType="/masterService/Api/get/employeeTypeMasterList";
	public static String getEmployeeListMaster="/userServices/Api/get/userManagerList";
	public static String getofficeLocationMaster="/userServices/Api/get/getCompaneyAddress";
	public static String getVoucherListWithIcon ="/masterService/Api/get/voucherList"; 
	public static String getVoucherListWithIconNew ="/masterService/Api/get/voucherCategoryList"; 
	public static String getPurposeListByVoucherCode ="/masterService/Api/get/mccByCotoCatIdList"; 
	// old public static String voucherUserSearch="/userServices/Api/get/userSearchWithMobile";
	public static String erupiVoucherStatusHistory="/empService/Api/get/erupiVoucherStatusHistory";
	public static String voucherUserSearch="/empService/Api/get/employeeSearchWithMobile";
	public static String getTotalVoucherCount = "/empService/Api/get/voucherSummaryTotalCount";
	public static String getVoucherCreateBankList = "/empService/Api/get/voucherCreateBankList";
	public static String getVoucherCreateSummaryDetailByAccount = "/empService/Api/get/voucherCreateSummaryDetailByAccount";
	public static String getcaptcha = "/userServices/Api/get/captcha";
	public static String getDepartmentMaster = "/masterService/Api/get/departmentMasterList";
	public static String getExpensesReimbursementApprovalList ="/empService/Api/get/expenseReimbFileByEmpId"; 
	public static String getErupiLinkAccountDetails ="/empService/Api/get/erupiLinkAccountDetails"; 
	public static String saveCotodelbankdetails ="/empService/Api/add/linkMultipleAccount"; 
	public static String getSavedCBankDetails ="/empService/Api/get/linkMultipleAccountList";
	public static String cApproveReject ="/empService/Api/update/linkMultipleAccountUpdate"; 
	public static String showLinkedAccAmount ="/empService/Api/get/linkMultipleAccountAmount";
	public static String getExpensesReimbursementDetailById ="/empService/Api/get/expenseReimbFileById"; 
	public static String getOcrDetails ="/empService/Api/get/ocrDetails";
	public static String expensesReimbursementUpdate ="/empService/Api/update/expenseReimbursementUpdate"; 
	public static String gstDetailService ="/gstPanService/Api/get/gstDetails";
	public static String saveCompanyDetail = "/userServices/Api/add/saveEmployerDetails";
	public static String updateCompanyDetail = "/userServices/Api/update/updateEmployerDetails";
	public static String cashAdvanceTravelRequest = "/empService/Api/add/advanceTravelRequestCash";
	public static String travelAdvanceTravelRequest = "/empService/Api/add/advanceTravelRequest";
	public static String getAdvanceTravelRequestData = "/empService/Api/get/advanceTravelRequest";
	public static String getTravelReviewData = "/empService/Api/get/advanceTravelRequestStatus";
	public static String getTravelReviewDataByExpensesID = "/empService/Api/get/advanceTravelSequenceIdRequestStatus";
	public static String getTravelDraftDataReview = "/empService/Api/get/advanceTravelRequestDraftStatus";
	public static String updateTraveRequestData ="/empService/Api/update/advanceTravelRequestUpdate";
	public static String deleteTravelReimbursement = "/empService/Api/delete/advanceTravelRequestDelete";
	public static String getAdanceCashTravelApprpvalList = "/empService/Api/get/advanceTravelApprovalRequest";
	public static String  approveAdvanceTravelRequest = "/empService/Api/update/advanceTravelRequestApproved";
	public static String  getCashAdvanceDetailById = "/empService/Api/get/advanceTravelById";
	public static String  saveReputeIdTokenData = "/userServices/Api/add/saveRepute";
	public static String registerReputeUserDetail = "/userServices/Api/add/saveReputeDetails";
	public static String getUserDetailByMobileNumber = "/userServices/Api/get/userDetailsWithMobile";
	public static String getReputeToken = "/userServices/Api/get/reputeToken";
	public static String getReputeEmployeeList = "/repute_marketplace/Api/get/employeeList";
	public static String getReputeSingleEmployeeDetails  = "/repute_marketplace/Api/get/employeeSingle";
	public static String getReputeEmployeeStatusUpdate = "/repute_marketplace/Api/get/employeeList";
	
	public static String updateReputeEmployeeDetailByWebhoock = "/userServices/Api/update/updateReputeDetails";
	public static String saveDirectorOnboarding = "/empService/Api/add/directorOnboarding";
	public static String getDirectorOnboarding = "/empService/Api/get/directorOnboarding";
	public static String getsaveWaitlist = "/userServices/Api/get/waitingListUsers";
	public static String updateWaitlist = "/userServices/Api/update/waitListUsers";
	public static String cashFreePaymentOrder ="/cashFree/Api/get/cashFreeOrder";
	public static String cashFreePaymentOrderDetailByOrderId = "/cashFree/Api/get/cashFreeOrderId";
	public static String viewcashFreePaymentOrderDetailByOrderId = "/cashFree/Api/get/cashFreeOrderIdView";
	public static String paymentCallBackDataSave = "/cashFree/Api/get/cashFreeOrderIdUpdate";
	public static String stagingWebhookSave = "http://13.234.119.146:8085/cashFree/Api/get/cashFreeOrderIdUpdate";
	public static String viewcashFreePaymentOrderDetailList = "/cashFree/Api/get/cashFreeOrderIdList";
	public static String getTravelRequestApprovalList ="/empService/Api/get/advanceTravelRequest";
	public static String getTravelRequestRequestById ="/empService/Api/get/advanceTravelById";
	public static String getExpanseLimitByExpenseTitleId  =	"/empService/Api/get/expenseCategoryBandId";
	public static String employerOnboarding = "userServices/Api/add/saveEmployerOnBoardingDetails";
		 
	public static String getVehicleManagementList="/empService/Api/get/getVehicleManagement";
	public static String addVehicleDetails="/empService/Api/add/VehicleManagement";
	public static String getVehicleManagementById = "/empService/Api/get/getVehicleManagementById";
	public static String getDriverDetail = "/empService/Api/get/getEmployeeDriver";
	public static String updateVehicleDetails = "/empService/Api/add/tripVehicleDetails";
	public static String vehichleTripHistory =  "/empService/Api/get/getTripHistoryList";
	
	public static String preprodWebhookCallback ="http://13.234.119.146:9085/cashFree/Api/get/cashFreeOrderIdUpdate";
	public static String getVehicleNumberDetaiilByVehicleNumber ="/gstPanService/Api/get/checkVehicleNo";
	public static String checkAccountNumberValidation ="/get/bankVerification";
	
	public static String activeInactiveVoucherAmount = "/empService/Api/get/voucherAmount";
	public static String usedAmountByCategories = "/empService/Api/get/erupiVoucherCreateListByPurposeCode";
	public static String  empOnboardingListTotalActive ="/empService/Api/get/empOnboardingListTotalActive";
	public static String erupiVoucherCreateListLimit ="/empService/Api/get/erupiVoucherCreateListLimit";
	//public static String  getVoucherTransactionList = "/empService/Api/get/erupiVoucherCreateTransactionList";
	public static String  getVoucherTransactionList = "/empService/Api/get/erupiVoucherCreateListRedeem";
	public static String bulkVehicleUpload = "/empService/Api/add/vehicleBulkUploadNew";
	public static String createbulkVehicle = "/empService/Api/add/vehicleBulkCreate";
	public static String currentMonthAmountLimit = "/cashFree/Api/get/currentMonthAmountLimit";
	
	
	public static String bulkEmpUpload = "/empService/Api/add/employeeBulkVoucherUpload";
	public static String createbulkEmpUpload = "/empService/Api/add/employeeBulkVoucherCreate";
	public static String submitTicket = "/empService/Api/add/ticket";
	public static String getTicket = "/empService/Api/get/allTicket";
	public static String getAdminTicketForAction = "/empService/Api/get/allTicketAdmin";
	public static String getTicketDetailById = "/empService/Api/get/ticketById";
	public static String submitTicketReply = "/empService/Api/add/ticketTransaction";
	public static String ticketReplyHistory = "/empService/Api/get/ticketTransHistory";
	///brnad api
	public static String activateBrandManagement = "/erupiBrandService/Api/add/erupiBrandOutletDetails";
	public static String brandList = "/erupiBrandService/Api/get/erupiBrandList";
	public static String brandOutletList = "/erupiBrandService/Api/get/erupiBrandOutletList";
	public static String addBrandDetails = "/erupiBrandService/Api/add/erupiBrandDetails";
	public static String addGeograpichDetails="/erupiBrandService/Api/add/erupiBrandGeoList";
	public static String getBrupiBrandGeoList="/erupiBrandService/Api/get/erupiBrandGeoList";
	public static String getOutletDetail="/erupiBrandService/Api/get/erupiBrandOutletList";
	public static String bulkOutletUpload ="/erupiBrandService/Api/add/outletBulkUpload";
	public static String confirmBulkOutletUpload= "/erupiBrandService/Api/add/outletBulkCreate";
	public static String brandOutletDetailById="/erupiBrandService/Api/get/erupiBrandOutletById";
	public static String saveBrandOutletDeviceDetails="/erupiBrandService/Api/add/erupiBrandOutletDeviceDetails";
	public static String getDeviceDetailList="/erupiBrandService/Api/get/erupiBrandOutletDeviceList";
	public static String deactivateOutlet="/erupiBrandService/Api/update/brandOutletDetailsDeActive";
	public static String editOutletDetail="/erupiBrandService/Api/update/updateBrandOutletDetails";
	public static String activateDeactivateLinkedDevice="/erupiBrandService/Api/update/outletDeviceDetailsDeActive";
	public static String reviewLinkedDevice="/erupiBrandService/Api/get/erupiBrandOutletDevice";
	
}
