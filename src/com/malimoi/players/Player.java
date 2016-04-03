package com.malimoi.players;

import java.awt.Color;
import java.util.List;

import com.malimoi.Main.MainClient;
import com.malimoi.cards.Card;

public class Player {
	
	private String name;
	private int color;
	private int rang;
	private int themeId;
	private List<Card> cards;
	private List<Card> handCards;
	
	public Player(String name, int color, int rang, int themeId, List<Card> cards, List<Card> handCards){
		this.name=name;
		this.color=color;
		this.rang=rang;
		this.themeId=themeId;
		this.cards=cards;
		this.handCards=handCards;
	}

	public List<Card> getHandCards() {
		return handCards;
	}

	public void setHandCards(List<Card> handCards) {
		this.handCards = handCards;
	}

	public List<Card> getCards() {
		return cards;
	}

	public void setCards(List<Card> cards) {
		this.cards = cards;
	}

	public int getThemeId() {
		return themeId;
	}

	public void setThemeId(int themeId) {
		this.themeId = themeId;
		// -Send to server
		MainClient.access.send("themeid "+themeId);
	}

	public int getColor() {
		return color;
	}

	public void setColor(int color) {
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

	public void setName(String name) {
		this.name = name;
	}
	
}
