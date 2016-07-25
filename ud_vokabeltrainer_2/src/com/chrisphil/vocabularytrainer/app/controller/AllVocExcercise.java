package com.chrisphil.vocabularytrainer.app.controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.chrisphil.vocabularytrainer.app.model.Data;
import com.chrisphil.vocabularytrainer.app.model.FileHandler;
import com.chrisphil.vocabularytrainer.app.model.Vocable;
import com.chrisphil.vocabularytrainer.app.util.ControlledScreen;
import com.chrisphil.vocabularytrainer.app.util.ScreensController;
import com.chrisphil.vocabularytrainer.app.util.Values;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;

public class AllVocExcercise implements Initializable,ControlledScreen{

	ScreensController myController;

	Data data;

	@FXML
	Label allVocableLabel;
	@FXML
	Label vocableAmountLabel;
	@FXML
	BorderPane mainPane;
	@FXML
	JFXButton quitButton;
	@FXML
	JFXButton nextButton;
	@FXML
	JFXButton startButton;
	@FXML
	Label descLabel;
	
	GridPane gridPane;
	Label vocableLabel;
	JFXTextField inputField;

	int currentVocIndex = 0;
	int score = 0;
	Vocable[] vocs;
	Vocable cv;

	@Override
	public void setScreenParent(ScreensController screenPage) {
		this.myController = screenPage;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		assert allVocableLabel != null : "fx:id \"allVocableLabel\" was not injected! 'AllVocExcercise.fxml'";
		assert vocableAmountLabel != null : "fx:id \"vocableAmountLabel\" was not injected! 'AllVocExcercise.fxml'";
		assert quitButton != null : "fx:id \"quitButton\" was not injected! 'AllVocExcercise.fxml'";
		assert nextButton != null : "fx:id \"nextButton\" was not injected! 'AllVocExcercise.fxml'";
		assert startButton != null : "fx:id \"nextButton\" was not injected! 'AllVocExcercise.fxml'";
		assert descLabel != null : "fx:id \"descLabel\" was not injected! 'AllVocExcercise.fxml'";
		assert mainPane != null : "fx:id \"mainPane\" was not injected! 'AllVocExcercise.fxml'";


		data = FileHandler.readFile();

		quitButton.setFont(new Font(20));
		nextButton.setFont(new Font(20));
		startButton.setFont(new Font(20));
		allVocableLabel.setFont(new Font(20));
		vocableAmountLabel.setFont(new Font(20));


		quitButton.setStyle(Values.buttonStyle);
		nextButton.setStyle(Values.buttonStyle);
		startButton.setStyle(Values.buttonStyle);
		
		descLabel.setFont(new Font(30));
		descLabel.setText("Klicke auf starten, um die Abfrage zu starten");
		
		allVocableLabel.setVisible(false);
		nextButton.setVisible(false);
		
		gridPane = new GridPane();
		gridPane.setAlignment(Pos.CENTER);
		
		vocableLabel = new Label("Test");
		vocableLabel.setFont(new Font(30));
		vocableLabel.setAlignment(Pos.CENTER);
		vocableLabel.setPadding(new Insets(20));
		
		inputField = new JFXTextField();
		
		gridPane.add(vocableLabel, 0, 0);
		gridPane.add(inputField, 0, 1);
		
		startButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				//Load Vocables check if Data == null
				if(data != null){
					vocs = new Vocable[data.vocabularys.size()];
					vocs = data.vocabularys.toArray(vocs);
					currentVocIndex = 0;
					score = 0;
					if(vocs.length > 0){
						nextButton.setVisible(true);
						startButton.setVisible(false);
						quitButton.setVisible(true);
						
						mainPane.setCenter(gridPane);
						vocableAmountLabel.setText("Vokabel " + (currentVocIndex+1) + " von " + vocs.length);
						
						cv = vocs[currentVocIndex];
						vocableLabel.setText(cv.vlang1);
						
						currentVocIndex++;
						
						nextButton.setOnAction(new EventHandler<ActionEvent>() {
							@Override
							public void handle(ActionEvent event) {
								String input = inputField.getText();
								if(input.toLowerCase().equals(cv.vlang2.toLowerCase())){
									allVocableLabel.setStyle(Values.backgroundRightStyle);
									allVocableLabel.setText("Richtig");
									allVocableLabel.setVisible(true);
									score++;
								}else{
									allVocableLabel.setStyle(Values.backgroundWrongStyle);
									allVocableLabel.setText("Falsch. Richtige Antwort: " + cv.vlang2);
									allVocableLabel.setVisible(true);
								}
								if(currentVocIndex < vocs.length){
									cv = vocs[currentVocIndex];
									vocableLabel.setText(cv.vlang1);
									vocableAmountLabel.setText("Vokabel " + (currentVocIndex+1) + " von " + vocs.length);
									inputField.setText("");
									currentVocIndex++;
								}else{
									descLabel.setText("Training abgeschlossen. " + score + " von " + vocs.length + " Vokabeln waren richtig");
									mainPane.setCenter(descLabel);
									quitButton.setVisible(true);
									allVocableLabel.setVisible(false);
									nextButton.setVisible(false);
									startButton.setVisible(true);
								}
							}
						});
					}else{
						descLabel.setText("Es sind nicht genug Vokabeln vorhanden!");
						quitButton.setVisible(true);
						nextButton.setVisible(false);
						startButton.setVisible(false);
					}
				}
			}
		});

		quitButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				myController.setScreen(TitleBar.exercisesMainMenuID);
			}
		});

	}
}
