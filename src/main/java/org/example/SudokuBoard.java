package org.example;

import java.util.Arrays;
import java.util.List;

public class SudokuBoard implements SudokuObserver {
    private final int number = 9;
    private int lastUpdatedRow;
    private int lastUpdatedColumn;
    private final SudokuField[][] board = new SudokuField[number][number];
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
                row.set(column, board[y][column]);
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
                column.set(row, board[row][x]);
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
            List<SudokuField> sudokuFieldList = Arrays.asList(new SudokuField[number]);
            int startRow = (x / 3) * 3;
            int startCol = (y / 3) * 3;
            int num = 0;
            for (int row = startRow; row < startRow + 3; row++) {
                for (int col = startCol; col < startCol + 3; col++) {
                    sudokuFieldList.set(num, board[row][col]);
                    num++;
                }
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
                board[x][y] = new SudokuField(this);
            }
        }
    }

    public void solveGame() {
        sudokuSolver.solve(this);
    }

    public Integer get(int x, int y) {
        if (x >= 0 && x <= 9 && y >= 0 && y <= 9) {
            return board[x][y].getFieldValue();
        }
        return null;
    }

    public void set(int x, int y, int value) {
        if (value >= 0 && value <= 9 && x >= 0 && x <= 9 && y >= 0 && y <= 9) {
            board[x][y].setFieldValue(value);
        }
    }

    public boolean setAndCheck(int x, int y, int value) {
        if (value >= 0 && value <= 9 && x >= 0 && x <= 9 && y >= 0 && y <= 9) {
            lastUpdatedColumn = y;
            lastUpdatedRow = x;
            this.set(x, y, value);
            return board[x][y].notifyObservers();
        }
        return false;
    }

    @Override
    public boolean verify(int row, int column) {
        return getBox(row, column).verify() && getRow(row).verify() && getColumn(column).verify();
    }

}
