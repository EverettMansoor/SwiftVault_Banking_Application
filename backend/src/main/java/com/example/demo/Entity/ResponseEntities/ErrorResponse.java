package com.example.demo.Entity.ResponseEntities;

public class ErrorResponse {
    private String message;
    private boolean success;

    // Default constructor
    public ErrorResponse() {
    }

    // Constructor with parameters
    public ErrorResponse(String message, boolean success) {
        this.message = message;
        this.success = success;
    }

    // Getter for message
    public String getMessage() {
        return message;
    }

    // Setter for message
    public void setMessage(String message) {
        this.message = message;
    }

    // Getter for success
    public boolean isSuccess() {
        return success;
    }

    // Setter for success
    public void setSuccess(boolean success) {
        this.success = success;
    }
}
