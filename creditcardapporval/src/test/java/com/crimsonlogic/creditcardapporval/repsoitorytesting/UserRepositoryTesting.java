package com.crimsonlogic.creditcardapporval.repsoitorytesting;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.crimsonlogic.creditcardapporval.entity.Role;
import com.crimsonlogic.creditcardapporval.entity.User;
import com.crimsonlogic.creditcardapporval.repository.Rolerepository;
import com.crimsonlogic.creditcardapporval.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
@DataJpaTest
public class UserRepositoryTesting {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private Rolerepository roleRepository;

    private User user;
    private Role role;

    @BeforeEach
    void setup() {
        role = new Role("R01", "User");
        role = roleRepository.save(role); // Persist the Role first

        user = new User();
        user.setUserEmail("test@example.com");
        user.setUserPassword("password");
        user.setRole(role); // Set the role after it's saved
    }

    @Test
    void testSaveFindUser() {
        // Save the User
        User savedUser = userRepository.save(user);

        // Find user by email
        User foundUser = userRepository.findByUserEmail(savedUser.getUserEmail()).orElse(null);

        // Assertions
        assertNotNull(foundUser);
        assertEquals(savedUser.getUserEmail(), foundUser.getUserEmail());
        assertEquals(savedUser.getRole().getRoleID(), foundUser.getRole().getRoleID());
    }
    
}
