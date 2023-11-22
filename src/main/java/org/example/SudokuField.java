package org.example;

import com.google.common.base.Objects;

public class SudokuField {

    private int value;
    private final SudokuObserver sudokuObserver;

    public SudokuField(SudokuObserver sudokuObserver) {
        this.sudokuObserver = sudokuObserver;
    }

    public boolean notifyObservers() {
        return sudokuObserver.verify(sudokuObserver.getLastUpdatedRow(), sudokuObserver.getLastUpdatedColumn());
    }

    public void setFieldValue(int value) {
        if (value >= 0 && value <= 9) {
            this.value = value;
        }
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
}
