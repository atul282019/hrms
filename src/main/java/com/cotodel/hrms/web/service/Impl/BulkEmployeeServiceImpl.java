package com.cotodel.hrms.web.service.Impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cotodel.hrms.web.controller.BulkUserController;
import com.cotodel.hrms.web.function.common.CommonUtils;
import com.cotodel.hrms.web.properties.ApplicationConstantConfig;
import com.cotodel.hrms.web.response.BulkEmployeeRequest;
import com.cotodel.hrms.web.response.UserRequest;
import com.cotodel.hrms.web.service.BulkEmployeeService;
import com.cotodel.hrms.web.util.CommonUtility;
import com.cotodel.hrms.web.util.MessageConstant;

@Service
public class BulkEmployeeServiceImpl implements BulkEmployeeService {
	
	private static final Logger logger = LoggerFactory.getLogger(BulkEmployeeServiceImpl.class);

	
	@Autowired
	public ApplicationConstantConfig applicationConstantConfig;

	@Override
	public String saveBulkDetail(String token, BulkEmployeeRequest bulkEmployeeRequest) {
		// TODO Auto-generated method stub
		
		String response="";
		String filename="";
		String mob="Mobile Number\n"
				+ "(Registered in  Aadhar)";
		HashMap<String, String> saveMap = new  HashMap<String, String> ();
		
		filename=bulkEmployeeRequest.getDocfile().getOriginalFilename();
		if(!filename.contains(".xlsx")) {
			saveMap.put("status", "N");
			saveMap.put("message","File Extention is not correct.Please Download Bulk Sample.");
			//return ContollerResponse.returnResponse(saveMap);
		}
		System.out.println(filename.substring(filename.indexOf("."), filename.length()));
		
		XSSFWorkbook workbook =null;
		try {
			workbook =new XSSFWorkbook(bulkEmployeeRequest.getDocfile().getInputStream());
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		
		XSSFSheet worksheet = workbook.getSheetAt(0);
		DataFormatter formatter = new DataFormatter();
		XSSFRow row1 = worksheet.getRow(0);
		String str=row1.getCell(1).toString().trim();
		int col=row1.getLastCellNum();
		if(col>10) {
			saveMap.put("status", "N");
			saveMap.put("message","File format is not correct.");
			//return ContollerResponse.returnResponse(saveMap); 
		}
		
		if(worksheet.getLastRowNum() < 2000) {
			for(int i=1;i<worksheet.getPhysicalNumberOfRows() ;i++) {
				UserRequest userRequest = new UserRequest();
				boolean toprint = false;
				XSSFRow row = worksheet.getRow(i);				
				
				Cell cl1 = row.getCell(1);
				if(cl1==null) {
					//return ContollerResponse.returnResponse(saveMap); 
				}
				//logger.info("Type::"+cl1.getCellType());
				String str2="";	
				
				str2=cl1.getStringCellValue().replaceAll("\\s", ""); 
					
				userRequest.setFirst_name(str2);
				userRequest.setLast_name(formatter.formatCellValue(row.getCell(2)));
				userRequest.setEmail(formatter.formatCellValue(row.getCell(3)));
				userRequest.setMobile(formatter.formatCellValue(row.getCell(4)));
				userRequest.setUsername(formatter.formatCellValue(row.getCell(5)));
			
				try {
					
					System.out.println("userRequest::"+userRequest.toString());
					response=CommonUtility.userRequest(token,MessageConstant.gson.toJson(userRequest), applicationConstantConfig.userServiceBaseUrl+CommonUtils.regiUserBulk);
										
				} catch (Exception e) {
					toprint=true;
					saveMap.put("status", "N");
					saveMap.put("message","");
					e.printStackTrace();
				}
				
			}
		}else {
//			ExcelResponse exl = new ExcelResponse();
//			exl.setMobile("");
//			exl.setName("");
//			exl.setGender("");
//			exl.setDob("");
//			exl.setEmail("");
//			exl.setCscid("");
//			exl.setStatus("Please Upload Excel Less Than 2000 Records");
//			excelResponse.add(exl);
//			saveMap.put("status", "N");
//			saveMap.put("message","Please Upload Excel Less Than 2000 Records.");
			
		}
		return response;
	}

	
	
	
	
	
//	@Override
//	public String saveEmployeeDetail(String token, EmployeeDetailsNewRequest employeeDetailRequest) {
//		// return CommonUtility.userRequest(token,MessageConstant.gson.toJson(employeeDetailRequest), applicationConstantConfig.employerServiceBaseUrl+CommonUtils.empDetails);
//		return CommonUtility.userRequest(token,MessageConstant.gson.toJson(employeeDetailRequest), applicationConstantConfig.employerServiceBaseUrl+CommonUtils.empDetails);
//	}
//
//	@Override
//	public String saveFamilyDetail(String token, EmployeeFamilyDetailRequest employeeFamilyDetailRequest) {
//		// TODO Auto-generated method stub
//		return CommonUtility.userRequest(token,MessageConstant.gson.toJson(employeeFamilyDetailRequest), applicationConstantConfig.employerServiceBaseUrl+CommonUtils.empFamilyDetails);
//	}
//
//	@Override
//	public String getEmployeeDetail(String token, EmployeeDetailsRequest employeeFamilyDetailRequest) {
//		// TODO Auto-generated method stub
//		return CommonUtility.userRequest(token,MessageConstant.gson.toJson(employeeFamilyDetailRequest), applicationConstantConfig.employerServiceBaseUrl+CommonUtils.getEmployeeDetails);
//	}
//
//	@Override
//	public String getEmployeeFamilyDetail(String token, EmployeeFamilyDetailRequest employeeFamilyDetailRequest) {
//		// TODO Auto-generated method stub
//		return CommonUtility.userRequest(token,MessageConstant.gson.toJson(employeeFamilyDetailRequest), applicationConstantConfig.employerServiceBaseUrl+CommonUtils.getEmployeeFamilyDetails);
//	}
//
//	@Override
//	public String saveFamilyQualification(String token, EmployeeQualificationRequest employeeQualificationRequest) {
//		// TODO Auto-generated method stub
//		return CommonUtility.userRequest(token,MessageConstant.gson.toJson(employeeQualificationRequest), applicationConstantConfig.employerServiceBaseUrl+CommonUtils.saveEmployeeQualification);
//	}
//
//	@Override
//	public String getEmployeeQualificationDetail(String token,
//			EmployeeQualificationRequest employeeQualificationRequest) {
//		// TODO Auto-generated method stub
//		return CommonUtility.userRequest(token,MessageConstant.gson.toJson(employeeQualificationRequest), applicationConstantConfig.employerServiceBaseUrl+CommonUtils.getEmployeeQualification);
//	}
//
//	@Override
//	public String saveEmpCertificateDetail(String token, EmployeeExperienceRequest	 employeeExperienceRequest) {
//		// TODO Auto-generated method stub
//		return CommonUtility.userRequest(token,MessageConstant.gson.toJson(employeeExperienceRequest), applicationConstantConfig.employerServiceBaseUrl+CommonUtils.saveEmpExperience);
//	}
//
//	@Override
//	public String getEmployeeExperience(String token, EmployeeExperienceRequest employeeExperienceRequest) {
//		// TODO Auto-generated method stub
//		return CommonUtility.userRequest(token,MessageConstant.gson.toJson(employeeExperienceRequest), applicationConstantConfig.employerServiceBaseUrl+CommonUtils.getEmpExperience);
//	}
//
//	@Override
//	public String saveEmployeeCertificate(String token, EmployeeCertificateRequest employeeCertificateRequest) {
//		// TODO Auto-generated method stub
//		return CommonUtility.userRequest(token,MessageConstant.gson.toJson(employeeCertificateRequest), applicationConstantConfig.employerServiceBaseUrl+CommonUtils.saveEmpCertificate);
//	}
//
//	@Override
//	public String getEmployeeCertificateDetail(String token, EmployeeCertificateRequest employeeCertificateRequest) {
//		return CommonUtility.userRequest(token,MessageConstant.gson.toJson(employeeCertificateRequest), applicationConstantConfig.employerServiceBaseUrl+CommonUtils.getEmpCertificate);
//	}
//
//	@Override
//	public String saveEmployeeProject(String token, EmployeeProjectRequest employeeProjectRequest) {
//		return CommonUtility.userRequest(token,MessageConstant.gson.toJson(employeeProjectRequest), applicationConstantConfig.employerServiceBaseUrl+CommonUtils.saveEmpProject);
//	}
//
//	@Override
//	public String getEmployeeProjectDetail(String token, EmployeeProjectRequest employeeProjectRequest) {
//		return CommonUtility.userRequest(token,MessageConstant.gson.toJson(employeeProjectRequest), applicationConstantConfig.employerServiceBaseUrl+CommonUtils.getEmpProject);
//	}
	
}
