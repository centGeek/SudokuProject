package org.example;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class SudokuPart {
    private final int size = 9;
    protected List<SudokuField> sudokuFields = Arrays.asList(new SudokuField[size]);

    public void setSudokuFields(List<SudokuField> sudokuFields) {
        this.sudokuFields = sudokuFields;
    }

    public List<SudokuField> getSudokuFields() {
        return sudokuFields;
    }

    public boolean verify() {
        int num = 0;
        for (SudokuField field : sudokuFields) {
            if (field.getFieldValue() != 0) {
                num = field.getFieldValue();
            }
        }
        if (num == 0) {
            return true;
        } else {
            Set<Integer> sudokuFieldSet = new HashSet<>();
            for (SudokuField field : sudokuFields) {
                if (!sudokuFieldSet.add(field.getFieldValue()) && field.getFieldValue() != 0) {
                    return false;
                }
            }
            return true;
        }
    }

}


