package org.example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SudokuBoard {
    private final int number = 9;
    private final SudokuField[][] board = new SudokuField[number][number];
    private final SudokuSolver sudokuSolver;


    public SudokuRow getRow(Integer y) {
        List<SudokuField> row = new ArrayList<>(number);
        for (int column = 0; column < number; column++) {
            row.add(board[y][column]);
        }
        SudokuRow sudokuRow = new SudokuRow();
        sudokuRow.setSudokuFields(row);
        return sudokuRow;
    }

    public SudokuColumn getColumn(Integer x) {
        List<SudokuField> column = new ArrayList<>(number);
        for (int row = 0; row < number; row++) {
            column.add(board[row][x]);
        }
        SudokuColumn sudokuColumn = new SudokuColumn();
        sudokuColumn.setSudokuFields(column);
        return sudokuColumn;
    }

    public SudokuBox getBox(Integer x, Integer y) {
        SudokuBox sudokuBox = new SudokuBox();
        List<SudokuField> sudokuFieldList = new ArrayList<>();
        int startRow = (x / 3) * 3;
        int startCol = (y / 3) * 3;
        for (int row = startRow; row < startRow + 3; row++) {
            sudokuFieldList.addAll(Arrays.asList(board[row]).subList(startCol, startCol + 3));
        }
        sudokuBox.setSudokuFields(sudokuFieldList);
        return sudokuBox;
    }


    public SudokuBoard(SudokuSolver sudokuSolver) {
        this.sudokuSolver = sudokuSolver;
        for (int x = 0; x < number; x++) {
            for (int y = 0; y < number; y++) {
                board[x][y] = new SudokuField();
            }
        }
    }

    public void solveGame() {
        sudokuSolver.solve(this);
    }

    public Integer get(int x, int y) {
        if(board[x][y]==null){
            return 0;
        }
        return board[x][y].getFieldValue();
    }

    public void set(int x, int y, int value) {
        if (value >= 0 && value <= 9 && x >= 0 && x <= 9 && y >= 0 && y <= 9) {
            board[x][y].setFieldValue(value);
        }
    }

    public void printSudoku() {
        for (int row = 0; row < 9; row++) {
            String redText = "\u001B[31m" + "%s" + "\u001B[0m";
            if (row % 3 == 0) {
                System.out.println();
            }
            for (int column = 0; column < 9; column++) {
                if (column % 3 == 0) {
                    System.out.print("  ");
                }
                if (column > 2 && column < 6) {
                    System.out.printf(redText, board[row][column].getFieldValue() + " ");
                    continue;
                }
                if (row > 2 && row < 6) {
                    System.out.printf(redText, board[row][column].getFieldValue()+ " ");
                } else {
                    System.out.print(board[row][column].getFieldValue() + " ");
                }
            }
            System.out.println();
        }
    }
}
