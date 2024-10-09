package com.crimsonlogic.creditcardapporval.entitytesting;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.crimsonlogic.creditcardapporval.payload.CardDetailsDto;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class CardDetailsEntityTesting {
	 
	 @Test
	    void testCardDetailsDtoConstructorAndGetters() {
	        // Arrange
	        String expectedCardType = "Visa";
	        int expectedCardLimit = 10000;
	        String expectedCardDescription = "Visa Credit Card";
	        String expectedCardStatus = "Active";
	        String expectedCardImage = "visa.png";

	        // Act
	        CardDetailsDto cardDetailsDto = new CardDetailsDto(expectedCardType, expectedCardLimit, expectedCardDescription, expectedCardStatus, expectedCardImage);

	        // Assert
	        assertEquals(expectedCardType, cardDetailsDto.getCardType());
	        assertEquals(expectedCardLimit, cardDetailsDto.getCardLimit());
	        assertEquals(expectedCardDescription, cardDetailsDto.getCardDescription());
	        assertEquals(expectedCardStatus, cardDetailsDto.getCardStatus());
	        assertEquals(expectedCardImage, cardDetailsDto.getCardImage());
	    }

	
	 @Test
	 void testSetters() {
	     // Arrange
	     CardDetailsDto cardDetailsDto = new CardDetailsDto();
	
	     // Act
	     cardDetailsDto.setCardType("MasterCard");
	     cardDetailsDto.setCardLimit(15000);
	     cardDetailsDto.setCardDescription("MasterCard Credit Card");
	     cardDetailsDto.setCardStatus("Active");
	     cardDetailsDto.setCardImage("mastercard.png");
	
	     // Assert
	     assertEquals("MasterCard", cardDetailsDto.getCardType());
	     assertEquals(15000, cardDetailsDto.getCardLimit());
	     assertEquals("MasterCard Credit Card", cardDetailsDto.getCardDescription());
	     assertEquals("Active", cardDetailsDto.getCardStatus());
	     assertEquals("mastercard.png", cardDetailsDto.getCardImage());
	 }
}	
