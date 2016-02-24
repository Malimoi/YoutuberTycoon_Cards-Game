package com.malimoi.cards;

import com.malimoi.cards.enums.Grades;
import com.malimoi.cards.enums.TypesOfThemes;

public class InfosSpeciale extends Infos{
	
	private int followers;
	private Grades grade;
	private int id_power;
	
	public InfosSpeciale(int followers, Grades grade, int id_power) {
		// TODO Auto-generated constructor stub
		this.followers=followers;
		this.grade=grade;
		this.id_power=id_power;
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
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getHearts() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public TypesOfThemes getTheme() {
		// TODO Auto-generated method stub
		return TypesOfThemes.RIEN;
	}

	@Override
	public Grades getGrade() {
		// TODO Auto-generated method stub
		return grade;
	}

}
