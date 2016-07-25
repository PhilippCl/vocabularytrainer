package com.chrisphil.vocabularytrainer.app.model;

import java.util.ArrayList;
import java.util.List;

public class Lesson {
	List<Integer> vocs = new ArrayList<Integer>();
	public String topic;
	public String lang;
	public int id;
	public static int sid;
	
	public Lesson(String topic,String lang){
		sid++;
		id = sid;
		this.topic = topic;
		this.lang = lang;
	}
	
	public boolean addVocable(Vocable v){
		for(Integer currentV : vocs){
			if(currentV == v.id)
				return false;
		}
		vocs.add(v.id);
		return true;
	}
	
	public boolean removeVocable(int id){
		for(Integer currentV : vocs){
			if(currentV == id){
				vocs.remove(vocs.indexOf(currentV));
				return true;
			}
		}
		return false;
	}
}
