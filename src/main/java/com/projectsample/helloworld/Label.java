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
public class Label {
    private static JLabel itemCount;
    private static JLabel deleteGuide;
    private static JLabel totalCostLabel;
    
    public static void setTotalCostString(JLabel currentTotalCostLabel) {
        totalCostLabel = currentTotalCostLabel;
        if (Cart.getNumberOfItems() == 0)
            totalCostLabel.setText("PHP 0.00");
        else
            totalCostLabel.setText("PHP " + Cost.getTotalCostString());
    }

    public static void setTotalCostString() {
        if (Cart.getNumberOfItems() == 0)
            totalCostLabel.setText("PHP 0.00");
        else
            totalCostLabel.setText("PHP " + Cost.getTotalCostString());
    }  
    
    public static void setItemQuantity(JLabel itemQuantity) {
        if (Item_Details.getQuantityInString() == null)
            itemQuantity.setText("0");
        else
            itemQuantity.setText(Item_Details.getQuantityInString());
    }
    
    public static void setItemsCount(JLabel updatedItemCount, String blankText) {
        itemCount = updatedItemCount;
        if (Cart.getNumberOfItems() == 0)
            itemCount.setText(blankText);
        else if (Cart.getNumberOfItems() == 1)
            itemCount.setText(Integer.toString(Cart.getNumberOfItems()) + " item");
        else
            itemCount.setText(Integer.toString(Cart.getNumberOfItems()) + " items");
    }
    
    public static void setItemsCount(String blankText) {
        if (Cart.getNumberOfItems() == 0)
            itemCount.setText(blankText);
        else if (Cart.getNumberOfItems() == 1)
            itemCount.setText(Integer.toString(Cart.getNumberOfItems()) + " item");
        else
            itemCount.setText(Integer.toString(Cart.getNumberOfItems()) + " items");
    }
    
    public static void setDeleteGuide(JLabel updatedDeleteGuide, String blankText) {
        deleteGuide = updatedDeleteGuide;
        if (Cart.getNumberOfItems() == 0)
            deleteGuide.setText(blankText);
        else
            deleteGuide.setText("Click an item to remove it.");
    }  
    
    public static void setDeleteGuide(String blankText) {
        if (Cart.getNumberOfItems() == 0)
            deleteGuide.setText(blankText);
        else
            deleteGuide.setText("Click an item to remove it.");
    }  
}