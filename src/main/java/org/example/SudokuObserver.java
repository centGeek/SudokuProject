package org.example;

public interface SudokuObserver {
    boolean update(SudokuBoard sudokuBoard, int x, int y);

}
