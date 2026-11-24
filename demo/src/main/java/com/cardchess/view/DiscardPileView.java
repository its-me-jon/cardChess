package com.cardchess.view;

import com.cardchess.model.Card;
import com.cardchess.model.Player;

public class DiscardPileView extends PileView {
    public DiscardPileView(Player player) {
        super(player);
    }

    public CardView getTopCard() {
        if (getPlayer().getDiscardPile().isEmpty()) {
            return null;
        }
        Card card = getPlayer().getDiscardPile().get(getPlayer().getDiscardPile().size() - 1);
        return new CardView(card);
    }
}
