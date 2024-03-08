package com.cotodel.hrms.web.function.common;

public interface CommonUtils {


	public static String tokenUrl="/tokenService/Api/get/access-token";
	public static String registerUserUrl = "/userServices/Api/get/saveUserDetails";
	public static String verifyEmail = "/userServices/Api//verifyLink";
	public static String sendOtp = "/userServices/Api/getOtp";
	public static String sendOtpNew = "/userServices/Api/getOtpNew";
	public static String resendOtpNew = "/userServices/Api/getOtpResend";
	public static String verifyOtpNew = "/userServices/Api/verifyOtpNew";
	
	public static String verifyOtp = "/userServices/Api/verifyOtp";

	public static String stateMaster = "/userServices/Api/get/state-list";
	public static String orgMaster = "/userServices/Api/get/Organization";
	public static String registerCompany = "/empService/Api/get/saveProfileDetails";
	public static String saveEmployee = "/empService/Api/add/employeeDetails";
	public static String getPayrollList = "/userServices/Api/get/payroll-list";
	public static String savePayrollDetail = "/empService/Api/save/payrollDetails";
	public static String getPermission  = "/userServices/Api/get/Permissions";
	public static String getRole  = "/userServices/Api/get/Roles";
	public static String singleUserCreation  = "/userServices/Api/add/saveUsers";
	public static String getSingleUser  = "/userServices/Api/add/saveUsers";
	
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
	
	
	
	
}
