package com.cardchess.model;

import com.cardchess.controller.GameController;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class Player {
    private final List<Card> deck;
    private final List<Card> hand = new ArrayList<>();
    private final List<Card> drawPile;
    private final List<Card> discardPile = new ArrayList<>();

    protected Player(List<Card> deck) {
        if (deck == null || deck.contains(null)) {
            throw new IllegalArgumentException("Deck cannot be null or contain null cards");
        }
        this.deck = new ArrayList<>(deck);
        this.drawPile = new ArrayList<>(deck);
    }

    public List<Card> getDeck() {
        return Collections.unmodifiableList(deck);
    }

    public List<Card> getHand() {
        return Collections.unmodifiableList(hand);
    }

    public List<Card> getDrawPile() {
        return Collections.unmodifiableList(drawPile);
    }

    public List<Card> getDiscardPile() {
        return Collections.unmodifiableList(discardPile);
    }

    public void drawCard(int numCards, List<Condition> conditions) {
        if (numCards < 0) {
            throw new IllegalArgumentException("Number of cards cannot be negative");
        }
        if (conditions == null || conditions.contains(null)) {
            throw new IllegalArgumentException("Conditions cannot be null or contain null values");
        }

        int drawn = 0;
        while (drawn < numCards && !drawPile.isEmpty()) {
            Card card = drawPile.remove(0);
            if (matchesConditions(card, conditions)) {
                hand.add(card);
                drawn++;
            } else {
                discardPile.add(card);
            }
        }
    }

    public void retrieveDiscardedCard(Card card) {
        if (card == null || !discardPile.remove(card)) {
            throw new IllegalArgumentException("Card is not in this player's discard pile");
        }
        hand.add(card);
    }

    public abstract void takeTurn(GameController controller);

    public boolean hasCardInHand(Card card) {
        return hand.contains(card);
    }

    public void discardFromHand(Card card) {
        if (card == null || !hand.remove(card)) {
            throw new IllegalArgumentException("Card is not in this player's hand");
        }
        discardPile.add(card);
    }

    private boolean matchesConditions(Card card, List<Condition> conditions) {
        for (Condition condition : conditions) {
            if (!condition.matches(card)) {
                return false;
            }
        }
        return true;
    }
}
