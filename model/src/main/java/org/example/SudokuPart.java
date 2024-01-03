package org.example;

import com.google.common.base.Objects;
import org.example.exceptions.SudokuFieldCloneFailureException;

import java.util.*;

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

    protected List<SudokuField> getSudokuFieldsCopy() {
        List<SudokuField> fieldsList = new ArrayList<>();
        for (int i = 0; i < this.getSudokuFields().size(); i++) {
            fieldsList.add(this.getSudokuFields().get(i).clone());
        }
        return fieldsList;
    }

    @Override
    public SudokuPart clone() {
        SudokuPart sudokuPart;
        try {
            sudokuPart = (SudokuPart) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new SudokuFieldCloneFailureException();
        }
        List<SudokuField> fieldsList = getSudokuFieldsCopy();
        sudokuPart.setSudokuFields(fieldsList);
        return sudokuPart;
    }
}


