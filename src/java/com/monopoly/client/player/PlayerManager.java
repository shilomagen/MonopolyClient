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
public class PlayerManager {
    private PlayerModel playerModel;
    private Player clientPlayer;

    public PlayerManager() {
        this.playerModel = new PlayerModel();
        
    }

    public void addClientPlayer(Player clientPlayer) {
        this.playerModel.addPlayer(clientPlayer);
        this.clientPlayer = clientPlayer;
    }

    public void removePlayerFromListByName(String name, List<PlayerDetails> playerDetailList) {
        for (int i=0;i<playerDetailList.size();++i){
             if (playerDetailList.get(i).getName().equals(name))
                 playerDetailList.remove(i);
        }
    }

    public void addGhostPlayersWithoutClientPlayer(List<PlayerDetails> playerDetailList, String name) {
        for (int i=0;i<playerDetailList.size();++i){
             if (!playerDetailList.get(i).getName().equals(name))
                 this.playerModel.addPlayer(new Player(playerDetailList.get(i)));
        }
    }

    public HashSet<Player> getPlayers() {
        return this.playerModel.getPlayerModel();
    }
    
    public Player getPlayerByName(String name)
    {
        for (Iterator<Player> itertor = playerModel.getPlayerModel().iterator(); itertor.hasNext(); ) {
        Player playerToFind = itertor.next();
        if (playerToFind.getPlayerName().equals(name))
            return playerToFind;
        }
        return null;
    }

    public Player getClientPlayer() {
        return this.clientPlayer;
    }

    public void refreshPlayers(List<PlayerDetails> playerDetailList) {
        for (PlayerDetails iterator : playerDetailList)
        {
            this.getPlayerByName(iterator.getName()).setPlayerDetails(iterator);
        }
    }
    
}
