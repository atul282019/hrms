package com.cotodel.hrms.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

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
import com.cotodel.hrms.web.response.DepartmentMaster;
import com.cotodel.hrms.web.response.EmployeeMassterRequest;
import com.cotodel.hrms.web.response.ErupiLinkBankAccount;
import com.cotodel.hrms.web.response.ErupiVoucherMaster;
import com.cotodel.hrms.web.response.MccMaster;
import com.cotodel.hrms.web.response.UserRegistrationRequest;
import com.cotodel.hrms.web.response.VoucherTypeMaster;
import com.cotodel.hrms.web.service.DepartmentMasterService;
import com.cotodel.hrms.web.service.MasterService;
import com.cotodel.hrms.web.service.Impl.TokenGenerationImpl;
import com.fasterxml.jackson.databind.ObjectMapper;



@Controller
@CrossOrigin
public class MasterController  extends CotoDelBaseController{
	
	private static final Logger logger = LoggerFactory.getLogger(MasterController.class);
	
	@Autowired
	public ApplicationConstantConfig applicationConstantConfig;
	
	@Autowired
	public MasterService masterService;
	
	@Autowired
	public DepartmentMasterService departmentMasterService;
	

	@Autowired
	TokenGenerationImpl tokengeneration;
	
	@GetMapping(value="/getStateMaster")
	public @ResponseBody String getStateMaster(HttpServletRequest request, ModelMap model,Locale locale,
			HttpSession session,UserRegistrationRequest userForm) {
		logger.info("getStateMaster");	
		String token = (String) session.getAttribute("cotodel");
		return masterService.getStateMaster(tokengeneration.getToken(),userForm);
	}

	@GetMapping(value="/getOrgMaster")
	public @ResponseBody String getOrgMaster(HttpServletRequest request, ModelMap model,Locale locale,HttpSession session
			,UserRegistrationRequest userForm) {
		return masterService.getOrgMaster(tokengeneration.getToken(),userForm);
	}
	
	@GetMapping(value="/getPermission")
	public @ResponseBody String getPermission(HttpServletRequest request, ModelMap model,Locale locale,HttpSession session
			,UserRegistrationRequest userForm) {
		return masterService.getPermission(tokengeneration.getToken(),userForm);
	}
	
	@GetMapping(value="/getRole")
	public @ResponseBody String getRole(HttpServletRequest request, ModelMap model,Locale locale,HttpSession session
			,UserRegistrationRequest userForm) {
		return masterService.getRole(tokengeneration.getToken(),userForm);
	}
	
	
	  @PostMapping(value="/getBankMaster") public @ResponseBody String getBankMaster(HttpServletRequest request, ModelMap model,Locale
	  locale,HttpSession session ,UserRegistrationRequest userForm) {
		  return  masterService.getBankMaster(tokengeneration.getToken(),userForm); 
	  }
	 
	
	@PostMapping(value="/getVoucherDetailByBoucherCode")
	public @ResponseBody String getVoucherDetailByBoucherCode(HttpServletRequest request, ModelMap model,Locale locale,HttpSession session
			,ErupiVoucherMaster erupiVoucherMaster) {
		return masterService.getVoucherDescriptionByVoucherCode(tokengeneration.getToken(),erupiVoucherMaster);
	}
	
	@PostMapping(value="/getBankDetailByAccountNo")
	public @ResponseBody String getBankDetailByAccountNo(HttpServletRequest request, ModelMap model,Locale locale,HttpSession session
			,ErupiLinkBankAccount erupiLinkBankAccount) {
		return masterService.getBankDetailByAccountNo(tokengeneration.getToken(),erupiLinkBankAccount);
	}
	
	@PostMapping(value="/getmccMasterListByPurposeCode")
	public @ResponseBody String getmccMasterListByPurposeCode(HttpServletRequest request, ModelMap model,Locale locale,HttpSession session
			,MccMaster mccMaster) {
		return masterService.getmccMasterListByPurposeCode(tokengeneration.getToken(),mccMaster);
	}
	
	@PostMapping(value="/getmccMasterDetailsByPurposeCodeAndMcc")
	public @ResponseBody String getmccMasterDetailsByPurposeCodeAndMcc(HttpServletRequest request, ModelMap model,Locale locale,HttpSession session
			,MccMaster mccMaster) {
		return masterService.getmccMasterDetailsByPurposeCodeAndMcc(tokengeneration.getToken(),mccMaster);
	}
	
	@GetMapping(value="/getEmployeeType")
	public @ResponseBody String getEmployeeType(HttpServletRequest request, ModelMap model,Locale locale,
			HttpSession session,EmployeeMassterRequest employeeMassterRequest) {
		logger.info("getEmployeeType");	
		String token = (String) session.getAttribute("cotodel");
		return masterService.getEmployeeType(tokengeneration.getToken(),employeeMassterRequest);
	}

	@GetMapping(value="/getEmployeeMasterList")
	public @ResponseBody String getEmployeeListMaster(HttpServletRequest request, ModelMap model,Locale locale,
			HttpSession session,EmployeeMassterRequest employeeMassterRequest) {
		logger.info("getEmployeeType");	
		String token = (String) session.getAttribute("cotodel");
		return masterService.getEmployeeListMaster(tokengeneration.getToken(),employeeMassterRequest);
	}
	
	@GetMapping(value="/getofficeLocationMaster")
	public @ResponseBody String getofficeLocationMaster(HttpServletRequest request, ModelMap model,Locale locale,
			HttpSession session,EmployeeMassterRequest employeeMassterRequest) {
		logger.info("getofficeLocationMaster");	
		String token = (String) session.getAttribute("cotodel");
		return masterService.getofficeLocationMaster(tokengeneration.getToken(),employeeMassterRequest);
	}
	
	@GetMapping(value="/getVoucherListWithIcon")
	public @ResponseBody String getVoucherListWithIcon(HttpServletRequest request, ModelMap model,Locale locale,
			HttpSession session,EmployeeMassterRequest employeeMassterRequest) {
		logger.info("getofficeLocationMaster");	
		String token = (String) session.getAttribute("cotodel");
		return masterService.getVoucherListWithIcon(tokengeneration.getToken(),employeeMassterRequest);
	}
	
	@GetMapping(value="/getPurposeListByVoucherCode")
	public @ResponseBody String getPurposeListByVoucherCode(HttpServletRequest request, ModelMap model,Locale locale,
			HttpSession session,EmployeeMassterRequest employeeMassterRequest) {
		logger.info("getofficeLocationMaster");	
		String token = (String) session.getAttribute("cotodel");
		return masterService.getPurposeListByVoucherCode(tokengeneration.getToken(),employeeMassterRequest);
	}
	
	@PostMapping(value="/voucherCreateBankList")
	public @ResponseBody String getBankListWithVocher(HttpServletRequest request, ModelMap model,Locale locale,
			HttpSession session,EmployeeMassterRequest employeeMassterRequest) {
		logger.info("voucherCreateBankList");	
		String token = (String) session.getAttribute("cotodel");
		return masterService.getBankListWithVocher(tokengeneration.getToken(),employeeMassterRequest);
	}
	
	@PostMapping(value="/voucherCreateSummaryDetailByAccount")
	public @ResponseBody String voucherCreateSummaryDetailByAccount(HttpServletRequest request, ModelMap model,Locale locale,
			HttpSession session,EmployeeMassterRequest employeeMassterRequest) {
		logger.info("voucherCreateSummaryDetailByAccount");	
		String token = (String) session.getAttribute("cotodel");
		return masterService.voucherCreateSummaryDetailByAccount(tokengeneration.getToken(),employeeMassterRequest);
	}
	
	@GetMapping(value="/getDepartmentMaster")
	public @ResponseBody String getDepartmentMaster(HttpServletRequest request, ModelMap model,Locale locale,HttpSession session
			,DepartmentMaster departmentMaster) {
		logger.info("getDepartmentMaster");	
		return departmentMasterService.getDepartmentMaster(tokengeneration.getToken(),departmentMaster);
	}
	
//	@GetMapping(value="/getDepartmentMaster")
//	public @ResponseBody String getDepartmentMaster(ModelMap model, Locale locale, HttpSession session,DepartmentMaster departmentMaster) {
//        String departmentResponse = null;
//        JSONObject departmentJsonResponse = null;
//        Map<String, Object> responseMap = new HashMap<>();
//        ObjectMapper mapper = new ObjectMapper();
//        String jsonResponse = null;
//        // Call the service to save the departmentResponse object
//        departmentResponse = departmentMasterService.getDepartmentMaster(tokengeneration.getToken(),departmentMaster);
//        logger.debug(departmentResponse);  // Logging the response
//        departmentJsonResponse = new JSONObject(departmentResponse);      
//        if(departmentJsonResponse.getBoolean("status")) { 
//			
////			responseMap.put("data", departmentJsonResponse.getJSONArray("data"));
//			List<Object> departmentList = departmentJsonResponse.getJSONArray("data").toList();
//			responseMap.put("status",true);
//			responseMap.put("data", departmentList);
//        }else {
//			//loginsevice.rsendEmailVerificationCompletion(userForm);
//			responseMap.put("status", false);
//		}
//        try {
//            jsonResponse = mapper.writeValueAsString(responseMap);
//        } catch (Exception e) {
//            e.printStackTrace(); 
//        }       
//        return jsonResponse;
//    }
}
