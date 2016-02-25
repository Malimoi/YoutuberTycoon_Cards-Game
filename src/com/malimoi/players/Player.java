package com.malimoi.players;

import java.awt.Color;

import com.malimoi.Main.MainClient;

public class Player {
	
	private String name;
	private String color;
	private int rang;
	private int themeId;
	
	public Player(String name, String color, int rang, int themeId){
		this.name=name;
		this.color=color;
		this.rang=rang;
		this.themeId=themeId;
	}

	public int getThemeId() {
		return themeId;
	}

	public void setThemeId(int themeId) {
		this.themeId = themeId;
		// -Send to server
		MainClient.access.send("themeid "+themeId);
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
