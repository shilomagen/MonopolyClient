/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.monopoly.client.player;

import com.monopoly.client.ws.PlayerDetails;
import javafx.scene.image.Image;

/**
 *
 * @author ShiloMangam
 */
public class Player {
    private PlayerDetails playerDetails;
    private final int playerID;
    private Image playerImage;
    private Image playerIcon;
    private int position;
    
    public Player(PlayerDetails details, int id){
        this.playerDetails = details;
        this.playerID = id;
        this.position = 0;
    }
    public Player(PlayerDetails details){
        this.playerDetails = details;
        this.playerID = -1;
    }
    
    public boolean isGhostPlayer(){
        return this.getPlayerName().contains("ROBOCOP");
        
    }
    
    
    public PlayerDetails getPlayerDetails(){
        return this.playerDetails;
    }

    /**
     * @return the playerImage
     */
    public Image getPlayerImage() {
        return playerImage;
    }

    /**
     * @param playerImage the playerImage to set
     */
    public void setPlayerImage(Image playerImage) {
        this.playerImage = playerImage;
    }

    /**
     * @return the playerIcon
     */
    public Image getPlayerIcon() {
        return playerIcon;
    }

    /**
     * @param playerIcon the playerIcon to set
     */
    public void setPlayerIcon(Image playerIcon) {
        this.playerIcon = playerIcon;
    }

    /**
     * @return the playerID
     */
    public int getPlayerID() {
        return playerID;
    }

    public String getPlayerName() {
        return this.getPlayerDetails().getName();
    }

    /**
     * @return the position
     */
    public int getPosition() {
        return position;
    }

    /**
     * @param position the position to set
     */
    public void setPosition(int position) {
        this.position = position;
    }

    public void setPlayerDetails(PlayerDetails playerDetails) {
        this.playerDetails = playerDetails;
    }
    
    
}
