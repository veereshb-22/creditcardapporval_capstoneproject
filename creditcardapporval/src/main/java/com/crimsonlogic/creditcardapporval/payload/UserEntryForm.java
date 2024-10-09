package com.crimsonlogic.creditcardapporval.payload;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class UserEntryForm {
	
		private String userID;          // For User entity
	   
		private String userEmail;       // For User entity
	    
		private String userPassword;     // For User entity
	    
		private String userName;        // For UserDetails entity
	    
		private LocalDate userDob;      // For UserDetails entity
	    
		private String userPhonenumber;  // For UserDetails entity
	    
		private String userStatus;      // For UserDetails entity
	    
		private String roleID ;
		
		private String userAddress;
}
