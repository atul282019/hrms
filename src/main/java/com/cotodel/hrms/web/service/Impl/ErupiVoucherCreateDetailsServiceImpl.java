package com.cotodel.hrms.web.service.Impl;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cotodel.hrms.web.controller.ErupiSingleVoucherCreationController;
import com.cotodel.hrms.web.function.common.CommonUtils;
import com.cotodel.hrms.web.properties.ApplicationConstantConfig;
import com.cotodel.hrms.web.response.BulkVoucherRequest;
import com.cotodel.hrms.web.response.ErupiBulkVoucherCreateRequest;
import com.cotodel.hrms.web.response.ErupiVoucherCreateDetails;
import com.cotodel.hrms.web.response.ExistingUserVoucherCreationRequest;
import com.cotodel.hrms.web.service.ErupiVoucherCreateDetailsService;
import com.cotodel.hrms.web.util.CommonUtility;
import com.cotodel.hrms.web.util.MessageConstant;

@Service
public class ErupiVoucherCreateDetailsServiceImpl implements ErupiVoucherCreateDetailsService{
	
	private static final Logger logger = LoggerFactory.getLogger(ErupiVoucherCreateDetailsServiceImpl.class);
	
	@Autowired
	public ApplicationConstantConfig applicationConstantConfig;

	@Override
	public String createSingleVoucher(String token, ErupiVoucherCreateDetails erupiVoucherCreateDetails) {
		return CommonUtility.userRequest(token,createVoucherRequestJson(erupiVoucherCreateDetails), applicationConstantConfig.employerServiceBaseUrl+CommonUtils.createSingleVoucher);

	}
	
	public  String createVoucherRequestJson(ErupiVoucherCreateDetails erupiVoucherCreateDetails){
		JSONObject data= new JSONObject();

		JSONObject voucherId= new JSONObject();
		voucherId.put("id", erupiVoucherCreateDetails.getVoucherId());
		
		data.put("voucherId", voucherId);
		
		data.put("name", erupiVoucherCreateDetails.getName());
		data.put("mobile", erupiVoucherCreateDetails.getMobile());
		data.put("amount", erupiVoucherCreateDetails.getAmount());
		data.put("startDate", erupiVoucherCreateDetails.getStartDate());
		data.put("expDate",erupiVoucherCreateDetails.getExpDate() );
		data.put("purposeCode", erupiVoucherCreateDetails.getPurposeCode());
		data.put("consent", erupiVoucherCreateDetails.getConsent());
		data.put("otpValidationStatus", "");
		data.put("creationDate", "");
		data.put("createdby", erupiVoucherCreateDetails.getCreatedby());
		data.put("accountId", "");
		data.put("orgId", erupiVoucherCreateDetails.getOrgId());
		data.put("accountNumber", erupiVoucherCreateDetails.getAccountNumber());
		data.put("voucherCode", erupiVoucherCreateDetails.getVoucherCode());
		data.put("voucherType", erupiVoucherCreateDetails.getVoucherType());
		data.put("voucherDesc", erupiVoucherCreateDetails.getVoucherDesc());
		
		
		JSONObject entrymodeIdPk= new JSONObject();
		entrymodeIdPk.put("id", "");
		data.put("entrymodeIdPk", entrymodeIdPk);
		
		data.put("response", erupiVoucherCreateDetails.getResponse());
		data.put("merchanttxnid", erupiVoucherCreateDetails.getMerchanttxnid());
		data.put("creationmode", "");
		//data.put("bulktblId", erupiVoucherCreateDetails.getBeneficiaryID());
		data.put("redemtionType", "SINGLE");
		data.put("mcc", erupiVoucherCreateDetails.getMcc());
		data.put("merchantId", erupiVoucherCreateDetails.getMerchantId());
		data.put("subMerchantId", erupiVoucherCreateDetails.getSubMerchantId());
		data.put("extra1", "");
		data.put("extra2","");
		data.put("extra3", "");
		data.put("beneficiaryID",erupiVoucherCreateDetails.getMobile());
		data.put("payerVA", erupiVoucherCreateDetails.getPayerVA());
		data.put("bankcode", erupiVoucherCreateDetails.getBankCode());
		data.put("type", "CREATE");
		
		logger.info(data.toString());
		return data.toString();
	}

	@Override
	public String getIssueVoucherList(String token, ErupiVoucherCreateDetails erupiVoucherCreateDetails) {
		return CommonUtility.userRequest(token,MessageConstant.gson.toJson(erupiVoucherCreateDetails), applicationConstantConfig.employerServiceBaseUrl+CommonUtils.getIssuseVoucherList);
	}

	@Override
	public String getVoucherSummaryList(String token, ErupiVoucherCreateDetails erupiVoucherCreateDetails) {
		return CommonUtility.userRequest(token,MessageConstant.gson.toJson(erupiVoucherCreateDetails), applicationConstantConfig.employerServiceBaseUrl+CommonUtils.getvoucherSummaryList);

	}

	@Override
	public String getPrimaryBankDetailsByOrgId(String token, ErupiVoucherCreateDetails erupiVoucherCreateDetails) {
		return CommonUtility.userRequest(token,MessageConstant.gson.toJson(erupiVoucherCreateDetails), applicationConstantConfig.employerServiceBaseUrl+CommonUtils.getPrimaryBankDetailByOrgId);
	}

	@Override
	public String revokeCreatedVoucher(String token, ErupiVoucherCreateDetails erupiVoucherCreateDetails) {
		return CommonUtility.userRequest(token,MessageConstant.gson.toJson(erupiVoucherCreateDetails), applicationConstantConfig.employerServiceBaseUrl+CommonUtils.revokeCreatedVoucherOneByOne);

	}

	@Override
	public String erupiVoucheSmsSend(String token, ErupiVoucherCreateDetails erupiVoucherCreateDetails) {
		
		return CommonUtility.userRequest(token,MessageConstant.gson.toJson(erupiVoucherCreateDetails), applicationConstantConfig.employerServiceBaseUrl+CommonUtils.resendErupiVoucheSmsSend);

	}

	@Override
	public String issueBulkVoucher(String token, ErupiBulkVoucherCreateRequest erupiBulkVoucherCreateRequest) {
		return CommonUtility.userRequest(token,createBulkVoucherRequestJson(erupiBulkVoucherCreateRequest), applicationConstantConfig.employerServiceBaseUrl+CommonUtils.createBulkVoucher);

	}
	

	public  String createBulkVoucherRequestJson(ErupiBulkVoucherCreateRequest erupiVoucherCreateDetails){
		JSONObject data= new JSONObject();

		JSONObject voucherId= new JSONObject();
		voucherId.put("id", erupiVoucherCreateDetails.getVoucherId());
		
		data.put("voucherId", voucherId);
		
		data.put("name", erupiVoucherCreateDetails.getName());
		data.put("mobile", erupiVoucherCreateDetails.getMobile());
		data.put("amount", erupiVoucherCreateDetails.getAmount());
		data.put("startDate", erupiVoucherCreateDetails.getStartDate());
		data.put("expDate",erupiVoucherCreateDetails.getExpDate() );
		data.put("purposeCode", erupiVoucherCreateDetails.getPurposeCode());
		data.put("consent", erupiVoucherCreateDetails.getConsent());
		data.put("otpValidationStatus", "");
		data.put("creationDate", "");
		data.put("createdby", erupiVoucherCreateDetails.getCreatedby());
		data.put("accountId", "");
		data.put("orgId", erupiVoucherCreateDetails.getOrgId());
		data.put("accountNumber", erupiVoucherCreateDetails.getAccountNumber());
		data.put("voucherCode", erupiVoucherCreateDetails.getVoucherCode());
		data.put("voucherType", erupiVoucherCreateDetails.getVoucherType());
		data.put("voucherDesc", erupiVoucherCreateDetails.getVoucherDesc());
		
		
		JSONObject entrymodeIdPk= new JSONObject();
		entrymodeIdPk.put("id", "");
		data.put("entrymodeIdPk", entrymodeIdPk);
		
		data.put("response", erupiVoucherCreateDetails.getResponse());
		data.put("merchanttxnid", erupiVoucherCreateDetails.getMerchanttxnid());
		data.put("creationmode", "");
		//data.put("bulktblId", erupiVoucherCreateDetails.getBeneficiaryID());
		data.put("redemtionType", "SINGLE");
		data.put("mcc", erupiVoucherCreateDetails.getMcc());
		data.put("merchantId", erupiVoucherCreateDetails.getMerchantId());
		data.put("subMerchantId", erupiVoucherCreateDetails.getSubMerchantId());
		data.put("extra1", "");
		data.put("extra2","");
		data.put("extra3", "");
		data.put("beneficiaryID",erupiVoucherCreateDetails.getMobile());
		data.put("payerVA", erupiVoucherCreateDetails.getPayerVA());
		data.put("bankcode", erupiVoucherCreateDetails.getBankCode());
		data.put("type", "CREATE");
		data.put("arrayofid", erupiVoucherCreateDetails.getArrayofid());
		
		logger.info(data.toString());
		return data.toString();
	}

	@Override
	public String beneficiaryDeleteFromVoucherList(String token, BulkVoucherRequest bulkVoucherRequest) {
		return CommonUtility.userRequest(token,MessageConstant.gson.toJson(bulkVoucherRequest), applicationConstantConfig.employerServiceBaseUrl+CommonUtils.beneficiaryDeleteFromVoucherList);

	}

	@Override
	public String geterupiVoucherOldList(String token, ErupiVoucherCreateDetails erupiVoucherCreateDetails) {
		return CommonUtility.userRequest(token,MessageConstant.gson.toJson(erupiVoucherCreateDetails), applicationConstantConfig.employerServiceBaseUrl+CommonUtils.getVoucherUserList);
	}

	@Override
	public String exitingUserVoucherCreation(String token,
			ExistingUserVoucherCreationRequest existingUserVoucherCreationRequest) {
		return CommonUtility.userRequest(token,createExitingUserVoucherRequestJson(existingUserVoucherCreationRequest), applicationConstantConfig.employerServiceBaseUrl+CommonUtils.exitingUserVoucherCreation);
	}
	public  String createExitingUserVoucherRequestJson(ExistingUserVoucherCreationRequest existingUserVoucherCreationRequest){
		JSONObject data= new JSONObject();

		JSONObject voucherId= new JSONObject();
		voucherId.put("id", existingUserVoucherCreationRequest.getVoucherId());
		
		data.put("voucherId", voucherId);
		
		data.put("name", existingUserVoucherCreationRequest.getName());
		//data.put("mobile", existingUserVoucherCreationRequest);
		data.put("amount", existingUserVoucherCreationRequest.getAmount());
		data.put("startDate", existingUserVoucherCreationRequest.getStartDate());
		data.put("expDate",existingUserVoucherCreationRequest.getExpDate() );
		data.put("purposeCode", existingUserVoucherCreationRequest.getPurposeCode());
		data.put("consent", existingUserVoucherCreationRequest.getConsent());
		data.put("otpValidationStatus", "");
		data.put("creationDate", "");
		data.put("createdby", existingUserVoucherCreationRequest.getCreatedby());
		data.put("accountId", "");
		data.put("orgId", existingUserVoucherCreationRequest.getOrgId());
		data.put("accountNumber", existingUserVoucherCreationRequest.getAccountId());
		data.put("voucherCode", existingUserVoucherCreationRequest.getVoucherCode());
		data.put("voucherType", existingUserVoucherCreationRequest.getVoucherType());
		data.put("voucherDesc", existingUserVoucherCreationRequest.getVoucherDesc());
		
		
		JSONObject entrymodeIdPk= new JSONObject();
		entrymodeIdPk.put("id", "");
		data.put("entrymodeIdPk", entrymodeIdPk);
	
		data.put("creationmode", "");
		//data.put("bulktblId", erupiVoucherCreateDetails.getBeneficiaryID());
		data.put("redemtionType", "SINGLE");
		data.put("mcc", existingUserVoucherCreationRequest.getMcc());
		data.put("merchantId", existingUserVoucherCreationRequest.getMerchantId());
		data.put("subMerchantId", existingUserVoucherCreationRequest.getSubMerchantId());
		data.put("extra1", "");
		data.put("extra2","");
		data.put("extra3", "");
		//data.put("beneficiaryID",existingUserVoucherCreationRequest.get);
		data.put("payerVA", existingUserVoucherCreationRequest.getPayerVA());
		data.put("bankcode", existingUserVoucherCreationRequest.getBankCode());
		data.put("type", "CREATE");
		data.put("arrayofnamemobile", existingUserVoucherCreationRequest.getArrayofnamemobile());
		
		logger.info(data.toString());
		return data.toString();
	}


}
