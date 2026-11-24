package com.cardchess.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Unit {
    private final String displayName;
    private final int maxHealth;
    private final List<Attack> attacks;
    private final Ability ability;

    public Unit(String displayName, int maxHealth, List<Attack> attacks, Ability ability) {
        if (displayName == null || displayName.trim().isEmpty()) {
            throw new IllegalArgumentException("Unit display name cannot be blank");
        }
        if (maxHealth <= 0) {
            throw new IllegalArgumentException("Unit health must be positive");
        }
        if (attacks == null || attacks.contains(null)) {
            throw new IllegalArgumentException("Attacks cannot be null or contain null values");
        }
        this.displayName = displayName;
        this.maxHealth = maxHealth;
        this.attacks = new ArrayList<>(attacks);
        this.ability = ability;
    }

    public String getDisplayName() {
        return displayName;
    }

    public List<Attack> getAttacks() {
        return Collections.unmodifiableList(attacks);
    }

    public Ability getAbility() {
        return ability;
    }

    public UnitOnBoard createInstance(Player owner, int id, Position position) {
        return new UnitOnBoard(id, this, owner, position);
    }

    public int getMaxHealth() {
        return maxHealth;
    }
}
