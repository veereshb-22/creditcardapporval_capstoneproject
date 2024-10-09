package com.crimsonlogic.creditcardapporval.controllertesting;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.crimsonlogic.creditcardapporval.controller.UserController;
import com.crimsonlogic.creditcardapporval.controller.UserDetailsController;
import com.crimsonlogic.creditcardapporval.entity.User;
import com.crimsonlogic.creditcardapporval.entity.UserDetails;
import com.crimsonlogic.creditcardapporval.exeception.UserDetailsNotFoundException;
import com.crimsonlogic.creditcardapporval.payload.UserDetailsDto;
import com.crimsonlogic.creditcardapporval.payload.UserDto;
import com.crimsonlogic.creditcardapporval.payload.UserEntryForm;
import com.crimsonlogic.creditcardapporval.service.UserDetailsService;
import com.crimsonlogic.creditcardapporval.service.UserService;

import jakarta.servlet.http.HttpSession;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.any;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(MockitoExtension.class)
public class UserDetailsControllerTesting {
	@Mock
    private UserDetailsService userDetailsService;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private UserDetailsController userDetailsController;

    private UserDetails userDetails;
    
    private UserDetailsDto userDetailsDto;

    @BeforeEach
    public void setUp() {
        userDetails = new UserDetails();
        userDetails.setUserTypeID("UIDI102802011");
        userDetails.setUserName("John");
        userDetails.setUserStatus("Active");

        userDetailsDto = new UserDetailsDto();
        userDetailsDto.setUserTypeID("UIDI102802011");
        userDetailsDto.setUserName("John");
        userDetailsDto.setUserStatus("Active");
    }
/*   
    @Test
    void testGetAllCustomers() {
        // Arrange
        List<UserDetails> mockUsers = Arrays.asList(
            new UserDetailsDto("UIDI102802011", "John Doe", LocalDate.of(1990, 1, 1), "9980910423", "Active", null, "12 main Raj")
        );

        // Mock the service method to return the mock user list
        when(userDetailsService.getAllCustomer()).thenReturn(mockUsers);

        // Act
        ResponseEntity<List<UserDetailsDto>> response = userDetailsController.getAllCustomers();

        // Assert
        List<UserDetailsDto> users = response.getBody();
        assertNotNull(users, "User list should not be null");
        assertFalse(users.isEmpty(), "User list should not be empty");

        UserDetailsDto firstUser = users.get(0);
        assertNotNull(firstUser, "First user should not be null");
        assertNotNull(firstUser.getUserName(), "User name should not be null");
        assertEquals("John Doe", firstUser.getUserName(), "First user's name should be 'John Doe'");
    }
*/
    @Test
    public void testGetAllSalesManagers() {
        // Arrange
        UserDetails mockUserDetails = new UserDetails();
        mockUserDetails.setUserTypeID("UIDI102802011");
        mockUserDetails.setUserName("John");
        mockUserDetails.setUserStatus("Active");
        
        // Mock the service to return the mock user details
        when(userDetailsService.getAllSalesManager()).thenReturn(Arrays.asList(mockUserDetails));
        
        // Mock the mapping
        when(modelMapper.map(mockUserDetails, UserDetailsDto.class))
            .thenReturn(new UserDetailsDto("UIDI102802011", "John", null, null, null, null, null));

        // Act
        ResponseEntity<List<UserDetailsDto>> response = userDetailsController.getAllSalesManagers();

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotEmpty();
        assertThat(response.getBody().get(0).getUserName()).isEqualTo("John");
    }
    @Test
    public void testChangeUserStatus_Success() {
        ResponseEntity<String> response = userDetailsController.changeUserStatus("UIDI102802011", "Active");

        verify(userDetailsService).changeStatus("UIDI102802011", "Active");
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo("User status updated successfully.");
    }

    @Test
    public void testChangeUserStatus_InvalidStatus() {
        ResponseEntity<String> response = userDetailsController.changeUserStatus("UIDI102802011", "InvalidStatus");

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isEqualTo("Invalid status.");
    }

    @Test
    public void testChangeUserStatus_UserNotFound() {
        doThrow(new RuntimeException("User not found")).when(userDetailsService).changeStatus("UIDI102802011", "Active");

        ResponseEntity<String> response = userDetailsController.changeUserStatus("UIDI102802011", "Active");

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isEqualTo("User not found");
    }

    @Test
    public void testCreateUser_Success() {
        UserEntryForm userEntryForm = new UserEntryForm();
        userEntryForm.setUserEmail("test@example.com");
        userEntryForm.setUserPassword("password123");
        userEntryForm.setRoleID("R01");
        userEntryForm.setUserName("John");
        userEntryForm.setUserDob(LocalDate.of(1990, 1, 1));
        userEntryForm.setUserAddress("12 main Raj");
        userEntryForm.setUserPhonenumber("9980910423");
        userEntryForm.setUserStatus("Active");

        ResponseEntity<String> response = userDetailsController.createUser(userEntryForm);

        verify(userDetailsService).saveUser(userEntryForm);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo("User created successfully.");
    }

    @Test
    public void testCreateUser_Failure() {
        UserEntryForm userEntryForm = new UserEntryForm();
        userEntryForm.setUserEmail("test@example.com");
        userEntryForm.setUserPassword("password123");
        userEntryForm.setRoleID("R01");
        userEntryForm.setUserName("John");
        userEntryForm.setUserDob(LocalDate.of(1990, 1, 1));
        userEntryForm.setUserAddress("12 main Raj");
        userEntryForm.setUserPhonenumber("9980910423");
        userEntryForm.setUserStatus("Active");

        doThrow(new RuntimeException("User Credentials Already Exists"))
            .when(userDetailsService).saveUser(Mockito.any(UserEntryForm.class));

        ResponseEntity<String> response = userDetailsController.createUser(userEntryForm);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody().trim()).isEqualTo("User Credentials Already Exists");
    }
}
