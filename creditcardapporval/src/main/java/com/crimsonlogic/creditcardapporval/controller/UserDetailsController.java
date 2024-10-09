package com.crimsonlogic.creditcardapporval.controller;


import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.crimsonlogic.creditcardapporval.entity.UserDetails;
import com.crimsonlogic.creditcardapporval.payload.UserDetailsDto;
import com.crimsonlogic.creditcardapporval.payload.UserEntryForm;
import com.crimsonlogic.creditcardapporval.service.UserDetailsService;


@CrossOrigin("*")
@RestController
@RequestMapping("/userdetails")
public class UserDetailsController {
	
	private static final Logger logger = LoggerFactory.getLogger(UserDetailsController.class);
	
	@Autowired
    private UserDetailsService userDetailsService;

	@Autowired
    private ModelMapper modelMapper;

	 @GetMapping("/customers")
	 public ResponseEntity<List<UserDetailsDto>> getAllCustomers() {
		 	logger.info("Fetching Customer");
	        List<UserDetails> userDetailsList = userDetailsService.getAllCustomer();
	        List<UserDetailsDto> userDetailsDTOs = userDetailsList.stream()
	                .map(userDetails -> modelMapper.map(userDetails, UserDetailsDto.class))
	                .collect(Collectors.toList());
	        
	        return ResponseEntity.ok(userDetailsDTOs);
    }
	 
	 @GetMapping("/sales-managers")
	 public ResponseEntity<List<UserDetailsDto>> getAllSalesManagers() {
	        List<UserDetails> userDetailsList = userDetailsService.getAllSalesManager();
	        List<UserDetailsDto> userDetailsDTOs = userDetailsList.stream()
	                .map(userDetails -> modelMapper.map(userDetails, UserDetailsDto.class))
	                .collect(Collectors.toList());
	        return ResponseEntity.ok(userDetailsDTOs);
	 }
	    
	 @PutMapping("/{userTypeID}/status")
	 public ResponseEntity<String> changeUserStatus(
	            @PathVariable String userTypeID,
	            @RequestParam String status) {
	        // Validate the status
	        if (!status.equalsIgnoreCase("Active") && !status.equalsIgnoreCase("InActive")) {
	            return ResponseEntity.badRequest().body("Invalid status.");
	        }
	        
	        // Change the user status
	        try {
	            userDetailsService.changeStatus(userTypeID, status);
	            logger.info("Status Updated");
	            return ResponseEntity.ok("User status updated successfully.");
	        } catch (RuntimeException e) {
	            return ResponseEntity.status(404).body(e.getMessage());
	        }
	 }
	 
	  @PostMapping("/createuser")
	  @ResponseStatus(HttpStatus.CREATED) // This sets the response status to 201 Created
	  public ResponseEntity<String> createUser(@RequestBody UserEntryForm userEntryForm) {
	        try {
	            userDetailsService.saveUser(userEntryForm);
	            logger.info("Created User");
	            return ResponseEntity.ok("User created successfully.");
	        } catch (Exception e) {
	            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User Credentials Already Exists ");
	        }
	    }
	  
}
