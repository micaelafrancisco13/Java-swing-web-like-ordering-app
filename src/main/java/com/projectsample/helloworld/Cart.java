/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.projectsample.helloworld;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.math.BigDecimal;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Ela
 */

public class Cart {
    private static JPanel[][] arrayOfPanels;
    private static JLabel[][] arrayOfLabels;
    private static int numberOfItems;
    
    public static void addItemToCart(int[] tracker, String panelLabel, JPanel BGPanel) {
        BGPanel.setLayout(new BoxLayout(BGPanel, BoxLayout.PAGE_AXIS));      
        if (arrayOfPanels == null)
            arrayOfPanels = new JPanel[6][4];
        if (arrayOfLabels == null)
            arrayOfLabels = new JLabel[6][4];
        int row = tracker[0];
        int column = tracker[1];
        if (arrayOfPanels[row][column] == null)
            createPanelContainer(tracker, BGPanel);
        createLabelOnPanel(tracker, panelLabel);
        Validate.addToSpecificPanel(tracker, arrayOfPanels, arrayOfLabels);
        BGPanel.revalidate();
    }
    
    private static void createPanelContainer(int[] tracker, JPanel BGPanel) {
        numberOfItems += 1;
        int row = tracker[0];
        int column = tracker[1];
        JLabel newLabel = new JLabel();
        arrayOfLabels[row][column] = newLabel;
        JPanel newPanel = new JPanel();
        arrayOfPanels[row][column] = newPanel;
        newPanel.setPreferredSize(new Dimension(278, 52));
        newPanel.setBackground(new Color(36, 25, 29)); 
        BGPanel.add(newPanel);
        BGPanel.add(Box.createRigidArea(new Dimension(0, 0)));
    }

    private static void createLabelOnPanel(int[] tracker, String panelLabel) {
        int row = tracker[0];
        int column = tracker[1];
        arrayOfLabels[row][column].setText(panelLabel);
        arrayOfLabels[row][column].setPreferredSize(new Dimension(278, 52));
        arrayOfLabels[row][column].setFont(new Font("Avenir LT Pro 35 Light", Font.PLAIN, 14));
        arrayOfLabels[row][column].setForeground(Color.WHITE);
        arrayOfLabels[row][column].setHorizontalAlignment(JLabel.CENTER);
        arrayOfLabels[row][column].setVerticalAlignment(JLabel.CENTER);
    }
        
    public static void updateQuantityInString(int[] tracker, String updatedLabel) {
        int row = tracker[0];
        int column = tracker[1];
        arrayOfLabels[row][column].setText(updatedLabel);
    }
    
    public static void modifyItemDetails(int[] tracker, String[] labelFormat, BigDecimal priceInt,
                                         JPanel cartBGPanel, JLabel[][] arrayOfQtyLabels,
                                         JLabel[] arrayOfItemLabels, String blankText) {
        int row = tracker[0];
        int column = tracker[1];
        var pesoCostLabel = arrayOfItemLabels[0];
        var itemCountLabel = arrayOfItemLabels[1];
        var deleteGuideLabel = arrayOfItemLabels[2];
        var itemQtyLabel = arrayOfQtyLabels[row][column];
        Quantity.incrementQuantity(tracker, labelFormat, priceInt, cartBGPanel);
        Label.setTotalCostString(pesoCostLabel);
        Label.setItemQuantity(itemQtyLabel);
        Label.setItemsCount(itemCountLabel, blankText);
        Label.setDeleteGuide(deleteGuideLabel, blankText);
        iterate(cartBGPanel, itemQtyLabel);
    }
    
    public static void iterate(JPanel cartBGPanel, JLabel itemQuantity) {
        for(int i = 0; i < Cart.getArrayOfPanels().length; ++i) {
            for(int j = 0; j < Cart.getArrayOfPanels()[i].length; ++j) {
                if (!(Cart.getArrayOfPanels()[i][j] == null)) {
                    Cart.getArrayOfPanels()[i][j].addMouseListener(new My_Mouse_Listener(cartBGPanel, itemQuantity));
                    Cart.getArrayOfPanels()[i][j].setCursor(new Cursor(Cursor.HAND_CURSOR));
                }
            }
        }
    }
    
    public static void resetComponents() {
        arrayOfPanels = null;
        arrayOfLabels = null;
    }
    
    public static JPanel[][] getArrayOfPanels() {
        return arrayOfPanels;
    }
    
    public static JLabel[][] getArrayOfLabels() {
        return arrayOfLabels;
    }
    
    public static void setNumberOfItems(int currentItemsCount) {
        numberOfItems = currentItemsCount;
    }
    
    public static int getNumberOfItems() {
        return numberOfItems;
    }
}