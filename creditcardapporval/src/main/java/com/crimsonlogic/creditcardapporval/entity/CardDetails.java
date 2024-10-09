package com.crimsonlogic.creditcardapporval.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="card_details")

public class CardDetails {
	@Id
	@Column(name="card_type")
	private String cardType;
	
	@Column(name="card_limit")
	private int cardLimit;
	
	@Column(name="card_description")
	private String cardDescription;
	
	@Column(name="card_status")
	private String cardStatus;
	
	@Column(name="card_image")
	private String cardImage;
	
}
