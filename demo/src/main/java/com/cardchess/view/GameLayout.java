package com.cardchess.view;

import com.cardchess.model.Board;
import com.cardchess.model.Player;

public class GameLayout {
    private final PlayerArea player1Area;
    private final PlayerArea player2Area;
    private final BoardView boardView;

    public GameLayout(Player player1, Player player2, Board board) {
        this.player1Area = new PlayerArea(player1);
        this.player2Area = new PlayerArea(player2);
        this.boardView = new BoardView(board);
    }

    public PlayerArea getPlayer1Area() {
        return player1Area;
    }

    public PlayerArea getPlayer2Area() {
        return player2Area;
    }

    public BoardView getBoardView() {
        return boardView;
    }
}
