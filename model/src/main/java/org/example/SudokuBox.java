package org.example;

import java.util.List;

public class SudokuBox extends SudokuPart {
    @Override
    public String toString() {
        return "SudokuBox{" + "sudokuFields=" + sudokuFields + '}';
    }

    @Override
    public SudokuBox clone() {
        SudokuBox sudokuBox;
        try {
            sudokuBox = (SudokuBox) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
        List<SudokuField> fieldsList = getSudokuFieldsCopy();
        sudokuBox.setSudokuFields(fieldsList);
        return sudokuBox;
    }
}
