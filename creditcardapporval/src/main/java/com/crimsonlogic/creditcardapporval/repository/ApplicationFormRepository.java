package com.crimsonlogic.creditcardapporval.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.crimsonlogic.creditcardapporval.entity.ApplicationForm;

@Repository
public interface ApplicationFormRepository  extends JpaRepository<ApplicationForm, String> {
		
	 List<ApplicationForm> findByApplicationStatus(String status);
	 
	 @Query("SELECT af FROM ApplicationForm af WHERE af.userDetail.userTypeID = :userTypeID")
	  List<ApplicationForm> findByUserTypeID(@Param("userTypeID") String userTypeID);
	 
	 @Query("SELECT a.cardRequestID FROM ApplicationForm a WHERE a.userDetail.userTypeID = :userTypeID")
	 String findApplicationIdByUserTypeId(@Param("userTypeID") String userTypeID);
	 
	 @Query("SELECT a FROM ApplicationForm a WHERE a.salesManagerName = :salesManagerName")
	 List<ApplicationForm> findSalesManagerApplicationByName(@Param("salesManagerName") String salesManagerName);
}	
	