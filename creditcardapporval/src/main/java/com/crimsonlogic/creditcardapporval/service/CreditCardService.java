package com.crimsonlogic.creditcardapporval.service;

import java.util.List;

import com.crimsonlogic.creditcardapporval.entity.CreditCard;
import com.crimsonlogic.creditcardapporval.exeception.CreditCardNotFoundException;

public interface CreditCardService {
	
	public List<CreditCard> gettCustomerCreditCard(String userID)throws CreditCardNotFoundException ;
	
}
