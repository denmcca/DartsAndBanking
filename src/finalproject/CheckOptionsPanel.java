/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package finalproject;

/**
 *
 * @author dennis_mccann
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class CheckOptionsPanel extends JFrameL //CheckOptionFrame(now a frame not a panel
{
    private JMenu fileMenu, acctMenu, transMenu, misc;
    private JMenuItem readFile, writeFile, addAcct, listAcctTrans, listCheck, 
                      listDep, findAcct, listAcct, addTrans, playDarts;
    private JMenuBar bar;
    private String readFileStr = "Load File", writeFileStr = "Save File",
            addTransStr = "Add Transactions", addAcctStr = "Add New Account", 
            listAcctTransStr = "List Account Transactions",
            listCheckStr = "List All Checks", listDepStr = "List All Deposits",
            findAcctStr = "Find Account", fileStr = "File", acctStr = "Account",
            listingAcctStr = "List All Accounts", transStr = "Transactions", 
            miscStr = "Misc.", dartsStr = "Play Darts!";
    private String emptyList = "No accounts on file!\nUse Add Account "
                            + "option to start a new database\nor use Load File "
                            + "option to open a database.";
    
//    private final JLabel prompt;
//    private final JRadioButton one, two, three, four, five, six, seven;

   //-----------------------------------------------------------------
   //  Sets up a panel with a label and a set of radio buttons
   //  that present options to the user.
   //-----------------------------------------------------------------
    
    public CheckOptionsPanel(String title)
    {
        super(title);
        MenuListener ml = new MenuListener();

        fileMenu = new JMenu(fileStr);
        
        readFile = new JMenuItem(readFileStr);
        readFile.addActionListener(ml);
        fileMenu.add(readFile);
        
        writeFile = new JMenuItem(writeFileStr);
        writeFile.addActionListener(ml);
        fileMenu.add(writeFile);
        
        acctMenu = new JMenu(acctStr);
        
        addAcct = new JMenuItem(addAcctStr);
        addAcct.addActionListener(ml);
        acctMenu.add(addAcct);
        
        listAcctTrans = new JMenuItem(listAcctTransStr);
        listAcctTrans.addActionListener(ml);
        acctMenu.add(listAcctTrans);
        
        listCheck = new JMenuItem(listCheckStr);
        listCheck.addActionListener(ml);
        acctMenu.add(listCheck);
        
        listDep = new JMenuItem(listDepStr);
        listDep.addActionListener(ml);
        acctMenu.add(listDep);
        
        findAcct = new JMenuItem(findAcctStr);
        findAcct.addActionListener(ml);
        acctMenu.add(findAcct);
        //new list accounts sorted
        listAcct = new JMenuItem(listingAcctStr);
        listAcct.addActionListener(ml);
        acctMenu.add(listAcct);
        
        transMenu = new JMenu(transStr);
        
        addTrans = new JMenuItem(addTransStr);
        addTrans.addActionListener(ml);
        transMenu.add(addTrans);
        
        misc = new JMenu(miscStr);
        
        playDarts = new JMenuItem(dartsStr);
        playDarts.addActionListener(ml);
        misc.add(playDarts);
        
        bar = new JMenuBar();
        bar.add(fileMenu);
        bar.add(acctMenu);
        bar.add(transMenu);
        bar.add(misc);
        
        setJMenuBar(bar); 
        
        setBackground(Color.green);
        setPreferredSize(new Dimension(325, 230));   
    }
    
    private class MenuListener implements ActionListener
    {
        public void actionPerformed (ActionEvent event)
        {
            String source = event.getActionCommand();            
            if (source.equals(readFileStr))
            {
                Main.frame.setVisible(false);
                CheckingAccount.readFile();
                Main.frame.setVisible(true);

            }
            
            else if (source.equals(writeFileStr))
            {
                if(CheckingAccount.acct1 == null)
                {
                    Main.ta.setText(emptyList);
                }
                else
                {
                    Main.frame.setVisible(false);
                    CheckingAccount.saveFile();
                    Main.frame.setVisible(true);
                }
            }
            
            else if (source.equals(addAcctStr))
            {
                Main.frame.setVisible(false);
                CheckingAccount.getInitBal();
                Main.frame.setVisible(true);
            }
            
            else if (source.equals(listAcctTransStr))
            {
                Main.frame.setVisible(false);

                if(CheckingAccount.dataStore.isEmpty())
                {
                    Main.ta.setText(emptyList);
                }
                else
                {
                CheckingAccount.listAllTrans(CheckingAccount.acct1);
                }
                Main.ta.setCaretPosition(0);
                Main.frame.setVisible(true);
            }
            
            else if (source.equals(listCheckStr))
            {
                Main.frame.setVisible(false);

                if(CheckingAccount.dataStore.isEmpty())
                {
                    Main.ta.setText(emptyList);
                }
                else
                {
                    CheckingAccount.listAllChecks(CheckingAccount.acct1);
                }
                Main.ta.setCaretPosition(0);
                Main.frame.setVisible(true);
            }
            
            else if (source.equals(listDepStr))
            {
                Main.frame.setVisible(false);

                if(CheckingAccount.dataStore.isEmpty())
                {
                    Main.ta.setText(emptyList);
                }                
                else
                {
                    CheckingAccount.listAllDeposits(CheckingAccount.acct1);
                }
                
                Main.ta.setCaretPosition(0);              
                Main.frame.setVisible(true);
            }
            
            else if (source.equals(findAcctStr))
            {
                Main.frame.setVisible(false);
                
                if(CheckingAccount.dataStore.isEmpty())
                {
                    Main.ta.setText(emptyList);
                }
                else
                {
                    CheckingAccount.findAccount();
                }
                
                Main.frame.setVisible(true);
            }
            
            else if (source.equals(listingAcctStr))
            {
                Main.frame.setVisible(false);
                
                if(CheckingAccount.dataStore.isEmpty())
                {
                    Main.ta.setText("There are no accounts on file!");
                }
                else
                {
                    CheckingAccount.listAllAcct();
                }
                
                Main.frame.setVisible(true);
            }
            
            else if (source.equals(addTransStr))
            {
                Main.frame.setVisible(false);
                
                if(CheckingAccount.dataStore.isEmpty())
                {
                    Main.ta.setText(emptyList);
                }
                else
                {
                    CheckingAccount.enterTransaction();
                }
                
                Main.frame.setVisible(true);
            }
            
            else if (source.equals(dartsStr))
            {
                if(CheckingAccount.dataStore.isEmpty())
                {
                    Main.ta.setText("There are no accounts on file!");
                }
                else
                {
                    Main.frame.setVisible((false));
                    CheckingAccount.startGame();
                }
            }
            
            else
                System.out.println("Error in during action listener");
            
        }
    }
    
   //*****************************************************************
   //  Represents the listener for the radio buttons
   //*****************************************************************
    private class CAPanelListener implements ActionListener
    {
      //--------------------------------------------------------------
      //  Calls the method to process the option for which radio
      //  button was pressed.
      //--------------------------------------------------------------
     
        public void actionPerformed(ActionEvent event)
        {
            Object source = event.getSource();

            Main.frame.setVisible(true);
        }
    }
}
