package com.crimsonlogic.creditcardapporval.serviceimp;

import java.io.File;
import java.io.IOException;
import java.util.List;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.crimsonlogic.creditcardapporval.entity.ApplicationForm;
import com.crimsonlogic.creditcardapporval.entity.CardDetails;
import com.crimsonlogic.creditcardapporval.entity.CreditCard;
import com.crimsonlogic.creditcardapporval.entity.UserDetails;
import com.crimsonlogic.creditcardapporval.repository.ApplicationFormRepository;
import com.crimsonlogic.creditcardapporval.repository.CardDetailsRepository;
import com.crimsonlogic.creditcardapporval.repository.CreditCardRepository;
import com.crimsonlogic.creditcardapporval.repository.UserDetailsRepository;
import com.crimsonlogic.creditcardapporval.service.ApplicationFormService;
import com.crimsonlogic.creditcardapporval.util.GeneralizedIdGenerator;

@Service
public class ApplicationFormServiceImp implements ApplicationFormService {

	@Autowired
	ApplicationFormRepository applicationRepository;
	
	@Autowired
	CardDetailsRepository cardDetailsRepository;
	
	@Autowired
	CreditCardRepository creditCardRepository;
	
	@Autowired
	UserDetailsRepository userDetailsRepository;
	
	@Autowired
	UserDetailsServiceImp   userDetailsService;
	
	private CreditCard creditCard;
	
	@Override
	public List<ApplicationForm> getAllProcessingApplications() {
		return applicationRepository.findByApplicationStatus("Processing");
	}

	@Override
	public void saveApplication(ApplicationForm application) {
		 applicationRepository.save(application);
	}

	@Override
	public void updateApplicationStatus(String id, String status,String salesManagerName) {
		 ApplicationForm application = applicationRepository.findById(id).orElse(null);
	        if (application != null) {
	        	application.setSalesManagerName(salesManagerName);
	            application.setApplicationStatus(status);
	            
	            applicationRepository.save(application);
	        }	
	}
	
	@Override
	public ApplicationForm findById(String id) {
		return applicationRepository.findById(id).orElse(null);
	}

	@Override
	public List<ApplicationForm> getAllProcessingApplications(String status) {
		 return applicationRepository.findByApplicationStatus(status);
	}
	
	@Override
	public void updateCardDetails(String applicationId, String cardType,String salesmanager) {
		 ApplicationForm application = applicationRepository.findById(applicationId).orElse(null);
	        if (application != null) {
	            CardDetails cardDetails = cardDetailsRepository.findById(cardType).orElse(null);
	            if (cardDetails != null) {
	            	application.setSalesManagerName(salesmanager);
	                application.setCardDetail(cardDetails);
	                applicationRepository.save(application);
	            }
	        }
	}
	
	@Override
	public void clearCardDetails(String applicationId) {
		ApplicationForm application = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new RuntimeException("Application not found"));
        application.setCardDetail(null); // Clear card details
        applicationRepository.save(application);	
	}

	@Override
	public List<ApplicationForm> getCustomerApplicationStatus(String email) {
		String userId=userDetailsRepository.findUserIDByUserEmail(email);
		List<ApplicationForm> getapplication=applicationRepository.findByUserTypeID(userId);
		return getapplication;
	}

	@Override
	public void updateAdminApplicationStatus(String id, String status) {
		ApplicationForm application = applicationRepository.findById(id).orElse(null);
        if (application != null) {
            application.setApplicationStatus(status);
            System.err.println("**********************************************");
            System.err.println(" STARTING UPDATING THE DATA");
            if(status.equals("Approved")){
            	creditCard=new CreditCard();
            	creditCard.setCardDetails(application.getCardDetail());
            	creditCard.setCardStatus("Active");
            	creditCard.setRequestLimit("0");
            	creditCard.setUserDetails(application.getUserDetail());
            	System.err.print("**********************************************");
 	            System.err.print(" CREATING THE CARD  UPDATING THE DATA");
 	            System.err.print("CARD DETIALS IS "+creditCard);
 	            creditCardRepository.save(creditCard);
            	}
            applicationRepository.save(application);
        }	
}

	@Override
	public String getApplicationIdByUserTypeId(String userTypeID) {
		// TODO Auto-generated method stub
		return applicationRepository.findApplicationIdByUserTypeId(userTypeID);
	}

	@Override
	public String processApplication(String accountNumber, String ifscCode, String aadhaarNumber, String panNumber,
			MultipartFile document, MultipartFile panDocuments, int monthlySalary, String gender, String companyName,
			String companyAddress, String accountType, String userHomeAddress, int creditScore,
			String customerCardTypeOption, String userEmail) {
		
		ApplicationForm application = new ApplicationForm();
        UserDetails userdetails = userDetailsRepository.findByUserEmail(userEmail);
        CardDetails card = cardDetailsRepository.findCardByType(customerCardTypeOption);        
        application.setUserDetail(userdetails);
        application.setAccountNumber(accountNumber);
        application.setIfscCode(ifscCode);
        application.setAadhaarNumber(GeneralizedIdGenerator.maskNumber(aadhaarNumber));
        application.setPanNumber(GeneralizedIdGenerator.maskNumber(panNumber));
        application.setMonthlySalary(monthlySalary);
        application.setGender(gender);
        application.setCompanyName(companyName);
        application.setCompanyAddress(companyAddress);
        application.setAccountType(accountType);
        application.setCreditScore(creditScore);
        application.setUserHomeAddress(userHomeAddress);
        application.setCardDetail(card);
        application.setApplicationStatus("Submitted");

        // Define the upload directory
        String uploadDir = "D:/springbootproject/creditcardapporval/src/main/resources/static/images/";
        try {
            File dir = new File(uploadDir);
            if (!dir.exists()) {
                dir.mkdirs(); // Create the directory if it doesn't exist
            }

            // Save the documents
            String documentPath = uploadDir + document.getOriginalFilename();
            String documentPath2 = uploadDir + panDocuments.getOriginalFilename();
            document.transferTo(new File(documentPath));
            panDocuments.transferTo(new File(documentPath2));

            // Set the document URLs
            application.setDocumentDetails("/images/" + document.getOriginalFilename());
            application.setPanDocuments("/images/" + panDocuments.getOriginalFilename());

            applicationRepository.save(application);
            return "Application submitted successfully";
        } catch (IOException e) {
            throw new RuntimeException("Failed to submit application", e);
        }
    
	}

	@Override
	public List<ApplicationForm> getSalesManagerApplicationStatusByName(String salesManagerName) {
		// TODO Auto-generated method stub
		return applicationRepository.findSalesManagerApplicationByName(salesManagerName);
	}

       
}
