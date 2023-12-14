package org.example;

import java.util.List;

public class SudokuRow extends SudokuPart {
    @Override
    public String toString() {
        return "SudokuRow{" + "sudokuFields=" + sudokuFields + '}';
    }

    @Override
    public SudokuRow clone() {
        SudokuRow sudokuRow;
        try {
            sudokuRow = (SudokuRow) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
        List<SudokuField> fieldsList = getSudokuFieldsCopy();
        sudokuRow.setSudokuFields(fieldsList);
        return sudokuRow;
    }
}
