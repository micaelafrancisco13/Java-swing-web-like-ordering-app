/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.projectsample.helloworld;

/**
 *
 * @author Ela
 */

import java.awt.Color;
import javax.swing.JButton;
import javax.swing.plaf.basic.BasicScrollBarUI;

public class My_Scrollbar_UI extends BasicScrollBarUI{
    @Override 
    public void configureScrollBarColors(){
        this.thumbColor = new Color(183, 171, 173);
        this.trackColor = new Color(36, 25, 29);
    }
    
    @Override
    public JButton createDecreaseButton(int orientation) {
        JButton button = super.createDecreaseButton(orientation);
        button.setBackground(new Color(36, 25, 29));
        return button;
    }

    @Override
    public JButton createIncreaseButton(int orientation) {
        JButton button = super.createIncreaseButton(orientation);
        button.setBackground(new Color(36, 25, 29));
        return button;
    }
}