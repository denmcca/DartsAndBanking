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
    static Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
    public static final int WIDTH = dim.width / 2, HEIGHT = dim.height / 2;



    public static void main(String[] args) throws IOException
    {
        Insets textAreaPad = new Insets(3,3,3,0);
        String mainTitle = "Darts.Game";
        CheckingAccount.dataStore = new Vector<CheckingAccount>();
        textAreaMsg = "Welcome to the Game of Darts";
        frame = new CheckOptionsPanel(mainTitle, new Dimension(WIDTH, HEIGHT));
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        ta = new JTextArea(textAreaMsg);
        ta.setEditable(false);
        ta.setBackground(Color.WHITE);
        ta.setMargin(textAreaPad);
        ta.setFont(new Font("Courier New", Font.PLAIN, 12*dim.width/WIDTH));
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