package com.crimsonlogic.creditcardapporval.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.crimsonlogic.creditcardapporval.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User,String> {
	
	 Optional<User> findByUserEmail(String email);
	 
}
