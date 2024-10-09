package com.crimsonlogic.creditcardapporval.serviceimp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crimsonlogic.creditcardapporval.entity.CreditCard;
import com.crimsonlogic.creditcardapporval.exeception.CreditCardNotFoundException;
import com.crimsonlogic.creditcardapporval.repository.CreditCardRepository;
import com.crimsonlogic.creditcardapporval.service.CreditCardService;

@Service
public class CreditCardServiceImp implements CreditCardService {
	
	@Autowired
	CreditCardRepository creditCardRepository;
	@Override
	public List<CreditCard> gettCustomerCreditCard(String userID)throws CreditCardNotFoundException {
		// TODO Auto-generated method stub
		 List<CreditCard> cards = creditCardRepository.findByUserDetails_UserTypeID(userID);
		 if(cards.isEmpty()) {
			  throw new CreditCardNotFoundException("Credit Card Not Found");
		 }
		 return cards;
       
	}
	
}
