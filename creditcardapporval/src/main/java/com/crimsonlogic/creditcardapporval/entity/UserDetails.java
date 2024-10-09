package com.crimsonlogic.creditcardapporval.entity;

import java.time.LocalDate;

import com.crimsonlogic.creditcardapporval.util.GeneralizedIdGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="user_details")
public class UserDetails {
	
	@Id
	@Column(name="usertype_id")
	private String userTypeID;
	
	@Column(name="user_name")
	private String userName;

	@Column(name="dob")
	private LocalDate userDob;
	
	@Column(name="user_phonenumber",unique = true)
	private String userPhonenumber;
	
	@Column(name="user_status")
	private String userStatus;
	
	@Column(name="user_address")
	private String userAddress;
	
	@OneToOne
	@JoinColumn(name="user_id", referencedColumnName = "user_id")
	private User user;
	
	@PrePersist
	public void prePersist() {
	this.userTypeID=GeneralizedIdGenerator.idGenerartor("UDID");
	}
	
}
