package com.crimsonlogic.creditcardapporval.service;

import java.util.Optional;

import com.crimsonlogic.creditcardapporval.entity.User;
import com.crimsonlogic.creditcardapporval.entity.UserDetails;
import com.crimsonlogic.creditcardapporval.payload.UserDto;

public interface UserService {
	
    
    User findByEmail(String email);
    
    UserDetails findUserDetails(User user);
    
    User login(UserDto userDto);		
	
    User validateUser(String email, String password);
    
   
		
}
