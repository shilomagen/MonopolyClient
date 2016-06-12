package com.monopoly.scenes;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Eden
 */
public class GameOverSceneController implements Initializable {

     final private BooleanProperty finish = new SimpleBooleanProperty(this, "Game Over Scene");
    @FXML 
    private Button exitButton;
    
    @FXML
    public void exitOnAction(){
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }
    
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
