package com.malimoi.players;

import java.awt.Color;

public class Player {
	
	private String name;
	private String color;
	private int rang;
	
	public Player(String name, String color, int rang){
		this.name=name;
		this.color=color;
		this.rang=rang;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public int getRang() {
		return rang;
	}

	public void setRang(int rang) {
		this.rang = rang;
	}

	public String getName() {
		return name;
	}
	
	

}
