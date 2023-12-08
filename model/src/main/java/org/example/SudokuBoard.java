package org.example;

import com.google.common.base.Objects;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

public class SudokuBoard implements SudokuObserver, Serializable, Cloneable {
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
        if (x >= 0 && x <= 9 && y >= 0 && y <= 9) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SudokuBoard that = (SudokuBoard) o;

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (!(that.board[i][j].getFieldValue() == board[i][j].getFieldValue())) {
                    return false;
                }
            }
        }
        return Objects.equal(sudokuSolver, that.sudokuSolver);
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(sudokuSolver);
        result = 31 * result + Arrays.deepHashCode(board);
        return result;
    }

    @Override
    public String toString() {
        StringBuilder boardToString = new StringBuilder();
        for (int row = 0; row < 9; row++) {
            String redText = "\u001B[31m" + "%s" + "\u001B[0m";
            if (row % 3 == 0) {
                boardToString.append("\n");
            }
            for (int column = 0; column < 9; column++) {
                if (column % 3 == 0) {
                    boardToString.append("  ");
                }
                if (column > 2 && column < 6) {
                    boardToString.append(String.format(redText, board[row][column].getFieldValue() + " "));
                    continue;
                }
                if (row > 2 && row < 6) {
                    boardToString.append(String.format(redText, board[row][column].getFieldValue() + " "));
                } else {
                    boardToString.append(board[row][column].getFieldValue()).append(" ");
                }
            }
            boardToString.append("\n");
        }
        return "      SudokuBoard {" + boardToString + "sudokuSolver -> " + sudokuSolver + " }";
    }

    @Override
    public SudokuBoard clone() {
        SudokuBoard sudokuBoard;
        try {
            sudokuBoard = (SudokuBoard) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
        for (int row = 0; row < number; row++) {
            for (int column = 0; column < number; column++) {
                sudokuBoard.set(row, column, board[row][column].getFieldValue());
            }
        }
        return sudokuBoard;
    }
}
