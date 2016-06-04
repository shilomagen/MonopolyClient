/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.monopoly.exceptions;

/**
 *
 * @author ShiloMangam
 */
public class InfoMissingException extends RuntimeException{
    public InfoMissingException(){
        super("Info Missing, fill it up!");
    }
    
}
