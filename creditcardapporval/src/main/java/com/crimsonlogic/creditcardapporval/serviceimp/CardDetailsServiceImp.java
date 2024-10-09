package com.crimsonlogic.creditcardapporval.serviceimp;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crimsonlogic.creditcardapporval.entity.CardDetails;
import com.crimsonlogic.creditcardapporval.repository.CardDetailsRepository;
import com.crimsonlogic.creditcardapporval.service.CardDetailsService;

@Service
public class CardDetailsServiceImp implements CardDetailsService{
	
	@Autowired
	CardDetailsRepository cardDetailsRepository;
	
	@Override
	public CardDetails createCard(CardDetails cardDetails) {
		 return cardDetailsRepository.save(cardDetails);
	}

	@Override
	public List<CardDetails> getAllCards() {
		 return cardDetailsRepository.findAll();
	}

	@Override
	public Optional<CardDetails> getCardByType(String cardType) {
		// TODO Auto-generated method stub
		   return cardDetailsRepository.findById(cardType);
	}
	
	@Override
	public CardDetails changeCardStatus(String cardType, String newStatus) {
		 int updatedCount = cardDetailsRepository.updateCardStatus(newStatus, cardType);
	        if (updatedCount > 0) {
	            return cardDetailsRepository.findById(cardType).orElseThrow(() -> new RuntimeException("Card not found"));
	        } else {
	            throw new RuntimeException("Card status update failed");
	        }
	    }

	@Override
	public List<CardDetails> getAllActiveCards() {
		return cardDetailsRepository.findAllActiveCards();
	}

	@Override
	public List<CardDetails> getAllActiveCardsForAssign() {
		// TODO Auto-generated method stub
	 return cardDetailsRepository.findAllActiveCards();
	}

	@Override
	public CardDetails getCardBycardID(String cardType) {
		// TODO Auto-generated method stub
		
	return cardDetailsRepository.findCardByType(cardType);
	}
	
	
	
}
