package org.example;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class SudokuFieldTest {
    @Test
    public void thatFieldsAreSetCorrectly() {
        SudokuSolver sudokuSolver = new BacktrackingSudokuSolver();
        SudokuBoard sudokuBoard = new SudokuBoard(sudokuSolver);
        SudokuField sudokuField1 = new SudokuField(sudokuBoard);
        SudokuField sudokuField2 = new SudokuField(sudokuBoard);
        SudokuField sudokuField3 = new SudokuField(sudokuBoard);
        SudokuField sudokuField4 = new SudokuField(sudokuBoard);

        sudokuField1.setFieldValue(2);
        sudokuField2.setFieldValue(3);
        sudokuField3.setFieldValue(5);
        sudokuField4.setFieldValue(1);

        SudokuPart sudokuPart = new SudokuRow();
        sudokuPart.setSudokuFields(List.of(sudokuField1, sudokuField2, sudokuField3, sudokuField4));

        Assertions.assertEquals(2, sudokuField1.getFieldValue());
        Assertions.assertEquals(3, sudokuField2.getFieldValue());
        Assertions.assertEquals(5, sudokuField3.getFieldValue());
        Assertions.assertEquals(1, sudokuField4.getFieldValue());

        sudokuField4.setFieldValue(-3);
        sudokuField3.setFieldValue(10);
        Assertions.assertNotEquals(-3, sudokuField4.getFieldValue());
        Assertions.assertNotEquals(10, sudokuField3.getFieldValue());
        Assertions.assertTrue(sudokuPart.verify());
    }
    @Test
    public void thatFieldsEqualsCorrectly(){
        SudokuSolver sudokuSolver = new BacktrackingSudokuSolver();
        SudokuBoard sudokuBoard = new SudokuBoard(sudokuSolver);
        SudokuField sudokuField1 = new SudokuField(sudokuBoard);
        SudokuField sudokuField2 = new SudokuField(sudokuBoard);
        Assertions.assertEquals(sudokuField1, sudokuField2);
        Assertions.assertEquals(sudokuField1.hashCode(), sudokuField2.hashCode());

        sudokuField1.setFieldValue(1);

        Assertions.assertNotEquals(sudokuField1, sudokuField2);

        Assertions.assertNotEquals(sudokuField2, null);
        Assertions.assertFalse(sudokuField1.equals(sudokuBoard));

        sudokuField1 = sudokuField2;

        Assertions.assertEquals(sudokuField1, sudokuField2);
    }
}
