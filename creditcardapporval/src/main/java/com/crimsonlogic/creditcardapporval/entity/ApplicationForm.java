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
@Table(name="application_form")
public class ApplicationForm {
	 	
		@Id
	    @Column(name="request_id")
	    private String cardRequestID;
	    
	    @Column(name="account_number")
	    private String accountNumber;
	    
	    @Column(name="ifsc_code")
	    private String ifscCode;
	    
	    @Column(name="sales_manager_name")
	    private String salesManagerName;
	    
	    @Column(name="aadhaar_number")
	    private String aadhaarNumber;
	    
	    @Column(name="pan_number")
	    private String panNumber;
	    
	    @Column(name="documents")
	    private String  documentDetails;
	    
	    @Column(name="pandocument")
	    private String panDocuments; // second image to upload
	    
	    
	    @Column(name="paysacle")
	    private int monthlySalary;
	    
	    @Column(name="gender")
	    private String gender;  
	    
	    @Column(name="companyname")
	    private String companyName;
	    
	    @Column(name="companyAddress")
	    private String companyAddress;
	    
	    
	    @Column(name="typeofaccount")
	    private String accountType;  // drop down for salary account , current account, saving account 
	    
	    @Column(name="application_status")
	    private String applicationStatus;
	    
	    @Column(name="user_homeaddress")
	    private String userHomeAddress;
	    
	    @Column(name="cerdit_Score")
	    private Integer creditScore;
	    
	    @ManyToOne
	    @JoinColumn(name="usertype_id", referencedColumnName = "usertype_id")
	    private UserDetails userDetail;
	    
	    @ManyToOne
	    @JoinColumn(name="card_type", referencedColumnName = "card_type")
	    private CardDetails cardDetail;
	    
	    @PrePersist
	    public void prePersist() {
	        this.cardRequestID = GeneralizedIdGenerator.idGenerartor("AFID");
	    }    
}