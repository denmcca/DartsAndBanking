/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package finalproject;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.util.*;
import java.awt.Toolkit.*;


/**
 *
 * @author Dennis
 */
public class FinalProject {
    static JFrame dartFrame;
    static int WIDTH = 640, HEIGHT = 480;
//    static Cursor blank = Toolkit.getDefaultToolkit().createCustomCursor(null, null, "./img/circle.png");
    
    /**
     * @param args the command line arguments
     */
    public static void dartsMain() {
        dartFrame = new JFrame("Darts");
        dartFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);    
        dartFrame.setAlwaysOnTop(true);
        dartFrame.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        dartFrame.setResizable(false);
//        dartFrame.setCursor(blank);
        dartFrame.setLocation((Main.dim.width - WIDTH) / 2, (Main.dim.height - HEIGHT) / 2);
//        System.out.print("width = " + (dartFrame.getWidth()) / 2 + " height = " + (Main.dim.height - dartFrame.getHeight()) / 2);
        dartFrame.getContentPane().add(new DartsPanel());
        dartFrame.pack();
        dartFrame.setVisible(true);
    }
    
}
