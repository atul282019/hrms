package com.cotodel.hrms.web.controller;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.cotodel.hrms.web.properties.ApplicationConstantConfig;
import com.cotodel.hrms.web.response.OrderUserRequest;
import com.cotodel.hrms.web.response.UserDetailsEntity;
import com.cotodel.hrms.web.service.CashfreePaymentService;
import com.cotodel.hrms.web.service.Impl.TokenGenerationImpl;
import com.cotodel.hrms.web.util.JwtTokenValidator;

@Controller
@CrossOrigin
public class CashfreePaymentController extends CotoDelBaseController{

	private static final Logger logger = LoggerFactory.getLogger(CashfreePaymentController.class);

	@Autowired
	public ApplicationConstantConfig applicationConstantConfig;
	@Autowired
	TokenGenerationImpl tokengeneration;
	
	@Autowired
	CashfreePaymentService cashfreePaymentService;
	
	@PostMapping(value="/getCashfreePaymentSession")
	public @ResponseBody String getCashfreePaymentSession(HttpServletRequest request, ModelMap model,Locale locale,
			HttpSession session,OrderUserRequest orderUserRequest) {
			logger.info("getPayrollMaster");	
			String token = (String) session.getAttribute("hrms");
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
			if (obj.getUser_role() == 9 || obj.getUser_role() == 1) {
				logger.info("Inside Role" + obj.getName());
				model.addAttribute("name", obj.getName());
				model.addAttribute("org", obj.getOrgName());
				model.addAttribute("mobile", obj.getMobile());
				model.addAttribute("email", obj.getEmail());
				model.addAttribute("order_id", order_id);
				model.addAttribute("id", id);
				return new ModelAndView("success", "command", "");
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
	
}
