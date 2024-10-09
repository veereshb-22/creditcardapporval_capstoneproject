package com.crimsonlogic.creditcardapporval.exeception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(UserDetailsNotFoundException.class)
	public ResponseEntity<String> handleCustomException(UserDetailsNotFoundException ex) {
	   return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
	}
	 
	 @ExceptionHandler(Exception.class)
	 public ResponseEntity<String> handleGenericException(Exception ex) {
	    return new ResponseEntity<>("An error occurred: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	  }
	 
	 @ExceptionHandler(CreditCardNotFoundException.class)
	    public ResponseEntity<String> handleCreditCardNotFoundException(CreditCardNotFoundException ex) {
	        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
	   }
	 
	 @ExceptionHandler(RuntimeException.class)
	 public ResponseEntity<String> handleRuntimeException(RuntimeException ex) {
	     return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	 }
}
