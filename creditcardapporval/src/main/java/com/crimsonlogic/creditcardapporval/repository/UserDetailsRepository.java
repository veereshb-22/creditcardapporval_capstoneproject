package com.crimsonlogic.creditcardapporval.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.crimsonlogic.creditcardapporval.entity.User;
import com.crimsonlogic.creditcardapporval.entity.UserDetails;

@Repository
public interface UserDetailsRepository extends JpaRepository<UserDetails, String>{
	
	 @Query("SELECT ud FROM UserDetails ud WHERE ud.user.role.roleType = 'Customer'")
	 List<UserDetails> findAllCustomers();
	    
	 @Query("SELECT ud FROM UserDetails ud WHERE ud.user.role.roleType = 'SalesManager'")
	 List<UserDetails> findAllSalesManagers();
	 
	 @Modifying
	 @Transactional
	 @Query("UPDATE UserDetails u SET u.userStatus = :newStatus WHERE u.userTypeID = :userTypeID")
	 int updateUserStatus(String userTypeID, String newStatus);
	
	 @Query("SELECT ud.userStatus = 'Active' FROM UserDetails ud WHERE ud.user.userEmail = :email")
	 Boolean isUserEmailActive(@Param("email") String email); 
	 
	 UserDetails findByUser(User user);
	 
	 @Query("SELECT ud FROM UserDetails ud WHERE ud.user.userEmail = :email")
	 UserDetails findByUserEmail(@Param("email") String email);
	 
	 @Query("SELECT ud.userName FROM UserDetails ud WHERE ud.user.userEmail = :email")
	 String findNameByUserEmail(@Param("email") String email);
	 
	 @Query("SELECT userTypeID FROM UserDetails ud WHERE ud.user.userEmail = :email")
	 String findUserIDByUserEmail(@Param("email") String email);
}
