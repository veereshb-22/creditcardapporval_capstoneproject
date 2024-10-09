package com.crimsonlogic.creditcardapporval.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.crimsonlogic.creditcardapporval.entity.CreditCard;

@Repository
public interface CreditCardRepository extends JpaRepository<CreditCard,String> {
	
	  @Query("SELECT c FROM CreditCard c WHERE c.userDetails.userTypeID = :userTypeID")
	  List<CreditCard> findByUserTypeID(@Param("userTypeID") String userTypeID);
	  
	  List<CreditCard> findByUserDetails_UserTypeID(String userTypeID);
}
