package com.malimoi.Main;

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
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

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
	
	// -CONTENT
	public static JPanel content = new JPanel();
	
	// -
	public static List<JPanel> cards_list = new ArrayList<JPanel>();
	public static List<JPanel> twiit_list = new ArrayList<JPanel>();
	
	public static int playerCards = 0;
	public static int advCards = 0;
	
	public GameFrame(){
		Boolean size = false;
		
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
		
		//content.remove(cards_list.get(2));
		
		//content.updateUI();
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setContentPane(content);
		this.setSize(LARGEUR, HAUTEUR);
		this.setVisible(true);
	}
	
	public void UpdateContent(){
		
		for (int i = 0;i<5;i++){
			Random r = new Random();
			String img_path = MainClient.cards_list.get(r.nextInt(29)).getPath();
			System.out.println(img_path);
			JPanel card = new AddCards(img_path, CARDS_WIDTH, CARDS_HEIGHT);
			card.addMouseListener(new MouseAdapter() {

				@Override
				public void mouseEntered(MouseEvent e) {
					card.setBounds(card.getX(), HAUTEUR - CARDS_HEIGHT - 100, CARDS_WIDTH, CARDS_HEIGHT);
					
					content.updateUI();
				}
				
				@Override
				public void mouseExited(MouseEvent e) {
					card.setBounds(card.getX(), HAUTEUR-CARDS_HEIGHT-30, CARDS_WIDTH, CARDS_HEIGHT);
					
					content.updateUI();
				}
				
				@Override
				public void mousePressed(MouseEvent e) {
					for (int a = 0;a<cards_list.size();a++){
						if (card.equals(cards_list.get(a))){
							cards_list.remove(a);
							content.removeAll();
						}					
					}
					UpdateContent();
				}

            });
			card.setBounds(LARGEUR/2 + TWIIT_WIDTH/2 - i*80 - CARDS_WIDTH, HAUTEUR - CARDS_HEIGHT - 30, CARDS_WIDTH, CARDS_HEIGHT);
			
			content.add(card);
			
			cards_list.add(card);
		}
		
		for (int i = 0;i<2;i++){		
			JPanel t = new COLOR(Color.WHITE, true, TWIIT_WIDTH, TWIIT_HEIGHT);	
			t.setLayout(null);
			t.setBounds(LARGEUR/2 - TWIIT_WIDTH/2, 10 + i*(TWIIT_HEIGHT-1), TWIIT_WIDTH, TWIIT_HEIGHT);
			
			JPanel profil = new COLOR(Color.BLUE, false, 0, 0);
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
			name.setText("The Malimoi");
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
			
			content.add(t);
		}	
		
		JPanel twiit_line = new COLOR(Color.WHITE, true, TWIIT_WIDTH, TWIIT_HEIGHT*5);
		twiit_line.setBounds(LARGEUR/2 - TWIIT_WIDTH/2, 10, TWIIT_WIDTH, TWIIT_HEIGHT*5);
		
		content.add(twiit_line);
		
		/*
		 * Players
		 */
		for (int i = 0;i<2;i++){
		
			JPanel players_content = new COLOR(Color.WHITE, true, 300, 650);
			players_content.setBounds(LARGEUR/2 - TWIIT_WIDTH/2 - 10 - 300 + i*(300+2*10+TWIIT_WIDTH), 10, 300, 650);
			
			content.add(players_content);
			
		}
		
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

	}
	
}
