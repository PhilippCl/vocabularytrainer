package com.chrisphil.vocabularytrainer.app.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ResourceBundle;

import com.chrisphil.vocabularytrainer.app.Main;
import com.chrisphil.vocabularytrainer.app.util.ControlledScreen;
import com.chrisphil.vocabularytrainer.app.util.ScreensController;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;

public class HelpMenu implements Initializable, ControlledScreen{

	ScreensController myController;
	
	@FXML
	TextArea helpTextArea;
	
	@Override
	public void setScreenParent(ScreensController screenPage) {
		myController = screenPage;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		assert helpTextArea != null :  "fx:id \"helpTextArea\" was not injected! 'HelpMenu.fxml'";
		
		try {
			String helpText = readFile("view/hilfe.txt");
			helpTextArea.setText(helpText);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	String readFile(String fileName) throws IOException {
		
	    BufferedReader br = new BufferedReader(new InputStreamReader(Main.class.getResourceAsStream((fileName))));
	    try {
	        StringBuilder sb = new StringBuilder();
	        String line = br.readLine();

	        while (line != null) {
	            sb.append(line);
	            sb.append("\n");
	            line = br.readLine();
	        }
	        return sb.toString();
	    } finally {
	        br.close();
	    }
	}

}
