package com.cotodel.hrms.web.function.common;

public interface CommonUtils {


	public static String tokenUrl="/tokenService/Api/get/access-token";
	public static String registerUserUrl = "/userServices/Api/get/saveUserDetails";
	public static String verifyEmail = "/userServices/Api//verifyLink";
	public static String sendOtp = "/userServices/Api/getOtp";
	public static String verifyOtp = "/userServices/Api/verifyOtp";

	public static String stateMaster = "/userServices/Api/get/state-list";
	public static String orgMaster = "/userServices/Api/get/Organization";
	public static String registerCompany = "/userServices/Api/get/saveProfileDetails";
	public static String saveEmployee = "/empService/Api/add/employeeDetails";
	public static String getPayrollList = "/userServices/Api/get/payroll-list";
	public static String savePayrollDetail = "/empService/Api/save/payrollDetails";
	public static String getPermission  = "/userServices/Api/get/Permissions";
	public static String getRole  = "/userServices/Api/get/Roles";
	
}
