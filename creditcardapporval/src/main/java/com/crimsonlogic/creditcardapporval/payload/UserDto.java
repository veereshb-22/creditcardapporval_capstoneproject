package com.crimsonlogic.creditcardapporval.payload;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
	
	private String userID;
	
	private String userEmail;
	
	private String userPassword;
	
	private RoleDto role; 
}
