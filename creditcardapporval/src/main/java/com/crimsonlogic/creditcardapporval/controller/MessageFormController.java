package com.crimsonlogic.creditcardapporval.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.crimsonlogic.creditcardapporval.entity.ApplicationForm;
import com.crimsonlogic.creditcardapporval.entity.MessageForm;
import com.crimsonlogic.creditcardapporval.service.ApplicationFormService;
import com.crimsonlogic.creditcardapporval.service.MessageFormService;
import com.crimsonlogic.creditcardapporval.service.UserDetailsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@CrossOrigin("*")
@RestController
@RequestMapping("/messages")
public class MessageFormController {
	private static final Logger logger = LoggerFactory.getLogger(MessageFormController.class);
	@Autowired
	MessageFormService messageFormService;
	
	@Autowired
	ApplicationFormService applicationFormService;
	
	@Autowired
	UserDetailsService userDetailsService;
	
	 @GetMapping("/{userEmail}")
	    public ResponseEntity<List<MessageForm>> getMessages(@PathVariable String userEmail) {
		 	List<MessageForm> messages=messageFormService.getMessagesByApplicationEmail(userEmail);
		 	logger.info("Fetching messages for user email: {}",messages);
	       
	       
	        return ResponseEntity.ok(messages);
	    }
	 
	  @PostMapping("/postmessage")
	  public ResponseEntity<MessageForm> createMessage(@RequestBody MessageForm messageForm) {
	        // Validate incoming messageForm
		  if (messageForm.getMessageInfo() == null || // Updated to messageInfo
			        messageForm.getApplicationForm() == null || 
			        messageForm.getApplicationForm().getCardRequestID() == null) {
			        
			        return ResponseEntity.badRequest().build(); // Return bad request if necessary fields are missing
			    }

			    MessageForm savedMessage = messageFormService.saveMessage(messageForm);
			    return ResponseEntity.status(HttpStatus.CREATED).body(savedMessage);
	  }
}
