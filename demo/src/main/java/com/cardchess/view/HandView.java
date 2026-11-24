package com.cardchess.view;

import com.cardchess.model.Card;
import com.cardchess.model.Player;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HandView {
    private final Player player;

    public HandView(Player player) {
        if (player == null) {
            throw new IllegalArgumentException("Player cannot be null");
        }
        this.player = player;
    }

    public List<CardView> getCards() {
        List<CardView> cards = new ArrayList<>();
        for (Card card : player.getHand()) {
            cards.add(new CardView(card));
        }
        return Collections.unmodifiableList(cards);
    }
}
