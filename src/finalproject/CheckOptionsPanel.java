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
import java.util.Arrays;
import java.util.Vector;

public class CheckOptionsPanel extends JFrameL //CheckOptionFrame(now a frame not a panel
{
    private JMenu fileMenu, acctMenu, transMenu, misc;
    private JMenuItem readFile, writeFile, addAcct, listAcctTrans, listCheck, 
                      listDep, findAcct, listAcct, addTrans, playDarts;
    private JMenuBar bar;
    private final String readFileStr = "Load File", writeFileStr = "Save File",
            addTransStr = "Add Transactions", addAcctStr = "Add New Account", 
            listAcctTransStr = "List Account Transactions",
            listCheckStr = "List All Checks", listDepStr = "List All Deposits",
            findAcctStr = "Find Account", fileStr = "File", acctStr = "Account",
            listingAcctStr = "List All Accounts", transStr = "Transactions", 
            miscStr = "Misc.", dartsStr = "Play Darts!";
    private final String emptyList = "No accounts on file!\nUse Add Account "
                            + "option to start a new database\nor use Load File "
                            + "option to open a database.";

    private class MenuContent {
        public JMenu menu;
        public Vector<JMenuItem> items;
        MenuContent(String name){
            this.menu = new JMenu(name);
            this.items = new Vector<>();
        }
    }

   //-----------------------------------------------------------------
   //  Sets up a panel with a label and a set of radio buttons
   //  that present options to the user.
   //-----------------------------------------------------------------
    
    public CheckOptionsPanel(String title, Dimension dim)
    {
        super(title);
        MenuListener ml = new MenuListener();

        Vector<MenuContent> menuContent = new Vector<>();
//        Vector<JMenuItem> fileMenuItems = new Vector<>(Arrays.asList(readFile, writeFile));
//        Vector<JMenuItem> acctMenuItems = new Vector<>(Arrays.asList(addAcct, findAcct, listAcct));
//        Vector<JMenuItem> transMenuItems = new Vector<>(Arrays.asList(listAcctTrans, addTrans, listDep, listCheck));
//        Vector<JMenuItem> miscItems = new Vector<>(Arrays.asList(playDarts));
//        menuContent.addElement(new MenuContent(fileMenu, fileMenuItems, fileStr));
//        menuContent.addElement(new MenuContent(acctMenu, acctMenuItems, acctStr));
//        menuContent.addElement(new MenuContent(transMenu, transMenuItems, transStr));
//        menuContent.addElement(new MenuContent(misc, miscItems, miscStr));
        Vector<String> fileMenuStrs = new Vector<>(Arrays.asList(fileStr, readFileStr, writeFileStr));
        Vector<String> acctMenuStrs = new Vector<>(Arrays.asList(acctStr, addAcctStr, findAcctStr, listingAcctStr));
        Vector<String> tranMenuStrs = new Vector<>(Arrays.asList(transStr, listAcctTransStr, addTransStr, listDepStr, listCheckStr));
        Vector<String> miscMenuStrs = new Vector<>(Arrays.asList(miscStr, dartsStr));
        Vector<JMenu> menuItems = new Vector<>();
        Vector<Vector<String>> menuStrs = new Vector<>(Arrays.asList(fileMenuStrs, acctMenuStrs, tranMenuStrs, miscMenuStrs));

        // Itterate through list to create list of JMenu objects
        final int fontSize = 12*Toolkit.getDefaultToolkit().getScreenSize().width/dim.width;
        menuStrs.forEach((vec) -> {
            JMenu menu = new JMenu(vec.elementAt(0));
            int length = vec.size();
            for (int i = 1; i < length; i++) {
                JMenuItem item = new JMenuItem(vec.elementAt(i));
                item.addActionListener(ml);
                item.setFont(new Font("Courier New", Font.PLAIN, fontSize - (int)(fontSize * 0.1)));
                menu.add(item);
                menu.setFont(new Font("Courier New", Font.PLAIN, fontSize));
            }
            menuItems.add(menu);
        });

        // Create and populate menu bar with JMenu objects
        bar = new JMenuBar();
        menuItems.forEach(item -> {
            bar.add(item);
        });

        setJMenuBar(bar);
        setBackground(Color.green);
        setPreferredSize(new Dimension(dim.width, dim.height));
    }
    
    private class MenuListener implements ActionListener
    {
        public void actionPerformed (ActionEvent event)
        {
            // Keeps Main frame suppressed when dart game is launched
            boolean keepInvisible = false;
            final String source = event.getActionCommand();

            Main.frame.setVisible(false);
            switch(source) {
                case readFileStr:
                    CheckingAccount.readFile();
                    break;
                case writeFileStr:
                    if (CheckingAccount.acct1 != null) {
                        CheckingAccount.saveFile();
                    } else Main.ta.setText(emptyList);
                    break;
                case addAcctStr:
                    CheckingAccount.getInitBal();
                    break;
                case listAcctTransStr:
                    if (CheckingAccount.dataStore.isEmpty())
                        Main.ta.setText(emptyList);
                    else CheckingAccount.listAllTrans(CheckingAccount.acct1);
                    Main.ta.setCaretPosition(0);
                    break;
                case listCheckStr:
                    if (CheckingAccount.dataStore.isEmpty())
                        Main.ta.setText(emptyList);
                    else CheckingAccount.listAllChecks(CheckingAccount.acct1);
                    Main.ta.setCaretPosition(0);
                    break;
                case listDepStr:
                    if (CheckingAccount.dataStore.isEmpty())
                        Main.ta.setText(emptyList);
                    else CheckingAccount.listAllDeposits(CheckingAccount.acct1);
                    Main.ta.setCaretPosition(0);
                    break;
                case findAcctStr:
                    if (CheckingAccount.dataStore.isEmpty())
                        Main.ta.setText(emptyList);
                    else CheckingAccount.findAccount();
                    break;
                case listingAcctStr:
                    if (CheckingAccount.dataStore.isEmpty())
                        Main.ta.setText("There are no accounts on file!");
                    else CheckingAccount.listAllAcct();
                    break;
                case addTransStr:
                    if (CheckingAccount.dataStore.isEmpty())
                        Main.ta.setText(emptyList);
                    else CheckingAccount.enterTransaction();
                    break;
                case dartsStr:
                    if (CheckingAccount.dataStore.isEmpty())
                        Main.ta.setText("There are no accounts on file!");
                    else {
                        CheckingAccount.startGame();
                        keepInvisible = true;
                    }
                    break;
                default: System.out.println("Error in during action listener");
            }
            if (!keepInvisible) Main.frame.setVisible(true);
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
