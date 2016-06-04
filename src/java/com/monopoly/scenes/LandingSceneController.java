/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.monopoly.scenes;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author ShiloMangam
 */
public class LandingSceneController implements Initializable {

    private SceneManager sceneManager;
    @FXML
    private Button createNewGameBtn;
    @FXML
    private Button joinExistingBtn;
    @FXML
    private Button exitBtn;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void onCreate(ActionEvent event) {
        this.sceneManager.getPrimaryStage().setScene(this.sceneManager.getCreateGameScene());
    }

    @FXML
    private void onJoin(ActionEvent event) {
        this.sceneManager.getPrimaryStage().setScene(this.sceneManager.getJoinGameScene());
    }

    @FXML
    private void onExit(ActionEvent event) {
        Stage stage = (Stage) exitBtn.getScene().getWindow();
        stage.close();
    }
    
    public void setSceneManager(SceneManager sceneMng){
        this.sceneManager = sceneMng;
    }

    
}
