/*
 */
package com.monopoly.scenes;

import com.monopoly.client.player.PlayerManager;
import com.monopoly.client.player.PlayerView;
import javafx.scene.Scene;
import javafx.scene.layout.FlowPane;

public class PlayersPane extends Scene {

    final private FlowPane root;

    public PlayersPane(PlayerManager playersManager) {
        super(new FlowPane(), 500, 400);
        root = (FlowPane) getRoot();
        root.setHgap(10);
        root.setVgap(10);
        playersManager.getPlayers().forEach(
                (player) -> root.getChildren().add(new PlayerView(player.getPlayerName(), player.getPlayerImage(), player.getPlayerIcon())));
        setRoot(root);
    }
}
