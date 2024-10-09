package com.crimsonlogic.creditcardapporval.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.crimsonlogic.creditcardapporval.entity.ApplicationForm;


public interface ApplicationFormService {
	
	void updateCardDetails(String applicationId, String cardType,String salesmanagerName);
	
	List<ApplicationForm> getAllProcessingApplications();
	 
	void clearCardDetails(String applicationId);
	
	void saveApplication(ApplicationForm application);
	 
	void updateApplicationStatus(String id, String status,String salesManagerName);
	 
	ApplicationForm findById(String id);
	
	List<ApplicationForm> getAllProcessingApplications(String status);
	
	List<ApplicationForm> getCustomerApplicationStatus(String email);
	
	void updateAdminApplicationStatus(String id, String status);
	
	public String getApplicationIdByUserTypeId(String userTypeID);

	public String processApplication(String accountNumber, String ifscCode, String aadhaarNumber, String panNumber,
			MultipartFile document, MultipartFile panDocuments, int monthlySalary, String gender, String companyName,
			String companyAddress, String accountType, String userHomeAddress, int creditScore,
			String customerCardTypeOption, String userEmail);
	
	List<ApplicationForm> getSalesManagerApplicationStatusByName(String salesManagerName);
	
}
