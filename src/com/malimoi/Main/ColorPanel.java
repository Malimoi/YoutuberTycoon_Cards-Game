package com.malimoi.Main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class ColorPanel extends JPanel {
	private Color c;
	private Boolean border;
	private int width;
	private int height;
	public ColorPanel(Color c, Boolean border, int width, int height){
		this.c=c;
		this.border=border;
		this.width=width;
		this.height=height;
	}
	public void paintComponent(Graphics g){
		Graphics2D g2d = (Graphics2D)g;
		g2d.setColor(c);
		g2d.fillRect(0, 0, GameFrame.LARGEUR, GameFrame.HAUTEUR);	

		if (border){
			g2d.setColor(Color.decode("#E4E5E5"));
			g2d.drawLine(0, 0, GameFrame.LARGEUR, 0);
			g2d.drawLine(0, 0, 0, GameFrame.HAUTEUR);
			g2d.drawLine(0, height-1, width-1, height-1);
			g2d.drawLine(width-1, 0, width-1, height-1);
		}
	
	}
	
	public void setColor(Color color){
		this.c=color;
		repaint();
	}
}
