package org.example;

public class SudokuBox extends SudokuPart {
    public boolean update(SudokuBoard sudokuBoard, int row, int column) {
        return sudokuBoard.getBox(row, column).verify();
    }
}
