/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.projectsample.helloworld;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 *
 * @author Ela
 */
public class Cost {
    private static BigDecimal[][] subTotal;
    private static BigDecimal totalCost;
    private static BigDecimal deliveryFee;
    
    public static void compute(int[] tracker, Item[][] item, int[][] quantity) {
        int row = tracker[0];
        int column = tracker[1];
        totalCost = BigDecimal.ZERO;
        deliveryFee = new BigDecimal(30.00);
        if (subTotal == null)
            subTotal = new BigDecimal[6][4];
        var currentQuantity = new BigDecimal(quantity[row][column]);
        subTotal[row][column] = (currentQuantity.multiply(item[row][column].getPriceArray()[row][column]));
        
        for (int i = 0; i < subTotal.length; ++i) {
            for (int j = 0; j < subTotal[i].length; ++j) {
                if (!(subTotal[i][j] == null))
                    totalCost = totalCost.add(subTotal[i][j]);
            }
        }
        setTotalCost(totalCost.add(deliveryFee));
    }

    public static void setSubTotalCost(int tracker[], BigDecimal updatedSubTotal) {
        int row = tracker[0];
        int column = tracker[1];
        subTotal[row][column] = updatedSubTotal;
    }
    
    public static BigDecimal[][] getSubTotalArray() {
        return subTotal;
    }
    
    public static void setTotalCost(BigDecimal newTotalCost) {
        totalCost = newTotalCost;
    }
    
    public static BigDecimal getTotalCost() {
        return totalCost;
    }

    public static String getTotalCostString() {
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        return decimalFormat.format(getTotalCost());
    } 
}