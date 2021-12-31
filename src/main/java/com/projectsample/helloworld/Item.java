/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.projectsample.helloworld;

import java.math.BigDecimal;

/**
 *
 * @author Ela
 */
public class Item {
    private int row;
    private int column;
    private String[][] dishName;
    private BigDecimal[][] price;
    private int[][] quantity;
    
    public Item(int row, int column, String dishName, 
                BigDecimal price, int quantity) {
        initializeArrays();
        setRow(row);
        setColumn(column);
        setDishName(dishName);
        setPrice(price);
        setQuantity(quantity);
    }

    private void initializeArrays() {
        if (dishName == null)
            this.dishName = new String[6][4];        
        if (price == null)
            this.price = new BigDecimal[6][4];
        if (quantity == null)
            this.quantity = new int[6][4];
    }
    
    private void setRow(int row) {
        this.row = row;
    }
    
    private void setColumn(int column) {
        this.column = column;
    }
    
    private void setDishName(String dishName) {
        this.dishName[row][column] = dishName;
    }

    private void setPrice(BigDecimal priceInt) {
        this.price[row][column] = priceInt;
    }

    private void setQuantity(int qtyInt) {
        this.quantity[row][column] = qtyInt;
    }

    public String[][] getDishName() {
        return dishName;
    }
    
    public BigDecimal[][] getPriceArray() {
        return price;
    }
}