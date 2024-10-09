package com.crimsonlogic.creditcardapporval.service;

import java.util.List;
import java.util.Optional;

import com.crimsonlogic.creditcardapporval.entity.CardDetails;

public interface CardDetailsService {
	
	public CardDetails createCard(CardDetails cardDetails) ;
	
	public List<CardDetails> getAllCards();
	
	public Optional<CardDetails> getCardByType(String cardType);
	 
	public CardDetails changeCardStatus(String cardType, String newStatus); 
	
	public List<CardDetails> getAllActiveCards();
	  
	public List<CardDetails> getAllActiveCardsForAssign();
	
	public CardDetails getCardBycardID(String carddType);
}
