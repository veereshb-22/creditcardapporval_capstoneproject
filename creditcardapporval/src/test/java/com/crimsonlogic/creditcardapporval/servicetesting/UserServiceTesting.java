package com.crimsonlogic.creditcardapporval.servicetesting;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.crimsonlogic.creditcardapporval.entity.Role;
import com.crimsonlogic.creditcardapporval.entity.User;
import com.crimsonlogic.creditcardapporval.entity.UserDetails;
import com.crimsonlogic.creditcardapporval.exeception.UserDetailsNotFoundException;
import com.crimsonlogic.creditcardapporval.payload.UserDto;
import com.crimsonlogic.creditcardapporval.repository.UserDetailsRepository;
import com.crimsonlogic.creditcardapporval.repository.UserRepository;
import com.crimsonlogic.creditcardapporval.serviceimp.UserServiceImp;

@ExtendWith(MockitoExtension.class)
public class UserServiceTesting {
	 	@Mock
	    private UserRepository userRepository;

	    @Mock
	    private UserDetailsRepository userDetailsRepository;

	    @InjectMocks
	    private UserServiceImp userService;

	    private User user;
	    private Role role;
	    private UserDto userDto;

	    @BeforeEach
	    void setUp() {
	        // Initialize Role
	        role = new Role("R01", "Customer");

	        // Initialize User
	        user = new User("USID1", "test@example.com", "password", role);

	        // Initialize UserDto
	        userDto = new UserDto();
	        userDto.setUserEmail("test@example.com");
	        userDto.setUserPassword("password");
	    }

	    @Test
	    void testFindByEmail_UserExists() {
	        // Arrange
	        when(userRepository.findByUserEmail(user.getUserEmail())).thenReturn(Optional.of(user));

	        // Act
	        User foundUser = userService.findByEmail(user.getUserEmail());

	        // Assert
	        assertNotNull(foundUser);
	        assertEquals(user.getUserEmail(), foundUser.getUserEmail());
	    }

	    @Test
	    void testFindByEmail_UserDoesNotExist() {
	        // Arrange
	        when(userRepository.findByUserEmail(user.getUserEmail())).thenReturn(Optional.empty());

	        // Act
	        User foundUser = userService.findByEmail(user.getUserEmail());

	        // Assert
	        assertNull(foundUser);
	    }

	    @Test
	    void testFindUserDetails() {
	        // Arrange
	        UserDetails userDetails = new UserDetails(); // Assume UserDetails is properly initialized
	        when(userDetailsRepository.findByUser(user)).thenReturn(userDetails);

	        // Act
	        UserDetails foundUserDetails = userService.findUserDetails(user);

	        // Assert
	        assertNotNull(foundUserDetails);
	        assertEquals(userDetails, foundUserDetails);
	    }

	    @Test
	    void testLogin_ValidCredentials() {
	        // Arrange
	        when(userRepository.findByUserEmail(userDto.getUserEmail())).thenReturn(Optional.of(user));
	        UserDetails userDetails = new UserDetails();
	        userDetails.setUserStatus("Active");
	        when(userDetailsRepository.findByUser(user)).thenReturn(userDetails);

	        // Act
	        User loggedInUser = userService.login(userDto);

	        // Assert
	        assertNotNull(loggedInUser);
	        assertEquals(user.getUserEmail(), loggedInUser.getUserEmail());
	    }

	    @Test
	    void testLogin_InvalidCredentials() {
	        // Arrange
	        when(userRepository.findByUserEmail(userDto.getUserEmail())).thenReturn(Optional.of(user));

	        // Act
	        userDto.setUserPassword("wrongPassword");
	        User loggedInUser = userService.login(userDto);

	        // Assert
	        assertNull(loggedInUser);
	    }

	    @Test
	    void testLogin_InactiveUser() {
	        // Arrange
	        when(userRepository.findByUserEmail(userDto.getUserEmail())).thenReturn(Optional.of(user));
	        UserDetails userDetails = new UserDetails();
	        userDetails.setUserStatus("Inactive");
	        when(userDetailsRepository.findByUser(user)).thenReturn(userDetails);

	        // Act
	        User loggedInUser = userService.login(userDto);

	        // Assert
	        assertNull(loggedInUser);
	    }

	    @Test
	    void testValidateUser_ValidUser() {
	        // Arrange
	        when(userRepository.findByUserEmail(user.getUserEmail())).thenReturn(Optional.of(user));
	        when(userDetailsRepository.isUserEmailActive(user.getUserEmail())).thenReturn(true);

	        // Act
	        User validatedUser = userService.validateUser(user.getUserEmail(), user.getUserPassword());

	        // Assert
	        assertNotNull(validatedUser);
	        assertEquals(user.getUserEmail(), validatedUser.getUserEmail());
	    }

	    @Test
	    void testValidateUser_InvalidCredentials() {
	        // Arrange
	        when(userRepository.findByUserEmail(user.getUserEmail())).thenReturn(Optional.of(user));
	        when(userDetailsRepository.isUserEmailActive(user.getUserEmail())).thenReturn(false);

	        // Act & Assert
	        assertThrows(UserDetailsNotFoundException.class, () -> {
	            userService.validateUser(user.getUserEmail(), user.getUserPassword());
	        });
	    }

	    @Test
	    void testValidateUser_UserNotFound() {
	        // Arrange
	        when(userRepository.findByUserEmail(user.getUserEmail())).thenReturn(Optional.empty());

	        // Act & Assert
	        assertThrows(UserDetailsNotFoundException.class, () -> {
	            userService.validateUser(user.getUserEmail(), user.getUserPassword());
	        });
	    }
	
}
