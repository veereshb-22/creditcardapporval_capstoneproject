package com.crimsonlogic.creditcardapporval.entity;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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
@NoArgsConstructor
@AllArgsConstructor
@Table(name="credit_card")
public class CreditCard {
	
	@Id
	@Column(name="card_number")
	private String cardNumber;
	
	@Column(name="card_status")
	private String cardStatus;
	
	@Column(name="card_requestlimit")
	private String requestLimit;
	
	@Column(name="issued_card_number")
	private String issuedcardNumber;
	
	@Column(name="card_issued_date")
	private String cardIssueDate;
	
	@Column(name="card_expired_date")
	private String cardExpiryDate;
	
	@ManyToOne
	@JoinColumn(name="card_type",referencedColumnName ="card_type")
	private CardDetails cardDetails; 
	
	
	@ManyToOne
	@JoinColumn(name="usertype_id",referencedColumnName ="usertype_id")
	private UserDetails userDetails;
	
	
	@PrePersist
	public void prePersist() {
	this.cardNumber=GeneralizedIdGenerator.idGenerartor("CCID");
	this.issuedcardNumber=GeneralizedIdGenerator.cardNumberGenerate();
	LocalDate currentDate = LocalDate.now();
    this.cardIssueDate = currentDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")); // Format to String
     // Set the expiry date to 5 years from the issue date
    this.cardExpiryDate = currentDate.plusYears(5).format(DateTimeFormatter.ofPattern("yyyy-MM-dd")); // Format to String
	}

}
