/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.projectsample.helloworld;

import java.awt.Color;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Ela
 */

public class Colour {
    public static void setToPrimaryButtonColor(JPanel panel, String state) {
        if (state.equals("entered")) {
            changePanelColor(panel, 235, 109, 139);
            changeBorderColor(panel, 255, 204, 153);
        }
        else {
            changePanelColor(panel, 85, 119, 141);
            changeBorderColor(panel, 153, 255, 255);
        }
    }
    
    public static void setToSecondaryButtonColor(JPanel panel, String state) {
        if (state.equals("entered"))
            changePanelColor(panel, 61, 81, 115);
        else
            changePanelColor(panel, 85, 119, 141);
    }
    
    public static void setToTertiaryButtonColor(JPanel panel, JLabel label, String state, String component) {
        if (component.equals("minimizeButton")) {
            if (state.equals("entered")) {
                changePanelColor(panel, 126, 157, 177);
                changeLabelColor(label, 254, 197, 204);
            }
            else {
                changePanelColor(panel, 36, 25, 29);
                changeLabelColor(label, 197, 96, 120);
            }
        }
        else if (component.equals("exitButton")) {
            if (state.equals("entered")) {
                changePanelColor(panel, 172, 3, 60);
                changeLabelColor(label, 254, 197, 204);
            }
            else {
                changePanelColor(panel, 36, 25, 29);
                changeLabelColor(label, 197, 96, 120);
            }
        }
    }
    
    public static void setToDishNameButtonColor(JPanel panel, String state) {
        if (state.equals("entered"))
            changePanelColor(panel, 129, 3, 43);
        else
            changePanelColor(panel, 172, 3, 60);
    }
    
    public static void setToCartItemColor(JPanel panel, String state) {
        if (state.equals("entered"))
            changePanelColor(panel, 27, 18, 21);
        else
            changePanelColor(panel, 36, 25, 29);
    }
    
    private static void changePanelColor(JPanel panel, int red, int green, int blue) {
        panel.setBackground(new Color(red, green, blue));
    }
    
    private static void changeLabelColor(JLabel label, int red, int green, int blue) {
        label.setForeground(new Color(red, green, blue));
    }

    private static void changeBorderColor(JPanel panel, int red, int green, int blue) {
        panel.setBorder(BorderFactory.createEmptyBorder());
        panel.setBorder(BorderFactory.createLineBorder(new Color(red, green, blue), 1));
    }
}