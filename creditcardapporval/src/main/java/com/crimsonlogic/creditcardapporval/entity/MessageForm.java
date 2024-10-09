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
@NoArgsConstructor
@AllArgsConstructor
@Table(name="message")
public class MessageForm {
		@Id
		@Column(name="message_id")
		private String messageID;
		
		@Column(name="message_info", length = 500)
		private String messageInfo;
		
		@ManyToOne
		@JoinColumn(name="request_id", referencedColumnName="request_id" )
		ApplicationForm applicationForm;
		
		@PrePersist
	    public void prePersist() {
	        this.messageID = GeneralizedIdGenerator.idGenerartor("MEID");
	    } 
}
