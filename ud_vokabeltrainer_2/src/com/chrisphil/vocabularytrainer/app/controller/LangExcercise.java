package com.chrisphil.vocabularytrainer.app.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.chrisphil.vocabularytrainer.app.model.Data;
import com.chrisphil.vocabularytrainer.app.model.FileHandler;
import com.chrisphil.vocabularytrainer.app.model.Lesson;
import com.chrisphil.vocabularytrainer.app.model.Vocable;
import com.chrisphil.vocabularytrainer.app.util.ControlledScreen;
import com.chrisphil.vocabularytrainer.app.util.ScreensController;
import com.chrisphil.vocabularytrainer.app.util.Values;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class LangExcercise implements Initializable,ControlledScreen{

	ScreensController myController;
	Data data;
	String selectedLang;
	List<Vocable> vocL;

	@FXML
	Label allLangLabel;
	@FXML
	Label langAmountLabel;
	@FXML
	VBox langSelectVBox;
	@FXML
	JFXListView<String> langListView;
	@FXML
	JFXButton quitButton;
	@FXML
	JFXButton startButton;
	@FXML
	JFXButton nextButton;
	@FXML
	BorderPane mainPane;

	GridPane gridPane;
	Label vocableLabel;
	JFXTextField inputField;
	List<String> langs;

	Label descLabel = new Label();
	int currentVocIndex = 0;
	int score = 0;
	Vocable[] vocs;
	Vocable cv;

	boolean ended = false;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		assert allLangLabel != null : "fx:id \"allLangLabel\" was not injected! 'LangExcerciseView.fxml'";
		assert langAmountLabel != null : "fx:id \"langAmountLabel\" was not injected! 'LangExcerciseView.fxml'";
		assert langSelectVBox != null : "fx:id \"langSelectVBox\" was not injected! 'LangExcerciseView.fxml'";
		assert langListView != null : "fx:id \"langListView\" was not injected! 'LangExcerciseView.fxml'";
		assert quitButton != null : "fx:id \"quitButton\" was not injected! 'LangExcerciseView.fxml'";
		assert startButton != null : "fx:id \"startButton\" was not injected! 'LangExcerciseView.fxml'";
		assert nextButton != null : "fx:id \"nextButton\" was not injected! 'LangExcerciseView.fxml'";
		assert mainPane != null : "fx:id \"mainPane\" was not injected! 'LangExcerciseView.fxml'";

		langs = new ArrayList<String>();

		Font font = new Font(20);
		allLangLabel.setFont(font);
		langAmountLabel.setFont(font);
		quitButton.setFont(font);
		startButton.setFont(font);
		nextButton.setFont(font);

		quitButton.setStyle(Values.buttonStyle);
		startButton.setStyle(Values.buttonStyle);
		nextButton.setStyle(Values.buttonStyle);

		gridPane = new GridPane();
		gridPane.setAlignment(Pos.CENTER);

		vocableLabel = new Label("Test");
		vocableLabel.setFont(new Font(30));
		vocableLabel.setAlignment(Pos.CENTER);
		vocableLabel.setPadding(new Insets(20));

		descLabel.setFont(new Font(30));

		inputField = new JFXTextField();

		gridPane.add(vocableLabel, 0, 0);
		gridPane.add(inputField, 0, 1);

		allLangLabel.setText("");
		langAmountLabel.setText("");

		nextButton.setVisible(false);

		//Load the different Languages
		data = FileHandler.readFile();
		if(data != null){
			List<Lesson> lessons = data.lessons;
			if(lessons.size()>0){
				for(Lesson l : lessons){
					if(!langs.contains(l.lang.toUpperCase())){
						langs.add(l.lang.toUpperCase());
					}
				}
			}
		}

		this.langListView.setItems(FXCollections.observableList(langs));

		quitButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				myController.setScreen(TitleBar.exercisesMainMenuID);
			}
		});

		startButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				if(!ended){
					selectedLang = langListView.getSelectionModel().getSelectedItem();
					if(selectedLang != null){
						vocL = new ArrayList<Vocable>();
						if(data != null){
							List<Lesson> lessons = data.lessons;
							if(lessons.size()>0){
								for(Lesson l : lessons){
									if(selectedLang.equals(l.lang.toUpperCase())){
										vocL.addAll(data.getVocablesOfLesson(l));
									}
								}
								if(vocL.size()>0){
									//Vokabelabfrage starten
									vocs = new Vocable[data.vocabularys.size()];
									vocs = data.vocabularys.toArray(vocs);
									currentVocIndex = 0;
									score = 0;
									if(vocs.length > 0){
										nextButton.setVisible(true);
										startButton.setVisible(false);
										quitButton.setVisible(true);

										mainPane.setCenter(gridPane);
										langAmountLabel.setText("Vokabel " + (currentVocIndex+1) + " von " + vocs.length);

										cv = vocs[currentVocIndex];
										vocableLabel.setText(cv.vlang1);

										currentVocIndex++;

										nextButton.setOnAction(new EventHandler<ActionEvent>() {
											@Override
											public void handle(ActionEvent event) {
												String input = inputField.getText();
												if(input.toLowerCase().equals(cv.vlang2.toLowerCase())){
													allLangLabel.setStyle(Values.backgroundRightStyle);
													allLangLabel.setText("Richtig");
													allLangLabel.setVisible(true);
													score++;
												}else{
													allLangLabel.setStyle(Values.backgroundWrongStyle);
													allLangLabel.setText("Falsch. Richtige Antwort: " + cv.vlang2);
													allLangLabel.setVisible(true);
												}
												if(currentVocIndex < vocs.length){
													cv = vocs[currentVocIndex];
													vocableLabel.setText(cv.vlang1);
													langAmountLabel.setText("Vokabel " + (currentVocIndex+1) + " von " + vocs.length);
													inputField.setText("");
													currentVocIndex++;
												}else{
													descLabel.setText("Training abgeschlossen. " + score + " von " + vocs.length + " Vokabeln waren richtig");
													mainPane.setCenter(descLabel);
													ended = true;
													quitButton.setVisible(true);
													allLangLabel.setVisible(false);
													nextButton.setVisible(false);
													startButton.setVisible(true);
												}
											}
										});
									}
								}else{
									Alert alert = new Alert(AlertType.ERROR);
									alert.setTitle("Nicht genug Vokabeln");
									alert.setHeaderText("Es gibt nicht genug Vokabeln dieser Sprache!");
									alert.showAndWait();
								}
							}else{
								Alert alert = new Alert(AlertType.ERROR);
								alert.setTitle("Keine Lektionen");
								alert.setHeaderText("Es gibt keine Lektionen mit dieser Sprache");
								alert.showAndWait();
							}
						}else{
							Alert alert = new Alert(AlertType.ERROR);
							alert.setTitle("Nicht genug Daten");
							alert.setHeaderText("Es gibt nicht genug Daten!");
							alert.showAndWait();
						}
					}else{
						Alert alert = new Alert(AlertType.ERROR);
						alert.setTitle("Keine Sprache ausgesucht");
						alert.setHeaderText("Sie haben keine Sprache markiert!");
						alert.showAndWait();
					}
				}else{
					mainPane.setCenter(langSelectVBox);
					ended = false;
				}
				
			}
		});
	}

	@Override
	public void setScreenParent(ScreensController screenPage) {
		myController = screenPage;
	}
}
