package org.example;

public class SudokuColumn extends SudokuPart {
    public boolean update(SudokuBoard sudokuBoard, int row, int column) {
        return sudokuBoard.getColumn(column).verify();
    }
}
