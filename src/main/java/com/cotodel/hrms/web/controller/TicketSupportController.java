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
import com.cotodel.hrms.web.response.ErupiTicketSaveRequest;
import com.cotodel.hrms.web.service.TicketSupportService;
import com.cotodel.hrms.web.service.Impl.TokenGenerationImpl;
import com.cotodel.hrms.web.util.EncriptResponse;
import com.cotodel.hrms.web.util.EncryptionDecriptionUtil;

@RestController
@CrossOrigin
public class TicketSupportController extends CotoDelBaseController {
	
	private static final Logger logger = LoggerFactory.getLogger(VehicleManagementController.class);
	private static final String SECRET_KEY = "0123456789012345"; // Must match frontend
    private static final String CLIENT_KEY = "client-secret-key"; // Extra validation

	@Autowired
	TokenGenerationImpl tokengeneration;
	
	@Autowired
	public ApplicationConstantConfig applicationConstantConfig;
	
	@Autowired
	public TicketSupportService ticketSupportService;
	
	@PostMapping(value = "/submitTicket")
	public @ResponseBody String addVehicleDetails(HttpServletRequest request, ModelMap model, Locale locale,
			HttpSession session, ErupiTicketSaveRequest erupiTicketSaveRequest) {
		String profileRes = null;
		
		try {
			String json = EncryptionDecriptionUtil.convertToJson(erupiTicketSaveRequest);

			EncriptResponse jsonObject=EncryptionDecriptionUtil.encriptResponse(json, applicationConstantConfig.apiSignaturePublicPath);

			String encriptResponse =  ticketSupportService.submitTicketDetails(tokengeneration.getToken(), jsonObject);
 
			EncriptResponse userReqEnc =EncryptionDecriptionUtil.convertFromJson(encriptResponse, EncriptResponse.class);

			profileRes =  EncryptionDecriptionUtil.decriptResponse(userReqEnc.getEncriptData(), userReqEnc.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
   
		return profileRes;	
}


	

}
