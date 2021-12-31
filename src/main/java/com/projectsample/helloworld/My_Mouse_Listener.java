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
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Ela
 */

public class My_Mouse_Listener implements MouseListener{ 
    private JPanel cartBGPanel;
    private JLabel itemQuantity;
    
    private JPanel newOrderPanel;
    private JPanel orderHistoryPanel;
    private String state;
    private My_SQL_Connection connection;
    
    public My_Mouse_Listener(JPanel cartBGPanel, JLabel itemQuantity) {
        this.cartBGPanel = cartBGPanel;
        this.itemQuantity = itemQuantity;
    }
    
    public My_Mouse_Listener(JPanel newOrderPanel, JPanel orderHistoryPanel, 
                             String state, My_SQL_Connection connection) {
        this.newOrderPanel = newOrderPanel;
        this.orderHistoryPanel = orderHistoryPanel;
        this.state = state;
        this.connection = connection;
    }
    
    @Override
    public void mouseClicked(MouseEvent event) {
        if (state == null) {
            JPanel panelToBeDeleted = (JPanel) event.getComponent();
            cartBGPanel.remove(panelToBeDeleted);
            new Validate().checkIfPanelExisted(panelToBeDeleted, itemQuantity);
            cartBGPanel.repaint();
            cartBGPanel.revalidate();
        }
        else {
            String dishName = "";
            int counter = 0;
            JLabel currentLabel;
            var components = newOrderPanel.getComponents();
            
            for (var currentComponent : components) {
                ++counter;
                if (currentComponent instanceof JLabel) {
                    currentLabel = (JLabel)currentComponent;
                    if (counter == 2) {
                        dishName = currentLabel.getText();
                        break;
                    }
                }
            }
            Database_Transaction.updateQtyStock(connection, dishName, "canceled order");
            Database_Transaction.cancelOrder(connection, dishName);
            JPanel panelToBeDeleted = (JPanel) event.getComponent();
            orderHistoryPanel.remove(panelToBeDeleted);           
            orderHistoryPanel.repaint();
            orderHistoryPanel.revalidate();
            panelToBeDeleted = null;
        }
    }
    
    @Override
    public void mouseExited(MouseEvent event) {
        Colour.setToCartItemColor((JPanel) event.getComponent(), "exited");
    }
    
    @Override
    public void mouseEntered(MouseEvent event) {
        Colour.setToCartItemColor((JPanel) event.getComponent(), "entered");
    }

    @Override
    public void mousePressed(MouseEvent event) {}

    @Override
    public void mouseReleased(MouseEvent event) {
    }
}