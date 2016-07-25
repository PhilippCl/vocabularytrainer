package com.chrisphil.vocabularytrainer.app.controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.chrisphil.vocabularytrainer.app.model.Data;
import com.chrisphil.vocabularytrainer.app.model.FileHandler;
import com.chrisphil.vocabularytrainer.app.model.Lesson;
import com.chrisphil.vocabularytrainer.app.model.Vocable;
import com.chrisphil.vocabularytrainer.app.util.ControlledScreen;
import com.chrisphil.vocabularytrainer.app.util.ScreensController;
import com.chrisphil.vocabularytrainer.app.util.Values;
import com.jfoenix.controls.JFXButton;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.text.Font;

public class ExercisesMainMenu implements Initializable,ControlledScreen{

	ScreensController myController;

	@FXML
	JFXButton allVocButton;
	@FXML
	JFXButton diffLessButton;
	@FXML
	JFXButton langButton;

	@Override
	public void setScreenParent(ScreensController screenPage) {
		this.myController = screenPage;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		assert allVocButton != null : "fx:id \"allVocButton\" was not injected! 'ExercisesMainMenuView.fxml'";
		assert diffLessButton != null : "fx:id \"diffLessButton\" was not injected! 'ExercisesMainMenuView.fxml'";
		assert langButton != null : "fx:id \"langButton\" was not injected! 'ExercisesMainMenuView.fxml'";

		this.allVocButton.setFont(new Font(30));
		this.diffLessButton.setFont(new Font(30));
		this.langButton.setFont(new Font(30));

		this.allVocButton.setStyle(Values.buttonStyle);
		this.diffLessButton.setStyle(Values.buttonStyle);
		this.langButton.setStyle(Values.buttonStyle);

		Data data = FileHandler.readFile();

		this.allVocButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				//Load Vocables check if Data == null
				if(data != null){
					Vocable[] vocs = new Vocable[data.vocabularys.size()];
					vocs = data.vocabularys.toArray(vocs);
					if(vocs.length > 0){
						//Es sind genug Vokabeln für eine Abfrage vorhanden
						myController.setScreen(TitleBar.exercisesAllVocMenuID);
					}else{
						Alert alert = new Alert(AlertType.INFORMATION);
						alert.setTitle("Keine Vokabeln");
						alert.setHeaderText("Es wurden noch nicht genug Vokabeln hinzgefuegt.");
						alert.showAndWait();
						myController.setScreen(TitleBar.exercisesMainMenuID);
					}
				}
			}
		});
		
		this.diffLessButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				//Load Vocables check if Data == null
				if(data != null){
					Lesson[] lessons = new Lesson[data.lessons.size()];
					lessons = data.lessons.toArray(lessons);
					if(lessons.length > 0){
						//Es sind genug Vokabeln für eine Abfrage vorhanden
						myController.setScreen(TitleBar.excerciseLessonMenuID);
					}else{
						Alert alert = new Alert(AlertType.INFORMATION);
						alert.setTitle("Keine Lektionen");
						alert.setHeaderText("Es wurden noch nicht genug Lektionen hinzgefuegt.");
						alert.showAndWait();
						myController.setScreen(TitleBar.exercisesMainMenuID);
					}
				}
			}
		});
		
		this.langButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				//TODO: Check if enough Languages exist
				myController.setScreen(TitleBar.excerciseLangMenuID);
			}
		});

	}
}
