package com.example.demo.Entity.ResponseEntities;

import java.util.ArrayList;
import java.util.List;

public class MultipleObjectsResponse<T> {
    private List<T> objects;
    private String message;
    private boolean success;

    // Default constructor
    public MultipleObjectsResponse() {
        this.objects = new ArrayList<>();
    }

    // Constructor with parameters
    public MultipleObjectsResponse(List<T> objects, String message, boolean success) {
        this.objects = objects;
        this.message = message;
        this.success = success;
    }

    // Getter for objects
    public List<T> getObjects() {
        return objects;
    }

    // Setter for objects
    public void setObjects(List<T> objects) {
        this.objects = objects;
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
