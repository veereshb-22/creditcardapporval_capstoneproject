package com.crimsonlogic.creditcardapporval.repsoitorytesting;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.crimsonlogic.creditcardapporval.entity.Role;
import com.crimsonlogic.creditcardapporval.entity.User;
import com.crimsonlogic.creditcardapporval.entity.UserDetails;
import com.crimsonlogic.creditcardapporval.repository.Rolerepository;
import com.crimsonlogic.creditcardapporval.repository.UserDetailsRepository;
import com.crimsonlogic.creditcardapporval.repository.UserRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@ExtendWith(MockitoExtension.class)
@DataJpaTest
public class UserDetailsRepsotoryTesting {
	  	@Autowired
	    private UserDetailsRepository userDetailsRepository;
	    
	  	@PersistenceContext
	  	private EntityManager entityManager;
	  	
	    @Autowired
	    private UserRepository userRepository;
	    
	    @Autowired
	    private Rolerepository rolerepository;
	    
	    private UserDetails userDetails;
	    
	    private UserDetails userDetailssales;
	    
	    private User user1,user2;
	    
	    private Role role,role2;
	    
	

	    @BeforeEach
	    public void setUp() {
	        role = new Role("R01","Customer");
	        role = rolerepository.save(role);
	        role2=new Role("R02","SalesManager");
	        role2=rolerepository.save(role2);
	        user1 = new User();
	        user1.setUserEmail("test@example.com");
	        user1.setUserPassword("password123");
	        user1.setRole(role);
	        user1 = userRepository.save(user1);
	        user2=new User();
	        user2.setUserEmail("sales@gmail.com");
	        user2.setUserPassword("Exmap!W@");
	        user2.setRole(role2);
	        user2=userRepository.save(user2);
	        userDetails = new UserDetails("UIDI102802011", "John", LocalDate.of(1990, 1, 1), "9980910423", "Active", "12 main Raj", user1);
	        userDetailssales = new UserDetails("UIDI102802012", "Jane", LocalDate.of(1992, 2, 2), "9980910424", "Active", "34 second St", user2);
	        userDetails = userDetailsRepository.save(userDetails); // Capture the saved userDetails
	        userDetailssales=userDetailsRepository.save(userDetailssales);
	    }

	    @Test
	    public void testFindAllCustomers() {
	        List<UserDetails> customers = userDetailsRepository.findAllCustomers();
	        assertThat(customers).isNotEmpty();
	    }

	    @Test
	    public void testFindAllSalesManagers() {
	        List<UserDetails> salesManagers = userDetailsRepository.findAllSalesManagers();
	        assertThat(salesManagers).isNotEmpty();
	    }

	    @Test
	    public void testUpdateUserStatus() {
	    	  System.err.print("************************************************************************************************");
	    	System.err.print(userDetails);
	        String userTypeID = userDetails.getUserTypeID(); // Get the generated ID
	        String newStatus = "Inactive";
	        int updatedCount = userDetailsRepository.updateUserStatus(userTypeID, newStatus);
	        assertThat(updatedCount).isEqualTo(1);
	        entityManager.refresh(userDetails);
	        System.err.print(userTypeID+"************************************************************************************************");
	        System.err.print(userDetails);
	      
	        assertThat(userDetails).isNotNull();
	        assertThat(userDetails.getUserStatus()).isEqualTo(newStatus);
	    }

	    @Test
	    public void testIsUserEmailActive() {
	        Boolean isActive = userDetailsRepository.isUserEmailActive("test@example.com");
	        assertThat(isActive).isTrue();
	    }

	    @Test
	    public void testFindByUserEmail() {
	        UserDetails foundUserDetails = userDetailsRepository.findByUserEmail("test@example.com");
	        assertThat(foundUserDetails).isNotNull();
	        assertThat(foundUserDetails.getUserName()).isEqualTo("John");
	    }

	    @Test
	    public void testFindNameByUserEmail() {
	        String userName = userDetailsRepository.findNameByUserEmail("test@example.com");
	        assertThat(userName).isEqualTo("John");
	    }

	    @Test
	    public void testFindUserIDByUserEmail() {
	        String userTypeID = userDetailsRepository.findUserIDByUserEmail("test@example.com");
	        assertThat(userTypeID).isEqualTo(userDetails.getUserTypeID()); // Compare with the actual ID
	   }
}	    
