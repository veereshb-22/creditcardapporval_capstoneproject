package com.crimsonlogic.creditcardapporval.servicetesting;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


import com.crimsonlogic.creditcardapporval.entity.Role;
import com.crimsonlogic.creditcardapporval.entity.User;
import com.crimsonlogic.creditcardapporval.entity.UserDetails;
import com.crimsonlogic.creditcardapporval.exeception.UserDetailsNotFoundException;
import com.crimsonlogic.creditcardapporval.payload.UserEntryForm;
import com.crimsonlogic.creditcardapporval.repository.Rolerepository;
import com.crimsonlogic.creditcardapporval.repository.UserDetailsRepository;
import com.crimsonlogic.creditcardapporval.repository.UserRepository;
import com.crimsonlogic.creditcardapporval.serviceimp.UserDetailsServiceImp;

@ExtendWith(MockitoExtension.class)
public class UserDetailsServiceTesting {
	 	@InjectMocks
	    private UserDetailsServiceImp userDetailsService;

	    @Mock
	    private UserDetailsRepository userDetailsRepository;

	    @Mock
	    private Rolerepository rolerepository;

	    @Mock
	    private UserRepository userRepository;

	    @BeforeEach
	    public void setUp() {
	        MockitoAnnotations.openMocks(this);
	    }

	    @Test
	    public void testGetAllCustomer_NoCustomersFound() {
	        when(userDetailsRepository.findAllCustomers()).thenReturn(Collections.emptyList());

	        Exception exception = assertThrows(UserDetailsNotFoundException.class, () -> {
	            userDetailsService.getAllCustomer();
	        });

	        assertEquals("No customers found.", exception.getMessage());
	    }

	    @Test
	    public void testGetAllCustomer_CustomersFound() {
	        UserDetails userDetails = new UserDetails();
	        when(userDetailsRepository.findAllCustomers()).thenReturn(Collections.singletonList(userDetails));

	        List<UserDetails> customers = userDetailsService.getAllCustomer();
	        assertFalse(customers.isEmpty());
	    }

	    @Test
	    public void testGetAllSalesManager_NoSalesManagersFound() {
	        when(userDetailsRepository.findAllSalesManagers()).thenReturn(Collections.emptyList());

	        Exception exception = assertThrows(UserDetailsNotFoundException.class, () -> {
	            userDetailsService.getAllSalesManager();
	        });

	        assertEquals("No Salesmanager found.", exception.getMessage());
	    }

	    @Test
	    public void testGetAllSalesManager_SalesManagersFound() {
	        UserDetails userDetails = new UserDetails();
	        when(userDetailsRepository.findAllSalesManagers()).thenReturn(Collections.singletonList(userDetails));

	        List<UserDetails> salesManagers = userDetailsService.getAllSalesManager();
	        assertFalse(salesManagers.isEmpty());
	    }

	    @Test
	    public void testChangeStatus_UserNotFound() {
	        when(userDetailsRepository.updateUserStatus(any(), any())).thenReturn(0);

	        Exception exception = assertThrows(RuntimeException.class, () -> {
	            userDetailsService.changeStatus("nonexistentId", "Active");
	        });

	        assertEquals("User not found with UserTypeID: nonexistentId", exception.getMessage());
	    }

	    @Test
	    public void testChangeStatus_UserFound() {
	        when(userDetailsRepository.updateUserStatus(any(), any())).thenReturn(1);

	        assertDoesNotThrow(() -> {
	            userDetailsService.changeStatus("existingId", "Active");
	        });
	    }

	    @Test
	    public void testSaveUser() {
	        UserEntryForm userEntryForm = new UserEntryForm();
	        userEntryForm.setUserEmail("test@example.com");
	        userEntryForm.setUserPassword("password");
	        userEntryForm.setRoleID("R01");
	        userEntryForm.setUserName("Test User");

	        Role role = new Role();
	        role.setRoleID("R01");
	        when(rolerepository.findById(userEntryForm.getRoleID())).thenReturn(Optional.of(role));

	        userDetailsService.saveUser(userEntryForm);

	        verify(userRepository).save(any(User.class));
	        verify(userDetailsRepository).save(any(UserDetails.class));
	    }

	    @Test
	    public void testFindUserNameByEmail() {
	        when(userDetailsRepository.findNameByUserEmail("test@example.com")).thenReturn("Test User");

	        String userName = userDetailsService.findUserNameByEmail("test@example.com");
	        assertEquals("Test User", userName);
	    }

	    @Test
	    public void testFindUserDetailsByEmail() {
	        UserDetails userDetails = new UserDetails();
	        when(userDetailsRepository.findByUserEmail("test@example.com")).thenReturn(userDetails);

	        UserDetails foundDetails = userDetailsService.findUserDetailsByEmail("test@example.com");
	        assertNotNull(foundDetails);
	    }

	    @Test
	    public void testFindUserIdByUserEmail() {
	        when(userDetailsRepository.findUserIDByUserEmail("test@example.com")).thenReturn("userId123");

	        String userId = userDetailsService.findUserIdByUserEmail("test@example.com");
	        assertEquals("userId123", userId);
	    }
}
