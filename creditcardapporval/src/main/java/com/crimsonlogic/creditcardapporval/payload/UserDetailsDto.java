package com.crimsonlogic.creditcardapporval.payload;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class UserDetailsDto {
	
	private String userTypeID;
	
	private String userName;
	
	private LocalDate userDob;
	
	private String userPhonenumber;
	
	private String userAddress;
	
	private UserDto user;
	
	private String userStatus;
	
}
