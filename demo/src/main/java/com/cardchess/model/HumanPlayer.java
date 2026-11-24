package com.cardchess.model;

import com.cardchess.controller.GameController;
import java.util.List;

public class HumanPlayer extends Player {
    public HumanPlayer(List<Card> deck) {
        super(deck);
    }

    @Override
    public void takeTurn(GameController controller) {
        if (controller == null) {
            throw new IllegalArgumentException("Controller cannot be null");
        }
        controller.startTurn(this);
    }
}
