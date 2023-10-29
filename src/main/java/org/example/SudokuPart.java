package org.example;

import java.util.*;

public abstract class SudokuPart {
    protected List<SudokuField> sudokuFields;

    public void setSudokuFields(List<SudokuField> sudokuFields) {
        this.sudokuFields = sudokuFields;
    }

    public boolean verify() {
        Set<Integer> uniqueValues = new HashSet<>();
        for (SudokuField field : sudokuFields) {
            int fieldValue = field.getFieldValue();
            if (fieldValue != 0 && !uniqueValues.add(fieldValue)) {
                return false;
            }
        }
        return true;
    }
}


