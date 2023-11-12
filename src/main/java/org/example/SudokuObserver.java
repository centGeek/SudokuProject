package org.example;

public interface SudokuObserver {
    boolean verify(int row, int column);

    int getLastUpdatedRow();

    int getLastUpdatedColumn();

}
