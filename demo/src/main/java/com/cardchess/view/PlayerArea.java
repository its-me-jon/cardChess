package com.cardchess.view;

import com.cardchess.model.Player;

public class PlayerArea {
    private final DrawPileView drawPile;
    private final HandView hand;
    private final DiscardPileView discardPile;

    public PlayerArea(Player player) {
        this.drawPile = new DrawPileView(player);
        this.hand = new HandView(player);
        this.discardPile = new DiscardPileView(player);
    }

    public DrawPileView getDrawPile() {
        return drawPile;
    }

    public HandView getHand() {
        return hand;
    }

    public DiscardPileView getDiscardPile() {
        return discardPile;
    }
}
