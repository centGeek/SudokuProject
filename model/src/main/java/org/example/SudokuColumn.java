package org.example;

public class SudokuColumn extends SudokuPart {
    @Override
    public String toString() {
        return "SudokuColumn{" + "sudokuFields=" + sudokuFields + '}';
    }

    @Override
    public SudokuColumn clone() {
        return (SudokuColumn) super.clone();

    }
}
