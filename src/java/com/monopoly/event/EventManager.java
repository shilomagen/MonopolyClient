/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.monopoly.event;

import com.monopoly.client.engine.ClientEngine;
import com.monopoly.client.ws.Event;
import com.monopoly.client.ws.GameDoesNotExists_Exception;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;

/**
 *
 * @author ShiloMangam
 */
public class EventManager {

    private EventModel eventModel;
    private ClientEngine clientEngine;

    public EventManager(ClientEngine clientEngine) {
        this.eventModel = new EventModel();
        this.clientEngine = clientEngine;
    }

    public int returnLastEventID() {
        return this.eventModel.returnLastEventID();
    }

    public void absorbEventsFromServer(List<Event> eventFromServer) {

        for (Event event : eventFromServer) {
            this.eventModel.addEventToUnhandledEventList(event);

        }

    }

    public void moveFromUnhandledToHandledEventList() {
        this.eventModel.moveFromUnhandledToHandledEventList();
    }

    public boolean isThereEventToHandle() {
        return this.eventModel.isUnhandledNotEmpty();
    }

    public List<Event> getUnhandledEvents() {
        return this.eventModel.getUnhandledEvents();
    }

    public void handleEvents() throws GameDoesNotExists_Exception, IOException, InterruptedException {
        for (Event event : this.eventModel.getUnhandledEvents()) {
            Platform.runLater(()->{
                try {
                    this.clientEngine.engineEventHandler(event);
                } catch (GameDoesNotExists_Exception ex) {
                    Logger.getLogger(EventManager.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(EventManager.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
            Thread.sleep(1000);
            
        }
        this.moveFromUnhandledToHandledEventList();

    }

}
