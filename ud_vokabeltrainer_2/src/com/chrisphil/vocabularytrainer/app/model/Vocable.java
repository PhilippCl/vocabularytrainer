package com.chrisphil.vocabularytrainer.app.model;

public class Vocable {
	
	public int id;
	public static int sid;
	public String vlang1;
	public String vlang2;
	public String lang1;
	public String lang2;
	
	public Vocable(String vlang1, String vlang2, String lang1, String lang2){
		sid++;
		id = sid;
		this.vlang1 = vlang1;
		this.vlang2 = vlang2;
		this.lang1 = lang1;
		this.lang2 = lang2;
	}
	
}
