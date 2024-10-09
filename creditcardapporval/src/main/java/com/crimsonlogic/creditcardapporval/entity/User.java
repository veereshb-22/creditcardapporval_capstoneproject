package com.crimsonlogic.creditcardapporval.entity;


import com.crimsonlogic.creditcardapporval.util.GeneralizedIdGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name= "user_data")

public class User {
	
	

	@Id
	@Column(name="user_id")
	private String userID;
	
	@Column(name="user_email", unique=true)
	private String userEmail;
	
	@Column(name="user_password")
	private String userPassword;
	
	@ManyToOne
	@JoinColumn(name="role_id",referencedColumnName = "role_id")
	private Role role; 
	
	@PrePersist
	public void prePersist() {
	this.userID=GeneralizedIdGenerator.idGenerartor("USID");
	}
}


