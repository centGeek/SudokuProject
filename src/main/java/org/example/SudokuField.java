package org.example;

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
        this.value = value;
    }

    public int getFieldValue() {
        return value;
    }
}
