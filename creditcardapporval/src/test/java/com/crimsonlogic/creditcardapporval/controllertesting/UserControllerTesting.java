package com.crimsonlogic.creditcardapporval.controllertesting;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.crimsonlogic.creditcardapporval.controller.UserController;
import com.crimsonlogic.creditcardapporval.entity.User;
import com.crimsonlogic.creditcardapporval.exeception.UserDetailsNotFoundException;
import com.crimsonlogic.creditcardapporval.payload.UserDto;
import com.crimsonlogic.creditcardapporval.service.UserService;

import jakarta.servlet.http.HttpSession;

@ExtendWith(MockitoExtension.class)
public class UserControllerTesting {
	@InjectMocks
    private UserController userController;
	
    @Mock
    private UserService userService;

    @Mock
    private HttpSession session;

    private UserDto userDto;
    private User user;

    @BeforeEach
    void setUp() {
        userDto = new UserDto();
        userDto.setUserEmail("test@example.com");
        userDto.setUserPassword("password");

        user = new User(); // Create a valid User object (or set properties if necessary)
        user.setUserEmail(userDto.getUserEmail());
        user.setUserPassword(userDto.getUserPassword());
    }

    @Test
    void testLogin_Success() {
        // Arrange
        when(userService.validateUser(userDto.getUserEmail(), userDto.getUserPassword())).thenReturn(user);
        // Act
        ResponseEntity<User> response = userController.login(userDto, session);
        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(userDto.getUserEmail(), response.getBody().getUserEmail());
        verify(session).setAttribute("userEmail", userDto.getUserEmail());
    }
    	
    @Test
    void testLogin_InvalidCredentials() {
        // Arrange
        when(userService.validateUser(userDto.getUserEmail(), userDto.getUserPassword()))
            .thenThrow(new UserDetailsNotFoundException("Invalid Credentials"));
        // Act
        ResponseEntity<User> response = userController.login(userDto, session);
        // Assert
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertNull(response.getBody());
        verify(session, never()).setAttribute("userEmail", userDto.getUserEmail());
    }
}
