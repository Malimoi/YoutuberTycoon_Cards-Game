package com.malimoi.cards;

import com.malimoi.cards.enums.Grades;
import com.malimoi.cards.enums.TypesOfThemes;

public class InfosYoutuber extends Infos {
	
	private int followers;
	private int rts;
	private int hearts;
	private TypesOfThemes theme;
	private Grades grade;
	private int id_power;
	
	public InfosYoutuber(int followers, int rts, int hearts, TypesOfThemes theme, Grades grade, int id_power) {
		// TODO Auto-generated constructor stub
		this.followers=followers;
		this.rts=rts;
		this.hearts=hearts;
		this.theme=theme;
		this.grade=grade;
		this.id_power=id_power;
	}
	
	@Override
	public void setFollowers(int followers) {
		this.followers = followers;
	}
	
	@Override
	public void setRts(int rts) {
		this.rts = rts;
	}
	
	@Override
	public void setHearts(int hearts) {
		this.hearts = hearts;
	}
	
	@Override
	public void setTheme(TypesOfThemes theme) {
		this.theme = theme;
	}
	
	@Override
	public void setGrade(Grades grade) {
		this.grade = grade;
	}
	
	@Override
	public void setId_power(int id_power) {
		this.id_power = id_power;
	}

	@Override
	public int getFollowers() {
		// TODO Auto-generated method stub
		return followers;
	}
	@Override
	public int getId_power() {
		// TODO Auto-generated method stub
		return id_power;
	}
	@Override
	public int getRts() {
		return rts;
	}
	@Override
	public int getHearts() {
		return hearts;
	}

	@Override
	public TypesOfThemes getTheme() {
		// TODO Auto-generated method stub
		return theme;
	}

	@Override
	public Grades getGrade() {
		// TODO Auto-generated method stub
		return grade;
	}

}

