package com.cotodel.hrms.web.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cotodel.hrms.web.jwt.util.JwtTokenGenerator;
import com.cotodel.hrms.web.properties.ApplicationConstantConfig;
import com.cotodel.hrms.web.response.EmployeeProfileRequest;
import com.cotodel.hrms.web.response.LoginWithPwd;
import com.cotodel.hrms.web.response.ReputeTokenRequest;
import com.cotodel.hrms.web.response.UserDetailsEntity;
import com.cotodel.hrms.web.response.UserForm;
import com.cotodel.hrms.web.response.UserOtpRequest;
import com.cotodel.hrms.web.service.LoginService;
import com.cotodel.hrms.web.service.Impl.TokenGenerationImpl;
import com.cotodel.hrms.web.util.EncriptResponse;
import com.cotodel.hrms.web.util.EncryptionDecriptionUtil;
import com.cotodel.hrms.web.util.MessageConstant;

@Controller
@CrossOrigin
public class LoginController extends CotoDelBaseController{

	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

	@Autowired
	public ApplicationConstantConfig applicationConstantConfig;

	@Autowired
	LoginService loginservice;

	@Autowired
	TokenGenerationImpl tokengeneration;

	@PostMapping(value="/userLogin")
	public String validateLogin(HttpServletResponse response, HttpServletRequest request,
			@ModelAttribute("userForm") UserForm userForm, BindingResult result, HttpSession session, Model model,RedirectAttributes redirect) {
		
		
		String profileRes=null;JSONObject profileJsonRes=null;String screenName="index";
		String profileResRepute=null;
		Integer orgid=null;
		JSONObject profileResJsonRepute=null;
		String message =null; String otpmobile =null; String orderid=null;
		UserDetailsEntity obj =null;
		 Date currentDate = new Date();

	        // Define the desired date format
	        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss E d MMM yyyy");
	        // Format the date
	        String formattedDate = formatter.format(currentDate);

	        // Print the formatted date
	        System.out.println(formattedDate);
		try {
			String otp = null;

			otp= userForm.getPassword1()+userForm.getPassword2()+userForm.getPassword3()+userForm.getPassword4()+userForm.getPassword5()+userForm.getPassword6();

			   userForm.setOtp(otp);
				String json = EncryptionDecriptionUtil.convertToJson(userForm);

				EncriptResponse jsonObject=EncryptionDecriptionUtil.encriptResponse(json, applicationConstantConfig.apiSignaturePublicPath);

				String encriptResponse = loginservice.verifyOtp(tokengeneration.getToken(), jsonObject);


				EncriptResponse userReqEnc =EncryptionDecriptionUtil.convertFromJson(encriptResponse, EncriptResponse.class);

				profileRes =  EncryptionDecriptionUtil.decriptResponse(userReqEnc.getEncriptData(), userReqEnc.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);

			logger.info(profileRes);

			if(!ObjectUtils.isEmpty(profileRes)) {

				profileJsonRes= new JSONObject(profileRes);

				if(profileJsonRes.getBoolean("status") && profileJsonRes.getString("message").equalsIgnoreCase(MessageConstant.RESPONSE_SUCCESS)) {

					// repute token

					try {
						ReputeTokenRequest reputeTokenRequest = new ReputeTokenRequest();
						reputeTokenRequest.setMobile(userForm.getMobile());
						String jsonRepute = EncryptionDecriptionUtil.convertToJson(reputeTokenRequest);

						EncriptResponse jsonObjectRepute=EncryptionDecriptionUtil.encriptResponse(jsonRepute, applicationConstantConfig.apiSignaturePublicPath);

						String encriptResponseRepute = loginservice.getReputeToken(tokengeneration.getToken(), jsonObjectRepute);


						EncriptResponse userReqEncRepute =EncryptionDecriptionUtil.convertFromJson(encriptResponseRepute, EncriptResponse.class);

						profileResRepute =  EncryptionDecriptionUtil.decriptResponse(userReqEncRepute.getEncriptData(), userReqEncRepute.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);

						logger.info(profileResRepute);
						profileResJsonRepute= new JSONObject(profileResRepute);
						session.setAttribute("reputeAccessToken", profileResJsonRepute.getJSONObject("data").getString("accessToken"));
						session.setAttribute("endpoint", profileResJsonRepute.getJSONObject("data").getString("vault_url"));
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}


					//set token in session
					request.getSession(true).setAttribute("email", profileJsonRes.getJSONObject("data").getString("email"));
					request.getSession(true).setAttribute("hrms", profileJsonRes.getJSONObject("data").getString("mobile"));
					request.getSession(true).setAttribute("organizationName", profileJsonRes.getJSONObject("data").getString("organizationName"));
					request.getSession(true).setAttribute("username", profileJsonRes.getJSONObject("data").getString("username"));
					request.getSession(true).setAttribute("user_role",  profileJsonRes.getJSONObject("data").getInt("role_id"));
					request.getSession(true).setAttribute("formattedDate",  formattedDate);
					if(profileJsonRes.getJSONObject("data").getInt("role_id") == 1) {

//					request.getSession(true).setAttribute("id", profileJsonRes.getJSONObject("data").getInt("id"));
//					session.setAttribute("id", profileJsonRes.getJSONObject("data").getInt("id"));
//					model.addAttribute("id",profileJsonRes.getJSONObject("data").getInt("id"));
//
//					session.setAttribute("empId", profileJsonRes.getJSONObject("data").getInt("id"));
//					model.addAttribute("empId",profileJsonRes.getJSONObject("data").getInt("id"));
//					orgid = profileJsonRes.getJSONObject("data").getInt("id");

					//new changes
					request.getSession(true).setAttribute("id", profileJsonRes.getJSONObject("data").getInt("employerid"));
					session.setAttribute("id", profileJsonRes.getJSONObject("data").getInt("employerid"));
					model.addAttribute("id",profileJsonRes.getJSONObject("data").getInt("employerid"));

					session.setAttribute("empId", profileJsonRes.getJSONObject("data").getInt("id"));
					model.addAttribute("empId",profileJsonRes.getJSONObject("data").getInt("id"));
				    orgid = profileJsonRes.getJSONObject("data").getInt("employerid");
					}
					else if(profileJsonRes.getJSONObject("data").getInt("role_id") == 9) {
						//request.getSession(true).setAttribute("id",SHA256Hash.getSHA256Hash(String.valueOf(profileJsonRes.getJSONObject("data").getInt("id"))));

//						request.getSession(true).setAttribute("id", profileJsonRes.getJSONObject("data").getInt("id"));
//						session.setAttribute("id", profileJsonRes.getJSONObject("data").getInt("id"));
//						model.addAttribute("id",profileJsonRes.getJSONObject("data").getInt("id"));
//
//						session.setAttribute("empId", profileJsonRes.getJSONObject("data").getInt("id"));
//						model.addAttribute("empId",profileJsonRes.getJSONObject("data").getInt("id"));
//						 orgid = profileJsonRes.getJSONObject("data").getInt("id");

						//new changes
						request.getSession(true).setAttribute("id", profileJsonRes.getJSONObject("data").getInt("employerid"));
						session.setAttribute("id", profileJsonRes.getJSONObject("data").getInt("employerid"));
						model.addAttribute("id",profileJsonRes.getJSONObject("data").getInt("employerid"));

						session.setAttribute("empId", profileJsonRes.getJSONObject("data").getInt("id"));
						model.addAttribute("empId",profileJsonRes.getJSONObject("data").getInt("id"));
					    orgid = profileJsonRes.getJSONObject("data").getInt("employerid");

					}
					else if(profileJsonRes.getJSONObject("data").getInt("role_id") == 3) {
//						request.getSession(true).setAttribute("id", profileJsonRes.getJSONObject("data").getInt("id"));
//						session.setAttribute("id", profileJsonRes.getJSONObject("data").getInt("id"));
//						model.addAttribute("id",profileJsonRes.getJSONObject("data").getInt("id"));
//
//						session.setAttribute("empId", profileJsonRes.getJSONObject("data").getInt("id"));
//						model.addAttribute("empId",profileJsonRes.getJSONObject("data").getInt("id"));
//						 orgid = profileJsonRes.getJSONObject("data").getInt("id");

						//new changes
						request.getSession(true).setAttribute("id", profileJsonRes.getJSONObject("data").getInt("employerid"));
						session.setAttribute("id", profileJsonRes.getJSONObject("data").getInt("employerid"));
						model.addAttribute("id",profileJsonRes.getJSONObject("data").getInt("employerid"));

						session.setAttribute("empId", profileJsonRes.getJSONObject("data").getInt("id"));
						model.addAttribute("empId",profileJsonRes.getJSONObject("data").getInt("id"));
					    orgid = profileJsonRes.getJSONObject("data").getInt("employerid");
					}// id and empid is same in 1,3,9
					else {
						//id and emp is diiferent
//						"id":1538 // employeeid
//						"employerid":1439 //orgid
						request.getSession(true).setAttribute("id", profileJsonRes.getJSONObject("data").getInt("employerid"));
						session.setAttribute("id", profileJsonRes.getJSONObject("data").getInt("employerid"));
						model.addAttribute("id",profileJsonRes.getJSONObject("data").getInt("employerid"));

						session.setAttribute("empId", profileJsonRes.getJSONObject("data").getInt("id"));
						model.addAttribute("empId",profileJsonRes.getJSONObject("data").getInt("id"));
					    orgid = profileJsonRes.getJSONObject("data").getInt("employerid");
					}
					//request.getSession(true).setAttribute("cotodel", profileJsonRes.getString("token"));

					//obj =  JwtTokenValidator.parseToken(profileJsonRes.getString("token"));

					session.setAttribute("email", profileJsonRes.getJSONObject("data").getString("email"));
					session.setAttribute("mobile", profileJsonRes.getJSONObject("data").getString("mobile"));
					session.setAttribute("username", profileJsonRes.getJSONObject("data").getString("username"));
					session.setAttribute("organizationName", profileJsonRes.getJSONObject("data").getString("organizationName"));
					//check user is in database or not
					//userRes=loginService.checkUserExsistance(userForm);

					//logger.info("user login user entity "+userRes);

					//check user is active
					//check role is defined
					// check user is verified or not

						Map<String,Object> userRole = new HashMap<>();
						userRole.put("email", profileJsonRes.getJSONObject("data").getString("email"));
						userRole.put("mobile", profileJsonRes.getJSONObject("data").getString("mobile"));
						String email = profileJsonRes.getJSONObject("data").getString("email");
						String mobile = profileJsonRes.getJSONObject("data").getString("mobile");
						String username = profileJsonRes.getJSONObject("data").getString("username");
						Integer user_role = profileJsonRes.getJSONObject("data").getInt("role_id");

						String token	=	JwtTokenGenerator.generateToken(email,mobile,username,user_role,orgid, MessageConstant.SECRET);
						//return JSONUtil.setJSONResonse(MessageConstant.RESPONSE_SUCCESS, MessageConstant.TRUE, userRole,token);
					    request.getSession(true).setAttribute("hrms", token);
					    // switch case to identify the user screen login
					    
					 // extract pwdFlag from response
					    String pwdFlag = profileJsonRes.getJSONObject("data").getString("pwdFlag");
					    //Integer roleId = profileJsonRes.getJSONObject("data").getInt("role_id");
					   
					    //roleId != 9 &&
					    if ("N".equalsIgnoreCase(pwdFlag) ) {
					        String triggerId = java.util.UUID.randomUUID().toString();
					        session.setAttribute("callForgotPassword", "true");
					        session.setAttribute("mobile", profileJsonRes.getJSONObject("data").getString("mobile"));
					        session.setAttribute("triggerId", triggerId); // ✅ store unique ID
					        return "redirect:/login";
					    }




					switch (String.valueOf(profileJsonRes.getJSONObject("data").getInt("role_id"))) {
					case "0":
						screenName="index";
						model.addAttribute("message", "No Role assigned to User. Please contact to Organisation Admin !!");
						break;
					case "1":
						//screenName="dashboard";
						screenName="custom-dashboard";

						break;
					case "9":
						//screenName="erupi-dashboard";
						//screenName="dashboard";
						screenName="custom-dashboard";
						break;
					case "2":
						//screenName="employee-dashboard";
						//screenName="dashboard";
						screenName="custom-dashboard";
						model.addAttribute("name",profileJsonRes.getJSONObject("data").getString("email"));
						break;
					case "3":
						//screenName="employee-dashboard";
						//screenName="dashboard";
						screenName="custom-dashboard";
						model.addAttribute("name",profileJsonRes.getJSONObject("data").getString("email"));
						break;
					case "10":
						//screenName="dashboard";
						screenName="custom-dashboard";
						model.addAttribute("name",profileJsonRes.getJSONObject("data").getString("email"));
						break;
					case "12":
						//screenName="dashboard";
						screenName="custom-dashboard";
						model.addAttribute("name",profileJsonRes.getJSONObject("data").getString("email"));
						break;
					case "7":
						//screenName="dashboard";
						screenName="custom-dashboard";
						model.addAttribute("name",profileJsonRes.getJSONObject("data").getString("email"));
						break;
					}
					
					return screenName;
				}else if(profileJsonRes.getString("message").equalsIgnoreCase("Invalid Request")){
					    session.setAttribute("message", "Incorrect OTP ||");
					    session.setAttribute("mobile", userForm.getMobile());
					    session.setAttribute("orderid", userForm.getOrderId());
					    ///for repute user case
					    model.addAttribute("reputeUser",session.getAttribute("reputeUser"));
					    //end
						model.addAttribute("message",session.getAttribute("message"));
						model.addAttribute("mobile",session.getAttribute("mobile"));
						model.addAttribute("orderid",session.getAttribute("orderid"));
						return "index";
				}
				else if(profileJsonRes.getString("message").equalsIgnoreCase("Expired")){
					  model.addAttribute("message", "OTP Expired");
					 return "index";
				}
				else if(profileJsonRes.getString("message").equalsIgnoreCase("Link expired")){
					  model.addAttribute("message", "OTP Expired");
					 return "index";
				}
			}else {
				model.addAttribute("message", "Incorrect OTP ||");
			}
			return "index";
		}catch (Exception e) {
			logger.error(e.getMessage());
			model.addAttribute("message", "System not responding, Please try again later..!");
			return "index";
		}finally {
			profileRes=null;profileJsonRes=null;screenName=null; obj =null;
		}
	}

    @PostMapping(value = "/Pwdlogin")
    public String Pwdlogin(HttpServletRequest request, HttpServletResponse response,
                               @ModelAttribute("loginWithPwd") LoginWithPwd loginWithPwd,
                               BindingResult result, HttpSession session, Model model,
                               RedirectAttributes redirect) {

        String profileRes = null;
        JSONObject profileJsonRes = null;
        String screenName = "index";
        Integer orgid = null;

        Date currentDate = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss E d MMM yyyy");
        String formattedDate = formatter.format(currentDate);

        try {
            // Convert request to JSON
            String json = EncryptionDecriptionUtil.convertToJson(loginWithPwd);

            // Encrypt request
            EncriptResponse jsonObject = EncryptionDecriptionUtil.encriptResponse(
                    json, applicationConstantConfig.apiSignaturePublicPath);

            // Call login service
            String encriptResponse = loginservice.loginwithPwd(tokengeneration.getToken(), jsonObject);

            // Decrypt response
            EncriptResponse userReqEnc = EncryptionDecriptionUtil.convertFromJson(
                    encriptResponse, EncriptResponse.class);

            profileRes = EncryptionDecriptionUtil.decriptResponse(
                    userReqEnc.getEncriptData(),
                    userReqEnc.getEncriptKey(),
                    applicationConstantConfig.apiSignaturePrivatePath);

            logger.info(profileRes);

            if (!ObjectUtils.isEmpty(profileRes)) {
                profileJsonRes = new JSONObject(profileRes);

                if (profileJsonRes.getBoolean("status") &&
                        profileJsonRes.getString("message")
                                .equalsIgnoreCase(MessageConstant.RESPONSE_SUCCESS)) {
                	
                	

                    // Set session data
                    session.setAttribute("email", profileJsonRes.getJSONObject("data").getString("email"));
                    session.setAttribute("mobile", profileJsonRes.getJSONObject("data").getString("mobile"));
                    session.setAttribute("organizationName", profileJsonRes.getJSONObject("data").getString("organizationName"));
                    session.setAttribute("username", profileJsonRes.getJSONObject("data").getString("username"));
                    session.setAttribute("user_role", profileJsonRes.getJSONObject("data").getInt("role_id"));
                    session.setAttribute("formattedDate", formattedDate);
                    
                    


                    // Role based session data
                    int roleId = profileJsonRes.getJSONObject("data").getInt("role_id");
                     String mobile= profileJsonRes.getJSONObject("data").getString("mobile");
                    if (roleId == 9) {
                    	 session.setAttribute("otp_after_pwd", "true");
                    	    session.setAttribute("mobile", mobile);
                    	    session.setAttribute("otp_once_token", String.valueOf(System.currentTimeMillis()));// unique token to see if user has come using authentication already
                        return "redirect:/login";
                    }
                    
                    
                    if (roleId == 1 || roleId == 3) {
                        session.setAttribute("id", profileJsonRes.getJSONObject("data").getInt("employerid"));
                        session.setAttribute("empId", profileJsonRes.getJSONObject("data").getInt("id"));
                        orgid = profileJsonRes.getJSONObject("data").getInt("employerid");
                    } else {
                        session.setAttribute("id", profileJsonRes.getJSONObject("data").getInt("employerid"));
                        session.setAttribute("empId", profileJsonRes.getJSONObject("data").getInt("id"));
                        orgid = profileJsonRes.getJSONObject("data").getInt("employerid");
                    }

                    // Generate JWT Token and store in session
                    String token = JwtTokenGenerator.generateToken(
                            profileJsonRes.getJSONObject("data").getString("email"),
                            profileJsonRes.getJSONObject("data").getString("mobile"),
                            profileJsonRes.getJSONObject("data").getString("username"),
                            roleId,
                            orgid,
                            MessageConstant.SECRET);
                    session.setAttribute("hrms", token);

                    // Decide screen based on role
                    switch (String.valueOf(roleId)) {
                        case "0":
                            screenName = "index";
                            model.addAttribute("message", "No Role assigned to User. Please contact Admin!!");
                            break;
                        default:
                            screenName = "custom-dashboard";
                            model.addAttribute("name", profileJsonRes.getJSONObject("data").getString("email"));
                            break;
                    }
                    return screenName;

                }
                else {
                    model.addAttribute("message", profileJsonRes.getString("message"));
                    return "index";
                }
            } else {
                model.addAttribute("message", "Invalid Credentials");
                return "index";
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            model.addAttribute("message", "System not responding, Please try again later..!");
            return "index";
        } finally {
            profileRes = null;
            profileJsonRes = null;
            screenName = null;
        }
    }
    @PostMapping(value="/updatepassword")
	public @ResponseBody String updatepassword(HttpServletRequest request, ModelMap model,Locale locale,
			HttpSession session, UserOtpRequest userotprequest) {
			logger.info("/updatepassword");	
			String token = (String) session.getAttribute("hrms");
			String profileRes=null;
		
			try {
				String json = EncryptionDecriptionUtil.convertToJson(userotprequest);

				EncriptResponse jsonObject=EncryptionDecriptionUtil.encriptResponse(json, applicationConstantConfig.apiSignaturePublicPath);

				String encriptResponse = loginservice.updatepassword(tokengeneration.getToken(), jsonObject);

	   
				EncriptResponse userReqEnc =EncryptionDecriptionUtil.convertFromJson(encriptResponse, EncriptResponse.class);

				profileRes =  EncryptionDecriptionUtil.decriptResponse(userReqEnc.getEncriptData(), userReqEnc.getEncriptKey(), applicationConstantConfig.apiSignaturePrivatePath);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	   
		return profileRes;
	}

}