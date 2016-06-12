
package com.monopoly.scenes;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class BuyingHousePopupController implements Initializable {

    final private BooleanProperty finish = new SimpleBooleanProperty(this, "finish buying popup");
    @FXML
    private Button yesButton;
    
    @FXML
    private Button noButton;
    
    @FXML
    private Label cityLabel;
    
    @FXML
    private Label priceLabel;
    
    private boolean wantToBuy;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    

    public void setCityLabel(String cityLabel) {
        this.cityLabel.setText(cityLabel);
    }

    public void setPriceLabel(String priceLabel) {
        this.priceLabel.setText(priceLabel);
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
