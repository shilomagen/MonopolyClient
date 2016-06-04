package com.monopoly.data;

import com.monopoly.client.player.Player;

public class GoToCard extends Card {
	String cellToGo;

	public GoToCard(String cardText, int cardCode, String cellToGo) {
		super(cardText, cardCode);
		this.cellToGo = cellToGo;
	}

	@Override
	public void surpriseAction(Player currentPlayer) {
//		if (this.cellToGo.equals("START")) {
//			GameEngine.addEventToEngine(EventTypes.RETURN_CARD_TO_SURPRISE_DECK);
//			GameEngine.addEventToEngine(EventTypes.GO_TO_START_CELL);
//
//		} else if (this.cellToGo.equals("NEXT_SURPRISE")) {
//			GameEngine.addEventToEngine(EventTypes.RETURN_CARD_TO_SURPRISE_DECK);
//			GameEngine.addEventToEngine(EventTypes.GO_TO_NEXT_SURPRISE);
//
//		}
	}

	@Override
	public void warrantAction(Player currentPlayer) {
//		if (this.cellToGo.equals("JAIL")) {
//			GameEngine.addEventToEngine(EventTypes.RETURN_CARD_TO_WARRANT_DECK);
//			GameEngine.addEventToEngine(EventTypes.ON_GO_TO_JAIL);
//
//		} else if (this.cellToGo.equals("NEXT_WARRANT")) {
//			GameEngine.addEventToEngine(EventTypes.RETURN_CARD_TO_WARRANT_DECK);
//			GameEngine.addEventToEngine(EventTypes.GO_TO_NEXT_WARRANT);
//			
//
//		}
	}

	public String toString() {
		return String.format(this.cardText, this.cellToGo);
	}

}
