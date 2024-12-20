package com.cotodel.hrms.web.controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cotodel.hrms.web.properties.ApplicationConstantConfig;
import com.cotodel.hrms.web.response.RoleAccessRequest;
import com.cotodel.hrms.web.response.RoleDTO;
import com.cotodel.hrms.web.response.UserDTO;
import com.cotodel.hrms.web.response.UserRoleDTO;
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

	
	/*
	 * @PostMapping(value="/AddUserRole") public @ResponseBody String
	 * editUserRole(HttpServletRequest request, ModelMap model,Locale locale,
	 * HttpSession session,RoleAccessRequest roleAccessRequest) {
	 * logger.info("edit User with Role"); String token = (String)
	 * session.getAttribute("hrms"); return
	 * roleaccessservice.editUserRole(tokengeneration.getToken(),roleAccessRequest);
	 * }
	 */
	
	@PostMapping(value="/editUserRole")
	public @ResponseBody String editUserRole(HttpServletRequest request, ModelMap model,Locale locale,
			HttpSession session,@RequestBody RoleDTO requestDTO) {
			logger.info("edit User with Role");	
			String token = (String) session.getAttribute("hrms");
			
			 // Validate or process the data as needed
	        System.out.println("Org ID: " + requestDTO.getOrgId());
	        System.out.println("Employer ID: " + requestDTO.getEmployerId());
	        System.out.println("Created By: " + requestDTO.getCreatedBy());
	        
	        for (UserDTO user : requestDTO.getUserDTO()) {
	            System.out.println("User ID: " + user.getId());
	            System.out.println("Username: " + user.getUsername());
	            System.out.println("Email: " + user.getEmail());
	            System.out.println("Mobile: " + user.getMobile());
	            for (UserRoleDTO role : user.getUserRole()) {
	                System.out.println("Role: " + role.getRoleDesc());
	            }
	        }

			
			return roleaccessservice.editUserRoleDTO(tokengeneration.getToken(),requestDTO);
			//return new ResponseEntity<>("User roles updated successfully.", HttpStatus.OK);
	}
	
	@PostMapping(value="/deleteUserRole")
	public @ResponseBody String deleteUserRole(HttpServletRequest request, ModelMap model,Locale locale,
			HttpSession session,RoleAccessRequest roleAccessRequest) {
			logger.info("delete User with Role");	
			String token = (String) session.getAttribute("hrms");
			return roleaccessservice.deleteUserRole(tokengeneration.getToken(),roleAccessRequest);
	}
	
	@PostMapping(value="/userSearch")
	public @ResponseBody String userSearch(HttpServletRequest request, ModelMap model,Locale locale,
			HttpSession session,RoleAccessRequest roleAccessRequest) {
			logger.info("search User");	
			String token = (String) session.getAttribute("hrms");
			return roleaccessservice.userSearch(tokengeneration.getToken(),roleAccessRequest);
	}
	
	

}
