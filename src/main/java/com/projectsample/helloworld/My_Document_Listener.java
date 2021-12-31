/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.projectsample.helloworld;

import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 *
 * @author Ela
 */
public class My_Document_Listener implements DocumentListener {
    private JLayeredPane passwordPeekBGPanel;
    private JPanel firstBGPanel;
    private JPanel secondBGPanel;
    private JPanel thirdBGPanel;
    private JPasswordField passwordField;
    private int index;
    public My_Document_Listener(JLayeredPane passwordPeekBGPanel, JPanel[] cardPanels, 
                                JPasswordField passwordField, int index) {
        this.passwordPeekBGPanel = passwordPeekBGPanel;
        this.firstBGPanel = cardPanels[0];
        this.secondBGPanel = cardPanels[1];
        this.thirdBGPanel = cardPanels[2];
        this.passwordField = passwordField;
        this.index = index;
    }
    
    @Override
    public void changedUpdate(DocumentEvent event) {
        checkPassword();
    }
    
    @Override
    public void insertUpdate(DocumentEvent event) {
        checkPassword();
    }
    
    @Override
    public void removeUpdate(DocumentEvent event) {
        checkPassword();
    }
    
    private void checkPassword() {
        boolean isPasswordPeekOn = Validate.getBoolenPasswordPeek()[index];
        if (new String(passwordField.getPassword()).isBlank()) {
            Card.changeCard(passwordPeekBGPanel, thirdBGPanel);
            Validate.setBooleanPasswordPeek(false, index);
        }           
        else {
            if (isPasswordPeekOn == true)
                Password.showPassword(passwordPeekBGPanel, secondBGPanel, passwordField);
            else
                Password.hidePassword(passwordPeekBGPanel, firstBGPanel, passwordField);
        }
    }
}