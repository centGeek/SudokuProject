package org.example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SudokuBoard implements SudokuObserver {
    private final int number = 9;
    private int lastUpdatedRow;
    private int lastUpdatedColumn;
    private final SudokuField[][] sudokuFields = new SudokuField[number][number];
    private final SudokuSolver sudokuSolver;

    public int getLastUpdatedRow() {
        return lastUpdatedRow;
    }

    public int getLastUpdatedColumn() {
        return lastUpdatedColumn;
    }


    public SudokuRow getRow(Integer y) {
        if (y >= 0 && y < 9) {
            List<SudokuField> row = Arrays.asList(new SudokuField[number]);
            for (int column = 0; column < number; column++) {
                row.set(column, sudokuFields[y][column]);
            }
            SudokuRow sudokuRow = new SudokuRow();
            sudokuRow.setSudokuFields(row);
            return sudokuRow;
        }
        return null;
    }

    public SudokuColumn getColumn(Integer x) {
        if (x >= 0 && x < 9) {
            List<SudokuField> column = Arrays.asList(new SudokuField[number]);
            for (int row = 0; row < number; row++) {
                column.set(row, sudokuFields[row][x]);
            }
            SudokuColumn sudokuColumn = new SudokuColumn();
            sudokuColumn.setSudokuFields(column);
            return sudokuColumn;
        }
        return null;
    }

    public SudokuBox getBox(Integer x, Integer y) {
        if (x >= 0 && x < 9 && y >= 0 && y < 9) {
            SudokuBox sudokuBox = new SudokuBox();
            List<SudokuField> sudokuFieldList = new ArrayList<>(number);
            int startRow = (x / 3) * 3;
            int startCol = (y / 3) * 3;
            for (int row = startRow; row < startRow + 3; row++) {
                sudokuFieldList.addAll(Arrays.asList(sudokuFields[row]).subList(startCol, startCol + 3));
            }
            sudokuBox.setSudokuFields(sudokuFieldList);
            return sudokuBox;
        }
        return null;
    }


    public SudokuBoard(SudokuSolver sudokuSolver) {
        this.sudokuSolver = sudokuSolver;
        for (int x = 0; x < number; x++) {
            for (int y = 0; y < number; y++) {
                sudokuFields[x][y] = new SudokuField(this);
            }
        }
    }

    public void solveGame() {
        sudokuSolver.solve(this);
    }

    public Integer get(int x, int y) {
        if (x >= 0 && x <= 9 && y >= 0 && y <= 9) {
            return sudokuFields[x][y].getFieldValue();
        }
        return null;
    }

    public void set(int x, int y, int value) {
        if (value >= 0 && value <= 9 && x >= 0 && x <= 9 && y >= 0 && y <= 9) {
            sudokuFields[x][y].setFieldValue(value);
        }
    }

    public boolean setAndCheck(int x, int y, int value) {
        if (value >= 0 && value <= 9 && x >= 0 && x <= 9 && y >= 0 && y <= 9) {
            lastUpdatedColumn = y;
            lastUpdatedRow = x;
            this.set(x, y, value);
            return sudokuFields[x][y].notifyObservers();
        }
        return false;
    }

    @Override
    public boolean verify(int row, int column) {
        return getBox(row, column).verify() && getRow(row).verify() && getColumn(column).verify();
    }

}
