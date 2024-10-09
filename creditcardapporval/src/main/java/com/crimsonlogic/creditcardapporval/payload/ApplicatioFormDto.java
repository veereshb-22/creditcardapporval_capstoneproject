package com.crimsonlogic.creditcardapporval.payload;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApplicatioFormDto {

	private String cardRequestID;
	
	private String accountNumber;
	
	private String  ifscCode;
	
	private String salesManagerName;
	
	private String aadharNumber;
	
	private String panNumber;

	private UserDetailsDto userDetails;
	
	private CardDetailsDto cardDetails;

    private String  documentDetails;
    
    private String panDocuments; // second image to upload
    
    private int monthlySalary=0;
    
    private String gender;  // drop down for age
       
    private String companyName;
    
    private String companyAddress;
      
    private String accountType;  // drop down for salary account , current account, saving account 
     
    private String applicationStatus;
    
    private String userHomeAddress;
    
    private int creditScore;
    
    
    
}
