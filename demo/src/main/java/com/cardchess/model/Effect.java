package com.cardchess.model;

public class Effect {
    private final EffectType type;
    private final int value;

    public Effect(EffectType type, int value) {
        if (type == null) {
            throw new IllegalArgumentException("Effect type cannot be null");
        }
        if (value < 0) {
            throw new IllegalArgumentException("Effect value cannot be negative");
        }
        this.type = type;
        this.value = value;
    }

    public EffectType getType() {
        return type;
    }

    public int getValue() {
        return value;
    }

    public void apply(UnitOnBoard target) {
        if (target == null) {
            throw new IllegalArgumentException("Effect target cannot be null");
        }
        if (type == EffectType.DAMAGE) {
            target.takeDamage(value);
        } else {
            target.heal(value);
        }
    }
}
