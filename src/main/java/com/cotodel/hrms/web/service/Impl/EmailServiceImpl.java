package com.cotodel.hrms.web.service.Impl;

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
import org.springframework.stereotype.Service;

import com.cotodel.hrms.web.service.EmailService;
import com.cotodel.hrms.web.util.CommonUtility;

@Service
public class EmailServiceImpl implements EmailService{
	
	private static final Logger logger = LogManager.getLogger(EmailServiceImpl.class);
	
	public String sendEmail(String email) {
		// Set up mail server properties
				String message="FAIL";
						//req.setMobile("9911851042");
						Properties properties = new Properties();
//						properties.put("mail.smtp.host", "smtp.gmail.com");
//						properties.put("mail.smtp.port", "587");
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
							msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
							//msg.setSubject(req.getSubject());
							msg.setSubject("Voucher Creation");
							msg.setContent("Verify Sigin", "text/html");
							msg.setSentDate(new Date());
						//	byte[] bytes = req.getMobile().getBytes(StandardCharsets.UTF_8);
						//	String encoded = DatatypeConverter.printBase64Binary(bytes);
							byte[] byt = email.getBytes(StandardCharsets.UTF_8);
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
							logger.info("verification mail sended successfully to :" + email);
							message= "SUCCESS";
						} catch (MessagingException e) {
							e.printStackTrace();
							message= "FAIL";
						}
						return message;
	}

}
