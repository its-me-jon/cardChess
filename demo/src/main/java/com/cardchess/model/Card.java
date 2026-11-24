package com.cardchess.model;

public abstract class Card {
    private final String displayName;

    protected Card(String displayName) {
        if (displayName == null || displayName.trim().isEmpty()) {
            throw new IllegalArgumentException("Card display name cannot be blank");
        }
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
