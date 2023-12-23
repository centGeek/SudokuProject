package org.example;

public class SudokuRow extends SudokuPart {
    @Override
    public String toString() {
        return "SudokuRow{" + "sudokuFields=" + sudokuFields + '}';
    }

    @Override
    public SudokuRow clone() {
        return (SudokuRow) super.clone();
    }
}
