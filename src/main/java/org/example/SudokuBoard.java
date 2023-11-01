package org.example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SudokuBoard implements SudokuObserver {
    private final int number = 9;
    private final SudokuField[][] sudokuBoard = new SudokuField[number][number];
    private final SudokuSolver sudokuSolver;

    public SudokuRow getRow(Integer y) {
        if (y >= 0 && y < 9) {
            List<SudokuField> column = new ArrayList<>(number);
            column.addAll(Arrays.asList(sudokuBoard[y]).subList(0, number));
            SudokuRow sudokuRow = new SudokuRow();
            sudokuRow.setSudokuFields(column);
            return sudokuRow;
        }
        return null;
    }

    public SudokuColumn getColumn(Integer x) {
        if (x >= 0 && x < 9) {
            List<SudokuField> column = new ArrayList<>(number);
            for (int row = 0; row < number; row++) {
                column.add(sudokuBoard[row][x]);
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
            List<SudokuField> sudokuFieldList = new ArrayList<>();
            int startRow = (x / 3) * 3;
            int startCol = (y / 3) * 3;
            for (int row = startRow; row < startRow + 3; row++) {
                sudokuFieldList.addAll(Arrays.asList(sudokuBoard[row]).subList(startCol, startCol + 3));
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
                sudokuBoard[x][y] = new SudokuField();
            }
        }
    }

    public void solveGame() {
        sudokuSolver.solve(this);
    }

    public Integer get(int x, int y) {
        if (x >= 0 && x <= 9 && y >= 0 && y <= 9) {
            return sudokuBoard[x][y].getFieldValue();
        }
        return null;
    }

    public void set(int x, int y, int value) {
        if (value >= 0 && value <= 9 && x >= 0 && x <= 9 && y >= 0 && y <= 9) {
            sudokuBoard[x][y].setFieldValue(value);
        }
    }

    public boolean setAndCheck(SudokuBoard sudokuBoard, int x, int y, int value) {
        if (value >= 0 && value <= 9 && x >= 0 && x <= 9 && y >= 0 && y <= 9) {
            sudokuBoard.set(x, y, value);
            return checkBoard(sudokuBoard, x, y);
        }
        return false;
    }

    public boolean checkBoard(SudokuBoard board, int row, int column) {
        return board.getRow(row).verify() && board.getColumn(column).verify() && board.getBox(row, column).verify();
    }

}
