/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.projectsample.helloworld;

import javax.swing.JLayeredPane;
import javax.swing.JPanel;

/**
 *
 * @author Ela
 */
public class Card {
    public static void changeCard(JLayeredPane currentPanel, JPanel nextPanel) {
        currentPanel.removeAll();
        currentPanel.add(nextPanel);
        currentPanel.repaint();
        currentPanel.revalidate();
    }
}
