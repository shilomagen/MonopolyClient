package com.monopoly.data;

import com.monopoly.client.player.Player;
//import com.monopoly.engine.GameController;
public class MontaryCard extends Card {
	private int sum;

	public MontaryCard(String cardText, int cardCode, int sum) {
		super(cardText, cardCode);
		this.sum = sum;
	}

	public String toString() {
		return String.format(this.cardText, sum);
	}

	@Override
	public void surpriseAction(Player currentPlayer) {
//		if (this.cardCode == 1) {
//			GameEngine.addEventToEngine(EventTypes.RETURN_CARD_TO_SURPRISE_DECK);
//			GameEngine.addEventToEngine(EventTypes.TAKE_MONEY_FROM_ALL_PLAYERS);
//
//		} else if (this.cardCode == 2) {
//			GameEngine.addEventToEngine(EventTypes.RETURN_CARD_TO_SURPRISE_DECK);
//			GameEngine.addEventToEngine(EventTypes.TAKE_MONEY_FROM_JACKPOT);
//		}
	}

	@Override
	public void warrantAction(Player currentPlayer) {
//		if (this.cardCode == 1) {
//			GameEngine.addEventToEngine(EventTypes.RETURN_CARD_TO_WARRANT_DECK);
//			GameEngine.addEventToEngine(EventTypes.PAY_TO_ALL_PLAYERS);
//
//		} else if (this.cardCode == 2) {
//			GameEngine.addEventToEngine(EventTypes.RETURN_CARD_TO_WARRANT_DECK);
//			GameEngine.addEventToEngine(EventTypes.PAY_TO_JACKPOT);
//		}
	}

	public int getSum() {
		return this.sum;
	}

}
