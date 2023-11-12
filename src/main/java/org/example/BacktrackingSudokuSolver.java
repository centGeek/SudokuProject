package org.example;

import java.util.ArrayList;
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

        List<Integer> numbers = new ArrayList<>();
        for (int num = 1; num <= 9; num++) {
            numbers.add(num);
        }
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
                    return List.of(row, column);
                }
            }
        }
        return null;
    }
}
