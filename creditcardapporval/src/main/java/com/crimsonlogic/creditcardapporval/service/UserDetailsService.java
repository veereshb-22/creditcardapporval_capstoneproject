package com.crimsonlogic.creditcardapporval.service;

import java.util.List;

import com.crimsonlogic.creditcardapporval.entity.UserDetails;
import com.crimsonlogic.creditcardapporval.payload.UserEntryForm;

public interface UserDetailsService {

	List<UserDetails> getAllCustomer();
	
	List<UserDetails> getAllSalesManager();
	
	void changeStatus(String userTypeID, String newStatus);
	
	public void saveUser(UserEntryForm userEntryForm);
	
	String findUserNameByEmail(String email);
	
	UserDetails findUserDetailsByEmail(String email);
	
	String findUserIdByUserEmail(String emial);
	
}
