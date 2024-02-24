package com.cotodel.hrms.web.controller;


import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
@CrossOrigin
public class ExceptionController extends CotoDelBaseController{

	    @ExceptionHandler(NoHandlerFoundException.class)
	    public String handle404Exception(NoHandlerFoundException ex) {
	         //do whatever you want
	    	return "redirect:/login";
	    }
	    
	    
	    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	    public String handle500Exception(HttpRequestMethodNotSupportedException ex) {
	         //do whatever you want
	    	return "redirect:/login";
	    }
	    
}