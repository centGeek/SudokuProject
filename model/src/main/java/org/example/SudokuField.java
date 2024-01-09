package org.example;

import com.google.common.base.Objects;
import org.example.exceptions.SudokuBoardCloneFailureException;
import org.example.exceptions.SudokuFieldNullValueException;
import org.example.exceptions.SudokuFieldWrongException;

import java.io.Serializable;

public class SudokuField implements Serializable, Comparable<SudokuField>, Cloneable {

    private int value;
    private final SudokuObserver sudokuObserver;


    public SudokuField(SudokuObserver sudokuObserver) {
        this.sudokuObserver = sudokuObserver;
    }

    public boolean notifyObservers() {
        return sudokuObserver.verify(sudokuObserver.getLastUpdatedRow(), sudokuObserver.getLastUpdatedColumn());
    }

    public void setFieldValue(int value) {
        if (value < 0) {
            throw new SudokuFieldWrongException("tooSmallValue");
        }
        if (value > 9) {
            throw new SudokuFieldWrongException("tooBigValue");
        }
        this.value = value;
    }

    public int getFieldValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SudokuField that = (SudokuField) o;
        return Objects.equal(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }

    @Override
    public String toString() {
        return "SudokuField{" + "value=" + value + ", sudokuObserver=" + sudokuObserver + '}';
    }

    @Override
    public int compareTo(SudokuField o) {
        if (o == null) {
            throw new SudokuFieldNullValueException("nullGiven");
        }
        return this.value - o.getFieldValue();
    }

    @Override
    public SudokuField clone() {

        try {
            return (SudokuField) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new SudokuBoardCloneFailureException();
        }
    }
}
