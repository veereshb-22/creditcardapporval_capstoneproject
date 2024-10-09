package com.crimsonlogic.creditcardapporval.payload;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CardDetailsDto {

	private String cardType;
		
	private int cardLimit;
	
	private String cardDescription;
	
	private String cardStatus;
	
	private String cardImage;
	
	

}
