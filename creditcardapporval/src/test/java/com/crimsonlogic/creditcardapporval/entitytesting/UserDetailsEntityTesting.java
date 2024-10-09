package com.crimsonlogic.creditcardapporval.entitytesting;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import org.hibernate.exception.ConstraintViolationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.dao.DataIntegrityViolationException;
import com.crimsonlogic.creditcardapporval.entity.Role;
import com.crimsonlogic.creditcardapporval.entity.User;
import com.crimsonlogic.creditcardapporval.entity.UserDetails;


@DataJpaTest
public class UserDetailsEntityTesting {
	
	 @Autowired
	    private TestEntityManager entityManager;

	    private User user;
	    
	    private UserDetails userDetails;

	    @BeforeEach
	    void setup() {
	        // Create a Role for the user
	        Role role = new Role("R01", "Customer");

	        // Create a User
	        user = new User();
	        user.setUserEmail("test@example.com");
	        user.setUserPassword("password");
	        user.setRole(role);
	        
	        // Save the User to the database
	        entityManager.persist(role);
	        entityManager.persist(user);

	        // Create UserDetails
	        userDetails = new UserDetails();
	        userDetails.setUserName("Test User");
	        userDetails.setUserDob(LocalDate.of(2000, 1, 23));
	        userDetails.setUserPhonenumber("1234567890");
	        userDetails.setUserStatus("Active");
	        userDetails.setUserAddress("123 Test St");
	        userDetails.setUser(user);
	    }

	    @Test
	    void testUserDetailsEntity() {
	        // Save UserDetails
	        UserDetails savedUserDetails = entityManager.persistAndFlush(userDetails);

	        // Retrieve UserDetails
	        UserDetails foundUserDetails = entityManager.find(UserDetails.class, savedUserDetails.getUserTypeID());

	        // Assertions
	        assertNotNull(foundUserDetails);
	        assertEquals(savedUserDetails.getUserName(), foundUserDetails.getUserName());
	        assertEquals(savedUserDetails.getUserDob(), foundUserDetails.getUserDob());
	        assertEquals(savedUserDetails.getUserPhonenumber(), foundUserDetails.getUserPhonenumber());
	        assertEquals(savedUserDetails.getUserStatus(), foundUserDetails.getUserStatus());
	        assertEquals(savedUserDetails.getUserAddress(), foundUserDetails.getUserAddress());
	    }

	    @Test
	    void testUniqueEmailConstraint() {
	        // Persist the first user with a unique email
	        entityManager.persistAndFlush(user);

	        // Create a new User with the same email
	        User duplicateUser = new User();
	        duplicateUser.setUserEmail("test@example.com"); // Same email as the first user
	        duplicateUser.setUserPassword("anotherPassword");
	        duplicateUser.setRole(user.getRole()); // Same role for the duplicate

	        // Attempt to save the duplicate User and expect a DataIntegrityViolationException
	        Exception exception = assertThrows(DataIntegrityViolationException.class, () -> {
	            try {
	                entityManager.persistAndFlush(duplicateUser);
	            } catch (ConstraintViolationException e) {
	                throw new DataIntegrityViolationException("Unique constraint violation", e);
	            }
	        });
	        assertTrue(exception.getMessage().contains("Unique constraint violation"));
	    }

}
