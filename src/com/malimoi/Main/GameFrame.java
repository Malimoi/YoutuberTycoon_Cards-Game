package com.malimoi.Main;

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
import java.io.IOException;
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
	
	public static Card lastPlayerCard = MainClient.cards_list.get(0);
	
	// -CONTENT
	public static JPanel content = new JPanel();
	public static JPanel livePlayerCard = new AddCards("images/cards/DOS_DE_CARTE.png", 280, 403);
	public static JPanel liveAdvCard = new AddCards("images/cards/DOS_DE_CARTE.png", 280, 403);
	
	// -
	public Player adversaire;
	public List<Player> listPlayers = new ArrayList<Player>();
	
	public static List<JPanel> cards_list = new ArrayList<JPanel>();
	public static List<JPanel> twiit_list = new ArrayList<JPanel>();
	public static List<Player> twiitListPlayer = new ArrayList<Player>();
	public static List<Card> twiitListCard = new ArrayList<Card>();
	
	public static int nbPlayerCards = 0;
	public static int nbAdvCards = 0;
	public static List<Card> playerCardsHand = new ArrayList<Card>();
	public static int pointerCard = -1;
	
	public static int playerFollowers = 100;
	public static int advFollowers = 100;
	public static int playerViews = 0;
	public static int advViews = 0;
	
	public GameFrame(Player adversaire){
		this.adversaire = adversaire;
		this.listPlayers.add(MainClient.player);
		this.listPlayers.add(adversaire);
		
		Boolean size = true;
		
		if (!size){
			HAUTEUR=720;
			LARGEUR=1200;
		}
		
		content.setLayout(null);
		content.setBackground(Color.decode("#F1F9FE"));
		content.setPreferredSize(new Dimension(LARGEUR, HAUTEUR));	
		
		InitializeContent();
		
		/*
		 * UPDATE
		 */
		
		UpdateContent();
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setContentPane(content);
		this.setSize(LARGEUR, HAUTEUR);
		this.setVisible(true);
	}
	
	public void InitializeContent(){
		
		for (int i = 0;i<5;i++){
			Random r = new Random();
			Card card = MainClient.playerCards.get(r.nextInt(29)+1);
			playerCardsHand.add(card);
			System.out.println(card.getName());
		}
		
		
	}
	
	public void InitializeBackcards(){
		
		if (pointerCard>=0){
			livePlayerCard = new AddCards(playerCardsHand.get(pointerCard).getPath(), 280, 403);
		}else{
			livePlayerCard = new AddCards(lastPlayerCard.getPath(), 280, 403);
		}		
		liveAdvCard = new AddCards("images/cards/DOS_DE_CARTE.png", 280, 403);
		
		livePlayerCard.setBounds(LARGEUR/2 - TWIIT_WIDTH/2 - 300 + 0*(300+2*10+TWIIT_WIDTH), 650 - 403, 280, 403);	
		liveAdvCard.setBounds(LARGEUR/2 - TWIIT_WIDTH/2 - 300 + 1*(300+2*10+TWIIT_WIDTH), 650 - 403, 280, 403);
		
		content.add(livePlayerCard);	
		content.add(liveAdvCard);
		
	}
	
	public void UpdateContent(){
		
		for (int i = 0;i<playerCardsHand.size();i++){
			JPanel card = new AddCards(playerCardsHand.get(i).getPath(), CARDS_WIDTH, CARDS_HEIGHT);
			card.addMouseListener(new MouseAdapter() {

				@Override
				public void mouseEntered(MouseEvent e) {
					
					for (int a = 0;a<cards_list.size();a++){
						
						if (card.equals(cards_list.get(a))){
							
							if (pointerCard != a){
								
								pointerCard = a;
								System.out.println("pointerCard = "+a);
								content.removeAll();
								cards_list.clear();
								UpdateContent();
								
							}
							
						}	
						
					}
					
				}
				
				@Override
				public void mouseExited(MouseEvent e) {

					pointerCard = -1;
					
					cards_list.clear();
					content.removeAll();
					UpdateContent();
				}
				
				@Override
				public void mousePressed(MouseEvent e) {
					for (int a = 0;a<cards_list.size();a++){
						if (card.equals(cards_list.get(a))){
							pointerCard = -1;
							
							cards_list.clear();
							lastPlayerCard = playerCardsHand.get(a);
							if (lastPlayerCard.getType().equals(TypesOfCards.YOUTUBER)){
								twiitListCard.add(lastPlayerCard);
								twiitListPlayer.add(MainClient.player);
							}							
							playerCardsHand.remove(a);
							content.removeAll();
						}					
					}
					UpdateContent();
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
		
		/*
		 * Fake twiits
		 */
		
		for (int i = 0;i<twiitListCard.size();i++){		
			JPanel t = new COLOR(Color.WHITE, true, TWIIT_WIDTH, TWIIT_HEIGHT);	
			t.setLayout(null);
			t.setBounds(LARGEUR/2 - TWIIT_WIDTH/2, 10 + i*(TWIIT_HEIGHT-1), TWIIT_WIDTH, TWIIT_HEIGHT);
			
			JPanel profil = new AddImages("images/profils/"+twiitListCard.get(i).getName()+".png", 50, 50);
			profil.setBounds(10, 10, 50, 50);
			t.add(profil);
			JPanel rt = new AddImages("images/RT_GRAY.png", 20, 20);
			rt.setBounds(TWIIT_WIDTH/5, TWIIT_HEIGHT-25, 20, 20);
			t.add(rt);
			JPanel heart = new AddImages("images/HEART_GRAY.png", 20, 20);
			heart.setBounds(TWIIT_WIDTH/5+TWIIT_WIDTH/7, TWIIT_HEIGHT-25, 20, 20);
			t.add(heart);
			JLabel name = new JLabel();
			name.setFont(new Font("Arial", Font.BOLD, 15));
			name.setText(twiitListCard.get(i).getName());
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
			
			content.add(t);
		}	
		
		JPanel twiit_line = new COLOR(Color.WHITE, true, TWIIT_WIDTH, TWIIT_HEIGHT*5);
		twiit_line.setBounds(LARGEUR/2 - TWIIT_WIDTH/2, 10, TWIIT_WIDTH, TWIIT_HEIGHT*5);
		
		content.add(twiit_line);
		
		/*
		 * Players
		 */
		
		InitializeBackcards();
		
		for (int i = 0;i<2;i++){
			
			Player p = MainClient.player; //Initialize
			int followers = 0;
			int views = 0;
			String apercu = "Dernière carte joué:";
			
			if (i==0){
				p=MainClient.player;
				followers=playerFollowers;
				views=playerViews;
				if (pointerCard>=0){
					apercu= "Aperçu:";
				}
			}else{
				p=adversaire;
				followers=advFollowers;
				views=advViews;
			}	
		
			JPanel players_content = new COLOR(Color.WHITE, true, 300, 650);
			players_content.setLayout(null);
			players_content.setBounds(LARGEUR/2 - TWIIT_WIDTH/2 - 10 - 300 + i*(300+2*10+TWIIT_WIDTH), 10, 300, 650);
			
			JPanel playerProfil = new COLOR(Color.decode(p.getColor()), false, 65, 65);
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
			
			JPanel minia = new AddImages("images/miniatures/"+MainClient.THEMES_IMAGES_PATH[p.getThemeId()-1], 165, 95);
			minia.setBounds(10, 30+17+28+25, 165, 95);
			JPanel viewsImg = new AddImages("images/miniatures/views.png", 25, 25);
			viewsImg.setBounds(165+5+10, 30+17+28+25, 25, 25);
			JLabel nbViews = new JLabel();
			nbViews.setFont(new Font("Tahoma", Font.PLAIN, 21));
			nbViews.setText(views+"");
			nbViews.setForeground(Color.decode("#A8A8A7"));
			nbViews.setHorizontalAlignment(JLabel.LEFT);
			nbViews.setBounds(165+5+10+25, 30+17+28+25, 300, 25);
			
			JLabel apercuCard = new JLabel();
			apercuCard.setFont(new Font("Tahoma", Font.BOLD, 19));
			apercuCard.setText(apercu);
			apercuCard.setForeground(Color.DARK_GRAY);
			apercuCard.setHorizontalAlignment(JLabel.LEFT);
			apercuCard.setBounds(10, 650-403-16-20, 300, 24);
			
			players_content.add(playerProfil);
			players_content.add(name);
			players_content.add(follow);
			players_content.add(nbFollow);
			players_content.add(minia);
			players_content.add(viewsImg);
			players_content.add(nbViews);	
			players_content.add(apercuCard);
			
			content.add(players_content);		
			
		}
		
		content.updateUI();
		
	}
	
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

	}
	
}
