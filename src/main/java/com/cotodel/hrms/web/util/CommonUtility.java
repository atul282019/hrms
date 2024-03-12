package com.cotodel.hrms.web.util;

import java.io.IOException;
import java.util.Base64;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.ObjectUtils;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import com.cotodel.hrms.web.response.EmployeeDetailsRequest;


public class CommonUtility {
	
	private static final Logger logger = LogManager.getLogger(CommonUtility.class);
	
	
	public static String userRequest(String sAccessToken,String requestJson,String url){
		String returnStr=null;
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		try{
			logger.info(" Request Json for url"+url+"---"+requestJson);

			headers.setContentType(MediaType.APPLICATION_JSON);
			
			if(sAccessToken!=null && !sAccessToken.isEmpty()) {
				headers.setBearerAuth(sAccessToken);
			}
			
			HttpEntity<String> entity = new HttpEntity<String>(requestJson,headers);

			returnStr = restTemplate.postForObject(url, entity, String.class);
			logger.info(" response Json---"+returnStr);
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
	
	
	public static String getTokenRequest(String sAccessToken,String requestJson,String companyId,String url){
		String returnStr=null;
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		try{
			logger.info(" Request URL---"+url);
			logger.info(" Request header---"+companyId);
			headers.setContentType(MediaType.APPLICATION_JSON);
			//headers.set("companyId", companyId);
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

	
	public static String userRequestforMultipartfile(String sAccessToken,EmployeeDetailsRequest requestJson,String url){
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		byte[] sigFile =null;
		byte[] docfile = null;
		try{
			logger.info(" Request Json for url"+url+"---"+requestJson);
			
			headers.setContentType(MediaType.MULTIPART_FORM_DATA);

			if(sAccessToken!=null && !sAccessToken.isEmpty()) {
				headers.setBearerAuth(sAccessToken);
			}
		
			  if(!ObjectUtils.isEmpty(requestJson.getSigfile()))
					try {
						sigFile = requestJson.getSigfile().getBytes();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				  
				  if(!ObjectUtils.isEmpty(requestJson.getDocfile()))
						try {
						docfile = requestJson.getDocfile().getBytes();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
				 
					
			MultiValueMap<String, Object> body  = new LinkedMultiValueMap<>();
			
//			ByteArrayResource fileAsResource = new ByteArrayResource(requestJson.getSigfile().getBytes()){
//			    @Override
//			    public String getFilename(){
//			        return requestJson.getSigfile().getOriginalFilename();
//			    }
//			};
//			ByteArrayResource fileAsResource2 = new ByteArrayResource(requestJson.getDocfile().getBytes()){
//			    @Override
//			    public String getFilename(){
//			        return requestJson.getSigfile().getOriginalFilename();
//			    }
//			};
			
			body.add("sigFile",Base64.getEncoder().encodeToString(sigFile));
			body.add("docFile",Base64.getEncoder().encodeToString(docfile));
		
			body.add("firstName", requestJson.getFirstName());
			body.add("middleName", requestJson.getMiddleName());
			body.add("lastName", requestJson.getLastName());
			body.add("dateOfBirth", requestJson.getDateOfBirth());
			body.add("nationality", requestJson.getNationality());
			body.add("maritalStatus", requestJson.getMaritalStatus());
			body.add("dateOfJoining", requestJson.getDateOfJoining());
     		body.add("designation", requestJson.getDesignation());
			body.add("department", requestJson.getDepartment());
			body.add("gender", requestJson.getGender());
			
			body.add("location", requestJson.getLocation());
			body.add("panNo", requestJson.getPanNo());
			body.add("esiNo", requestJson.getEsiNo());
			body.add("accountNumber", requestJson.getAccountNumber());
			body.add("uanNo", requestJson.getUanNo());
			body.add("bankName", requestJson.getBankName());
			body.add("remarks", requestJson.getRemarks());
     		body.add("serviceStatus", requestJson.getServiceStatus());
			body.add("employeeType", requestJson.getEmployeeType());
			body.add("category", requestJson.getCategory());
			
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
}
