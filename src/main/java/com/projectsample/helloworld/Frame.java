/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.projectsample.helloworld;

import javax.swing.JFrame;

/**
 *
 * @author Ela
 */
public class Frame {
    public static void minimizeFrame(JFrame frame) {
        frame.setExtendedState(frame.ICONIFIED);
    }
    
    public static void exitFrame(JFrame frame) {
        frame.dispose();
    }
}
