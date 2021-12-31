/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.projectsample.helloworld;

import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

/**
 *
 * @author Ela
 */
public class Password {
    public static void showPassword(JLayeredPane passwordPeekBGPanel, 
                                    JPanel nextPanel, 
                                    JPasswordField passwordField){
        decryptPassword(passwordField);
        Card.changeCard(passwordPeekBGPanel, nextPanel);
    }
    
    public static void hidePassword(JLayeredPane passwordPeekBGPanel, 
                                    JPanel nextPanel, 
                                    JPasswordField passwordField){
        encryptPassword(passwordField);
        Card.changeCard(passwordPeekBGPanel, nextPanel);
    }
    
    private static void encryptPassword(JPasswordField passwordField) {
        passwordField.setEchoChar('\u2022');
    }
    
    private static void decryptPassword(JPasswordField passwordField) {
        passwordField.setEchoChar('\u0000');
    }
}
