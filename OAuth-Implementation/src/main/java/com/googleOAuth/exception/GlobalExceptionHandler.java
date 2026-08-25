package com.googleOAuth.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(OAuthException.class)
    public String handleOAuthException(
            OAuthException exception,
            Model model) {

        model.addAttribute("errorMessage", exception.getMessage());

        return "error";
    }
	
	
	 @ExceptionHandler(Exception.class)
	    public String handleException(
	            Exception exception,
	            Model model) {

	        exception.printStackTrace();

	        model.addAttribute(
	                "errorMessage",
	                "Something went wrong. Please try again."
	        );

	        return "error";
	    }
	
}
