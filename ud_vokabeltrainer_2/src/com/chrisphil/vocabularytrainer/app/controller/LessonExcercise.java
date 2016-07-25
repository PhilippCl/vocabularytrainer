package com.chrisphil.vocabularytrainer.app.controller;

import java.net.URL;
import java.util.ArrayList;
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
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class LessonExcercise implements Initializable,ControlledScreen{
	
	ScreensController myController;
	Data data;
	ArrayList<Lesson> lessonArrayList;
	ArrayList<Vocable> vocableList;
	boolean ended = false;
	Label descLabel = new Label();
	int currentVocIndex = 0;
	int score = 0;
	Vocable[] vocs;
	Vocable cv;
	
	@FXML
	Label allLangLabel;
	@FXML
	Label langAmountLabel;
	@FXML
	VBox langSelectVBox;
	@FXML
	JFXListView<Lesson> langListView;
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
		
		vocableList = new ArrayList<Vocable>();
		lessonArrayList = new ArrayList<Lesson>();
		
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
		
		inputField = new JFXTextField();
		
		gridPane.add(vocableLabel, 0, 0);
		gridPane.add(inputField, 0, 1);
		
		allLangLabel.setText("");
		langAmountLabel.setText("");
		nextButton.setVisible(false);
		
		this.langListView.setCellFactory((ListView<Lesson> l) -> new LessonsListCell());
		
		this.data = FileHandler.readFile();
		this.langListView.setItems(FXCollections.observableList(data.lessons));
		
		this.langListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		
		this.langListView.setOnMouseClicked(new EventHandler<Event>() {

	         @Override
	         public void handle(Event event) {
	             ObservableList<Lesson> selectedItems =  langListView.getSelectionModel().getSelectedItems();
	             
	             lessonArrayList.clear();
	             if(selectedItems!=null){
	            	 for(Lesson l : selectedItems){
	            		 lessonArrayList.add(l);
	            	 }
	             }
	         }
	     });
		
		this.startButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				if(!ended){
					if(data != null){
						if(lessonArrayList.size()!=0){
							for(Lesson l : lessonArrayList){
								vocableList.addAll(data.getVocablesOfLesson(l));
							}
							if(vocableList.size()>0){
								//Vokabelabfrage starten
								vocs = new Vocable[vocableList.size()];
								vocs = vocableList.toArray(vocs);
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
							}
							else{
								Alert alert = new Alert(AlertType.ERROR);
								alert.setTitle("Keine Vokabeln");
								alert.setHeaderText("Es gibt keine Vokabeln in dieser Lektion!");
								alert.showAndWait();
							}
						}
						else{	
							Alert a = new Alert(AlertType.ERROR);
							a.setTitle("Fehlgeschlagen!");
							a.setContentText("Es wurden keine Lektionen markiert!");
							a.showAndWait();
						}
					}
				}
				else{
					mainPane.setCenter(langSelectVBox);
					ended = false;
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
	 
	static class LessonsListCell extends ListCell<Lesson>{

		public LessonsListCell(){
			super();
		}

		@Override
		public void updateItem(Lesson item, boolean empty){
			super.updateItem(item, empty);
			HBox box = new HBox();
			Label lesson = new Label();
			lesson.setPadding(new Insets(15));

			if(item != null){
				lesson.setText(item.topic);
				box.getChildren().add(lesson);
				setGraphic(box);
			} else {
				setGraphic(null);
			}
		}
	}
	
	@Override
	public void setScreenParent(ScreensController screenPage) {
		myController = screenPage;
	}	
}