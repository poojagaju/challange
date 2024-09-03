package com.dws.challenge.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorDetails {

    // Getters and Setters
    private String errorMessage;
    private String requestedURI;

    // Default constructor
    public ErrorDetails() {
    }

    // Parameterized constructor
    public ErrorDetails(String errorMessage, String requestedURI) {
        this.errorMessage = errorMessage;
        this.requestedURI = requestedURI;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public void setRequestedURI(String requestedURI) {
        this.requestedURI = requestedURI;
    }
}
