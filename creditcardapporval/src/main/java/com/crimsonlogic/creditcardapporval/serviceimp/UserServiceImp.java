package com.crimsonlogic.creditcardapporval.serviceimp;

import java.util.Base64;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crimsonlogic.creditcardapporval.entity.User;
import com.crimsonlogic.creditcardapporval.entity.UserDetails;
import com.crimsonlogic.creditcardapporval.exeception.UserDetailsNotFoundException;
import com.crimsonlogic.creditcardapporval.payload.UserDto;
import com.crimsonlogic.creditcardapporval.repository.UserRepository;
import com.crimsonlogic.creditcardapporval.service.UserService;
import com.crimsonlogic.creditcardapporval.util.GeneralizedIdGenerator;
import com.crimsonlogic.creditcardapporval.repository.UserDetailsRepository;

@Service
public class UserServiceImp implements UserService {
	
	private GeneralizedIdGenerator gen;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserDetailsRepository userDetailsRepository;



    @Override
    public User findByEmail(String email) {
        // Find the user by email
        Optional<User> userOptional = userRepository.findByUserEmail(email);
        return userOptional.orElse(null); // Return user if found, otherwise null
    }

    @Override
    public UserDetails findUserDetails(User user) {
        // Find user details by user entity
        return userDetailsRepository.findByUser(user);
    }

    @Override
    public User login(UserDto userDto) {
        // Attempt to find user by email
        Optional<User> userOptional = userRepository.findByUserEmail(userDto.getUserEmail());
        
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            // Check if the password matches (no encoding for now)
            if (user.getUserPassword().equals(userDto.getUserPassword())) {
                // Fetch user details
                UserDetails userDetails = userDetailsRepository.findByUser(user);
                // Check if user status is active
                if (userDetails != null && "Active".equals(userDetails.getUserStatus())) {
                    return user; // User is authenticated
                }
            }
        }
        return null; // Invalid credentials or inactive status
    }

	@Override
	public User validateUser(String email, String password) {
		 Optional<User> userOptional = userRepository.findByUserEmail(email) ;
		 gen=new GeneralizedIdGenerator();
		 String user_password=gen.securePassword(password);
	        if (userOptional.isPresent()) {
	            User user = userOptional.get();
	            if (user.getUserPassword().equals(user_password) && userDetailsRepository.isUserEmailActive(email)) {
	                return user;
	            } else {
	                throw new UserDetailsNotFoundException("Invalid Credentails");
	            }
	        } else {
	            throw new UserDetailsNotFoundException("Invalid Credentails UNT");
	        }
	        
	    }


	
	
}
