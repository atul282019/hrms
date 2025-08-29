package com.cotodel.hrms.web.filter;


import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Component
@CrossOrigin(origins = "", allowedHeaders = "")
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CotoDelAdminFilter implements Filter, WebMvcConfigurer{


	//Logger logger = LoggerFactory.getLogger(CotoDelAdminFilter.class);

	FilterConfig filterConfig = null;

	@Override
	public void init(FilterConfig filterConfig){
		this.filterConfig = filterConfig;
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException{

		HttpServletRequest httpServletRequest = (HttpServletRequest)request;
		String requestURI = httpServletRequest.getRequestURI();

		boolean isStaticResource1 = httpServletRequest.getRequestURI().contains("cotodel-js/");
		boolean isStaticResource2 = httpServletRequest.getRequestURI().contains("vendor/");
		boolean isStaticResource3 = httpServletRequest.getRequestURI().contains("images/");
		boolean isStaticResourceJs = httpServletRequest.getRequestURI().contains("js/");
		boolean isStaticResourceCss = httpServletRequest.getRequestURI().contains("css/");
		boolean isStaticResourceSass = httpServletRequest.getRequestURI().contains("scss/");
		boolean isStaticResourceImg = httpServletRequest.getRequestURI().contains("img/");
		boolean isStaticResourceFiles = httpServletRequest.getRequestURI().contains("files/");
		boolean isStaticResourcewebfonts = httpServletRequest.getRequestURI().contains("webfonts/");
		boolean isStaticResourcewebfontsblog = httpServletRequest.getRequestURI().contains("/blogs");
		boolean isStaticResourcewebfontssolutions = httpServletRequest.getRequestURI().contains("/solutions");

		HttpSession session = httpServletRequest.getSession(false);


		HttpServletResponse res = (HttpServletResponse) response;
		//logger.info("inside cors filter method == "+httpServletRequest.getMethod()+" || "+httpServletRequest.getRequestURI());
        res.setHeader("Access-Control-Allow-Origin", "*");
        res.setHeader("Access-Control-Allow-Methods", "GET,HEAD,OPTIONS,POST,PUT");
        res.setHeader("Access-Control-Allow-Headers", "Access-Control-Allow-Headers, Origin,Accept,"
        		+ " X-Requested-With, Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers , Authorization,Access-Control-Allow-Origin");

        if ("OPTIONS".equalsIgnoreCase(httpServletRequest.getMethod())) {
        	res.setStatus(HttpServletResponse.SC_OK);
        	res.setContentType("application/json");
        }

        if (requestURI.contains(";jsessionid")) {
            String cleanUri = requestURI.replaceAll(";jsessionid=[^?]*", "");
            ((HttpServletResponse) response).sendRedirect(cleanUri);
            return;
        }
		if (session == null) {
			session = httpServletRequest.getSession(true);
		}
		String login = (String) session.getAttribute("hrms");
		//String login = (String) session.getAttribute("Bis_Login");
		//logger.info("login"+login);
		//logger.info("request uri-------------"+requestURI);
		if(login==null){
			if( isStaticResourceImg || isStaticResource1 || isStaticResource2 || isStaticResource3
					|| isStaticResourceJs || isStaticResourceCss || isStaticResourceSass || isStaticResourcewebfonts || isStaticResourceFiles
					||isStaticResourcewebfontsblog || isStaticResourcewebfontssolutions) {
						chain.doFilter(request, response);
			}
			else if(requestURI.contains("/login")){
				RequestDispatcher rd = request.getRequestDispatcher("login");
				rd.forward(request, response);
			}
			else if(requestURI.contains("/FleetLogin")){
				RequestDispatcher rd = request.getRequestDispatcher("FleetLogin");
				rd.forward(request, response);
			}
			else if(requestURI.contains("/signup")){
				RequestDispatcher rd = request.getRequestDispatcher("signup");
				rd.forward(request, response);
			}
			else if(requestURI.contains("/signin")){
				RequestDispatcher rd = request.getRequestDispatcher("signin");
				rd.forward(request, response);
			}
			else if(requestURI.contains("/smsOtpSender")){
				RequestDispatcher rd = request.getRequestDispatcher("smsOtpSender");
				rd.forward(request, response);
			}
			else if(requestURI.contains("/otpWithoutLogin")){
				RequestDispatcher rd = request.getRequestDispatcher("otpWithoutLogin");
				rd.forward(request, response);
			}
			else if(requestURI.contains("/userLogin")){
				RequestDispatcher rd = request.getRequestDispatcher("userLogin");
				rd.forward(request, response);
			}
			else if(requestURI.contains("/Pwdlogin")){
				RequestDispatcher rd = request.getRequestDispatcher("Pwdlogin");
				rd.forward(request, response);
			}
			else if(requestURI.contains("/registerUser")){
				RequestDispatcher rd = request.getRequestDispatcher("registerUser");
				rd.forward(request, response);
			}
			else if(requestURI.contains("/corporateRegistration")){
				RequestDispatcher rd = request.getRequestDispatcher("corporateRegistration");
				rd.forward(request, response);
			}
			else if(requestURI.contains("/sendEmailVerifyLink")){
				RequestDispatcher rd = request.getRequestDispatcher("sendEmailVerifyLink");
				rd.forward(request, response);
			}else if(requestURI.contains("/verifyRegisterUser")){
				RequestDispatcher rd = request.getRequestDispatcher("verifyRegisterUser");
				rd.forward(request, response);
			}
//			else if(requestURI.contains("/companyDetails")){
//				RequestDispatcher rd = request.getRequestDispatcher("companyDetails");
//				rd.forward(request, response);
//			}
//			else if(requestURI.contains("/dashboard1")){
//				RequestDispatcher rd = request.getRequestDispatcher("dashboard1");
//				rd.forward(request, response);
//			}
			else if(requestURI.contains("/sitemap.xml")){
				RequestDispatcher rd = request.getRequestDispatcher("sitemap.xml");
				rd.forward(request, response);
			}
			else if(requestURI.contains("/robots.txt")){
				RequestDispatcher rd = request.getRequestDispatcher("robots.txt");
				rd.forward(request, response);
			}
			else if(requestURI.contains("/logout")){
				RequestDispatcher rd = request.getRequestDispatcher("logout");
				rd.forward(request, response);
			}
			else if(requestURI.contains("/tempLogin")){
				RequestDispatcher rd = request.getRequestDispatcher("tempLogin");
				rd.forward(request, response);
			}
			else if(requestURI.contains("/smsOtpResender")){
				RequestDispatcher rd = request.getRequestDispatcher("smsOtpResender");
				rd.forward(request, response);
			}
			else if(requestURI.contains("/userWaitList")){
				RequestDispatcher rd = request.getRequestDispatcher("userWaitList");
				rd.forward(request, response);
			}
			else if(requestURI.contains("/updatepassword")){
				RequestDispatcher rd = request.getRequestDispatcher("updatepassword");
				rd.forward(request, response);
			}
			else if(requestURI.contains("/captcha")){
				RequestDispatcher rd = request.getRequestDispatcher("captcha");
				rd.forward(request, response);
			}
			else if(requestURI.contains("/nointernet")){
				RequestDispatcher rd = request.getRequestDispatcher("nointernet");
				rd.forward(request, response);
			}
			else if(requestURI.contains("/termsconditions")){
				RequestDispatcher rd = request.getRequestDispatcher("termsconditions");
				rd.forward(request, response);
			}
			else if(requestURI.contains("/refundpolicy")){
				RequestDispatcher rd = request.getRequestDispatcher("refundpolicy");
				rd.forward(request, response);
			}
			else if(requestURI.contains("/privacypolicy")){
				RequestDispatcher rd = request.getRequestDispatcher("privacypolicy");
				rd.forward(request, response);
			}
			else if(requestURI.contains("/solutions")){
				RequestDispatcher rd = request.getRequestDispatcher("solutions");
				rd.forward(request, response);
			}
			else if(requestURI.contains("/about")){
				RequestDispatcher rd = request.getRequestDispatcher("about");
				rd.forward(request, response);
			}
			else if(requestURI.contains("/faqs")){
				RequestDispatcher rd = request.getRequestDispatcher("faqs");
				rd.forward(request, response);
			}
			else if(requestURI.contains("/transport")){
				RequestDispatcher rd = request.getRequestDispatcher("transport");
				rd.forward(request, response);
			}
			else if(requestURI.contains("/retail")){
				RequestDispatcher rd = request.getRequestDispatcher("retail");
				rd.forward(request, response);
			}
			else if(requestURI.contains("/consulting")){
				RequestDispatcher rd = request.getRequestDispatcher("consulting");
				rd.forward(request, response);
			}
			else if(requestURI.contains("/blogs")){
				RequestDispatcher rd = request.getRequestDispatcher("blogspage");
				rd.forward(request, response);
			}
			else if(requestURI.contains("/whatsapp/send")){
				RequestDispatcher rd = request.getRequestDispatcher("send");
				rd.forward(request, response);
			}
			else if(requestURI.contains("/webhook-callback")){
				RequestDispatcher rd = request.getRequestDispatcher("webhook-callback");
				rd.forward(request, response);
			}
			else if(requestURI.contains("/webhook-repute")){
				RequestDispatcher rd = request.getRequestDispatcher("webhook-repute");
				rd.forward(request, response);
			}
			else if(requestURI.contains("/staging_webhook")){
				RequestDispatcher rd = request.getRequestDispatcher("staging_webhook");
				rd.forward(request, response);
			}
			else if(requestURI.contains("/stagingWebhook")){
				RequestDispatcher rd = request.getRequestDispatcher("stagingWebhook");
				rd.forward(request, response);
			}
			else if(requestURI.contains("/preprod-webhook-callback")){
				RequestDispatcher rd = request.getRequestDispatcher("preprod-webhook-callback");
				rd.forward(request, response);
			}
			else if(requestURI.contains("/production-webhook-callback")){
				RequestDispatcher rd = request.getRequestDispatcher("production-webhook-callback");
				rd.forward(request, response);
			}

			else if(requestURI.contains("/contact")){
				RequestDispatcher rd = request.getRequestDispatcher("contact");
				rd.forward(request, response);
			}
			else if(requestURI.contains("/waitlist")){
				RequestDispatcher rd = request.getRequestDispatcher("waitlist");
				rd.forward(request, response);
			}
			else if(requestURI.contains("/solutions/corporateexpensemanagement/upivoucher")){
				RequestDispatcher rd = request.getRequestDispatcher("experienceupivoucher");
				rd.forward(request, response);
			}
			else if(requestURI.contains("/solutions/fleetexpensemanagement/upivoucher")){
				RequestDispatcher rd = request.getRequestDispatcher("experienceupivoucher");
				rd.forward(request, response);
			}

			else if(requestURI.contains("/solutions/fleetexpensemanagement")){
				RequestDispatcher rd = request.getRequestDispatcher("fleetManagement");
				rd.forward(request, response);
			}
			else if(requestURI.contains("/blogs/erupi")){
				RequestDispatcher rd = request.getRequestDispatcher("erupiBlog");
				rd.forward(request, response);
			}
			else if(requestURI.contains("/blogs/upilite")){
				RequestDispatcher rd = request.getRequestDispatcher("upiLite");
				rd.forward(request, response);
			}
			else if(requestURI.contains("/blogs/upi123pay")){
				RequestDispatcher rd = request.getRequestDispatcher("upiPay");
				rd.forward(request, response);
			}
			else if(requestURI.contains("/blogs/upicircle")){
				RequestDispatcher rd = request.getRequestDispatcher("upiCircle");
				rd.forward(request, response);
			}
			else if(requestURI.contains("/blogs/fleetmanagement")){
				RequestDispatcher rd = request.getRequestDispatcher("fleetmanagement");
				rd.forward(request, response);
			}
			else if(requestURI.contains("/solutions/corporateexpensemanagement")){
				RequestDispatcher rd = request.getRequestDispatcher("corporatesManagement");
				rd.forward(request, response);
			}
			else if(requestURI.contains("/policy")){
				RequestDispatcher rd = request.getRequestDispatcher("policy");
				rd.forward(request, response);
			}
			else if(requestURI.contains("/home")){
				RequestDispatcher rd = request.getRequestDispatcher("home");
				rd.forward(request, response);
			}
			else if(requestURI.contains("/Industries")){
				RequestDispatcher rd = request.getRequestDispatcher("Industries");
				rd.forward(request, response);
			}
			else if(requestURI.contains("/fleettrial")){
				RequestDispatcher rd = request.getRequestDispatcher("fleettrial");
				rd.forward(request, response);
			}
			else if(requestURI.contains("/custom01")){
				RequestDispatcher rd = request.getRequestDispatcher("custom01");
				rd.forward(request, response);
			}
			else if(requestURI.contains("/custom02")){
				RequestDispatcher rd = request.getRequestDispatcher("custom02");
				rd.forward(request, response);
			}
			else if(requestURI.contains("/custom03")){
				RequestDispatcher rd = request.getRequestDispatcher("custom03");
				rd.forward(request, response);
			}
			else if(requestURI.contains("/corporatetrial")){
				RequestDispatcher rd = request.getRequestDispatcher("corporatetrial");
				rd.forward(request, response);
			}
			else if(requestURI.contains("/blogs/fleetcardvsupi")){
				RequestDispatcher rd = request.getRequestDispatcher("fleetcardvsupi");
				rd.forward(request, response);
			}else if(requestURI.contains("/customlogin")){
				RequestDispatcher rd = request.getRequestDispatcher("customlogin");
				rd.forward(request, response);
			}
			else if(requestURI.contains("/otpWithOutRegister")){
				RequestDispatcher rd = request.getRequestDispatcher("otpWithOutRegister");
				rd.forward(request, response);
			}
			else if(requestURI.contains("/verifyOtpWithOutRegister")){
				RequestDispatcher rd = request.getRequestDispatcher("verifyOtpWithOutRegister");
				rd.forward(request, response);
			}
			else if(requestURI.contains("/otpVerifyWithTemplate")){
				RequestDispatcher rd = request.getRequestDispatcher("otpVerifyWithTemplate");
				rd.forward(request, response);
			}
			
			else{
				RequestDispatcher rd = request.getRequestDispatcher("/");
					rd.forward(request, response);
			}
		}else{
			chain.doFilter(request, response);
		}
	}

	@Override
	public void destroy() {

	}

}
