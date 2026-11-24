package com.cardchess.model;

public class UnitOnBoard {
    private final int id;
    private int health;
    private Position position;
    private final Player owner;
    private final Unit unit;

    public UnitOnBoard(int id, Unit unit, Player owner, Position position) {
        if (id < 0 || unit == null || owner == null || position == null) {
            throw new IllegalArgumentException("A board unit requires a valid id, unit, owner, and position");
        }
        this.id = id;
        this.unit = unit;
        this.owner = owner;
        this.position = position;
        this.health = unit.getMaxHealth();
    }

    public int getId() {
        return id;
    }

    public int getHealth() {
        return health;
    }

    public Position getPosition() {
        return position;
    }

    public Player getOwner() {
        return owner;
    }

    public Unit getUnit() {
        return unit;
    }

    public void takeDamage(int damage) {
        if (damage < 0) {
            throw new IllegalArgumentException("Damage cannot be negative");
        }
        health = Math.max(0, health - damage);
    }

    public void heal(int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Healing cannot be negative");
        }
        health = (int) Math.min(unit.getMaxHealth(), (long) health + amount);
    }

    public void setPosition(Position position) {
        if (position == null) {
            throw new IllegalArgumentException("Position cannot be null");
        }
        this.position = position;
    }
}
