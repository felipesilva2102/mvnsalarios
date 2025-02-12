package com.felipe.mvnsalarios.beans;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;

@Named
@RequestScoped
public class UserBean {

    private String username;
    private boolean submitted;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isSubmitted() {
        return submitted;
    }

    public void setSubmitted(boolean submitted) {
        this.submitted = submitted;
    }

    public String submit() {
        // Process user input or perform any necessary actions
        submitted = true;
        return null; // Navigate to the same page (refresh)
    }

}
