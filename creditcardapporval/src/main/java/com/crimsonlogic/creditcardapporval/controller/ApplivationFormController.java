package com.crimsonlogic.creditcardapporval.controller;


import java.util.List;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.crimsonlogic.creditcardapporval.entity.ApplicationForm;
import com.crimsonlogic.creditcardapporval.payload.ApplicatioFormDto;
import com.crimsonlogic.creditcardapporval.service.ApplicationFormService;

import com.crimsonlogic.creditcardapporval.service.UserDetailsService;


@CrossOrigin("*")
@RestController
@RequestMapping("/application")
public class ApplivationFormController {

    private static final Logger logger = LoggerFactory.getLogger(ApplivationFormController.class);

    @Autowired
    private ApplicationFormService applicationService;
    
    @Autowired 
    private UserDetailsService userDetailsService;
    
    @Autowired
	ModelMapper modelMapper;
    
    @GetMapping("/processing")
    public ResponseEntity<List<ApplicatioFormDto>> getProcessingApplications() {
    	 List<ApplicationForm> applications = applicationService.getAllProcessingApplications("Processing");

    	    // Map the list of ApplicationForm to ApplicatioFormDto
    	    List<ApplicatioFormDto> applicationDtos = applications.stream()
    	            .map(application -> modelMapper.map(application, ApplicatioFormDto.class))
    	            .toList(); // Using Java 16+, or use Collectors.toList() for earlier versions

    	    return ResponseEntity.ok(applicationDtos);
    	
    }
    /**
     * For Sales Manager Fetching Customer who has Submitted the form
     * @return
     */
    @GetMapping("/aftersubmit")
    public ResponseEntity<List<ApplicatioFormDto>> getSubmittedApplications() {
        List<ApplicationForm> applications = applicationService.getAllProcessingApplications("Submitted");

        // Map the list of ApplicationForm to ApplicatioFormDto
        List<ApplicatioFormDto> applicationDtos = applications.stream()
                .map(application -> modelMapper.map(application, ApplicatioFormDto.class))
                .toList(); // Using Java 16+, or use Collectors.toList() for earlier versions

        return ResponseEntity.ok(applicationDtos);
    }
    
    
    @PostMapping("/customerapplicationsubmit")
    public ResponseEntity<String> submitApplication(@RequestParam String accountNumber,@RequestParam String ifscCode,@RequestParam String aadhaarNumber,@RequestParam String panNumber,@RequestParam MultipartFile document, @RequestParam MultipartFile panDocuments,
            @RequestParam int monthlySalary, @RequestParam String gender,@RequestParam String companyName,@RequestParam String companyAddress,@RequestParam String accountType,@RequestParam String userHomeAddress,@RequestParam int creditScore,@RequestParam String CustomerCardTypeOption,@RequestParam String userEmail) {
    	 
    	 try {
    	        String result = applicationService.processApplication( accountNumber,ifscCode,aadhaarNumber,panNumber,document,panDocuments,monthlySalary,gender,companyName, companyAddress,accountType,userHomeAddress,creditScore, CustomerCardTypeOption,userEmail
    	        );
    	        return ResponseEntity.ok(result);
    	    } catch (RuntimeException e) {
    	        logger.error("Error submitting application", e);
    	        return ResponseEntity.status(500).body("Failed to submit application");
    	    }
    }
    
    @GetMapping("/document/{id}")
    public ResponseEntity<String> getDocument(@PathVariable String id) {
        ApplicationForm application = applicationService.findById(id);
        if (application != null && application.getDocumentDetails() != null) {
            logger.info("Document retrieved successfully for ID: {}", id);
            return ResponseEntity.ok(application.getDocumentDetails());
        } else {
            logger.warn("Document not found for ID: {}", id);
            return ResponseEntity.status(404).body("Document not found");
        }
    }
    
    /**
     * 
     * Here it Can Be used to used by Manager and SalesManager to update the status  
     * @param id
     * @param status
     * @return
     */
    
    @PutMapping("/{id}/status")
    public ResponseEntity<String> updateApplicationStatus(@PathVariable String id, @RequestParam String status,@RequestParam String email) {
    	String salesmanager=userDetailsService.findUserNameByEmail(email);
    	applicationService.updateApplicationStatus(id, status,salesmanager);
    	logger.info("Status Updated status of appliction form");
        return ResponseEntity.ok("Status updated to " + status);
    }
    
    @PutMapping("/doc/{id}/status")
    public ResponseEntity<String> updateAdminApplicationStatus(@PathVariable String id, @RequestParam String status) {
    	
    	applicationService.updateAdminApplicationStatus(id, status);
        return ResponseEntity.ok("Status updated to " + status);
    }
    
    @PutMapping("/{id}/card")
    public ResponseEntity<String> updateCardDetails(@PathVariable String id, @RequestParam String cardType,@RequestParam String userEmail) {
    	String email=userEmail;
    	String salesmanager=userDetailsService.findUserNameByEmail(email);
    	System.err.print("**************************************************");
        System.err.print("THE SESSION ATTRIBUTE SALESMAN NAME IS" +salesmanager);
    	applicationService.updateCardDetails(id, cardType,salesmanager);
        return ResponseEntity.ok("Card details updated to " + cardType);
    }
    
    @GetMapping("/specificcustomer") // Endpoint to get application status by email
    public ResponseEntity<List<ApplicationForm>> getApplicationStatusByEmail(@RequestParam String email) {
    	List<ApplicationForm> app=applicationService.getCustomerApplicationStatus(email);
    	return ResponseEntity.ok(app);
    }
    
    @GetMapping("/specifisalesman") // Endpoint to get application status by email
    public ResponseEntity<List<ApplicationForm>> getApplicationStatusForSalesMan(@RequestParam String email) {
    	String name=userDetailsService.findUserNameByEmail(email);
    	System.err.print(name+" NAME OF SALESMANAGER");
    	List<ApplicationForm> app=applicationService.getSalesManagerApplicationStatusByName(name);
    	System.err.println(app+" LIST");
    	return ResponseEntity.ok(app);
    }
}
