package com.malimoi.Main;

import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.malimoi.cards.Card;
import com.malimoi.cards.enums.TypesOfCards;
import com.malimoi.cards.enums.TypesOfThemes;
import com.malimoi.players.Player;
import com.sun.media.jfxmedia.AudioClip;

import sun.applet.Main;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

@SuppressWarnings("serial")
public class GameFrame extends JFrame{
	public static GraphicsEnvironment graphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();

	// get maximum window bounds
	public static Rectangle maximumWindowBounds = graphicsEnvironment.getMaximumWindowBounds();

	// -FINAL
	static int HAUT = (int) maximumWindowBounds.getHeight();
	static int LARG = (int) maximumWindowBounds.getWidth();
	
	public static int HAUTEUR = HAUT;
	public static int LARGEUR = LARG;
	public static final int TWIIT_WIDTH = 140*4;
	public static final int TWIIT_HEIGHT = 90;
	public static final int CARDS_WIDTH = 150;
	public static final int CARDS_HEIGHT = 216;
	
	public static Card lastPlayerCard = MainClient.cards_list[0];
	public static Card lastAdvCard = MainClient.cards_list[0];
	
	// -CONTENT
	public static JPanel content = new AddImages("images/BACKGROUND1.jpg",LARGEUR,HAUTEUR);
	public static AddCards livePlayerCard = new AddCards("images/cards/DOS_DE_CARTE.png", 280, 403);
	public static AddCards liveAdvCard = new AddCards("images/cards/DOS_DE_CARTE.png", 280, 403);
	
	// -
	public static Player Adversaire;
	public static List<Player> listPlayers = new ArrayList<Player>();
	
	public static List<JPanel> cards_list = new ArrayList<JPanel>();
	public static List<ColorPanel> twiit_list = new ArrayList<ColorPanel>();
	public static List<Player> twiitListPlayer = new ArrayList<Player>();
	public static final List<Card> twiitListCard = new ArrayList<Card>();
	public static List<String> logs = new ArrayList<String>();
	
	public static JLabel msg_log1 = new JLabel();
	public static JLabel msg_log2 = new JLabel();
	
	public static int nbPlayerCards = 0;
	public static int nbAdvCards = 0;
	public static List<Card> playerCardsHand = new ArrayList<Card>();
	public static int pointerCard = -1;
	public static int pointerTwiit = -1;
	
	public static int playerFollowers = 100;
	public static int advFollowers = 100;
	public static int playerViews = 0;
	public static int advViews = 0;
	
	public static int tours = 0;
	
	public static Player startPlayer = MainClient.player;
	
	public static Boolean advTrouve = false;
	
	static JLabel apercuCard = new JLabel();
	static JLabel nbViews1 = new JLabel();
	static JLabel nbViews2 = new JLabel();
	
	public static Card twiitSelect = null;
	public static List<Card> alreadyPlay = new ArrayList<Card>();
	
	public static int specialMod = 0;
	
	public static Boolean poseTwiit = false;
	
	public static Color[] colorTab = {Color.RED,Color.YELLOW,Color.BLACK,Color.PINK,Color.GREEN,Color.BLUE,Color.ORANGE,Color.CYAN,Color.GRAY,Color.MAGENTA};
	public static Color[] colorTabOrange = {Color.decode("#E5B65A"),Color.decode("#E6B34D"),Color.decode("#E3AC3C"),Color.decode("#E3A832"),Color.decode("#E2A428"),
			Color.decode("#E2A01B"),Color.decode("#E29C11"),Color.decode("#E29908")};
	public static Color[] colorTabGreen = {Color.decode("#77DF5C"),Color.decode("#6DDE51"),Color.decode("#63DC45"),Color.decode("#5CDB3C"),Color.decode("#55DB34"),
			Color.decode("#4DD82B"),Color.decode("#46D921"),Color.decode("#3FD919")};
	
	public GameFrame(){
		
		
		Boolean size = true;
		
		if (!size){
			HAUTEUR=720;
			LARGEUR=1200;
		}
		
		content.setLayout(null);
		content.setBackground(Color.decode("#838383"));
		content.setPreferredSize(new Dimension(LARGEUR, HAUTEUR));
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setContentPane(content);
		this.setSize(LARGEUR, HAUTEUR);
		this.setVisible(true);
		
		JPanel rideau = new JPanel();
		rideau.setLayout(null);
		rideau.setBackground(Color.RED);
		rideau.setPreferredSize(new Dimension(LARGEUR, HAUTEUR));
		rideau.setBounds(0, 0, LARGEUR, HAUTEUR);
		
		content.add(rideau);
		
		
		
		/* Recherche d'adversaire, entré dans l'arène */
		MainClient.access.send("join");
		
		String n = "Recherche d'adversaire en cours";
		
		JLabel note = new JLabel();
		note.setFont(new Font("Tahoma", Font.PLAIN, 35));
		note.setText(n+"...");
		note.setForeground(Color.WHITE);
		note.setHorizontalAlignment(JLabel.CENTER);
		note.setBounds(0, HAUTEUR/2-40, LARGEUR, 40);
		rideau.add(note);
		
		Random r = new Random();
		int x = -100;
		int y = 0;
		int total = 0;
		
		boolean boucle = true;
		
		while(boucle){
			if (x>LARGEUR){
				x=0;
				y+=100;
			}else{
				x+=100;
			}
			if (! (y>(HAUTEUR+100))){
				JPanel carre = new JPanel();
				carre.setBounds(x, y, 100, 100);
				carre.setBackground(colorTabOrange[r.nextInt(colorTabOrange.length)]);
				rideau.add(carre);
				carre.repaint();
				total+=1;
			}else{
				boucle=false;
			}
			
		}
		int millisec = 0;
		while(!advTrouve){
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
				Color c = colorTabOrange[r.nextInt(colorTabOrange.length)];
				int comp = r.nextInt(total);
				rideau.getComponent(comp).setBackground(c);
				note.repaint();
				rideau.getComponent(comp).repaint();
			
		}
		
		note.setText("Adversaire trouvé !");
		
		for (int i = 0;i<HAUTEUR;i++){
			rideau.setBounds(0, 0, LARGEUR, HAUTEUR-i);
			rideau.repaint();
			try {
				Thread.sleep(2);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
	}
	
	public static void UpdateDisplayLogs(){
		int maxLogsDisplay = logs.size()>2?2:logs.size();
		for(int i = 0;i<maxLogsDisplay;i++){
			JLabel ml = i==0?msg_log1:msg_log2;
			ml.setFont(new Font("Tahoma", Font.PLAIN, 16));
			ml.setText(logs.get(logs.size()-i-1));
			ml.setForeground(Color.DARK_GRAY);
			ml.setHorizontalAlignment(JLabel.LEFT);
			ml.setBounds(LARGEUR/2 - TWIIT_WIDTH/2 + 110, TWIIT_HEIGHT*5+22+i*25, 500, 20);
			content.add(ml);
		}
	}
	
	public static void UpdateTwiitsLine(){
		int max = twiitListCard.size();
		if (max >= 5){
			max=5;
		}
		
		for (int i = 0;i<max;i++){			
			
			ColorPanel t = new ColorPanel(Color.WHITE, true, TWIIT_WIDTH, TWIIT_HEIGHT);	
			if (twiitListPlayer.get(twiitListCard.size()-1-i)==MainClient.player){
				t.setColor(Color.decode("#E7FBED"));
			}
			t.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseEntered(MouseEvent e){
					
					for (int a = 0;a<twiit_list.size();a++){
						
						if (t.equals(twiit_list.get(a))){
							
							if (twiitSelect == null){
								if (twiitListPlayer.get(twiitListCard.size()-1-a)==MainClient.player){
									t.setColor(Color.decode("#E1E0E0"));
								}
							}else{
								if (twiitListPlayer.get(twiitListCard.size()-1-a)==Adversaire){
									t.setColor(Color.decode("#FEB3B3"));
								}
							}
						
							apercuCard.setText("Aperçu:");
							livePlayerCard.setPath(twiitListCard.get(twiitListCard.size()-1-a).getPath());
							break;
						}		
						
					}
				}
				@Override
				public void mouseExited(MouseEvent e){
					
					t.setColor(Color.WHITE);
					for (int a = 0;a<twiit_list.size();a++){
						
						if (t.equals(twiit_list.get(a))){
							if (twiitSelect == null){
								if (twiitListPlayer.get(twiitListCard.size()-1-a)==MainClient.player){
									t.setColor(Color.decode("#E7FBED"));
									break;
								}
							}else{
								if (twiitListPlayer.get(twiitListCard.size()-1-a)==MainClient.player){
									t.setColor(Color.decode("#E7FBED"));
									if (twiitSelect==twiitListCard.get(twiitListCard.size()-1-a)){
										t.setColor(Color.decode("#B3FEB5"));
										break;
									}					
								}if (twiitListPlayer.get(twiitListCard.size()-1-a)==Adversaire){
										t.setColor(Color.WHITE);
										break;				
								}
							}
						}		
						
					}
					apercuCard.setText("Dernière carte jouée:");
					livePlayerCard.setPath(lastPlayerCard.getPath());
				}
				@Override
				public void mousePressed(MouseEvent e){
						
					
					
					if (twiitSelect == null){
						for (int a = 0;a<twiit_list.size();a++){
							
							if (t.equals(twiit_list.get(a))){
								
								if (twiitListPlayer.get(twiitListCard.size()-1-a)==MainClient.player){
										if (specialMod>0){
											if (specialMod==2){
												
											}
											
											specialMod=0;
											break;
										}else{
											boolean already = false;
											for (int b = 0; b < alreadyPlay.size(); b++){
												if (alreadyPlay.get(b).equals(twiitListCard.get(twiitListCard.size()-1-a))){
													already=true;
													break;
												}
											}
											if(!already){
												t.setColor(Color.decode("#B3FEB5"));
												twiitSelect=twiitListCard.get(twiitListCard.size()-1-a);
												
											}
											break;		
										}
											
								}
								
								
							}
						}
					}else{
						for (int a = 0;a<twiit_list.size();a++){
							
							if (t.equals(twiit_list.get(a))){
								if (twiitListPlayer.get(twiitListCard.size()-1-a)==MainClient.player){
									if(twiitSelect==twiitListCard.get(twiitListCard.size()-1-a)){
										twiitSelect=null;
										t.setColor(Color.WHITE);
										break;
									}
								}
								
								if (twiitListPlayer.get(twiitListCard.size()-1-a)==Adversaire){
									for (int b = 0; b<twiitListCard.size(); b++){
										if (twiitListCard.get(b).equals(twiitSelect) && twiitListPlayer.get(b).equals(MainClient.player)){
											/*
											 * Faire verif server
											 */
											MainClient.access.send("attack "+b+" "+(twiitListCard.size()-1-a));
											System.out.println(twiitListCard.get(b).getName()+" attaque "+twiitListCard.get(twiitListCard.size()-1-a).getName());
											alreadyPlay.add(twiitSelect);
											twiitSelect=null;
											
											break;
										}
									}
									
								}
							}
						}
						
					}
					
				}
			});
				t.setLayout(null);
				if (i!=0 && poseTwiit==true){
					t.setBounds(LARGEUR/2 - TWIIT_WIDTH/2, 10 + (i-1)*(TWIIT_HEIGHT-1), TWIIT_WIDTH, TWIIT_HEIGHT);

				}else if (poseTwiit==true){
					t.setBounds(LARGEUR/2 - TWIIT_WIDTH/2, 10 + (i)*(TWIIT_HEIGHT-1), TWIIT_WIDTH, 0);
				}else{
					t.setBounds(LARGEUR/2 - TWIIT_WIDTH/2, 10 + (i)*(TWIIT_HEIGHT-1), TWIIT_WIDTH, TWIIT_HEIGHT);
				}
				
				
				JPanel profil = new AddImages("images/profils/"+twiitListCard.get(twiitListCard.size()-1-i).getName()+".png", 50, 50);
				profil.setBounds(10, 10, 50, 50);
				t.add(profil);
				JPanel rt = new AddImages("images/RT_GRAY.png", 20, 20);
				rt.setBounds(TWIIT_WIDTH/5, TWIIT_HEIGHT-25, 20, 20);
				t.add(rt);
				JLabel NbRts = new JLabel();
				NbRts.setFont(new Font("Tahoma", Font.PLAIN, 15));
				NbRts.setText(twiitListCard.get(twiitListCard.size()-1-i).getInfos().getRts()+"");
				NbRts.setForeground(Color.decode("#BDBDBD"));
				NbRts.setHorizontalAlignment(JLabel.LEFT);
				NbRts.setBounds(TWIIT_WIDTH/5+24, TWIIT_HEIGHT-25, 20, 20);
				t.add(NbRts);
				JPanel heart = new AddImages("images/HEART_GRAY.png", 20, 20);
				heart.setBounds(TWIIT_WIDTH/5+TWIIT_WIDTH/7, TWIIT_HEIGHT-25, 20, 20);
				t.add(heart);
				JLabel NbHearts = new JLabel();
				NbHearts.setFont(new Font("Tahoma", Font.PLAIN, 15));
				NbHearts.setText(twiitListCard.get(twiitListCard.size()-1-i).getInfos().getHearts()+"");
				NbHearts.setForeground(Color.decode("#BDBDBD"));
				NbHearts.setHorizontalAlignment(JLabel.LEFT);
				NbHearts.setBounds(TWIIT_WIDTH/5+TWIIT_WIDTH/7+23, TWIIT_HEIGHT-25, 20, 20);
				t.add(NbHearts);
				JLabel name = new JLabel();
				name.setFont(new Font("Arial", Font.BOLD, 15));
				name.setText(twiitListCard.get(twiitListCard.size()-1-i).getName());
				name.setForeground(Color.DARK_GRAY);
				name.setHorizontalAlignment(JLabel.LEFT);
				name.setBounds(10+50+5, 10, 300, 18);
				t.add(name);
				JLabel msg_l1 = new JLabel();
				msg_l1.setFont(new Font("Arial", Font.PLAIN, 15));
				msg_l1.setText("Ceci est un message pour vous dire que j'adore les frites !");
				msg_l1.setForeground(Color.DARK_GRAY);
				msg_l1.setHorizontalAlignment(JLabel.LEFT);
				msg_l1.setBounds(10+50+5, 30, TWIIT_WIDTH-65, 15);
				t.add(msg_l1);
			
				twiit_list.add(t);
				if (poseTwiit){
					if (i!=0){
						content.add(t);
						
						Thread th = new Thread(new AnimationAddTwiit(t));
						th.start();		
					}		
					else{

						Thread th = new Thread(new AnimationAddTwiit2(t));
						th.start();
					}
					
				}
				else{
					content.add(t);
					t.repaint();

				}
				
		}	
		poseTwiit=false;
	}
	
	public static void StartGame(Player adversaire){
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Adversaire = adversaire;
		listPlayers.add(MainClient.player);
		listPlayers.add(adversaire);
		
		//InitializeContent();
		
		/*
		 * UPDATE
		 */
		
		UpdateContent();
	}
	
	public static void AnimViews(int nbPl, int addViews){
		JLabel lab = nbViews1;
		int views = playerViews;
		int max = playerViews+addViews;
		int idition = (int) (addViews / 500);
		if (nbPl==2){
			lab=nbViews2;
			views = advViews;
			max = advViews+addViews;
		}
		
		for (int i = views; i <= max; i=i+idition){
			lab.setText(i+"");
			if (nbPl==1){
				playerViews=i;
			}else{
				advViews=i;
			}
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}	
		
	}
	
	public static void AnimAddTwiit(ColorPanel cp){
		for (int i = 0; i<TWIIT_HEIGHT-1; i++){
			
			cp.setBounds((int)cp.getLocation().getX(), (int)cp.getLocation().getY()+1,
					(int)cp.getBounds().getWidth(), (int)cp.getBounds().getHeight());
			cp.repaint();
			try {
				Thread.sleep(5);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public static void AnimAddTwiit2(ColorPanel cp){
		
		content.add(cp);
		for (int i = 0; i<TWIIT_HEIGHT+1; i++){
			cp.setBounds((int)cp.getLocation().getX(), (int)cp.getLocation().getY(),
					(int)cp.getBounds().getWidth(), i);
			cp.repaint();
		}
		
	}
	
	public static void PrepareUpdate(){
		content.removeAll();
		cards_list.clear();
		twiit_list.clear();
		UpdateContent();
	}
	
	public static void InitializeContent(){
		
		for (int i = 0;i<5;i++){
			Random r = new Random();
			Card card = MainClient.playerCards.get(r.nextInt(29)+1);
			MainClient.player.getHandCards().add(card);
			System.out.println(card.getName());
		}
		
		
	}
	
	public static void InitializeBackcards(){
		
		if (pointerCard>=0){
			livePlayerCard = new AddCards(MainClient.player.getHandCards().get(pointerCard).getPath(), 280, 403);
		}else{
			livePlayerCard = new AddCards(lastPlayerCard.getPath(), 280, 403);
		}		
		liveAdvCard = new AddCards("images/cards/DOS_DE_CARTE.png", 280, 403);
		
		livePlayerCard.setBounds(LARGEUR/2 - TWIIT_WIDTH/2 - 300 + 0*(300+2*10+TWIIT_WIDTH), 650 - 403, 280, 403);	
		liveAdvCard.setBounds(LARGEUR/2 - TWIIT_WIDTH/2 - 300 + 1*(300+2*10+TWIIT_WIDTH), 650 - 403, 280, 403);
		
		content.add(livePlayerCard);	
		content.add(liveAdvCard);
		
	}
	
	public static void UpdateTwiits(){
		
		for (int i = 0;i<twiit_list.size();i++){
			twiit_list.get(i).setBounds(0,0,0,0);
			twiit_list.get(i).repaint();
			content.remove(twiit_list.get(i));
		}
		twiit_list.clear();
		UpdateTwiitsLine();
	}
	
	public static void UpdateContent(){
		
		for (int i = 0;i<MainClient.player.getHandCards().size();i++){
			AddCards card = new AddCards(MainClient.player.getHandCards().get(i).getPath(), CARDS_WIDTH, CARDS_HEIGHT);
			card.addMouseListener(new MouseAdapter() {

				@Override
				public void mouseEntered(MouseEvent e) {
					
					for (int a = 0;a<cards_list.size();a++){
						
						if (card.equals(cards_list.get(a))){
							
							if (pointerCard != a){
								
								pointerCard = a;
								cards_list.get(a).setBounds(LARGEUR/2 + TWIIT_WIDTH/2 - a*80 - CARDS_WIDTH,
										HAUTEUR - CARDS_HEIGHT - 100, CARDS_WIDTH, CARDS_HEIGHT);
								cards_list.get(a).repaint();
								
								apercuCard.setText("Aperçu:");
								livePlayerCard.setPath(MainClient.player.getHandCards().get(a).getPath());
								break;
//								content.removeAll();
//								cards_list.clear();
//								twiit_list.clear();
//								UpdateContent();
								
							}
							
						}	
						
					}
					
				}
				
				@Override
				public void mouseExited(MouseEvent e) {
					
					if (pointerCard!=-1){
						cards_list.get(pointerCard).setBounds(LARGEUR/2 + TWIIT_WIDTH/2 - pointerCard*80 - CARDS_WIDTH,
								HAUTEUR - CARDS_HEIGHT - 30, CARDS_WIDTH, CARDS_HEIGHT);
						cards_list.get(pointerCard).repaint();
						pointerCard = -1;
						
						apercuCard.setText("Dernière carte jouée:");
						livePlayerCard.setPath(lastPlayerCard.getPath());
//						cards_list.clear();
//						twiit_list.clear();
//						content.removeAll();
//						UpdateContent();
					}
					
				}
				
				@Override
				public void mousePressed(MouseEvent e) {
					Thread t = new Thread(new PlaySound("click.wav"));
					t.start();
					if (MainClient.canPlay){
						
						for (int a = 0;a<cards_list.size();a++){
							
							if (card.equals(cards_list.get(a))){
								
								lastPlayerCard = MainClient.player.getHandCards().get(a);	
								
								if (lastPlayerCard.getInfos().getFollowers()<=playerFollowers){
									
									
									
									playerFollowers=(int) (playerFollowers-lastPlayerCard.getInfos().getFollowers()/2);
									
									pointerCard = -1;
									
									cards_list.get(a).setBounds(0, 0, 0, 0);												
									
									MainClient.access.send("pose "+MainClient.player.getHandCards().get(a).getId()+" "+playerFollowers);
									
									if (lastPlayerCard.getType().equals(TypesOfCards.YOUTUBER)){						
										poseTwiit=true;
										GameFrame.logs.add("<html><font color=blue>"+MainClient.player.getName()+"</font> pose"
                            					+ " <font color=black>"+GameFrame.lastPlayerCard.getName()+"</font></html>");
										twiitListCard.add(lastPlayerCard);
										twiitListPlayer.add(MainClient.player);
										
									}
									
									MainClient.player.getHandCards().remove(a);
									
									cards_list.get(a).repaint();
									cards_list.remove(a);
									
									UpdateTwiits();
									//content.removeAll();
									
									//UpdateContent();
									
								}else if( (lastPlayerCard.getInfos().getFollowers() / 2)<=playerFollowers && lastPlayerCard.getType().equals(TypesOfCards.SPECIALE)){
									
									MainClient.access.send("pose "+MainClient.player.getHandCards().get(a).getId()+" "+playerFollowers);
									
									if (lastPlayerCard.getInfos().getId_power()==2){
										specialMod=2;
									}
									
									MainClient.player.getHandCards().remove(a);
									
									content.removeAll();
									UpdateContent();
								}
								
							}		
							
						}
						
					}
			
					
				}

            });
			if (pointerCard>=0){
				if (i==pointerCard){
					card.setBounds(LARGEUR/2 + TWIIT_WIDTH/2 - i*80 - CARDS_WIDTH, HAUTEUR - CARDS_HEIGHT - 100, CARDS_WIDTH, CARDS_HEIGHT);
				}else{
					card.setBounds(LARGEUR/2 + TWIIT_WIDTH/2 - i*80 - CARDS_WIDTH, HAUTEUR - CARDS_HEIGHT - 30, CARDS_WIDTH, CARDS_HEIGHT);
				}
				
			}else{
				card.setBounds(LARGEUR/2 + TWIIT_WIDTH/2 - i*80 - CARDS_WIDTH, HAUTEUR - CARDS_HEIGHT - 30, CARDS_WIDTH, CARDS_HEIGHT);
			}
			
			content.add(card);
			
			cards_list.add(card);
		}
		
		UpdateTwiitsLine();
		
//		JPanel twiit_line = new ColorPanel(Color.WHITE, true, TWIIT_WIDTH, TWIIT_HEIGHT*5);
//		twiit_line.setBounds(LARGEUR/2 - TWIIT_WIDTH/2, 10, TWIIT_WIDTH, TWIIT_HEIGHT*5);
//		
//		content.add(twiit_line);
		
		for (int i = 0;i<1;i++){
			
			ColorPanel tourButton = new ColorPanel(Color.WHITE, true, 100, 50);
			tourButton.setBounds(LARGEUR/2 - TWIIT_WIDTH/2 + i*(TWIIT_WIDTH)-i*100, TWIIT_HEIGHT*5+20, 100, 50);
			
			if (i == 0){
				tourButton.setColor(Color.decode("#EFFEF4"));
				if (MainClient.canPlay){
					tourButton.setColor(Color.decode("#74FD9D"));
				}
				JLabel msg_l1 = new JLabel();
				msg_l1.setFont(new Font("Tahoma", Font.PLAIN, 16));
				msg_l1.setText("Fin du tour");
				msg_l1.setForeground(Color.DARK_GRAY);
				msg_l1.setHorizontalAlignment(JLabel.CENTER);
				msg_l1.setBounds(0, 40, 100, 20);
				tourButton.add(msg_l1);
				tourButton.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseEntered(MouseEvent e){
						if (MainClient.canPlay){
							tourButton.setColor(Color.decode("#B7FE4C"));
						}
					}
					@Override
					public void mouseExited(MouseEvent e){
						if (MainClient.canPlay){
							tourButton.setColor(Color.decode("#74FD9D"));
						}
					}
					@Override
					public void mousePressed(MouseEvent e){
						if (MainClient.canPlay){
							MainClient.canPlay=false;
							MainClient.access.send("toursuivant");
							advFollowers+=100;
							tourButton.setColor(Color.decode("#FE4C4C"));
							msg_l1.setText("Tour adverse");
//							PrepareUpdate();
						}
					}
				});
			}
			
			content.add(tourButton);
			
		}
		
		UpdateDisplayLogs();
		
		/*
		 * Players
		 */
		
		InitializeBackcards();
		
		for (int i = 0;i<2;i++){
			
			Player p = MainClient.player; //Initialize
			int followers = 0;
			int views = 0;
			JLabel nbViewslab = new JLabel();
			String apercu = "Dernière carte jouée:";
			
			if (i==0){
				p=MainClient.player;
				followers=playerFollowers;
				views=playerViews;
				nbViewslab=nbViews1;
				if (pointerCard>=0 || pointerTwiit>=0){
					apercu= "Aperçu:";
				}
			}else{
				p=Adversaire;
				followers=advFollowers;
				views=advViews;
				nbViewslab=nbViews2;
			}	
		
			JPanel players_content = new ColorPanel(Color.WHITE, true, 300, 650);
			players_content.setLayout(null);
			players_content.setBounds(LARGEUR/2 - TWIIT_WIDTH/2 - 10 - 300 + i*(300+2*10+TWIIT_WIDTH), 10, 300, 650);
			
			JPanel playerProfil = new ColorPanel(Color.decode(p.getColor()), false, 65, 65);
			playerProfil.setLayout(new BorderLayout());
			playerProfil.setBounds(10, 10, 65, 65);
			
			JLabel letter = new JLabel();
			letter.setFont(new Font("Tahoma", Font.PLAIN, 50));
			letter.setText((p.getName().toCharArray()[0]+"").toUpperCase());
			letter.setForeground(Color.WHITE);
			letter.setHorizontalAlignment(JLabel.CENTER);
			letter.setPreferredSize(new Dimension(65, 65));
			
			playerProfil.add(letter, BorderLayout.CENTER);
			
			JLabel name = new JLabel();
			name.setFont(new Font("Arial", Font.BOLD, 18));
			name.setText(p.getName());
			name.setForeground(Color.DARK_GRAY);
			name.setHorizontalAlignment(JLabel.LEFT);
			name.setBounds(10+65+5, 10, 300, 22);
			
			JLabel follow = new JLabel();
			follow.setFont(new Font("Tahoma", Font.PLAIN, 13));
			follow.setText("FOLLOWERS");
			follow.setForeground(Color.GRAY);
			follow.setHorizontalAlignment(JLabel.LEFT);
			follow.setBounds(10+65+5, 10+20+8, 300, 15);
			JLabel nbFollow = new JLabel();
			nbFollow.setFont(new Font("Tahoma", Font.PLAIN, 28));
			nbFollow.setText(followers+"");
			nbFollow.setForeground(Color.decode("#575757"));
			nbFollow.setHorizontalAlignment(JLabel.LEFT);
			nbFollow.setBounds(10+65+5, 30+17, 300, 34);
			
			final AddImages minia = new AddImages("images/miniatures/"+MainClient.THEMES_IMAGES_PATH[p.getThemeId()-1], 165, 95);
			minia.setBounds(10, 30+17+28+25, 165, 95);
			
			minia.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseEntered(MouseEvent e){
					minia.redefine(165-20, 95-20);
					minia.setBounds(20, 30+17+28+25+10, 165-20, 95-20);
				}
				@Override
				public void mouseExited(MouseEvent e){
					minia.redefine(165, 95);
					minia.setBounds(10, 30+17+28+25, 165, 95);
				}
				@Override
				public void mousePressed(MouseEvent e){
					if (twiitSelect!=null){
						int views = twiitSelect.getInfos().getRts()*1000*2;
						Thread th = new Thread(new AnimationViewsThread(1, views));
						th.start();
						
						MainClient.access.send("views "+views);
						
						minia.redefine(165, 95);
						minia.setBounds(10, 30+17+28+25, 165, 95);
						
						alreadyPlay.add(twiitSelect);
						twiitSelect=null;
						
//						PrepareUpdate();
					}
				}
			});
			
			JPanel viewsImg = new AddImages("images/miniatures/views.png", 25, 25);
			viewsImg.setBounds(165+5+10, 30+17+28+25, 25, 25);
			
			nbViewslab.setFont(new Font("Tahoma", Font.PLAIN, 21));
			nbViewslab.setText(views+"");
			nbViewslab.setForeground(Color.decode("#A8A8A7"));
			nbViewslab.setHorizontalAlignment(JLabel.LEFT);
			nbViewslab.setBounds(165+5+10+25, 30+17+28+25, 300, 25);
			
			
			if (i==0){
				apercuCard = new JLabel();
				apercuCard.setFont(new Font("Tahoma", Font.BOLD, 19));
				apercuCard.setText(apercu);
				apercuCard.setForeground(Color.DARK_GRAY);
				apercuCard.setHorizontalAlignment(JLabel.LEFT);
				apercuCard.setBounds(10, 650-403-16-20, 300, 24);
				players_content.add(apercuCard);
			}else{
				JLabel apercuCard = new JLabel();
				apercuCard.setFont(new Font("Tahoma", Font.BOLD, 19));
				apercuCard.setText(apercu);
				apercuCard.setForeground(Color.DARK_GRAY);
				apercuCard.setHorizontalAlignment(JLabel.LEFT);
				apercuCard.setBounds(10, 650-403-16-20, 300, 24);
				players_content.add(apercuCard);
			}
			
			
			players_content.add(playerProfil);
			players_content.add(name);
			players_content.add(follow);
			players_content.add(nbFollow);
			players_content.add(minia);
			players_content.add(viewsImg);
			players_content.add(nbViewslab);	
			
			
			content.add(players_content);		
			
		}
		
		content.updateUI();
		
	}
	
	public static void PlaySound(String path) throws IOException{
			String gongFile = "sounds/"+path;
		    InputStream in = new FileInputStream(gongFile);
		 
		    // create an audiostream from the inputstream
		    AudioStream audioStream = new AudioStream(in);
		 
		    // play the audio clip with the audioplayer class
		    AudioPlayer.player.start(audioStream);
	}
	
	/*
	 * InputStream inputStream = getClass().getResourceAsStream(SOUND_FILENAME); <------ GET IN SAME DIRECTORY
	 */
	
	static class AddImages extends JPanel {

		private String path;
		private int x;
		private int y;

		public AddImages(String path, int x, int y) {
			this.path = path;
			this.x = x;
			this.y = y;
		}

		public void paintComponent(Graphics g) {
			try {
				Graphics2D g2d = (Graphics2D) g;
				BufferedImage img = ImageIO.read(new File(path));
				// -?
				g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
				g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
				g2d.drawImage(img, 0, 0, x, y, this);
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		
		public void redefine(int x, int y){
			this.x=x;
			this.y=y;
			repaint();
		}

	}
	
	static class AddCards extends JPanel {

		private String path;
		private int x;
		private int y;

		public AddCards(String path, int x, int y) {
			this.path = path;
			this.x = x;
			this.y = y;
		}

		public void paintComponent(Graphics g) {
			try {
				Graphics2D g2d = (Graphics2D) g;
				BufferedImage img = ImageIO.read(new File(path));
				// -?
				g2d.drawImage(img, 0, 0, x, y, this);
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		
		public String getPath(){
			return path;
		}
		
		public void setPath(String s){
			this.path=s;
			repaint();
		}

	}
	
	public static class PlaySound implements Runnable{
		
		private String path;
		
		public PlaySound(String path){
			this.path=path;
		}

		@Override
		public void run() {
			try {
				PlaySound(path);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
	}
	
	public static class AnimationViewsThread implements Runnable{
		
		private int nbPl;
		private int addViews;
		
		public AnimationViewsThread(int nbPl, int addViews){
			this.nbPl=nbPl;
			this.addViews=addViews;
		}

		@Override
		public void run() {
				AnimViews(nbPl, addViews);
		}
		
	}
	
	public static class AnimationAddTwiit implements Runnable{
		
		private ColorPanel cp;
		
		public AnimationAddTwiit(ColorPanel cp){
			this.cp=cp;
		}

		@Override
		public void run() {
				AnimAddTwiit(cp);
		}
		
	}
	
	public static class AnimationAddTwiit2 implements Runnable{
		
		private ColorPanel cp;
		
		public AnimationAddTwiit2(ColorPanel cp){
			this.cp=cp;
		}

		@Override
		public void run() {
				AnimAddTwiit2(cp);
		}
		
	}
}
