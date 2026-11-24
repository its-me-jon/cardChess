package com.cardchess.controller;

import com.cardchess.model.Attack;
import com.cardchess.model.AttackCard;
import com.cardchess.model.Board;
import com.cardchess.model.Card;
import com.cardchess.model.Effect;
import com.cardchess.model.Player;
import com.cardchess.model.Position;
import com.cardchess.model.SkillCard;
import com.cardchess.model.UnitCard;
import com.cardchess.model.Unit;
import com.cardchess.model.UnitOnBoard;
import com.cardchess.view.GameLayout;
import java.util.Collections;
import java.util.List;

public class GameController {
    private final Player player1;
    private final Player player2;
    private Player currentPlayer;
    private final Board board;
    private final GameLayout gameLayout;
    private int nextUnitId;
    private int turnNumber;
    private boolean turnStarted;

    public GameController(Player player1, Player player2, Board board) {
        this(player1, player2, board, new GameLayout(player1, player2, board));
    }

    public GameController(Player player1, Player player2, Board board, GameLayout gameLayout) {
        if (player1 == null || player2 == null || player1 == player2
                || board == null || gameLayout == null) {
            throw new IllegalArgumentException("Controller requires two players, a board, and a layout");
        }
        this.player1 = player1;
        this.player2 = player2;
        this.board = board;
        this.gameLayout = gameLayout;
        this.nextUnitId = 1;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public Board getBoard() {
        return board;
    }

    public GameLayout getGameLayout() {
        return gameLayout;
    }

    public int getTurnNumber() {
        return turnNumber;
    }

    public UnitOnBoard createUnit(Unit unit, Player owner, Position position) {
        requirePlayer(owner);
        if (unit == null || !board.isValidPosition(position) || board.getUnitAt(position) != null) {
            throw new IllegalArgumentException("Unit or position is invalid or the position is occupied");
        }
        UnitOnBoard instance = unit.createInstance(owner, generateUnitId(), position);
        board.placeUnit(instance, position);
        return instance;
    }

    public void moveUnit(UnitOnBoard unit, Position position) {
        requireUnitOnBoard(unit);
        board.moveUnit(unit, position);
    }

    public void removeUnit(UnitOnBoard unit) {
        requireUnitOnBoard(unit);
        board.removeUnit(unit.getPosition());
    }

    public void attackUnit(UnitOnBoard attacker, UnitOnBoard target, Attack attack) {
        requireUnitOnBoard(attacker);
        requireUnitOnBoard(target);
        if (attack == null || !attacker.getUnit().getAttacks().contains(attack)) {
            throw new IllegalArgumentException("Attack is not available to the attacking unit");
        }
        performAttack(attacker, target, attack);
    }

    private void performAttack(UnitOnBoard attacker, UnitOnBoard target, Attack attack) {
        requireUnitOnBoard(attacker);
        requireUnitOnBoard(target);
        if (attacker == target || attacker.getOwner() == target.getOwner()) {
            throw new IllegalArgumentException("An attack must target an opposing unit");
        }
        target.takeDamage(attack.getDamage());
        applyEffects(attack.getEffects(), target);
        if (target.getHealth() == 0) {
            board.removeUnit(target.getPosition());
        }
    }

    public void playCard(Player player, Card card) {
        if (card instanceof UnitCard) {
            playUnitCard(player, (UnitCard) card, findOpenPosition());
        } else if (card instanceof AttackCard || card instanceof SkillCard) {
            throw new IllegalArgumentException(
                    "Attack and skill cards require a target; use playAttackCard or playSkillCard");
        } else {
            throw new IllegalArgumentException("Unsupported card type");
        }
    }

    public UnitOnBoard playUnitCard(Player player, UnitCard card, Position position) {
        requireCurrentPlayer(player);
        requireCardInHand(player, card);
        UnitOnBoard unit = createUnit(card.getUnit(), player, position);
        player.discardFromHand(card);
        return unit;
    }

    public void playAttackCard(Player player, AttackCard card,
            UnitOnBoard attacker, UnitOnBoard target) {
        requireCurrentPlayer(player);
        requireCardInHand(player, card);
        if (attacker == null || attacker.getOwner() != player) {
            throw new IllegalArgumentException("Attacker must belong to the player");
        }
        performAttack(attacker, target, card.getAttack());
        player.discardFromHand(card);
    }

    public void playSkillCard(Player player, SkillCard card, UnitOnBoard target) {
        requireCurrentPlayer(player);
        requireCardInHand(player, card);
        requireUnitOnBoard(target);
        applyEffects(card.getSkill().getEffects(), target);
        if (target.getHealth() == 0) {
            board.removeUnit(target.getPosition());
        }
        player.discardFromHand(card);
    }

    public void startTurn(Player player) {
        requirePlayer(player);
        if (currentPlayer != null && currentPlayer != player) {
            throw new IllegalStateException("It is not this player's turn");
        }
        if (currentPlayer == null) {
            currentPlayer = player;
        }
        if (turnStarted) {
            throw new IllegalStateException("This turn has already started");
        }
        if (player == player1) {
            turnNumber++;
        }
        turnStarted = true;
        player.drawCard(1, Collections.emptyList());
    }

    public void endTurn() {
        if (currentPlayer == null || !turnStarted) {
            throw new IllegalStateException("No turn is currently active");
        }
        currentPlayer = currentPlayer == player1 ? player2 : player1;
        turnStarted = false;
    }

    private int generateUnitId() {
        return nextUnitId++;
    }

    private void requirePlayer(Player player) {
        if (player != player1 && player != player2) {
            throw new IllegalArgumentException("Player does not belong to this game");
        }
    }

    private void requireCurrentPlayer(Player player) {
        requirePlayer(player);
        if (currentPlayer != player || !turnStarted) {
            throw new IllegalStateException("It is not this player's turn");
        }
    }

    private void requireCardInHand(Player player, Card card) {
        if (card == null || !player.hasCardInHand(card)) {
            throw new IllegalArgumentException("Card is not in this player's hand");
        }
    }

    private void requireUnitOnBoard(UnitOnBoard unit) {
        if (unit == null || !board.isValidPosition(unit.getPosition())
                || board.getUnitAt(unit.getPosition()) != unit) {
            throw new IllegalArgumentException("Unit is not on this board");
        }
    }

    private Position findOpenPosition() {
        for (int row = 0; row < board.getRows(); row++) {
            for (int column = 0; column < board.getColumns(); column++) {
                Position position = new Position(row, column);
                if (board.getUnitAt(position) == null) {
                    return position;
                }
            }
        }
        throw new IllegalStateException("There are no open positions on the board");
    }

    private void applyEffects(List<Effect> effects, UnitOnBoard target) {
        for (Effect effect : effects) {
            effect.apply(target);
            if (target.getHealth() == 0) {
                return;
            }
        }
    }
}
