package com.malimoi.cards;

import com.malimoi.cards.enums.TypesOfCards;

public class Card {
	
	private String name;
	private TypesOfCards type;
	private Infos infos;
	private String path;
	private int id;
	
	public Card(String name, TypesOfCards type, Infos infos, String path, int id){
		
		this.name=name;
		this.type=type;
		this.infos=infos;
		this.path=path;
		this.id=id;
		
	}
	
	public String getName() {
		return name;
	}
	public TypesOfCards getType() {
		return type;
	}
	public Infos getInfos() {
		return infos;
	}
	public String getPath() {
		return path;
	}
	public int getId() {
		return id;
	}
	
	public String toString(){
		return name+" - "+type+" - "+infos.getFollowers()+" - "+infos.getHearts()+" - "+infos.getRts()+" - "+infos.getId_power()+" - "+
				id;
	}

}
