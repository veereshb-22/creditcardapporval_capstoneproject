package com.crimsonlogic.creditcardapporval.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreditCardDto {
	
	private String cardNumber;

	private String cardStatus;
	
	private String requestLimit;
	
	private CardDetailsDto cardDetails; 
	
	private UserDetailsDto userDetails;
	
	private String cardIssueDate;
	
	private String cardExpiryDate;
	
	private int creditScore; 
}	
