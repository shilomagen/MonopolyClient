/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.monopoly.client.engine;

import com.monopoly.client.player.Player;
import com.monopoly.client.ws.Event;
import com.monopoly.client.ws.GameDoesNotExists_Exception;
import com.monopoly.client.ws.InvalidParameters_Exception;
import com.monopoly.client.ws.PlayerStatus;
import com.monopoly.event.EventManager;
import com.monopoly.scenes.BuyingHousePopupController;
import com.monopoly.scenes.BuyingPopupController;
import com.monopoly.scenes.MainBoardController;
import com.monopoly.scenes.WinnerSceneController;
import static com.monopoly.utility.GameConstants.PC_PLAYER_NAME;
import com.monopoly.ws.MonopolyWSClient;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Popup;
import javafx.stage.Stage;

/**
 *
 * @author ShiloMangam
 */
public class ClientEngine {

    private MonopolyWSClient monopolyClient;
    private EventManager eventManager;
    private MainBoardController mainBoardController;
    private BuyingPopupController buyingPopupController;
    private BuyingHousePopupController buyingHousePopupController;
    private WinnerSceneController winnerSceneController;
   

    public ClientEngine(MonopolyWSClient client) {
        this.monopolyClient = client;
    }

    public void setEventManager(EventManager eventManager) {
        this.eventManager = eventManager;
    }

    public void setMainBoardController(MainBoardController mainBoardController) {
        this.mainBoardController = mainBoardController;
    }

    

    
    
    public void engineEventHandler(Event event) throws GameDoesNotExists_Exception, IOException {
        
        switch (event.getType()) {
            case GAME_START:
                this.monopolyClient.startGame();
                break;
            case GAME_OVER:

                this.monopolyClient.killTimer();
                System.out.println("GameFinished!");

                // TODO PANE GAME OVER
                break;
            case GAME_WINNER:

                this.openWinnerScene(event.getPlayerName());
                break;
            case PLAYER_RESIGNED:
                monopolyClient.refreshPlayers(monopolyClient.getGameName());
                String PlayerResignedName = event.getPlayerName();
                Player playerResigned = monopolyClient.getPlayerManager().getPlayerByName(event.getPlayerName());

                playerResigned.getPlayerDetails().setStatus(PlayerStatus.RETIRED);
                mainBoardController.setBankruptIndication(monopolyClient.getPlayerManager().
                        getPlayerByName(event.getPlayerName()));
                mainBoardController.refreshPlayersOnMainBoard();
                mainBoardController.removePlayerIconFromBoard(playerResigned);
                mainBoardController.showMessage(event.getEventMessage());
                break;
            case PLAYER_LOST:
                monopolyClient.refreshPlayers(monopolyClient.getGameName());
                String PlayerLostName = event.getPlayerName();
                Player playerLost = monopolyClient.getPlayerManager().getPlayerByName(event.getPlayerName());

                playerLost.getPlayerDetails().setStatus(PlayerStatus.RETIRED);
                mainBoardController.setBankruptIndication(monopolyClient.getPlayerManager().
                        getPlayerByName(event.getPlayerName()));
                mainBoardController.refreshPlayersOnMainBoard();
                mainBoardController.removePlayerIconFromBoard(playerLost);
                mainBoardController.showMessage(event.getEventMessage());
                // delete owner from all the propertys
                break;
            case DICE_ROLL:
                monopolyClient.refreshPlayers(monopolyClient.getGameName());
                mainBoardController.refreshPlayersOnMainBoard();
                mainBoardController.activatePlayer(monopolyClient.getPlayerManager().getPlayerByName(event.getPlayerName()).getPositionOnPlayerVBox(), true);
                int firstDiceResult = event.getFirstDiceResult();
                int secondDiceResult = event.getSecondDiceResult();
                mainBoardController.updateDice(firstDiceResult, secondDiceResult);
                break;
            case MOVE:
                monopolyClient.refreshPlayers(monopolyClient.getGameName());
                mainBoardController.refreshPlayersOnMainBoard();
                mainBoardController.activatePlayer(monopolyClient.getPlayerManager().getPlayerByName(event.getPlayerName()).getPositionOnPlayerVBox(), true);
                boolean canMove = event.isPlayerMove();
                if (canMove) {
                    int placeToMove = event.getNextBoardSquareID();
                    mainBoardController.movePlayerIconToSpecificCell(placeToMove, monopolyClient.
                            getPlayerManager().getPlayerByName(event.getPlayerName()));
                    monopolyClient.getPlayerManager().getPlayerByName(event.getPlayerName()).setPosition(placeToMove);
                } else {
                    mainBoardController.activatePlayer(monopolyClient.getPlayerManager().getPlayerByName(event.getPlayerName()).getPositionOnPlayerVBox(), false);
                    mainBoardController.showMessage(event.getEventMessage());
                }
                break;
            case PASSED_START_SQUARE:
                monopolyClient.refreshPlayers(monopolyClient.getGameName());
                mainBoardController.refreshPlayersOnMainBoard();
                mainBoardController.activatePlayer(monopolyClient.getPlayerManager().getPlayerByName(event.getPlayerName()).getPositionOnPlayerVBox(), true);
                mainBoardController.showMessage(event.getEventMessage());
                mainBoardController.refreshPlayersOnMainBoard();
                break;
            case LANDED_ON_START_SQUARE:
                monopolyClient.refreshPlayers(monopolyClient.getGameName());
                mainBoardController.refreshPlayersOnMainBoard();
                mainBoardController.activatePlayer(monopolyClient.getPlayerManager().getPlayerByName(event.getPlayerName()).getPositionOnPlayerVBox(), true);
                mainBoardController.showMessage(event.getEventMessage());
                mainBoardController.refreshPlayersOnMainBoard();
                break;
            case GO_TO_JAIL:
                monopolyClient.refreshPlayers(monopolyClient.getGameName());
                mainBoardController.refreshPlayersOnMainBoard();
                mainBoardController.showMessage(event.getEventMessage());
                mainBoardController.activatePlayer(monopolyClient.getPlayerManager().getPlayerByName(event.getPlayerName()).getPositionOnPlayerVBox(), false);
                mainBoardController.refreshPlayersOnMainBoard();
                break;
            case PROPMT_PLAYER_TO_BY_ASSET:
                mainBoardController.activatePlayer(monopolyClient.getPlayerManager().getPlayerByName(event.getPlayerName()).getPositionOnPlayerVBox(), true);
                this.showBuyingAssetQuestion(event);
                monopolyClient.refreshPlayers(monopolyClient.getGameName());
                mainBoardController.refreshPlayersOnMainBoard();
                break;
            case PROPMPT_PLAYER_TO_BY_HOUSE:
                mainBoardController.activatePlayer(monopolyClient.getPlayerManager().getPlayerByName(event.getPlayerName()).getPositionOnPlayerVBox(), true);
                this.showBuyingHouseQuestion(event);
                monopolyClient.refreshPlayers(monopolyClient.getGameName());
                mainBoardController.refreshPlayersOnMainBoard();
                break;
            case ASSET_BOUGHT:
                monopolyClient.refreshPlayers(monopolyClient.getGameName());
                mainBoardController.showMessage(event.getEventMessage());
                mainBoardController.refreshPlayersOnMainBoard();
                break;
            case HOUSE_BOUGHT:
                monopolyClient.refreshPlayers(monopolyClient.getGameName());
                mainBoardController.showMessage(event.getEventMessage());
                mainBoardController.refreshPlayersOnMainBoard();
                break;
            case SURPRISE_CARD:
                monopolyClient.refreshPlayers(monopolyClient.getGameName());
                mainBoardController.showMessage(event.getEventMessage());
                mainBoardController.refreshPlayersOnMainBoard();
                break;
            case WARRANT_CARD:
                monopolyClient.refreshPlayers(monopolyClient.getGameName());
                mainBoardController.showMessage(event.getEventMessage());
                mainBoardController.refreshPlayersOnMainBoard();
                break;
            case GET_OUT_OF_JAIL_CARD:
                monopolyClient.refreshPlayers(monopolyClient.getGameName());
                mainBoardController.showMessage(event.getEventMessage());
                mainBoardController.activatePlayer(monopolyClient.getPlayerManager().getPlayerByName(event.getPlayerName()).getPositionOnPlayerVBox(), true);
                mainBoardController.refreshPlayersOnMainBoard();
            case PAYMENT:
                monopolyClient.refreshPlayers(monopolyClient.getGameName());
                if (event.isPaymemtFromUser()) {
                    mainBoardController.showMessage(event.getPlayerName() + "Paid to " + event.getPaymentToPlayerName() + " " + event.getPaymentAmount() + "$");
                } else {
                    mainBoardController.showMessage(event.getPaymentToPlayerName() + "Paid to " + event.getPlayerName() + " " + event.getPaymentAmount() + "$");
                }
                mainBoardController.refreshPlayersOnMainBoard();
                break;
            case PLAYER_USED_GET_OUT_OF_JAIL_CARD:
                monopolyClient.refreshPlayers(monopolyClient.getGameName());
                mainBoardController.showMessage(event.getEventMessage());
                mainBoardController.activatePlayer(monopolyClient.getPlayerManager().getPlayerByName(event.getPlayerName()).getPositionOnPlayerVBox(), true);
                mainBoardController.refreshPlayersOnMainBoard();
            default:
                System.out.println(event.getType().toString());
                break;
        }
    }

    private void openGeneralBuyingPopup(String property, String nameOfProperty, String price, Event event) throws IOException {
        Popup buyingPopUp = new Popup();
        buyingPopUp.setX(500);
        buyingPopUp.setY(150);
        this.buyingPopupController = new BuyingPopupController();
        FXMLLoader load = new FXMLLoader();
        load.setLocation(MainBoardController.class.getResource("BuyingPopup.fxml"));
        Pane buyingPopupPane = load.load();
        buyingPopupController = (BuyingPopupController) load.getController();
        buyingPopupController.setProperty(property);
        buyingPopupController.setNameOfProperty(nameOfProperty);
        buyingPopupController.setPrice(price);
        buyingPopUp.getContent().add(buyingPopupPane);
        Stage stage = (Stage) this.mainBoardController.getMainBoardScene().getWindow();
        buyingPopUp.show(stage);
        buyingPopupController.getFinish().addListener((source, oldValue, newValue) -> {
            if (newValue) {
                try {
                    this.handlePlayerBuyableChoice(this.buyingPopupController, event);
                } catch (InvalidParameters_Exception ex) {
                    Logger.getLogger(ClientEngine.class.getName()).log(Level.SEVERE, null, ex);
                }
                buyingPopUp.hide();
            }
        });
        if (event.getPlayerName().equals(PC_PLAYER_NAME)) {
            this.buyingPopupController.yesButtonOnAction();
        }

    }

    private void handlePlayerBuyableChoice(BuyingPopupController buyingPopupController, Event event) throws InvalidParameters_Exception {
        boolean isWantToBuy = buyingPopupController.isWantToBuy();
        this.monopolyClient.buyAnswer(event.getId(), isWantToBuy);
        //TODO monopolyWeb buy
    }

    private void showBuyingAssetQuestion(Event event) throws IOException {
        String[] tokens = event.getEventMessage().split("_");
        String kindOfProperty = tokens[0];
        String nameOfProperty = tokens[1];
        String priceOfProperty = tokens[2];
        this.openGeneralBuyingPopup(kindOfProperty, nameOfProperty, priceOfProperty, event);
    }

    private void openBuyingHousePopup(String cityName, String price, Event event) throws IOException {

        Popup buyingHousePopUp = new Popup();
        buyingHousePopUp.setX(500);
        buyingHousePopUp.setY(150);

        this.buyingHousePopupController = new BuyingHousePopupController();
        FXMLLoader load = new FXMLLoader();
        load.setLocation(MainBoardController.class.getResource("BuyingHousePopup.fxml"));
        Pane buyingHousePopupPane = load.load();
        buyingHousePopupController = (BuyingHousePopupController) load.getController();
        buyingHousePopupController.setCityLabel(cityName);
        buyingHousePopupController.setPriceLabel(price);
        buyingHousePopUp.getContent().add(buyingHousePopupPane);
        Stage stage = (Stage) this.mainBoardController.getMainBoardScene().getWindow();
        buyingHousePopUp.show(stage);
        buyingHousePopupController.getFinish().addListener((source, oldValue, newValue) -> {
            if (newValue) {
                this.handlePlayerHouseChoice(this.buyingHousePopupController, event.getBoardSquareID());
                buyingHousePopUp.hide();
            }
        });
        if (event.getPlayerName().equals(PC_PLAYER_NAME)) {
            this.buyingHousePopupController.yesButtonOnAction();
        }
    }

    private void handlePlayerHouseChoice(BuyingHousePopupController buyingHousePopupController, int playerPosition) {

        boolean isWantToBuy = buyingHousePopupController.isWantToBuy();
        if (isWantToBuy) {
            mainBoardController.addHouseToSpecificCell(playerPosition);
        }

    }

    private void showBuyingHouseQuestion(Event event) throws IOException {
        String[] tokens = event.getEventMessage().split("_");
        String cityName = tokens[0];
        String price = tokens[1];
        this.openBuyingHousePopup(cityName, price, event);
    }

    private void openWinnerScene(String winnerPlayerName) throws IOException {
        FXMLLoader load = new FXMLLoader();
        load.setLocation(MainBoardController.class.getResource("WinnerScene.fxml"));
        Pane WinnerScenePane = load.load();
        this.winnerSceneController = new WinnerSceneController();
        this.winnerSceneController = (WinnerSceneController) load.getController();
        this.winnerSceneController.setWinnerName(winnerPlayerName);
        Stage stage = (Stage) this.mainBoardController.getMainBoardScene().getWindow();
        Scene scene = new Scene(WinnerScenePane);
        stage.setScene(scene);
        stage.show();
    }

}
