package org.example;

public class SudokuRow extends SudokuPart {
    public boolean update(SudokuBoard sudokuBoard, int row, int column) {
        return sudokuBoard.getRow(row).verify();
    }
}
