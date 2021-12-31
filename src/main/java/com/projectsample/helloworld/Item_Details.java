/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.projectsample.helloworld;

import javax.swing.JLabel;

/**
 *
 * @author Ela
 */
public class Item_Details {
    private static JLabel[][] arraysOfQtyLabel;
    private static String quantityInString;
    private static String panelLabel;
    
    public static void setQuantityInString(int[] tracker) {
        int row = tracker[0];
        int column = tracker[1];
        String updatedQuantity = Integer.toString(Quantity.getArrayOfQuantities()[row][column]);
        quantityInString = updatedQuantity;
    }
    
    public static String getQuantityInString() {
        return quantityInString;
    }
    
    public static void setPanelLabel(String[] labelFormat) {
        String dishName = labelFormat[0];
        String whiteSpace = labelFormat[1];
        String priceString = labelFormat[2];
        panelLabel = quantityInString + " × " + dishName + whiteSpace + priceString;
    }
    
    public static String getPanelLabel() {
        return panelLabel;
    }

    public static JLabel[][] getArraysOfQtyLabel() {
        return arraysOfQtyLabel;
    }
}