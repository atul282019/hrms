package com.cotodel.hrms.web.service.Impl;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cotodel.hrms.web.function.common.CommonUtils;
import com.cotodel.hrms.web.properties.ApplicationConstantConfig;
import com.cotodel.hrms.web.response.ErupiBulkVoucherCreateRequest;
import com.cotodel.hrms.web.response.ErupiVoucherCreateDetails;
import com.cotodel.hrms.web.response.ExistingUserVoucherCreationRequest;
import com.cotodel.hrms.web.response.RevokeVoucher;
import com.cotodel.hrms.web.response.SingleVoucherCreationRequest;
import com.cotodel.hrms.web.service.ErupiVoucherCreateDetailsService;
import com.cotodel.hrms.web.util.CommonUtility;
import com.cotodel.hrms.web.util.EncriptResponse;
import com.cotodel.hrms.web.util.MessageConstant;

@Service
public class ErupiVoucherCreateDetailsServiceImpl implements ErupiVoucherCreateDetailsService{
	
	private static final Logger logger = LoggerFactory.getLogger(ErupiVoucherCreateDetailsServiceImpl.class);
	
	@Autowired
	public ApplicationConstantConfig applicationConstantConfig;

	@Override
	public String createSingleVoucher(String token, ErupiVoucherCreateDetails erupiVoucherCreateDetails) {
		return CommonUtility.bulkUserRequest(token,createVoucherRequestJson(erupiVoucherCreateDetails), 
				applicationConstantConfig.employerServiceBaseUrl+CommonUtils.createSingleVoucher,applicationConstantConfig.apiSignaturePublicPath,
				applicationConstantConfig.apiSignaturePrivatePath);
		
	}
	
	public  String createVoucherRequestJson(ErupiVoucherCreateDetails erupiVoucherCreateDetails){
		JSONObject data= new JSONObject();

		JSONObject voucherId= new JSONObject();
		voucherId.put("id", erupiVoucherCreateDetails.getVoucherId());
		
		data.put("voucherId", voucherId);
		data.put("requestId", erupiVoucherCreateDetails.getRequestId());
		
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
		data.put("response", "");
		data.put("responseApi","" );
		data.put("merchanttxnid", erupiVoucherCreateDetails.getMerchanttxnid());
		data.put("creationmode", "");
		data.put("bulktblId","" );
		data.put("redemtionType", erupiVoucherCreateDetails.getRedemtionType());
		data.put("mcc", erupiVoucherCreateDetails.getMcc());
		data.put("extra1", "");
		data.put("extra2","");
		data.put("extra3", "");

		data.put("beneficiaryID",erupiVoucherCreateDetails.getMobile());
		data.put("payerVA", erupiVoucherCreateDetails.getPayerVA());
		data.put("bankcode", erupiVoucherCreateDetails.getBankcode());
		data.put("type", "");
		data.put("voucherCode", erupiVoucherCreateDetails.getVoucherCode());
		data.put("voucherType", erupiVoucherCreateDetails.getVoucherType());
		data.put("voucherDesc", erupiVoucherCreateDetails.getVoucherDesc());
		data.put("merchantId", erupiVoucherCreateDetails.getMerchantId());
		data.put("subMerchantId", erupiVoucherCreateDetails.getSubMerchantId());
		
		data.put("mandateType","01");
		data.put("payeeVPA","");
		data.put("clientKey",erupiVoucherCreateDetails.getClientKey());
		data.put("hash",erupiVoucherCreateDetails.getHash());
		
		data.put("validity",erupiVoucherCreateDetails.getValidity());
		
		logger.info(data.toString());
		return data.toString();
	}
	
	@Override
	public String erupiVoucherCreateListRedeem(String token, EncriptResponse erupiVoucherCreateDetails) {
		return CommonUtility.userRequest(token,MessageConstant.gson.toJson(erupiVoucherCreateDetails), applicationConstantConfig.employerServiceBaseUrl+CommonUtils.erupiVoucherCreateListRedeem);
	}
	@Override
	public String getIssueVoucherList(String token, EncriptResponse erupiVoucherCreateDetails) {
		return CommonUtility.userRequest(token,MessageConstant.gson.toJson(erupiVoucherCreateDetails), applicationConstantConfig.employerServiceBaseUrl+CommonUtils.getIssuseVoucherList);
	}

	@Override
	public String getVoucherSummaryList(String token, EncriptResponse erupiVoucherCreateDetails) {
		return CommonUtility.userRequest(token,MessageConstant.gson.toJson(erupiVoucherCreateDetails), applicationConstantConfig.employerServiceBaseUrl+CommonUtils.getvoucherSummaryList);

	}

	@Override
	public String getPrimaryBankDetailsByOrgId(String token, EncriptResponse erupiVoucherCreateDetails) {
		return CommonUtility.userRequest(token,MessageConstant.gson.toJson(erupiVoucherCreateDetails), applicationConstantConfig.employerServiceBaseUrl+CommonUtils.getPrimaryBankDetailByOrgId);
	}

	@Override
	public String erupiVoucheSmsSend(String token, EncriptResponse erupiVoucherStatusSmsRequest) {
		
		return CommonUtility.userRequest(token,MessageConstant.gson.toJson(erupiVoucherStatusSmsRequest), applicationConstantConfig.employerServiceBaseUrl+CommonUtils.resendErupiVoucheSmsSend);

	}

	@Override
	public String issueBulkVoucher(String token, ErupiBulkVoucherCreateRequest erupiBulkVoucherCreateRequest) {
		return CommonUtility.bulkUserRequest(token,createBulkVoucherRequestJson(erupiBulkVoucherCreateRequest), 
				applicationConstantConfig.employerServiceBaseUrl+CommonUtils.createBulkVoucher,
				applicationConstantConfig.apiSignaturePublicPath,
				applicationConstantConfig.apiSignaturePrivatePath);

	}
	

	public  String createBulkVoucherRequestJson(ErupiBulkVoucherCreateRequest erupiVoucherCreateDetails){
		JSONObject data= new JSONObject();
		
		data.put("subMerchantId", erupiVoucherCreateDetails.getSubMerchantId());
		data.put("mcc", erupiVoucherCreateDetails.getMcc());
		data.put("merchantId", erupiVoucherCreateDetails.getMerchantId());
		data.put("type", "CREATE");
		data.put("createdby", erupiVoucherCreateDetails.getCreatedby());
	    data.put("redemtionType", "SINGLE");
		data.put("payerVA", erupiVoucherCreateDetails.getPayerVA());
		data.put("bankcode", erupiVoucherCreateDetails.getBankcode());
		data.put("purposeCode", erupiVoucherCreateDetails.getPurposeCode());
		
		data.put("voucherCode", erupiVoucherCreateDetails.getVoucherCode());
		data.put("voucherType", erupiVoucherCreateDetails.getVoucherType());
		data.put("voucherDesc", erupiVoucherCreateDetails.getVoucherDesc());
		data.put("creationDate", "");
		data.put("accountId", "");
		data.put("orgId", erupiVoucherCreateDetails.getOrgId());
		data.put("accountNumber", erupiVoucherCreateDetails.getAccountNumber());
		data.put("response", erupiVoucherCreateDetails.getResponse());
		data.put("responseApi","");
		data.put("mandateType","01");
		data.put("merchanttxnid", erupiVoucherCreateDetails.getMerchanttxnid());
		data.put("creationmode", "");
		data.put("bulktblId", erupiVoucherCreateDetails.getBeneficiaryID());
		data.put("beneficiaryID",erupiVoucherCreateDetails.getMobile());
			
		data.put("arrayofid", erupiVoucherCreateDetails.getArrayofid());
		data.put("clientKey",erupiVoucherCreateDetails.getClientKey());
		data.put("hash",erupiVoucherCreateDetails.getHash());
				
		data.put("consent", erupiVoucherCreateDetails.getConsent());
		data.put("otpValidationStatus", "");
		
		data.put("extra1", "");
		data.put("extra2","");
		data.put("extra3", "");
		logger.info(data.toString());
		return data.toString();
	}

	@Override
	public String beneficiaryDeleteFromVoucherList(String token, EncriptResponse bulkVoucherRequest) {
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
		data.put("type", "");
		data.put("arrayofnamemobile", existingUserVoucherCreationRequest.getArrayofnamemobile());
		
		logger.info(data.toString());
		return data.toString();
	}

	@Override
	public String voucherUserSearch(String token, EncriptResponse roleAccessRequest) {
		return CommonUtility.userRequest(token,MessageConstant.gson.toJson(roleAccessRequest), applicationConstantConfig.employerServiceBaseUrl+CommonUtils.voucherUserSearch);
	}

	@Override
	public String getTotalVoucherCount(String token, EncriptResponse erupiVoucherCreateDetails) {
		return CommonUtility.userRequest(token,MessageConstant.gson.toJson(erupiVoucherCreateDetails), applicationConstantConfig.employerServiceBaseUrl+CommonUtils.getTotalVoucherCount);
	}

	@Override
	public String revokeCreatedVoucherSingle(String token, EncriptResponse json) {
		return CommonUtility.userRequest(token,MessageConstant.gson.toJson(json), applicationConstantConfig.employerServiceBaseUrl+CommonUtils.revokeCreatedVoucherOneByOne);

	}

	@Override
	public String createSingleVoucherWithMultipleRequest(String token, EncriptResponse jsonObject) {
		return CommonUtility.userRequest(token,MessageConstant.gson.toJson(jsonObject), applicationConstantConfig.employerServiceBaseUrl+CommonUtils.createSingleVoucher);
	}



}
