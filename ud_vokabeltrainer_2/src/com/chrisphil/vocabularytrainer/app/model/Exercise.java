package com.chrisphil.vocabularytrainer.app.model;

import java.util.ArrayList;
import java.util.List;

public class Exercise {
	List<Integer> lessons = new ArrayList<Integer>();
	public int id;
	public static int sid;
	
	public Exercise(){
		sid++;
		id = sid;
		
	}
	
	public boolean addLesson(Lesson l){
		for(Integer currentL : lessons){
			if(currentL == l.id)
				return false;
		}
		lessons.add(l.id);
		return true;
	}
	
	public boolean removeVocable(int id){
		for(Integer currentL : lessons){
			if(currentL == id){
				lessons.remove(lessons.indexOf(currentL));
				return true;
			}
		}
		return false;
	}
}
