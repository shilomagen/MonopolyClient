/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.monopoly.event;

import com.monopoly.client.ws.Event;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ShiloMangam
 */
public class EventModel {
    private List<Event> handledEvents;
    private List<Event> unhandledEvents;
    
    public EventModel(){
        this.handledEvents = new ArrayList<>();
        this.unhandledEvents = new ArrayList<>();
    }

    /**
     * @return the handledEvents
     */
    public List<Event> getEventList() {
        return handledEvents;
    }
    
    public void addEventToUnhandledEventList(Event eventToAdd){
        this.unhandledEvents.add(eventToAdd);
    }
    
    public int returnLastEventID(){
       return this.handledEvents.isEmpty() ? 0 : this.handledEvents.get(this.handledEvents.size()-1).getId()+1;
    }

    void moveFromUnhandledToHandledEventList() {
        for (Event event : this.unhandledEvents){
             this.addEventToHandledList(event);
        }
       
        this.unhandledEvents.clear();
    }
    
    public void addEventToHandledList(Event event){
        this.handledEvents.add(event);
    }

    public boolean isUnhandledNotEmpty() {
       return !this.unhandledEvents.isEmpty();
    }

    public List<Event> getUnhandledEvents() {
        return this.unhandledEvents;
    }
    
    
    
    
    
}
