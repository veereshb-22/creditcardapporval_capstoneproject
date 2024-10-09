package com.crimsonlogic.creditcardapporval.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.crimsonlogic.creditcardapporval.entity.CreditCard;
import com.crimsonlogic.creditcardapporval.exeception.CreditCardNotFoundException;
import com.crimsonlogic.creditcardapporval.repository.UserDetailsRepository;
import com.crimsonlogic.creditcardapporval.service.CreditCardService;

@CrossOrigin("*")
@RestController
@RequestMapping("/creditcard")
public class CreditCardController {
	
	@Autowired
	CreditCardService creditCardService;
	
	@Autowired
	UserDetailsRepository  userDetailsRepository;
	
	@GetMapping("/getcustomercard")
    	public ResponseEntity<List<CreditCard>>  getAllCards(@RequestParam  String email) {
		String userID=userDetailsRepository.findUserIDByUserEmail(email);
        List<CreditCard> cards;
      
        try {
            cards = creditCardService.gettCustomerCreditCard(userID);
            return ResponseEntity.ok(cards);
        } catch (CreditCardNotFoundException e) {
            return ResponseEntity.status(404).body(null); // You can customize the response
        }
	}      
}
