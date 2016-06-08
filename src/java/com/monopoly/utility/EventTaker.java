/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.monopoly.utility;

import com.monopoly.client.ws.InvalidParameters_Exception;
import com.monopoly.ws.MonopolyWSClient;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ShiloMangam
 */
public class EventTaker extends TimerTask {
    
    private MonopolyWSClient monopolyClient;

    public EventTaker(MonopolyWSClient monopolyClient) {
        this.monopolyClient = monopolyClient;
    }

    
    @Override
    public void run() {
        try {
            System.out.println("take events");
            this.monopolyClient.getEvents(this.monopolyClient.getLastEvent());
        } catch (InvalidParameters_Exception ex) {
            System.out.println("error");
        }
    }
    
}
