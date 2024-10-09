package com.crimsonlogic.creditcardapporval.repsoitorytesting;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.crimsonlogic.creditcardapporval.entity.CardDetails;
import com.crimsonlogic.creditcardapporval.repository.CardDetailsRepository;

import jakarta.persistence.EntityManager;

@ExtendWith(MockitoExtension.class)
@DataJpaTest
public class CardDetailsRepositoryEntityTesting {
	@Autowired
    private CardDetailsRepository cardDetailsRepository;
	
	@Autowired
	private EntityManager entityManager;
	  
    private CardDetails cardDetails;

    @BeforeEach
    public void setUp() {
        cardDetails = new CardDetails("Visa", 10000, "Visa Credit Card", "Active", "visa.png");
        cardDetailsRepository.save(cardDetails);
    }

    @Test
    void testUpdateCardStatusWithPersistence() {
        // Fetch the existing CardDetails entity
        CardDetails existingCard = entityManager.find(CardDetails.class, cardDetails.getCardType());
        
        // Modify the status directly
        existingCard.setCardStatus("Inactive");
        
        // Persist the changes
        entityManager.merge(existingCard);
        entityManager.flush(); // Flush changes to ensure they are written to the database

        // Assert the changes
        CardDetails updatedCard = cardDetailsRepository.findCardByType("Visa");
        assertEquals("Inactive", updatedCard.getCardStatus());
    }

    @Test
    void testFindByCardStatus() {
        // Act
        List<CardDetails> activeCards = cardDetailsRepository.findByCardStatus("Active");

        // Assert
        assertThat(activeCards).isNotEmpty();
        assertThat(activeCards).contains(cardDetails);
    }

    @Test
    void testFindAllActiveCards() {
        // Act
        List<CardDetails> activeCards = cardDetailsRepository.findAllActiveCards();

        // Assert
        assertThat(activeCards).isNotEmpty();
        assertThat(activeCards).contains(cardDetails);
    }

    @Test
    void testFindCardByType() {
        // Act
        CardDetails foundCard = cardDetailsRepository.findCardByType("Visa");

        // Assert
        assertThat(foundCard).isNotNull();
        assertThat(foundCard.getCardType()).isEqualTo("Visa");
    }
}
