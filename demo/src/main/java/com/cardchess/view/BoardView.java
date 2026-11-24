package com.cardchess.view;

import com.cardchess.model.Board;

public class BoardView {
    private final Board board;

    public BoardView(Board board) {
        if (board == null) {
            throw new IllegalArgumentException("Board cannot be null");
        }
        this.board = board;
    }

    public Board getBoard() {
        return board;
    }
}
