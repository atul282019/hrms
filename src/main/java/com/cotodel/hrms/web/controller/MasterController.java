package com.cotodel.hrms.web.controller;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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
import com.cotodel.hrms.web.response.EmployeeMassterRequest;
import com.cotodel.hrms.web.response.ErupiLinkBankAccount;
import com.cotodel.hrms.web.response.ErupiVoucherMaster;
import com.cotodel.hrms.web.response.MccMaster;
import com.cotodel.hrms.web.response.UserRegistrationRequest;
import com.cotodel.hrms.web.service.MasterService;
import com.cotodel.hrms.web.service.Impl.TokenGenerationImpl;



@Controller
@CrossOrigin
public class MasterController  extends CotoDelBaseController{
	
	private static final Logger logger = LoggerFactory.getLogger(MasterController.class);
	
	@Autowired
	public ApplicationConstantConfig applicationConstantConfig;
	
	@Autowired
	public MasterService masterService;
	

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
		logger.info("getEmployeeType");	
		String token = (String) session.getAttribute("cotodel");
		return masterService.getofficeLocationMaster(tokengeneration.getToken(),employeeMassterRequest);
	}
	
	

}
