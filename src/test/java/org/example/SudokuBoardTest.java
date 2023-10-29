package org.example;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

class SudokuBoardTest {
    @Test
    void isDifferent() {
        SudokuSolver sudokuSolver = new BacktrackingSudokuSolver();
        SudokuBoard sudokuBoard = new SudokuBoard(sudokuSolver);
        sudokuBoard.solveGame();
        int[][] board1 = new int[9][9];
        int[][] board2 = new int[9][9];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                board1[i][j] = sudokuBoard.get(i, j);
            }
        }
        sudokuBoard.solveGame();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                board2[i][j] = sudokuBoard.get(i, j);
            }
        }
        Assertions.assertFalse(Arrays.deepEquals(board1, board2));
    }

    @Test
    void gridAlignment() {
        SudokuSolver sudokuSolver = new BacktrackingSudokuSolver();
        SudokuBoard sudokuBoard = new SudokuBoard(sudokuSolver);
        sudokuBoard.solveGame();
        int number;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                number = sudokuBoard.get(i, j); //sprawdzanie po wierszach`
                for (int k = 0; k < 9; k++) {
                    if (j == k) {
                        continue;
                    }
                    Assertions.assertNotEquals(number, sudokuBoard.get(i, k));
                }
            }
        }
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                number = sudokuBoard.get(j, i); //sprawdzanie po kolumnach
                for (int k = 0; k < 9; k++) {
                    if (j == k) {
                        continue;
                    }
                    Assertions.assertNotEquals(number, sudokuBoard.get(k, i));
                }
            }
        }
        for (int i = 0; i < 9; i += 3) {
            for (int j = 0; j < 9; j += 3) {
                for (int x = i; x < i + 3; x++) {
                    for (int y = j; y < j + 3; y++) {
                        number = sudokuBoard.get(x, y); // sprawdzanie kwadratów 3x3
                        for (int a = i; a < i + 3; a++) {
                            for (int b = j; b < j + 3; b++) {
                                if (x == a && y == b) {
                                    continue;
                                }
                                Assertions.assertNotEquals(number, sudokuBoard.get(a, b));
                            }
                        }
                    }
                }
            }
        }
    }
    @Test
    void thatGetterAndSetterWorkCorrectly(){
        SudokuSolver sudokuSolver = new BacktrackingSudokuSolver();
        SudokuBoard sudokuBoard = new SudokuBoard(sudokuSolver);

        sudokuBoard.set(0,0,4);
        sudokuBoard.set(0,1,10);

        Assertions.assertEquals(4,sudokuBoard.get(0,0));
        Assertions.assertEquals(0,sudokuBoard.get(0,1));
    }
}