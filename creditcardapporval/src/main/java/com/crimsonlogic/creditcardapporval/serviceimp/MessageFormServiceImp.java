package com.crimsonlogic.creditcardapporval.serviceimp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crimsonlogic.creditcardapporval.entity.MessageForm;
import com.crimsonlogic.creditcardapporval.repository.MessageApplicationForm;
import com.crimsonlogic.creditcardapporval.service.MessageFormService;

@Service
public class MessageFormServiceImp implements MessageFormService{
	
	@Autowired
	MessageApplicationForm messageRepository;
	
	@Override
	public List<MessageForm> getMessagesByApplicationEmail(String Email) {
		// TODO Auto-generated method stub
		 return messageRepository.findMessagesByUserEmail(Email);
	}

	@Override
	public MessageForm saveMessage(MessageForm messageForm) {
		// TODO Auto-generated method stub
		return messageRepository.save(messageForm);
	}

}
