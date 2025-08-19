package com.cotodel.hrms.web.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.xml.bind.DatatypeConverter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import com.cotodel.hrms.web.response.EmailRequest;
import com.cotodel.hrms.web.response.EmployeeQualificationRequest;
import com.cotodel.hrms.web.response.ReputeCompanyDetails;
import com.cotodel.hrms.web.response.ReputeTokenRequest;
import com.fasterxml.jackson.databind.ObjectMapper;


public class CommonUtility {
	
	private static final Logger logger = LogManager.getLogger(CommonUtility.class);
	
	
	public static String userRequest(String sAccessToken,String requestJson,String url){
		String returnStr=null;
		String companyId = "HRMS00001";
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		try{
			logger.info(" Request Json for url"+url+"---"+requestJson);
			System.out.println(" Request Json for url"+url+"---"+requestJson);

			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.set("companyId", companyId);
			
			if(sAccessToken!=null && !sAccessToken.isEmpty()) {
				headers.setBearerAuth(sAccessToken);
			}
			
			HttpEntity<String> entity = new HttpEntity<String>(requestJson,headers);

			returnStr = restTemplate.postForObject(url, entity, String.class);
			logger.info(" response Json---"+returnStr);
			System.out.println(" response Json---"+returnStr);
			return returnStr;
		}catch(HttpStatusCodeException e) {
			logger.error("HttpStatusCodeException error in---"+url+"-"+e.getResponseBodyAsString());
			return e.getResponseBodyAsString();
		}catch(Exception e){
			logger.error(" error in---"+url+"-"+e);
			return null;
		}finally {
			restTemplate=null;headers=null;sAccessToken=null;requestJson=null;url=null;	
		}		
	}
	
	
	public static String bulkUserRequest(String sAccessToken,String requestJson,String url,String publicPath,String privatePath){
		String returnStr=null;
		String decript=null;
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		try{
			
			headers.setContentType(MediaType.APPLICATION_JSON);
			
			if(sAccessToken!=null && !sAccessToken.isEmpty()) {
				headers.setBearerAuth(sAccessToken);
			}
			EncriptResponse jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(requestJson, publicPath);
			String jsonEncript =  EncryptionDecriptionUtil.convertToJson(jsonEncriptObject);
			logger.info(" Request Json for url"+url+"---"+jsonEncript);
			 
			HttpEntity<String> entity = new HttpEntity<String>(jsonEncript,headers);
 
			returnStr = restTemplate.postForObject(url, entity, String.class);
			EncriptResponse enResponse= EncryptionDecriptionUtil.convertFromJson(returnStr, EncriptResponse.class);
			decript=EncryptionDecriptionUtil.decriptResponse(enResponse.getEncriptData(), enResponse.getEncriptKey(), privatePath);
			logger.info(" response Json---"+decript);
			return decript;
		}catch(HttpStatusCodeException e) {
			logger.error("HttpStatusCodeException error in---"+url+"-"+e.getResponseBodyAsString());
			return e.getResponseBodyAsString();
		}catch(Exception e){
			logger.error(" error in---"+url+"-"+e);
			return null;
		}finally {
			restTemplate=null;headers=null;sAccessToken=null;requestJson=null;url=null;	
		}		
	}

	public static String getTokenRequest(String sAccessToken,String requestJson,String companyId,String url){
		String returnStr=null;
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		try{
			logger.info(" Request URL---"+url);
			logger.info(" Request header---"+companyId);
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.set("companyId", companyId);
			if(sAccessToken!=null && !sAccessToken.isEmpty()) {
				headers.setBearerAuth(sAccessToken);
			}

			HttpEntity<String> entity = new HttpEntity<String>(requestJson,headers);
			ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
	        returnStr = response.getBody();
			logger.info(" response Json---"+returnStr);
			return returnStr;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}finally {
			restTemplate=null;headers=null;	
		}		
	}

	
	public static String userRequestforMultipartfile(String sAccessToken,EmployeeQualificationRequest requestJson,String url){
		RestTemplate restTemplate = new RestTemplate();
		String companyId = "HRMS00001";
		HttpHeaders headers = new HttpHeaders();
		try{
			logger.info(" Request Json for url"+url+"---"+requestJson);
			
			headers.setContentType(MediaType.MULTIPART_FORM_DATA);
			headers.set("companyId", companyId);
			if(sAccessToken!=null && !sAccessToken.isEmpty()) {
				headers.setBearerAuth(sAccessToken);
			}
			
			MultiValueMap<String, Object> body  = new LinkedMultiValueMap<>();
//			
//			ByteArrayResource fileAsResource = new ByteArrayResource(requestJson.getSigfile().getBytes()){
//			    @Override
//			    public String getFilename(){
//			        return requestJson.getSigfile().getOriginalFilename();
//			    }
//			};
//			ByteArrayResource fileAsResource2 = new ByteArrayResource(requestJson.getAttachment().getBytes()){
//			    @Override
//			    public String getFilename(){
//			        return requestJson.attachment.getOriginalFilename();
//			    }
//			};
			
		//	body.add("sigFile",fileAsResource);
			//body.add("attachment",fileAsResource2);
		
			body.add("fromDate", requestJson.getFromDate());
			body.add("toDate", requestJson.getToDate());
			body.add("education", requestJson.getEducation());
			body.add("institutes", requestJson.getInstitutes());
			body.add("referenceType", requestJson.getReferenceType());
			body.add("remarks", requestJson.getRemarks());
			
;			logger.info(" body Json---"+body);
			HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

			ResponseEntity<String> response = restTemplate.exchange(url,HttpMethod.POST,requestEntity, String.class);
			
			logger.info(" response Json---"+response.getBody());
			return response.getBody();
		}catch(HttpStatusCodeException e) {
			logger.error("HttpStatusCodeException error in---"+url+"-"+e.getResponseBodyAsString()+e.getMessage());
			return e.getResponseBodyAsString();
		}catch(Exception e){
			logger.error(" error in---"+url+"-"+e);
			return null;
		}finally {
			restTemplate=null;headers=null;	
		}		
	}
	
	public static String getuserRequest(String sAccessToken,String requestJson,String url){
		String returnStr=null;
		String companyId = "HRMS00001";
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		try{
			logger.info(" Request URL---"+url);
			//logger.info(" Request header---"+companyId);
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.set("companyId", companyId);
			if(sAccessToken!=null && !sAccessToken.isEmpty()) {
				headers.setBearerAuth(sAccessToken);
			}

			HttpEntity<String> entity = new HttpEntity<String>(requestJson,headers);
			ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
	        returnStr = response.getBody();
			logger.info(" response Json---"+returnStr);
			return returnStr;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}finally {
			restTemplate=null;headers=null;	
		}		
	}
	
	public static String expenseRequest(String sAccessToken,String requestJson,String url,String publicPath,String privatePath){
		String returnStr=null;
		String decript=null;
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		try{
			
			headers.setContentType(MediaType.APPLICATION_JSON);
			
			if(sAccessToken!=null && !sAccessToken.isEmpty()) {
				headers.setBearerAuth(sAccessToken);
			}
			EncriptResponse jsonEncriptObject=EncryptionDecriptionUtil.encriptResponse(requestJson, publicPath);
			String jsonEncript =  EncryptionDecriptionUtil.convertToJson(jsonEncriptObject);
			logger.info(" Request Json for url"+url+"---"+jsonEncript);
			 
			HttpEntity<String> entity = new HttpEntity<String>(jsonEncript,headers);
 
			returnStr = restTemplate.postForObject(url, entity, String.class);
			EncriptResponse enResponse= EncryptionDecriptionUtil.convertFromJson(returnStr, EncriptResponse.class);
			decript=EncryptionDecriptionUtil.decriptResponse(enResponse.getEncriptData(), enResponse.getEncriptKey(), privatePath);
			logger.info(" response Json---"+decript);
			return decript;
		}catch(HttpStatusCodeException e) {
			logger.error("HttpStatusCodeException error in---"+url+"-"+e.getResponseBodyAsString());
			return e.getResponseBodyAsString();
		}catch(Exception e){
			logger.error(" error in---"+url+"-"+e);
			return null;
		}finally {
			restTemplate=null;headers=null;sAccessToken=null;requestJson=null;url=null;	
		}		
	}

	public static ReputeTokenRequest getReputeToken(String code,String url,String redirectUri) {
		
		//String redirectUri="http://43.205.206.102:8088/repute_marketplace/";
		ReputeTokenRequest reputeCompanyDetails=new ReputeTokenRequest();
		try {
	        String body = "code="+code+ "&grant_type=authorization_code&redirect_uri="+ redirectUri;
	        String response=CommonUtility.getAccessToken(code,redirectUri,url+"/oauth2/token");
	        String accessToken ="";
	        String idToken ="";
	        if(response!=null) {
	        	JSONObject jsonObject = new JSONObject(response);
	        	accessToken = jsonObject.getString("access_token");
	        	idToken = jsonObject.getString("id_token");
	        	reputeCompanyDetails.setAccessToken(accessToken);
	        	reputeCompanyDetails.setIdToken(idToken);
	        	reputeCompanyDetails.setScope(jsonObject.getString("scope"));
	        	reputeCompanyDetails.setRefreshToken(jsonObject.getString("refresh_token"));
	        	
	        }
	        
		} catch (Exception e) {
			e.printStackTrace();
		}
	    
		return reputeCompanyDetails;
	}
	public static String getAccessToken(String code,String redirectUri,String sendurl) {
		try {
            // URL for the token request
            //URL url = new URL("https://app.demohrms.stg.repute.net/oauth2/token");
			URL url = new URL(sendurl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            
            // Set HTTP request method to POST
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.setRequestProperty("Authorization", "Basic YTc2Y2M2OGMtZmI5MS00ODI2LTk5NDctYmJlYmVlZjAxZTBhOnZlVEh1Y1UwOGJ1M1YzTnhFaE9MOUFkeDJFUVZWYlBT");

            // Enable input and output streams for POST data
            connection.setDoOutput(true);
            connection.setDoInput(true);

            // Form data to send in the POST request
            String urlParameters = "code="+code
                    + "&grant_type=authorization_code"
                    + "&redirect_uri="+redirectUri;

            // Write the data to the output stream
            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = urlParameters.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            // Read the response
            try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"))) {
                StringBuilder response = new StringBuilder();
                String responseLine;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
                System.out.println("Response: " + response.toString());
                return response.toString();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
	

	public static String sendEmailForBusiness(EmailRequest req) {
		// Set up mail server properties
		String message="FAIL";
				//req.setMobile("9911851042");
				Properties properties = new Properties();
//				properties.put("mail.smtp.host", "smtp.gmail.com");
//				properties.put("mail.smtp.port", "587");
				properties.put("mail.smtp.host", "smtp.office365.com");
				properties.put("mail.smtp.port", "587");
				properties.put("mail.smtp.auth", "true");
				properties.put("mail.smtp.starttls.enable", "true");
				Session session = Session.getInstance(properties, new Authenticator() {
					@Override
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication("business@cotodel.com", "CotoBiz@2023");
						//return new PasswordAuthentication("cotodel.info@gmail.com", "zxvg tryo uhdh akgf");
						//return new PasswordAuthentication("cotodel917@gmail.com", "CotoDel@123");
					}
				});

				Message msg = new MimeMessage(session);
				try {
					msg.setHeader("Content-Type", "text/plain; charset=UTF-8");
					msg.setFrom(new InternetAddress("business@cotodel.com", false));
					msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(req.getEmail()));
					msg.setSubject(req.getSubject());
					msg.setContent("Verify Sigin", "text/html");
					msg.setSentDate(new Date());
				//	byte[] bytes = req.getMobile().getBytes(StandardCharsets.UTF_8);
				//	String encoded = DatatypeConverter.printBase64Binary(bytes);
					byte[] byt = req.getEmail().getBytes(StandardCharsets.UTF_8);
					String emailbyt = DatatypeConverter.printBase64Binary(byt);
		
					String content=" <div style=\"max-width: 600px; margin: 0 auto;\">\r\n"
							+ "    <h1 style=\"color: #333333;\">Congratulations on Your Achievement!</h1>\r\n"
							+ "    <p style=\"font-size: 16px;\">Dear All,</p>\r\n"
							+ "    <p style=\"font-size: 16px;\">I hope this email finds you well. I just wanted to take a moment to extend my heartfelt congratulations to you on your recent achievement! It's truly wonderful to see your hard work and dedication paying off.</p>\r\n"
							+ "    <p style=\"font-size: 16px;\">Your accomplishment is a testament to your perseverance, skills, and determination. I have always admired your passion for what you do, and it's no surprise that you have achieved such success.</p>\r\n"
							+ "    <p style=\"font-size: 16px;\">Please know that your efforts have not gone unnoticed, and I am genuinely thrilled for you. This is just the beginning of many great things to come, and I am confident that you will continue to excel in all your future endeavors.</p>\r\n"
							+ "    <p style=\"font-size: 16px;\">Once again, congratulations on this well-deserved achievement! Wishing you continued success and happiness.</p>\r\n"
							+ "    <p style=\"font-size: 16px;\">Warm regards,</p>\r\n"
							+ "    <p style=\"font-size: 16px;\">Team Cotodel</p>\r\n"
							+ "  </div>";
					MimeBodyPart messageBodyPart = new MimeBodyPart();
					// String password =generatePassword(8);
					messageBodyPart.setContent(content, "text/html");
					Multipart multipart = new MimeMultipart();
					multipart.addBodyPart(messageBodyPart);
					msg.setContent(multipart);
					Transport.send(msg);
					logger.info("verification mail sended successfully to :" + req.getEmail());
					message= "SUCCESS";
				} catch (MessagingException e) {
					e.printStackTrace();
					message= "FAIL";
				}
				return message;
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
}
