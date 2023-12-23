package org.example;

public class SudokuBox extends SudokuPart {
    @Override
    public String toString() {
        return "SudokuBox{" + "sudokuFields=" + sudokuFields + '}';
    }

    @Override
    public SudokuBox clone() {
        return (SudokuBox) super.clone();
    }
}
