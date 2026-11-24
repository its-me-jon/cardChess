package com.cardchess.model;

import java.util.Collections;
import java.util.List;

public class Ability {
    private final String displayName;
    private final String description;
    private final List<Effect> effects;

    public Ability(String displayName, String description, List<Effect> effects) {
        if (displayName == null || displayName.trim().isEmpty()) {
            throw new IllegalArgumentException("Ability display name cannot be blank");
        }
        this.displayName = displayName;
        this.description = description == null ? "" : description;
        this.effects = Attack.copyEffects(effects);
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getDescription() {
        return description;
    }

    public List<Effect> getEffects() {
        return Collections.unmodifiableList(effects);
    }
}
