package org.example;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

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
        SudokuBox sudokuBox = new SudokuBox();
        SudokuColumn sudokuColumn = new SudokuColumn();
        SudokuRow sudokuRow = new SudokuRow();
        SudokuSolver sudokuSolver = new BacktrackingSudokuSolver();
        SudokuBoard sudokuBoard = new SudokuBoard(sudokuSolver);
        sudokuBoard.attach(List.of(sudokuRow,sudokuColumn,sudokuBox));
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
    void thatGetterAndSetterWorkCorrectly() {
        SudokuBox sudokuBox = new SudokuBox();
        SudokuColumn sudokuColumn = new SudokuColumn();
        SudokuRow sudokuRow = new SudokuRow();
        SudokuSolver sudokuSolver = new BacktrackingSudokuSolver();
        SudokuBoard sudokuBoard = new SudokuBoard(sudokuSolver);
        sudokuBoard.attach(List.of(sudokuRow,sudokuColumn,sudokuBox));

        sudokuBoard.set(0, 0, 4);
        sudokuBoard.set(0, 1, 10);
        sudokuBoard.set(0, -2, 1);
        sudokuBoard.set(0, 10, 2);
        sudokuBoard.set(0, 10, -3);
        sudokuBoard.set(-1, 0, 1);
        sudokuBoard.set(10, 0, 1);

        Assertions.assertEquals(4, sudokuBoard.get(0, 0));
        Assertions.assertEquals(0, sudokuBoard.get(0, 1));
        Assertions.assertEquals(0, sudokuBoard.get(0, 2));
        Assertions.assertNull(sudokuBoard.get(0, -2));
        Assertions.assertNull(sudokuBoard.get(-1, 0));
        Assertions.assertNull(sudokuBoard.get(0, 10));
        Assertions.assertNull(sudokuBoard.get(10, 0));
    }

    @Test
    void getRowTest() {
        SudokuBox sudokuBox = new SudokuBox();
        SudokuColumn sudokuColumn = new SudokuColumn();
        SudokuRow sudokuRow = new SudokuRow();

        SudokuSolver sudokuSolver = new BacktrackingSudokuSolver();
        SudokuBoard sudokuBoard = new SudokuBoard(sudokuSolver);
        sudokuBoard.attach(List.of(sudokuRow,sudokuColumn,sudokuBox));

        sudokuBoard.solveGame();

        Assertions.assertNull(sudokuBoard.getRow(9));
        Assertions.assertNull(sudokuBoard.getRow(-1));
        Assertions.assertNotNull(sudokuBoard.getRow(8));

        SudokuRow row = sudokuBoard.getRow(4);

        Assertions.assertEquals(sudokuBoard.get(4, 2), row.sudokuFields.get(2).getFieldValue());
        Assertions.assertTrue(row.verify());
    }

    @Test
    void getColumnTest() {
        SudokuBox sudokuBox = new SudokuBox();
        SudokuColumn sudokuColumn = new SudokuColumn();
        SudokuRow sudokuRow = new SudokuRow();
        SudokuSolver sudokuSolver = new BacktrackingSudokuSolver();
        SudokuBoard sudokuBoard = new SudokuBoard(sudokuSolver);
        sudokuBoard.attach(List.of(sudokuRow,sudokuColumn,sudokuBox));
        sudokuBoard.solveGame();

        Assertions.assertNull(sudokuBoard.getColumn(9));
        Assertions.assertNull(sudokuBoard.getColumn(-1));
        Assertions.assertNotNull(sudokuBoard.getColumn(8));

        SudokuColumn column = sudokuBoard.getColumn(4);

        Assertions.assertEquals(sudokuBoard.get(2, 4), column.sudokuFields.get(2).getFieldValue());
        Assertions.assertTrue(column.verify());
    }

    @Test
    void getSudokuBoxTest() {
        SudokuSolver sudokuSolver = new BacktrackingSudokuSolver();
        SudokuBox sudokuBox = new SudokuBox();
        SudokuColumn sudokuColumn = new SudokuColumn();
        SudokuRow sudokuRow = new SudokuRow();
        SudokuBoard sudokuBoard = new SudokuBoard(sudokuSolver);
        sudokuBoard.attach(List.of(sudokuRow,sudokuColumn,sudokuBox));
        sudokuBoard.solveGame();

        Assertions.assertNull(sudokuBoard.getBox(9, 8));
        Assertions.assertNull(sudokuBoard.getBox(-9, 8));
        Assertions.assertNull(sudokuBoard.getBox(8, -8));
        Assertions.assertNull(sudokuBoard.getBox(8, 9));
        Assertions.assertNotNull(sudokuBoard.getBox(8, 8));

        SudokuBox sudokuBox2 = sudokuBoard.getBox(2, 1);

        Assertions.assertEquals(sudokuBoard.get(1, 0), sudokuBox2.sudokuFields.get(3).getFieldValue());
        Assertions.assertTrue(sudokuBox2.verify());
    }

    @Test
    public void attachAndDetachWorksCorrectly(){
        SudokuBox sudokuBox = new SudokuBox();
        SudokuColumn sudokuColumn = new SudokuColumn();
        SudokuRow sudokuRow = new SudokuRow();
        SudokuSolver sudokuSolver = new BacktrackingSudokuSolver();
        SudokuBoard sudokuBoard = new SudokuBoard(sudokuSolver);
        sudokuBoard.attach(List.of(sudokuRow, sudokuColumn, sudokuBox));
        Assertions.assertEquals(3, sudokuBoard.getSudokuObservers().size());

        sudokuBoard.detach(sudokuBox);
        Assertions.assertEquals(2, sudokuBoard.getSudokuObservers().size());
    }

    @Test
    public void checkingWorksCorrectly() {
        SudokuBox sudokuBox = new SudokuBox();
        SudokuColumn sudokuColumn = new SudokuColumn();
        SudokuRow sudokuRow = new SudokuRow();

        SudokuSolver sudokuSolver = new BacktrackingSudokuSolver();
        SudokuBoard sudokuBoard = new SudokuBoard(sudokuSolver);
        sudokuBoard.attach(List.of(sudokuRow,sudokuColumn,sudokuBox));

        Assertions.assertTrue(sudokuBoard.setAndCheck(sudokuBoard, 0, 0, 4));

        Assertions.assertFalse(sudokuBoard.setAndCheck(sudokuBoard, 0, 3, 4));
        Assertions.assertFalse(sudokuBoard.setAndCheck(sudokuBoard, 3, 0, 4));
        Assertions.assertFalse(sudokuBoard.setAndCheck(sudokuBoard, 1, 1, 4));
        Assertions.assertFalse(sudokuBoard.setAndCheck(sudokuBoard, 1, 1, 4));

        Assertions.assertFalse(sudokuBoard.setAndCheck(sudokuBoard, -3, 1, 5));
        Assertions.assertFalse(sudokuBoard.setAndCheck(sudokuBoard, 10, 1, 6));
        Assertions.assertFalse(sudokuBoard.setAndCheck(sudokuBoard, 2, -1, 7));
        Assertions.assertFalse(sudokuBoard.setAndCheck(sudokuBoard, 2, 10, 8));
        Assertions.assertFalse(sudokuBoard.setAndCheck(sudokuBoard, 2, 3, 10));
        Assertions.assertFalse(sudokuBoard.setAndCheck(sudokuBoard, 2, 5, -1));
    }
}