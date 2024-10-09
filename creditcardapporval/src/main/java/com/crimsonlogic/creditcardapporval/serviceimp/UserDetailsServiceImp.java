package com.crimsonlogic.creditcardapporval.serviceimp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crimsonlogic.creditcardapporval.entity.Role;
import com.crimsonlogic.creditcardapporval.entity.User;
import com.crimsonlogic.creditcardapporval.entity.UserDetails;
import com.crimsonlogic.creditcardapporval.exeception.UserDetailsNotFoundException;
import com.crimsonlogic.creditcardapporval.payload.UserEntryForm;
import com.crimsonlogic.creditcardapporval.repository.Rolerepository;
import com.crimsonlogic.creditcardapporval.repository.UserDetailsRepository;
import com.crimsonlogic.creditcardapporval.repository.UserRepository;
import com.crimsonlogic.creditcardapporval.service.UserDetailsService;
import com.crimsonlogic.creditcardapporval.util.GeneralizedIdGenerator;

@Service
public class UserDetailsServiceImp implements UserDetailsService {
	
	@Autowired
	UserDetailsRepository  userDetailsRepository;
	
	@Autowired
	Rolerepository rolerepository;
	
	@Autowired
	UserRepository userRepository;
	
	
	private GeneralizedIdGenerator gen;
	@Override
	public List<UserDetails> getAllCustomer() {
		 List<UserDetails> customers = userDetailsRepository.findAllCustomers();
	        if (customers.isEmpty()) {
	            throw new UserDetailsNotFoundException("No customers found.");
	        }
	        return customers;
	}
	
	@Override
	public List<UserDetails> getAllSalesManager() {
		List<UserDetails> salesmanager = userDetailsRepository.findAllSalesManagers();
        if (salesmanager.isEmpty()) {
            throw new UserDetailsNotFoundException("No Salesmanager found.");
        }
        return salesmanager;
	}
	
	   public void changeStatus(String userTypeID, String newStatus) {
	        int updatedRows = userDetailsRepository.updateUserStatus(userTypeID, newStatus);
	        if (updatedRows == 0) {
	            throw new RuntimeException("User not found with UserTypeID: " + userTypeID);
	        }
	    }

	@Override
	public void saveUser(UserEntryForm userEntryForm) {
		// TODO Auto-generated method stub
		System.out.println("User Entry Form: " + userEntryForm);
		Role role = rolerepository.findById(userEntryForm.getRoleID())
	            .orElseThrow(() -> new RuntimeException("Role not found with ID: " + userEntryForm.getRoleID()));
		// Create User 
		rolerepository.save(role);
		User user = new User();
        user.setUserEmail(userEntryForm.getUserEmail());
        gen=new GeneralizedIdGenerator();
        String pass=gen.securePassword(userEntryForm.getUserPassword());
        user.setUserPassword(pass);
        user.setRole(role);
        userRepository.save(user);	
        // Create and save the UserDetails entity
        UserDetails userDetails = new UserDetails();
        userDetails.setUserName(userEntryForm.getUserName());
        userDetails.setUserDob(userEntryForm.getUserDob());
        userDetails.setUserAddress(userEntryForm.getUserAddress());
        userDetails.setUserPhonenumber(userEntryForm.getUserPhonenumber());
        userDetails.setUserStatus(userEntryForm.getUserStatus());
        userDetails.setUser(user); // Link User to UserDetails
        userDetailsRepository.save(userDetails);
    }
	
	
	@Override
	public String findUserNameByEmail(String email) {
		return userDetailsRepository.findNameByUserEmail(email);
		
	}
	
	@Override
	public UserDetails findUserDetailsByEmail(String email) {
		// TODO Auto-generated method stub
		return userDetailsRepository.findByUserEmail(email);
	}

	@Override
	public String findUserIdByUserEmail(String email) {
		return userDetailsRepository.findUserIDByUserEmail(email);  
	}
}
