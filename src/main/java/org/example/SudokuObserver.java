package org.example;

public interface SudokuObserver {
    boolean checkBoard(SudokuBoard sudokuBoard, int x, int y);

    boolean setAndCheck(SudokuBoard sudokuBoard, int x, int y, int value);

}
