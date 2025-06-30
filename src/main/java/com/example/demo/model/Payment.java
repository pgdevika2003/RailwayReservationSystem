package com.example.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity

public class Payment {

	    @Id
	    @NotBlank(message = "Card number is required")	    
	    @Size(min = 19, max = 19, message = "Card number must be 16 digits")
	    private String cardNumber;

	    @NotBlank(message = "Expiration date is required")
	    @Pattern(regexp = "^(0[1-9]|1[0-2])\\/?([0-9]{2})$", message = "Expiration date must be in the format MM/YY")
	    private String expirationDate;

	    @NotBlank(message = "CVV is required")
	    @Size(min = 3, max = 4, message = "CVV must be between 3 and 4 digits")
	    private String cvv;

	    @NotBlank(message = "Card owner name is required")
	    private String cardOwnerName;

	    // Getters and Setters
	    public String getCardNumber() {
	        return cardNumber;
	    }

	    public void setCardNumber(String cardNumber) {
	        this.cardNumber = cardNumber;
	    }

	    public String getExpirationDate() {
	        return expirationDate;
	    }

	    public void setExpirationDate(String expirationDate) {
	        this.expirationDate = expirationDate;
	    }

	    public String getCvv() {
	        return cvv;
	    }

	    public void setCvv(String cvv) {
	        this.cvv = cvv;
	    }

	    public String getCardOwnerName() {
	        return cardOwnerName;
	    }

	    public void setCardOwnerName(String cardOwnerName) {
	        this.cardOwnerName = cardOwnerName;
	    }
}
