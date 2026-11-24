package com.cardchess.model;

public class Board {
    private final int rows;
    private final int columns;
    private final UnitOnBoard[][] units;

    public Board(int rows, int columns) {
        if (rows <= 0 || columns <= 0) {
            throw new IllegalArgumentException("Board dimensions must be positive");
        }
        this.rows = rows;
        this.columns = columns;
        this.units = new UnitOnBoard[rows][columns];
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    public UnitOnBoard getUnitAt(Position position) {
        requireValidPosition(position);
        return units[position.getRow()][position.getColumn()];
    }

    public void placeUnit(UnitOnBoard unit, Position position) {
        if (unit == null) {
            throw new IllegalArgumentException("Unit cannot be null");
        }
        requireValidPosition(position);
        if (getUnitAt(position) != null) {
            throw new IllegalStateException("Position is already occupied: " + position);
        }
        if (unit.getPosition() != null && getUnitAt(unit.getPosition()) == unit) {
            throw new IllegalStateException("Unit is already on this board");
        }
        unit.setPosition(position);
        units[position.getRow()][position.getColumn()] = unit;
    }

    public UnitOnBoard removeUnit(Position position) {
        requireValidPosition(position);
        UnitOnBoard removed = units[position.getRow()][position.getColumn()];
        units[position.getRow()][position.getColumn()] = null;
        return removed;
    }

    public void moveUnit(UnitOnBoard unit, Position position) {
        if (unit == null) {
            throw new IllegalArgumentException("Unit cannot be null");
        }
        requireValidPosition(position);
        Position oldPosition = unit.getPosition();
        if (oldPosition == null || !isValidPosition(oldPosition)
                || getUnitAt(oldPosition) != unit) {
            throw new IllegalStateException("Unit is not on this board");
        }
        if (!oldPosition.equals(position) && getUnitAt(position) != null) {
            throw new IllegalStateException("Position is already occupied: " + position);
        }
        units[oldPosition.getRow()][oldPosition.getColumn()] = null;
        units[position.getRow()][position.getColumn()] = unit;
        unit.setPosition(position);
    }

    public boolean isValidPosition(Position position) {
        return position != null
                && position.getRow() >= 0 && position.getRow() < rows
                && position.getColumn() >= 0 && position.getColumn() < columns;
    }

    private void requireValidPosition(Position position) {
        if (!isValidPosition(position)) {
            throw new IllegalArgumentException("Position is outside the board: " + position);
        }
    }
}
