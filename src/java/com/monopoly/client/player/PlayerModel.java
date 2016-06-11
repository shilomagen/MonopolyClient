/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.monopoly.client.player;

import com.monopoly.client.ws.PlayerDetails;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author ShiloMangam
 */
public class PlayerModel {
    private HashSet<Player> playerModel;
    

    PlayerModel() {
        this.playerModel = new HashSet<>();
    }

    /**
     * @return the playerModel
     */
    public HashSet<Player> getPlayerModel() {
        return playerModel;
    }

    /**
     * @param playerModel the playerModel to set
     */
    public void setPlayerModel(HashSet<Player> playerModel) {
        this.playerModel = playerModel;
    }
    
    public void addPlayer(Player playerToAdd){
        this.playerModel.add(playerToAdd);
    }
    
}
