package org.example;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class SudokuBoardTest {
    @Test
    void isDifferent() {
        SudokuBoard sudokuBoard1 = new SudokuBoard();
        SudokuBoard sudokuBoard2 = new SudokuBoard();
        sudokuBoard1.fillBoard();
        sudokuBoard2.fillBoard();
        boolean isTrue = false;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (sudokuBoard1.getNumber(i, j) != sudokuBoard2.getNumber(i, j)) {
                    isTrue = true;
                    break;
                }
            }
        }
        Assertions.assertTrue(isTrue);
    }

    @Test
    void gridAlignment() {
        SudokuBoard sudokuBoard = new SudokuBoard();
        sudokuBoard.fillBoard();
        int number = 0;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                number = sudokuBoard.getNumber(i, j); //sprawdzanie po wierszach
                for (int k = 0; k < 9; k++) {
                    if (j == k) {
                        continue;
                    }
                    Assertions.assertNotEquals(number, sudokuBoard.getNumber(i, k));
                }
            }
        }
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                number = sudokuBoard.getNumber(j, i); //sprawdzanie po kolumnach
                for (int k = 0; k < 9; k++) {
                    if (j == k) {
                        continue;
                    }
                    Assertions.assertNotEquals(number, sudokuBoard.getNumber(k, i));
                }
            }
        }
        for (int i = 0; i < 9; i += 3) {
            for (int j = 0; j < 9; j += 3) {
                for (int x = i; x < i + 3; x++) {
                    for (int y = j; y < j + 3; y++) {
                        number = sudokuBoard.getNumber(x, y); // sprawdzanie kwadratów 3x3
                        for (int a = i; a < i + 3; a++) {
                            for (int b = j; b < j + 3; b++) {
                                if (x == a && y == b) {
                                    continue;
                                }
                                Assertions.assertNotEquals(number, sudokuBoard.getNumber(a, b));
                            }
                        }
                    }
                }
            }
        }
    }
}