package com.cotodel.hrms.web.controller;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.font.TextAttribute;
import java.awt.image.BufferedImage;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.cotodel.hrms.web.properties.ApplicationConstantConfig;
import com.cotodel.hrms.web.response.ReCaptcha;
import com.cotodel.hrms.web.service.ReCaptchaService;
import com.cotodel.hrms.web.service.Impl.TokenGenerationImpl;

@RestController
@CrossOrigin
public class ReCaptchaController extends CotoDelBaseController{
	
	@Autowired
	TokenGenerationImpl tokengeneration;
	
	@Autowired
	public ApplicationConstantConfig applicationConstantConfig;
	
	@Autowired
	ReCaptchaService reCaptchaService;
	
	@GetMapping("/getrecaptcha")
	public @ResponseBody String getcaptcha(ModelMap model, Locale locale, HttpSession session,ReCaptcha reCaptcha) {
        String companyId = "HRMS00001";
        return reCaptchaService.getcaptcha(tokengeneration.getToken(),companyId);
    }
	public static final String FILE_TYPE = "jpeg";
	@GetMapping("/captcha")
	public void getCaptchaImage(ModelMap model, Locale locale, HttpSession session,ReCaptcha reCaptcha) {
		
			response.setHeader( "Cache-Control", "no-store" );
			response.setHeader( "Pragma", "no-cache" );
			response.setDateHeader( "Expires", 0 );
			String companyId = request.getHeader("companyId");
			System.out.println("companyId:"+companyId);
	       String captchaStr=generateCaptcha(6);
	       try {
	           int width=125;      
	           int height=35;
	           Color bg = new Color(44,45,110);
	           Color fg = new Color(255,255,255);
	           
	           Map<TextAttribute, Object> attributes = new HashMap<TextAttribute, Object>();
	           attributes.put(TextAttribute.TRACKING, 0.4);
 
	           Font font = new Font("Arial", Font.BOLD, 16);
	           font = font.deriveFont(attributes);
	           BufferedImage cpimg =new BufferedImage(width,height,BufferedImage.TYPE_BYTE_GRAY);
	           Graphics g = cpimg.createGraphics();
	           g.setFont(font);
	           g.setColor(bg);
	           g.fillRect(0, 0, width, height);
	           g.setColor(fg);
	           g.drawString(captchaStr,10,25);   
	           session.setAttribute("CAPTCHA", captchaStr);

	          OutputStream outputStream = response.getOutputStream();

	          ImageIO.write(cpimg, FILE_TYPE, outputStream);
	          outputStream.flush();
	          outputStream.close();
	       } catch (Exception e) {
	    	   e.printStackTrace();
	           Map<String, Object> errorResponse = new HashMap<>();
	           errorResponse.put("status", "error");
	           errorResponse.put("message", "Failed to generate captcha");
	       }
	   }
	   private String generateCaptcha(int captchaLength) {
		   String saltChars = "ABCDEFGHJKMNPQRSTUVWXYZabcdefghjkmnopqrstuvwxyz123456789";
		   StringBuffer captchaStrBuffer = new StringBuffer();
		   Random rnd = new Random();
		   while (captchaStrBuffer.length() < captchaLength)
		   {
			   int index = (int) (rnd.nextFloat() * saltChars.length());
			   captchaStrBuffer.append(saltChars.substring(index, index+1));
		   }
		   return captchaStrBuffer.toString();
	}
	   @GetMapping("/verifyCaptcha")
	   @ResponseBody
	   public Map<String, Object> verifyCaptcha(HttpSession session, String captchaInput) {
	       Map<String, Object> response = new HashMap<>();
	       String sessionCaptcha = (String) session.getAttribute("CAPTCHA");

	       if (sessionCaptcha != null && sessionCaptcha.equals(captchaInput)) {
	           response.put("status", "success");
	       } else {
	           response.put("status", "fail");
	       }
	       return response;
	   }

}
