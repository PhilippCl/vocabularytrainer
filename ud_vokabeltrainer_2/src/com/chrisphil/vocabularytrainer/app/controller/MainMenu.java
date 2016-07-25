package com.chrisphil.vocabularytrainer.app.controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.chrisphil.vocabularytrainer.app.util.ControlledScreen;
import com.chrisphil.vocabularytrainer.app.util.ScreensController;
import com.chrisphil.vocabularytrainer.app.util.Values;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Tooltip;
import javafx.scene.text.Font;

public class MainMenu implements Initializable,ControlledScreen{
	
	ScreensController myController;
	
	@FXML
	JFXButton practiseButton;
	@FXML
	JFXButton editLessonsButton;
	@FXML
	JFXTextArea authorTextArea;
	
	Tooltip practiseButtonTooltip = new Tooltip("Hier kannst du die Vokabeln trainieren.");
	Tooltip editLessonsButtonTooltip = new Tooltip("Um Lektionen zu bearbeiten/erstellen oder entfernen"
			+ ", hier klicken.");

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		assert practiseButton != null : "fx:id \"practiseButton\" was not injected! 'MainMenuView.fxml'";
		assert editLessonsButton != null : "fx:id \"editLessonsButton\" was not injected! 'MainMenuView.fxml'";
		assert authorTextArea != null : "fx:id \"authorTextArea\" was not injected! 'MainMenuView.fxml'";
		
		//After everything is injected
		this.practiseButton.setFont(new Font(30));
		this.editLessonsButton.setFont(new Font(30));
		this.authorTextArea.setFont(new Font(20));
		
		this.practiseButton.setText("Lernen");
		this.editLessonsButton.setText("Lektionen bearbeiten");
		String version = Runtime.class.getPackage().getImplementationVersion();
		this.authorTextArea.setText("Vokabeltrainer v1.0.1\n" + "Authoren: Philipp Clausing & Christopher Kluck\n" 
							+ "Java Version: "+ version);
		this.authorTextArea.setEditable(false);
		
		this.practiseButtonTooltip.setFont(new Font(15));
		this.editLessonsButtonTooltip.setFont(new Font(15));
		
		this.practiseButton.setTooltip(this.practiseButtonTooltip);
		this.editLessonsButton.setTooltip(this.editLessonsButtonTooltip);
		
		this.practiseButton.setStyle(Values.buttonStyle);
		this.editLessonsButton.setStyle(Values.buttonStyle);
		
		this.practiseButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				myController.loadScreen(TitleBar.exercisesAllVocMenuID, TitleBar.exercisesAllVocMenuFile);
				myController.loadScreen(TitleBar.excerciseLessonMenuID, TitleBar.excerciseLessonMenuFile);
				myController.loadScreen(TitleBar.excerciseLangMenuID, TitleBar.excerciseLangMenuFile);
				myController.setScreen(TitleBar.exercisesMainMenuID);
			}
		});
		
		this.editLessonsButton.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				myController.setScreen(TitleBar.editLessonsID);
			}
		});
	}

	@Override
	public void setScreenParent(ScreensController screenPage) {
		myController = screenPage;
	}

}
