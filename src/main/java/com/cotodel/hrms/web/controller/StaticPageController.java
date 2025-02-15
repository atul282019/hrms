package com.cotodel.hrms.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.cotodel.hrms.web.response.UserDetailsEntity;
import com.cotodel.hrms.web.response.VoucherTypeMaster;
import com.cotodel.hrms.web.util.JwtTokenValidator;

@Controller
@CrossOrigin
public class StaticPageController extends CotoDelBaseController{

	private static final Logger logger = LoggerFactory.getLogger(StaticPageController.class);
	
	
	@GetMapping(value="/index")
	public ModelAndView firstPage(Model model) {
		return new ModelAndView("home", "command", "");
	}	
	@GetMapping(value="/signin")
	public ModelAndView directSignin(Model model) {
		return new ModelAndView("signin", "command", "");
	}	
	@GetMapping(value="/login")
	public ModelAndView loginPage(Model model) {
		logger.info("opening login Page");
	    String mobile  = (String) session.getAttribute("mobile");
		if(mobile!=null) {
			model.addAttribute("message","");
			model.addAttribute("mobile","");
			model.addAttribute("orderid","");
			return new ModelAndView("index", "command", "");
		}
		return new ModelAndView("index", "command", "");

	}
	@GetMapping(value="/FleetLogin")
	public ModelAndView loginFleet(Model model) {
		logger.info("opening login Fleet");
		
	    String message = (String)session.getAttribute("message");
	    String mobile  = (String) session.getAttribute("mobile");
	    String orderid  = (String) session.getAttribute("orderid");
		
		model.addAttribute("message",message);
		model.addAttribute("mobile",mobile);
		model.addAttribute("orderid",orderid);
		return new ModelAndView("index", "command", "");

	}	
	
	@GetMapping(value="/loginNew")
	public ModelAndView loginNew(Model model) {
		logger.info("opening loginNew");
		return new ModelAndView("login-FleetManagment", "command", "");
	}	

	@GetMapping(value="/signup")
	public ModelAndView SignupPage(Model model) {
		logger.info("opening signupPage");
		return new ModelAndView("signup", "command", "");
	}	
	
	@GetMapping(value="/companyDetails")
	public String companyDetail(Model model) {
		logger.info("opening companyDetailPage");
		String token = (String) session.getAttribute("hrms");
		Integer id  = (Integer) session.getAttribute("id");
		if(token!=null) {
			UserDetailsEntity obj = JwtTokenValidator.parseToken(token);
			if(obj!=null) {
				if(obj.getUser_role()==9 || obj.getUser_role()==1){
				model.addAttribute("name",obj.getName());
				model.addAttribute("org",obj.getOrgName());
				model.addAttribute("mobile",obj.getMobile());
				model.addAttribute("email",obj.getEmail());
				model.addAttribute("id",id);
				return "company-details";
			}
		  return "error";
		}
		return "redirect:/index";
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
				if(obj.getUser_role()==9 || obj.getUser_role()==1 || obj.getUser_role()==2) {
				model.addAttribute("name",obj.getName());
				model.addAttribute("org",obj.getOrgName());
				model.addAttribute("mobile",obj.getMobile());
				model.addAttribute("email",obj.getEmail());
				model.addAttribute("id",id);
				return "dashboard";
			}
			 return "error";
		}
		return "redirect:/index";
		
	}
	return "redirect:/index";	
}	

	@GetMapping(value="/dashboard1")
	public String dashboard1(Model model) {
		logger.info("opening dashboard1");
		String token = (String) session.getAttribute("hrms");
		Integer id  = (Integer) session.getAttribute("id");
		if(token!=null) {
			UserDetailsEntity obj = JwtTokenValidator.parseToken(token);
			if(obj!=null) {
				if(obj.getUser_role()==9 || obj.getUser_role()==2 || obj.getUser_role()==1) {
				model.addAttribute("name",obj.getName());
				model.addAttribute("org",obj.getOrgName());
				model.addAttribute("mobile",obj.getMobile());
				model.addAttribute("email",obj.getEmail());
				model.addAttribute("id",id);
				return "dashboard01";
			}	
		  return "error";
		}
		return "index";
	}
	return "index";
}
	@GetMapping(value="/tempLogin")
	public String dashboard1(Model model, @RequestParam("mobile") String mobile,@RequestParam("email") String email) {
		logger.info("opening tempLogin");
		System.out.println("getting token"+mobile);
		String token = (String) session.getAttribute("hrms");
		Integer id  = (Integer) session.getAttribute("id");
		if(token!=null) {
			UserDetailsEntity obj = JwtTokenValidator.parseToken(token);
			if(obj!=null) {
				if(obj.getUser_role()==9) {
				model.addAttribute("name",obj.getName());
				model.addAttribute("org",obj.getOrgName());
				model.addAttribute("mobile",obj.getMobile());
				model.addAttribute("email",obj.getEmail());
				model.addAttribute("id",id);
				return "dashboard01";
			}
			return "error";
		}
		return "dashboard01";
	}
	return "dashboard01";
}
	
	@GetMapping(value="/employeeDetails")
	public ModelAndView employeeDetails(Model model) {
		
		logger.info("opening employeeDetails");
		String token = (String) session.getAttribute("hrms");
		Integer id  = (Integer) session.getAttribute("id");
		if(token!=null) {
			UserDetailsEntity obj = JwtTokenValidator.parseToken(token);
			if(obj!=null) {
				if(obj.getUser_role()==9) {
				model.addAttribute("name",obj.getName());
				model.addAttribute("org",obj.getOrgName());
				model.addAttribute("mobile",obj.getMobile());
				model.addAttribute("email",obj.getEmail());
				model.addAttribute("id",id);
				return new ModelAndView("emp-details", "command", "");
			}
			 return new ModelAndView("error", "command", "");
		}
		model.addAttribute("id",id);
		return new ModelAndView("index", "command", "");
	}
	return new ModelAndView("index", "command", "");
}

	@GetMapping(value="/bulkInvite")
	public ModelAndView bulkInvite(Model model) {
		logger.info("opening bulkInvite");
		String token = (String) session.getAttribute("hrms");
		Integer id  = (Integer) session.getAttribute("id");
		if(token!=null) {
			UserDetailsEntity obj = JwtTokenValidator.parseToken(token);
			if(obj!=null) {
				if(obj.getUser_role()==9 || obj.getUser_role()==1) {
				model.addAttribute("name",obj.getName());
				model.addAttribute("org",obj.getOrgName());
				model.addAttribute("mobile",obj.getMobile());
				model.addAttribute("email",obj.getEmail());
				model.addAttribute("employerId",id);
				return new ModelAndView("bulk-invite", "command", "");
			}
			 return new ModelAndView("error", "command", "");
		}
		return new ModelAndView("index", "command", "");
	}
	return new ModelAndView("index", "command", "");
}
	@GetMapping(value="/bulkupload")
	public ModelAndView bulkUser(Model model) {
		logger.info("opening bulkupload");
		//return new ModelAndView("bulk-emp-details", "command", "");
		String token = (String) session.getAttribute("hrms");
		Integer id  = (Integer) session.getAttribute("id");
		if(token!=null) {
			UserDetailsEntity obj = JwtTokenValidator.parseToken(token);
			if(obj!=null) {
				if(obj.getUser_role()==9 || obj.getUser_role()==1) {
				model.addAttribute("name",obj.getName());
				model.addAttribute("org",obj.getOrgName());
				model.addAttribute("mobile",obj.getMobile());
				model.addAttribute("email",obj.getEmail());
				model.addAttribute("employerId",id);
				return new ModelAndView("bulk-upload", "command", "");
			}
			 return new ModelAndView("error", "command", "");
		}
		return new ModelAndView("index", "command", "");
	}
	return new ModelAndView("index", "command", "");
}

	@GetMapping(value="/bulkUserList")
	public ModelAndView bulkUserList(Model model) {
		logger.info("opening bulkUserList");
		//return new ModelAndView("bulk-emp-details", "command", "");
		String token = (String) session.getAttribute("hrms");
		Integer id  = (Integer) session.getAttribute("id");
		if(token!=null) {
			UserDetailsEntity obj = JwtTokenValidator.parseToken(token);
			if(obj!=null) {
				if(obj.getUser_role()==9) {
				model.addAttribute("name",obj.getName());
				model.addAttribute("org",obj.getOrgName());
				model.addAttribute("mobile",obj.getMobile());
				model.addAttribute("email",obj.getEmail());
				model.addAttribute("employerId",id);
				return new ModelAndView("bulk-table-invitelist_2", "command", "");
			}
			 return new ModelAndView("error", "command", "");
		}
		return new ModelAndView("index", "command", "");
	}
	return new ModelAndView("index", "command", "");
}
	
	@GetMapping(value="/manageEmployee")
	public ModelAndView manageEmployee(Model model) {
		logger.info("opening manageEmployee");
		//return new ModelAndView("bulk-emp-details", "command", "");
		String token = (String) session.getAttribute("hrms");
		Integer id  = (Integer) session.getAttribute("id");
		if(token!=null) {
			UserDetailsEntity obj = JwtTokenValidator.parseToken(token);
			if(obj!=null) {
				if(obj.getUser_role()==1  || obj.getUser_role()==9) {
				model.addAttribute("name",obj.getName());
				model.addAttribute("org",obj.getOrgName());
				model.addAttribute("mobile",obj.getMobile());
				model.addAttribute("email",obj.getEmail());
				model.addAttribute("employerId",id);
				return new ModelAndView("employee-manage", "command", "");
			}
			 return new ModelAndView("error", "command", "");
		}
		return new ModelAndView("index", "command", "");
	}
	return new ModelAndView("index", "command", "");
}
		@GetMapping(value="/manageEmployeeContractor")
		public ModelAndView manageEmployeeContractor(Model model) {
			logger.info("opening manageEmployeeContractor");
			//return new ModelAndView("bulk-emp-details", "command", "");
			String token = (String) session.getAttribute("hrms");
			Integer id  = (Integer) session.getAttribute("id");
			if(token!=null) {
				UserDetailsEntity obj = JwtTokenValidator.parseToken(token);
				if(obj!=null) {
					if(obj.getUser_role()==9) {
					model.addAttribute("name",obj.getName());
					model.addAttribute("org",obj.getOrgName());
					model.addAttribute("mobile",obj.getMobile());
					model.addAttribute("email",obj.getEmail());
					model.addAttribute("employerId",id);
					return new ModelAndView("employee-manage-and-contractors", "command", "");
				}
				 return new ModelAndView("error", "command", "");
			}
			return new ModelAndView("index", "command", "");
		}
		return new ModelAndView("index", "command", "");
	}
		
		@GetMapping(value="/employeeOnBoarding")
		public ModelAndView employeeOnBoarding(Model model) {
			logger.info("opening employeeOnBoarding");
			//return new ModelAndView("bulk-emp-details", "command", "");
			String token = (String) session.getAttribute("hrms");
			Integer id  = (Integer) session.getAttribute("id");
			if(token!=null) {
				UserDetailsEntity obj = JwtTokenValidator.parseToken(token);
				if(obj!=null) {
					if(obj.getUser_role()==9 || obj.getUser_role()==1) {
					model.addAttribute("name",obj.getName());
					model.addAttribute("org",obj.getOrgName());
					model.addAttribute("mobile",obj.getMobile());
					model.addAttribute("email",obj.getEmail());
					model.addAttribute("employerId",id);
					return new ModelAndView("employee-onboarding", "command", "");
				}
				 return new ModelAndView("error", "command", "");
			}
			return new ModelAndView("index", "command", "");
		}
		return new ModelAndView("index", "command", "");
	}
	
	@GetMapping(value="/dashBoardNew")
	public ModelAndView dashboardNew(Model model) {
		String token = (String) session.getAttribute("hrms");
		Integer id  = (Integer) session.getAttribute("id");
		if(token!=null) {
			UserDetailsEntity obj = JwtTokenValidator.parseToken(token);
			if(obj!=null) {
				if(obj.getUser_role()==9 || obj.getUser_role()==2 || obj.getUser_role()==1) {
				model.addAttribute("name",obj.getName());
				model.addAttribute("org",obj.getOrgName());
				model.addAttribute("mobile",obj.getMobile());
				model.addAttribute("email",obj.getEmail());
				model.addAttribute("employerId",id);
				return new ModelAndView("dashboard-old", "command", "");
			}
			 return new ModelAndView("error", "command", "");
		}
		return new ModelAndView("index", "command", "");
	}
	return new ModelAndView("index", "command", "");
}
	
	@GetMapping(value="/companyDetailNew")
	public ModelAndView companyDetailNew(Model model) {
		String token = (String) session.getAttribute("hrms");
		Integer id  = (Integer) session.getAttribute("id");
		if(token!=null) {
			UserDetailsEntity obj = JwtTokenValidator.parseToken(token);
			if(obj!=null) {
				if(obj.getUser_role()==9) {
				model.addAttribute("name",obj.getName());
				model.addAttribute("org",obj.getOrgName());
				model.addAttribute("mobile",obj.getMobile());
				model.addAttribute("email",obj.getEmail());
				model.addAttribute("employerId",id);
				return new ModelAndView("company-detail-new", "command", "");
			}
			 return new ModelAndView("error", "command", "");
		}
		return new ModelAndView("index", "command", "");
	}
	return new ModelAndView("index", "command", "");
}
	
	@GetMapping(value="/employeeOnboarding")
	public ModelAndView employeeOnboarding(Model model) {
		String token = (String) session.getAttribute("hrms");
		Integer id  = (Integer) session.getAttribute("id");
		if(token!=null) {
			UserDetailsEntity obj = JwtTokenValidator.parseToken(token);
			if(obj!=null) {
				if(obj.getUser_role()==9) {
				model.addAttribute("name",obj.getName());
				model.addAttribute("org",obj.getOrgName());
				model.addAttribute("mobile",obj.getMobile());
				model.addAttribute("email",obj.getEmail());
				model.addAttribute("employerId",id);
				return new ModelAndView("employee-onboarding-admin", "command", "");
			}
			 return new ModelAndView("error", "command", "");
		}
		return new ModelAndView("index", "command", "");
	}
	return new ModelAndView("index", "command", "");
}
	
	@GetMapping(value="/employeeOnboarding-full-action")
	public ModelAndView employeeOnboardingFullAction(Model model) {
		String token = (String) session.getAttribute("hrms");
		Integer id  = (Integer) session.getAttribute("id");
		if(token!=null) {
			UserDetailsEntity obj = JwtTokenValidator.parseToken(token);
			if(obj!=null) {
				if(obj.getUser_role()==9 || obj.getUser_role()==1 || obj.getUser_role()==2) {
				model.addAttribute("name",obj.getName());
				model.addAttribute("org",obj.getOrgName());
				model.addAttribute("mobile",obj.getMobile());
				model.addAttribute("email",obj.getEmail());
				model.addAttribute("employerId",id);
				
				return new ModelAndView("employee-onboarding-full-action", "command", "");
			}
			 return new ModelAndView("error", "command", "");
		}
		return new ModelAndView("index", "command", "");
	}
	return new ModelAndView("index", "command", "");
}
//	@PostMapping(value="/employeeOnboarding-full-action")
//	public ModelAndView employeeOnboardingFullAction(  String reportingManager,  int employeeId,Model model) {
//		String token = (String) session.getAttribute("hrms");
//		Integer id  = (Integer) session.getAttribute("id");
//		if(token!=null) {
//			UserDetailsEntity obj = JwtTokenValidator.parseToken(token);
//			if(obj!=null) {
//				if(obj.getUser_role()==9 || obj.getUser_role()==1 || obj.getUser_role()==2) {
//				model.addAttribute("name",obj.getName());
//				model.addAttribute("org",obj.getOrgName());
//				model.addAttribute("mobile",obj.getMobile());
//				model.addAttribute("email",obj.getEmail());
//				model.addAttribute("employerId",id);
//				model.addAttribute("reportingManagerid", reportingManager);
//				model.addAttribute("employeeId", employeeId);
//				return new ModelAndView("employee-onboarding-full-action", "command", "");
//			}
//			 return new ModelAndView("error", "command", "");
//		}
//		return new ModelAndView("index", "command", "");
//	}
//	return new ModelAndView("index", "command", "");
//}
	
	@GetMapping(value="/employeeProfile")
	public ModelAndView employeeOnboarding2(Model model) {
		String token = (String) session.getAttribute("hrms");
		Integer id  = (Integer) session.getAttribute("id");
		if(token!=null) {
			UserDetailsEntity obj = JwtTokenValidator.parseToken(token);
			if(obj!=null) {
				if(obj.getUser_role()==9 || obj.getUser_role()==2) {
				model.addAttribute("name",obj.getName());
				model.addAttribute("org",obj.getOrgName());
				model.addAttribute("mobile",obj.getMobile());
				model.addAttribute("email",obj.getEmail());
				model.addAttribute("employerId",id);
				return new ModelAndView("Employee-Onboarding-Admin", "command", "");
			}
			 return new ModelAndView("error", "command", "");
		}
		return new ModelAndView("index", "command", "");
	}
	return new ModelAndView("index", "command", "");
}
	
	@GetMapping(value="/expanceTravel")
	public ModelAndView expanceTravel(Model model) {
		String token = (String) session.getAttribute("hrms");
		Integer id  = (Integer) session.getAttribute("id");
		if(token!=null) {
			UserDetailsEntity obj = JwtTokenValidator.parseToken(token);
			if(obj!=null) {
				if(obj.getUser_role()==1) {
				model.addAttribute("name",obj.getName());
				model.addAttribute("org",obj.getOrgName());
				model.addAttribute("mobile",obj.getMobile());
				model.addAttribute("email",obj.getEmail());
				model.addAttribute("employerId",id);
				return new ModelAndView("expenses-travel-policies", "command", "");
			}
			 return new ModelAndView("error", "command", "");
		}
		return new ModelAndView("index", "command", "");
	}
	return new ModelAndView("index", "command", "");
}
	
	@GetMapping(value="/expenseReimbursements")
	public ModelAndView expenseReimbursements(Model model) {
		String token = (String) session.getAttribute("hrms");
		Integer id  = (Integer) session.getAttribute("id");
		if(token!=null) {
			UserDetailsEntity obj = JwtTokenValidator.parseToken(token);
			if(obj!=null) {
				if(obj.getUser_role()==2 || obj.getUser_role()==1|| obj.getUser_role()==9) {
				model.addAttribute("name",obj.getName());
				model.addAttribute("org",obj.getOrgName());
				model.addAttribute("mobile",obj.getMobile());
				model.addAttribute("email",obj.getEmail());
				model.addAttribute("employerId",id);
				return new ModelAndView("expense-reimbursements", "command", "");
			}
			 return new ModelAndView("error", "command", "");
		}
		return new ModelAndView("index", "command", "");
	}
	return new ModelAndView("index", "command", "");
}
	@GetMapping(value="/expenseReimbursementsCompany")
	public ModelAndView expenseReimbursementsCompany(Model model) {
		String token = (String) session.getAttribute("hrms");
		Integer id  = (Integer) session.getAttribute("id");
		if(token!=null) {
			UserDetailsEntity obj = JwtTokenValidator.parseToken(token);
			if(obj!=null) {
				if(obj.getUser_role()==1 ) {
				model.addAttribute("name",obj.getName());
				model.addAttribute("org",obj.getOrgName());
				model.addAttribute("mobile",obj.getMobile());
				model.addAttribute("email",obj.getEmail());
				model.addAttribute("employerId",id);
				return new ModelAndView("expense-reimbursements-company", "command", "");
			}
			 return new ModelAndView("error", "command", "");
		}
		return new ModelAndView("index", "command", "");
	}
	return new ModelAndView("index", "command", "");
}
	
	@GetMapping(value="/employeeBand")
	public ModelAndView employeeBand(Model model) {
		String token = (String) session.getAttribute("hrms");
		Integer id  = (Integer) session.getAttribute("id");
		if(token!=null) {
			UserDetailsEntity obj = JwtTokenValidator.parseToken(token);
			if(obj!=null) {
				if(obj.getUser_role()==9 || obj.getUser_role()==1) {
				model.addAttribute("name",obj.getName());
				model.addAttribute("org",obj.getOrgName());
				model.addAttribute("mobile",obj.getMobile());
				model.addAttribute("email",obj.getEmail());
				model.addAttribute("employerId",id);
				return new ModelAndView("employee-bands", "command", "");
			}
			 return new ModelAndView("error", "command", "");
		}
		return new ModelAndView("index", "command", "");
	}
	return new ModelAndView("index", "command", "");
}
	

	@GetMapping(value="/employeeBandSetting")
	public ModelAndView employeeBandSetting(Model model) {
		String token = (String) session.getAttribute("hrms");
		Integer id  = (Integer) session.getAttribute("id");
		if(token!=null) {
			UserDetailsEntity obj = JwtTokenValidator.parseToken(token);
			if(obj!=null) {
				if(obj.getUser_role()==9) {
				model.addAttribute("name",obj.getName());
				model.addAttribute("org",obj.getOrgName());
				model.addAttribute("mobile",obj.getMobile());
				model.addAttribute("email",obj.getEmail());
				model.addAttribute("employerId",id);
				return new ModelAndView("employee-band-setting", "command", "");
			}
			 return new ModelAndView("error", "command", "");
		}
		return new ModelAndView("index", "command", "");
	}
	return new ModelAndView("index", "command", "");
}

	@GetMapping(value="/bulkVoucherIssuance")
	public ModelAndView bulkVoucherIssuance(Model model) {
		String token = (String) session.getAttribute("hrms");
		Integer id  = (Integer) session.getAttribute("id");
		if(token!=null) {
			UserDetailsEntity obj = JwtTokenValidator.parseToken(token);
			if(obj!=null) {
				if(obj.getUser_role()==9) {
				model.addAttribute("name",obj.getName());
				model.addAttribute("org",obj.getOrgName());
				model.addAttribute("mobile",obj.getMobile());
				model.addAttribute("email",obj.getEmail());
				model.addAttribute("employerId",id);
				return new ModelAndView("bulk-voucher-issuance", "command", "");
			}
			 return new ModelAndView("error", "command", "");
		}
		return new ModelAndView("index", "command", "");
	}
	return new ModelAndView("index", "command", "");
}
	@GetMapping(value="/expenseReimbursementsNew")
	public ModelAndView expenseReimbursementsNew(Model model) {
		String token = (String) session.getAttribute("hrms");
		Integer id  = (Integer) session.getAttribute("id");
		if(token!=null) {
			UserDetailsEntity obj = JwtTokenValidator.parseToken(token);
			if(obj!=null) {
				if(obj.getUser_role()==1) {
				model.addAttribute("name",obj.getName());
				model.addAttribute("org",obj.getOrgName());
				model.addAttribute("mobile",obj.getMobile());
				model.addAttribute("email",obj.getEmail());
				model.addAttribute("employerId",id);
				return new ModelAndView("expense-reimbursements-new", "command", "");
			}
			 return new ModelAndView("error", "command", "");
		}
		return new ModelAndView("index", "command", "");
	}
	return new ModelAndView("index", "command", "");
}
	@GetMapping(value="/displaybankMaster")
	public ModelAndView displaybankMaster(Model model) {
		String token = (String) session.getAttribute("hrms");
		Integer id  = (Integer) session.getAttribute("id");
		if(token!=null) {
			UserDetailsEntity obj = JwtTokenValidator.parseToken(token);
			if(obj!=null) {
				if(obj.getUser_role()==9 || obj.getUser_role()==1) {
				model.addAttribute("name",obj.getName());
				model.addAttribute("org",obj.getOrgName());
				model.addAttribute("mobile",obj.getMobile());
				model.addAttribute("email",obj.getEmail());
				model.addAttribute("employerId",id);

				return new ModelAndView("display-bank-master", "command", "");
			}
			 return new ModelAndView("error", "command", "");
		}
		return new ModelAndView("index", "command", "");
	}
	return new ModelAndView("index", "command", "");
}
 
	@GetMapping(value="/displayvoucherMaster")
	public ModelAndView displayvoucherMaster(Model model) {
		String token = (String) session.getAttribute("hrms");
		Integer id  = (Integer) session.getAttribute("id");
		if(token!=null) {
			UserDetailsEntity obj = JwtTokenValidator.parseToken(token);
			if(obj!=null ) {
				if(obj.getUser_role()==9 || obj.getUser_role()==1) {
				model.addAttribute("name",obj.getName());
				model.addAttribute("org",obj.getOrgName());
				model.addAttribute("mobile",obj.getMobile());
				model.addAttribute("email",obj.getEmail());
				model.addAttribute("employerId",id);

				return new ModelAndView("display-voucher-Master", "command", "");
		     	}
			 return new ModelAndView("error", "command", "");
			}
			 return new ModelAndView("index", "command", "");
		}
		return new ModelAndView("index", "command", "");
	}
	@GetMapping(value="/bankMaster")
	public ModelAndView bankMaster(Model model) {
		String token = (String) session.getAttribute("hrms");
		Integer id  = (Integer) session.getAttribute("id");
		if(token!=null) {
			UserDetailsEntity obj = JwtTokenValidator.parseToken(token);
			if(obj!=null) {
				if(obj.getUser_role()==9 || obj.getUser_role()==1) {
				model.addAttribute("name",obj.getName());
				model.addAttribute("org",obj.getOrgName());
				model.addAttribute("mobile",obj.getMobile());
				model.addAttribute("email",obj.getEmail());
				model.addAttribute("employerId",id);

				return new ModelAndView("bank-master", "command", "");
			}
			 return new ModelAndView("error", "command", "");
		}
		return new ModelAndView("index", "command", "");
	}
	return new ModelAndView("index", "command", "");
}
	
	@GetMapping(value="/voucherTypeMaster")
	public ModelAndView voucherTypeMaster(Model model) {
		String token = (String) session.getAttribute("hrms");
		Integer id  = (Integer) session.getAttribute("id");
		if(token!=null) {
			UserDetailsEntity obj = JwtTokenValidator.parseToken(token);
			if(obj!=null) {
				if(obj.getUser_role()==9) {
				model.addAttribute("name",obj.getName());
				model.addAttribute("org",obj.getOrgName());
				model.addAttribute("mobile",obj.getMobile());
				model.addAttribute("email",obj.getEmail());
				model.addAttribute("employerId",id);

				return new ModelAndView("voucher-Type-Master", "command", "");
			}
			 return new ModelAndView("error", "command", "");
		}
		return new ModelAndView("index", "command", "");
	}
	return new ModelAndView("index", "command", "");
}
	
	@PostMapping("/editBankMaster")
	public String editBankMaster(@RequestParam int id,
            @RequestParam String bankName,
            @RequestParam String bankCode,
            @RequestParam int status,
			 @RequestParam String bankLogo,
           
            Model model) {
	    //Pass data to the edit page
	    model.addAttribute("bankId", id);
	    model.addAttribute("bankName", bankName);
	    model.addAttribute("bankCode", bankCode);
	    model.addAttribute("status", status);
	    model.addAttribute("bankLogo", bankLogo);
	   
	    return "edit-Bank-Master"; // This refers to the Thymeleaf template for editing
	}
	@GetMapping(value="/managerMaster")
	public ModelAndView managerMaster(Model model) {
		String token = (String) session.getAttribute("hrms");
		Integer id  = (Integer) session.getAttribute("id");
		if(token!=null) {
			UserDetailsEntity obj = JwtTokenValidator.parseToken(token);
			if(obj!=null) {
				if(obj.getUser_role()==9) {
				model.addAttribute("name",obj.getName());
				model.addAttribute("org",obj.getOrgName());
				model.addAttribute("mobile",obj.getMobile());
				model.addAttribute("email",obj.getEmail());
				model.addAttribute("employerId",id);
				return new ModelAndView("manager-Master", "command", "");
			}
			 return new ModelAndView("error", "command", "");
		}
		return new ModelAndView("index", "command", "");
	}
	return new ModelAndView("index", "command", "");
}
	
	@GetMapping(value="/displaymanagerMaster")
	public ModelAndView displaymanagerMaster(Model model) {
		String token = (String) session.getAttribute("hrms");
		Integer id  = (Integer) session.getAttribute("id");
		if(token!=null) {
			UserDetailsEntity obj = JwtTokenValidator.parseToken(token);
			if(obj!=null) {
				if(obj.getUser_role()==9) {
				model.addAttribute("name",obj.getName());
				model.addAttribute("org",obj.getOrgName());
				model.addAttribute("mobile",obj.getMobile());
				model.addAttribute("email",obj.getEmail());
				model.addAttribute("employerId",id);
				return new ModelAndView("display-manager-Master", "command", "");
			}
			 return new ModelAndView("error", "command", "");
		}
		return new ModelAndView("index", "command", "");
	}
	return new ModelAndView("index", "command", "");
}
	
	@GetMapping(value="/jobTitlemaster")
	public ModelAndView jobTitlemaster(Model model) {
		String token = (String) session.getAttribute("hrms");
		Integer id  = (Integer) session.getAttribute("id");
		if(token!=null) {
			UserDetailsEntity obj = JwtTokenValidator.parseToken(token);
			if(obj!=null) {
				if(obj.getUser_role()==9) {
				model.addAttribute("name",obj.getName());
				model.addAttribute("org",obj.getOrgName());
				model.addAttribute("mobile",obj.getMobile());
				model.addAttribute("email",obj.getEmail());
				model.addAttribute("employerId",id);
				return new ModelAndView("job-Title-master", "command", "");
			}
			 return new ModelAndView("error", "command", "");
		}
		return new ModelAndView("index", "command", "");
	}
	return new ModelAndView("index", "command", "");
}
	
	@GetMapping(value="/displayjobTitlemaster")
	public ModelAndView displayjobTitlemaster(Model model) {
		String token = (String) session.getAttribute("hrms");
		Integer id  = (Integer) session.getAttribute("id");
		if(token!=null) {
			UserDetailsEntity obj = JwtTokenValidator.parseToken(token);
			if(obj!=null) {
				if(obj.getUser_role()==9) {
				model.addAttribute("name",obj.getName());
				model.addAttribute("org",obj.getOrgName());
				model.addAttribute("mobile",obj.getMobile());
				model.addAttribute("email",obj.getEmail());
				model.addAttribute("employerId",id);
				return new ModelAndView("display-jobTitlemaster", "command", "");
			}
			 return new ModelAndView("error", "command", "");
		}
		return new ModelAndView("index", "command", "");
	}
	return new ModelAndView("index", "command", "");
}
	
	@PostMapping("/editmanagerMaster")
	public String editmanagerMaster(@RequestParam int id,
            @RequestParam String managerLblDesc,
            @RequestParam String createdBy,
            @RequestParam String orgId,
			 @RequestParam String remarks,
           
            Model model) {
	    //Pass data to the edit page
		model.addAttribute("id", id);
	    model.addAttribute("managerLblDesc", managerLblDesc);
	    model.addAttribute("createdBy", createdBy);
	    model.addAttribute("orgId", orgId);
	    model.addAttribute("remarks", remarks);
	   
	    return "edit-manager-Master"; // This refers to the Thymeleaf template for editing
	}
	
	@PostMapping("/editjobTitlemaster")
	public String editjobTitleMaster(@RequestParam int id,
            @RequestParam int managerLblId,
            @RequestParam String jobDisc,
            @RequestParam String createdBy,
            @RequestParam String orgId,
			 @RequestParam String remarks,
           
            Model model) {
	    //Pass data to the edit page
		model.addAttribute("id", id);
		model.addAttribute("jobDisc",jobDisc);
	    model.addAttribute("managerLblId", managerLblId);
	    model.addAttribute("createdBy", createdBy);
	    model.addAttribute("orgId", orgId);
	    model.addAttribute("remarks", remarks);
	   
	    return "edit-jobTitlemaster"; // This refers to the Thymeleaf template for editing
	}
	
	@GetMapping(value="/linkBankDetail")
	public ModelAndView linkBankDetail(Model model) {
		String token = (String) session.getAttribute("hrms");
		Integer id  = (Integer) session.getAttribute("id");
		if(token!=null) {
			UserDetailsEntity obj = JwtTokenValidator.parseToken(token);
			if(obj!=null) {
				if(obj.getUser_role()==9 || obj.getUser_role()==1) {
				model.addAttribute("name",obj.getName());
				model.addAttribute("org",obj.getOrgName());
				model.addAttribute("mobile",obj.getMobile());
				model.addAttribute("email",obj.getEmail());
				model.addAttribute("employerId",id);
				return new ModelAndView("link-bank-account", "command", "");
			}
			 return new ModelAndView("error", "command", "");
		}
		return new ModelAndView("index", "command", "");
	}
	return new ModelAndView("index", "command", "");
}
	@GetMapping(value="/error")
	public ModelAndView error(Model model) {
		String token = (String) session.getAttribute("hrms");
		Integer id  = (Integer) session.getAttribute("id");
		if(token!=null) {
			UserDetailsEntity obj = JwtTokenValidator.parseToken(token);
			if(obj!=null) {
				if(obj.getUser_role()==9) {
				model.addAttribute("name",obj.getName());
				model.addAttribute("org",obj.getOrgName());
				model.addAttribute("mobile",obj.getMobile());
				model.addAttribute("email",obj.getEmail());
				model.addAttribute("employerId",id);
				return new ModelAndView("error", "command", "");
			}
			 return new ModelAndView("error", "command", "");
		}
		return new ModelAndView("index", "command", "");
	}
	return new ModelAndView("index", "command", "");
}
	
	@GetMapping(value="/roleAccess")
	public ModelAndView roleAccess(Model model) {
		String token = (String) session.getAttribute("hrms");
		Integer id  = (Integer) session.getAttribute("id");
		if(token!=null) {
			UserDetailsEntity obj = JwtTokenValidator.parseToken(token);
			if(obj!=null) {
				if(obj.getUser_role()==9 || obj.getUser_role()==1) {
				model.addAttribute("name",obj.getName());
				model.addAttribute("org",obj.getOrgName());
				model.addAttribute("mobile",obj.getMobile());
				model.addAttribute("email",obj.getEmail());
				model.addAttribute("employerId",id);
				return new ModelAndView("role-access", "command", "");
			}
			 return new ModelAndView("error", "command", "");
		}
		return new ModelAndView("index", "command", "");
	}
	return new ModelAndView("index", "command", "");
}
	
	@GetMapping(value="/userVoucheCreation")
	public ModelAndView userVoucheCreationr(Model model) {
		String token = (String) session.getAttribute("hrms");
		Integer id  = (Integer) session.getAttribute("id");
		if(token!=null) {
			UserDetailsEntity obj = JwtTokenValidator.parseToken(token);
			if(obj!=null) {
				if(obj.getUser_role()==9) {
				model.addAttribute("name",obj.getName());
				model.addAttribute("org",obj.getOrgName());
				model.addAttribute("mobile",obj.getMobile());
				model.addAttribute("email",obj.getEmail());
				model.addAttribute("employerId",id);
				return new ModelAndView("voucher-creation", "command", "");
			}
			 return new ModelAndView("error", "command", "");
		}
		return new ModelAndView("index", "command", "");
	}
	return new ModelAndView("index", "command", "");
}
	
	@GetMapping(value="/erupiDashboard")
	public ModelAndView erupiDashboard(Model model) {
		String token = (String) session.getAttribute("hrms");
		Integer id  = (Integer) session.getAttribute("id");
		if(token!=null) {
			UserDetailsEntity obj = JwtTokenValidator.parseToken(token);
			if(obj!=null) {
				if(obj.getUser_role()==9) {
				model.addAttribute("name",obj.getName());
				model.addAttribute("org",obj.getOrgName());
				model.addAttribute("mobile",obj.getMobile());
				model.addAttribute("email",obj.getEmail());
				model.addAttribute("employerId",id);
				return new ModelAndView("erupi-dashboard", "command", "");
			}
			 return new ModelAndView("error", "command", "");
		}
		return new ModelAndView("index", "command", "");
	}
	return new ModelAndView("index", "command", "");
}
	
	@GetMapping(value="/ProfileInfo")
	public ModelAndView ProfileInfo(Model model) {
		String token = (String) session.getAttribute("hrms");
		Integer id  = (Integer) session.getAttribute("id");
		Integer role_id  = (Integer) session.getAttribute("user_role");
		if(token!=null) {
			UserDetailsEntity obj = JwtTokenValidator.parseToken(token);
			if(obj!=null) {
				if(obj.getUser_role()==9 || obj.getUser_role()==1 || obj.getUser_role()==2) {
				model.addAttribute("name",obj.getName());
				model.addAttribute("org",obj.getOrgName());
				model.addAttribute("mobile",obj.getMobile());
				model.addAttribute("email",obj.getEmail());
				model.addAttribute("employerId",id);
				return new ModelAndView("Profile-Info", "command", "");
			}
			 return new ModelAndView("error", "command", "");
		}
		return new ModelAndView("index", "command", "");
	}
	return new ModelAndView("index", "command", "");
}		
	@GetMapping(value="/upiVoucherIssuanceNew")
	public ModelAndView upiVoucherIssuanceNew(Model model) {
		String token = (String) session.getAttribute("hrms");
		Integer id  = (Integer) session.getAttribute("id");
		if(token!=null) {
			UserDetailsEntity obj = JwtTokenValidator.parseToken(token);
			if(obj!=null) {
				if(obj.getUser_role()==9) {
				model.addAttribute("name",obj.getName());
				model.addAttribute("org",obj.getOrgName());
				model.addAttribute("mobile",obj.getMobile());
				model.addAttribute("email",obj.getEmail());
				model.addAttribute("employerId",id);
				return new ModelAndView("upi-voucher-issuance-new", "command", "");
			   }
			 return new ModelAndView("error", "command", "");
			}
			return new ModelAndView("index", "command", "");
		}
		return new ModelAndView("index", "command", "");
	}
	/// new voucher design 
	@GetMapping(value="/upiVoucherIssuanceManually")
	public ModelAndView upiVoucherIssuanceManually(Model model) {
		String token = (String) session.getAttribute("hrms");
		Integer id  = (Integer) session.getAttribute("id");
		if(token!=null) {
			UserDetailsEntity obj = JwtTokenValidator.parseToken(token);
			if(obj!=null) {
				if(obj.getUser_role()==9) {
				model.addAttribute("name",obj.getName());
				model.addAttribute("org",obj.getOrgName());
				model.addAttribute("mobile",obj.getMobile());
				model.addAttribute("email",obj.getEmail());
				model.addAttribute("employerId",id);
				return new ModelAndView("create-upi-voucher-issue-manually", "command", "");
				}
				 return new ModelAndView("error", "command", "");
			}
			return new ModelAndView("index", "command", "");
		}
		return new ModelAndView("index", "command", "");
	}
	
	/// new voucher design 
		@GetMapping(value="/issueWithBulkUploadVoucher")
		public ModelAndView issueWithBulkUploadVoucher(Model model) {
			String token = (String) session.getAttribute("hrms");
			Integer id  = (Integer) session.getAttribute("id");
			if(token!=null) {
				UserDetailsEntity obj = JwtTokenValidator.parseToken(token);
				if(obj!=null) {
					if(obj.getUser_role()==9) {
					model.addAttribute("name",obj.getName());
					model.addAttribute("org",obj.getOrgName());
					model.addAttribute("mobile",obj.getMobile());
					model.addAttribute("email",obj.getEmail());
					model.addAttribute("employerId",id);
					return new ModelAndView("Issue-with-bulk-upload-vouchers", "command", "");
				}
				 return new ModelAndView("error", "command", "");
			}
			return new ModelAndView("index", "command", "");
		}
		return new ModelAndView("index", "command", "");
	}

		@GetMapping("/editVoucherMaster")
		public ModelAndView editVoucherMaster(@RequestParam int vid,@RequestParam int status,
				Model model) {
			String token = (String) session.getAttribute("hrms");
			Integer id  = (Integer) session.getAttribute("id");
			if(token!=null) {
				UserDetailsEntity obj = JwtTokenValidator.parseToken(token);
				if(obj!=null) {
		    //Pass data to the edit page
		   model.addAttribute("vid", vid);
		   model.addAttribute("status", status);
		    return new ModelAndView("edit-Voucher-Master", "command", ""); // This refers to the Thymeleaf template for editing
		}
	}
	return new ModelAndView("index", "command", "");
}
		@GetMapping(value="/settingOrganizationAccount")
		public ModelAndView settingOrganizationAccount(Model model) {
			String token = (String) session.getAttribute("hrms");
			Integer id  = (Integer) session.getAttribute("id");
			Integer role_id  = (Integer) session.getAttribute("user_role");
			if(token!=null) {
				UserDetailsEntity obj = JwtTokenValidator.parseToken(token);
				if(obj!=null) {
					if(obj.getUser_role()==9 || obj.getUser_role()==1 || obj.getUser_role()==2) {
					model.addAttribute("name",obj.getName());
					model.addAttribute("org",obj.getOrgName());
					model.addAttribute("mobile",obj.getMobile());
					model.addAttribute("email",obj.getEmail());
					model.addAttribute("employerId",id);
					return new ModelAndView("setting-organization-account", "command", "");
				}
				 return new ModelAndView("error", "command", "");
			}
			return new ModelAndView("index", "command", "");
		}
		return new ModelAndView("index", "command", "");
	}
		
		@GetMapping(value="/waitList")
		public ModelAndView cotodelWaitlist(Model model) {
			return new ModelAndView("cotodel-Waitlist", "command", "");
		}	
		
		@GetMapping(value="/termsConditions")
		public ModelAndView termsConditions(Model model) {
			return new ModelAndView("Termsconditions", "command", "");
		}

		@GetMapping(value="/refundPolicy")
		public ModelAndView returnPolicy(Model model) {
			return new ModelAndView("refund-policy", "command", "");
		}

}
