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
import com.cotodel.hrms.web.response.RoleAccessRequest;
import com.cotodel.hrms.web.service.RoleAccessService;
import com.cotodel.hrms.web.service.Impl.TokenGenerationImpl;

@Controller
@CrossOrigin
public class RoleAccessController extends CotoDelBaseController{

	private static final Logger logger = LoggerFactory.getLogger(RoleAccessController.class);
	

	@Autowired
	public ApplicationConstantConfig applicationConstantConfig;

	@Autowired
	TokenGenerationImpl tokengeneration;
	
	@Autowired
	RoleAccessService roleaccessservice;
	
	@GetMapping(value="/getUserWithRole")
	public @ResponseBody String getStateMaster(HttpServletRequest request, ModelMap model,Locale locale,
			HttpSession session,RoleAccessRequest roleAccessRequest) {
			logger.info("get User With Role");	
			String token = (String) session.getAttribute("hrms");
			return roleaccessservice.getUserRole(tokengeneration.getToken(),roleAccessRequest);
	}

	@PostMapping(value="/editUserRole")
	public @ResponseBody String editUserRole(HttpServletRequest request, ModelMap model,Locale locale,
			HttpSession session,RoleAccessRequest roleAccessRequest) {
			logger.info("edit User with Role");	
			String token = (String) session.getAttribute("hrms");
			return roleaccessservice.editUserRole(tokengeneration.getToken(),roleAccessRequest);
	}
	
	@PostMapping(value="/deleteUserRole")
	public @ResponseBody String deleteUserRole(HttpServletRequest request, ModelMap model,Locale locale,
			HttpSession session,RoleAccessRequest roleAccessRequest) {
			logger.info("edit User with Role");	
			String token = (String) session.getAttribute("hrms");
			return roleaccessservice.deleteUserRole(tokengeneration.getToken(),roleAccessRequest);
	}
	

}
