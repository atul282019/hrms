package com.cotodel.hrms.web.controller;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.cotodel.hrms.web.properties.ApplicationConstantConfig;
import com.cotodel.hrms.web.response.CurrentMonthAmountLimit;
import com.cotodel.hrms.web.response.OrderUserRequest;
import com.cotodel.hrms.web.response.Root;
import com.cotodel.hrms.web.response.UserDetailsEntity;
import com.cotodel.hrms.web.response.UserForm;
import com.cotodel.hrms.web.response.WhatsAppRequest;
import com.cotodel.hrms.web.service.CashfreePaymentService;
import com.cotodel.hrms.web.service.ErupiVoucherCreateDetailsService;
import com.cotodel.hrms.web.service.LoginService;
import com.cotodel.hrms.web.service.Impl.EmailServiceImpl;
import com.cotodel.hrms.web.service.Impl.TokenGenerationImpl;
import com.cotodel.hrms.web.util.EncriptResponse;
import com.cotodel.hrms.web.util.EncryptionDecriptionUtil;
import com.cotodel.hrms.web.util.JwtTokenValidator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper; // version 2.11.1

@Controller
@CrossOrigin
public class CashfreePaymentController extends CotoDelBaseController{

	private static final Logger logger = LoggerFactory.getLogger(CashfreePaymentController.class);

	@Autowired
	public ApplicationConstantConfig applicationConstantConfig;
	@Autowired
	TokenGenerationImpl tokengeneration;
	
	@Autowired
	LoginService loginservice;
	
	@Autowired
	EmailServiceImpl emailService;
	
	@Autowired
	CashfreePaymentService cashfreePaymentService;
	
	@Autowired
	ErupiVoucherCreateDetailsService erupiVoucherCreateDetailsService;
	
	@PostMapping(value="/getCashfreePaymentSession")
	public @ResponseBody String getCashfreePaymentSession(HttpServletRequest request, ModelMap model,Locale locale,
			HttpSession session,OrderUserRequest orderUserRequest) {
			logger.info("getPayrollMaster");	
			String profileRes=null;
			try {
				profileRes = cashfreePaymentService.getCashfreePaymentSession(tokengeneration.getToken(),orderUserRequest);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	   
		return profileRes;
	}
	
	@GetMapping(value="/cashfreeReturnUrl")
	public ModelAndView employeeDetails(Model model , @RequestParam String order_id) {
		
		logger.info("cashfreeReturnUrl" + order_id);	
		String token = (String) session.getAttribute("hrms");
		Integer id  = (Integer) session.getAttribute("id");
		if(token!=null) {
			logger.info("Inside token" + token);	
			UserDetailsEntity obj = JwtTokenValidator.parseToken(token);
			logger.info("Inside Object" + obj.toString());
			logger.info("Inside Object" + obj.getName());
			if (obj.getUser_role() == 9 || obj.getUser_role() == 1 || obj.getUser_role() == 3) {
				logger.info("Inside Role" + obj.getName());
				model.addAttribute("name", obj.getName());
				model.addAttribute("org", obj.getOrgName());
				model.addAttribute("mobile", obj.getMobile());
				model.addAttribute("email", obj.getEmail());
				model.addAttribute("order_id", order_id);
				model.addAttribute("id", id);
				//return new ModelAndView("success", "command", "");
				return new ModelAndView("coto-wallet", "command", "");
			}
			return new ModelAndView("error", "command", "");
	}
	return new ModelAndView("index", "command", "");
}
	
@PostMapping(value="/getOrderDetailByOrderId")
public @ResponseBody String getOrderDetailByOrderId(HttpServletRequest request, ModelMap model,Locale locale,
			HttpSession session,OrderUserRequest orderUserRequest) {
			logger.info("getPayrollMaster");	
			String token = (String) session.getAttribute("hrms");
			String profileRes=null;
			try {
				profileRes = cashfreePaymentService.getOrderDetailByOrderId(tokengeneration.getToken(),orderUserRequest);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	   
		return profileRes;
	}
@PostMapping(value="/webhook-callback_notify")
public ResponseEntity<Void> paymentCallBackWebhooks(@RequestBody(required = false) String payload) throws JsonMappingException, JsonProcessingException {
	
		logger.info("webhook-callback called");
		logger.info("hook called"+payload);	
		String profileRes = null;
	    ObjectMapper om = new ObjectMapper();
		Root root = om.readValue(payload, Root.class); 
		try {
			profileRes = cashfreePaymentService.paymentCallBackData(tokengeneration.getToken(),root);
			

			// Start SMS and Email service
            UserForm userForm = new UserForm();
            userForm.setMobile((String) session.getAttribute("mobile"));
            userForm.setTemplate("Cotodel Voucher Activity");
            try {
            String userFormjson = EncryptionDecriptionUtil.convertToJson(userForm);

			EncriptResponse userFormjsonObject=EncryptionDecriptionUtil.encriptResponse(userFormjson, applicationConstantConfig.apiSignaturePublicPath);

			String encriptResponse = loginservice.sendOtpWith2Factor(tokengeneration.getToken(), userFormjsonObject);

   
			EncriptResponse userFornReqEnc =EncryptionDecriptionUtil.convertFromJson(encriptResponse, EncriptResponse.class);

			String smsResponse =  EncryptionDecriptionUtil.decriptResponse(userFornReqEnc.getEncriptData(), userFornReqEnc.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
			//String emailRequest =	emailService.sendEmail(employeeOnboarding.getEmail());
            } catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
            
            try {
            	WhatsAppRequest whatsapp = new WhatsAppRequest();
                whatsapp.setSource("new-landing-page form");
                whatsapp.setCampaignName("Voucher_Issuance");
                whatsapp.setFirstName((String) session.getAttribute("usernamme"));
                whatsapp.setAmount(Integer.toString(root.data.order.order_amount));
                //whatsapp.setCategory(item.getVoucherDesc());
                whatsapp.setMobile((String) session.getAttribute("mobile"));
                whatsapp.setOrganizationName("Cotodel");
                //whatsapp.setValidity(item.getValidity());
                //whatsapp.setType(item.getRedemtionType());
                whatsapp.setUserName("Cotodel Communications");
    			String json = EncryptionDecriptionUtil.convertToJson(whatsapp);

    			EncriptResponse jsonObject=EncryptionDecriptionUtil.encriptResponse(json, applicationConstantConfig.apiSignaturePublicPath);

    			String encriptResponse =  erupiVoucherCreateDetailsService.sendWhatsupMessage(tokengeneration.getToken(), jsonObject);
       
    			EncriptResponse userReqEnc =EncryptionDecriptionUtil.convertFromJson(encriptResponse, EncriptResponse.class);

    			profileRes =  EncryptionDecriptionUtil.decriptResponse(userReqEnc.getEncriptData(), userReqEnc.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
    		} catch (Exception e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
         //End Start SMS and Email service
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new ResponseEntity<>(null, HttpStatus.OK);
		
}

@PostMapping(value="/production-webhook-callback")
public ResponseEntity<Void> paymentCallBack(@RequestBody(required = false) String payload) throws JsonMappingException, JsonProcessingException {
	
		logger.info("production-webhook-callback");
		logger.info("production-webhook-callback"+payload);	
		String profileRes=null;
	    ObjectMapper om = new ObjectMapper();
		Root root = om.readValue(payload, Root.class); 
		try {
			
			profileRes = cashfreePaymentService.paymentCallBackData(tokengeneration.getToken(),root);
			
			// Start SMS and Email service
            UserForm userForm = new UserForm();
            userForm.setMobile((String) session.getAttribute("mobile"));
            userForm.setTemplate("Cotodel Voucher Activity");
            try {
            String userFormjson = EncryptionDecriptionUtil.convertToJson(userForm);

			EncriptResponse userFormjsonObject=EncryptionDecriptionUtil.encriptResponse(userFormjson, applicationConstantConfig.apiSignaturePublicPath);

			String encriptResponse = loginservice.sendOtpWith2Factor(tokengeneration.getToken(), userFormjsonObject);

   
			EncriptResponse userFornReqEnc =EncryptionDecriptionUtil.convertFromJson(encriptResponse, EncriptResponse.class);

			String smsResponse =  EncryptionDecriptionUtil.decriptResponse(userFornReqEnc.getEncriptData(), userFornReqEnc.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
			//String emailRequest =	emailService.sendEmail(employeeOnboarding.getEmail());
            } catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
            try {
            	WhatsAppRequest whatsapp = new WhatsAppRequest();
                whatsapp.setSource("new-landing-page form");
                whatsapp.setCampaignName("Voucher_Issuance");
                whatsapp.setFirstName((String) session.getAttribute("usernamme"));
                whatsapp.setAmount(Integer.toString(root.data.order.order_amount));
                //whatsapp.setCategory(item.getVoucherDesc());
                whatsapp.setMobile((String) session.getAttribute("mobile"));
                whatsapp.setOrganizationName("Cotodel");
                //whatsapp.setValidity(item.getValidity());
                //whatsapp.setType(item.getRedemtionType());
                whatsapp.setUserName("Cotodel Communications");
    			String json = EncryptionDecriptionUtil.convertToJson(whatsapp);

    			EncriptResponse jsonObject=EncryptionDecriptionUtil.encriptResponse(json, applicationConstantConfig.apiSignaturePublicPath);

    			String encriptResponse =  erupiVoucherCreateDetailsService.sendWhatsupMessage(tokengeneration.getToken(), jsonObject);
       
    			EncriptResponse userReqEnc =EncryptionDecriptionUtil.convertFromJson(encriptResponse, EncriptResponse.class);

    			profileRes =  EncryptionDecriptionUtil.decriptResponse(userReqEnc.getEncriptData(), userReqEnc.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
    		} catch (Exception e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
         //End Start SMS and Email service
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new ResponseEntity<>(null, HttpStatus.OK);
		
}


@PostMapping(value="/preprod-webhook-callback")
public ResponseEntity<Void> preprodWebhookCallback(@RequestBody(required = false) String payload) throws JsonMappingException, JsonProcessingException {
	
		logger.info("preprod-webhook-callback");
		logger.info("preprod-webhook-callback"+payload);	
		String profileRes=null;
	    ObjectMapper om = new ObjectMapper();
		Root root = om.readValue(payload, Root.class); 
		try {
			
			profileRes = cashfreePaymentService.preprodWebhookCallback(tokengeneration.getToken(),root);
			
			// Start SMS and Email service
            UserForm userForm = new UserForm();
            userForm.setMobile((String) session.getAttribute("mobile"));
            userForm.setTemplate("Cotodel Voucher Activity");
            try {
            String userFormjson = EncryptionDecriptionUtil.convertToJson(userForm);

			EncriptResponse userFormjsonObject=EncryptionDecriptionUtil.encriptResponse(userFormjson, applicationConstantConfig.apiSignaturePublicPath);

			String encriptResponse = loginservice.sendOtpWith2Factor(tokengeneration.getToken(), userFormjsonObject);
   
			EncriptResponse userFornReqEnc =EncryptionDecriptionUtil.convertFromJson(encriptResponse, EncriptResponse.class);

			String smsResponse =  EncryptionDecriptionUtil.decriptResponse(userFornReqEnc.getEncriptData(), userFornReqEnc.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
			//String emailRequest =	emailService.sendEmail(employeeOnboarding.getEmail());
            } catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
            try {
            	WhatsAppRequest whatsapp = new WhatsAppRequest();
                whatsapp.setSource("new-landing-page form");
                whatsapp.setCampaignName("Voucher_Issuance");
                whatsapp.setFirstName((String) session.getAttribute("usernamme"));
                whatsapp.setAmount(Integer.toString(root.data.order.order_amount));
                //whatsapp.setCategory(item.getVoucherDesc());
                whatsapp.setMobile((String) session.getAttribute("mobile"));
                whatsapp.setOrganizationName("Cotodel");
                //whatsapp.setValidity(item.getValidity());
                //whatsapp.setType(item.getRedemtionType());
                whatsapp.setUserName("Cotodel Communications");
    			String json = EncryptionDecriptionUtil.convertToJson(whatsapp);

    			EncriptResponse jsonObject=EncryptionDecriptionUtil.encriptResponse(json, applicationConstantConfig.apiSignaturePublicPath);

    			String encriptResponse =  erupiVoucherCreateDetailsService.sendWhatsupMessage(tokengeneration.getToken(), jsonObject);
       
    			EncriptResponse userReqEnc =EncryptionDecriptionUtil.convertFromJson(encriptResponse, EncriptResponse.class);

    			profileRes =  EncryptionDecriptionUtil.decriptResponse(userReqEnc.getEncriptData(), userReqEnc.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
    		} catch (Exception e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
         //End Start SMS and Email service
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new ResponseEntity<>(null, HttpStatus.OK);
		
}

@PostMapping(value="/stagingWebhook")
public ResponseEntity<Void> staging_webhook(@RequestBody(required = false) String payload) throws JsonMappingException, JsonProcessingException {
	
		logger.info("staging_webhook");
		logger.info("staging_webhook hook called"+payload);	
		String profileRes=null;
	    ObjectMapper om = new ObjectMapper();
		Root root = om.readValue(payload, Root.class); 
		try {
			
			profileRes = cashfreePaymentService.stagingWebhookSave(tokengeneration.getToken(),root);
			

			// Start SMS and Email service
            UserForm userForm = new UserForm();
            userForm.setMobile((String) session.getAttribute("mobile"));
            userForm.setTemplate("Cotodel Voucher Activity");
            try {
            String userFormjson = EncryptionDecriptionUtil.convertToJson(userForm);

			EncriptResponse userFormjsonObject=EncryptionDecriptionUtil.encriptResponse(userFormjson, applicationConstantConfig.apiSignaturePublicPath);

			String encriptResponse = loginservice.sendOtpWith2Factor(tokengeneration.getToken(), userFormjsonObject);

   
			EncriptResponse userFornReqEnc =EncryptionDecriptionUtil.convertFromJson(encriptResponse, EncriptResponse.class);

			String smsResponse =  EncryptionDecriptionUtil.decriptResponse(userFornReqEnc.getEncriptData(), userFornReqEnc.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
			//String emailRequest =	emailService.sendEmail(employeeOnboarding.getEmail());
            } catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
            try {
            	WhatsAppRequest whatsapp = new WhatsAppRequest();
                whatsapp.setSource("new-landing-page form");
                whatsapp.setCampaignName("Voucher_Issuance");
                whatsapp.setFirstName((String) session.getAttribute("usernamme"));
                whatsapp.setAmount(Integer.toString(root.data.order.order_amount));
                //whatsapp.setCategory(item.getVoucherDesc());
                whatsapp.setMobile((String) session.getAttribute("mobile"));
                whatsapp.setOrganizationName("Cotodel");
                //whatsapp.setValidity(item.getValidity());
                //whatsapp.setType(item.getRedemtionType());
                whatsapp.setUserName("Cotodel Communications");
    			String json = EncryptionDecriptionUtil.convertToJson(whatsapp);

    			EncriptResponse jsonObject=EncryptionDecriptionUtil.encriptResponse(json, applicationConstantConfig.apiSignaturePublicPath);

    			String encriptResponse =  erupiVoucherCreateDetailsService.sendWhatsupMessage(tokengeneration.getToken(), jsonObject);
       
    			EncriptResponse userReqEnc =EncryptionDecriptionUtil.convertFromJson(encriptResponse, EncriptResponse.class);

    			profileRes =  EncryptionDecriptionUtil.decriptResponse(userReqEnc.getEncriptData(), userReqEnc.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
    		} catch (Exception e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
         //End Start SMS and Email service
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new ResponseEntity<>(null, HttpStatus.OK);
		
}
@PostMapping(value="/viewOrderDetailByOrderId")
public @ResponseBody String viewOrderDetailByOrderId(HttpServletRequest request, ModelMap model,Locale locale,
			HttpSession session,OrderUserRequest orderUserRequest) {
			logger.info("getPayrollMaster");	
			String token = (String) session.getAttribute("hrms");
			String profileRes=null;
			try {
				profileRes = cashfreePaymentService.viewOrderDetailByOrderId(tokengeneration.getToken(),orderUserRequest);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	   
		return profileRes;
	}
@PostMapping(value="/viewOrderIdList")
public @ResponseBody String viewOrderIdList(HttpServletRequest request, ModelMap model,Locale locale,
			HttpSession session,OrderUserRequest orderUserRequest) {
			logger.info("getPayrollMaster");	
			String profileRes=null;
			try {
				profileRes = cashfreePaymentService.viewOrderIdList(tokengeneration.getToken(),orderUserRequest);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	   
		return profileRes;
	}



@PostMapping(value="/currentMonthAmountLimit")
public @ResponseBody String getCurrentMonthAmountLimit(HttpServletRequest request, ModelMap model,Locale locale,
		HttpSession session,CurrentMonthAmountLimit currentMonthAmountLimit) {
		logger.info("getPayrollMaster");	
		String profileRes=null;
		try {
			profileRes = cashfreePaymentService.getCurrentMonthAmountLimit(tokengeneration.getToken(),currentMonthAmountLimit);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
   
	return profileRes;
}

}

