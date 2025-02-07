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
import com.cotodel.hrms.web.util.EncriptResponse;
import com.cotodel.hrms.web.util.EncryptionDecriptionUtil;
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
		//return masterService.getStateMaster(tokengeneration.getToken(),userForm);
		
		String profileRes=null;
		
		try {
			String json = EncryptionDecriptionUtil.convertToJson(userForm);

			EncriptResponse jsonObject=EncryptionDecriptionUtil.encriptResponse(json, applicationConstantConfig.apiSignaturePublicPath);

			String encriptResponse =  masterService.getStateMaster(tokengeneration.getToken(), jsonObject);

   
			EncriptResponse userReqEnc =EncryptionDecriptionUtil.convertFromJson(encriptResponse, EncriptResponse.class);

			profileRes =  EncryptionDecriptionUtil.decriptResponse(userReqEnc.getEncriptData(), userReqEnc.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
   
	return profileRes;
	}

	@GetMapping(value="/getOrgMaster")
	public @ResponseBody String getOrgMaster(HttpServletRequest request, ModelMap model,Locale locale,HttpSession session
			,UserRegistrationRequest userForm) {
		//return masterService.getOrgMaster(tokengeneration.getToken(),userForm);
		String profileRes=null;
		
		try {
			String json = EncryptionDecriptionUtil.convertToJson(userForm);

			EncriptResponse jsonObject=EncryptionDecriptionUtil.encriptResponse(json, applicationConstantConfig.apiSignaturePublicPath);

			String encriptResponse =  masterService.getOrgMaster(tokengeneration.getToken(), jsonObject);

   
			EncriptResponse userReqEnc =EncryptionDecriptionUtil.convertFromJson(encriptResponse, EncriptResponse.class);

			profileRes =  EncryptionDecriptionUtil.decriptResponse(userReqEnc.getEncriptData(), userReqEnc.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
   
	return profileRes;
	}
	
	@GetMapping(value="/getPermission")
	public @ResponseBody String getPermission(HttpServletRequest request, ModelMap model,Locale locale,HttpSession session
			,UserRegistrationRequest userForm) {
		//return masterService.getPermission(tokengeneration.getToken(),userForm);
		String profileRes=null;
		
		try {
			String json = EncryptionDecriptionUtil.convertToJson(userForm);

			EncriptResponse jsonObject=EncryptionDecriptionUtil.encriptResponse(json, applicationConstantConfig.apiSignaturePublicPath);

			String encriptResponse =   masterService.getPermission(tokengeneration.getToken(), jsonObject);

   
			EncriptResponse userReqEnc =EncryptionDecriptionUtil.convertFromJson(encriptResponse, EncriptResponse.class);

			profileRes =  EncryptionDecriptionUtil.decriptResponse(userReqEnc.getEncriptData(), userReqEnc.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
   
	return profileRes;
	}
	
	@GetMapping(value="/getRole")
	public @ResponseBody String getRole(HttpServletRequest request, ModelMap model,Locale locale,HttpSession session
			,UserRegistrationRequest userForm) {
		//return masterService.getRole(tokengeneration.getToken(),userForm);
		String profileRes=null;
		
		try {
			String json = EncryptionDecriptionUtil.convertToJson(userForm);

			EncriptResponse jsonObject=EncryptionDecriptionUtil.encriptResponse(json, applicationConstantConfig.apiSignaturePublicPath);

			String encriptResponse =   masterService.getRole(tokengeneration.getToken(), jsonObject);

   
			EncriptResponse userReqEnc =EncryptionDecriptionUtil.convertFromJson(encriptResponse, EncriptResponse.class);

			profileRes =  EncryptionDecriptionUtil.decriptResponse(userReqEnc.getEncriptData(), userReqEnc.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
   
	return profileRes;
	}
	
	
	  @PostMapping(value="/getBankMaster") public @ResponseBody String getBankMaster(HttpServletRequest request, ModelMap model,Locale
	  locale,HttpSession session ,UserRegistrationRequest userForm) {
		 // return  masterService.getBankMaster(tokengeneration.getToken(),userForm); 
		  
			String profileRes=null;
			
			try {
				String json = EncryptionDecriptionUtil.convertToJson(userForm);

				EncriptResponse jsonObject=EncryptionDecriptionUtil.encriptResponse(json, applicationConstantConfig.apiSignaturePublicPath);

				String encriptResponse =  masterService.getBankMaster(tokengeneration.getToken(), jsonObject);

	   
				EncriptResponse userReqEnc =EncryptionDecriptionUtil.convertFromJson(encriptResponse, EncriptResponse.class);

				profileRes =  EncryptionDecriptionUtil.decriptResponse(userReqEnc.getEncriptData(), userReqEnc.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	   
		return profileRes;
	  }
	 
	
	@PostMapping(value="/getVoucherDetailByBoucherCode")
	public @ResponseBody String getVoucherDetailByBoucherCode(HttpServletRequest request, ModelMap model,Locale locale,HttpSession session
			,ErupiVoucherMaster erupiVoucherMaster) {
		//return masterService.getVoucherDescriptionByVoucherCode(tokengeneration.getToken(),erupiVoucherMaster);
		String profileRes=null;
		
		try {
			String json = EncryptionDecriptionUtil.convertToJson(erupiVoucherMaster);

			EncriptResponse jsonObject=EncryptionDecriptionUtil.encriptResponse(json, applicationConstantConfig.apiSignaturePublicPath);

			String encriptResponse =  masterService.getVoucherDescriptionByVoucherCode(tokengeneration.getToken(), jsonObject);

   
			EncriptResponse userReqEnc =EncryptionDecriptionUtil.convertFromJson(encriptResponse, EncriptResponse.class);

			profileRes =  EncryptionDecriptionUtil.decriptResponse(userReqEnc.getEncriptData(), userReqEnc.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
   
	return profileRes;
	}
	
	@PostMapping(value="/getBankDetailByAccountNo")
	public @ResponseBody String getBankDetailByAccountNo(HttpServletRequest request, ModelMap model,Locale locale,HttpSession session
			,ErupiLinkBankAccount erupiLinkBankAccount) {
		//return masterService.getBankDetailByAccountNo(tokengeneration.getToken(),erupiLinkBankAccount);
		String profileRes=null;
		
		try {
			String json = EncryptionDecriptionUtil.convertToJson(erupiLinkBankAccount);

			EncriptResponse jsonObject=EncryptionDecriptionUtil.encriptResponse(json, applicationConstantConfig.apiSignaturePublicPath);

			String encriptResponse =  masterService.getBankDetailByAccountNo(tokengeneration.getToken(), jsonObject);

   
			EncriptResponse userReqEnc =EncryptionDecriptionUtil.convertFromJson(encriptResponse, EncriptResponse.class);

			profileRes =  EncryptionDecriptionUtil.decriptResponse(userReqEnc.getEncriptData(), userReqEnc.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
   
	return profileRes;
	}
	
	@PostMapping(value="/getmccMasterListByPurposeCode")
	public @ResponseBody String getmccMasterListByPurposeCode(HttpServletRequest request, ModelMap model,Locale locale,HttpSession session
			,MccMaster mccMaster) {
		//return masterService.getmccMasterListByPurposeCode(tokengeneration.getToken(),mccMaster);
		String profileRes=null;
		
		try {
			String json = EncryptionDecriptionUtil.convertToJson(mccMaster);

			EncriptResponse jsonObject=EncryptionDecriptionUtil.encriptResponse(json, applicationConstantConfig.apiSignaturePublicPath);

			String encriptResponse =  masterService.getmccMasterListByPurposeCode(tokengeneration.getToken(), jsonObject);

   
			EncriptResponse userReqEnc =EncryptionDecriptionUtil.convertFromJson(encriptResponse, EncriptResponse.class);

			profileRes =  EncryptionDecriptionUtil.decriptResponse(userReqEnc.getEncriptData(), userReqEnc.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
   
	return profileRes;
	}
	
	@PostMapping(value="/getmccMasterDetailsByPurposeCodeAndMcc")
	public @ResponseBody String getmccMasterDetailsByPurposeCodeAndMcc(HttpServletRequest request, ModelMap model,Locale locale,HttpSession session
			,MccMaster mccMaster) {
		//return masterService.getmccMasterDetailsByPurposeCodeAndMcc(tokengeneration.getToken(),mccMaster);
		String profileRes=null;
		
		try {
			String json = EncryptionDecriptionUtil.convertToJson(mccMaster);

			EncriptResponse jsonObject=EncryptionDecriptionUtil.encriptResponse(json, applicationConstantConfig.apiSignaturePublicPath);

			String encriptResponse =  masterService.getmccMasterDetailsByPurposeCodeAndMcc(tokengeneration.getToken(), jsonObject);

   
			EncriptResponse userReqEnc =EncryptionDecriptionUtil.convertFromJson(encriptResponse, EncriptResponse.class);

			profileRes =  EncryptionDecriptionUtil.decriptResponse(userReqEnc.getEncriptData(), userReqEnc.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
   
	return profileRes;
	}
	
	@GetMapping(value="/getEmployeeType")
	public @ResponseBody String getEmployeeType(HttpServletRequest request, ModelMap model,Locale locale,
			HttpSession session,EmployeeMassterRequest employeeMassterRequest) {
		logger.info("getEmployeeType");	
		String token = (String) session.getAttribute("cotodel");
		//return masterService.getEmployeeType(tokengeneration.getToken(),employeeMassterRequest);
		String profileRes=null;
		
		try {
			String json = EncryptionDecriptionUtil.convertToJson(employeeMassterRequest);

			EncriptResponse jsonObject=EncryptionDecriptionUtil.encriptResponse(json, applicationConstantConfig.apiSignaturePublicPath);

			String encriptResponse =  masterService.getEmployeeType(tokengeneration.getToken(), jsonObject);

   
			EncriptResponse userReqEnc =EncryptionDecriptionUtil.convertFromJson(encriptResponse, EncriptResponse.class);

			profileRes =  EncryptionDecriptionUtil.decriptResponse(userReqEnc.getEncriptData(), userReqEnc.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
   
	return profileRes;
	}

	@GetMapping(value="/getEmployeeMasterList")
	public @ResponseBody String getEmployeeListMaster(HttpServletRequest request, ModelMap model,Locale locale,
			HttpSession session,EmployeeMassterRequest employeeMassterRequest) {
		logger.info("getEmployeeType");	
		String token = (String) session.getAttribute("cotodel");
		//return masterService.getEmployeeListMaster(tokengeneration.getToken(),employeeMassterRequest);
		String profileRes=null;
		
		try {
			String json = EncryptionDecriptionUtil.convertToJson(employeeMassterRequest);

			EncriptResponse jsonObject=EncryptionDecriptionUtil.encriptResponse(json, applicationConstantConfig.apiSignaturePublicPath);

			String encriptResponse =  masterService.getEmployeeListMaster(tokengeneration.getToken(), jsonObject);

   
			EncriptResponse userReqEnc =EncryptionDecriptionUtil.convertFromJson(encriptResponse, EncriptResponse.class);

			profileRes =  EncryptionDecriptionUtil.decriptResponse(userReqEnc.getEncriptData(), userReqEnc.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
   
	return profileRes;
	}
	
	@GetMapping(value="/getofficeLocationMaster")
	public @ResponseBody String getofficeLocationMaster(HttpServletRequest request, ModelMap model,Locale locale,
			HttpSession session,EmployeeMassterRequest employeeMassterRequest) {
		logger.info("getofficeLocationMaster");	
		String token = (String) session.getAttribute("cotodel");
		//return masterService.getofficeLocationMaster(tokengeneration.getToken(),employeeMassterRequest);
		String profileRes=null;
		
		try {
			String json = EncryptionDecriptionUtil.convertToJson(employeeMassterRequest);

			EncriptResponse jsonObject=EncryptionDecriptionUtil.encriptResponse(json, applicationConstantConfig.apiSignaturePublicPath);

			String encriptResponse =  masterService.getofficeLocationMaster(tokengeneration.getToken(), jsonObject);

   
			EncriptResponse userReqEnc =EncryptionDecriptionUtil.convertFromJson(encriptResponse, EncriptResponse.class);

			profileRes =  EncryptionDecriptionUtil.decriptResponse(userReqEnc.getEncriptData(), userReqEnc.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
   
	return profileRes;
	}
	
	@GetMapping(value="/getVoucherListWithIcon")
	public @ResponseBody String getVoucherListWithIcon(HttpServletRequest request, ModelMap model,Locale locale,
			HttpSession session,EmployeeMassterRequest employeeMassterRequest) {
		logger.info("getofficeLocationMaster");	
		String token = (String) session.getAttribute("cotodel");
		//return masterService.getVoucherListWithIcon(tokengeneration.getToken(),employeeMassterRequest);
		
		String profileRes=null;
		
		try {
			String json = EncryptionDecriptionUtil.convertToJson(employeeMassterRequest);

			EncriptResponse jsonObject=EncryptionDecriptionUtil.encriptResponse(json, applicationConstantConfig.apiSignaturePublicPath);

			String encriptResponse =  masterService.getVoucherListWithIcon(tokengeneration.getToken(), jsonObject);

   
			EncriptResponse userReqEnc =EncryptionDecriptionUtil.convertFromJson(encriptResponse, EncriptResponse.class);

			profileRes =  EncryptionDecriptionUtil.decriptResponse(userReqEnc.getEncriptData(), userReqEnc.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
   
	return profileRes;
	}
	
	@GetMapping(value="/getPurposeListByVoucherCode")
	public @ResponseBody String getPurposeListByVoucherCode(HttpServletRequest request, ModelMap model,Locale locale,
			HttpSession session,EmployeeMassterRequest employeeMassterRequest) {
		logger.info("getofficeLocationMaster");	
		String token = (String) session.getAttribute("cotodel");
		//return masterService.getPurposeListByVoucherCode(tokengeneration.getToken(),employeeMassterRequest);
		String profileRes=null;
		
		try {
			String json = EncryptionDecriptionUtil.convertToJson(employeeMassterRequest);

			EncriptResponse jsonObject=EncryptionDecriptionUtil.encriptResponse(json, applicationConstantConfig.apiSignaturePublicPath);

			String encriptResponse =  masterService.getPurposeListByVoucherCode(tokengeneration.getToken(), jsonObject);

   
			EncriptResponse userReqEnc =EncryptionDecriptionUtil.convertFromJson(encriptResponse, EncriptResponse.class);

			profileRes =  EncryptionDecriptionUtil.decriptResponse(userReqEnc.getEncriptData(), userReqEnc.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
   
	return profileRes;
	}
	
	@PostMapping(value="/voucherCreateBankList")
	public @ResponseBody String getBankListWithVocher(HttpServletRequest request, ModelMap model,Locale locale,
			HttpSession session,EmployeeMassterRequest employeeMassterRequest) {
		logger.info("voucherCreateBankList");	
		String token = (String) session.getAttribute("cotodel");
		//return masterService.getBankListWithVocher(tokengeneration.getToken(),employeeMassterRequest);
		
		String profileRes=null;
		
		try {
			String json = EncryptionDecriptionUtil.convertToJson(employeeMassterRequest);

			EncriptResponse jsonObject=EncryptionDecriptionUtil.encriptResponse(json, applicationConstantConfig.apiSignaturePublicPath);

			String encriptResponse =  masterService.getBankListWithVocher(tokengeneration.getToken(), jsonObject);

   
			EncriptResponse userReqEnc =EncryptionDecriptionUtil.convertFromJson(encriptResponse, EncriptResponse.class);

			profileRes =  EncryptionDecriptionUtil.decriptResponse(userReqEnc.getEncriptData(), userReqEnc.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
   
	return profileRes;
	}
	
	@PostMapping(value="/voucherCreateSummaryDetailByAccount")
	public @ResponseBody String voucherCreateSummaryDetailByAccount(HttpServletRequest request, ModelMap model,Locale locale,
			HttpSession session,EmployeeMassterRequest employeeMassterRequest) {
		logger.info("voucherCreateSummaryDetailByAccount");	
		String token = (String) session.getAttribute("cotodel");
		
		String profileRes=null;
		
		//return masterService.voucherCreateSummaryDetailByAccount(tokengeneration.getToken(),employeeMassterRequest);
		
		try {
			String json = EncryptionDecriptionUtil.convertToJson(employeeMassterRequest);

			EncriptResponse jsonObject=EncryptionDecriptionUtil.encriptResponse(json, applicationConstantConfig.apiSignaturePublicPath);

			String encriptResponse =  masterService.voucherCreateSummaryDetailByAccount(tokengeneration.getToken(),jsonObject);
   
			EncriptResponse userReqEnc =EncryptionDecriptionUtil.convertFromJson(encriptResponse, EncriptResponse.class);

			profileRes =  EncryptionDecriptionUtil.decriptResponse(userReqEnc.getEncriptData(), userReqEnc.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
       
		return profileRes;
	}
	
	@GetMapping(value="/getDepartmentMaster")
	public @ResponseBody String getDepartmentMaster(HttpServletRequest request, ModelMap model,Locale locale,HttpSession session
			,DepartmentMaster departmentMaster) {
		logger.info("getDepartmentMaster");	
		//return departmentMasterService.getDepartmentMaster(tokengeneration.getToken(),departmentMaster);
		
		String profileRes=null;
		
		try {
			String json = EncryptionDecriptionUtil.convertToJson(departmentMaster);

			EncriptResponse jsonObject=EncryptionDecriptionUtil.encriptResponse(json, applicationConstantConfig.apiSignaturePublicPath);

			String encriptResponse =  departmentMasterService.getDepartmentMaster(tokengeneration.getToken(), jsonObject);

   
			EncriptResponse userReqEnc =EncryptionDecriptionUtil.convertFromJson(encriptResponse, EncriptResponse.class);

			profileRes =  EncryptionDecriptionUtil.decriptResponse(userReqEnc.getEncriptData(), userReqEnc.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
   
	return profileRes;
	}
	
}
