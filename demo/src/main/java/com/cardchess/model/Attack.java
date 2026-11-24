package com.cardchess.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Attack {
    private final String displayName;
    private final String description;
    private final int damage;
    private final List<Effect> effects;

    public Attack(String displayName, String description, int damage, List<Effect> effects) {
        if (displayName == null || displayName.trim().isEmpty()) {
            throw new IllegalArgumentException("Attack display name cannot be blank");
        }
        if (damage < 0) {
            throw new IllegalArgumentException("Attack damage cannot be negative");
        }
        this.displayName = displayName;
        this.description = description == null ? "" : description;
        this.damage = damage;
        this.effects = copyEffects(effects);
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getDescription() {
        return description;
    }

    public int getDamage() {
        return damage;
    }

    public List<Effect> getEffects() {
        return Collections.unmodifiableList(effects);
    }

    static List<Effect> copyEffects(List<Effect> effects) {
        if (effects == null || effects.contains(null)) {
            throw new IllegalArgumentException("Effects cannot be null or contain null values");
        }
        return new ArrayList<>(effects);
    }
}
