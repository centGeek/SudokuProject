package org.example;

import java.util.*;

public abstract class SudokuPart implements SudokuObserver {


    protected List<SudokuField> sudokuFields;

    public void setSudokuFields(List<SudokuField> sudokuFields) {
        this.sudokuFields = sudokuFields;
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


