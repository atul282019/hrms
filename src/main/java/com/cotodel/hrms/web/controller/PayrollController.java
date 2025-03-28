package com.cotodel.hrms.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cotodel.hrms.web.properties.ApplicationConstantConfig;
import com.cotodel.hrms.web.response.EmployeePayrollNew;
import com.cotodel.hrms.web.response.EmployeePayrollRequest;
import com.cotodel.hrms.web.response.PayrollRequest;
import com.cotodel.hrms.web.service.PayrollService;
import com.cotodel.hrms.web.service.Impl.TokenGenerationImpl;
import com.cotodel.hrms.web.util.EncriptResponse;
import com.cotodel.hrms.web.util.EncryptionDecriptionUtil;
import com.cotodel.hrms.web.util.MessageConstant;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@CrossOrigin
public class PayrollController extends CotoDelBaseController{

	private static final Logger logger = LoggerFactory.getLogger(CompanyDetailController.class);

	@Autowired
	public ApplicationConstantConfig applicationConstantConfig;
	
	@Autowired
	PayrollService payrollService;

	@Autowired
	TokenGenerationImpl tokengeneration;
	
//	@GetMapping(value="/getPayrollMaster")
//	public @ResponseBody String getStateMaster(HttpServletRequest request, ModelMap model,Locale locale,
//			HttpSession session,PayrollRequest payrollRequest) {
//			logger.info("getPayrollMaster");	
//			String token = (String) session.getAttribute("hrms");
//			String profileRes=null;
//			//return payrollService.getPayrollMaster(tokengeneration.getToken(),payrollRequest);
//			
//			try {
//				String json = EncryptionDecriptionUtil.convertToJson(payrollRequest);
//
//				EncriptResponse jsonObject=EncryptionDecriptionUtil.encriptResponse(json, applicationConstantConfig.apiSignaturePublicPath);
//
//				String encriptResponse = payrollService.getPayrollMaster(tokengeneration.getToken(), jsonObject);
//
//	   
//				EncriptResponse userReqEnc =EncryptionDecriptionUtil.convertFromJson(encriptResponse, EncriptResponse.class);
//
//				profileRes =  EncryptionDecriptionUtil.decriptResponse(userReqEnc.getEncriptData(), userReqEnc.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//	   
//		return profileRes;
//			  
//			
//	}
//	

//	@PostMapping(value="/saveCompanyPayroll")
//	public @ResponseBody String savePayrollDetail(HttpServletRequest request, ModelMap model,Locale locale,HttpSession session,EmployeePayrollRequest employeePayrollRequest) {
//		String profileRes=null;JSONObject profileJsonRes=null;
//		HashMap<String, String> otpMap = new  HashMap<String, String> ();
//		ObjectMapper mapper = new ObjectMapper();
//		String res=null;String userRes=null;
//		List<EmployeePayrollNew> list=new ArrayList<EmployeePayrollNew>();
//		String data[]=employeePayrollRequest.getListArray();
//		for (int i = 0; i < data.length; i++) {
//			String listValue=data[i];
//			String[] rowArray=listValue.split("@");
//			EmployeePayrollNew employeePayrollNew=new EmployeePayrollNew();
//			employeePayrollNew.setSalary_component(rowArray[0]);
//			employeePayrollNew.setPer_ctc(rowArray[1]);
//			employeePayrollNew.setPer(rowArray[2]);
//			employeePayrollNew.setTaxable(rowArray[3]);
//			employeePayrollNew.setEmployerId(employeePayrollRequest.getEmployerId().longValue());
//			list.add(employeePayrollNew);
//		}
//		
//		employeePayrollRequest.setList(list);
////		profileRes = payrollService.savePayrollDetail(tokengeneration.getToken(),employeePayrollRequest);
////		profileJsonRes= new JSONObject(profileRes);
////			
////		if(profileJsonRes.getBoolean("status")) { 
////			otpMap.put("status", MessageConstant.RESPONSE_SUCCESS);
////		}else {
////			loginservice.sendEmailVerificationCompletion(userForm);
////			otpMap.put("status", MessageConstant.RESPONSE_FAILED);
////		}
////		try {
////			res = mapper.writeValueAsString(otpMap);
////		} catch (Exception e) {
////			
////		}
////		
////		return profileRes;
//		
//		try {
//			String json = EncryptionDecriptionUtil.convertToJson(employeePayrollRequest);
//
//			EncriptResponse jsonObject=EncryptionDecriptionUtil.encriptResponse(json, applicationConstantConfig.apiSignaturePublicPath);
//
//			String encriptResponse =  payrollService.savePayrollDetail(tokengeneration.getToken(), jsonObject);
//
//   
//			EncriptResponse userReqEnc =EncryptionDecriptionUtil.convertFromJson(encriptResponse, EncriptResponse.class);
//
//			profileRes =  EncryptionDecriptionUtil.decriptResponse(userReqEnc.getEncriptData(), userReqEnc.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//   
//	return profileRes;
//	}
	

}
