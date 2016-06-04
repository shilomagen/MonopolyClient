/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.monopoly.scenes;

import com.monopoly.client.ws.DuplicateGameName_Exception;
import com.monopoly.client.ws.InvalidParameters_Exception;
import com.monopoly.exceptions.InfoMissingException;
import com.monopoly.ws.MonopolyWSClient;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author ShiloMangam
 */
public class CreateGameController implements Initializable {

    private SceneManager sceneManager;
    @FXML
    private TextField serverAddressText;
    @FXML
    private TextField portText;
    @FXML
    private TextField playerNameText;
    @FXML
    private TextField humanText;
    @FXML
    private TextField pcText;
    @FXML
    private Label errorLabel;
    @FXML
    private Button createBtn;
    @FXML
    private Button backBtn;
    @FXML
    private TextField gameNameText;

    private MonopolyWSClient monopolyWS;
    private boolean isErrorShown = false;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.serverAddressText.setText("localhost");
        this.portText.setText("8080");
    }

    @FXML
    private void onCreate(ActionEvent event) {
        try {
            if (this.serverAddressText.getText().trim().isEmpty() || this.portText.getText().trim().isEmpty()) {
                throw new InfoMissingException();
            }
            this.monopolyWS = this.sceneManager.getMonopolyWS();
            this.monopolyWS.createWSClient(serverAddressText.getText(), Integer.parseInt(portText.getText()));
            this.monopolyWS.createNewGame(gameNameText.getText(), playerNameText.getText(), Integer.parseInt(humanText.getText()), Integer.parseInt(pcText.getText()));
            this.resetInput();

            this.sceneManager.showLandingScreen();

        } catch (DuplicateGameName_Exception | InvalidParameters_Exception | InfoMissingException | MalformedURLException ex) {

            this.showError(ex.getMessage());
        }
    }

    @FXML
    private void onBack(ActionEvent event) {
        this.sceneManager.showLandingScreen();

    }

    public void setManager(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
    }

    public void showError(String error) {
        this.isErrorShown = true;
        this.errorLabel.setText(error);
    }

    private void resetInput() {
        this.serverAddressText.setText("");
        this.portText.setText("");
        this.playerNameText.setText("");
        this.gameNameText.setText("");
        this.humanText.setText("");
        this.pcText.setText("");
        this.isErrorShown = false;
    }
}
