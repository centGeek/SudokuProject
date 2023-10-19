package org.example;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TypeSudokuSolver implements SudokuSolver{

    @Override
    public void solve(SudokuBoard sudokuBoard) {
       solveSudoku(sudokuBoard);
    }
    private boolean solveSudoku(SudokuBoard sudokuBoard) {
        List<Integer> emptyCell = findEmptyCell(sudokuBoard);
        if (emptyCell == null) {
            return true;
        }

        int row = emptyCell.get(0);
        int column = emptyCell.get(1);

        List<Integer> numbers = new ArrayList<>();
        for (int num = 1; num <= 9; num++) {
            numbers.add(num);
        }
        Collections.shuffle(numbers);

        for (int num : numbers) {
            if (isValidMove(sudokuBoard, row, column, num)) {
                sudokuBoard.setNumber(row, column, num);

                if (solveSudoku(sudokuBoard)) {
                    return true;
                }

                sudokuBoard.setNumber(row,column,0);
            }
        }

        return false;
    }
    private boolean isValidMove(SudokuBoard sudokuBoard, int row, int column, int num) {
        for (int i = 0; i < 9; i++) {
            if (sudokuBoard.getNumber(row,i) == num || sudokuBoard.getNumber(i, column) == num) {
                return false;
            }
        }

        int startRow = (row / 3) * 3;
        int startCol = (column / 3) * 3;
        for (int i = startRow; i < startRow + 3; i++) {
            for (int j = startCol; j < startCol + 3; j++) {
                if (sudokuBoard.getNumber(i,j) == num) {
                    return false;
                }
            }
        }

        return true;
    }

    private List<Integer> findEmptyCell(SudokuBoard sudokuBoard) {
        for (int row = 0; row < 9; row++) {
            for (int column = 0; column < 9; column++) {
                if (sudokuBoard.getNumber(row, column) == 0) {
                    return List.of(row, column);
                }
            }
        }
        return null;
    }
}
