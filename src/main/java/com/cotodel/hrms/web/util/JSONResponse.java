package com.cotodel.hrms.web.util;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
@Component
public class JSONResponse {
	
	String status;
	String message;
	Object data;
	Object token;
	
	public JSONResponse() {
		// TODO Auto-generated constructor stub
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getToken() {
		return token;
	}

	public void setToken(Object token) {
		this.token = token;
	}

	@Override
	public String toString() {
		return "JSONResponse [status=" + status + ", message=" + message + ", data=" + data + ", token=" + token + "]";
	}

}

