package com.chrisphil.vocabularytrainer.app.controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.chrisphil.vocabularytrainer.app.model.Data;
import com.chrisphil.vocabularytrainer.app.model.FileHandler;
import com.chrisphil.vocabularytrainer.app.util.ScreensController;
import com.chrisphil.vocabularytrainer.app.util.Values;
import com.jfoenix.controls.JFXButton;
import com.pepperonas.fxiconics.FxIconics;
import com.pepperonas.fxiconics.gmd.FxFontGoogleMaterial;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;

public class TitleBar implements Initializable{

	//Different Menues
	public static String mainMenuID = "mainMenu";
	public static String mainMenuFile = "view/MainMenuView.fxml";

	public static String editLessonsID = "editLessons";
	public static String editLessonsFile = "view/EditLessonsView.fxml";
	
	public static String exercisesMainMenuID = "exercisesMainMenu";
	public static String exercisesMainMenuFile = "view/ExercisesMainMenuView.fxml";
	
	public static String exercisesAllVocMenuID = "exercisesAllVocMenu";
	public static String exercisesAllVocMenuFile = "view/AllVocExcerciseView.fxml";
	
	public static String excerciseLessonMenuID = "excerciseLessonMenu";
	public static String excerciseLessonMenuFile = "view/LessonExcercise.fxml";
	
	public static String excerciseLangMenuID = "excerciseLangMenu";
	public static String excerciseLangMenuFile = "view/LangExcerciseView.fxml";
	
	public static String helpMenuID = "helpMenu";
	public static String helpMenuFile = "view/HelpView.fxml";

	@FXML
	JFXButton homeButton;

	@FXML
	JFXButton helpButton;

	@FXML
	Label titleLabel;

	@FXML
	StackPane containerPane;


	@Override
	public void initialize(URL location, ResourceBundle resources) {
		assert homeButton != null : "fx:id \"homeButton\" was not injected! 'TitlebarView.fxml'";
		assert helpButton != null : "fx:id \"helpButton\" was not injected! 'TitlebarView.fxml'";
		assert titleLabel != null : "fx:id \"titleLabel\" was not injected! 'TitlebarView.fxml'";
		assert containerPane != null : "fx:id \"containerPane\" was not injected! 'TitlebarView.fxml'";

		//After everything is injected
		this.titleLabel.setFont(new Font(40));

		this.homeButton.setFont(FxIconics.getGoogleMaterialFont(30));
		this.helpButton.setFont(FxIconics.getGoogleMaterialFont(30));

		this.homeButton.setText(FxFontGoogleMaterial.Icons.gmd_home.toString());
		this.helpButton.setText(FxFontGoogleMaterial.Icons.gmd_help.toString());

		this.homeButton.setStyle(Values.buttonStyle);
		this.helpButton.setStyle(Values.buttonStyle);
		
		//Check if data.json exists
        try{
        	Data data = FileHandler.readFile();
        	if(data == null){
        		throw new Exception("No Data File");
        	}
        }catch(Exception e){
        	FileHandler.writeFile(new Data());
        }

		//Load the different Menu's
		ScreensController con = new ScreensController();
		con.loadScreen(TitleBar.mainMenuID, TitleBar.mainMenuFile);
		con.loadScreen(TitleBar.editLessonsID, TitleBar.editLessonsFile);
		con.loadScreen(TitleBar.exercisesMainMenuID, TitleBar.exercisesMainMenuFile);
		con.loadScreen(TitleBar.exercisesAllVocMenuID, TitleBar.exercisesAllVocMenuFile);
		con.loadScreen(TitleBar.excerciseLessonMenuID, TitleBar.excerciseLessonMenuFile);
		con.loadScreen(TitleBar.excerciseLangMenuID, TitleBar.excerciseLangMenuFile);
		con.loadScreen(TitleBar.helpMenuID, TitleBar.helpMenuFile);

		con.setScreen(TitleBar.mainMenuID);
		this.containerPane.getChildren().add(con);

		this.homeButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				con.setScreen(TitleBar.mainMenuID);
			}
		});
		
		this.helpButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				con.setScreen(TitleBar.helpMenuID);
			}
		});
	}
}
