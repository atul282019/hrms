package com.cotodel.hrms.web.controller;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.cotodel.hrms.web.properties.ApplicationConstantConfig;
import com.cotodel.hrms.web.response.ErupiVoucherCreateDetails;
import com.cotodel.hrms.web.response.ExistingUserVoucherCreationRequest;
import com.cotodel.hrms.web.service.ErupiVoucherCreateDetailsService;
import com.cotodel.hrms.web.service.Impl.TokenGenerationImpl;

@RestController
@CrossOrigin
public class ErupiSingleVoucherCreationController  extends CotoDelBaseController{

	private static final Logger logger = LoggerFactory.getLogger(ErupiSingleVoucherCreationController.class);

	@Autowired
	public ApplicationConstantConfig applicationConstantConfig;
	
	@Autowired
	TokenGenerationImpl tokengeneration;
	
	@Autowired
	ErupiVoucherCreateDetailsService erupiVoucherCreateDetailsService;
	
	@PostMapping(value="/createSingleVoucher")
	public @ResponseBody String createSingleVoucher(HttpServletRequest request, ModelMap model,Locale locale,HttpSession session,
			ErupiVoucherCreateDetails erupiVoucherCreateDetails) {
		String profileRes=null;
		
		profileRes = erupiVoucherCreateDetailsService.createSingleVoucher(tokengeneration.getToken(),erupiVoucherCreateDetails);
		
		return profileRes;
	}
	
	@PostMapping(value = "/getIssueVoucherList")
	public @ResponseBody String getIssueVoucherList(HttpServletRequest request, ModelMap model, Locale locale,
			HttpSession session, ErupiVoucherCreateDetails erupiVoucherCreateDetails) {
		String profileRes = null;
		profileRes = erupiVoucherCreateDetailsService.getIssueVoucherList(tokengeneration.getToken(), erupiVoucherCreateDetails);

		return profileRes;
	}
	
	@PostMapping(value = "/getVoucherSummaryList")
	public @ResponseBody String getVoucherSummaryList(HttpServletRequest request, ModelMap model, Locale locale,
			HttpSession session, ErupiVoucherCreateDetails erupiVoucherCreateDetails) {
		String profileRes = null;
		profileRes = erupiVoucherCreateDetailsService.getVoucherSummaryList(tokengeneration.getToken(),	erupiVoucherCreateDetails);

		return profileRes;
	}
	
	@PostMapping(value = "/getPrimaryBankDetailsByOrgId")
	public @ResponseBody String getPrimaryBankDetailsByOrgId(HttpServletRequest request, ModelMap model, Locale locale,
			HttpSession session, ErupiVoucherCreateDetails erupiVoucherCreateDetails) {
		String profileRes = null;
		profileRes = erupiVoucherCreateDetailsService.getPrimaryBankDetailsByOrgId(tokengeneration.getToken(),	erupiVoucherCreateDetails);

		return profileRes;
	}
	
	@PostMapping(value = "/revokeCreatedVoucher")
	public @ResponseBody String revokeCreatedVoucher(HttpServletRequest request, ModelMap model, Locale locale,
			HttpSession session, ErupiVoucherCreateDetails erupiVoucherCreateDetails) {
		String profileRes = null;
		profileRes = erupiVoucherCreateDetailsService.revokeCreatedVoucher(tokengeneration.getToken(),	erupiVoucherCreateDetails);

		return profileRes;
	}
	
	@PostMapping(value = "/erupiVoucheSmsSend")
	public @ResponseBody String erupiVoucheSmsSend(HttpServletRequest request, ModelMap model, Locale locale,
			HttpSession session, ErupiVoucherCreateDetails erupiVoucherCreateDetails) {
		String profileRes = null;
		profileRes = erupiVoucherCreateDetailsService.erupiVoucheSmsSend(tokengeneration.getToken(),	erupiVoucherCreateDetails);

		return profileRes;
	}
	
	@PostMapping(value="/geterupiVoucherOldList")
	public @ResponseBody String geterupiVoucherOldList(HttpServletRequest request, ModelMap model,Locale locale,HttpSession session,
			ErupiVoucherCreateDetails erupiVoucherCreateDetails) {
		String profileRes=null;
		
		profileRes = erupiVoucherCreateDetailsService.geterupiVoucherOldList(tokengeneration.getToken(),erupiVoucherCreateDetails);
		
		return profileRes;
	}
	
	@PostMapping(value="/exitingUserVoucherCreation")
	public @ResponseBody String exitingUserVoucherCreation(HttpServletRequest request, ModelMap model,Locale locale,HttpSession session,
			ExistingUserVoucherCreationRequest existingUserVoucherCreationRequest) {
		String profileRes=null;
		
		profileRes = erupiVoucherCreateDetailsService.exitingUserVoucherCreation(tokengeneration.getToken(),existingUserVoucherCreationRequest);
		
		return profileRes;
	}
}
