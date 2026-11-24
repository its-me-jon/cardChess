package com.cardchess.view;

import com.cardchess.model.Card;

public class CardView {
    private final Card card;

    public CardView(Card card) {
        if (card == null) {
            throw new IllegalArgumentException("Card cannot be null");
        }
        this.card = card;
    }

    public Card getCard() {
        return card;
    }
}
