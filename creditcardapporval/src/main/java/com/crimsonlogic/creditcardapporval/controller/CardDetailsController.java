package com.crimsonlogic.creditcardapporval.controller;


import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.crimsonlogic.creditcardapporval.entity.CardDetails;
import com.crimsonlogic.creditcardapporval.payload.CardDetailsDto;
import com.crimsonlogic.creditcardapporval.service.CardDetailsService;

@CrossOrigin("*")
@RestController
@RequestMapping("/carddetails")
public class CardDetailsController {

		@Autowired 
	    private CardDetailsService cardDetailsService;
		
		@Autowired
		private ModelMapper modelMapper;
		
		@PostMapping(value = "/addcard")
	    public ResponseEntity<CardDetails> createCard(
	            @RequestParam("cardType") String cardType,
	            @RequestParam("cardLimit") int cardLimit,
	            @RequestParam("cardDescription") String cardDescription,
	            @RequestParam("cardStatus") String cardStatus,
	            @RequestParam("cardImage") MultipartFile cardImage) {
		        CardDetails cardDetails = new CardDetails();
		        cardDetails.setCardType(cardType);
		        cardDetails.setCardLimit(cardLimit);
		        cardDetails.setCardDescription(cardDescription);
		        cardDetails.setCardStatus(cardStatus);
		        
	        String uploadDir = "D:/springbootproject/creditcardapporval/src/main/resources/static/images/"; // Change this path as needed
	        try {
	            // Ensure the upload directory exists
	            File dir = new File(uploadDir);
	            if (!dir.exists()) {
	                dir.mkdirs(); // Create the directory if it doesn't exist
	            }
	            
	            // Store the image file
	            String imageFileName = cardImage.getOriginalFilename();
	            String imagePath = uploadDir + imageFileName;
	            cardImage.transferTo(new File(imagePath)); // Save the file to the server
	           
	            // Store the string path of the image in the database
	            cardDetails.setCardImage("/images/" + imageFileName);
	            
	            // Save card details to the database
	            CardDetails createdCard = cardDetailsService.createCard(cardDetails);
	            return ResponseEntity.ok(createdCard);
	            
	        } catch (Exception e) {
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	        }
	    }
	    @GetMapping("/getcard")
	    public ResponseEntity<List<CardDetailsDto>> getAllCards() {
	    	 List<CardDetails> cards = cardDetailsService.getAllCards();
	    	 List<CardDetailsDto> cardDetailsDTOs = cards.stream()
	    	            .map(cardDetails -> modelMapper.map(cardDetails, CardDetailsDto.class))
	    	            .collect(Collectors.toList());
	    	 return ResponseEntity.ok(cardDetailsDTOs);
	    }
	    
	    @GetMapping("/getactivecards")
	    public ResponseEntity<List<CardDetailsDto>> getAllActiveCards() {
	        List<CardDetails> cards = cardDetailsService.getAllActiveCards();
	        List<CardDetailsDto> cardDetailsDTOs = cards.stream()
    	            .map(cardDetails -> modelMapper.map(cardDetails, CardDetailsDto.class))
    	            .collect(Collectors.toList());
    	 return ResponseEntity.ok(cardDetailsDTOs);
	      
	    }
	    /**
	     * 
	     * @param cardType
	     * @return
	     */
	    	
	    @GetMapping("/{cardType}")
	    public ResponseEntity<CardDetails> getCardByType(@PathVariable String cardType) {
	        CardDetails card = cardDetailsService.getCardByType(cardType)
	                .orElseThrow(() -> new RuntimeException("Card not found"));
	        return ResponseEntity.ok(card);
	    }

	    @PutMapping("/{cardType}/status")
	    public ResponseEntity<CardDetails> changeStatus(
	            @PathVariable String cardType, @RequestParam String newStatus) {
	        CardDetails updatedCard = cardDetailsService.changeCardStatus(cardType, newStatus);
	        return ResponseEntity.ok(updatedCard);
	    }
}
