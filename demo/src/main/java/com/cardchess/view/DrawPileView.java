package com.cardchess.view;

import com.cardchess.model.Player;

public class DrawPileView extends PileView {
    public DrawPileView(Player player) {
        super(player);
    }

    public int getCardCount() {
        return getPlayer().getDrawPile().size();
    }
}
