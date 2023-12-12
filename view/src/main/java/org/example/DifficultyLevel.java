package org.example;

public enum DifficultyLevel {
    EASY(10),
    MEDIUM(15),
    HARD(20);
    private final int value;

    DifficultyLevel(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
