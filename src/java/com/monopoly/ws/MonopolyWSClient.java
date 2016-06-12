/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.monopoly.ws;

import com.monopoly.client.engine.ClientEngine;
import com.monopoly.client.player.Player;
import com.monopoly.client.player.PlayerManager;
import com.monopoly.client.ws.DuplicateGameName_Exception;
import com.monopoly.client.ws.Event;
import com.monopoly.client.ws.GameDetails;
import com.monopoly.client.ws.GameDoesNotExists_Exception;
import com.monopoly.client.ws.GameStatus;
import com.monopoly.client.ws.InvalidParameters_Exception;
import com.monopoly.client.ws.MonopolyWebService;
import com.monopoly.client.ws.MonopolyWebServiceService;
import com.monopoly.client.ws.PlayerDetails;
import com.monopoly.event.EventManager;
import com.monopoly.scenes.MainBoardController;
import com.monopoly.scenes.SceneManager;
import com.monopoly.utility.EventTaker;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Timer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ShiloMangam
 */
public class MonopolyWSClient {

    private MonopolyWebServiceService monopolyService;
    private MonopolyWebService monopoly;
    private ClientEngine clientEngine;
    private String schemeContent;
    private String xmlContent;
    private String gameName;
    private GameDetails gameDetails;
    private Player clientPlayer;
    private PlayerManager playerManager;
    private Timer eventTaker;
    private EventManager eventManager;
    private SceneManager sceneManager;
    private Timer timer;
    private MainBoardController mainBoardController;

    public MonopolyWSClient(SceneManager _sceneManager) {
        this.clientEngine = new ClientEngine(this);
        this.playerManager = new PlayerManager();
        this.eventManager = new EventManager(this.clientEngine);
        this.clientEngine.setEventManager(this.eventManager);
        this.sceneManager = _sceneManager;

    }

    public void createWSClient(String serverAddress, int serverPort) throws MalformedURLException {
        URL url = new URL("http://" + serverAddress + ":" + serverPort + "/monopolyapi/MonopolyWebServiceService");
        this.monopolyService = new MonopolyWebServiceService(url);
        this.monopoly = this.monopolyService.getMonopolyWebServicePort();

    }

    public void createNewGame(String _gameName, int humanPlayers, int pcPlayers) throws DuplicateGameName_Exception, InvalidParameters_Exception {
        this.gameName = _gameName;
        this.monopoly.createGame(pcPlayers, humanPlayers, this.gameName);
        this.getBoardScheme();
        this.getBoardXML();
        this.mainBoardController = new MainBoardController(this.sceneManager);
        this.sceneManager.setMainBoardController(this.mainBoardController);

    }

    public void addPlayerToGame(String gameName, String playerName) throws GameDoesNotExists_Exception, InvalidParameters_Exception {
        int playerID = this.monopoly.joinGame(gameName, playerName);
        this.clientPlayer = new Player(this.monopoly.getPlayerDetails(playerID), playerID);
        this.playerManager.addClientPlayer(this.clientPlayer);
        this.startTakingEvents();

    }

    public void getBoardScheme() {
        this.schemeContent = this.monopoly.getBoardSchema();
        if (this.schemeContent != null) {
            this.createNewFileInResources("monopoly_config.xsd", this.schemeContent);
        }
    }

    public void getBoardXML() {
        this.xmlContent = this.monopoly.getBoardXML();
        if (this.xmlContent != null) {
            this.createNewFileInResources("monopoly_config.xml", this.xmlContent);
        }
    }

    public void createNewFileInResources(String fileName, String fileContent) {
        BufferedWriter bufferedWriter = null;
        try {
            URL url = getClass().getResource(File.separator + "com" + File.separator + "monopoly" + File.separator + "resources");
            File fileToCreate = new File(url.getPath() + File.separator + fileName);
            if (!fileToCreate.exists()) {
                fileToCreate.createNewFile();
            }
            Writer writer = new FileWriter(fileToCreate);
            bufferedWriter = new BufferedWriter(writer);
            bufferedWriter.write(fileContent);
        } catch (IOException ex) {
            System.out.println("couldnt write");
        } finally {
            try {
                if (bufferedWriter != null) {
                    bufferedWriter.close();
                }
            } catch (Exception ex) {
                System.out.println("couldnt finally");
            }
        }

    }

    public void getGameDetails(String gameName) throws GameDoesNotExists_Exception {
        this.gameDetails = this.monopoly.getGameDetails(gameName);
    }

    public void getPlayersDetails(String gameName) throws GameDoesNotExists_Exception {
        List<PlayerDetails> playerDetailList = this.monopoly.getPlayersDetails(gameName);
        this.playerManager.addGhostPlayersWithoutClientPlayer(playerDetailList, this.clientPlayer.getPlayerDetails().getName());

    }

    public int getLastEvent() {
        return this.eventManager.returnLastEventID();
    }

    public void getEvents(int eventID) throws InvalidParameters_Exception {

        List<Event> eventFromServer;
        eventFromServer = this.monopoly.getEvents(eventID, this.playerManager.getClientPlayer().getPlayerID());
        if (eventFromServer != null) {
            this.eventManager.absorbEventsFromServer(eventFromServer);
        }

        if (this.eventManager.isThereEventToHandle()) {
            this.eventManager.handleEvents();
        } else {
            System.out.println("there is no event to handle");
        }
    }

    public PlayerManager getPlayerManager() {
        return this.playerManager;
    }

    public boolean isActive() {
        try {
            return this.monopoly.getGameDetails(this.gameName).getStatus() != GameStatus.FINISHED;
        } catch (GameDoesNotExists_Exception ex) {
            System.out.println("Cant find game, bitch");
            return false;
        }

    }

    public void killTimer() {
        this.timer.cancel();
    }

    private void startTakingEvents() {
        this.timer = new Timer();
        timer.schedule(new EventTaker(this), 0, 1000);
    }

    public void startGame() {
        try {
            this.getPlayersDetails(this.gameName);
            this.mainBoardController.setPlayersManager(this.playerManager);
            this.mainBoardController.addPlayersToMainBoard();
            this.sceneManager.setMainBoardScene();

        } catch (GameDoesNotExists_Exception ex) {
            System.out.println("error on start game");
        }
    }

    /**
     * @return the mainBoardController
     */
    public MainBoardController getMainBoardController() {
        return mainBoardController;
    }

    /**
     * @param mainBoardController the mainBoardController to set
     */
    public void setMainBoardController(MainBoardController mainBoardController) {
        this.mainBoardController = mainBoardController;
    }

}
