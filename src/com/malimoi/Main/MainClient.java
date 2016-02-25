package com.malimoi.Main;
/*
 * ******************************************************
 *  * Copyright (C) 2016 Malimoi <sandeaujules975@gmail.com>
 *  *
 *  * This file (MainClient) is part of YoutuberTycoon_Cars-Game.
 *  *
 *  * Created by Malimoi on 23/02/16 11:34.
 *  *
 *  * YoutuberTycoon can not be copied and/or distributed without the express
 *  * permission of Malimoi.
 *  ******************************************************
 */


import javax.swing.*;

import com.malimoi.cards.Card;
import com.malimoi.cards.InfosSpeciale;
import com.malimoi.cards.InfosYoutuber;
import com.malimoi.cards.enums.Grades;
import com.malimoi.cards.enums.TypesOfCards;
import com.malimoi.cards.enums.TypesOfThemes;
import com.malimoi.players.Player;

import de.javasoft.plaf.synthetica.SyntheticaPlainLookAndFeel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class MainClient {
	public static String version = "Alpha 0.2";
	/*
	 * Client propieties
	 */
	public static Boolean first_conn = true;
	public static String first_choice = "";
	
	/*
	 * For server
	 */
	public static boolean send = false;
	public static String str_send = "";
	
	/*
	 * Variables players
	 */
	public static List<Card> cards_list = new ArrayList<Card>();
	public static List<Card> playerCards = new ArrayList<Card>();
	public static Player player = new Player("Malimoi", "#01FE12", 1);
	
	/*
	 * ICI : PRESENT QUE DANS LES VERSIONS TEST POUR NE PAS PASSER PAS LE LAUCHER / SERVER
	 */
	public static Boolean IsTest = true;
	
	public static ChatAccess access;
	
    public static JFrame frame;

    public static void main(String[] args) {
    	
    	/*
    	 * CARDS-LIST
    	 */
    	
    	cards_list.add(new Card("Dos de carte", TypesOfCards.BACK, null, "images/cards/DOS_DE_CARTE.png", 0));
    	cards_list.add(new Card("Amandine Cuisine", TypesOfCards.YOUTUBER, new InfosYoutuber(200, 3, 1, 
    			TypesOfThemes.VIEPRATIQUE_STYLE_BEAUTE, Grades.BASE, 0), "images/cards/Amandine Cuisine.png", 1));
    	cards_list.add(new Card("Anthony-Fitness", TypesOfCards.YOUTUBER, new InfosYoutuber(350, 5, 2, TypesOfThemes.SPORT, Grades.BASE, 0),
    			"images/cards/Anthony-Fitness.png", 2));
    	cards_list.add(new Card("Aurélie Testenbeauté", TypesOfCards.YOUTUBER, new InfosYoutuber(250, 2, 3, 
    			TypesOfThemes.VIEPRATIQUE_STYLE_BEAUTE, Grades.BASE, 0), "images/cards/Aurélie Testenbeauté.png", 3));
    	cards_list.add(new Card("Boost vues 5k", TypesOfCards.SPECIALE, new InfosSpeciale(400, Grades.BASE, 1),
    			"images/cards/Boost vues 5k.png", 4));
    	cards_list.add(new Card("CAPTAIN WORKOUT", TypesOfCards.YOUTUBER, new InfosYoutuber(450, 7, 2, TypesOfThemes.SPORT, Grades.BASE, 0),
    			"images/cards/CAPTAIN WORKOUT.png", 5));
    	cards_list.add(new Card("Cédric Froment", TypesOfCards.YOUTUBER, new InfosYoutuber(300, 3, 3, TypesOfThemes.EDUCATION, Grades.BASE, 0),
    			"images/cards/Cédric Froment.png", 6));
    	cards_list.add(new Card("Ellen LC", TypesOfCards.YOUTUBER, new InfosYoutuber(250, 1, 4, 
    			TypesOfThemes.VIEPRATIQUE_STYLE_BEAUTE, Grades.BASE, 0), "images/cards/Ellen LC.png", 7));
    	cards_list.add(new Card("Esprit Riche", TypesOfCards.YOUTUBER, new InfosYoutuber(200, 2, 2, 
    			TypesOfThemes.EDUCATION, Grades.BASE, 0), "images/cards/Esprit Riche.png", 8));
    	cards_list.add(new Card("Flomars", TypesOfCards.YOUTUBER, new InfosYoutuber(450, 2, 7, 
    			TypesOfThemes.GAMING, Grades.BASE, 0), "images/cards/Flomars.png", 9));
    	cards_list.add(new Card("FrankCotty", TypesOfCards.YOUTUBER, new InfosYoutuber(350, 3, 4, 
    			TypesOfThemes.MUSIQUE, Grades.BASE, 0), "images/cards/FrankCotty.png", 10));
    	cards_list.add(new Card("Delete user gamma", TypesOfCards.SPECIALE, new InfosSpeciale(650, Grades.BASE, 2),
    			"images/cards/Delete user gamma.png", 11));
    	cards_list.add(new Card("Double ♥", TypesOfCards.SPECIALE, new InfosSpeciale(650, Grades.BASE, 3),
    			"images/cards/Double ♥.png", 12));
    	cards_list.add(new Card("Double RTs", TypesOfCards.SPECIALE, new InfosSpeciale(650, Grades.BASE, 4),
    			"images/cards/Double RTs.png", 13));
    	cards_list.add(new Card("Fred deBeauxArts", TypesOfCards.YOUTUBER, new InfosYoutuber(300, 3, 3, 
    			TypesOfThemes.VIEPRATIQUE_STYLE_BEAUTE, Grades.BASE, 0), "images/cards/Fred deBeauxArts.png", 14));
    	cards_list.add(new Card("Gadu Gaming", TypesOfCards.YOUTUBER, new InfosYoutuber(350, 3, 4, 
    			TypesOfThemes.GAMING, Grades.BASE, 0), "images/cards/Gadu Gaming.png", 15));
    	cards_list.add(new Card("JeanFaitTrop", TypesOfCards.YOUTUBER, new InfosYoutuber(350, 4, 3, 
    			TypesOfThemes.HUMOUR_DIVERTISSEMENT, Grades.BASE, 0), "images/cards/JeanFaitTrop.png", 16));
    	cards_list.add(new Card("Léonard", TypesOfCards.YOUTUBER, new InfosYoutuber(300, 2, 4, 
    			TypesOfThemes.HUMOUR_DIVERTISSEMENT, Grades.BASE, 0), "images/cards/Léonard.png", 17));
    	cards_list.add(new Card("Loulou", TypesOfCards.YOUTUBER, new InfosYoutuber(400, 3, 5, 
    			TypesOfThemes.HUMOUR_DIVERTISSEMENT, Grades.BASE, 0), "images/cards/Loulou.png", 18));
    	cards_list.add(new Card("Monte Le Son", TypesOfCards.YOUTUBER, new InfosYoutuber(150, 1, 2, 
    			TypesOfThemes.MUSIQUE, Grades.BASE, 0), "images/cards/Monte Le Son.png", 19));
    	cards_list.add(new Card("Napoleon", TypesOfCards.YOUTUBER, new InfosYoutuber(400, 4, 4, 
    			TypesOfThemes.GAMING, Grades.BASE, 0), "images/cards/Napoleon.png", 20));
    	cards_list.add(new Card("PikaShoute", TypesOfCards.YOUTUBER, new InfosYoutuber(200, 2, 2, 
    			TypesOfThemes.HUMOUR_DIVERTISSEMENT, Grades.BASE, 0), "images/cards/PikaShoute.png", 21));
    	cards_list.add(new Card("RAPH&MAX", TypesOfCards.YOUTUBER, new InfosYoutuber(500, 4, 6, 
    			TypesOfThemes.HUMOUR_DIVERTISSEMENT, Grades.BASE, 0), "images/cards/RAPH&MAX.png", 22));
    	cards_list.add(new Card("Roxas_", TypesOfCards.YOUTUBER, new InfosYoutuber(250, 4, 1, 
    			TypesOfThemes.GAMING, Grades.BASE, 0), "images/cards/Roxas_.png", 23));
    	cards_list.add(new Card("Samia Tsuki", TypesOfCards.YOUTUBER, new InfosYoutuber(300, 2, 4, 
    			TypesOfThemes.VIEPRATIQUE_STYLE_BEAUTE, Grades.BASE, 0), "images/cards/Samia Tsuki.png", 24));
    	cards_list.add(new Card("SophieGuichard", TypesOfCards.YOUTUBER, new InfosYoutuber(150, 1, 2, 
    			TypesOfThemes.EDUCATION, Grades.BASE, 0), "images/cards/SophieGuichard.png", 25));
    	cards_list.add(new Card("TheSeB's", TypesOfCards.YOUTUBER, new InfosYoutuber(400, 5, 3, 
    			TypesOfThemes.GAMING, Grades.BASE, 0), "images/cards/TheSeB's.png", 26));
    	cards_list.add(new Card("Tidark Production", TypesOfCards.YOUTUBER, new InfosYoutuber(450, 1, 8, 
    			TypesOfThemes.MUSIQUE, Grades.BASE, 0), "images/cards/Tidark Production.png", 27));
    	cards_list.add(new Card("Toc Production", TypesOfCards.YOUTUBER, new InfosYoutuber(350, 4, 3, 
    			TypesOfThemes.HUMOUR_DIVERTISSEMENT, Grades.BASE, 0), "images/cards/Toc Production.png", 28));
    	cards_list.add(new Card("ZORKA", TypesOfCards.YOUTUBER, new InfosYoutuber(450, 5, 4, 
    			TypesOfThemes.HUMOUR_DIVERTISSEMENT, Grades.BASE, 0), "images/cards/ZORKA.png", 29));
    	
    	playerCards = cards_list;
    	
    	
    	if (!IsTest){
    		String server = "5.196.72.214";
            int port = 25565;
            //ChatAccess access = null;
            try {
                access = new ChatAccess(server, port);
            } catch (IOException ex) {
                System.out.println("Cannot connect to " + server + ":" + port);
                ex.printStackTrace();
                System.exit(0);
            }
            frame = new ChatFrame(access);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setResizable(false);
            frame.setVisible(false);
            
            //new Launcher();

    	}else{
    		setJFrames();
    	}
    }
    public static void setJFrames(){
     	 /*
          * Ceci sont des valeurs fictives. Il faudra seulement get toutes les horraires sur le server.
 		 */
 
         
         UIManager.put("Synthetica.window.decoration", Boolean.FALSE);
         try {
     		UIManager.setLookAndFeel(new SyntheticaPlainLookAndFeel());
     	} catch (Exception e) {
     		e.printStackTrace();
     	} 
         
         new GameFrame(new Player("KevinX", "#FEDC01", 1));
         
         /*
         JFrame frame = new JFrame();
         frame.setTitle("YoutuberTycoon V0.0.2");
         frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         frame.pack();
         frame.setLocationRelativeTo(null);
         frame.setResizable(false);
         frame.setVisible(true);
         */
     }
    /**
     * Chat client access
     */
    public static class ChatAccess extends Observable {
        private static final String CRLF = "\r\n"; // newline
        private Socket socket;
        private OutputStream outputStream;
		//private PrintWriter out = null;

        /**
         * Create socket, and receiving thread
         */
        public ChatAccess(String server, int port) throws IOException {
            socket = new Socket(server, port);
            outputStream = socket.getOutputStream();

            Thread receivingThread = new Thread() {
				@Override
                public void run() {
                    try {
                        BufferedReader in = new BufferedReader(
                                new InputStreamReader(socket.getInputStream()));
                        String line;
                        while (true)
                        	if ((line = in.readLine()) != null){
                        		//
                        	}
                            
                    } catch (IOException ex) {
                        notifyObservers(ex);
                    }
                }
            };
            receivingThread.start();
        }

        @Override
        public void notifyObservers(Object arg) {
            super.setChanged();
            super.notifyObservers(arg);
        }

        /**
         * Send a line of text
         */
        public void send(String text) {
            try {
                outputStream.write((text + CRLF).getBytes());
                outputStream.flush();
            } catch (IOException ex) {
                notifyObservers(ex);
            }
        }

        /**
         * Close the socket
         */
        public void close() {
            try {
                socket.close();
            } catch (IOException ex) {
                notifyObservers(ex);
            }
        }

    }

    /**
     * Chat client UI
     */
    @SuppressWarnings("serial")
	static class ChatFrame extends JFrame implements Observer {

        private JTextArea textArea;
        private JTextField inputTextField;
        private JButton sendButton;
        private ChatAccess chatAccess;

        public ChatFrame(ChatAccess chatAccess) {
            this.chatAccess = chatAccess;
            chatAccess.addObserver(this);
            buildGUI();
        }

        /* Builds the user interface */

        private void buildGUI() {
            textArea = new JTextArea(20, 50);
            textArea.setEditable(false);
            textArea.setLineWrap(true);
            add(new JScrollPane(textArea), BorderLayout.CENTER);

            Box box = Box.createHorizontalBox();
            add(box, BorderLayout.SOUTH);
            inputTextField = new JTextField();
            sendButton = new JButton("Envoyé");
            box.add(inputTextField);
            box.add(sendButton);

            // Action for the inputTextField and the goButton
            ActionListener sendListener = new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    String str = inputTextField.getText();
                    if (str != null && str.trim().length() > 0)
                        chatAccess.send(str);
                    inputTextField.selectAll();
                    inputTextField.requestFocus();
                    inputTextField.setText("");
                }
            };
            inputTextField.addActionListener(sendListener);
            sendButton.addActionListener(sendListener);

            this.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    chatAccess.close();
                }
            });
        }

        /**
         * Updates the UI depending on the Object argument
         */
        public void update(Observable o, Object arg) {
            final Object finalArg = arg;
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    textArea.append(finalArg.toString());
                    textArea.append("\n");
                }
            });
        }
    }
    
}