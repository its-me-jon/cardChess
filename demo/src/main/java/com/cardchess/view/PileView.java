package com.cardchess.view;

import com.cardchess.model.Player;

public abstract class PileView {
    private final Player player;

    protected PileView(Player player) {
        if (player == null) {
            throw new IllegalArgumentException("Player cannot be null");
        }
        this.player = player;
    }

    protected Player getPlayer() {
        return player;
    }
}
