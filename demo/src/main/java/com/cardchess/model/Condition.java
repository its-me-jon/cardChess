package com.cardchess.model;

@FunctionalInterface
public interface Condition {
    boolean matches(Card card);
}
