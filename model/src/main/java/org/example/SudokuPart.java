package org.example;

import com.google.common.base.Objects;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class SudokuPart implements Cloneable {
    private final int size = 9;
    protected List<SudokuField> sudokuFields = Arrays.asList(new SudokuField[size]);

    public void setSudokuFields(List<SudokuField> sudokuFields) {
        this.sudokuFields = Collections.unmodifiableList(sudokuFields);
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SudokuPart that = (SudokuPart) o;
        return Objects.equal(sudokuFields, that.sudokuFields);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(sudokuFields);
    }

    @Override
    public SudokuPart clone() {
        try {
            return (SudokuPart) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}


