package com.chrisphil.vocabularytrainer.app.model;

import java.util.ArrayList;
import java.util.List;

public class Data {
	
	public List<Vocable> vocabularys = new ArrayList<Vocable>();
	public List<Lesson> lessons = new ArrayList<Lesson>();
	public List<Exercise> exercises = new ArrayList<Exercise>();
	
	public boolean addVocable(Vocable voc){
		for(Vocable currentVoc : vocabularys){
			if(currentVoc.id == voc.id){
				return false;
			}
		}
		vocabularys.add(voc);
		return true;
	}
	
	public boolean addLesson(Lesson lesson){
		for(Lesson currentLesson : lessons){
			if(currentLesson.id == lesson.id){
				return false;
			}
		}
		lessons.add(lesson);
		return true;
	}
	
	public boolean addExercise(Exercise e){
		for(Exercise currentE : exercises){
			if(currentE.id == e.id){
				return false;
			}
		}
		exercises.add(e);
		return true;
	}
	
	public boolean removeVocable(int vocID){
		for(int i = 0; i < vocabularys.size();i++){
			if(vocabularys.get(i).id == vocID){
				vocabularys.remove(i);
				return true;
			}
		}
		return false;
	}
	
	public boolean removeLesson(int lessonID){
		for(int i = 0; i < lessons.size();i++){
			if(lessons.get(i).id == lessonID){
				lessons.remove(i);
				return true;
			}
		}
		return false;
	}
	
	public boolean removeExercise(int exerciseID){

		for(int i = 0; i < exercises.size(); i++){
			if(exercises.get(i).id == exerciseID){
				exercises.remove(i);
				return true;
			}
		}
		return false;
	}
	
	public List<Vocable> getVocablesOfLesson(Lesson l){
		ArrayList<Vocable> voc = new ArrayList<Vocable>();
		if(l == null)
			return voc;
		for(int i = 0; i < l.vocs.size();i++){
			for(Vocable v : vocabularys){
				if(v.id == l.vocs.get(i)){
					voc.add(v);
				}
			}
		}
		return voc;
	}
}
