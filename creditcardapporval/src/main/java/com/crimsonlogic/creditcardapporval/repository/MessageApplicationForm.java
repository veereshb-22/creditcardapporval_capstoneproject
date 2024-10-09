package com.crimsonlogic.creditcardapporval.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.crimsonlogic.creditcardapporval.entity.MessageForm;

@Repository
public interface MessageApplicationForm extends  JpaRepository<MessageForm, String> {
	  List<MessageForm> findByApplicationForm_CardRequestID(String requestId);
	  
	  @Query("SELECT m FROM MessageForm m " +
		       "JOIN m.applicationForm a " +
		       "JOIN a.userDetail u " +
		       "JOIN u.user u2 " +
		       "WHERE u2.userEmail = :userEmail")
	  List<MessageForm> findMessagesByUserEmail(@Param("userEmail") String userEmail);
	
}
