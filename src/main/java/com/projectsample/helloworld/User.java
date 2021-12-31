/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.projectsample.helloworld;

import javax.swing.JComboBox;
import javax.swing.JPasswordField;

/**
 *
 * @author Ela
 */
public class User {
    private String firstName;
    private String lastName;
    private String emailAddress;
    private String contactNumber;
    private String password;
    private String completeAddress;
    private String barangay;
    
    User(String firstName, String lastName, String emailAddress,
         String contactNumber, JPasswordField passwordField,
         String completeAddress, JComboBox jComboBox) {
        setFirstName(firstName);
        setLastName(lastName);
        setEmailAddress(emailAddress);
        setContactNumber(contactNumber);
        setPassword(passwordField);
        setCompleteAddress(completeAddress);
        setBarangay(jComboBox);
    }

    private void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    private void setLastName(String lastName) {
        this.lastName = lastName;
    }

    private void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
        Database_Transaction.setEmailAddress(emailAddress);
    }

    private void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    private void setPassword(JPasswordField passwordField) {
        this.password = new String(passwordField.getPassword());
    }

    private void setCompleteAddress(String completeAddress) {
        this.completeAddress = completeAddress;
    }

    private void setBarangay(JComboBox jComboBox) {
        this.barangay = jComboBox.getSelectedItem().toString();
    }
}