package org.example;

import org.example.database.JpaSudokuBoardDao;
import org.example.exceptions.FileDaoException;
import org.example.exceptions.SudokuBoardCloneFailureException;
import org.example.exceptions.SudokuFieldWrongException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class SudokuBoardTest {
    @Test
    public void isDifferent() {
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
    public void gridAlignment() {
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
    public void thatGetterAndSetterWorkCorrectly() {
        SudokuSolver sudokuSolver = new BacktrackingSudokuSolver();
        SudokuBoard sudokuBoard = new SudokuBoard(sudokuSolver);

        sudokuBoard.set(0, 0, 4);
        Assertions.assertThrows(SudokuFieldWrongException.class, () -> sudokuBoard.set(0, 1, 10));
        Assertions.assertThrows(SudokuFieldWrongException.class, () -> sudokuBoard.set(0, -2, 1));
        Assertions.assertThrows(SudokuFieldWrongException.class, () -> sudokuBoard.set(0, 10, 2));
        Assertions.assertThrows(SudokuFieldWrongException.class, () -> sudokuBoard.set(0, 10, -3));
        Assertions.assertThrows(SudokuFieldWrongException.class, () -> sudokuBoard.set(-1, 0, 1));
        Assertions.assertThrows(SudokuFieldWrongException.class, () -> sudokuBoard.set(10, 0, 1));


        Assertions.assertEquals(4, sudokuBoard.get(0, 0));
        Assertions.assertEquals(0, sudokuBoard.get(0, 1));
        Assertions.assertEquals(0, sudokuBoard.get(0, 2));
        Assertions.assertNull(sudokuBoard.get(0, -2));
        Assertions.assertNull(sudokuBoard.get(-1, 0));
        Assertions.assertNull(sudokuBoard.get(0, 10));
        Assertions.assertNull(sudokuBoard.get(10, 0));
    }

    @Test
    public void getRowTest() {

        SudokuSolver sudokuSolver = new BacktrackingSudokuSolver();
        SudokuBoard sudokuBoard = new SudokuBoard(sudokuSolver);

        sudokuBoard.solveGame();

        Assertions.assertNull(sudokuBoard.getRow(9));
        Assertions.assertNull(sudokuBoard.getRow(-1));
        Assertions.assertNotNull(sudokuBoard.getRow(8));

        SudokuRow row = sudokuBoard.getRow(4);
        for (int i = 0; i < 9; i++) {
            Assertions.assertEquals(sudokuBoard.get(4, i), row.sudokuFields.get(i).getFieldValue());
        }
        Assertions.assertTrue(row.verify());
    }

    @Test
    public void getColumnTest() {
        SudokuSolver sudokuSolver = new BacktrackingSudokuSolver();
        SudokuBoard sudokuBoard = new SudokuBoard(sudokuSolver);
        sudokuBoard.solveGame();

        Assertions.assertNull(sudokuBoard.getColumn(9));
        Assertions.assertNull(sudokuBoard.getColumn(-1));
        Assertions.assertNotNull(sudokuBoard.getColumn(8));

        SudokuColumn column = sudokuBoard.getColumn(4);
        for (int i = 0; i < 9; i++) {
            Assertions.assertEquals(sudokuBoard.get(i, 4), column.sudokuFields.get(i).getFieldValue());
        }

        Assertions.assertTrue(column.verify());
    }

    @Test
    public void getSudokuBoxTest() {
        SudokuSolver sudokuSolver = new BacktrackingSudokuSolver();
        SudokuBoard sudokuBoard = new SudokuBoard(sudokuSolver);
        sudokuBoard.solveGame();

        Assertions.assertNull(sudokuBoard.getBox(9, 8));
        Assertions.assertNull(sudokuBoard.getBox(-9, 8));
        Assertions.assertNull(sudokuBoard.getBox(8, -8));
        Assertions.assertNull(sudokuBoard.getBox(8, 9));
        Assertions.assertNotNull(sudokuBoard.getBox(8, 8));


        SudokuBox sudokuBox2 = sudokuBoard.getBox(2, 1);
        List<SudokuField> sudokuFields = sudokuBox2.getSudokuFields();
        int counter = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                Assertions.assertEquals(sudokuBoard.get(i, j), sudokuFields.get(counter).getFieldValue());
                counter++;
            }
        }
    }

    @Test
    public void thatGetLastUpdatedRowAndColumnWorksCorrectly() {
        SudokuSolver sudokuSolver = new BacktrackingSudokuSolver();
        SudokuBoard sudokuBoard = new SudokuBoard(sudokuSolver);
        Assertions.assertEquals(0, sudokuBoard.getLastUpdatedRow());
        Assertions.assertEquals(0, sudokuBoard.getLastUpdatedColumn());
        sudokuBoard.solveGame();
        Assertions.assertEquals(8, sudokuBoard.getLastUpdatedRow());
        Assertions.assertEquals(8, sudokuBoard.getLastUpdatedColumn());
    }

    @Test
    public void checkingWorksCorrectly() {
        SudokuSolver sudokuSolver = new BacktrackingSudokuSolver();
        SudokuBoard sudokuBoard = new SudokuBoard(sudokuSolver);

        Assertions.assertTrue(sudokuBoard.setAndCheck(0, 0, 4));

        Assertions.assertFalse(sudokuBoard.setAndCheck(0, 3, 4));
        Assertions.assertFalse(sudokuBoard.setAndCheck(3, 0, 4));
        Assertions.assertFalse(sudokuBoard.setAndCheck(1, 1, 4));
        Assertions.assertFalse(sudokuBoard.setAndCheck(1, 1, 4));

        Assertions.assertFalse(sudokuBoard.setAndCheck(-3, 1, 5));
        Assertions.assertFalse(sudokuBoard.setAndCheck(10, 1, 6));
        Assertions.assertFalse(sudokuBoard.setAndCheck(2, -1, 7));
        Assertions.assertFalse(sudokuBoard.setAndCheck(2, 10, 8));
        Assertions.assertFalse(sudokuBoard.setAndCheck(2, 3, 10));
        Assertions.assertFalse(sudokuBoard.setAndCheck(2, 5, -1));
    }

    @Test
    public void sudokuBoardEqualsWorksCorrectly() {
        SudokuSolver sudokuSolver = new BacktrackingSudokuSolver();
        SudokuBoard sudokuBoard1 = new SudokuBoard(sudokuSolver);
        SudokuBoard sudokuBoard2 = new SudokuBoard(sudokuSolver);
        Assertions.assertEquals(sudokuBoard1, sudokuBoard2);
        Assertions.assertEquals(sudokuBoard1.hashCode(), sudokuBoard2.hashCode());
        sudokuBoard2.solveGame();
        Assertions.assertNotEquals(sudokuBoard1, sudokuBoard2);
        sudokuBoard1 = null;
        Assertions.assertNotEquals(sudokuBoard2, sudokuBoard1);
        sudokuBoard1 = sudokuBoard2;
        Assertions.assertEquals(sudokuBoard1, sudokuBoard2);
        SudokuPart sudokuPart = new SudokuColumn();
        Assertions.assertNotEquals(sudokuBoard1, sudokuPart);
    }

    @Test
    public void thatSerializationAndDeserializationLoadsCorrectly() throws IOException {
        SudokuSolver sudokuSolver = new BacktrackingSudokuSolver();
        SudokuBoard sudokuBoard = new SudokuBoard(sudokuSolver);
        sudokuBoard.solveGame();


        String path = ".\\sudokuBoard.txt";
        Dao<SudokuBoard> fileDao = SudokuBoardDaoFactory.getFileDao(path);
        fileDao.write(sudokuBoard);
        File file1 = new File(path);
        fileDao.write(sudokuBoard);

        Assertions.assertTrue(file1.isFile() && file1.length() > 100);

        SudokuBoard read1 = fileDao.read();

        Assertions.assertEquals(sudokuBoard.toString(), read1.toString());

        String fileName = ".\\drugiSudokuBoard.txt";
        Dao<SudokuBoard> fileDao2 = SudokuBoardDaoFactory.getFileDao(fileName);

        fileDao2.write(sudokuBoard);
        SudokuBoard read2 = fileDao2.read();

        Assertions.assertEquals(read1.toString(), read2.toString());

        File file2 = new File(fileName);


        Assertions.assertEquals(file1.getTotalSpace(), file2.getTotalSpace());
    }

    @Test
    public void thatSerializationAndDeserializationLoadsBadly() {
        SudokuSolver sudokuSolver = new BacktrackingSudokuSolver();
        SudokuBoard sudokuBoard = new SudokuBoard(sudokuSolver);
        sudokuBoard.solveGame();


        String badPath = "./kompoMnieJedzie/xd";
        Dao<SudokuBoard> fileDao3 = SudokuBoardDaoFactory.getFileDao(badPath);
        Assertions.assertThrows(Exception.class, () -> fileDao3.write(sudokuBoard));
        Assertions.assertThrows(Exception.class, fileDao3::read);
    }

    @Test
    public void databaseSystemWorksCorrectly() {
        SudokuSolver sudokuSolver = new BacktrackingSudokuSolver();
        SudokuBoard sudokuBoard = new SudokuBoard(sudokuSolver);
        sudokuBoard.solveGame();

        try (JpaSudokuBoardDao sudokuBoardDao = new JpaSudokuBoardDao("zmiecieZPlanszy")) {
            sudokuBoardDao.write(sudokuBoard);
            SudokuBoard read = sudokuBoardDao.read();

            for (int row = 0; row < 9; row++) {
                for (int column = 0; column < 9; column++) {
                    Assertions.assertNotEquals(0, read.get(row, column));
                    Assertions.assertEquals(sudokuBoard.get(row, column), read.get(row, column));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            Assertions.fail("Exception occurred: " + e.getMessage());
        }
    }
}