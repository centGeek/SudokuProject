package org.example;

import java.util.Random;

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

    public static SudokuBoard copySudokuBoard() {
        SudokuSolver sudokuSolver = new BacktrackingSudokuSolver();
        SudokuBoard sudokuBoard = new SudokuBoard(sudokuSolver);
        sudokuBoard.solveGame();
        return sudokuBoard.clone();
    }

    public static SudokuBoard deleteRandomNumbers(SudokuBoard sudokuBoard, String buttonName) {
        int amount = howManyDeletedFields(buttonName);
        Random random = new Random();

        int deleted = 0;
        while (deleted < amount) {
            int row = random.nextInt(9);
            int col = random.nextInt(9);

            if (sudokuBoard.get(row, col) != 0) {
                sudokuBoard.set(row, col, 0);
                deleted++;
            }
        }

        return sudokuBoard;
    }

    private static int howManyDeletedFields(String buttonName) {
        return switch (buttonName) {
            case "Easy" -> DifficultyLevel.EASY.getValue();
            case "Normal" -> DifficultyLevel.MEDIUM.getValue();
            case "Hard" -> DifficultyLevel.HARD.getValue();
            default -> throw new RuntimeException("Something unexpected happened");
        };
    }
}
