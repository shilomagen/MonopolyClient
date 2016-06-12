package com.monopoly.scenes;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.FadeTransition;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.util.Duration;

public class BuyingPopupController implements Initializable {


    final private BooleanProperty finish = new SimpleBooleanProperty(this, "finish buying popup");

    
    @FXML
    private Button yesButton;
    
    @FXML
    private Button noButton;
    
    @FXML
    private Label Property;
    
    @FXML
    private Label price;
    
    @FXML
    private Label nameOfProperty;
    
    private boolean wantToBuy;

    public void setProperty(String Property) {
        this.Property.setText(Property);
    }

    public void setPrice(String price) {
        this.price.setText(price);
    }

    public void setNameOfProperty(String nameOfProperty) {
        this.nameOfProperty.setText(nameOfProperty);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    
    
     
     
    public void yesButtonOnAction(){
        this.wantToBuy = true;
        this.finish.setValue(true);
    }
    
    public void noButtonOnAction(){
        this.wantToBuy = false;
        this.finish.setValue(true);
    }

    public BooleanProperty getFinish() {
        return finish;
	}

	public boolean isWantToBuy() {
		return wantToBuy;
	}

	public void setWantToBuy(boolean wantToBuy) {
		this.wantToBuy = wantToBuy;
	}

}
