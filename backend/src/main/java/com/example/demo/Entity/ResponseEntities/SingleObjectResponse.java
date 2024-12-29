package com.example.demo.Entity.ResponseEntities;

public class SingleObjectResponse<T> {
    private T object;
    private String message;
    private boolean success;

    // Default constructor
    public SingleObjectResponse() {
    }

    // Constructor with parameters
    public SingleObjectResponse(T object, String message, boolean success) {
        this.object = object;
        this.message = message;
        this.success = success;
    }

    // Getter for object
    public T getObject() {
        return object;
    }

    // Setter for object
    public void setObject(T object) {
        this.object = object;
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
