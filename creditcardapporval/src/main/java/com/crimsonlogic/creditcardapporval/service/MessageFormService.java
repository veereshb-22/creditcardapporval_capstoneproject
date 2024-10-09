package com.crimsonlogic.creditcardapporval.service;

import java.util.List;

import com.crimsonlogic.creditcardapporval.entity.MessageForm;

public interface MessageFormService {
	
	public List<MessageForm> getMessagesByApplicationEmail(String Email);
	
	public MessageForm saveMessage(MessageForm messageForm) ; 
}
