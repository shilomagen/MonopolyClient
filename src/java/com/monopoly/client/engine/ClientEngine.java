/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.monopoly.client.engine;

import com.monopoly.client.ws.Event;
import com.monopoly.event.EventManager;
import com.monopoly.ws.MonopolyWSClient;
import javafx.application.Platform;

/**
 *
 * @author ShiloMangam
 */
public class ClientEngine {

    private MonopolyWSClient monopolyClient;
    private EventManager eventManager;

    public ClientEngine(MonopolyWSClient client) {
        this.monopolyClient = client;
    }

    public void setEventManager(EventManager eventManager){
        this.eventManager = eventManager;
    }
    public void engineEventHandler(Event event) {
        switch (event.getType()){
            case GAME_START:
                System.out.println("game on!!!");
                Platform.runLater(()->{
                    this.monopolyClient.startGame();
                });
                
                break;
            default:
                System.out.println(event.getType().toString());
                break;
        }
    }


    
}
