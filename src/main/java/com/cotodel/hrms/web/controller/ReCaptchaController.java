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

import org.json.JSONObject;
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
import com.fasterxml.jackson.databind.ObjectMapper;


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
		System.out.println("Testing captcha");
        String captchaResponse = null;
        JSONObject captchaJsonResponse = null;
        Map<String, Object> responseMap = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();
        String jsonResponse = null;
        String companyId = "HRMS00001";
        // Call the service to save the BankMaster object
        return reCaptchaService.getcaptcha(tokengeneration.getToken(),companyId);
//        System.out.println(captchaResponse);  // Logging the response
//        captchaJsonResponse = new JSONObject(captchaResponse);      
//        if(captchaJsonResponse.getBoolean("status")) { 
//			responseMap.put("status", MessageConstant.RESPONSE_SUCCESS);
//		}else {
//			//loginsevice.rsendEmailVerificationCompletion(userForm);
//			responseMap.put("status", MessageConstant.RESPONSE_FAILED);
//		}
//        
//        try {
//            jsonResponse = mapper.writeValueAsString(responseMap);
//        } catch (Exception e) {
//            e.printStackTrace(); 
//        }       
//        return captchaResponse;
//        
    }
//	@GetMapping("/getrecaptcha")
//	public ResponseEntity<byte[]> getCaptcha() {
//	    // Get the base64 string from the ReCaptcha service
//	    String base64Image = reCaptchaService.getcaptcha(tokengeneration.getToken(), "HRMS00001");
//	    String base64Data = base64Image.replaceFirst("^data:image\\/[^;]+;base64,", "");
//	    // Decode the base64 string to a byte array
//	    byte[] imageBytes = Base64.getDecoder().decode(base64Data);
//
//	    // Set the headers to indicate that the response is an image
//	    HttpHeaders headers = new HttpHeaders();
//	    headers.setContentType(MediaType.IMAGE_JPEG);  // Or IMAGE_PNG if your image is PNG
//	    headers.setContentLength(imageBytes.length); // Set content length
//
//	    // Return the byte array as an image with the appropriate headers
//	    return new ResponseEntity<>(imageBytes, headers, HttpStatus.OK);
//	}
	public static final String FILE_TYPE = "jpeg";
	@GetMapping("/captcha")
	public void getCaptchaImage(ModelMap model, Locale locale, HttpSession session,ReCaptcha reCaptcha) {
		
			response.setHeader( "Cache-Control", "no-store" );
			response.setHeader( "Pragma", "no-cache" );
			response.setDateHeader( "Expires", 0 );
			String companyId = request.getHeader("companyId");
			System.out.println("companyId:"+companyId);
			//SetDatabaseTenent.setDataSource(companyId);
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
 
	          // HttpSession session = request.getSession(true);
	           session.setAttribute("CAPTCHA", captchaStr);
 
	           //logger.info("captchaStr==="+captchaStr);
	          OutputStream outputStream = response.getOutputStream();

	          ImageIO.write(cpimg, FILE_TYPE, outputStream);
	        
	          outputStream.flush();
	          outputStream.close();
//	           Map<String, Object> responseMap = new HashMap<>();
//	           responseMap.put("captcha", captchaStr);
//	           responseMap.put("status", "success");
// 
//	           // If you still want to send the image, you could add it as a base64 string, for example
//	           ByteArrayOutputStream baos = new ByteArrayOutputStream();
//	           ImageIO.write(cpimg, "PNG", baos);
//	           String imageBase64 = Base64.getEncoder().encodeToString(baos.toByteArray());
//	           responseMap.put("captcha_image", imageBase64); // Add base64-encoded image to the response
// 
//	           return ResponseEntity.ok(new UserCaptchaResponse(MessageConstant.TRUE,MessageConstant.RESPONSE_SUCCESS,imageBase64,TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp()));
	           
	       } catch (Exception e) {
	    	   e.printStackTrace();
	           Map<String, Object> errorResponse = new HashMap<>();
	           errorResponse.put("status", "error");
	           errorResponse.put("message", "Failed to generate captcha");
//	           return ResponseEntity.ok(new UserCaptchaResponse(MessageConstant.FALSE,MessageConstant.RESPONSE_FAILED,"error",TransactionManager.getTransactionId(),TransactionManager.getCurrentTimeStamp()));
	       }
	   }
 
	   private String generateCaptcha(int captchaLength) {
 
		   String saltChars = "ABCDEFGHJKLMNPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz123456789";
		   StringBuffer captchaStrBuffer = new StringBuffer();
		   Random rnd = new Random();
 
		   while (captchaStrBuffer.length() < captchaLength)
		   {
			   int index = (int) (rnd.nextFloat() * saltChars.length());
			   captchaStrBuffer.append(saltChars.substring(index, index+1));
		   }
 
		   return captchaStrBuffer.toString();
 
	}
	   
	  

}
