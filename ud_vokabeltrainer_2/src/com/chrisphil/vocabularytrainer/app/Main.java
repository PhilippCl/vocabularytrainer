package com.chrisphil.vocabularytrainer.app;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.chrisphil.vocabularytrainer.app.util.Values;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main extends Application{
	
	/**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Application.launch(Main.class, (java.lang.String[])null);
    }

    /**
     * @param primaryStage the main application stage
     */
    
	@Override
	public void start(Stage primaryStage) throws Exception {
		try {
			primaryStage.getIcons().add(new Image(this.getClass().getResourceAsStream("view/icon.png")));
            BorderPane page = (BorderPane) FXMLLoader.load(Main.class.getResource("view/TitlebarView.fxml"));
            page.setStyle(Values.backgroundStyle);
            Scene scene = new Scene(page);
            primaryStage.setScene(scene);
            primaryStage.setMinHeight(600);
            primaryStage.setMinWidth(800);
            primaryStage.setHeight(601);
            primaryStage.setWidth(801);
            primaryStage.setTitle("Vokabeltrainer");
            primaryStage.show();
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
	}

}
