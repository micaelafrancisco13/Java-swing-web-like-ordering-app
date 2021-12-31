/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.projectsample.helloworld;

import java.math.BigDecimal;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 *
 * @author Ela
 */

public class Validate {
    private static boolean doesEmailExist;
    private static boolean[] isShowPasswordOn;
    
    public static boolean validateName(String name) {
        name = name.replaceAll("\\s", "");
        if (!(name.matches("^[a-zA-Z]*$")))
            return false;
        return true;
    }
    
    public static boolean validateEmail(My_SQL_Connection connection, JTextField emailField) {
        String emailAddress = emailField.getText();
        doesEmailExist = Database_Transaction.doesEmailExist(connection, emailField);
        if (doesEmailExist == true)
            return false;
        else {
            emailAddress = emailAddress.trim();
            System.out.println("emailAddress " + emailAddress);
            if ((!(emailAddress.contains("@"))) || (emailAddress.contains(" ")))
                return false;
        }
        return true;
    }
    
    public static boolean validateContactNumber(String contactNumber) {
        contactNumber = contactNumber.replaceAll("\\s", "");
        if (!(contactNumber.matches("[0-9]+"))  || (contactNumber.length() != 11))
            return false;
        return true;
    }
    
    public static boolean validatePassword(JPasswordField passwordField, JPasswordField confirmPasswordField) {
        if (!(new String(passwordField.getPassword())).equals((new String(confirmPasswordField.getPassword()))))
            return false;
        return true;
    }
    
    public static void setBooleanPasswordPeek(boolean updateValue, int index) {
        if (isShowPasswordOn == null)
            isShowPasswordOn = new boolean[6];
        isShowPasswordOn[index] = updateValue;
    }
    
    public static boolean[] getBoolenPasswordPeek() {
        if (isShowPasswordOn == null)
            isShowPasswordOn = new boolean[6];
        return isShowPasswordOn;
    }

    public static void addToSpecificPanel(int[] tracker, JPanel[][] arrayPanels, JLabel[][] arrayLabels) {
        int row = tracker[0];
        int column = tracker[1];
        JLabel label = arrayLabels[row][column];
        arrayPanels[row][column].add(label);
    }
    
    public void checkIfPanelExisted(JPanel panelToBeDeleted, JLabel itemQuantity) {
        for(int row = 0; row < Cart.getArrayOfPanels().length; ++row) {
            for(int column = 0; column < Cart.getArrayOfPanels()[row].length; ++column) {
                if (!(Cart.getArrayOfPanels()[row][column] == null)) {
                    if (Cart.getArrayOfPanels()[row][column].equals(panelToBeDeleted)) {
                        var tracker = new int[]{row, column};
                        updateCart(tracker);
                        updateItemLabel(itemQuantity);
                    }
                }
            }
        }
    }
        
    public static void updateCart(int[] tracker) {
        int row = tracker[0];
        int column = tracker[1];
        var connection = new My_SQL_Connection();
        Quantity.setQuantity(tracker, 0);
        Cart.setNumberOfItems(Cart.getNumberOfItems() - 1);
        Cart.getArrayOfPanels()[row][column] = null;
        Cost.setTotalCost(Cost.getTotalCost().subtract(Cost.getSubTotalArray()[row][column]));   
        Cost.setSubTotalCost(tracker, new BigDecimal(0.00));
        Item_Details.setQuantityInString(tracker);
        Database_Transaction.removeOrderFromCart(connection, tracker);
    }
    
    public static void updateItemLabel(JLabel itemQuantity) {
        String blankText = "     ";
        Label.setTotalCostString();
        Label.setItemQuantity(itemQuantity);
        Label.setItemsCount(blankText);
        Label.setDeleteGuide(blankText);
    }
    
    public static boolean getEmailBoolean() {
        return doesEmailExist;
    }
}