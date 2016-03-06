package com.malimoi.cards;

import com.malimoi.cards.enums.Grades;
import com.malimoi.cards.enums.TypesOfThemes;

public abstract class Infos {

	public abstract int getFollowers();
	public abstract int getRts();
	public abstract int getHearts();
	public abstract TypesOfThemes getTheme();
	public abstract Grades getGrade();
	public abstract int getId_power();
	
	public abstract void setFollowers(int followers);
	public abstract void setRts(int rts);
	public abstract void setHearts(int hearts);
	public abstract void setTheme(TypesOfThemes theme);
	public abstract void setGrade(Grades grade);
	public abstract void setId_power(int id_power);
	
}

