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

import com.malimoi.Main.GameFrame.PlaySound;
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
	 * CONSTANT
	 */
	public static final String[] THEMES_IMAGES_PATH = {"HUMOUR.png","STYLE.png","SPORT.png","MUSIQUE.png","GAMING.png","EDUCATION.png"};
	
	/*
	 * Variables players
	 */
	public static final Card[] cards_list = { new Card("Dos de carte", TypesOfCards.BACK, null, "images/cards/DOS_DE_CARTE.png", 0),
	    	new Card("Amandine Cuisine", TypesOfCards.YOUTUBER, new InfosYoutuber(200, 3, 1, 
	    			TypesOfThemes.VIEPRATIQUE_STYLE_BEAUTE, Grades.BASE, 0), "images/cards/Amandine Cuisine.png", 1),
	    	new Card("Anthony-Fitness", TypesOfCards.YOUTUBER, new InfosYoutuber(350, 5, 2, TypesOfThemes.SPORT, Grades.BASE, 0),
	    			"images/cards/Anthony-Fitness.png", 2),
	    	new Card("Aurélie Testenbeauté", TypesOfCards.YOUTUBER, new InfosYoutuber(250, 2, 3, 
	    			TypesOfThemes.VIEPRATIQUE_STYLE_BEAUTE, Grades.BASE, 0), "images/cards/Aurélie Testenbeauté.png", 3),
	    	new Card("Boost vues 5k", TypesOfCards.SPECIALE, new InfosSpeciale(400, Grades.BASE, 1),
	    			"images/cards/Boost vues 5k.png", 4),
	    	new Card("CAPTAIN WORKOUT", TypesOfCards.YOUTUBER, new InfosYoutuber(450, 7, 2, TypesOfThemes.SPORT, Grades.BASE, 0),
	    			"images/cards/CAPTAIN WORKOUT.png", 5),
	    	new Card("Cédric Froment", TypesOfCards.YOUTUBER, new InfosYoutuber(300, 3, 3, TypesOfThemes.EDUCATION, Grades.BASE, 0),
	    			"images/cards/Cédric Froment.png", 6),
	    	new Card("Ellen LC", TypesOfCards.YOUTUBER, new InfosYoutuber(250, 1, 4, 
	    			TypesOfThemes.VIEPRATIQUE_STYLE_BEAUTE, Grades.BASE, 0), "images/cards/Ellen LC.png", 7),
	    	new Card("Esprit Riche", TypesOfCards.YOUTUBER, new InfosYoutuber(200, 2, 2, 
	    			TypesOfThemes.EDUCATION, Grades.BASE, 0), "images/cards/Esprit Riche.png", 8),
	    	new Card("Flomars", TypesOfCards.YOUTUBER, new InfosYoutuber(450, 2, 7, 
	    			TypesOfThemes.GAMING, Grades.BASE, 0), "images/cards/Flomars.png", 9),
	    	new Card("FrankCotty", TypesOfCards.YOUTUBER, new InfosYoutuber(350, 3, 4, 
	    			TypesOfThemes.MUSIQUE, Grades.BASE, 0), "images/cards/FrankCotty.png", 10),
	    	new Card("Delete user gamma", TypesOfCards.SPECIALE, new InfosSpeciale(650, Grades.BASE, 2),
	    			"images/cards/Delete user gamma.png", 11),
	    	new Card("Double ♥", TypesOfCards.SPECIALE, new InfosSpeciale(650, Grades.BASE, 3),
	    			"images/cards/Double ♥.png", 12),
	    	new Card("Double RTs", TypesOfCards.SPECIALE, new InfosSpeciale(650, Grades.BASE, 4),
	    			"images/cards/Double RTs.png", 13),
	    	new Card("Fred deBeauxArts", TypesOfCards.YOUTUBER, new InfosYoutuber(300, 3, 3, 
	    			TypesOfThemes.VIEPRATIQUE_STYLE_BEAUTE, Grades.BASE, 0), "images/cards/Fred deBeauxArts.png", 14),
	    	new Card("Gadu Gaming", TypesOfCards.YOUTUBER, new InfosYoutuber(350, 3, 4, 
	    			TypesOfThemes.GAMING, Grades.BASE, 0), "images/cards/Gadu Gaming.png", 15),
	    	new Card("JeanFaitTrop", TypesOfCards.YOUTUBER, new InfosYoutuber(350, 4, 3, 
	    			TypesOfThemes.HUMOUR_DIVERTISSEMENT, Grades.BASE, 0), "images/cards/JeanFaitTrop.png", 16),
	    	new Card("Léonard", TypesOfCards.YOUTUBER, new InfosYoutuber(300, 2, 4, 
	    			TypesOfThemes.HUMOUR_DIVERTISSEMENT, Grades.BASE, 0), "images/cards/Léonard.png", 17),
	    	new Card("Loulou", TypesOfCards.YOUTUBER, new InfosYoutuber(400, 3, 5, 
	    			TypesOfThemes.HUMOUR_DIVERTISSEMENT, Grades.BASE, 0), "images/cards/Loulou.png", 18),
	    	new Card("Monte Le Son", TypesOfCards.YOUTUBER, new InfosYoutuber(150, 1, 2, 
	    			TypesOfThemes.MUSIQUE, Grades.BASE, 0), "images/cards/Monte Le Son.png", 19),
	    	new Card("Napoleon", TypesOfCards.YOUTUBER, new InfosYoutuber(400, 4, 4, 
	    			TypesOfThemes.GAMING, Grades.BASE, 0), "images/cards/Napoleon.png", 20),
	    	new Card("PikaShoute", TypesOfCards.YOUTUBER, new InfosYoutuber(200, 2, 2, 
	    			TypesOfThemes.HUMOUR_DIVERTISSEMENT, Grades.BASE, 0), "images/cards/PikaShoute.png", 21),
	    	new Card("RAPH&MAX", TypesOfCards.YOUTUBER, new InfosYoutuber(500, 4, 6, 
	    			TypesOfThemes.HUMOUR_DIVERTISSEMENT, Grades.BASE, 0), "images/cards/RAPH&MAX.png", 22),
	    	new Card("Roxas_", TypesOfCards.YOUTUBER, new InfosYoutuber(250, 4, 1, 
	    			TypesOfThemes.GAMING, Grades.BASE, 0), "images/cards/Roxas_.png", 23),
	    	new Card("Samia Tsuki", TypesOfCards.YOUTUBER, new InfosYoutuber(300, 2, 4, 
	    			TypesOfThemes.VIEPRATIQUE_STYLE_BEAUTE, Grades.BASE, 0), "images/cards/Samia Tsuki.png", 24),
	    	new Card("SophieGuichard", TypesOfCards.YOUTUBER, new InfosYoutuber(150, 1, 2, 
	    			TypesOfThemes.EDUCATION, Grades.BASE, 0), "images/cards/SophieGuichard.png", 25),
	    	new Card("TheSeB's", TypesOfCards.YOUTUBER, new InfosYoutuber(400, 5, 3, 
	    			TypesOfThemes.GAMING, Grades.BASE, 0), "images/cards/TheSeB's.png", 26),
	    	new Card("Tidark Production", TypesOfCards.YOUTUBER, new InfosYoutuber(450, 1, 8, 
	    			TypesOfThemes.MUSIQUE, Grades.BASE, 0), "images/cards/Tidark Production.png", 27),
	    	new Card("Toc Production", TypesOfCards.YOUTUBER, new InfosYoutuber(350, 4, 3, 
	    			TypesOfThemes.HUMOUR_DIVERTISSEMENT, Grades.BASE, 0), "images/cards/Toc Production.png", 28),
	    	new Card("ZORKA", TypesOfCards.YOUTUBER, new InfosYoutuber(450, 5, 4, 
	    			TypesOfThemes.HUMOUR_DIVERTISSEMENT, Grades.BASE, 0), "images/cards/ZORKA.png", 29) };
	
	public static List<Card> playerCards = new ArrayList<Card>();
	public static Player player = new Player("Kevin", "#01FE12", 1, 1, new ArrayList<Card>(), new ArrayList<Card>());
	public static Boolean canPlay = false;
	
	/*
	 * ICI : PRESENT QUE DANS LES VERSIONS TEST POUR NE PAS PASSER PAS LE LAUCHER / SERVER
	 */
	public static Boolean IsTest = false;
	
	public static ChatAccess access;
	
    public static JFrame frame;
    
    public static Integer a = 10;
    public static final Integer c = 10;

    public static void main(String[] args) {
    	
    	/*
    	 * CARDS-LIST
    	 */
    	
    	try{
    		InputStream flux=new FileInputStream("card.txt"); 
    		InputStreamReader lecture=new InputStreamReader(flux);
    		BufferedReader buff=new BufferedReader(lecture);
    		String ligne;
    		while ((ligne=buff.readLine())!=null){
    			System.out.println(ligne);
    		}
    		buff.close(); 
    		}		
    		catch (Exception e){
    		System.out.println(e.toString());
    		}  
    	
    	
    	if (!IsTest){
    		String server = "127.0.0.1";
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
            
            new Launcher();

    	}else{
    		setJFrames();
    	}
    }
    
    public static TypesOfCards getTypesOfCardsFile(String type){
    	
    	TypesOfCards TOC = null;
    	
    	if (type.contains("YOUTUBER")){
    		TOC=TypesOfCards.YOUTUBER;
    	}else if (type.contains("SPECIALE")){
    		TOC=TypesOfCards.SPECIALE;
    	}else if (type.contains("BACK")){
    		TOC=TypesOfCards.BACK;
    	}
    	
		return TOC;
    	
    }
    
    public static TypesOfThemes getThemesFile(String theme){
    	TypesOfThemes Theme = null;
    	
    	if (theme.contains("EDUCATION")){
    		Theme=TypesOfThemes.EDUCATION;
    	}else if (theme.contains("GAMING")){
    		Theme=TypesOfThemes.GAMING;
    	}else if (theme.contains("HUMOUR_DIVERTISSEMENT")){
    		Theme=TypesOfThemes.HUMOUR_DIVERTISSEMENT;
    	}else if (theme.contains("MUSIQUE")){
    		Theme=TypesOfThemes.MUSIQUE;
    	}else if (theme.contains("RIEN")){
    		Theme=TypesOfThemes.RIEN;
    	}else if (theme.contains("SPORT")){
    		Theme=TypesOfThemes.SPORT;
    	}else if (theme.contains("VIEPRATIQUE_STYLE_BEAUTE")){
    		Theme=TypesOfThemes.VIEPRATIQUE_STYLE_BEAUTE;
    	}
    	
		return Theme;
    	
    }
    
    public static Grades getGradesFile(String grade){
    	
    	Grades Grade = null;
    	
    	if (grade.contains("BASE")){
    		Grade=Grades.BASE;
    	}else if (grade.contains("ARGENT")){
    		Grade=Grades.ARGENT;
    	}else if (grade.contains("BRONZE")){
    		Grade=Grades.BRONZE;
    	}else if (grade.contains("DIAMANT")){
    		Grade=Grades.DIAMANT;
    	}else if (grade.contains("OR")){
    		Grade=Grades.OR;
    	}
    	
		return Grade;
    	
    	
    }
    
    public static void setJFrames(){
         
         UIManager.put("Synthetica.window.decoration", Boolean.FALSE);
         try {
     		UIManager.setLookAndFeel(new SyntheticaPlainLookAndFeel());
     	} catch (Exception e) {
     		e.printStackTrace();
     	} 
         
         new GameFrame();

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
                        		System.out.println(line);
                        		String[] contains = line.split(" ");
                        		String[] containsVir = line.split(",");
                        		if (line.startsWith("startthegame")){
                        			Thread pass = new Thread(new PasserelThread());
                        			pass.start();
                        		}else if(line.startsWith("adv")){
                        			GameFrame.playerFollowers=0;
                        			GameFrame.advFollowers=0;
                        			
                        			String[] s = line.split(" ");
                        			GameFrame.advTrouve=true;
                        			GameFrame.StartGame(new Player(s[1], s[2], Integer.valueOf(s[3]), Integer.valueOf(s[4]),
                        					new ArrayList<Card>(), new ArrayList<Card>()));	
                        			if (Integer.valueOf(s[5]).equals(0)){
                        				canPlay=true;
                        				GameFrame.playerFollowers+=100;
                        				access.send("pioche");
                        			}
                        			
                        			
                        		}else if(line.startsWith("pioche")){
                        			Thread t = new Thread(new GameFrame.PlaySound("pop.wav"));
                					t.start();
                					
                					String name = containsVir[1];
                					TypesOfCards type = getTypesOfCardsFile(containsVir[2]);
                					
                					int followers = 0;
                					int rts = 0;
                					int hearts = 0;
                					TypesOfThemes theme = TypesOfThemes.RIEN;
                					Grades grade = Grades.BASE;
                					int id_power = 0;
                					String path = "";
                					int id = 0;
                					
                					if (type==TypesOfCards.YOUTUBER){
                						followers = Integer.valueOf(containsVir[3]);
                    					rts = Integer.valueOf(containsVir[4]);
                    					hearts = Integer.valueOf(containsVir[5]);
                    					theme = getThemesFile(containsVir[6]);
                    					grade = getGradesFile(containsVir[7]);
                    					id_power = Integer.valueOf(containsVir[8]);
                    					path = containsVir[9];
                    					id = Integer.valueOf(containsVir[10]);
                					}else if(type==TypesOfCards.SPECIALE){
                						followers = Integer.valueOf(containsVir[3]);
                						grade = getGradesFile(containsVir[4]);
                    					id_power = Integer.valueOf(containsVir[5]);
                    					path = containsVir[6];
                    					id = Integer.valueOf(containsVir[7]);
                					}
                					
                					
                					
                        			if (type==TypesOfCards.YOUTUBER){
                        				player.getHandCards().add(new Card(name, type, new InfosYoutuber(followers,
                        						rts, hearts, theme, grade, id_power), path, id));
                        			}else if(type==TypesOfCards.SPECIALE){
                        				player.getHandCards().add(new Card(name, type, new InfosSpeciale(followers,
                        						grade, id_power), path, id));
                        			}
                     			
                        			GameFrame.PrepareUpdate();
                        			
                        		}else if(line.startsWith("pose")){
                        			GameFrame.twiit_list.clear();
                        			GameFrame.cards_list.clear();
                        			
                        			String name = containsVir[2];
                					TypesOfCards type = getTypesOfCardsFile(containsVir[3]);
                					
                					int followers = 0;
                					int rts = 0;
                					int hearts = 0;
                					TypesOfThemes theme = TypesOfThemes.RIEN;
                					Grades grade = Grades.BASE;
                					int id_power = 0;
                					String path = "";
                					int id = 0;
                					
                					if (type==TypesOfCards.YOUTUBER){
                						followers = Integer.valueOf(containsVir[4]);
                    					rts = Integer.valueOf(containsVir[5]);
                    					hearts = Integer.valueOf(containsVir[6]);
                    					theme = getThemesFile(containsVir[7]);
                    					grade = getGradesFile(containsVir[8]);
                    					id_power = Integer.valueOf(containsVir[9]);
                    					path = containsVir[10];
                    					id = Integer.valueOf(containsVir[11]);
                					}else if(type==TypesOfCards.SPECIALE){
                						followers = Integer.valueOf(containsVir[4]);
                						grade = getGradesFile(containsVir[5]);
                    					id_power = Integer.valueOf(containsVir[6]);
                    					path = containsVir[7];
                    					id = Integer.valueOf(containsVir[8]);
                					}

                        			if (type==TypesOfCards.YOUTUBER){
                        				GameFrame.lastAdvCard=new Card(name, type, new InfosYoutuber(followers,
                        						rts, hearts, theme, grade, id_power), path, id);
                        			}else if(type==TypesOfCards.SPECIALE){
                        				GameFrame.lastAdvCard=new Card(name, type, new InfosSpeciale(followers,
                        						grade, id_power), path, id);
                        			}
					
        							if (GameFrame.lastAdvCard.getType().equals(TypesOfCards.YOUTUBER)){						
        								
        								GameFrame.twiitListCard.add(GameFrame.lastAdvCard);
        								GameFrame.twiitListPlayer.add(GameFrame.Adversaire);
        								
        							}
        							GameFrame.advFollowers=(int) (Integer.valueOf(line.split(",")[1]));
        							GameFrame.content.removeAll();
        							GameFrame.UpdateContent();
                        		}else if(line.startsWith("toursuivant")){
                        			canPlay=true;
                        			GameFrame.alreadyPlay.clear();
                        			access.send("pioche");
                        			GameFrame.specialMod=0;
                        			GameFrame.playerFollowers+=100;
                        			GameFrame.PrepareUpdate();
                        		}else if(line.startsWith("dammage")){ //pense a faire un plusieurs en 1
                        			int place = Integer.valueOf(contains[1]);
                        			for (int i = 0; i < GameFrame.twiitListCard.size(); i++){
                        				if (GameFrame.twiitListCard.get(place) == GameFrame.twiitListCard.get(i)){
                        					
                        					GameFrame.twiitListCard.get(i).getInfos().setHearts(Integer.valueOf(contains[2]));
                        					
                        					if (GameFrame.twiitListCard.get(i).getInfos().getHearts() <= 0){
                        						GameFrame.twiitListCard.remove(i);
                        						/*
                        						 * Ne pas oublier d'enlever le player !!!
                        						 */
                        						GameFrame.twiitListPlayer.remove(i);
                        					}

                        					GameFrame.PrepareUpdate();
                        					break;
                        				}
                        			}
                        		}else if(line.startsWith("views")){
                        			int views = Integer.valueOf(contains[2]);
                        			Thread th = new Thread(new GameFrame.AnimationViewsThread(Integer.valueOf(contains[1]), views));
                        			th.start();
                        		}
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
    
    public static class PasserelThread implements Runnable{
    	
    	public void run(){
    		setJFrames();
    	}
    	
    }
    
}