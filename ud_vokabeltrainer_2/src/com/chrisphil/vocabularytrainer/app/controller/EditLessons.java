package com.chrisphil.vocabularytrainer.app.controller;

import java.net.URL;
import java.util.Optional;
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
import com.pepperonas.fxiconics.FxIconics;
import com.pepperonas.fxiconics.gmd.FxFontGoogleMaterial;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

public class EditLessons implements Initializable,ControlledScreen{

	ScreensController myControlller;
	Lesson currentLesson;
	Vocable currentVocable;
	Data data;

	@FXML
	JFXButton lessonAddButton;

	@FXML
	JFXButton lessonDeleteButton;

	@FXML
	JFXTextField lessonNameLabel;

	@FXML
	JFXTextField lessonLangLabel;

	@FXML
	JFXButton vocAddButton;

	@FXML
	JFXButton vocDeleteButton;

	@FXML
	JFXButton vocEditButton;

	@FXML
	JFXListView<Lesson> lessonListView;

	@FXML
	JFXListView<Vocable> vocableListView;

	@FXML
	JFXButton lessonSaveButton;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		assert lessonAddButton != null : "fx:id \"lessonAddButton\" was not injected! 'EditLessonsView.fxml'";
		assert lessonDeleteButton != null : "fx:id \"lessonDeleteButton\" was not injected! 'EditLessonsView.fxml'";
		assert vocAddButton != null : "fx:id \"vocAddButton\" was not injected! 'EditLessonsView.fxml'";
		assert vocDeleteButton != null : "fx:id \"vocDeleteButton\" was not injected! 'EditLessonsView.fxml'";
		assert vocEditButton != null : "fx:id \"vocEditButton\" was not injected! 'EditLessonsView.fxml'";
		assert lessonNameLabel != null : "fx:id \"LessonNameLabel\" was not injected! 'EditLessonsView.fxml'";
		assert lessonLangLabel != null : "fx:id \"LessonLangLabel\" was not injected! 'EditLessonsView.fxml'";
		assert lessonListView != null : "fx:id \"lessonListView\" was not injected! !EditLessonsView.fxml'";
		assert vocableListView != null : "fx:id \"vocableListView\" was not injected! !EditLessonsView.fxml'";
		assert lessonSaveButton != null : "fx:id \"lessonSaveButton\" was not injected! 'EditLessonsView.fxml'";


		//After everything is injected

		this.lessonAddButton.setFont(FxIconics.getGoogleMaterialFont(20));
		this.lessonAddButton.setText(FxFontGoogleMaterial.Icons.gmd_plus_one.toString());
		this.lessonAddButton.setStyle(Values.buttonStyle);

		this.lessonDeleteButton.setFont(FxIconics.getGoogleMaterialFont(20));
		this.lessonDeleteButton.setText(FxFontGoogleMaterial.Icons.gmd_delete.toString());
		this.lessonDeleteButton.setStyle(Values.buttonStyle);

		this.vocDeleteButton.setFont(FxIconics.getGoogleMaterialFont(20));
		this.vocDeleteButton.setText(FxFontGoogleMaterial.Icons.gmd_delete.toString());
		this.vocDeleteButton.setStyle(Values.buttonStyle);

		this.vocAddButton.setFont(FxIconics.getGoogleMaterialFont(20));
		this.vocAddButton.setText(FxFontGoogleMaterial.Icons.gmd_plus_one.toString());
		this.vocAddButton.setStyle(Values.buttonStyle);

		this.vocEditButton.setFont(FxIconics.getGoogleMaterialFont(20));
		this.vocEditButton.setText(FxFontGoogleMaterial.Icons.gmd_edit.toString());
		this.vocEditButton.setStyle(Values.buttonStyle);
		
		this.lessonSaveButton.setFont(FxIconics.getGoogleMaterialFont(20));
		this.lessonSaveButton.setText(FxFontGoogleMaterial.Icons.gmd_save.toString());
		this.lessonSaveButton.setStyle(Values.buttonStyle);

		this.lessonListView.setCellFactory((ListView<Lesson> l) -> new LessonsListCell());
		this.vocableListView.setCellFactory((ListView<Vocable> l) -> new VocabularyListCell());

		this.lessonAddButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				onLessonAddButton();
			}
		});

		this.lessonDeleteButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				onLessonDeleteButton();
			}
		});

		this.vocAddButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				onVocableAddButton();
			}
		});

		this.vocDeleteButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				onVocableDeleteButton();
			}
		});

		this.vocEditButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				onVocableEditButton();
			}
		});
		
		this.lessonSaveButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				onLessonSaveButton();
			}
		});

		this.data = FileHandler.readFile();

		this.lessonListView.setItems(FXCollections.observableList(data.lessons));

		this.lessonListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Lesson>() {

			@Override
			public void changed(ObservableValue<? extends Lesson> arg0, Lesson old1, Lesson new1) {
				currentLesson = new1;
				if(currentLesson!=null){
					lessonNameLabel.setText(currentLesson.topic);
					lessonLangLabel.setText(currentLesson.lang);
					vocableListView.setItems(FXCollections.observableList(data.getVocablesOfLesson(currentLesson)));
				}
			}
		});

		this.vocableListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Vocable>() {

			@Override
			public void changed(ObservableValue<? extends Vocable> observable, Vocable oldValue, Vocable newValue) {
				currentVocable = newValue;
			}
		});
		this.lessonListView.getSelectionModel().select(0);
		currentLesson = this.lessonListView.getSelectionModel().getSelectedItem();
		if(currentLesson != null){
			vocableListView.setItems(FXCollections.observableList(data.getVocablesOfLesson(currentLesson)));
		}


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

	static class VocabularyListCell extends ListCell<Vocable>{

		public VocabularyListCell() {
			super();
		}

		@Override
		public void updateItem(Vocable item, boolean empty) {
			super.updateItem(item, empty);
			HBox box = new HBox();
			Label voc = new Label();
			if (item != null) {
				voc.setText(item.vlang1 + " - " + item.vlang2 + " | "
						+item.lang1 + " - " + item.lang2);
				box.getChildren().add(voc);
				setGraphic(box);
			} else {
				setGraphic(null);
			}
		}
	}

	@Override
	public void setScreenParent(ScreensController screenPage) {
		myControlller = screenPage;
	}

	public void onLessonAddButton(){
		Alert dialog = new Alert(AlertType.CONFIRMATION);
		dialog.setTitle("Neue Lektion");
		dialog.setHeaderText("Geben sie die Daten der neuen Lektion ein: ");

		GridPane pane = new GridPane();
		Label topicLabel = new Label("Name: ");
		Label langLabel = new Label("Trainierende Sprache: ");
		JFXTextField topicField = new JFXTextField();
		JFXTextField langField = new JFXTextField();
		pane.add(topicLabel, 0, 0);
		pane.add(langLabel, 1, 0);
		pane.add(topicField, 0, 1);
		pane.add(langField, 1, 1);

		dialog.getDialogPane().setContent(pane);

		Optional<javafx.scene.control.ButtonType> result = dialog.showAndWait();
		if(result.isPresent()){
			boolean success = true;
			if(topicField.getText().equals("") || langField.getText().equals("")){
				success = false;
				Alert a = new Alert(AlertType.ERROR);
				a.setTitle("Fehlgeschlagen!");
				a.setContentText("Es kann keine Lektion ohne Name erstellt werden");
				a.showAndWait();
			}
			for(Lesson currentL : data.lessons){
				if(currentL.topic.equals(topicField.getText())){
					success = false;
					Alert a = new Alert(AlertType.ERROR);
					a.setTitle("Fehlgeschlagen!");
					a.setContentText("Es existiert bereits eine Lektion mit diesem Namen.");
					a.showAndWait();
				}
			}
			if(success){
				Lesson l = new Lesson(topicField.getText(),langField.getText());
				data.addLesson(l);
				FileHandler.writeFile(data);
				this.lessonListView.setItems(FXCollections.observableList(data.lessons));
			}
		}
	}

	public void onLessonDeleteButton(){
		Alert dialog = new Alert(AlertType.WARNING);
		dialog.setTitle("Lektion entfernen");
		dialog.setContentText("Sind sie sicher das sie die Lektion entfernen wollen ?");

		Optional<javafx.scene.control.ButtonType> result = dialog.showAndWait();
		if(result.isPresent()){
			Lesson ldel = lessonListView.getSelectionModel().getSelectedItem();
			if(ldel != null){
				if(data.removeLesson(ldel.id)){
					Alert dialogCon = new Alert(AlertType.CONFIRMATION);
					dialogCon.setTitle("Erfolgreich");
					dialog.setContentText("Die Lektion wurde erfolgreich entfernt!");

					FileHandler.writeFile(data);
					this.lessonListView.setItems(FXCollections.observableList(data.lessons));
				}else {
					Alert dialogerr = new Alert(AlertType.ERROR);
					dialogerr.setTitle("Fehler");
					dialogerr.setContentText("Die Lektion konnte nicht entfernt werden.");
				}
			}

		}
	}

	public void onVocableAddButton(){
		Alert dialog = new Alert(AlertType.CONFIRMATION);
		dialog.setTitle("Neue Vokabel");
		dialog.setHeaderText("Geben sie die Daten der neuen Vokabel ein: ");

		GridPane pane = new GridPane();
		Label vlang1Label = new Label("Vokabel(Sprache 1): ");
		Label vlang2Label = new Label("Vokabel(Sprache 2): ");
		Label lang1Label = new Label("Sprache 1: ");
		Label lang2Label = new Label("Sprache 2: ");

		JFXTextField vlang1Field = new JFXTextField();
		JFXTextField vlang2Field = new JFXTextField();
		JFXTextField lang1Field = new JFXTextField();
		JFXTextField lang2Field = new JFXTextField();
		pane.add(vlang1Label, 0, 0);
		pane.add(vlang2Label, 1, 0);
		pane.add(vlang1Field, 0, 1);
		pane.add(vlang2Field, 1, 1);
		pane.add(lang1Label, 0, 2);
		pane.add(lang2Label, 1, 2);
		pane.add(lang1Field, 0, 3);
		pane.add(lang2Field, 1, 3);


		dialog.getDialogPane().setContent(pane);
		Optional<javafx.scene.control.ButtonType> result = dialog.showAndWait();
		if(result.isPresent()){
			boolean success = true;
			if(vlang1Field.getText().equals("")||vlang2Field.getText().equals(""))
				success = false;
			if(lang1Field.getText().equals("")||lang2Field.getText().equals(""))
				success = false;

			for(Vocable v : data.getVocablesOfLesson(currentLesson)){
				if(v.vlang1.equals(vlang1Field.getText())&&v.vlang2.equals(vlang2Field.getText())){
					success = false;
					Alert a = new Alert(AlertType.ERROR);
					a.setTitle("Fehler beim hinzufuegen.");
					a.setContentText("Es ist bereits eine solche Vokabel vorhanden.");
					a.showAndWait();
				}
			}
			if(success){
				Vocable voc = new Vocable(vlang1Field.getText(), vlang2Field.getText(), lang1Field.getText(), lang2Field.getText());
				data.vocabularys.add(voc);
				data.lessons.get(data.lessons.lastIndexOf(currentLesson)).addVocable(voc);
				currentLesson = data.lessons.get(data.lessons.lastIndexOf(currentLesson));
				vocableListView.setItems(FXCollections.observableList(data.getVocablesOfLesson(currentLesson)));
				FileHandler.writeFile(data);
			}
		}
	}

	public void onVocableDeleteButton(){
		Alert dialog = new Alert(AlertType.WARNING);
		dialog.setTitle("Vokabel entfernen");
		dialog.setContentText("Sind sie sicher das sie die Vokabel entfernen wollen ?");

		Optional<javafx.scene.control.ButtonType> result = dialog.showAndWait();
		if(result.isPresent()){
			if(currentVocable!=null&&currentLesson!=null){
				data.removeVocable(currentVocable.id);
				data.lessons.get(data.lessons.lastIndexOf(currentLesson)).removeVocable(currentVocable.id);
				FileHandler.writeFile(data);
				this.lessonListView.setItems(FXCollections.observableList(data.lessons));
			}else{
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Fehler");
				alert.setContentText("Keine Vokabel ausgewaehlt!");
				alert.showAndWait();
			}
		}
	}

	public void onVocableEditButton(){
		Alert dialog = new Alert(AlertType.CONFIRMATION);
		dialog.setTitle("Vokabel bearbeiten");
		dialog.setHeaderText("Bearbeiten sie die Daten der Vokabel: ");

		GridPane pane = new GridPane();
		Label vlang1Label = new Label("Vokabel(Sprache 1): ");
		Label vlang2Label = new Label("Vokabel(Sprache 2): ");
		Label lang1Label = new Label("Sprache 1: ");
		Label lang2Label = new Label("Sprache 2: ");

		if(currentVocable != null){
			JFXTextField vlang1Field = new JFXTextField(currentVocable.vlang1);
			JFXTextField vlang2Field = new JFXTextField(currentVocable.vlang2);
			JFXTextField lang1Field = new JFXTextField(currentVocable.lang1);
			JFXTextField lang2Field = new JFXTextField(currentVocable.lang2);
			pane.add(vlang1Label, 0, 0);
			pane.add(vlang2Label, 1, 0);
			pane.add(vlang1Field, 0, 1);
			pane.add(vlang2Field, 1, 1);
			pane.add(lang1Label, 0, 2);
			pane.add(lang2Label, 1, 2);
			pane.add(lang1Field, 0, 3);
			pane.add(lang2Field, 1, 3);


			dialog.getDialogPane().setContent(pane);
			Optional<javafx.scene.control.ButtonType> result = dialog.showAndWait();
			if(result.isPresent()){
				boolean success = true;
				if(vlang1Field.getText().equals("")||vlang2Field.getText().equals(""))
					success = false;
				if(lang1Field.getText().equals("")||lang2Field.getText().equals(""))
					success = false;

				if(success){
					int lastIndex = data.vocabularys.lastIndexOf(currentVocable);
					data.vocabularys.get(lastIndex).vlang1 = vlang1Field.getText();
					data.vocabularys.get(lastIndex).vlang2 = vlang2Field.getText();
					data.vocabularys.get(lastIndex).lang1 = lang1Field.getText();
					data.vocabularys.get(lastIndex).lang2 = lang2Field.getText();

					FileHandler.writeFile(data);
					currentVocable = data.vocabularys.get(lastIndex);
					this.lessonListView.setItems(FXCollections.observableList(data.lessons));
					this.vocableListView.setItems(FXCollections.observableList(data.getVocablesOfLesson(currentLesson)));
					this.vocableListView.refresh();
				}else{
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Fehler");
					alert.setContentText("Konnte Vokabel nicht bearbeiten!");
					alert.showAndWait();
				}
			}
		}else{
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Keine Vokabel");
			alert.setHeaderText("Es ist keine Vokabel markiert");
			alert.showAndWait();
		}
	}
	
	public void onLessonSaveButton(){
		int lastIndex = data.lessons.lastIndexOf(currentLesson);
		boolean success = true;
		
		if(!lessonNameLabel.getText().equals("")){
			data.lessons.get(lastIndex).topic = lessonNameLabel.getText();
		}else{
			success = false;
		}
		
		if(!lessonLangLabel.getText().equals("")){
			data.lessons.get(lastIndex).lang = lessonLangLabel.getText();
		}else{
			success = false;
		}
		
		if(success){
			FileHandler.writeFile(data);
			lessonListView.setItems(FXCollections.observableList(data.lessons));
			lessonListView.refresh();
			
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Gespeichert");
			alert.setHeaderText("Die Lektion wurde gespeichert.");
			alert.showAndWait();
		}else{
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Nicht gespeichert!");
			alert.setHeaderText("Die Werte dürfen nicht leer sein!");
			alert.showAndWait();
		}
		
	}
}