package com.chrisphil.vocabularytrainer.app.model;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

public class FileHandler {

	public static void writeFile(Data data){
		GsonBuilder builder = new GsonBuilder();
		builder.setPrettyPrinting();
		Gson gson = builder.create();
		
		String file = gson.toJson(data);
		
		try (PrintWriter writer = new PrintWriter("data.json")){
			writer.print(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public static Data readFile(){
		GsonBuilder builder = new GsonBuilder();
		builder.setPrettyPrinting();
		Gson gson = builder.create();
		Data data = null;
		String content = "";
		try {
			content = new String(Files.readAllBytes(Paths.get("data.json")), "UTF-8");
		} catch (IOException e1) {
			return null;
		}
		try {
			data = gson.fromJson(content, Data.class);
			//Setze alle static ID's
			int lessonSID = 0;
			int vocableSID = 0;
			int exerciseSID = 0;
			for(int i = 0; i < data.lessons.size();i++){
				if(lessonSID < data.lessons.get(i).id){
					lessonSID = data.lessons.get(i).id;
				}
			}
			for(int i = 0; i < data.vocabularys.size();i++){
				if(vocableSID < data.vocabularys.get(i).id){
					vocableSID = data.vocabularys.get(i).id;
				}
			}
			for(int i = 0; i < data.exercises.size();i++){
				if(exerciseSID < data.exercises.get(i).id){
					exerciseSID = data.exercises.get(i).id;
				}
			}
			Lesson.sid = lessonSID;
			Vocable.sid = vocableSID;
			Exercise.sid = exerciseSID;
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
		}
		return data;
	}
}
