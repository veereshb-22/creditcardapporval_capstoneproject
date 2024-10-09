package com.crimsonlogic.creditcardapporval.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.crimsonlogic.creditcardapporval.entity.CardDetails;



@Repository
public interface CardDetailsRepository extends JpaRepository<CardDetails,String> {
	
	 @Modifying
	 @Transactional
	 @Query("UPDATE CardDetails c SET c.cardStatus = ?1 WHERE c.cardType = ?2")
	 int updateCardStatus(String newStatus, String cardType);
	 
	 List<CardDetails> findByCardStatus(String status);
	 
	 
	 @Query("SELECT c FROM CardDetails c WHERE c.cardStatus = 'Active'")
	 List<CardDetails> findAllActiveCards();
	 
	 
	 @Query("SELECT c FROM CardDetails c WHERE  c.cardType = ?1")
	 CardDetails findCardByType(String cardType);
}
