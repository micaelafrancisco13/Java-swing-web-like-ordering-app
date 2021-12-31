/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.projectsample.helloworld;

import java.math.BigDecimal;
import javax.swing.JPanel;

/**
 *
 * @author Ela
 */
public class Quantity {
    private static Item[][] item;
    private static int quantity[][];
    private static String whiteSpace[][];
    private static String state; 
    
    public static void incrementQuantity(int[] tracker, String[] labelFormat, 
                                         BigDecimal priceInt, JPanel cartBGPanel) {
        int row = tracker[0];
        int column = tracker[1];
        String dishName = labelFormat[0];
        
        if (whiteSpace == null)
            whiteSpace = new String[6][4];
        if (quantity == null)
            quantity = new int[6][4];
        if (item == null)
            item = new Item[6][4];
        if (item[row][column] == null)
            item[row][column] = new Item(row, column, dishName, priceInt, 
                                         quantity[row][column]);
        if (state == null)
            quantity[row][column] += 1;
            
        whiteSpace[row][column] = labelFormat[1];
        Item_Details.setQuantityInString(tracker);
        Item_Details.setPanelLabel(labelFormat);
        Cart.addItemToCart(tracker, Item_Details.getPanelLabel(), cartBGPanel);
        Cost.compute(tracker, item, quantity);
    }
    
    public static void decrementQuantity(int[] tracker, String[] labelFormat, 
                                         JPanel cartBGPanel) {
        int row = tracker[0];
        int column = tracker[1];

        if (item == null)
            item = new Item[6][4];
        
        if (!(item[row][column] == null) && (!(Cart.getArrayOfPanels()[row][column] == null))) {                        
            if (quantity[row][column] > 0) {
                quantity[row][column] = (quantity[row][column] - 1);
                Cost.compute(tracker, item, quantity);
            }
            if ((quantity[row][column]) == 0) {
                quantity[row][column] = 0;
                cartBGPanel.remove(Cart.getArrayOfPanels()[row][column]);
                cartBGPanel.repaint();
                cartBGPanel.revalidate();
                Cart.setNumberOfItems(Cart.getNumberOfItems() - 1);
                Cart.getArrayOfPanels()[row][column] = null;
                Cost.compute(tracker, item, quantity);
                if (Cart.getNumberOfItems() <= 1)
                    Cart.setNumberOfItems(0);
                item[row][column] = null;
            }
            Item_Details.setQuantityInString(tracker);
            Item_Details.setPanelLabel(labelFormat);
            Cart.updateQuantityInString(tracker, Item_Details.getPanelLabel());
        }
    }    
    
    public static Item[][] getArrayOfItems() {
        return item;
    }
    
    public static void setQuantity(int[] tracker, int updatedQuantity) {
        int row = tracker[0];
        int column = tracker[1];
        if (quantity == null)
            quantity = new int[6][4];
        quantity[row][column] = updatedQuantity;
    }
    
    public static void setSavedQuantity(int[] tracker, int updatedQuantity, String currentState) {
        state = currentState;
        int row = tracker[0];
        int column = tracker[1];
        if (quantity == null)
            quantity = new int[6][4];
        quantity[row][column] = updatedQuantity;
    }
    
    public static void setState(String updatedState) {
        state = updatedState;
    }
    
    public static int[][] getArrayOfQuantities() {
        return quantity;
    }
    
    public static String[][] getArrayOfWhiteSpaces() {
        return whiteSpace;
    }
}