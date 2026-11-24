package com.cardchess;

import com.cardchess.controller.GameController;
import com.cardchess.model.Attack;
import com.cardchess.model.AttackCard;
import com.cardchess.model.Board;
import com.cardchess.model.CPUPlayer;
import com.cardchess.model.Effect;
import com.cardchess.model.EffectType;
import com.cardchess.model.HumanPlayer;
import com.cardchess.model.Position;
import com.cardchess.model.Unit;
import com.cardchess.model.UnitCard;
import com.cardchess.model.UnitOnBoard;
import java.util.Arrays;
import java.util.Collections;

public class Main {
    public static void main(String[] args) {
        Attack slash = new Attack("Slash", "A basic unit attack", 1, Collections.emptyList());
        Unit scout = new Unit("Scout", 4, Collections.singletonList(slash), null);
        Unit guardian = new Unit("Guardian", 5, Collections.singletonList(slash), null);

        UnitCard scoutCard = new UnitCard("Scout", scout);
        AttackCard firebolt = new AttackCard("Firebolt",
                new Attack("Firebolt", "Deals damage and burns the target", 2,
                        Collections.singletonList(new Effect(EffectType.DAMAGE, 1))));
        UnitCard guardianCard = new UnitCard("Guardian", guardian);

        HumanPlayer player1 = new HumanPlayer(Arrays.asList(scoutCard, firebolt));
        CPUPlayer player2 = new CPUPlayer(Collections.singletonList(guardianCard));
        GameController game = new GameController(player1, player2, new Board(4, 4));

        player1.takeTurn(game);
        System.out.println("Player 1 draws: " + player1.getHand().get(0).getDisplayName());
        UnitOnBoard deployedScout = game.playUnitCard(player1, scoutCard, new Position(0, 0));
        System.out.println("Player 1 deploys Scout at " + deployedScout.getPosition());
        game.endTurn();

        player2.takeTurn(game);
        System.out.println("Player 2 draws: " + player2.getHand().get(0).getDisplayName());
        UnitOnBoard deployedGuardian = game.playUnitCard(player2, guardianCard, new Position(3, 3));
        System.out.println("Player 2 deploys Guardian at " + deployedGuardian.getPosition());
        game.endTurn();

        player1.takeTurn(game);
        System.out.println("Player 1 begins turn " + game.getTurnNumber()
                + " and draws: " + player1.getHand().get(0).getDisplayName());
        game.playAttackCard(player1, firebolt, deployedScout, deployedGuardian);
        System.out.println("Firebolt hits Guardian; remaining health: "
                + deployedGuardian.getHealth());
        game.endTurn();
    }
}
