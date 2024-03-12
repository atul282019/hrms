package com.cotodel.hrms.web.util;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;


/**
 * 
 * @author Alim
 * 
 */
public class JSONUtil {

	public Map<String, Object> getRequestHeaderMap(HttpServletRequest request) {
		Map<String,Object> requestHeaderMap = new HashMap<String,Object>();
		Enumeration<String> headerNames = request.getHeaderNames();
		while (headerNames.hasMoreElements()) {
			String key = (String) headerNames.nextElement();
			String value = request.getHeader(key);
			requestHeaderMap.put(key, value);
		}
		return requestHeaderMap;
	}
		
	public static JSONResponse setJSONResonse(String message, boolean status,Object data) {

		JSONResponse jsonResponse = new JSONResponse();
		if (status) {
			jsonResponse.setStatus(MessageConstant.RESPONSE_SUCCESS);
		} else {
			jsonResponse.setStatus(MessageConstant.RESPONSE_FAILED);
		}

		jsonResponse.setMessage(message);
		jsonResponse.setData(data);
		return jsonResponse;
	}

	public static JSONResponse setJSONResonse(String message, boolean status,Object data, Object token) {
		JSONResponse jsonResponse = new JSONResponse();
		if (status) {
			jsonResponse.setStatus(MessageConstant.RESPONSE_SUCCESS);
		} else {
			jsonResponse.setStatus(MessageConstant.RESPONSE_FAILED);
		}
		jsonResponse.setMessage(message);
		jsonResponse.setData(data);
		jsonResponse.setToken(token);
		return jsonResponse;
	}

}