package com.cardchess.model;

public class AttackCard extends Card {
    private final Attack attack;

    public AttackCard(String displayName, Attack attack) {
        super(displayName);
        if (attack == null) {
            throw new IllegalArgumentException("Attack cannot be null");
        }
        this.attack = attack;
    }

    public Attack getAttack() {
        return attack;
    }
}
