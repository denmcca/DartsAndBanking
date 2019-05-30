/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package finalproject;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
//import java.util.ArrayList;
import java.awt.image.BufferedImage;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.*;
import javax.sound.sampled.*;
import javax.sound.sampled.AudioSystem.*;
import javax.sound.sampled.AudioInputStream.*;
import java.net.*;
import java.applet.*;
import java.util.Arrays;
//import Toolkit.createCustomCursor;

/**
 *
 * @author Dennis
 */
public class DartsPanel extends JPanel
{   
    //in progress
    private static int yCirPos = 0, xCirPos = 50, cirRadius = 50, mod = -1;
    
    //in progress end
    private final static int width = 640, height = 480;
    private final static int midX = width / 2, midY = (height / 2);
    
    //measured units
    private static int meter = 320; //for every meter there are 320 pixels
    //measured units end
    
    //toggles
    private static boolean driftCxl = true; //toggle drift (true off, false on)
    //toggles end

    //audio implementation
    private String tinny_impact = "./audio/tinny_impact.wav", 
            wooden_impact = "./audio/wooden_impact.wav",
            terrible = "./audio/PIR_fail.wav",
            woodPlastic = "./audio/missedDart.wav", 
            gameOverSong = "./audio/Game_Over_-_Sound_Effect.wav";
    
    private Clip clip;

    private File input;
    private AudioInputStream sound; 
    private AudioClip audioClip, dartWall, dartBoard;
    private DataLine.Info info;
    //audio end
    
    //graphics
    private String handArr[] = {"./img/hand1.png", "./img/hand2.png", 
        "./img/hand3.png", "./img/hand4.png", "./img/hand5.png",
        "./img/hand6.png", "./img/hand5.png", "./img/hand4.png",
        "./img/hand3.png", "./img/hand2.png", "./img/hand1.png",
        "./img/hand7.png"};
//    private ImageIcon handArr[];
    private ImageIcon hand, board, bg, dart, meterBar, aimBar, quitIcon, 
            quitIcon2, restartIcon, restartIcon2, tutorial1PopUp, 
            tutorial2PopUp, tutorial3PopUp, helpIcon, helpIcon2;
    public boolean tutorial = false, tutorial1 = false, tutorial2 = false, 
            tutorial3 = false, handDraw = true;
    public int circleDiameter = 20;
    private static final int circleAimMax = 50;
    private int xHand, yHand, whiteOut = 0;
    private int scoreColor1 = 255, scoreColor2 = 255, scoreColor3 = 255, 
            scoreColor4 = 255;
    public int helpIconX = (width * 1 / 9), helpIconY  = (height * 7 / 9);
    public int pingSpeedMod = 0;
    //graphics ends
    
    //score handling
    private static int turnScore = 0, turnScore1 = 0, turnScore2 = 0, 
            turnScore3 = 0, playerScore = 0, scoreMultiplier = 0, 
            scoreMultiplier1 = 0, scoreMultiplier2 = 0, scoreMultiplier3, sideX, 
            sideY;
        
    private static int scoreBoardX = (width * 7 / 10), 
            scoreBoardY = (height / 15), pointsSum = 0, 
            highScoreBoardX = (width * 1 / 10), highScoreBoardY = scoreBoardY;
    private static boolean scorePop1 = false, scorePop2 = false, 
            scorePop3 = false;
    private static int scorePop1X, scorePop1Y, scorePop2X, scorePop2Y, 
            scorePop3X, scorePop3Y;
    private static int highScore = 0;

    //score handling end
    private int handArrIndex = 0;
    private static double angle;

    private int DELAY = 120, DELAYFLOW = 18, IMAGE_SCALE = 2,
            IMAGE_SCALE_BOARD = 1;
    private int aniMode = 1;
    private boolean driftXrev = false, driftYrev = false, imageResize = true;
    private boolean attempt1 = false, attempt1F = false, attempt2 = false, 
            attempt2F = false, attempt3 = false, attempt3F = false, 
            lockBar = false, lockAimBar = false;
    private int X1, Y1, X2, Y2, X3, Y3, xAim = 0, yAim = 1, yAimFactor = 2;
    private int power = 5, deviation = 1;
    private final int distance = 600 , gravity = 5;
    private int powerBarFlow = 2, aimBarFlow = -150, powerBarFactor = 1,
            aimBarFactor = 5, dirPBF = 1, dirABF = 1, dirAimCircle = 1;
    
    private Timer timer, timerFlow;
    private int x = (width / 2) , y = (height / 2), driftX = 15, driftY = 0, 
            dartX = 0, dartY = 0, driftMax = 15;
    private java.net.URL handUrl, dartUrl;
    
    //game control
    private static boolean endGame = false, restartGame = false, 
            endGameFirstPass = true;
    //game control end
    public DartsPanel()
    {           
        ImageIcon hand1 = new ImageIcon("./img/hand1.png");
        ImageIcon hand2 = new ImageIcon("./img/hand2.png");
        ImageIcon hand3 = new ImageIcon("./img/hand3.png");
        ImageIcon hand4 = new ImageIcon("./img/hand4.png");
        ImageIcon hand5 = new ImageIcon("./img/hand5.png");
        ImageIcon hand6 = new ImageIcon("./img/hand6.png");
        ImageIcon hand7 = new ImageIcon("./img/hand7.png");
        
        hand1.setImage(hand1.getImage().getScaledInstance(44, 66, Image.SCALE_SMOOTH));
        hand2.setImage(hand2.getImage().getScaledInstance(44, 66, Image.SCALE_SMOOTH));
        hand3.setImage(hand3.getImage().getScaledInstance(44, 66, Image.SCALE_SMOOTH));
        hand4.setImage(hand4.getImage().getScaledInstance(44, 66, Image.SCALE_SMOOTH));
        hand5.setImage(hand5.getImage().getScaledInstance(44, 66, Image.SCALE_SMOOTH));
        hand6.setImage(hand6.getImage().getScaledInstance(44, 66, Image.SCALE_SMOOTH));
        hand7.setImage(hand7.getImage().getScaledInstance(44, 66, Image.SCALE_SMOOTH));

        ImageIcon handArr[] = { hand1, hand2, hand3, hand4, hand5, hand6, hand7 };


        
        timer = new Timer(DELAY, new PlayerListener());
        timerFlow = new Timer(DELAYFLOW, new TimerListener());
        
        PlayerListener listener = new PlayerListener();

        board = getImage("./img/BigDartboard.png");       
        board.setImage(board.getImage().getScaledInstance(144, 144, Image.SCALE_SMOOTH));


        meterBar = getImage("./img/meter_bar_wood.png");
        meterBar.setImage(meterBar.getImage().getScaledInstance(60, 275, Image.SCALE_SMOOTH));
        
        bg = getImage("./img/bg.jpg");
        bg.setImage(bg.getImage().getScaledInstance(640, 480, Image.SCALE_FAST));
        
        aimBar = getImage("img/aimBarWood.png");
        aimBar.setImage(aimBar.getImage().getScaledInstance(350, 40, Image.SCALE_SMOOTH));
        
        restartIcon = getImage("img/restart.png");
        restartIcon.setImage(restartIcon.getImage().getScaledInstance(75, 75, Image.SCALE_SMOOTH));
        
        restartIcon2 = getImage("img/restart2.png");
        restartIcon2.setImage(restartIcon2.getImage().getScaledInstance(75, 75, Image.SCALE_SMOOTH));
        
        quitIcon = getImage("img/quit.png");
        quitIcon.setImage(quitIcon.getImage().getScaledInstance(75, 75, Image.SCALE_SMOOTH));
                
        quitIcon2 = getImage("img/quit2.png");
        quitIcon2.setImage(quitIcon2.getImage().getScaledInstance(75, 75, Image.SCALE_SMOOTH));
        
        helpIcon = getImage("img/help.png");
        helpIcon.setImage(helpIcon.getImage().getScaledInstance(75, 75, Image.SCALE_SMOOTH));
        
        helpIcon2 = getImage("img/help2.png");
        helpIcon2.setImage(helpIcon2.getImage().getScaledInstance(75, 75, Image.SCALE_SMOOTH));
        
        dartWall = setSound(tinny_impact);
        dartBoard = setSound(wooden_impact);
        
        tutorial1PopUp = getImage("img/tutorial1.png");
        tutorial1PopUp.setImage(tutorial1PopUp.getImage().getScaledInstance(230, 150, Image.SCALE_SMOOTH));
        tutorial2PopUp = getImage("img/tutorial2.png");
        tutorial2PopUp.setImage(tutorial2PopUp.getImage().getScaledInstance(230, 150, Image.SCALE_SMOOTH));
        tutorial3PopUp = getImage("img/tutorial3.png");
        tutorial3PopUp.setImage(tutorial3PopUp.getImage().getScaledInstance(230, 150, Image.SCALE_SMOOTH));

        setPreferredSize (new Dimension(width, height));
        setBackground(Color.white);
        
        addMouseListener(listener);
        addMouseMotionListener(listener);

        gameReset();
        CheckingAccount.bettingCharge();
        
        xHand = width / 2;
        yHand = height / 2;
        
        highScore = CheckingAccount.acct1.getHighScore();
        
        timer.start();
        timerFlow.start();
        tutorial = CheckingAccount.acct1.tutorialState;
        
        
        ////////////////////////System.out.println("mainpanel"); 
    }
    
    public ImageIcon getImage(String file)
    {
        ImageIcon temp;
        
        java.net.URL bgUrl = getClass().getResource(file);
        if (bgUrl != null) 
        {
            temp = new ImageIcon(bgUrl);
        } 
        else 
        {
            throw new IllegalArgumentException("This icon file does not exist");
        }
        
        return temp;
    }
    
    public void paintComponent(Graphics g)
    {
//        System.out.println("in paint");
        super.paintComponent(g);
                
        if(hand != null && board != null)
        {
            bg.paintIcon(this, g, 0, 0); //paints wall background
            board.paintIcon(this, g, (width / 2) - (board.getIconWidth() / 2) + 1, (height / 2) - (board.getIconHeight() / 2) + 1);
            drift();

            if(attempt1)
            {
//                System.out.println("inside attempt1");
                if(!(attempt1F))
                {
                    X1 = xHand - (hand.getIconWidth() / 2) + dartX;
                    Y1 = yHand - (hand.getIconHeight() / 2) + dartY;
                    sideX = X1;
                    sideY = Y1;
                    attempt1F = true;
//                                        System.out.println("X1 in attempt = " + X1 + " Y1 in attempt = " + Y1);
                    angle = angleFind(X1 - (width / 2), -Y1 + (height / 2));    
                    scoreFind(X1 - (width / 2), -Y1 + (height / 2));
                    scorePop1 = true;
                    scorePop1X = X1;
                    scorePop1Y = Y1;
                    turnScore1 = turnScore;
                    scoreMultiplier1 = scoreMultiplier;
                }
                
                if(scorePop1)
                {
                    g.setColor(Color.blue);
                    g.setFont(new Font("TimesRoman", Font.BOLD, 20));
                    
                    if(scoreMultiplier1 > 1)
                    {
                        g.drawString(Integer.toString(turnScore1) + " x " + Integer.toString(scoreMultiplier1), scorePop1X, scorePop1Y);
                    }
                    else
                    {
                        g.drawString(Integer.toString(turnScore1), scorePop1X, scorePop1Y);
                    }
                    
                    if(scorePop1Y > 0)
                    {
                        scorePop1Y--;
                    }
                    else
                    {
                        scorePop1 = false;
                    }
                }

                g.setColor(Color.blue.brighter().brighter());
                g.drawOval(X1 - (circleDiameter / 2), Y1 - (circleDiameter / 2), circleDiameter, circleDiameter);
                dart.paintIcon(this, g, X1 - (dart.getIconWidth() / 2), Y1 - (dart.getIconHeight() / 2));
            }
            
            if(attempt2)
            {
                if(!(attempt2F))
                {
                    X2 = xHand - (hand.getIconWidth() / 2) + dartX;
                    Y2 = yHand - (hand.getIconHeight() / 2) + dartY;
                    sideX = X2;
                    sideY = Y2;
                    attempt2F = true;
//                                        System.out.println("X2 in attempt = " + X2 + " Y2 in attempt = " + Y2);
                    angle = angleFind(X2 - (width / 2), -Y2 + (height / 2));
                    scoreFind(X2 - (width / 2), -Y2 + (height / 2));
                    scorePop2 = true;
                    scorePop2X = X2;
                    scorePop2Y = Y2;
                    turnScore2 = turnScore;
                    scoreMultiplier2 = scoreMultiplier;
                }
                
                if(scorePop2)
                {
                    g.setColor(Color.yellow);
                    g.setFont(new Font("TimesRoman", Font.BOLD, 20));
                    
                    if(scoreMultiplier2 > 1)
                    {
                        g.drawString(Integer.toString(turnScore2) + " x " + Integer.toString(scoreMultiplier2), scorePop2X, scorePop2Y);
                    }
                    else
                    {
                        g.drawString(Integer.toString(turnScore2), scorePop2X, scorePop2Y);
                    }
                    
                    if(scorePop2Y > 0)
                    {
                        scorePop2Y--;
                    }
                    else
                    {
                        scorePop2 = false;
                    }
                }
                g.setColor(Color.yellow.brighter().brighter());
                g.drawOval(X2 - (circleDiameter / 2), Y2 - (circleDiameter / 2), circleDiameter, circleDiameter);                
                dart.paintIcon(this, g, X2 - (dart.getIconWidth() / 2), Y2 - (dart.getIconHeight() / 2));
            }
            if(attempt3)
            {
                if(!(attempt3F))
                {
                    X3 = xHand - (hand.getIconWidth() / 2) - (dart.getIconWidth() / 2) + dartX;
                    Y3 = yHand - (hand.getIconHeight() / 2) - (dart.getIconHeight() / 2) + dartY;
                    sideX = X3;
                    sideY = Y3;
                    attempt3F = true;
//                                        System.out.println("X3 in attempt = " + X3 + " Y3 in attempt = " + Y3);
                    angle = angleFind(X3 - (width / 2), -Y3 + (height / 2));
                    scoreFind(X3 - (width / 2), -Y3 + (height / 2));
                    
                    scorePop3 = true;
                    scorePop3X = X3;
                    scorePop3Y = Y3;
                    turnScore3 = turnScore;
                    scoreMultiplier3 = scoreMultiplier;

                    endGame = true;
                }
                if(scorePop3)
                {
                    g.setColor(Color.green);
                    g.setFont(new Font("TimesRoman", Font.BOLD, 20));
                    if(scoreMultiplier3 > 1)
                    {
                        g.drawString(Integer.toString(turnScore3) + " x " + Integer.toString(scoreMultiplier3), scorePop3X, scorePop3Y);
                    }
                    else
                    {
                        g.drawString(Integer.toString(turnScore3), scorePop3X, scorePop3Y);
                    }
                    if(scorePop3Y > 0)
                    {
                        scorePop3Y--;
                    }
                    else
                    {
                        scorePop3 = false;
                    }
                }
      
                highScoreCheck();
                
                g.setColor(Color.green.brighter().brighter());
                g.drawOval(X3 - (circleDiameter / 2), Y3 - (circleDiameter / 2), circleDiameter, circleDiameter);                
                dart.paintIcon(this, g, X3 - (dart.getIconWidth() / 2), Y3 - (dart.getIconHeight() / 2));
            }
//            System.out.println("player score = " + playerScore);


            if(aniMode == 2)
            {
                meterBar.paintIcon(this, g, (width * 7 / 8) - (10) , (height / 2) - (meterBar.getIconHeight() / 2));
                g.setColor(new Color(255 - powerBarFlow, powerBarFlow, 0, 200).brighter().brighter());
                g.fillRect((width * 7 / 8) + 5, ((height * 2 / 3) + (39)) - powerBarFlow, 32, powerBarFlow); //powerBar
//                System.out.println("animode 2 paint " + ", width = " + width + ", height = " + height + ", powerBarFlow = " + powerBarFlow);
            }
            if(aniMode == 3)  //aimBar
            {   
                aimBar.paintIcon(this, g, (width / 2) - (aimBar.getIconWidth() / 2), height / 8 - (aimBar.getIconHeight() / 2));
                g.setColor(new Color((int)(0 + Math.abs((double)aimBarFlow) * 1.5), (int)(255 - Math.abs((double)aimBarFlow) * 1.5), 0, 200).brighter().brighter());
                g.fillRect(((width / 2) + 3) - ((Math.abs(aimBarFlow) - aimBarFlow)/2), (height / 8) - 8, Math.abs(aimBarFlow), 18);

//                                System.out.println("animode 3 paint");

            }

            g.setColor(Color.white);
            g.drawOval(xHand + xAim, yHand + yAim - 50, 20, 20);
            
            if (aniMode == 1)
            {
                getHandCoor();
            }

            if (!tutorial)
            {
//if((Math.pow(x - (width / 3), 2) + Math.pow(y - (height / 2), 2)) < Math.pow(restartIcon.getIconWidth() / 2, 2))
//                {
//                    restartIcon2.paintIcon(this, g, (width / 3) - (restartIcon2.getIconWidth() / 2), (height / 2) - (restartIcon.getIconHeight() / 2));                   
//                }
//                else
//                {
//                    restartIcon.paintIcon(this, g, (width / 3) - (restartIcon.getIconWidth() / 2), (height / 2) - (restartIcon.getIconHeight() / 2));
//                }

                if((Math.pow(x - helpIconX, 2) + Math.pow(y - helpIconY, 2)) < (Math.pow(helpIcon.getIconHeight() / 2, 2))) //turns on tutorial/tips
                {
                    helpIcon2.paintIcon(this, g, helpIconX - helpIcon.getIconWidth() / 2, helpIconY - helpIcon.getIconHeight() / 2);
                }
                else
                {
                    helpIcon.paintIcon(this, g, helpIconX - helpIcon.getIconWidth() / 2, helpIconY - helpIcon.getIconHeight() / 2);
                }
            }
                
            hand.paintIcon(this, g, xHand, yHand); //paints animated hand
            
            if (aniMode == 0)
            {
//                if (restartGlow)
//                {
//                    g.setColor(Color.yellow.brighter().brighter().brighter());
//                    g.fillOval((width / 3) - (restartIcon.getIconWidth() / 2), (height / 2) - (restartIcon.getIconHeight() / 2), restartIcon.getIconWidth() + 2, restartIcon.getIconHeight() + 2);
//                }
                if (whiteOut < 254)
                {           
                    whiteOut += 2;
                    scoreColor1 -= 2;
                    scoreColor2 -= 2;
                    scoreColor3 -= 2;

                }
                else
                {
                    whiteOut = 255;
                    scoreColor1 = 0;
                    scoreColor2 = 0;
                    scoreColor3 = 0;
                }

                g.setColor(new Color(255,255,255,whiteOut));
                g.fillRect(0, 0, width, height);

                g.setColor(new Color(scoreColor1, scoreColor2, scoreColor3, scoreColor4));
                g.setFont(new Font(Font.SERIF, Font.BOLD+Font.ITALIC, 20));
                if(CheckingAccount.activeBet)
                {   
                    g.drawString(winMessage(), (width / 2) - 100, (height / 2) - 100);
                }
                g.drawString("Play Again?", (width / 2) - 50, height / 2);
                
                g.drawString("Top Score: " + CheckingAccount.highestScore + " (" + CheckingAccount.highestScorer + ")", (width / 12), (height * 9 / 10));
                
                if((Math.pow(x - (width / 3), 2) + Math.pow(y - (height / 2), 2)) < Math.pow(restartIcon.getIconWidth() / 2, 2))
                {
                    restartIcon2.paintIcon(this, g, (width / 3) - (restartIcon2.getIconWidth() / 2), (height / 2) - (restartIcon.getIconHeight() / 2));                   
                }
                else
                {
                    restartIcon.paintIcon(this, g, (width / 3) - (restartIcon.getIconWidth() / 2), (height / 2) - (restartIcon.getIconHeight() / 2));
                }
                
                if(Math.pow(x - (width * 2 / 3), 2) + Math.pow(y - (height / 2) , 2) < Math.pow(quitIcon.getIconWidth() / 2, 2))
                {
                    quitIcon2.paintIcon(this, g, (width * 2 / 3) - (quitIcon2.getIconWidth() / 2), (height / 2) - (quitIcon2.getIconHeight() / 2));
                }
                else
                {
                    quitIcon.paintIcon(this, g, (width * 2 / 3) - (quitIcon.getIconWidth() / 2), (height / 2) - (quitIcon.getIconHeight() / 2));

                }
                
            }
            
            g.setColor(new Color(scoreColor1, scoreColor2, scoreColor3, scoreColor4));
            g.setFont(new Font("Serif", Font.BOLD+Font.ITALIC, 30));
            g.drawString("Score: " + Integer.toString(playerScore), scoreBoardX, scoreBoardY);
            g.drawString("High Score: " + Integer.toString(highScore), highScoreBoardX, highScoreBoardY);
            
            if(tutorial1)
            {
                tutorial1PopUp.paintIcon(null, g, width / 100 , height * 3 / 5);               
            }
            if(tutorial2)
            {
                tutorial2PopUp.paintIcon(null, g, width / 100, height * 3 / 5);
            }
            if(tutorial3)
            {
                tutorial3PopUp.paintIcon(null, g, width / 100, height * 3 / 5);
            }
            

        }

    }
    
    private void getHandCoor()
    {
        xHand = (x - (getImage(handArr[0]).getIconWidth() / 2) + driftX);

        yHand = (y - (getImage(handArr[0]).getIconHeight() / 2) + driftY);        
    }
    
    private class TimerListener implements ActionListener
    {
        public void actionPerformed(ActionEvent event)
        {
            circularPath();
//            System.out.println("inside timerlistener");
            circleDiameterPing();
            
            if(aniMode == 1)
            {
                aimCircleCoor();
                if(tutorial)
                {
                    tutorial1 = true;
                }
            }

            if (aniMode < 4 && aniMode > 1)
            {
//                System.out.println("inside listenerloop");
                if(aniMode == 2)
                {
                    powerBar();
                    if(tutorial)
                    {
                        tutorial1 = false;
                        tutorial2 = true;
                    }
                }
                if(aniMode == 3)
                {    
                    aimBar();
                    if(tutorial)
                    {
                        tutorial2 = false;
                        tutorial3 = true;
                    }
                }
                
            }
            
            repaint();       
        }
    }
    
    private class PlayerListener implements MouseListener, 
                                            MouseMotionListener, 
                                            ActionListener
    {
        public void mousePressed(MouseEvent event)
        {
//            System.out.println("press detected\n");
//            mouse click after release
        }
        
        public void mouseClicked(MouseEvent event)
        {
//                    System.out.println("inside mouseclick");
            if(!endGame)
            {

                if((Math.pow(x - (width * 1 / 32), 2) + Math.pow(y - (height * 9 / 12), 2)) < Math.pow(helpIcon.getIconWidth(), 2)) //turns on tutorial/tips
                {
                    tutorial = true;
//                    System.out.println(Math.pow(x - helpIconX, 2) + Math.pow(y - helpIconY, 2) + " " + Math.pow(helpIcon.getIconWidth() / 2, 2));
                }
                else
                {
                    if(aniMode == 1) //switch to throw animation
                    {
                        aniMode = 2;
                        handArrIndex = 0;
                        DELAY = 10;

                        handReady();

                        timer.setDelay(DELAY);                
                    }
                    else if (aniMode == 2)
                    {
                        aniMode = 3;
                        lockBar = true;
                    }
                    else if (aniMode == 3)
                    {
                        dartPosition();
                        aniMode = 4;
                    }
                    else if (aniMode == 4)
                    {
//                        System.out.println("actionPerformed aniMode 4");
                        aniMode = 1;
                        DELAY = 120;
                        handArrIndex = 0;
                        timer.setDelay(DELAY);
                        timer.start();
                        tutorialOff();
                    }                    
                }
                

            }
            else
            {
                timer.stop();
                aniMode = 0;
                                
                if(endGameFirstPass)
                {
                    endGameFirstPass = false;
                    x = 0;
                    y = 0;
                    playSound(gameOverSong);
                    if (CheckingAccount.activeBet)
                    {
                        CheckingAccount.depositWin();
                    }
                    CheckingAccount.checkTopScore();
                }    

//                System.out.println((Math.pow(x - (width / 3), 2) + Math.pow(y - (height / 2), 2)) + Math.pow(restartIcon.getIconWidth() / 2, 2));
                if((Math.pow(x - (width / 3), 2) + Math.pow(y - (height / 2), 2)) < Math.pow(restartIcon.getIconWidth() / 2, 2))
                {
                    FinalProject.dartFrame.dispose();
                    CheckingAccount.startGame();
                }
                if(Math.pow(x - (width * 2 / 3), 2) + Math.pow(y - (height / 2) , 2) < Math.pow(quitIcon.getIconWidth() / 2, 2))
                {
                    gameQuit();
                }
                

            }
        }
        
        public void actionPerformed(ActionEvent event)
        {

            if(aniMode == 1) //default animation
            {
               handIdle();
            }
            else if(aniMode == 4) //throw animation
            {            
                handThrow();                
            }
            else {}
            
            repaint();
        }
        
        public void mouseDragged(MouseEvent event){}
        public void mouseReleased(MouseEvent event)
        {
            //detected click release
//            System.out.println("mouse released detected");
        }
        public void mouseEntered(MouseEvent event){}
        public void mouseExited(MouseEvent event){}
        public void mouseMoved(MouseEvent event)
        {
            x = event.getX();
            y = event.getY();
            imageResize = true;
//            System.out.println(Math.pow(x - (width * 1 / 32), 2) + Math.pow(y - (height * 9 / 12), 2) + " " + (Math.pow(helpIcon.getIconHeight() / 2, 2)));
        }
    }
    
    private void handIdle()
    {
        IMAGE_SCALE = 2;
        lockBar = false;

        hand = getImage(handArr[handArrIndex]);

        handArrIndex = (handArrIndex + 1) % 11;

        hand.setImage(hand.getImage().getScaledInstance(hand.getIconWidth() 
                * IMAGE_SCALE, hand.getIconHeight() * IMAGE_SCALE, 
                Image.SCALE_FAST));
    }
    
    private void handReady()
    {
        IMAGE_SCALE = 2;

        hand = getImage(handArr[1]);

//        handArrIndex = (handArrIndex + 1) % 11;

        hand.setImage(hand.getImage().getScaledInstance(hand.getIconWidth() 
                * IMAGE_SCALE, hand.getIconHeight() * IMAGE_SCALE, 
                Image.SCALE_FAST));
    }
    
    private void handThrow()
    {
        handUrl = getClass().getResource(handArr[handArrIndex]);
        if (handUrl != null) 
        {
            hand = new ImageIcon(handUrl);
        } 
        else 
        {
            throw new IllegalArgumentException("This icon file does not exist");
        }
        if(handArrIndex < 5)
            handArrIndex++;

        else if(handArrIndex == 5)
        {
    //                                            System.out.println("inside dart list");
            handArrIndex = 11;
            darts();
            if(!(attempt1))
            {
                attempt1 = true;
            }
            else if(!(attempt2))
            {
                attempt2 = true;
            }
            else if(!(attempt3))
            {
                attempt3 = true;
            }
        }
        else if(handArrIndex == 11)
        {
            timer.stop();
        }
        else{}

        hand.setImage(hand.getImage().getScaledInstance(hand.getIconWidth() 
            * IMAGE_SCALE, hand.getIconHeight() * IMAGE_SCALE, 
            Image.SCALE_FAST));
    }
    
    public void darts()
    {
        dartUrl = getClass().getResource("./img/dart.png");
        
        dart = new ImageIcon(dartUrl);
        if(dart == null)
        {
            System.out.println("Problem loading dart image");
        }
//        System.out.println("inside darts");
    }
    
    public void drift()
    {
            if (aniMode == 1 && driftCxl == false)
            {
                if(driftYrev)
                {
                    driftY = --driftY;
                    if (driftY < -driftMax)
                        driftYrev = false;
                }
                else
                {
                    driftY = ++driftY;
                    if (driftY > driftMax)
                        driftYrev = true;
                }
                if(driftXrev)
                {
                    driftX = --driftX;
                    if(driftX < -driftMax)
                        driftXrev = false;
                }
                else
                {
                    driftX = ++driftX;
                    if(driftX > driftMax)
                        driftXrev = true;
                }
//                System.out.println("driftx = " + driftX + " driftY = " + driftY);
            }
    }
    private void dartPosition()
    {
        power = powerBarFlow;

        if (power == 0)
            power = 1;
        
        try
        {
            dartY = (yAim + gravity * ((distance / power) * (distance / power)));
        }
        catch (ArithmeticException e)
        {
            System.out.println(e);
            dartY = (yAim + gravity * ((distance / 1) * (distance / 1)));            
        }
        
        dartX = deviation * (distance / power) - 25;
//        System.out.println("dartY pos = " + dartY + " dartX pos = " + dartX + " power = " + power + " gravity "+ gravity + " distance " + distance);

    }

    private void powerBar()
    {
//        System.out.println("powerBar top");
                        
        if(aniMode == 2)
        {
            if(!(lockBar))
            {
                if (dirPBF == 1)
                {
                    powerBarFlow += powerBarFactor++;

                    if(powerBarFlow > 100)
                    {
                        dirPBF = -1;
                        powerBarFactor = 12;
                        powerBarFlow = 155;
                    }
                }
                else
                {
                    powerBarFlow += powerBarFactor--;

                    if(powerBarFlow < 1)
                    {
                        dirPBF = 1;
                        powerBarFactor = 0;
                        powerBarFlow = 0;
                    }
                }
    //            System.out.println("dir = " + dirPBF + " powerBarFlow = " + powerBarFlow);

            }
        }
//                System.out.println("powerBarFlow = " + powerBarFlow + " power " + power + " flowDELAY " + timerFlow.getDelay());
    }
    private void aimBar()
    {
        if (aniMode == 3)
        {
            if(!(lockAimBar))
            {
                if(dirABF == 1)
                {
    //                if (aimBarFlow < -155)
                    aimBarFlow += aimBarFactor++;

                    if(aimBarFlow > 150)
                    {
                        dirABF = -1;
                        aimBarFactor = 5;
                        aimBarFlow = 150;
                    }
                }
                else
                {
                    aimBarFlow += aimBarFactor--;

                    if(aimBarFlow < -150)
                    {
                        dirABF = 1;
                        aimBarFactor = -5;
                        aimBarFlow = -150;
                    }
                }
                deviation = aimBarFlow / 5;
//                System.out.println("aimBarFlow = " + aimBarFlow +  " aimBarFactor = " + aimBarFactor);
            }
        }
    }
    private Clip getAudioClip(String path)
    {
        try
        {
            input = new File(path);
            System.out.println("file exists? " + input.exists() + " file executable? " + input.canExecute());
            sound = AudioSystem.getAudioInputStream(input.getAbsoluteFile());
            info = new DataLine.Info(Clip.class, sound.getFormat());
            clip = (Clip) AudioSystem.getLine(info);
        }
        catch (IOException e)
        {
            System.out.println(e);
            System.exit(1);
        }
        catch (UnsupportedAudioFileException e)
        {
            System.out.println(e);
            System.exit(1);
        }
        catch(LineUnavailableException e)
        {
            System.out.println(e);
            System.exit(1);
        }
        return clip;
    }
    
    void playSound(String path)
    {
        try
        {
            int delay = 0;
            String url = "file:" + path;
            audioClip = Applet.newAudioClip(new URL(url));
            audioClip.play();
            Thread.sleep(delay);

        }
        catch (MalformedURLException murle) 
        {
            System.out.println(murle);
        }
        catch (InterruptedException e)
        {
            System.out.println(e);
        }
    }
    
    AudioClip setSound(String path)
    {
        try
        {
            int delay = 0;
            String url = "file:" + path;
            audioClip = Applet.newAudioClip(new URL(url));
//            clip.play();
//            Thread.sleep(delay);

        }
        catch (MalformedURLException murle) 
        {
            System.out.println(murle);
        }
//        catch (InterruptedException e)
//        {
//            System.out.println(e);
//        }
        return audioClip;
    }
    
    void aimCircleCoor()
    {
        if(dirAimCircle == -1)
        {
            yAim -= yAimFactor;
//                                                        System.out.println("1" + " yAim = " + yAim + " dirAim.Circle = " + dirAimCircle + " yAimFactor = " + yAimFactor);
            if(yAim < -circleAimMax)
            {
                dirAimCircle = 1;
                yAim = -circleAimMax;
            }
        }
        else 
        {
            yAim += yAimFactor;
//                                                        System.out.println("2" + " yAim = " + yAim + " dirAim.Circle = " + dirAimCircle + " yAimFactor = " + yAimFactor);
            if(yAim > circleAimMax)
            {
                dirAimCircle = -1;
                yAim = circleAimMax;
            }   
        }
        xAim = -75;

    }
    
    private void circleDiameterPing()
    {
        pingSpeedMod++;
        if(pingSpeedMod % 1 == 0)
        {
            circleDiameter--;
            if (circleDiameter == 0)
            circleDiameter = 20;
        }
    }
    private void circularPath()
    {
        if (xCirPos > cirRadius)
            mod = -1;
        else if (xCirPos < -cirRadius)
            mod = 1;
            
        yCirPos = (int)Math.sqrt(Math.pow(cirRadius, 2) - Math.pow(xCirPos, 2)) * mod;
//        System.out.println((double)yCirPos);
        xCirPos += (1 * mod);
    }
    
    private void scoreFind(int xIn, int yIn)
    {
//        System.out.println("xIn = " + xIn + " yIn = " + yIn + " angle = " + angle);
        int distance = xIn*xIn + yIn*yIn ;
        int radius = (board.getIconWidth() / 2) * (board.getIconWidth() / 2);
        if ( distance > radius)
        {
//            System.out.println("miss detected");
            if(distance > (radius * 10))
            {
                System.out.println(1);
                playSound(terrible);
            }
            else
            {
                System.out.println(2);
                dartWall.play();
//                wallImpact.start();
//                if (!(wallImpact.isRunning()))
//                {
//                    wallImpact.stop();
//                }
            }
            turnScore = 0;
            scoreMultiplier = 0;

        }
        else
        {
//            System.out.println("distance = " + distance + " radius = " + radius);
            dartBoard.play();
            
//            System.out.println("hit detected");

            if (distance > 2*2)
            {
                if (distance > 5*5)
                {
                    if(distance > 35*35)
                    {
                        if(distance > 38*38)
                        {
                            if(distance > 56*56)
                            {
                                if (distance > 59*59)
                                {
//                                    System.out.println("outside rim");
                                    scoreMultiplier = 0;
                                    turnScore = 0;
                                    return;
                                }
//                                System.out.println("double ring");
                                scoreMultiplier = 2;
                                        playerScore += scoreMultiplierFind(xIn, yIn);

                                return;
                            }
//                            System.out.println("between double ring and triple ring");
                            scoreMultiplier = 1;
                                    playerScore += scoreMultiplierFind(xIn, yIn);

                            return;
                        }
//                        System.out.println("triple ring");
                        scoreMultiplier = 3;
                                playerScore += scoreMultiplierFind(xIn, yIn);

                        return;
                    }
//                    System.out.println("between outer bullseye and triple ring");
                    scoreMultiplier = 1;
                            playerScore += scoreMultiplierFind(xIn, yIn);

                    return;
                }
//                System.out.println("outer bullseye ring");
                scoreMultiplier = 1;
                turnScore = 25;
                playerScore += turnScore;
                return;
            }
//            System.out.println("BULLSEYE");
            scoreMultiplier = 1;
            turnScore = 50;
            playerScore += turnScore;
            return;
        }
//        System.out.println(3);

        return;
    }
    private static double angleFind(double xIn, double yIn)
    {
//        System.out.println("xIn = " + xIn + " yIn = " + yIn + " angle to degrees " + Math.toDegrees(Math.atan2(yIn, xIn)));
        return Math.toDegrees(Math.atan2(yIn, xIn)); // y , x
        
    }
    private static int scoreMultiplierFind(int xIn, int yIn)
    {
        sideY = yIn - (height / 2);
        sideX = xIn - (width / 2);
//        System.out.println(yIn + " = sideY sideX = " + xIn);
        if (-yIn > 0)
        {
            if(xIn > 0)
            {
//                System.out.println("on right side1111" + " angle = " + angle);
//                System.out.println("Quandrant IV");
                if(angle <= -81)
                {
//                    System.out.println("hit 3");
                    turnScore = 3;
                }
                else if(angle <= -63)
                {
//                    System.out.println("hit 17");
                    turnScore = 17;
                }
                else if(angle <= -45)
                {
//                    System.out.println("hit 2");
                    turnScore = 2;
                }
                else if(angle <= -27)
                {
//                    System.out.println("hit 15");
                    turnScore = 15;
                }                
                else if(angle <= -9)
                {
//                    System.out.println("hit 10");
                    turnScore = 10;
                }
                else
                {
//                    System.out.println("hit 6");
                    turnScore = 6;

                }
            }
            else
            {
//                System.out.println("Quandrant III");
                if(angle <= -171)
                {
//                    System.out.println("hit 11");
                    turnScore = 11;
                }
                else if(angle <= -153)
                {
//                    System.out.println("hit 8");
                    turnScore = 8;
                }
                else if(angle <= -135)
                {
//                    System.out.println("hit 16");
                    turnScore = 16;
                }
                else if(angle <= -117)
                {
//                    System.out.println("hit 7");
                    turnScore = 7;
                }
                else if(angle <= -99)
                {
//                    System.out.println("hit 19");
                    turnScore = 19;
                }
                else
                {
//                    System.out.println("hit 3");
                    turnScore = 3;
                }
            }
//            System.out.println("on bottom3333");
        }
        else
        {
            if(xIn > 0)
            {
//                System.out.println("Quadrant I" + " angle = " + angle + " angle using sin = " + 73.0*Math.sin(5.0*Math.PI/10.0));
                if(angle >= 81)
                {
//                    System.out.println("hit 20");
                    turnScore = 20;
                }
                else if(angle >= 63)
                {
//                    System.out.println("hit 1");                    
                    turnScore = 1;
                }   
                else if(angle >= 45)
                {
//                    System.out.println("hit 18");
                    turnScore = 18;
                }
                else if(angle >= 27)
                {
//                    System.out.println("hit 4");
                    turnScore = 4;
                }
                else if(angle >= 9)
                {
//                    System.out.println("hit 13");
                    turnScore = 13;
                }
                else
                {
//                    System.out.println("hit 6");
                    turnScore = 6;
                }
            }
            else
            {
//                System.out.println("Quandrant II");
                if(angle >= 171)
                {
//                    System.out.println("hit 11");
                    turnScore = 11;
                }
                else if(angle >= 153)
                {
//                    System.out.println("hit 14");
                    turnScore = 14;
                }
                else if(angle >= 135)
                {
//                    System.out.println("hit 9");
                    turnScore = 9;
                }
                else if(angle >= 117)
                {
//                    System.out.println("hit 12");
                    turnScore = 12;
                }
                else if(angle >= 99)
                {
//                    System.out.println("hit 5");
                    turnScore = 5;
                }
                else
                {
//                    System.out.println("20");
                    turnScore = 20;
                }
            }
//            System.out.println("on top6666");
        }
                
        return turnScore * scoreMultiplier;
    }
    
    private void gameReset()
    {
//        CheckingAccount.acct1.addTrans(new Deposit(1, CheckingAccount.calculateWin(playerScore), CheckingAccount.acct1.getTransCount(), 0, playerScore));
//        CheckingAccount.bettingCharge();

        System.out.println("resetting values");
        attempt1 = false;
        attempt2 = false;
        attempt3 = false;
        attempt1F = false;
        attempt2F = false;
        attempt3F = false;
        scorePop1 = false;
        scorePop2 = false;
        scorePop3 = false;
        playerScore = 0;
        DELAY = 120;
        handArrIndex = 0;
        timer.setDelay(DELAY);
        endGame = false;
        timer.start();
        aniMode = 1;
        timerFlow.start();
        whiteOut = 0;
        endGameFirstPass = true;
        scoreColor1 = 255;
        scoreColor2 = 255;
        scoreColor3 = 255;
    }
    private void gameQuit()
    {
        System.out.println("Disposing frame");
        FinalProject.dartFrame.dispose();
        
        Main.frame.setVisible(true);
    }
    
    private String winMessage()
    {
        String message = new String("");
        String scoreStr = CheckingAccount.fmt.format(CheckingAccount.calculateWin(playerScore));
        message += ("You won " + scoreStr + "!");
        
        return message;
        
    }
    
    public static double getPlayerScore()
    {
        return playerScore;
    }
    
    private Clip setAudio(final String path) {
        try {
    //        stopPlay();
            InputStream is = new FileInputStream(path);
            InputStream bufferedIn = new BufferedInputStream(is);
            AudioInputStream ais = AudioSystem.getAudioInputStream(bufferedIn);
            AudioFormat format = ais.getFormat();
            // this is the value of format.
            // PCM_SIGNED 44100.0 Hz, 16 bit, stereo, 4 bytes/frame, little-endian
            DataLine.Info info = new DataLine.Info(Clip.class, format);
            clip = (Clip)AudioSystem.getLine(info);
            clip.open(ais);
    //        clip.start();
        } catch (Exception e) {
    //        stopPlay();
                e.printStackTrace();
        }
        
        return clip;
    }
    
    public void highScoreCheck()
    {
        if (playerScore > CheckingAccount.acct1.highScore)
        {
            CheckingAccount.acct1.highScore = playerScore;
        }
        else
        {
            highScore = CheckingAccount.acct1.highScore;
        }
    }
    
    public void tutorialOff()
    {
        tutorial = false;
        tutorial1 = false;
        tutorial2 = false;
        tutorial3 = false;
        CheckingAccount.acct1.tutorialState = false;
    }
}
