package com.cotodel.hrms.web.service.Impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.cotodel.hrms.web.function.common.CommonUtils;
import com.cotodel.hrms.web.properties.ApplicationConstantConfig;
import com.cotodel.hrms.web.response.BulkEmployeeRequest;
import com.cotodel.hrms.web.response.UserRequest;
import com.cotodel.hrms.web.service.BulkEmployeeService;
import com.cotodel.hrms.web.util.CommonUtility;
import com.cotodel.hrms.web.util.EncriptResponse;
import com.cotodel.hrms.web.util.MessageConstant;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class BulkEmployeeServiceImpl implements BulkEmployeeService {
	
	private static final Logger logger = LoggerFactory.getLogger(BulkEmployeeServiceImpl.class);

	
	@Autowired
	public ApplicationConstantConfig applicationConstantConfig;

	@Override
	public String saveBulkDetail(String token, BulkEmployeeRequest bulkEmployeeRequest) {
		
		String response="";
		String res=null;
		
		String filename="";
		if(bulkEmployeeRequest.docfile==null)
			bulkEmployeeRequest.setDocfile(bulkEmployeeRequest.getUp());
		HashMap<String, String> saveMap = new  HashMap<String, String> ();
		List<UserRequest> userCorrectList=new ArrayList<UserRequest>();
		List<UserRequest> userInCorrectList=new ArrayList<UserRequest>();
		filename=bulkEmployeeRequest.getDocfile().getOriginalFilename();
		if(!filename.contains(".xlsx")) {
			saveMap.put("status", "N");
			saveMap.put("message","File Extention is not correct.Please Download Bulk Sample.");
		}
		
		XSSFWorkbook workbook =null;
		try {
			workbook =new XSSFWorkbook(bulkEmployeeRequest.getDocfile().getInputStream());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		XSSFSheet worksheet = workbook.getSheetAt(0);
		DataFormatter formatter = new DataFormatter();
		XSSFRow row1 = worksheet.getRow(0);
		String str=row1.getCell(1).toString().trim();
		int col=row1.getLastCellNum();
		if(col>10) {
			saveMap.put("status", "N");
			saveMap.put("message","File format is not correct.");
		}
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
		if(worksheet.getLastRowNum() < 2000) {
			for(int i=1;i<worksheet.getPhysicalNumberOfRows() ;i++) {
				UserRequest userRequest = new UserRequest();
				boolean toprint = false;
				XSSFRow row = worksheet.getRow(i);				
				if (isRowEmpty(row)) {
			        break; // Exit the while loop if the row is blank
			    }
				Cell cl1 = row.getCell(1);
				if(cl1==null) {
					//return ContollerResponse.returnResponse(saveMap); 
				}
				String str2="";	
				
				str2=cl1.getStringCellValue().replaceAll("\\s", ""); 
				String userType=str2;
				String name=formatter.formatCellValue(row.getCell(2));
				String mobile=formatter.formatCellValue(row.getCell(3));
				userRequest.setName(name);
				userRequest.setEmpOrCont(userType);
				userRequest.setEmployerId(bulkEmployeeRequest.getEmployerId().intValue());
				userRequest.setMobile(mobile);
				boolean CustomCheckEmail=false;
				boolean CustomCheckUpdate=false;
				if(bulkEmployeeRequest.getCustomCheckUpdate()!= null && bulkEmployeeRequest.getCustomCheckUpdate().equals("on")) {
					CustomCheckUpdate=true;
				}
				userRequest.setUpdateStatus(CustomCheckUpdate);
				userRequest.setEmailStatus(CustomCheckEmail);
				boolean validmobile=isValidMobile(userRequest.getMobile());				
				//boolean validEmail= isValidEmail(userRequest.getEmail());
				boolean validName= isValidName(name);
				logger.info(userRequest.getMobile() +" : "+ validmobile+"\n"); 
	            logger.info(name +" : "+ validName+"\n"); 
	                        
				try {
					logger.info("userRequest::"+userRequest.toString());					
					if(validmobile && validName) {
						response=CommonUtility.bulkUserRequest(token,MessageConstant.gson.toJson(userRequest), applicationConstantConfig.employerServiceBaseUrl+CommonUtils.regiUserBulk,
								applicationConstantConfig.apiSignaturePublicPath, applicationConstantConfig.apiSignaturePrivatePath);
						if(!ObjectUtils.isEmpty(response)) {
							JSONObject demoRes= new JSONObject(response);
							boolean status = demoRes.getBoolean("status");
							if(status) {
								userRequest.setResponse(MessageConstant.RESPONSE_SUCCESS);
								userCorrectList.add(userRequest);
							}else if(!status) {
								userRequest.setResponse(demoRes.getString("message"));
								userInCorrectList.add(userRequest);
							}
								
							}
					}else {
						userRequest.setResponse(MessageConstant.NAME_MOBILE_INVALID);
						userInCorrectList.add(userRequest);
					}
					
					
					
				} catch (Exception e) {
					toprint=true;
					saveMap.put("status", "N");
					saveMap.put("message","");
					e.printStackTrace();
				}
				
			}
		}else {

		}
		
		try {
			saveMap.put("status", MessageConstant.RESPONSE_SUCCESS);
			int correctSize=userCorrectList!=null?userCorrectList.size():0;
			int incorrectSize=userCorrectList!=null?userInCorrectList.size():0;
			int total=correctSize+incorrectSize;
			String correct=mapper.writeValueAsString(userCorrectList);
			String incorrect=mapper.writeValueAsString(userInCorrectList);
			saveMap.put("correct", correct);
			saveMap.put("incorrect", incorrect);
			saveMap.put("correctSize", ""+correctSize);
			saveMap.put("incorrectSize", ""+incorrectSize);
			saveMap.put("total", ""+total);			
			res=mapper.writeValueAsString(saveMap);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return res;
	}

	
	public static boolean isValidMobile(String mobile)
    {       
        Pattern p = Pattern.compile("^\\d{10}$"); 
        Matcher m = p.matcher(mobile); 
        return (m.matches());
    }
	
	public static boolean isValidName(String name) {
	    if (name == null || name.trim().isEmpty()) {
	        return false;
	    }

	    // Regular expression that allows only alphabets and spaces
	    String regex = "^[A-Za-z]+(\\s[A-Za-z]+)*$";
	    Pattern pattern = Pattern.compile(regex);
	    Matcher matcher = pattern.matcher(name);

	    return matcher.matches();  // Returns true if the name matches the pattern
	}
	
	public static boolean isValidEmail(String email)
    {       
		 String regex = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";  
	     //Compile regular expression to get the pattern  
	     Pattern pattern = Pattern.compile(regex);  
	     Matcher matcher = pattern.matcher(email);
	     return matcher.matches();
    }
	private boolean isRowEmpty(Row row) {
	    for (int i = row.getFirstCellNum(); i < row.getLastCellNum(); i++) {
	        Cell cell = row.getCell(i);
	        if (cell != null && !cell.toString().trim().isEmpty()) {
	            return false; // Row is not empty if at least one cell has content
	        }
	    }
	    return true; // Row is empty
	}


	@Override
	public String saveBulkEmpDetail(String token, EncriptResponse jsonObject) {
		return CommonUtility.userRequest(token,MessageConstant.gson.toJson(jsonObject), applicationConstantConfig.employerServiceBaseUrl+CommonUtils.bulkEmpUpload);

		
	}
	@Override
	public String createBulkEmpDetail(String token, EncriptResponse jsonObject) {
		return CommonUtility.userRequest(token,MessageConstant.gson.toJson(jsonObject), applicationConstantConfig.employerServiceBaseUrl+CommonUtils.createbulkEmpUpload);

		
	}
	
}
