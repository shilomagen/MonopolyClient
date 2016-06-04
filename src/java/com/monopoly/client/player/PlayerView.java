package com.monopoly.client.player;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;


public class PlayerView extends VBox{

    public PlayerView(String name, Image image, Image icon) {
        setSpacing(10);
        setAlignment(Pos.CENTER);
        getChildren().addAll(createImage(image), createLabel(name), createIcon(icon));
    }
    
    private Label createLabel(String title){
        return new Label(title);
    }
    
    private ImageView createImage(Image image){
    	ImageView playerImage = new ImageView(image);
    	playerImage.setPreserveRatio(false);
    	playerImage.setFitHeight(150);
    	playerImage.setFitWidth(100);
    	
        return playerImage;
    }
    
    private ImageView createIcon(Image icon){
    	 ImageView playerIcon = new ImageView(icon);
    	  playerIcon.setPreserveRatio(false);
    	  playerIcon.setFitHeight(50);
    	  playerIcon.setFitWidth(50);
    	  
    	 return playerIcon;
    	 }

}