package com.monopoly.scenes;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class WinnerSceneController implements Initializable {

    final private BooleanProperty finish = new SimpleBooleanProperty(this, "Winner Scene");
    @FXML 
    private Button exitButton;
    
    @FXML
    private Label playerName;
    
    public void setWinnerName(String playerName){
        this.playerName.setText(playerName);
    }
    
    @FXML
    public void exitOnAction(){
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }
    
    
    public BooleanProperty getFinish() {
		return finish;
	}
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
