package org.example;

import java.util.Locale;
import java.util.Random;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;





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

    public static SudokuBoard startGame() {
        SudokuSolver sudokuSolver = new BacktrackingSudokuSolver();
        SudokuBoard sudokuBoard = new SudokuBoard(sudokuSolver);
        sudokuBoard.solveGame();
        return sudokuBoard.clone();
    }

    public static SudokuBoard deleteRandomNumbers(SudokuBoard sudokuBoard, String buttonName, Locale locale) {
        Logger logger = LoggerFactory.getLogger(DifficultyLevel.class);
        ResourceBundle resourceBundle = ResourceBundle.getBundle("logger", locale);
        String difficultyLevel = resourceBundle.getString(buttonName + "Difficulty");

        logger.info(difficultyLevel);
        SudokuBoard clone = sudokuBoard.clone();
        int amount = howManyDeletedFields(buttonName);
        Random random = new Random();
        int deleted = 0;
        while (deleted < amount) {
            int row = random.nextInt(9);
            int col = random.nextInt(9);

            if (clone.get(row, col) != 0) {
                clone.set(row, col, 0);
                clone.getSudokuField(row, col).setEditable(true);
                logger.info(resourceBundle.getString("deletedField") + " [" + row + "][" + col + "]");
                deleted++;
            }
        }

        return clone;
    }

    private static int howManyDeletedFields(String buttonName) {
        return switch (buttonName) {
            case "button1" -> DifficultyLevel.EASY.getValue();
            case "button2" -> DifficultyLevel.MEDIUM.getValue();
            case "button3" -> DifficultyLevel.HARD.getValue();
            default -> throw new RuntimeException("Something unexpected happened");
        };
    }
}
