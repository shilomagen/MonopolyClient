/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.monopoly.ws;

import com.monopoly.client.player.Player;
import com.monopoly.client.player.PlayerManager;
import com.monopoly.client.ws.DuplicateGameName_Exception;
import com.monopoly.client.ws.GameDetails;
import com.monopoly.client.ws.GameDoesNotExists_Exception;
import com.monopoly.client.ws.InvalidParameters_Exception;
import com.monopoly.client.ws.MonopolyWebService;
import com.monopoly.client.ws.MonopolyWebServiceService;
import com.monopoly.client.ws.PlayerDetails;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/**
 *
 * @author ShiloMangam
 */
public class MonopolyWSClient {

    private MonopolyWebServiceService monopolyService;
    private MonopolyWebService monopoly;
    private String schemeContent;
    private String xmlContent;
    private GameDetails gameDetails;
    private Player clientPlayer;
    private PlayerManager playerManager;
    
    public MonopolyWSClient(){
        this.playerManager = new PlayerManager();
    }

    public void createWSClient(String serverAddress, int serverPort) throws MalformedURLException {
        URL url = new URL("http://" + serverAddress + ":" + serverPort + "/monopolyapi/MonopolyWebServiceService");
        this.monopolyService = new MonopolyWebServiceService(url);
        this.monopoly = this.monopolyService.getMonopolyWebServicePort();

    }

    public void createNewGame(String gameName, String playerName, int humanPlayers, int pcPlayers) throws DuplicateGameName_Exception, InvalidParameters_Exception {
        this.monopoly.createGame(pcPlayers, humanPlayers, gameName);

    }

    public void addPlayerToGame(String gameName, String playerName) throws GameDoesNotExists_Exception, InvalidParameters_Exception {
        int playerID = this.monopoly.joinGame(gameName, playerName);  
        this.clientPlayer = new Player(this.monopoly.getPlayerDetails(playerID), playerID);
        this.playerManager.addClientPlayer(this.clientPlayer);
        
        
    }

    public void getBoardScheme() {
        this.schemeContent = this.monopoly.getBoardSchema();
        if (this.schemeContent != null)
            this.createNewFileInResources("monopoly_config.xsd", this.schemeContent);
    }

    public void getBoardXML() {
        this.xmlContent = this.monopoly.getBoardXML();
        if (this.xmlContent != null)
            this.createNewFileInResources("monopoly_config.xml", this.xmlContent);
    }

    public void createNewFileInResources(String fileName, String fileContent) {
        BufferedWriter bufferedWriter = null;
        try {
            URL url = getClass().getResource(File.separator + "com" +File.separator + "monopoly" + File.separator +"resources");
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
    
    public void getGameDetails(String gameName) throws GameDoesNotExists_Exception{
        this.gameDetails = this.monopoly.getGameDetails(gameName);
    }
    
    public void getPlayersDetails(String gameName) throws GameDoesNotExists_Exception{
        List<PlayerDetails> playerDetailList = this.monopoly.getPlayersDetails(gameName);
        this.playerManager.addGhostPlayersWithoutClientPlayer(playerDetailList, this.clientPlayer.getPlayerDetails().getName());
        
        
    }
}
