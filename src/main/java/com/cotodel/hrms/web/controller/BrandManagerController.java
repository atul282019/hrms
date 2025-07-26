package com.cotodel.hrms.web.controller;

import java.io.File;
import java.io.FileInputStream;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.cotodel.hrms.web.properties.ApplicationConstantConfig;
import com.cotodel.hrms.web.response.BrandManagementRequest;
import com.cotodel.hrms.web.response.ErupiBrandDetailsRequest;
import com.cotodel.hrms.web.response.ErupiBrandGeoRequest;
import com.cotodel.hrms.web.response.TravelRequest;
import com.cotodel.hrms.web.service.BrandManagementService;
import com.cotodel.hrms.web.service.Impl.TokenGenerationImpl;
import com.cotodel.hrms.web.util.EncriptResponse;
import com.cotodel.hrms.web.util.EncryptionDecriptionUtil;

@RestController
@CrossOrigin
public class BrandManagerController extends CotoDelBaseController{
	
	private static final Logger logger = LoggerFactory.getLogger(BrandManagerController.class);

	@Autowired
	TokenGenerationImpl tokengeneration;
	@Autowired
	public ApplicationConstantConfig applicationConstantConfig;
	
	@Autowired
	public BrandManagementService brandManagementService;
	
	@GetMapping(value = "/getOutletTemplate")
	public ResponseEntity<InputStreamResource> getOutletTemplate() {
		try {
			//String filePath ="D:\\opt\\file\\"; //local path 
			String filePath ="/opt/cotodel/key/";
			String fileName = "Bulk_Outlet_Templates.xlsx";
			File file = new File(filePath+fileName);
			HttpHeaders headers = new HttpHeaders();    
			
			headers.add("content-disposition", "inline;filename=" +fileName);

			InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

			return ResponseEntity.ok()
					.headers(headers)
					.contentLength(file.length())
					.contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
					.body(resource);

		}catch (Exception e) {
			logger.info(e.getMessage());// TODO: handle exception
		}
		return null;
	}
	
	@PostMapping(value = "/addOutletDetail")
	public @ResponseBody String activateBrandManagement(HttpServletRequest request, ModelMap model, Locale locale,
			HttpSession session, BrandManagementRequest brandManagementRequest) {
		String profileRes = null;
		
		try {
			String json = EncryptionDecriptionUtil.convertToJson(brandManagementRequest);

			EncriptResponse jsonObject=EncryptionDecriptionUtil.encriptResponse(json, applicationConstantConfig.apiSignaturePublicPath);

			String encriptResponse =  brandManagementService.activateBrandManagement(tokengeneration.getToken(), jsonObject);
 
			EncriptResponse userReqEnc =EncryptionDecriptionUtil.convertFromJson(encriptResponse, EncriptResponse.class);

			profileRes =  EncryptionDecriptionUtil.decriptResponse(userReqEnc.getEncriptData(), userReqEnc.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
   
		return profileRes;	
}

	@GetMapping(value = "/getOutletDetail")
	public @ResponseBody String getOutletDetail(HttpServletRequest request, ModelMap model, Locale locale,
			HttpSession session, BrandManagementRequest brandManagementRequest) {
		String profileRes = null;
		
		try {
			String json = EncryptionDecriptionUtil.convertToJson(brandManagementRequest);

			EncriptResponse jsonObject=EncryptionDecriptionUtil.encriptResponse(json, applicationConstantConfig.apiSignaturePublicPath);

			String encriptResponse =  brandManagementService.getOutletDetail(tokengeneration.getToken(), jsonObject);
 
			EncriptResponse userReqEnc =EncryptionDecriptionUtil.convertFromJson(encriptResponse, EncriptResponse.class);

			profileRes =  EncryptionDecriptionUtil.decriptResponse(userReqEnc.getEncriptData(), userReqEnc.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
   
		return profileRes;	
}

	@PostMapping(value = "/addBrandDetails")
	public @ResponseBody String addBrandDetails(HttpServletRequest request, ModelMap model, Locale locale,
			HttpSession session, ErupiBrandDetailsRequest erupiBrandDetailsRequest) {
		String profileRes = null;
		
		try {
			String json = EncryptionDecriptionUtil.convertToJson(erupiBrandDetailsRequest);

			EncriptResponse jsonObject=EncryptionDecriptionUtil.encriptResponse(json, applicationConstantConfig.apiSignaturePublicPath);

			String encriptResponse =  brandManagementService.addBrandDetails(tokengeneration.getToken(), jsonObject);
 
			EncriptResponse userReqEnc =EncryptionDecriptionUtil.convertFromJson(encriptResponse, EncriptResponse.class);

			profileRes =  EncryptionDecriptionUtil.decriptResponse(userReqEnc.getEncriptData(), userReqEnc.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
   
		return profileRes;	
}
	
	@PostMapping(value = "/addGeograpgicDetails")
	public @ResponseBody String addGeograpgicDetails(HttpServletRequest request, ModelMap model, Locale locale,
			HttpSession session,@RequestBody ErupiBrandGeoRequest erupiBrandGeoRequest,BindingResult result) {
		String profileRes = null;
		
		try {
			String json = EncryptionDecriptionUtil.convertToJson(erupiBrandGeoRequest);

			EncriptResponse jsonObject=EncryptionDecriptionUtil.encriptResponse(json, applicationConstantConfig.apiSignaturePublicPath);

			String encriptResponse =  brandManagementService.addGeograpgicDetails(tokengeneration.getToken(), jsonObject);
 
			EncriptResponse userReqEnc =EncryptionDecriptionUtil.convertFromJson(encriptResponse, EncriptResponse.class);

			profileRes =  EncryptionDecriptionUtil.decriptResponse(userReqEnc.getEncriptData(), userReqEnc.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
   
		return profileRes;	
}
	@GetMapping(value = "/getBrandOutletList")
	public @ResponseBody String getBrandOutletList(HttpServletRequest request, ModelMap model, Locale locale,
			HttpSession session, ErupiBrandDetailsRequest brandManagementRequest) {
		String profileRes = null;
		
		try {
			String json = EncryptionDecriptionUtil.convertToJson(brandManagementRequest);

			EncriptResponse jsonObject=EncryptionDecriptionUtil.encriptResponse(json, applicationConstantConfig.apiSignaturePublicPath);

			String encriptResponse =  brandManagementService.getBrandOutletList(tokengeneration.getToken(), jsonObject);

			EncriptResponse userReqEnc =EncryptionDecriptionUtil.convertFromJson(encriptResponse, EncriptResponse.class);

			profileRes =  EncryptionDecriptionUtil.decriptResponse(userReqEnc.getEncriptData(), userReqEnc.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
   return profileRes;
}

	@GetMapping(value = "/getBrupiBrandGeoList")
	public @ResponseBody String getBrupiBrandGeoList(HttpServletRequest request, ModelMap model, Locale locale,
			HttpSession session, ErupiBrandGeoRequest erupiBrandGeoRequest) {
		String profileRes = null;
		
		try {
			String json = EncryptionDecriptionUtil.convertToJson(erupiBrandGeoRequest);

			EncriptResponse jsonObject=EncryptionDecriptionUtil.encriptResponse(json, applicationConstantConfig.apiSignaturePublicPath);

			String encriptResponse =  brandManagementService.getBrupiBrandGeoList(tokengeneration.getToken(), jsonObject);

			EncriptResponse userReqEnc =EncryptionDecriptionUtil.convertFromJson(encriptResponse, EncriptResponse.class);

			profileRes =  EncryptionDecriptionUtil.decriptResponse(userReqEnc.getEncriptData(), userReqEnc.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
   return profileRes;
}

}
