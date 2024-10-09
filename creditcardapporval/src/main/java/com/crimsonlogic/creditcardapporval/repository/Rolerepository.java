package com.crimsonlogic.creditcardapporval.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.crimsonlogic.creditcardapporval.entity.Role;
@Repository
public interface Rolerepository extends JpaRepository<Role,String > {

}
