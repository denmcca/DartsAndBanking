/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package finalproject;

import java.awt.*;
import javax.swing.*;
import java.io.*;
import java.util.*;

public class Main {
      
    public static CheckOptionsPanel frame;
    public static JTextArea ta;
    public static String textAreaMsg;
    public static JScrollPane scrollPane;
    public static final int WIDTH = 355, HEIGHT = 250;

            
    static Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        
    public static void main(String[] args) throws IOException
    {
        Insets textAreaPad = new Insets(3,3,3,0);
        String mainTitle = "Darts.Game";
        CheckingAccount.dataStore = new Vector<CheckingAccount>();
        textAreaMsg = "Welcome to the Game of Darts";
        frame = new CheckOptionsPanel(mainTitle);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.setSize(CheckOptionsPanel.HEIGHT, CheckOptionsPanel.WIDTH);
        ta = new JTextArea(textAreaMsg);
        ta.setEditable(false);
        ta.setBackground(Color.WHITE);
        ta.setMargin(textAreaPad);
        ta.setFont(new Font("Courier New", Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(ta);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        frame.getContentPane().add(scrollPane);
        frame.pack();
        frame.setAlwaysOnTop(true);
        frame.setSize(WIDTH, HEIGHT);
        frame.setLocation(dim.width/2 - frame.getWidth()/2, dim.height/2 - frame.getHeight()/2);
        frame.setVisible(true);
    }
    
}