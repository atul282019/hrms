package com.cotodel.hrms.web.controller;

import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.cotodel.hrms.web.jwt.util.JwtTokenGenerator;
import com.cotodel.hrms.web.properties.ApplicationConstantConfig;
import com.cotodel.hrms.web.response.ReputeCompanyDetails;
import com.cotodel.hrms.web.response.ReputeEmployeeSingleRequest;
import com.cotodel.hrms.web.response.ReputeTokenRequest;
import com.cotodel.hrms.web.response.ReputeUserDetailRequest;
import com.cotodel.hrms.web.response.ReputeUserRequest;
import com.cotodel.hrms.web.response.UserDetailsEntity;
import com.cotodel.hrms.web.service.ReputeService;
import com.cotodel.hrms.web.service.SingleUserCreationService;
import com.cotodel.hrms.web.service.Impl.TokenGenerationImpl;
import com.cotodel.hrms.web.util.CommonUtility;
import com.cotodel.hrms.web.util.EncriptResponse;
import com.cotodel.hrms.web.util.EncryptionDecriptionUtil;
import com.cotodel.hrms.web.util.JwtTokenValidator;
import com.cotodel.hrms.web.util.MessageConstant;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@CrossOrigin
public class StaticPageController extends CotoDelBaseController{

	private static final Logger logger = LoggerFactory.getLogger(StaticPageController.class);
	
	@Autowired
	ApplicationConstantConfig applicationConstantConfig;
	
	@Autowired
	SingleUserCreationService usercreationService;
	
	@Autowired
	TokenGenerationImpl tokengeneration;
	
	@Autowired
	ReputeService reputeService;
	
	
	@GetMapping(value="/")
	public String firstPage(Model model) {
		 return "redirect:/home";
	}	
	@GetMapping(value="/home")
	public ModelAndView home(Model model) {
		return new ModelAndView("home", "command", "");
	}	
	@GetMapping(value="/solutions")
	public ModelAndView soultions(Model model) {
		return new ModelAndView("solutions", "command", "");
	}	
	@GetMapping(value="/about")
	public ModelAndView about(Model model) {
		return new ModelAndView("about", "command", "");
	}	
	@GetMapping(value="/faqs")
	public ModelAndView faqs(Model model) {
		return new ModelAndView("faqs", "command", "");
	}	
	@GetMapping(value="/signin")
	public ModelAndView directSignin(Model model) {
		return new ModelAndView("signin", "command", "");
	}	
	@GetMapping(value="/privacypolicy")
	public ModelAndView policy(Model model) {
		return new ModelAndView("privacy-policy", "command", "");
	}	
	@GetMapping(value="/contact")
	public ModelAndView firstContact(Model model) {
		return new ModelAndView("firstContact", "command", "");
	}	
	@GetMapping(value="/waitlist")
	public ModelAndView secondContact(Model model) {
		return new ModelAndView("secondContact", "command", "");
	}
	
	@GetMapping(value="/solutions/fleetexpensemanagement")
	public ModelAndView newTransportlogistics(Model model) {
		return new ModelAndView("newTransportLogistics", "command", "");
	} 
	@GetMapping(value="/solutions/fleetexpensemanagement/upivoucher")
	public ModelAndView experienceupivoucher(Model model) {
		return new ModelAndView("experience-upi-voucher", "command", "");
	}
	@GetMapping(value="/solutions/corporateexpensemanagement/upivoucher")
	public ModelAndView experienceupivoucher1(Model model) {
		return new ModelAndView("experience-upi-voucher01", "command", "");
	}
	@GetMapping(value="/blogs/erupi")
	public ModelAndView whatisErupiblog1(Model model) {
		return new ModelAndView("whatisErupi-blog1", "command", "");
	}
	
	@GetMapping(value="/blogs")
	public ModelAndView blogspage(Model model) {
		return new ModelAndView("blogspage", "command", "");
	}
	@GetMapping(value="/nointernet")
	public ModelAndView nointernet(Model model) {
		return new ModelAndView("nointernet", "command", "");
	}
	@GetMapping(value="/blogs/upilite")
	public ModelAndView whatisupiLiteblog2(Model model) {
		return new ModelAndView("whatisupiLite-blog2", "command", "");
	}
	@GetMapping(value="/blogs/upi123pay")
	public ModelAndView upi123payblog3(Model model) {
		return new ModelAndView("upi123pay-blog3", "command", "");
	}
	
	@GetMapping(value="/blogs/upicircle")
	public ModelAndView upicircleblog4(Model model) {
		return new ModelAndView("upicircle-blog4", "command", "");
	}
	@GetMapping(value="/blogs/fleetmanagement")
	public ModelAndView fleetmanagement(Model model) {
		return new ModelAndView("fleetmgmt-blog5", "command", "");
	}
	@GetMapping(value="/solutions/corporateexpensemanagement")
	public ModelAndView expenseMgmtForCorporates(Model model) {
		return new ModelAndView("expenseMgmtForCorporates", "command", "");
	}
	@GetMapping(value="/login")
	public String loginPage(Model model,@RequestParam(defaultValue = "") String code,
            @RequestParam(defaultValue = "") String vault_url,
            @RequestParam(defaultValue = "") String hrms_id,
            @RequestParam(defaultValue = "") String hrms_name,
            @RequestParam(defaultValue = "") String hrms_logo_url,
            @RequestParam(defaultValue = "") String company_id,
            @RequestParam(defaultValue = "") String role) {
		logger.info("opening login Page");
		String screenName="index";
		Date currentDate = new Date();
		  // Define the desired date format
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss E d MMM yyyy");
        // Format the date
        String formattedDate = formatter.format(currentDate);
        
        // Print the formatted date
        System.out.println(formattedDate);
		if(code != null && !code.isEmpty() && code != "") {
		
		model.addAttribute("reputeUser","reputeUser");
		session.setAttribute("reputeUser", "reputeUser");
		ReputeTokenRequest repute=CommonUtility.getReputeToken(code, vault_url, applicationConstantConfig.tokenRedirectUrl);
		
	    String[] jwtParts = repute.getIdToken().split("\\.");
        String payload = jwtParts[1];  // This is the middle part (payload)
        Integer orgid=null;
        
        // Decode the payload from Base64
        String value=new String(Base64.getDecoder().decode(payload));
        System.out.println("value: " + value);
        // Print the access token
       
        ReputeCompanyDetails reputeCompanyDetails=new ReputeCompanyDetails();
        reputeCompanyDetails=parseJson(value);
		//logger.info("opening login email ::"+reputeCompanyDetails.getEmail());
		//logger.info("opening login phone no ::"+reputeCompanyDetails.getPhoneNumber());
		
		String profileRes=null;
		JSONObject profileJsonRes=null;
		String profileResIdtoken=null;
		JSONObject profileJsonResIdtoken=null;
		ReputeUserDetailRequest reputeUserForm = new ReputeUserDetailRequest();
		try {
			
			//logger.info("opening login email 1 ::"+reputeCompanyDetails.getEmail());
			reputeUserForm.setEmail(reputeCompanyDetails.getEmail());
			reputeUserForm.setUsername(hrms_name);
		
		    String mobileNumber= reputeCompanyDetails.getPhoneNumber();
            String mobile1 = (mobileNumber.startsWith("0")) ? mobileNumber.substring(1) : mobileNumber;
            reputeUserForm.setMobile(mobile1);
        
            reputeUserForm.setHrmsId(hrms_id);
            reputeUserForm.setCompanyId(company_id);
            reputeUserForm.setHrmsName(hrms_name);
            reputeUserForm.setRole(role);
            reputeUserForm.setEmployeeName(reputeCompanyDetails.getEmployeeName());
            reputeUserForm.setEmployeeId(reputeCompanyDetails.getEmployeeId());
	        ReputeUserRequest userRequest = new ReputeUserRequest();
	        userRequest.setMobile(mobile1);
	        repute.setMobile(mobile1);
	        repute.setVault_url(vault_url);
	        repute.setCompany_id(company_id);
	        repute.setHrms_id(hrms_id);
	        repute.setHrms_name(hrms_name);
	        repute.setRole(role);
	        repute.setRole(role);
	       // repute.setEmployeeId(reputeCompanyDetails.getEmployeeId());
	        ReputeEmployeeSingleRequest reputeEmployeeSingleRequest = new ReputeEmployeeSingleRequest();
	         reputeEmployeeSingleRequest.setEmployeeId(reputeCompanyDetails.getEmployeeId());
	         reputeEmployeeSingleRequest.setAccessToken(repute.getAccessToken());
			 reputeEmployeeSingleRequest.setEndpoint(repute.getVault_url());
	        try {
				String json = EncryptionDecriptionUtil.convertToJson(reputeEmployeeSingleRequest);

				EncriptResponse jsonObject=EncryptionDecriptionUtil.encriptResponse(json, applicationConstantConfig.apiSignaturePublicPath);

				String encriptResponse =  reputeService.getReputeEmployeeDetailByEmployeeId(tokengeneration.getToken(), jsonObject);

	   
				EncriptResponse userReqEnc =EncryptionDecriptionUtil.convertFromJson(encriptResponse, EncriptResponse.class);

				profileRes =  EncryptionDecriptionUtil.decriptResponse(userReqEnc.getEncriptData(), userReqEnc.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
				
				profileJsonRes= new JSONObject(profileRes);
				if(profileJsonRes.getBoolean("status")) { 
					reputeUserForm.setManagerEmployeeId(profileJsonRes.getJSONObject("data").getString("managerEmployeeId"));
				}
	        } catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        
			 String json = EncryptionDecriptionUtil.convertToJson(reputeUserForm);
	         //2-json string data encript
	         EncriptResponse jsonObject=EncryptionDecriptionUtil.encriptResponse(json, applicationConstantConfig.apiSignaturePublicPath);
			
	         String encriptResponse = usercreationService.saveReputeUserDetailEncript(tokengeneration.getToken(),jsonObject);
	         //3-decript data convert to object            
	         EncriptResponse userReqEnc =EncryptionDecriptionUtil.convertFromJson(encriptResponse, EncriptResponse.class);
	         //4-object data to decript to json
	         profileRes=EncryptionDecriptionUtil.decriptResponse(userReqEnc.getEncriptData(), userReqEnc.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
	         
	         profileJsonRes= new JSONObject(profileRes);
	 		if(profileJsonRes.getBoolean("status")) { 
	 			 try {
 				//logger.info("opening login email 33::"+reputeCompanyDetails.getEmail());
 				//token.setAccessToken(repute.)
 				 String json2 = EncryptionDecriptionUtil.convertToJson(repute);
 				
 				EncriptResponse jsonObjectIdToken =EncryptionDecriptionUtil.encriptResponse(json2, applicationConstantConfig.apiSignaturePublicPath);
 				
				String encriptReputeTokenRequest = usercreationService.reputeRequestSave(tokengeneration.getToken(),jsonObjectIdToken);
				        
				 EncriptResponse userencriptReputeTokenRequest =EncryptionDecriptionUtil.convertFromJson(encriptReputeTokenRequest, EncriptResponse.class);
				
				 profileResIdtoken=EncryptionDecriptionUtil.decriptResponse(userencriptReputeTokenRequest.getEncriptData(), userencriptReputeTokenRequest.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
				
				 profileJsonResIdtoken= new JSONObject(profileResIdtoken);
				 if(profileJsonResIdtoken.getBoolean("status")) {
					 
					 //logger.info("opening login email 66 ::"+reputeCompanyDetails.getEmail());
					 session.setAttribute("reputeAccessToken", repute.getAccessToken());
					 session.setAttribute("endpoint", repute.getVault_url());
					 //String  userResponse = usercreationService.userDetailByMobileNo(tokengeneration.getToken(),userRequest);
					 String json3 = EncryptionDecriptionUtil.convertToJson(userRequest);
		 				
		 				EncriptResponse jsonObjectIdToken2 =EncryptionDecriptionUtil.encriptResponse(json3, applicationConstantConfig.apiSignaturePublicPath);
		 				
						String encriptReputeTokenRequest2 = usercreationService.userDetailByMobileNo(tokengeneration.getToken(),jsonObjectIdToken2);
						        
						 EncriptResponse userencriptReputeTokenRequest2 =EncryptionDecriptionUtil.convertFromJson(encriptReputeTokenRequest2, EncriptResponse.class);
						
						 profileResIdtoken=EncryptionDecriptionUtil.decriptResponse(userencriptReputeTokenRequest2.getEncriptData(), userencriptReputeTokenRequest2.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
						
						 profileJsonResIdtoken= new JSONObject(profileResIdtoken);
						
					 logger.info("user Detail by mobile no ::"+profileJsonResIdtoken);
					 if(profileJsonResIdtoken.getBoolean("status") && profileJsonResIdtoken.getJSONObject("userEntity").getInt("status")==1) { 
						  System.out.println("User stauts success");
						  
						 session.setAttribute("reputeAccessToken", repute.getAccessToken());
						 session.setAttribute("endpoint", repute.getVault_url());
						 
						 request.getSession(true).setAttribute("email",  profileJsonResIdtoken.getJSONObject("userEntity").getString("email"));									  
						 request.getSession(true).setAttribute("hrms",  profileJsonResIdtoken.getJSONObject("userEntity").getString("hrmsId"));
						 request.getSession(true).setAttribute("username", profileJsonResIdtoken.getJSONObject("userEntity").getString("hrmsName"));
						 request.getSession(true).setAttribute("user_role",   profileJsonResIdtoken.getJSONObject("userEntity").getInt("role_id"));
						 request.getSession(true).setAttribute("formattedDate",  formattedDate);
		                
//						 request.getSession(true).setAttribute("id", profileJsonResIdtoken.getJSONObject("userEntity").getString("email"));
//	     				 session.setAttribute("id", profileJsonResIdtoken.getJSONObject("userEntity").getString("email"));
//						 model.addAttribute("id",profileJsonResIdtoken.getJSONObject("userEntity").getString("email"));
//						
//						 request.getSession(true).setAttribute("id",profileJsonResIdtoken.getJSONObject("userEntity").getString("email"));
//						 session.setAttribute("id", profileJsonResIdtoken.getJSONObject("userEntity").getString("email"));
//						 model.addAttribute("id",profileJsonResIdtoken.getJSONObject("userEntity").getString("email"));
						
					     if(profileJsonResIdtoken.getJSONObject("userEntity").getInt("role_id") == 3) {
						   
//							request.getSession(true).setAttribute("id", profileJsonResIdtoken.getJSONObject("userEntity").getInt("id"));
//							session.setAttribute("id", profileJsonResIdtoken.getJSONObject("userEntity").getInt("id"));
//							model.addAttribute("id",profileJsonResIdtoken.getJSONObject("userEntity").getInt("id"));
//							
//							session.setAttribute("empId", profileJsonResIdtoken.getJSONObject("userEntity").getInt("id"));
//							model.addAttribute("empId",profileJsonResIdtoken.getJSONObject("userEntity").getInt("id"));
//							 orgid = profileJsonResIdtoken.getJSONObject("userEntity").getInt("id");
					    	 //new change
					    	 request.getSession(true).setAttribute("id", profileJsonResIdtoken.getJSONObject("userEntity").getInt("employerid"));
								session.setAttribute("id", profileJsonResIdtoken.getJSONObject("userEntity").getInt("employerid"));
								model.addAttribute("id",profileJsonResIdtoken.getJSONObject("userEntity").getInt("employerid"));
								
								session.setAttribute("empId", profileJsonResIdtoken.getJSONObject("userEntity").getInt("id"));
								model.addAttribute("empId",profileJsonResIdtoken.getJSONObject("userEntity").getInt("id"));
							    orgid = profileJsonResIdtoken.getJSONObject("userEntity").getInt("employerid");
						}// id and empid is same in 1,3,9 
						else {
							//id and emp is diiferent
							//"id":1538 // employeeid
							//"employerid":1439 //orgid
							request.getSession(true).setAttribute("id", profileJsonResIdtoken.getJSONObject("userEntity").getInt("employerid"));
							session.setAttribute("id", profileJsonResIdtoken.getJSONObject("userEntity").getInt("employerid"));
							model.addAttribute("id",profileJsonResIdtoken.getJSONObject("userEntity").getInt("employerid"));
							
							session.setAttribute("empId", profileJsonResIdtoken.getJSONObject("userEntity").getInt("id"));
							model.addAttribute("empId",profileJsonResIdtoken.getJSONObject("userEntity").getInt("id"));
						    orgid = profileJsonResIdtoken.getJSONObject("userEntity").getInt("employerid");
						}
					    session.setAttribute("mobile", profileJsonResIdtoken.getJSONObject("userEntity").getString("mobile"));
						session.setAttribute("email", profileJsonResIdtoken.getJSONObject("userEntity").getString("email"));
						session.setAttribute("username", profileJsonResIdtoken.getJSONObject("userEntity").getString("username"));
						
						String email = profileJsonResIdtoken.getJSONObject("userEntity").getString("email");
						String mobile = profileJsonResIdtoken.getJSONObject("userEntity").getString("mobile");
						String username = profileJsonResIdtoken.getJSONObject("userEntity").getString("username");
						Integer user_role = profileJsonResIdtoken.getJSONObject("userEntity").getInt("role_id");
					    String token	=	JwtTokenGenerator.generateToken(email,mobile,username,user_role,orgid, MessageConstant.SECRET);
						//return JSONUtil.setJSONResonse(MessageConstant.RESPONSE_SUCCESS, MessageConstant.TRUE, userRole,token);
					    request.getSession(true).setAttribute("hrms", token);
					   switch (String.valueOf(profileJsonResIdtoken.getJSONObject("userEntity").getInt("role_id"))) {	
						
						case "2":
							screenName="dashboard";
							model.addAttribute("name",profileJsonResIdtoken.getJSONObject("userEntity").getString("email"));
							break;
						case "3":
								screenName="dashboard";
							model.addAttribute("name",profileJsonResIdtoken.getJSONObject("userEntity").getString("email"));
							break;
						}
						return screenName;
					 }
				 
				 }
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
 	         
 		}
 		else if(!profileJsonRes.getBoolean("status") && profileJsonRes.getString("message").equals("User Already exist with this email or mobile number !!")) {
 			try {
 				logger.info("opening login email 55 ::"+reputeCompanyDetails.getEmail());
 				//token.setAccessToken(repute.)
 				 String json2 = EncryptionDecriptionUtil.convertToJson(repute);
 				
 				EncriptResponse jsonObjectIdToken =EncryptionDecriptionUtil.encriptResponse(json2, applicationConstantConfig.apiSignaturePublicPath);
 				
				String encriptReputeTokenRequest = usercreationService.reputeRequestSave(tokengeneration.getToken(),jsonObjectIdToken);
				        
				 EncriptResponse userencriptReputeTokenRequest =EncryptionDecriptionUtil.convertFromJson(encriptReputeTokenRequest, EncriptResponse.class);
				
				 profileResIdtoken=EncryptionDecriptionUtil.decriptResponse(userencriptReputeTokenRequest.getEncriptData(), userencriptReputeTokenRequest.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
				
				 profileJsonResIdtoken= new JSONObject(profileResIdtoken);
				 logger.info("opening login email 66 profileJsonResIdtoken ::"+profileJsonResIdtoken);
				 if(profileJsonResIdtoken.getBoolean("status")) { 
					 System.out.println("User Already exist");
					 logger.info("opening login email 66 ::"+reputeCompanyDetails.getEmail());
					 session.setAttribute("reputeAccessToken", repute.getAccessToken());
					 session.setAttribute("endpoint", repute.getVault_url());
					 //String  userResponse = usercreationService.userDetailByMobileNo(tokengeneration.getToken(),userRequest);
					 String json3 = EncryptionDecriptionUtil.convertToJson(userRequest);
		 				
		 				EncriptResponse jsonObjectIdToken2 =EncryptionDecriptionUtil.encriptResponse(json3, applicationConstantConfig.apiSignaturePublicPath);
		 				
						String encriptReputeTokenRequest2 = usercreationService.userDetailByMobileNo(tokengeneration.getToken(),jsonObjectIdToken2);
						        
						 EncriptResponse userencriptReputeTokenRequest2 =EncryptionDecriptionUtil.convertFromJson(encriptReputeTokenRequest2, EncriptResponse.class);
						
						 profileResIdtoken=EncryptionDecriptionUtil.decriptResponse(userencriptReputeTokenRequest2.getEncriptData(), userencriptReputeTokenRequest2.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
						
						 profileJsonResIdtoken= new JSONObject(profileResIdtoken);
						
					 logger.info("user Detail by mobile no ::"+profileJsonResIdtoken);
					 if(profileJsonResIdtoken.getBoolean("status")  && profileJsonResIdtoken.getJSONObject("userEntity").getInt("status")==1) { 
						
						 session.setAttribute("reputeAccessToken", repute.getAccessToken());
						 session.setAttribute("endpoint", repute.getVault_url());
						 
						 request.getSession(true).setAttribute("email",  profileJsonResIdtoken.getJSONObject("userEntity").getString("email"));									  
						 request.getSession(true).setAttribute("hrms",  profileJsonResIdtoken.getJSONObject("userEntity").getString("hrmsId"));
						 request.getSession(true).setAttribute("username", profileJsonResIdtoken.getJSONObject("userEntity").getString("hrmsName"));
						 request.getSession(true).setAttribute("user_role",   profileJsonResIdtoken.getJSONObject("userEntity").getInt("role_id"));
						 request.getSession(true).setAttribute("formattedDate",  formattedDate);
		               
//						 request.getSession(true).setAttribute("id", profileJsonResIdtoken.getJSONObject("userEntity").getString("email"));
//	     				 session.setAttribute("id", profileJsonResIdtoken.getJSONObject("userEntity").getString("email"));
//						 model.addAttribute("id",profileJsonResIdtoken.getJSONObject("userEntity").getString("email"));
//						
//						 request.getSession(true).setAttribute("id",profileJsonResIdtoken.getJSONObject("userEntity").getString("email"));
//						 session.setAttribute("id", profileJsonResIdtoken.getJSONObject("userEntity").getString("email"));
//						 model.addAttribute("id",profileJsonResIdtoken.getJSONObject("userEntity").getString("email"));
//						
					    if(profileJsonResIdtoken.getJSONObject("userEntity").getInt("role_id") == 3) {
						   
					    	request.getSession(true).setAttribute("id", profileJsonResIdtoken.getJSONObject("userEntity").getInt("employerid"));
							session.setAttribute("id", profileJsonResIdtoken.getJSONObject("userEntity").getInt("employerid"));
							model.addAttribute("id",profileJsonResIdtoken.getJSONObject("userEntity").getInt("employerid"));
							
							session.setAttribute("empId", profileJsonResIdtoken.getJSONObject("userEntity").getInt("id"));
							model.addAttribute("empId",profileJsonResIdtoken.getJSONObject("userEntity").getInt("id"));
						    orgid = profileJsonResIdtoken.getJSONObject("userEntity").getInt("employerid");
						    
						}// id and empid is same in 1,3,9 
						else {
							//id and emp is diiferent
							//"id":1538 // employeeid
							//"employerid":1439 //orgid
							request.getSession(true).setAttribute("id", profileJsonResIdtoken.getJSONObject("userEntity").getInt("employerid"));
							session.setAttribute("id", profileJsonResIdtoken.getJSONObject("userEntity").getInt("employerid"));
							model.addAttribute("id",profileJsonResIdtoken.getJSONObject("userEntity").getInt("employerid"));
							
							session.setAttribute("empId", profileJsonResIdtoken.getJSONObject("userEntity").getInt("id"));
							model.addAttribute("empId",profileJsonResIdtoken.getJSONObject("userEntity").getInt("id"));
						    orgid = profileJsonResIdtoken.getJSONObject("userEntity").getInt("employerid");
						}
					    session.setAttribute("mobile", profileJsonResIdtoken.getJSONObject("userEntity").getString("mobile"));
						session.setAttribute("email", profileJsonResIdtoken.getJSONObject("userEntity").getString("email"));
						session.setAttribute("username", profileJsonResIdtoken.getJSONObject("userEntity").getString("username"));
						
						String email = profileJsonResIdtoken.getJSONObject("userEntity").getString("email");
						String mobile = profileJsonResIdtoken.getJSONObject("userEntity").getString("mobile");
						String username = profileJsonResIdtoken.getJSONObject("userEntity").getString("username");
						Integer user_role = profileJsonResIdtoken.getJSONObject("userEntity").getInt("role_id");
					    String token	=	JwtTokenGenerator.generateToken(email,mobile,username,user_role,orgid, MessageConstant.SECRET);
						request.getSession(true).setAttribute("hrms", token);
					    switch (String.valueOf(profileJsonResIdtoken.getJSONObject("userEntity").getInt("role_id"))) {
						case "2":
							 System.out.println("User stauts success case2 ");
							screenName="dashboard";
							model.addAttribute("name",profileJsonResIdtoken.getJSONObject("userEntity").getString("email"));
							break;
						case "3":
							screenName="dashboard";
							model.addAttribute("name",profileJsonResIdtoken.getJSONObject("userEntity").getString("email"));
							break;
						}
						return screenName;
					 }
				 }
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
 		}
 		
		} catch (Exception e) {
			// TODO: handle exception
		}
		//logger.info("opening login Page::"+reputeCompanyDetails.getEmail());
		}
		String mobile  = (String) session.getAttribute("mobile");
		if(mobile!=null) {
			model.addAttribute("message","");
			model.addAttribute("mobile","");
			model.addAttribute("orderid","");
			return screenName;
		}
		return screenName;

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
	
	@GetMapping(value="/signup")
	public ModelAndView SignupPage(Model model) {
		return new ModelAndView("signup", "command", "");
	}	
	

	@GetMapping(value="/dashboard")
	public String dashboard(Model model) {
		logger.info("opening dashboardPage");
		String token = (String) session.getAttribute("hrms");
		Integer id  = (Integer) session.getAttribute("id");
		if(token!=null) {
			UserDetailsEntity obj = JwtTokenValidator.parseToken(token);
			if(obj!=null) {
				if(obj.getUser_role()==9 || obj.getUser_role()==1 || obj.getUser_role()==2
						|| obj.getUser_role()==3  || obj.getUser_role()==12) {

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
//		@GetMapping(value="/manageEmployeeContractor")
//		public ModelAndView manageEmployeeContractor(Model model) {
//			logger.info("opening manageEmployeeContractor");
//			//return new ModelAndView("bulk-emp-details", "command", "");
//			String token = (String) session.getAttribute("hrms");
//			Integer id  = (Integer) session.getAttribute("id");
//			if(token!=null) {
//				UserDetailsEntity obj = JwtTokenValidator.parseToken(token);
//				if(obj!=null) {
//					if(obj.getUser_role()==9) {
//					model.addAttribute("name",obj.getName());
//					model.addAttribute("org",obj.getOrgName());
//					model.addAttribute("mobile",obj.getMobile());
//					model.addAttribute("email",obj.getEmail());
//					model.addAttribute("employerId",id);
//					return new ModelAndView("employee-manage-and-contractors", "command", "");
//				}
//				 return new ModelAndView("error", "command", "");
//			}
//			return new ModelAndView("index", "command", "");
//		}
//		return new ModelAndView("index", "command", "");
//	}
		
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

	
	@GetMapping(value="/employeeProfile")
	public ModelAndView employeeOnboarding2(Model model) {
		String token = (String) session.getAttribute("hrms");
		Integer id  = (Integer) session.getAttribute("id");
		if(token!=null) {
			UserDetailsEntity obj = JwtTokenValidator.parseToken(token);
			if(obj!=null) {
				if(obj.getUser_role()==2) {
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
				if(obj.getUser_role()==1|| obj.getUser_role()==9 || obj.getUser_role()==3) {
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
				if(obj.getUser_role()==3 || obj.getUser_role()==2 || obj.getUser_role()==1|| obj.getUser_role()==9) {
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
//	@GetMapping(value="/expenseReimbursementsNew")
//	public ModelAndView expenseReimbursementsNew(Model model) {
//		String token = (String) session.getAttribute("hrms");
//		Integer id  = (Integer) session.getAttribute("id");
//		if(token!=null) {
//			UserDetailsEntity obj = JwtTokenValidator.parseToken(token);
//			if(obj!=null) {
//				if(obj.getUser_role()==1 || obj.getUser_role()==3 ) {
//				model.addAttribute("name",obj.getName());
//				model.addAttribute("org",obj.getOrgName());
//				model.addAttribute("mobile",obj.getMobile());
//				model.addAttribute("email",obj.getEmail());
//				model.addAttribute("employerId",id);
//				return new ModelAndView("expense-reimbursements-new", "command", "");
//			}
//			 return new ModelAndView("error", "command", "");
//		}
//		return new ModelAndView("index", "command", "");
//	}
//	return new ModelAndView("index", "command", "");
//}
	@GetMapping(value="/displaybankMaster")
	public ModelAndView displaybankMaster(Model model) {
		String token = (String) session.getAttribute("hrms");
		Integer id  = (Integer) session.getAttribute("id");
		if(token!=null) {
			UserDetailsEntity obj = JwtTokenValidator.parseToken(token);
			if(obj!=null) {
				if(obj.getUser_role()==9 || obj.getUser_role()==12) {
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
				if(obj.getUser_role()==12) {
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
				if(obj.getUser_role()==12) {
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
//	@GetMapping(value="/managerMaster")
//	public ModelAndView managerMaster(Model model) {
//		String token = (String) session.getAttribute("hrms");
//		Integer id  = (Integer) session.getAttribute("id");
//		if(token!=null) {
//			UserDetailsEntity obj = JwtTokenValidator.parseToken(token);
//			if(obj!=null) {
//				if(obj.getUser_role()==9) {
//				model.addAttribute("name",obj.getName());
//				model.addAttribute("org",obj.getOrgName());
//				model.addAttribute("mobile",obj.getMobile());
//				model.addAttribute("email",obj.getEmail());
//				model.addAttribute("employerId",id);
//				return new ModelAndView("manager-Master", "command", "");
//			}
//			 return new ModelAndView("error", "command", "");
//		}
//		return new ModelAndView("index", "command", "");
//	}
//	return new ModelAndView("index", "command", "");
//}
	
	/*@GetMapping(value="/displaymanagerMaster")
	public ModelAndView displaymanagerMaster(Model model) {
		String token = (String) session.getAttribute("hrms");
		Integer id  = (Integer) session.getAttribute("id");
		if(token!=null) {
			UserDetailsEntity obj = JwtTokenValidator.parseToken(token);
			if(obj!=null) {
				if(obj.getUser_role()==12 || obj.getUser_role()==9 ) {
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
}*/
	
//	@GetMapping(value="/displayjobTitlemaster")
//	public ModelAndView displayjobTitlemaster(Model model) {
//		String token = (String) session.getAttribute("hrms");
//		Integer id  = (Integer) session.getAttribute("id");
//		if(token!=null) {
//			UserDetailsEntity obj = JwtTokenValidator.parseToken(token);
//			if(obj!=null) {
//				if(obj.getUser_role()==12 ) {
//				model.addAttribute("name",obj.getName());
//				model.addAttribute("org",obj.getOrgName());
//				model.addAttribute("mobile",obj.getMobile());
//				model.addAttribute("email",obj.getEmail());
//				model.addAttribute("employerId",id);
//				return new ModelAndView("display-jobTitlemaster", "command", "");
//			}
//			 return new ModelAndView("error", "command", "");
//		}
//		return new ModelAndView("index", "command", "");
//	}
//	return new ModelAndView("index", "command", "");
//}
	
//	@PostMapping("/editmanagerMaster")
//	public String editmanagerMaster(@RequestParam int id,
//            @RequestParam String managerLblDesc,
//            @RequestParam String createdBy,
//            @RequestParam String orgId,
//			 @RequestParam String remarks,
//           
//            Model model) {
//	    //Pass data to the edit page
//		model.addAttribute("id", id);
//	    model.addAttribute("managerLblDesc", managerLblDesc);
//	    model.addAttribute("createdBy", createdBy);
//	    model.addAttribute("orgId", orgId);
//	    model.addAttribute("remarks", remarks);
//	   
//	    return "edit-manager-Master"; // This refers to the Thymeleaf template for editing
//	}
//	
//	@PostMapping("/editjobTitlemaster")
//	public String editjobTitleMaster(@RequestParam int id,
//            @RequestParam int managerLblId,
//            @RequestParam String jobDisc,
//            @RequestParam String createdBy,
//            @RequestParam String orgId,
//			 @RequestParam String remarks,
//           
//            Model model) {
//	    //Pass data to the edit page
//		model.addAttribute("id", id);
//		model.addAttribute("jobDisc",jobDisc);
//	    model.addAttribute("managerLblId", managerLblId);
//	    model.addAttribute("createdBy", createdBy);
//	    model.addAttribute("orgId", orgId);
//	    model.addAttribute("remarks", remarks);
//	   
//	    return "edit-jobTitlemaster"; // This refers to the Thymeleaf template for editing
//	}
	
//	@GetMapping(value="/linkBankDetail")
//	public ModelAndView linkBankDetail(Model model) {
//		String token = (String) session.getAttribute("hrms");
//		Integer id  = (Integer) session.getAttribute("id");
//		if(token!=null) {
//			UserDetailsEntity obj = JwtTokenValidator.parseToken(token);
//			if(obj!=null) {
//				if(obj.getUser_role()==9 || obj.getUser_role()==1) {
//				model.addAttribute("name",obj.getName());
//				model.addAttribute("org",obj.getOrgName());
//				model.addAttribute("mobile",obj.getMobile());
//				model.addAttribute("email",obj.getEmail());
//				model.addAttribute("employerId",id);
//				return new ModelAndView("link-bank-account", "command", "");
//			}
//			 return new ModelAndView("error", "command", "");
//		}
//		return new ModelAndView("index", "command", "");
//	}
//	return new ModelAndView("index", "command", "");
//}
	@GetMapping(value="/error")
	public ModelAndView error(Model model) {
		String token = (String) session.getAttribute("hrms");
		Integer id  = (Integer) session.getAttribute("id");
		if(token!=null) {
			UserDetailsEntity obj = JwtTokenValidator.parseToken(token);
			if(obj!=null) {
				if(obj.getUser_role()==9 || obj.getUser_role()==2 ||
						obj.getUser_role()==3 || obj.getUser_role()==5 || obj.getUser_role()== 6||
						obj.getUser_role()==1 || obj.getUser_role()==10) {
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
				if(obj.getUser_role()==9 || obj.getUser_role()==1 || obj.getUser_role()==3) {
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
				if(obj.getUser_role()==9 || obj.getUser_role()==1 || obj.getUser_role()==2 
						|| obj.getUser_role()==3 || obj.getUser_role()==10 || obj.getUser_role()==12) {
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
	
	@GetMapping(value="/reputeUpiVoucherIssuance")
	public ModelAndView reputeUpiVoucherIssuance(Model model) {
		String token = (String) session.getAttribute("hrms");
		String reputeAccessToken = (String) session.getAttribute("reputeAccessToken");
		Integer id  = (Integer) session.getAttribute("id");
		if(token!=null) {
			UserDetailsEntity obj = JwtTokenValidator.parseToken(token);
			if(obj!=null) {
				if(obj.getUser_role()==9 || obj.getUser_role()==3) {
				
				model.addAttribute("name",obj.getName());
				model.addAttribute("org",obj.getOrgName());
				model.addAttribute("mobile",obj.getMobile());
				model.addAttribute("email",obj.getEmail());
				model.addAttribute("employerId",id);
				return new ModelAndView("repute-upi-voucher-issuance", "command", "");
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
	
	@GetMapping(value="/reputeUpiVoucherIssuanceManually")
	public ModelAndView reputeUpiVoucherIssuanceManually(Model model) {
		String token = (String) session.getAttribute("hrms");
		Integer id  = (Integer) session.getAttribute("id");
		if(token!=null) {
			UserDetailsEntity obj = JwtTokenValidator.parseToken(token);
			if(obj!=null) {
				if(obj.getUser_role()==9 || obj.getUser_role()==3) {
				model.addAttribute("name",obj.getName());
				model.addAttribute("org",obj.getOrgName());
				model.addAttribute("mobile",obj.getMobile());
				model.addAttribute("email",obj.getEmail());
				model.addAttribute("employerId",id);
				return new ModelAndView("repute-upi-voucher-issue-manually", "command", "");
				}
				 return new ModelAndView("error", "command", "");
			}
			return new ModelAndView("index", "command", "");
		}
		return new ModelAndView("index", "command", "");
	}
	
	@GetMapping(value="/requestedUpiVoucherIssuance")
	public ModelAndView requestedUpiVoucherIssuance(Model model) {
		String token = (String) session.getAttribute("hrms");
		Integer id  = (Integer) session.getAttribute("id");
		if(token!=null) {
			UserDetailsEntity obj = JwtTokenValidator.parseToken(token);
			if(obj!=null) {
				if(obj.getUser_role()==9 || obj.getUser_role()==3) {
				model.addAttribute("name",obj.getName());
				model.addAttribute("org",obj.getOrgName());
				model.addAttribute("mobile",obj.getMobile());
				model.addAttribute("email",obj.getEmail());
				model.addAttribute("employerId",id);
				return new ModelAndView("requested-upi-voucher-issue", "command", "");
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
		
		@GetMapping(value="/termsconditions")
		public ModelAndView termsConditions(Model model) {
			return new ModelAndView("Termsconditions", "command", "");
		}

		@GetMapping(value="/refundpolicy")
		public ModelAndView returnPolicy(Model model) {
			return new ModelAndView("refund-policy", "command", "");
		}
		@GetMapping(value="/requestVoucher")
		public ModelAndView requestVoucher(Model model) {
			String token = (String) session.getAttribute("hrms");
			Integer id  = (Integer) session.getAttribute("id");
			if(token!=null) {
				UserDetailsEntity obj = JwtTokenValidator.parseToken(token);
				if(obj!=null) {
					if(obj.getUser_role()==2 || obj.getUser_role()==3) {
					model.addAttribute("name",obj.getName());
					model.addAttribute("org",obj.getOrgName());
					model.addAttribute("mobile",obj.getMobile());
					model.addAttribute("email",obj.getEmail());
					model.addAttribute("employerId",id);

					return new ModelAndView("requestVoucher", "command", "");
				} 
				 return new ModelAndView("error", "command", "");
			}
			return new ModelAndView("index", "command", "");
		}
		return new ModelAndView("index", "command", "");
	}
		@GetMapping(value="/requestedVoucherList")
		public ModelAndView requestedVoucherList(Model model) {
			String token = (String) session.getAttribute("hrms");
			Integer id  = (Integer) session.getAttribute("id");
			if(token!=null) {
				UserDetailsEntity obj = JwtTokenValidator.parseToken(token);
				if(obj!=null) {
					if(obj.getUser_role()==2) {
					model.addAttribute("name",obj.getName());
					model.addAttribute("org",obj.getOrgName());
					model.addAttribute("mobile",obj.getMobile());
					model.addAttribute("email",obj.getEmail());
					model.addAttribute("employerId",id);

					return new ModelAndView("requestedVoucherList", "command", "");
				}
				 return new ModelAndView("error", "command", "");
			}
			return new ModelAndView("index", "command", "");
		}
		return new ModelAndView("index", "command", "");
	}
		
		@GetMapping(value="/directorOnboarding")
		public ModelAndView directorOnboarding(Model model) {
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

					return new ModelAndView("directorOnboarding", "command", "");
				}
				 return new ModelAndView("error", "command", "");
			}
			return new ModelAndView("index", "command", "");
		}
		return new ModelAndView("index", "command", "");
	}
		
	public static ReputeCompanyDetails parseJson(String json) {
    	ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(json, ReputeCompanyDetails.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
	@GetMapping(value="/displayWaitlist")
	public ModelAndView displayWaitlist(Model model) {
		String token = (String) session.getAttribute("hrms");
		Integer id  = (Integer) session.getAttribute("id");
		if(token!=null) {
			UserDetailsEntity obj = JwtTokenValidator.parseToken(token);
			if(obj!=null) {
				if(obj.getUser_role()==12 || obj.getUser_role()==9) {
				model.addAttribute("name",obj.getName());
				model.addAttribute("org",obj.getOrgName());
				model.addAttribute("mobile",obj.getMobile());
				model.addAttribute("email",obj.getEmail());
				model.addAttribute("employerId",id);

				return new ModelAndView("displayWaitlist", "command", "");
			}
			 return new ModelAndView("error", "command", "");
		}
		return new ModelAndView("index", "command", "");
	}
	return new ModelAndView("index", "command", "");
}
	@GetMapping(value="/deactiveEmployee")
	public ModelAndView deactiveEmployee(Model model) {
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
				return new ModelAndView("employee-deactive", "command", "");
			}
			 return new ModelAndView("error", "command", "");
		}
		return new ModelAndView("index", "command", "");
	}
	return new ModelAndView("index", "command", "");
}
	
    @GetMapping(value = "/transport")
    public ModelAndView transportAndLogistics(Model model) {
        return new ModelAndView("transport", "command", "");
    }

    @GetMapping(value = "/retail")
    public ModelAndView retailAndTrade(Model model) {
        return new ModelAndView("retail", "command", "");
    }

    @GetMapping(value = "/consulting")
    public ModelAndView consultingAndAdvisory(Model model) {
        return new ModelAndView("consulting", "command", "");
    }
    
    @GetMapping(value="/payment")
	public ModelAndView payment(Model model) {
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
 
				return new ModelAndView("paynow", "command", "");
			}
			 return new ModelAndView("error", "command", "");
		}
		return new ModelAndView("index", "command", "");
	}
	return new ModelAndView("index", "command", "");
}

    @GetMapping(value="/cotodelApproval")
	public ModelAndView cotodelApproval(Model model) {
		String token = (String) session.getAttribute("hrms");
		Integer id  = (Integer) session.getAttribute("id");
		if(token!=null) {
			UserDetailsEntity obj = JwtTokenValidator.parseToken(token);
			if(obj!=null) {
				if(obj.getUser_role()==9 || obj.getUser_role()==12) {
				model.addAttribute("name",obj.getName());
				model.addAttribute("org",obj.getOrgName());
				model.addAttribute("mobile",obj.getMobile());
				model.addAttribute("email",obj.getEmail());
				model.addAttribute("employerId",id);
 
				return new ModelAndView("cotodelApproval", "command", "");
			}
			 return new ModelAndView("error", "command", "");
		}
		return new ModelAndView("index", "command", "");
	}
	return new ModelAndView("index", "command", "");
}
    
    @GetMapping(value="/editvehicle")
	public ModelAndView vehiclemanagement(Model model) {
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
 
				return new ModelAndView("vehicle-management", "command", "");
			}
			 return new ModelAndView("error", "command", "");
		}
		return new ModelAndView("index", "command", "");
	}
	return new ModelAndView("index", "command", "");
}
    @GetMapping(value="/bulkvehicleupload")
	public ModelAndView bulkvehicleupload(Model model) {
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
 
				return new ModelAndView("bulk-vehicleupload", "command", "");
			}
			 return new ModelAndView("error", "command", "");
		}
		return new ModelAndView("index", "command", "");
	}
	return new ModelAndView("index", "command", "");
}
    
    @GetMapping(value="/savebulkvehicle")
	public ModelAndView savebulkvehicle(Model model) {
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
 
				return new ModelAndView("save-bulkvehicle", "command", "");
			}
			 return new ModelAndView("error", "command", "");
		}
		return new ModelAndView("index", "command", "");
	}
	return new ModelAndView("index", "command", "");
}
    @GetMapping(value="/vehiclemanagement")
   	public ModelAndView editvehicle(Model model) {
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
    
   				return new ModelAndView("vehicleManagement", "command", "");
   			}
   			 return new ModelAndView("error", "command", "");
   		}
   		return new ModelAndView("index", "command", "");
   	}
   	return new ModelAndView("index", "command", "");
   }
}
