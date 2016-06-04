/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.monopoly.scenes;

import com.monopoly.client.ws.GameDoesNotExists_Exception;
import com.monopoly.client.ws.InvalidParameters_Exception;
import com.monopoly.ws.MonopolyWSClient;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author ShiloMangam
 */
public class JoinGameController implements Initializable {

    private SceneManager sceneManager;
    @FXML
    private TextField gameNameText;
    @FXML
    private TextField playerNameText;
    @FXML
    private Label errorLabel;
    @FXML
    private Button joinBtn;
    @FXML
    private Button backBtn;
    private MonopolyWSClient monopolyWS;
    private boolean isErrorShown = false;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void onJoin(ActionEvent event) {
        try {
            this.monopolyWS = this.sceneManager.getMonopolyWS();
            this.monopolyWS.addPlayerToGame(gameNameText.getText(), playerNameText.getText());
            this.resetInput();
            this.sceneManager.setWaitScene();
            
        } catch (GameDoesNotExists_Exception | InvalidParameters_Exception ex) {
           this.showError(ex.getMessage());
        }
    }

    @FXML
    private void onBack(ActionEvent event) {
        this.sceneManager.showLandingScreen();
    }

    void setManager(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
    }

    private void resetInput() {
        this.playerNameText.setText("");
        this.gameNameText.setText("");
    }
    
    private void showError(String error){
        this.isErrorShown = true;
        this.errorLabel.setText(error);
        
    }

}
