package org.example;

import java.util.List;

public class SudokuColumn extends SudokuPart {
    @Override
    public String toString() {
        return "SudokuColumn{" + "sudokuFields=" + sudokuFields + '}';
    }

    @Override
    public SudokuColumn clone() {
        SudokuColumn sudokuColumn;
        try {
            sudokuColumn = (SudokuColumn) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
        List<SudokuField> fieldsList = getSudokuFieldsCopy();
        sudokuColumn.setSudokuFields(fieldsList);
        return sudokuColumn;
    }
}
