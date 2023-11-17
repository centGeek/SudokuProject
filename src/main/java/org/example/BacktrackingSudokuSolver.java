package org.example;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class BacktrackingSudokuSolver implements SudokuSolver {
    @Override
    public void solve(SudokuBoard board) {
        clearBoard(board);
        solveSudoku(board);
    }

    private void clearBoard(SudokuBoard board) {
        if (board.get(0, 0) != 0) {
            for (int row = 0; row < 9; row++) {
                for (int column = 0; column < 9; column++) {
                    board.set(row, column, 0);
                }
            }
        }
    }

    private boolean solveSudoku(SudokuBoard board) {
        List<Integer> emptyCell = findEmptyCell(board);
        if (emptyCell == null) {
            return true;
        }

        int row = emptyCell.get(0);
        int column = emptyCell.get(1);

        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);
        Collections.shuffle(numbers);
        for (int num : numbers) {
            if (board.setAndCheck(row, column, num)) {
                if (solveSudoku(board)) {
                    return true;
                }
                board.set(row, column, 0);
            }
            board.set(row, column, 0);
        }
        return false;
    }


    private List<Integer> findEmptyCell(SudokuBoard board) {
        for (int row = 0; row < 9; row++) {
            for (int column = 0; column < 9; column++) {
                if (board.get(row, column) == 0) {
                    return Arrays.asList(row, column);
                }
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "BacktrackingSudokuSolver{}";
    }
}
