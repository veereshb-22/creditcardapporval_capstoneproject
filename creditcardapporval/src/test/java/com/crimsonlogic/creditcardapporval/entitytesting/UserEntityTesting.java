package com.crimsonlogic.creditcardapporval.entitytesting;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.dao.DataIntegrityViolationException;

import com.crimsonlogic.creditcardapporval.entity.Role;
import com.crimsonlogic.creditcardapporval.entity.User;



@DataJpaTest
public class UserEntityTesting {
	
	@Autowired
    private TestEntityManager entityManager;
	
	private  User user;
    
	private Role role;
	
	@BeforeEach
	void setup1() {
	role = new Role("R00", "roletype")	;
	user = new User();
    user.setUserEmail("test@example.com");
    user.setUserPassword("password");
    user.setRole(role);
	}
	@Test
    void testUserEntity() {
        // Save Role
        entityManager.persist(role);

        // Save the User
        User savedUser = entityManager.persistAndFlush(user);

        // Retrieve the User
        User foundUser = entityManager.find(User.class, savedUser.getUserID());

        // Assertions
        assertNotNull(foundUser);
        assertEquals(savedUser.getUserEmail(), foundUser.getUserEmail());
        assertEquals(savedUser.getRole().getRoleID(), foundUser.getRole().getRoleID());     
    }
	
	@Test
	void testUniqueEmailConstraint() {
	    // Save the Role
	    entityManager.persist(role);
	    
	    // Save the first User
	    entityManager.persistAndFlush(user);
	    
	    // Create another User with the same email
	    User duplicateUser = new User();
	    duplicateUser.setUserEmail("test@example.com"); // Same email as the first user
	    duplicateUser.setUserPassword("anotherPassword");
	    duplicateUser.setRole(role);
	    
	    // Attempt to save the duplicate User and expect a DataIntegrityViolationException
	    Exception exception = assertThrows(DataIntegrityViolationException.class, () -> {
	        entityManager.persistAndFlush(duplicateUser);
	    });

	    // Check if the exception message contains the expected text
	    assertEquals("could not execute statement; SQL [insert into user_data (role_id,user_email,user_password,user_id) values (?,?,?,?)]; constraint [UKHKH7JNJRGPVPBW556V7FQ0HO6_INDEX_B] violated", exception.getCause().getMessage());
	}

	 
}
