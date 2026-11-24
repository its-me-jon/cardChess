package com.cardchess.model;

public class UnitCard extends Card {
    private final Unit unit;

    public UnitCard(String displayName, Unit unit) {
        super(displayName);
        if (unit == null) {
            throw new IllegalArgumentException("Unit cannot be null");
        }
        this.unit = unit;
    }

    public Unit getUnit() {
        return unit;
    }
}
