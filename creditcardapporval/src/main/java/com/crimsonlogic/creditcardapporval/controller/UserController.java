package com.crimsonlogic.creditcardapporval.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.crimsonlogic.creditcardapporval.entity.User;
import com.crimsonlogic.creditcardapporval.exeception.UserDetailsNotFoundException;
import com.crimsonlogic.creditcardapporval.payload.UserDto;
import com.crimsonlogic.creditcardapporval.service.UserService;

import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@CrossOrigin("*")
@RestController
@RequestMapping("/api")
public class UserController {
		private static final Logger logger = LoggerFactory.getLogger(UserController.class);
		
		@Autowired
	    private UserService userService;
		
		@PostMapping("/login")
		public ResponseEntity<User> login(@RequestBody UserDto user, HttpSession session) {		    
			  try {
	            User validatedUser = userService.validateUser(user.getUserEmail(), user.getUserPassword());
	            session.setAttribute("userEmail",user.getUserEmail());
	            System.err.println("**************************************************");
	            String s=(String)session.getAttribute("userEmail");
	            System.err.println("EMAILS SESSION IS "+s);
	            logger.info("Login Controller");
	            return new ResponseEntity<User>(validatedUser, HttpStatus.OK);
			  	} catch (UserDetailsNotFoundException ex) {
	            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
	        }
		    
		  }    
}
