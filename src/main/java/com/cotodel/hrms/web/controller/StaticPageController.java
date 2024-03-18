package com.cotodel.hrms.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.cotodel.hrms.web.response.UserDetailsEntity;
import com.cotodel.hrms.web.util.JwtTokenValidator;

@Controller
@CrossOrigin
public class StaticPageController extends CotoDelBaseController{

	private static final Logger logger = LoggerFactory.getLogger(StaticPageController.class);
	
	@GetMapping(value="/index")
	public ModelAndView firstPage(Model model) {
		logger.info("opening index page");
		return new ModelAndView("index", "command", "");
	}	
	@GetMapping(value="/signin")
	public ModelAndView directSignin(Model model) {
		logger.info("opening index page");
		return new ModelAndView("signin", "command", "");
	}	
	@GetMapping(value="/login")
	public ModelAndView loginPage(Model model) {
		logger.info("opening login Page");
		return new ModelAndView("index", "command", "");
	}	

	@GetMapping(value="/signup")
	public ModelAndView SignupPage(Model model) {
		logger.info("opening signupPage");
		return new ModelAndView("signup", "command", "");
	}	
	@GetMapping(value="/addEmployee")
	public ModelAndView addEmployee(Model model) {
		logger.info("opening signupPage");
		return new ModelAndView("add-emp", "command", "");
	}	
	
	
	
	@GetMapping(value="/companyDetails")
	public String companyDetail(Model model) {
		logger.info("opening companyDetailPage");
		String token = (String) session.getAttribute("hrms");
		Integer id  = (Integer) session.getAttribute("id");
		if(token!=null) {
			UserDetailsEntity obj = JwtTokenValidator.parseToken(token);
			if(obj!=null) {
				model.addAttribute("name",obj.getName());
				model.addAttribute("org",obj.getOrgName());
				model.addAttribute("mobile",obj.getMobile());
				model.addAttribute("email",obj.getEmail());
				model.addAttribute("id",id);
				return "company-details";
			}
		
		}
		return "redirect:/index";
	}	
	@GetMapping(value="/dashboard")
	public String dashboard(Model model) {
		logger.info("opening dashboardPage");
		String token = (String) session.getAttribute("hrms");
		Integer id  = (Integer) session.getAttribute("id");
		if(token!=null) {
			UserDetailsEntity obj = JwtTokenValidator.parseToken(token);
			if(obj!=null) {
				model.addAttribute("name",obj.getName());
				model.addAttribute("org",obj.getOrgName());
				model.addAttribute("mobile",obj.getMobile());
				model.addAttribute("email",obj.getEmail());
				model.addAttribute("id",id);
				return "dashboard";
			}
			
		}
		return "redirect:/index";
		
	}
	@GetMapping(value="/dashboard1")
	public String dashboard1(Model model) {
		logger.info("opening dashboardPage");
		String token = (String) session.getAttribute("hrms");
		Integer id  = (Integer) session.getAttribute("id");
		if(token!=null) {
			UserDetailsEntity obj = JwtTokenValidator.parseToken(token);
			if(obj!=null) {
				model.addAttribute("name",obj.getName());
				model.addAttribute("org",obj.getOrgName());
				model.addAttribute("mobile",obj.getMobile());
				model.addAttribute("email",obj.getEmail());
				model.addAttribute("id",id);
				return "dashboard01";
			}	
		}
		return "index";
	}
	@GetMapping(value="/tempLogin")
	public String dashboard1(Model model, @RequestParam("mobile") String mobile,@RequestParam("email") String email) {
		logger.info("opening dashboardPage");
		System.out.println("getting token"+mobile);
		String token = (String) session.getAttribute("hrms");
		Integer id  = (Integer) session.getAttribute("id");
		if(token!=null) {
			UserDetailsEntity obj = JwtTokenValidator.parseToken(token);
			if(obj!=null) {
				model.addAttribute("name",obj.getName());
				model.addAttribute("org",obj.getOrgName());
				model.addAttribute("mobile",obj.getMobile());
				model.addAttribute("email",obj.getEmail());
				model.addAttribute("id",id);
				return "dashboard01";
			}
		}
		return "dashboard01";
	}
	@GetMapping(value="/companyDetails01")
	public ModelAndView companyDetails(Model model) {
		logger.info("opening dashboard-details01");
		return new ModelAndView("company-details01", "command", "");
	}
	
	@GetMapping(value="/tdsPayment")
	public ModelAndView tdsPayment(Model model) {
		logger.info("opening dashboard-details01");
		return new ModelAndView("emp-tds", "command", "");
	}
	@GetMapping(value="/employeeIncentive")
	public ModelAndView employeeIncentive(Model model) {
		logger.info("opening dashboard-details01");
		return new ModelAndView("employee-incentive", "command", "");
	}
	
	@GetMapping(value="/employeeDetails")
	public ModelAndView employeeDetails(Model model) {
		
		logger.info("opening companyDetailPage");
		String token = (String) session.getAttribute("hrms");
		Integer id  = (Integer) session.getAttribute("id");
		if(token!=null) {
			UserDetailsEntity obj = JwtTokenValidator.parseToken(token);
			if(obj!=null) {
				model.addAttribute("name",obj.getName());
				model.addAttribute("org",obj.getOrgName());
				model.addAttribute("mobile",obj.getMobile());
				model.addAttribute("email",obj.getEmail());
				model.addAttribute("id",id);
				return new ModelAndView("emp-details", "command", "");
			}
		}
			model.addAttribute("id",id);
		return new ModelAndView("index", "command", "");
	}
	@GetMapping(value="/employeeSalary")
	public ModelAndView employeeSalary(Model model) {
		logger.info("opening dashboard-details01");
		return new ModelAndView("emp-salary", "command", "");
	}
	@GetMapping(value="/bulkUser")
	public ModelAndView bulkUser(Model model) {
		logger.info("opening dashboard-bulk-emp-details");
		return new ModelAndView("bulk-emp-details", "command", "");
	}
	@GetMapping(value="/pfPayment")
	public ModelAndView pfPayment(Model model) {
		logger.info("opening dashboard-details01");
		return new ModelAndView("emp-pf-payment", "command", "");
	}
	
	@GetMapping(value="/userCreation")
	public ModelAndView userCreation(Model model) {
		logger.info("opening dashboard-details01");
		String token = (String) session.getAttribute("hrms");
		Integer id  = (Integer) session.getAttribute("id");
		if(token!=null) {
			UserDetailsEntity obj = JwtTokenValidator.parseToken(token);
			if(obj!=null) {
				model.addAttribute("name",obj.getName());
				model.addAttribute("org",obj.getOrgName());
				model.addAttribute("mobile",obj.getMobile());
				model.addAttribute("email",obj.getEmail());
				model.addAttribute("employerId",id);
				return new ModelAndView("user-creation", "command", "");
			}
		}
			
		return new ModelAndView("index", "command", "");
	}
	
	
	@GetMapping(value="/empPayroll")
	public ModelAndView empPayroll(Model model) {
		String token = (String) session.getAttribute("hrms");
		Integer id  = (Integer) session.getAttribute("id");
		if(token!=null) {
			UserDetailsEntity obj = JwtTokenValidator.parseToken(token);
			if(obj!=null) {
				model.addAttribute("name",obj.getName());
				model.addAttribute("org",obj.getOrgName());
				model.addAttribute("mobile",obj.getMobile());
				model.addAttribute("email",obj.getEmail());
				model.addAttribute("employerId",id);
				return new ModelAndView("emp-payroll", "command", "");
			}
		}
		return new ModelAndView("index", "command", "");
	}
	
	@GetMapping(value="/peopel")
	public ModelAndView getPeopelList(Model model) {
		String token = (String) session.getAttribute("hrms");
		Integer id  = (Integer) session.getAttribute("id");
		if(token!=null) {
			UserDetailsEntity obj = JwtTokenValidator.parseToken(token);
			if(obj!=null) {
				model.addAttribute("name",obj.getName());
				model.addAttribute("org",obj.getOrgName());
				model.addAttribute("mobile",obj.getMobile());
				model.addAttribute("email",obj.getEmail());
				model.addAttribute("employerId",id);
				return new ModelAndView("peopel", "command", "");
			}
		}
		return new ModelAndView("index", "command", "");
	}
	
}
